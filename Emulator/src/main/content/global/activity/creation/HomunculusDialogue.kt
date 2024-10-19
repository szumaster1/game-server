package content.global.activity.creation

import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE
import org.rs.consts.NPCs

class HomunculusDialogue : DialogueFile() {

    /*
     * Homunculus post-quest dialogue.
     */

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.HOMUNCULUS_5581)
        when (stage) {
            0 -> playerl(FacialExpression.HALF_ASKING, "Hi there, you mentioned something about creating monsters...?").also { stage++ }
            1 -> npcl(FacialExpression.FRIENDLY, "Good! I gain know from alchemists and builders. Me make beings.").also { stage++ }
            2 -> playerl(FacialExpression.THINKING, "Interesting. Tell me if I'm right.").also { stage++ }
            3 -> playerl(FacialExpression.THINKING, "By the alchemists and builders creating you, you have inherited their combined knowledge in much the same way that a child might inherit the looks of their parents.").also { stage++ }
            4 -> npcl(FacialExpression.FRIENDLY, "Yes, you right!").also { stage++ }
            5 -> playerl(FacialExpression.HALF_ASKING, "So what do you need me to do?").also { stage++ }
            6 -> npcl(FacialExpression.NEUTRAL, "Inspect symbol of life altars around dungeon. You see item give. Use item on altar. Activate altar to create, you fight.").also { stage++ }
            7 -> playerl(FacialExpression.NOD_YES, "Okay. Sounds like a challenge.").also { stage = END_DIALOGUE }
        }
    }
}