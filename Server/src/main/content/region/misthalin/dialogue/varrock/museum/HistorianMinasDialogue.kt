package content.region.misthalin.dialogue.varrock.museum

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class HistorianMinasDialogue(player: Player? = null) : Dialogue(player) {

    /*
     *  Info: Employee who can be found managing the history
     *  exhibits on the 1st floor, near the staircase.
     *  Location: 3262,3455,1
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        player(FacialExpression.HALF_GUILTY, "Hello").also { stage++ }
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.HALF_GUILTY, "Hello there, welcome to Varrock Museum! Can I help you?").also { stage++ }
            1 -> playerl(FacialExpression.HALF_GUILTY, "Tell me about the Museum.").also { stage++ }
            2 -> npcl(FacialExpression.HALF_GUILTY, "Well, as you can see we have recently expanded a great deal to cope with the influx of finds from the Dig Site.").also { stage++ }
            3 -> npc(FacialExpression.HALF_GUILTY, "Also, of course, to prepare for the new dig we're", "opening soon.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.HISTORIAN_MINAS_5931)
    }

}
