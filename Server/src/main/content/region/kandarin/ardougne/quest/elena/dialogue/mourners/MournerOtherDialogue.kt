package content.region.kandarin.ardougne.quest.elena.dialogue.mourners

import cfg.consts.NPCs
import core.api.getQuestStage
import core.api.openDialogue
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE

/**
 * Represents the Mourner other dialogue.
 */
class MournerOtherDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.MOURNER_717)
        when (getQuestStage(player!!, "Plague City")) {
            in 0..8 -> when (stage) {
                0 -> playerl(FacialExpression.FRIENDLY, "Hello there.").also { stage++ }
                1 -> npcl(FacialExpression.NEUTRAL, "Do you have a problem traveller?").also { stage++ }
                2 -> playerl(FacialExpression.NEUTRAL, "No, I just wondered why you're wearing that outfit... Is it fancy dress?").also { stage++ }

                3 -> npcl(FacialExpression.NEUTRAL, "No! It's for protection.").also { stage++ }
                4 -> playerl(FacialExpression.NEUTRAL, "Protection from what?").also { stage++ }
                5 -> npcl(FacialExpression.FRIENDLY, "The plague of course...").also { stage = END_DIALOGUE }
            }
            in 9..15 -> when (stage) {
                0 -> playerl(FacialExpression.FRIENDLY, "Hello there.").also { stage++ }
                1 -> npcl(FacialExpression.NEUTRAL, "Can I help you?").also { stage++ }
                2 -> playerl(FacialExpression.NEUTRAL, "What are you doing?").also { stage++ }
                3 -> npcl(FacialExpression.NEUTRAL, "I'm guarding the border to West Ardougne. No-one except we mourners can pass through.").also { stage++ }
                4 -> playerl(FacialExpression.NEUTRAL, "Why?").also { stage++ }
                5 -> npcl(FacialExpression.FRIENDLY, "The plague of course. We can't risk cross contamination.").also { stage++ }
                6 -> playerl(FacialExpression.FRIENDLY, "Ok then, see you around.").also { stage++ }
                7 -> npcl(FacialExpression.FRIENDLY, "Maybe...").also { stage = END_DIALOGUE }
            }
            16 -> when (stage) {
                0 -> {
                    end()
                    openDialogue(player!!, MournerPlagueCityDialogue())
                }
            }
            in 17..100 -> when (stage) {
                0 -> playerl(FacialExpression.FRIENDLY, "Hello there.").also { stage++ }
                1 -> npcl(FacialExpression.FRIENDLY, "I'd stand away from there. That black cross means that house has been touched by the plague.").also { stage = END_DIALOGUE }
            }
        }
    }
}
