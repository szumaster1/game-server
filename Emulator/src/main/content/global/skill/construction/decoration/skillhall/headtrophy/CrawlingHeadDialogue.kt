package content.global.skill.construction.decoration.skillhall.headtrophy

import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.tools.RandomFunction
import core.tools.START_DIALOGUE
import core.tools.END_DIALOGUE
import org.rs.consts.NPCs

/**
 * Crawling hand trophy dialogue.
 */
class CrawlingHeadDialogue : DialogueFile() {

    private val randomConversation = RandomFunction.getRandomElement(arrayOf(0, 1, 2))
    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.CRAWLING_HAND_4226)
        if (!player!!.houseManager.isInHouse(player!!)) {
            when (stage) {
                0 -> playerl(FacialExpression.LAUGH, "Hey, a crawling hand!").also { stage = 10 }
                10 -> npc(FacialExpression.CHILD_FRIENDLY, "yes, what?").also { stage = 20 }
                20 -> player("House owner must be pretty handy to have slayed that!").also { stage = END_DIALOGUE }
            }
        }

        if (randomConversation == 0) {
            when (stage) {
                START_DIALOGUE -> playerl(FacialExpression.LAUGH, "Hey, I was going to make some furniture, do you think you could lend a HAND?").also { stage = 100 }
                100 -> npc(FacialExpression.CHILD_NEUTRAL, "Very funny.").also { stage = END_DIALOGUE }
            }
        }

        if (randomConversation == 1) {
            when (stage) {
                START_DIALOGUE -> playerl(FacialExpression.ASKING, "Hey, hand, do you want to know how I slayed you?").also { stage = 200 }
                200 -> npc(FacialExpression.CHILD_NEUTRAL, "I don't know, how?").also { stage = 300 }
                300 -> player(FacialExpression.LOUDLY_LAUGHING, "Because you're just a hand! You're ARMLESS!").also { stage = END_DIALOGUE }
            }
        }

        if (randomConversation == 2) {
            when (stage) {
                START_DIALOGUE -> playerl(FacialExpression.HALF_ASKING, "Hey, you're just a hand, right? So what do you eat?").also { stage = 400 }
                400 -> npc(FacialExpression.CHILD_NORMAL, "Finger food, of course!").also { stage = END_DIALOGUE }
            }
        }
    }
}