package content.global.skill.production.crafting.item

import content.global.skill.production.crafting.data.SilverData
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
 * Silver craft pulse
 *
 * @param player Represents the player involved in the crafting process.
 * @param product Represents the silver product being crafted.
 * @param furnace Represents the furnace used for crafting.
 * @param amount Represents the quantity of the product to be crafted.
 * @constructor Silver craft pulse initializes the crafting process with the specified parameters.
 */
class SilverCraftPulse(
    val player: Player, // The player who is crafting the silver product
    val product: SilverData, // The specific silver product being crafted
    val furnace: Scenery, // The furnace where the crafting takes place
    var amount: Int // The number of silver products to be crafted
) : Pulse() { // Inherits from the Pulse class, indicating it is part of a larger crafting system

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
