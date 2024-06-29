package content.global.skill.production.crafting.item

import content.global.skill.production.crafting.data.SilverData
import core.api.*
import core.api.consts.Animations
import core.api.consts.Items
import core.api.consts.Sounds
import core.game.event.ResourceProducedEvent
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.scenery.Scenery
import core.game.system.task.Pulse

class SilverCraftPulse(
    val player: Player,
    val product: SilverData,
    val furnace: Scenery,
    var amount: Int
) : Pulse() {

    override fun pulse(): Boolean {
        if (amount < 1) return true

        if (!inInventory(player, product.requiredItemId) || !inInventory(player, Items.SILVER_BAR_2355)) {
            return true
        }

        animate(player, Animations.HUMAN_FURNACE_SMELTING_3243)
        playAudio(player, Sounds.FURNACE_2725)
        if (removeItem(player, Items.SILVER_BAR_2355, Container.INVENTORY)) {
            addItem(player, product.producedItemId, product.amountProduced)
            rewardXP(player, Skills.CRAFTING, product.xpReward)

            player.dispatch(
                ResourceProducedEvent(
                    product.producedItemId,
                    product.amountProduced,
                    furnace,
                    Items.SILVER_BAR_2355
                )
            )
        } else return true

        amount--
        delay = 5

        return false
    }
}
