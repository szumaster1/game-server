package content.region.kandarin.ardougne.plaguecity.quest.elena.dialogue

import core.api.*
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE
import org.rs.consts.NPCs
import org.rs.consts.QuestName

/**
 * Represents the Mourner plague city dialogue (Plague City quest).
 */
class MournerWestDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.MOURNER_3216)
        when (getQuestStage(player!!, QuestName.PLAGUE_CITY)) {
            in 0..5 -> when (stage) {
                1 -> npcl(FacialExpression.NEUTRAL, "What are you up to with old man Edmond?").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "Nothing, we've just been chatting.").also { stage++ }
                3 -> npcl(FacialExpression.NEUTRAL, "What about his daughter?").also { stage++ }
                4 -> playerl(FacialExpression.FRIENDLY, "you know about that then?").also { stage++ }
                5 -> npcl(FacialExpression.NEUTRAL, "We know about everything that goes on in Ardougne. We have to if we are to contain the plague.").also { stage++ }
                6 -> playerl(FacialExpression.FRIENDLY, "Have you see his daughter recently?").also { stage++ }
                7 -> npcl(FacialExpression.NEUTRAL, "I imagine she's caught the plague. Either way she won't be allowed out of West Ardougne, the risk is too great.").also { stage = END_DIALOGUE }
            }

            6 -> when (stage) {
                0 -> playerl(FacialExpression.FRIENDLY, "Hello there.").also { stage++ }
                1 -> npcl(FacialExpression.NEUTRAL, "Been digging have we?").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "What do you mean?").also { stage++ }
                3 -> npcl(FacialExpression.NEUTRAL, "Your hands are covered in mud.").also { stage++ }
                4 -> playerl(FacialExpression.FRIENDLY, "Oh that...").also { stage++ }
                5 -> npcl(FacialExpression.NEUTRAL, "Funny, you don't look like the gardening type.").also { stage++ }
                6 -> playerl(FacialExpression.FRIENDLY, "Oh no, I love gardening! It's my favorite pastime.").also { stage = END_DIALOGUE }
            }

            7 -> when (stage) {
                0 -> playerl(FacialExpression.FRIENDLY, "Hello there.").also { stage++ }
                1 -> npcl(FacialExpression.NEUTRAL, "What are you up to?").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "What do you mean?").also { stage++ }
                3 -> npcl(FacialExpression.NEUTRAL, "You and that Edmond fella, you're looking very suspicious.").also { stage++ }
                4 -> playerl(FacialExpression.FRIENDLY, "We're just gardening. Have you heard any news about West Ardougne?").also { stage++ }
                5 -> npcl(FacialExpression.NEUTRAL, "Just the usual, everyone's sick or dying. I'm furious at King Tyras for bringing this plague to our lands.").also { stage = END_DIALOGUE }
            }

            8 -> when (stage) {
                0 -> playerl(FacialExpression.FRIENDLY, "Hello there.").also { stage++ }
                1 -> npcl(FacialExpression.NEUTRAL, "Do you have a problem traveller?").also { stage++ }
                2 -> playerl(FacialExpression.NEUTRAL, "No, I just wondered why you're wearing that outfit... Is it fancy dress?").also { stage++ }
                3 -> npcl(FacialExpression.NEUTRAL, "No! It's for protection.").also { stage++ }
                4 -> playerl(FacialExpression.NEUTRAL, "Protection from what?").also { stage++ }
                5 -> npcl(FacialExpression.FRIENDLY, "The plague of course...").also { stage = END_DIALOGUE }
            }

            in 9..16 -> when (stage) {
                0 -> playerl(FacialExpression.FRIENDLY, "Hello there.").also { stage++ }
                1 -> npcl(FacialExpression.NEUTRAL, "Can I help you?").also { stage++ }
                2 -> playerl(FacialExpression.NEUTRAL, "What are you doing?").also { stage++ }
                3 -> npcl(FacialExpression.NEUTRAL, "I'm guarding the border to West Ardougne. No-one except we mourners can pass through.").also { stage++ }
                4 -> playerl(FacialExpression.NEUTRAL, "Why?").also { stage++ }
                5 -> npcl(FacialExpression.FRIENDLY, "The plague of course. We can't risk cross contamination.").also { stage++ }
                6 -> playerl(FacialExpression.FRIENDLY, "Ok then, see you around.").also { stage++ }
                7 -> npcl(FacialExpression.FRIENDLY, "Maybe...").also { stage = END_DIALOGUE }
            }

            in 17..100 -> when (stage) {
                0 -> playerl(FacialExpression.FRIENDLY, "Hello there.").also { stage++ }
                1 -> npcl(FacialExpression.FRIENDLY, "I'd stand away from there. That black cross means that house has been touched by the plague.").also { stage = END_DIALOGUE }
            }
        }
    }
}
