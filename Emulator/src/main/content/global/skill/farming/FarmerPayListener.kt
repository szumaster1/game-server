package content.global.skill.farming

import core.api.openDialogue
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.Node
import core.game.node.entity.player.Player

/**
 * Represents the Farmer payment interaction listener.
 */
class FarmerPayListener : InteractionListener {

    override fun defineListeners() {
        on(IntType.NPC, "pay", "pay (north)", "pay (north-west)") { player, node ->
            return@on attemptPay(player, node, 0)
        }

        on(IntType.NPC, "pay (south)", "pay (south-east)") { player, node ->
            return@on attemptPay(player, node, 1)
        }
    }

    /**
     * Attempt pay
     *
     * @param player The player attempting to make a payment
     * @param node The node representing the payment destination
     * @param index The index of the payment attempt
     * @return Returns true if the payment was successful, false otherwise
     */
    fun attemptPay(player: Player, node: Node, index: Int): Boolean {
        val farmer = Farmers.forId(node.id) ?: return false
        val patch = farmer.patches[index].getPatchFor(player)

        openDialogue(player, FarmerPayOptionDialogue(patch, true), node.asNpc())
        return true
    }
}