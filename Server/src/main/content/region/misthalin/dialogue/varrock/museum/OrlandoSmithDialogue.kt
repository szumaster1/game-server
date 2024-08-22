package content.region.misthalin.dialogue.varrock.museum

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Orlando smith dialogue.
 */
@Initializable
class OrlandoSmithDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Info: He runs the Natural history quiz there. [But not now]
     * Location: 1759,4955
     */

    override fun open(vararg args: Any): Boolean {
        npcl(FacialExpression.HALF_GUILTY, "G'day there, mate.")
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "Good day. Are you alright? You look a little lost.").also { stage++ }
            1 -> npcl(FacialExpression.HALF_GUILTY, "Well, mate, to tell you the truth, I think I've come a gutser with these displays.").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "Come a what?").also { stage++ }
            3 -> npcl(FacialExpression.HALF_GUILTY, "Gutser and no mistake. Me boss asked me to put together a quiz for the visitors.").also { stage++ }
            4 -> npcl(FacialExpression.HALF_GUILTY, "But to be deadset with you, I wasn't paying much attention to me boss over there and I've done a bit of a rush job.").also { stage++ }
            5 -> playerl(FacialExpression.FRIENDLY, "You mean the natural historian?").also { stage++ }
            6 -> npcl(FacialExpression.HALF_GUILTY, "Yep, that's the bloke. Say, mate, you do me a favour?").also { stage++ }
            7 -> playerl(FacialExpression.FRIENDLY, "Perhaps. What do you need?").also { stage++ }
            8 -> npcl(FacialExpression.HALF_GUILTY, "Well, you look like a pretty smart cobber. Could you take a look at the display plaques and give 'em a runthrough?").also { stage++ }
            9 -> options("Sure thing.", "No thanks.").also { stage++ }
            10 -> when (buttonId) {
                1 -> player("Sure thing.").also { stage = 11 }
                2 -> player("No thanks I'm too busy.").also { stage = 13 }
            }
            11 -> npcl(FacialExpression.HALF_GUILTY, "Bonza, mate! I reckon three questions per case should be bang to rights.").also { stage++ }
            12 -> npcl(FacialExpression.HALF_GUILTY, "Take a gander at each case and I'll look over your shoulder to give some advice.").also { stage = END_DIALOGUE }
            13 -> npcl(FacialExpression.HALF_GUILTY, "Fair dinkum mate. I'm sure I'll get someone else to help me.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ORLANDO_SMITH_5965)
    }

}
