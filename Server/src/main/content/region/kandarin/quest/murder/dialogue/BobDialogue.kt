package content.region.kandarin.quest.murder.dialogue

import cfg.consts.NPCs
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
 * Represents the Bob dialogue.
 */
@Initializable
class BobDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if (!isQuestComplete(player, "Murder Mystery")) {
            playerl(FacialExpression.FRIENDLY, "I'm here to help the guards with their investigation.").also { stage++ }
        } else {
            sendMessage(player!!, "He is ignoring you.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun handle(componentID: Int, buttonID: Int): Boolean {
        val questStage = getQuestStage(player!!, "Murder Mystery")
        when (questStage) {
            in 1..3 -> when (stage) {
                0 -> npcl(FacialExpression.HALF_GUILTY, "I suppose I had better talk to you then.").also { stage = 2 }
                2 -> if (getQuestStage(player, "Murder Mystery") == 3) {
                    options("Who do you think is responsible?", "Where were you when the murder happened?", "Do you recognise this thread?", "Why'd you buy poison the other day?").also { stage++ }
                } else {
                    options("Who do you think is responsible?", "Where were you when the murder happened?", "Do you recognise this thread?").also { stage++ }
                }

                3 -> if (getQuestStage(player, "Murder Mystery") == 3) {
                    when (buttonID) {
                        1 -> playerl(FacialExpression.SUSPICIOUS, "Who do you think is responsible?").also { stage++ }
                        2 -> playerl(FacialExpression.SUSPICIOUS, "Where were you when the murder happened?").also { stage = 5 }
                        3 -> playerl(FacialExpression.SUSPICIOUS, "Do you recognise this thread?").also { stage = 8 }
                    }
                } else {
                    when (buttonID) {
                        1 -> playerl(FacialExpression.SUSPICIOUS, "Who do you think is responsible?").also { stage++ }
                        2 -> playerl(FacialExpression.SUSPICIOUS, "Where were you when the murder happened?").also { stage = 5 }
                        3 -> playerl(FacialExpression.SUSPICIOUS, "Do you recognise this thread?").also { stage = 8 }
                        4 -> playerl(FacialExpression.SUSPICIOUS, "Why'd you buy poison the other day?").also { stage = 10 }
                    }
                }

                4 -> npcl(FacialExpression.ANGRY, "I don't really care as long as no one thinks it's me. Maybe it was that strange poison seller who headed towards the seers village.").also { stage = END_DIALOGUE }

                5 -> npcl(FacialExpression.NEUTRAL, "I was walking by myself in the garden.").also { stage = 6 }
                6 -> playerl(FacialExpression.SUSPICIOUS, "And can anyone vouch for that?").also { stage = 7 }
                7 -> npcl(FacialExpression.ANNOYED, "No. But I was.").also { stage = END_DIALOGUE }
                8 -> npcl(FacialExpression.ANNOYED, "It's some red thread. I suppose you think that's some kind of clue? It looks like the material my trousers are made of.").also { stage = END_DIALOGUE }
                9 -> npcl(FacialExpression.HALF_GUILTY, "What's it to you anyway?").also { stage = 10 }
                10 -> npcl(FacialExpression.ANGRY, "If you absolutely must know, we had a problem with the beehive in the garden, and as all of our servants are so pathetically useless, I decided I would deal with it myself. So I did.").also { stage++ }
                11 -> {
                    end()
                    setQuestStage(player!!, "Murder Mystery", 4)
                }
            }

            100 -> when (stage) {
                0 -> npcl(FacialExpression.HALF_GUILTY, "Apparently you aren't as stupid as you look.").also { stage = END_DIALOGUE }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BOB_815)
    }
}
