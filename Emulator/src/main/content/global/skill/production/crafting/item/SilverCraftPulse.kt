package content.global.skill.production.crafting.item

import content.global.skill.production.crafting.data.Silver
import core.api.*
import cfg.consts.Animations
import cfg.consts.Items
import cfg.consts.Sounds
import core.game.event.ResourceProducedEvent
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.scenery.Scenery
import core.game.system.task.Pulse

/**
 * Represents the skill pulse for silver crafting.
 *
 * @param player The player who is crafting the item.
 * @param product The product being crafted.
 * @param furnace The furnace where the crafting takes place.
 * @param amount the amount of item crafted.
 */
class SilverCraftPulse(
    val player: Player,
    val product: Silver,
    val furnace: Scenery,
    var amount: Int
) : Pulse() {

    override fun pulse(): Boolean {
        if (amount < 1) return true

        if (!inInventory(player, product.required) || !inInventory(player, Items.SILVER_BAR_2355)) {
            return true
        }

        animate(player, Animations.HUMAN_FURNACE_SMELTING_3243)
        playAudio(player, Sounds.FURNACE_2725)
        if (removeItem(player, Items.SILVER_BAR_2355, Container.INVENTORY)) {
            addItem(player, product.product, product.amount)
            rewardXP(player, Skills.CRAFTING, product.experience)

            player.dispatch(
                ResourceProducedEvent(
                    product.product,
                    product.amount,
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
