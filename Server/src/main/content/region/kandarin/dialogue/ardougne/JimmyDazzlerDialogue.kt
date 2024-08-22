package content.region.kandarin.dialogue.ardougne

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Jimmy Dazzler dialogue.
 */
@Initializable
class JimmyDazzlerDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Jimmy Dazzler is a wealthy exterminator and
     * citizen of East Ardougne featured in the Rat Catchers quest.
     * Location: 2562,3320
     */

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npcl(FacialExpression.SUSPICIOUS, "What's your name and your business here?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "My name is ${player.username}, and I have a couple of questions for you.").also { stage++ }
            1 -> npcl(FacialExpression.NEUTRAL, "No.").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "What do you mean by no?").also { stage++ }
            3 -> npcl(FacialExpression.NEUTRAL, "That just isn't the ticket. I don't know you nor I will not answer any questions. Now get gone from here, you'll attract unwanted attention to me.").also { stage++ }
            4 -> playerl(FacialExpression.ROLLING_EYES, "As if those 'clothes' you're wearing wouldn't do that already.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.JIMMY_DAZZLER_2949)
    }
}
