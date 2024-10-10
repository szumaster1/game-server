package content.global.skill.summoning.dialogue

import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE
import org.rs.consts.NPCs

class PikkupstixDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.PIKKUPSTIX_6970)
        when(stage) {
            0 -> npc(FacialExpression.NEUTRAL, "Good choice. Here you go, you can now store spells on", "it.").also { stage++ }
            1 -> player(FacialExpression.CALM_TALK, "Excellent. Thank you!").also { stage = END_DIALOGUE }
        }
    }
}