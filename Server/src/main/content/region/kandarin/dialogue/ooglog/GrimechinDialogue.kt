package content.region.kandarin.dialogue.ooglog

import core.api.consts.NPCs
import core.api.sendNPCDialogue
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE

/**
 * Grimechin dialogue.
 */
class GrimechinDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        when (stage) {
            START_DIALOGUE -> playerl(FacialExpression.HALF_ASKING, "So, how do you like what's, um, being done to your head?").also { stage++ }
            1 -> sendNPCDialogue(player!!, NPCs.KRINGK_7063, "Quit talking to goblin, Player. If she moves her head, she mess up work.", FacialExpression.CHILD_NEUTRAL).also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "Oh, sorry! I would never dream of interfering in the creative process.").also { stage = END_DIALOGUE }
        }
    }

}
