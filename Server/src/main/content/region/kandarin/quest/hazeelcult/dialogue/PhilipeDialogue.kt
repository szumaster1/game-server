package content.region.kandarin.quest.hazeelcult.dialogue

import content.region.kandarin.quest.hazeelcult.HazeelCultListeners
import core.api.consts.NPCs
import core.api.getAttribute
import core.api.getQuestStage
import core.api.sendMessage
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class PhilipeDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(componentID: Int, buttonID: Int): Boolean {
        val questName = "Hazeel Cult"
        val questStage = getQuestStage(player!!, questName)

        when {
            (questStage == 0) -> when (stage) {
                0 -> npcl(FacialExpression.FRIENDLY, "Hello, how are you today?").also { stage++ }
                1 -> playerl(FacialExpression.FRIENDLY, "Good thank you. And yourself?").also { stage++ }
                2 -> npcl(FacialExpression.FRIENDLY, "Fine and dandy.").also { stage = END_DIALOGUE }
            }

            (questStage in 1..2) -> when (stage) {
                0 -> npcl(FacialExpression.FRIENDLY, "Mommy said you're here to kill all the nasty people that keep breaking in.").also { stage++ }
                1 -> playerl(FacialExpression.FRIENDLY, "Something like that.").also { stage++ }
                2 -> npcl(FacialExpression.FRIENDLY, "Can I watch?").also { stage++ }
                3 -> playerl(FacialExpression.FRIENDLY, "No!").also { stage = END_DIALOGUE }
            }

            (questStage == 3) -> when (stage) {
                0 -> {
                    if (getAttribute(player, HazeelCultListeners.MAHJARRAT, true) && !getAttribute(player, HazeelCultListeners.CARNILLEAN, true)) {
                        playerl(FacialExpression.FRIENDLY, "Hello there.").also { stage++ }
                    } else {
                        sendMessage(player, "They aren't interested in talking to you.").also { stage = END_DIALOGUE }
                    }
                }
                1 -> npcl(FacialExpression.SAD, "Someone killed Scruffy. I liked Scruffy. He never told me off.").also { stage++ }
                2 -> playerl(FacialExpression.HALF_GUILTY, "Yeah... it's a real shame.").also { stage++ }
                3 -> npcl(FacialExpression.SAD, "I want my mommy.").also { stage = END_DIALOGUE }
            }

            (questStage == 100) -> when (stage) {
                0 -> {
                    if (getAttribute(player, HazeelCultListeners.MAHJARRAT, true) && !getAttribute(player, HazeelCultListeners.CARNILLEAN, true)) {
                        playerl(FacialExpression.FRIENDLY, "Hello there.").also { stage = 1 }
                    } else {
                        playerl(FacialExpression.FRIENDLY, "Hello youngster.").also { stage = 4 }
                    }
                }
                // Sided with Hazeel
                1 -> npcl(FacialExpression.NEUTRAL, "What have you brought me? I want some more toys!").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "I'm afraid I don't have any toys.").also { stage++ }
                3 -> npcl(FacialExpression.ANNOYED, "Toys! I want toys!").also { stage = END_DIALOGUE }
                // Sided with Carnillean
                4 -> npcl(FacialExpression.FRIENDLY, "Daddy says you don't like Mr. Jones. Mr. Jones is nice. He brings me toys and sweets.").also { stage++ }
                5 -> playerl(FacialExpression.FRIENDLY, "Jones is a bad man, Philipe.").also { stage++ }
                6 -> npcl(FacialExpression.FRIENDLY, "You're a bad " + (if (player.isMale) "man" else "lady") + " I don't like you!").also { stage++ }
                7 -> playerl(FacialExpression.FRIENDLY, "I'll try and console myself about that disappointment somehow.").also { stage = END_DIALOGUE }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.PHILIPE_CARNILLEAN_888)
    }
}
