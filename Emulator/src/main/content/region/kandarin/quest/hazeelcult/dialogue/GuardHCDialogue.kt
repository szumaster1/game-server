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
 * Represents the Hazeel Guard dialogue.
 */
@Initializable
class GuardHCDialogue(player: Player? = null) : Dialogue(player) {
    override fun handle(componentID: Int, buttonID: Int): Boolean {
        val questName = "Hazeel Cult"
        val questStage = getQuestStage(player!!, questName)

        when {
            (questStage == 0) -> when (stage) {
                0 -> playerl(FacialExpression.FRIENDLY, "Hello.").also { stage++ }
                1 -> npcl(FacialExpression.FRIENDLY, "Hello. If you've come to admire the Carnillean family home, I must warn you not to cause any trouble. Due to recent criminal activities against such a prominent member of Ardougnian politics,").also { stage++ }
                2 -> npcl(FacialExpression.FRIENDLY, "I have been sent here on special guard duty to ensure the Carnilleans' security.").also { stage++ }
                3 -> npcl(FacialExpression.FRIENDLY, "Anyone caught interfering with the person or possessions of said Carnilleans will be taken to jail so quickly that their feet won't touch the floor!").also { stage++ }
                4 -> playerl(FacialExpression.FRIENDLY, "Well, I wasn't actually planning on doing anything like that...").also { stage++ }
                5 -> npcl(FacialExpression.FRIENDLY, "Glad to hear it. Let's keep it that way, hmmm?").also { stage = END_DIALOGUE }
            }

            (questStage in 1..2) -> when (stage) {
                0 -> playerl(FacialExpression.FRIENDLY, "Hello there.").also { stage++ }
                1 -> npcl(FacialExpression.FRIENDLY, "Hello there. I hear you're after that cult who broke in the other night. It always gladdens me when civilians assist the law like this.").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "I'm just happy to be of help.").also { stage = END_DIALOGUE }
            }

            (questStage == 3) -> when (stage) {
                0 -> {
                    if (getAttribute(player, HazeelCultListener.MAHJARRAT, true) && !getAttribute(player, HazeelCultListener.CARNILLEAN, true)) {
                        playerl(FacialExpression.FRIENDLY, "Hello there.").also { stage++ }
                    } else {
                        sendMessage(player, "They aren't interested in talking to you.").also { stage = END_DIALOGUE }
                    }
                }
                1 -> npcl(FacialExpression.SAD, "Today is a dark day. Those cultists have been back, and this time they've gone further than ever before! Murder! We can't afford to keep letting them get away with this.").also { stage = END_DIALOGUE }
            }

            (questStage == 100) -> when (stage) {
                0 -> {
                    if (getAttribute(player, HazeelCultListener.MAHJARRAT, true) && !getAttribute(player, HazeelCultListener.CARNILLEAN, true)) {
                        playerl(FacialExpression.FRIENDLY, "Hello there.").also { stage = 1 }
                    } else {
                        playerl(FacialExpression.FRIENDLY, "Hello.").also { stage = 6 }
                    }
                }
                // Sided with Hazeel
                1 -> npcl(FacialExpression.NEUTRAL, "Hello, adventurer. It's a shame you never found that cult. We have a horrible suspicion that there's been another burglary.").also { stage++ }
                2 -> playerl(FacialExpression.NEUTRAL, "That's worrying.").also { stage++ }
                3 -> npcl(FacialExpression.NEUTRAL, "Yes, it is. The thing is, we can't even work out what they've taken! It's all very odd.").also { stage++ }
                4 -> playerl(FacialExpression.HALF_ASKING, "Is there anything more I can do to help?").also { stage++ }
                5 -> npcl(FacialExpression.NEUTRAL, "I don't think so. Sir Ceril says you've done enough.").also { stage = END_DIALOGUE }
                // Sided with Carnillean
                6 -> npcl(FacialExpression.ANNOYED, "Well well well... if it isn't our very own local hero. Good to see you managed to clear your name I always had faith in you!").also { stage = END_DIALOGUE }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GUARD_887)
    }
}
