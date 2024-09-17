package content.minigame.puropuro

import content.minigame.puropuro.dialogue.ElnockInquisitorDialogue
import core.api.addItemOrDrop
import org.rs.consts.Items
import org.rs.consts.NPCs
import core.api.sendNPCDialogue
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * Elnock inquisitor listener.
 */
class ElnockInquisitorListener : InteractionListener {

    companion object {
        private val IMPLING_SCROLL = Items.IMPLING_SCROLL_11273
        private val ELNOCK_INQUISITOR = NPCs.ELNOCK_INQUISITOR_6070
    }

    override fun defineListeners() {
        // Elnock Exchange
        on(NPCs.ELNOCK_INQUISITOR_6070, IntType.NPC, "trade") { player, _ ->
            ElnockInquisitorDialogue.openShop(player)
            return@on true
        }

        // Quick-start option
        on(ELNOCK_INQUISITOR, IntType.NPC, "quick-start") { player, _ ->
            if (!player.savedData.activityData.isElnockSupplies) {
                player.savedData.activityData.isElnockSupplies = true
                addItemOrDrop(player, 10010, 1)
                addItemOrDrop(player, 11262, 1)
                addItemOrDrop(player, 11260, 6)
                sendNPCDialogue(player, ELNOCK_INQUISITOR, "Here you go!")
            } else {
                sendNPCDialogue(player, ELNOCK_INQUISITOR, "Since I have already given you some equipment for free, I'll be willing to sell you some now.")
            }
            return@on true
        }
    }

}