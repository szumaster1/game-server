package content.global.skill.production.crafting.item

import content.global.skill.production.crafting.data.Glass
import core.api.*
import org.rs.consts.Animations
import org.rs.consts.Items
import org.rs.consts.Sounds
import core.game.event.ResourceProducedEvent
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.system.task.Pulse

/**
 * Represents a pulse used to craft glass items.
 */
class GlassCraftPulse(private val player: Player, private val product: Glass, private var amount: Int) : Pulse() {

    override fun pulse(): Boolean {
        if (amount < 1) {
            return true
        }

        if (!inInventory(player, Items.GLASSBLOWING_PIPE_1785) || !inInventory(player, Items.MOLTEN_GLASS_1775)) {
            return true
        }

        lock(player, 3)
        animate(player, Animations.GLASS_BLOWING_884)
        playAudio(player, Sounds.GLASSBLOWING_2724)

        if (product.productId in intArrayOf(Items.UNPOWERED_ORB_567, Items.OIL_LAMP_4525)) {
            sendMessage(player, "You make an ${getItemName(product.productId)}.")
        } else {
            sendMessage(player, "You make a ${getItemName(product.productId)}.")
        }

        if (removeItem(player, Items.MOLTEN_GLASS_1775)) {
            addItem(player, product.productId, product.amount)
            rewardXP(player, Skills.CRAFTING, product.experience)
            player.dispatch(ResourceProducedEvent(product.productId, product.amount, player))
        } else {
            return true
        }
        amount--
        delay = 3
        return false
    }
}
