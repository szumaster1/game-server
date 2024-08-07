package content.region.kandarin.quest.murdermystery.dialogue

import core.api.consts.NPCs
import core.api.getQuestStage
import core.api.isQuestComplete
import core.api.sendMessage
import core.api.setQuestStage
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Carol dialogue.
 */
@Initializable
class CarolDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if (!isQuestComplete(player, "Murder Mystery")) {
            playerl(FacialExpression.FRIENDLY, "I'm here to help the guards with their investigation.").also { stage++ }
        } else {
            sendMessage(player!!, "She is ignoring you.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        val questStage = getQuestStage(player!!, "Murder Mystery")
        when (questStage) {
            in 1..3 -> when (stage) {
                0 -> npcl(FacialExpression.ANNOYED, "Well, ask what you want to know then.").also { stage = 2 }
                2 -> if (getQuestStage(player, "Murder Mystery") == 3) {
                    options("Who do you think is responsible?", "Where were you when the murder happened?", "Do you recognise this thread?", "Why'd you buy poison the other day?").also { stage = 3 }
                } else {
                    options("Who do you think is responsible?", "Where were you when the murder happened?", "Do you recognise this thread?").also { stage = 3 }
                }
                3 -> if (getQuestStage(player, "Murder Mystery") == 3) {
                    when (buttonId) {
                        1 -> playerl(FacialExpression.SUSPICIOUS, "Who do you think is responsible?").also { stage = 4 }
                        2 -> playerl(FacialExpression.SUSPICIOUS, "Where were you when the murder happened?").also { stage = 5 }
                        3 -> playerl(FacialExpression.SUSPICIOUS, "Do you recognise this thread?").also { stage = 6 }
                    }
                } else {
                    when (buttonId) {
                        1 -> playerl(FacialExpression.SUSPICIOUS, "Who do you think is responsible?").also { stage = 4 }
                        2 -> playerl(FacialExpression.SUSPICIOUS, "Where were you when the murder happened?").also { stage = 5 }
                        3 -> playerl(FacialExpression.SUSPICIOUS, "Do you recognise this thread?").also { stage = 6 }
                        4 -> playerl(FacialExpression.SUSPICIOUS, "Why'd you buy poison the other day?").also { stage = 7 }
                    }
                }
                4 -> npcl(FacialExpression.NEUTRAL, "I don't know. I think it's very convenient that you have arrived here so soon after it happened. Maybe it was you.").also { stage = END_DIALOGUE }
                5 -> npcl(FacialExpression.NEUTRAL, "Why? Are you accusing me of something? You seem to have a very high opinion of yourself. I was in my room if you must know, alone.").also { stage = END_DIALOGUE }
                6 -> npcl(FacialExpression.ANNOYED, "It's some red thread... it kind of looks like the Same material as my trousers. But obviously it's not.").also { stage = END_DIALOGUE }
                7 -> npcl(FacialExpression.ANNOYED, "I don't see what on earth it has to do with you, but the drain outside was").also { stage = 8 }
                8 -> npcl(FacialExpression.ANNOYED, "blocked, and as nobody else here has the intelligence to even unblock a simple drain I felt I had to do it myself.").also { stage++ }
                9 -> {
                    end()
                    setQuestStage(player!!, "Murder Mystery", 4)
                }
            }

            100 -> when (stage) {
                0 -> npcl(FacialExpression.NEUTRAL, "Apparently you aren't as stupid as you look.").also { stage = END_DIALOGUE }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.CAROL_816)
    }
}
