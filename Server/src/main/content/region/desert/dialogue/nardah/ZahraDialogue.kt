package content.region.desert.dialogue.nardah

import core.api.consts.NPCs
import core.api.isQuestComplete
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class ZahraDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if(!isQuestComplete(player, "Spirits of the Elid")){
            player(FacialExpression.FRIENDLY, "Good day to you.")
        } else {
            playerl(FacialExpression.FRIENDLY, "How's life now the curse has been lifted?").also { stage = 5 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.FRIENDLY, "Hello.").also { stage++ }
            1 -> player("You don't look too happy.").also { stage++ }
            2 -> npc("True. We've not fallen on the best of times here.").also { stage++ }
            3 -> player("Any way that I can help?").also { stage++ }
            4 -> npcl(FacialExpression.HALF_GUILTY, "Possibly. I'd go talk to Awusah the Mayor of Nardah. He's in the big house on the east side of the town square.").also { stage = END_DIALOGUE }
            5 -> npcl(FacialExpression.HALF_GUILTY, "Much better thanks to you. We're all very impressed.").also { stage = END_DIALOGUE }

        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ZAHRA_3036)
    }
}
