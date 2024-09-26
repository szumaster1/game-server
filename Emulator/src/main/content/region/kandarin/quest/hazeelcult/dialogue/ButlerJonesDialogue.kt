package content.region.kandarin.quest.hazeelcult.dialogue

import content.region.kandarin.quest.hazeelcult.handlers.HazeelCultListener
import org.rs.consts.NPCs
import core.api.getAttribute
import core.api.getQuestStage
import core.api.sendMessage
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Butler Jones dialogue.
 */
@Initializable
class ButlerJonesDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(componentID: Int, buttonID: Int): Boolean {
        val questName = "Hazeel Cult"
        val questStage = getQuestStage(player!!, questName)

        when {
            (questStage in 0..2) -> when (stage) {
                0 -> playerl(FacialExpression.FRIENDLY, "Hello there.").also { stage++ }
                1 -> npcl(FacialExpression.FRIENDLY, "Hello. How are you today?").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "Good thank you, and yourself?").also { stage++ }
                3 -> npcl(FacialExpression.FRIENDLY, "Very well, thank you.").also { stage = END_DIALOGUE }
            }

            (questStage == 3) -> when (stage) {
                0 -> {
                    if (getAttribute(player, HazeelCultListener.MAHJARRAT, true) && !getAttribute(player, HazeelCultListener.CARNILLEAN, true)) {
                        playerl(FacialExpression.FRIENDLY, "Hello there.").also { stage++ }
                    } else {
                        sendMessage(player, "They aren't interested in talking to you.").also { stage = END_DIALOGUE }
                    }
                }
                1 -> npcl(FacialExpression.NEUTRAL, "Hello, adventurer. Such a terrible shame about Scruffy. I wonder if the family will ever fully recover.").also { stage++ }

                2 -> npcl(FacialExpression.FRIENDLY, "Anyway, I hear your quest is going well.").also { stage++ }
                3 -> playerl(FacialExpression.FRIENDLY, "Really?").also { stage++ }
                4 -> npcl(FacialExpression.ANNOYED, "Oh yes. Do keep up the good work.").also { stage = END_DIALOGUE }
            }

            (questStage == 100) -> when (stage) {
                0 -> {
                    if (getAttribute(player, HazeelCultListener.MAHJARRAT, true) && !getAttribute(player, HazeelCultListener.CARNILLEAN, true)) {
                        playerl(FacialExpression.FRIENDLY, "Hello stranger.").also { stage = 1 }
                    } else {
                        playerl(FacialExpression.FRIENDLY, "Hello there.").also { stage = 6 }
                    }
                }
                // Sided with Hazeel
                1 -> npcl(FacialExpression.NEUTRAL, "It's an honour to be in your presence again, adventurer. I hope things are well?").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "Not bad, thanks. Yourself?").also { stage++ }
                3 -> npcl(FacialExpression.ANNOYED, "Unfortunately, I am still forced to deal with this insufferable family. Many generations have passed, but they are still the enemy. As such, they must be kept a close eye on.").also { stage++ }
                4 -> npcl(FacialExpression.ANNOYED, "Still, I have no doubt that the time will soon come for me to leave this place. Our lord will certainly have need of me elsewhere once his current work is complete.").also { stage++ }
                5 -> playerl(FacialExpression.FRIENDLY, "I see. Well good luck with it all.").also { stage = END_DIALOGUE }
                // Sided with Carnillean
                6 -> npcl(FacialExpression.ANNOYED, "Why hello there.").also { stage++ }
                7 -> playerl(FacialExpression.FRIENDLY, "I take it you're the new butler...?").also { stage++ }
                8 -> npcl(FacialExpression.ANNOYED, "That's right. I hear that they had some problems with the last one.").also { stage++ }
                9 -> playerl(FacialExpression.FRIENDLY, "Yes, you could say that...").also { stage = END_DIALOGUE }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BUTLER_JONES_890)
    }
}
