package content.global.skill.production.crafting.glass

import core.api.*
import core.api.consts.Items
import core.api.consts.Sounds
import core.game.event.ResourceProducedEvent
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.system.task.Pulse

class GlassCraftingPulse(
    private val player: Player,
    private val product: GlassProduct,
    private var amount: Int
) : Pulse() {

    override fun pulse(): Boolean {
        if (amount < 1) return true

        if (!inInventory(player, Items.GLASSBLOWING_PIPE_1785) || !inInventory(player, Items.MOLTEN_GLASS_1775)) {
            return true
        }
        lock(player, 3)
        animate(player, 884)
        playAudio(player, Sounds.GLASSBLOWING_2724)
        if (product.producedItemId in intArrayOf(Items.UNPOWERED_ORB_567, Items.OIL_LAMP_4525)) {
            sendMessage(player, "You make an ${product.name.lowercase().replace("_", " ")}.")

        } else sendMessage(player, "You make a ${product.name.lowercase().replace("_", " ")}.")

        if (removeItem(player, Items.MOLTEN_GLASS_1775)) {
            addItem(player, product.producedItemId, product.amount)
            rewardXP(player, Skills.CRAFTING, product.experience)
            player.dispatch(ResourceProducedEvent(product.producedItemId, product.amount, player))

        } else return true

        amount--
        delay = 3

        return false
    }
}