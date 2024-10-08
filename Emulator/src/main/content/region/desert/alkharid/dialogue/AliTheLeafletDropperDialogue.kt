package content.region.desert.alkharid.dialogue

import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.tools.END_DIALOGUE

/**
 * Represents the Ali the leaflet dropper dialogue.
 */
class AliTheLeafletDropperDialogue(val it: Int) : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        when (it) {
            1 -> when (stage) {
                0 -> npcl(FacialExpression.CHILD_NORMAL, "Here! Take one and let me get back to work.").also { stage++ }
                1 -> npcl(FacialExpression.CHILD_THINKING, "I still have hundreds of these flyers to hand out. I wonder if Ali would notice if I quietly dumped them somewhere?").also { stage = END_DIALOGUE }
            }
            2 -> npcl(FacialExpression.CHILD_SUSPICIOUS, "Are you trying to be funny or has age turned your brain to mush? You already have a flyer!").also { stage = END_DIALOGUE }
        }
    }
}
