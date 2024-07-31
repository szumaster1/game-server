package content.region.misc.dialogue.zanaris

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class BlaecDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Info: Sandworker, found wandering around the sandpit.
     * Location: 2440,4444
     */

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        when ((1..3).random()) {
            1 -> npc(FacialExpression.FRIENDLY, "Wunnerful weather we're having today!").also { stage = END_DIALOGUE }
            2 -> npc(FacialExpression.FRIENDLY, "Greetin's " + player.name + ", fine day today!").also { stage = END_DIALOGUE }
            3 -> npcl(FacialExpression.ANNOYED, "Please leave me alone, I'm busy trapping the pygmy shrews.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BLAEC_3115)
    }

}
