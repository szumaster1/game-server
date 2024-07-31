package content.region.desert.dialogue.sophanem

import core.api.consts.NPCs
import core.api.sendDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class KlenterDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Klenter is a ghost who is located in Sophanem,
     * just south of the Pyramid Plunder pyramid.
     */

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.OLD_NORMAL, "OOOOoOOOoOO")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> sendDialogue(player,"You cannot understand the ghost.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.KLENTER_2014)
    }

}
