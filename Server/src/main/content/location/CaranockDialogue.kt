package content.location

import cfg.consts.NPCs
import core.api.sendDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Caranock dialogue.
 */
@Initializable
class CaranockDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * G.L.O. Caranock is the Gnome Liaison Officer
     * for the Karamja Shipyard.
     * Location: 2957,3025
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        sendDialogue(player, "Caranock seems too busy to talk.").also { stage = END_DIALOGUE }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GLO_CARANOCK_1427)
    }

}
