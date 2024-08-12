package content.global.skill.production.crafting.item

import core.api.*
import core.api.consts.Animations
import core.api.consts.Items
import core.game.event.ResourceProducedEvent
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.system.task.Pulse

/**
 * Glass make pulse
 *
 * @property player Represents the player associated with this pulse.
 * @property product Represents the product identifier for this pulse.
 * @property amount Represents the quantity of the product in this pulse.
 * @constructor Initializes a GlassMakePulse instance with the specified player, product, and amount.
 */
class GlassMakePulse(private val player: Player, val product: Int, private var amount: Int) : Pulse() {

    override fun pulse(): Boolean {
        if (amount < 1) return true

        if (!inInventory(player, Items.SODA_ASH_1781) || !inInventory(player, Items.BUCKET_OF_SAND_1783)) {
            return true
        }

        lock(player, 3)
        animate(player, Animations.USE_FURNACE_3243)
        sendMessage(player, "You heat the sand and soda ash in the furnace to make glass.")

        if (removeItem(player, Items.SODA_ASH_1781) && removeItem(player, Items.BUCKET_OF_SAND_1783)) {
            addItem(player, Items.BUCKET_1925)
            addItem(player, Items.MOLTEN_GLASS_1775)
            rewardXP(player, Skills.CRAFTING, 20.0)
            player.dispatch(ResourceProducedEvent(product, amount, player))

        } else return true

        amount--
        delay = 3

        return false
    }
}
