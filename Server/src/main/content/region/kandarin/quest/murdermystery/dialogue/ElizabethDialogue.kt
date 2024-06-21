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
import core.utilities.END_DIALOGUE

@Initializable
class ElizabethDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if (!isQuestComplete(player, "Murder Mystery")) {
            playerl(FacialExpression.FRIENDLY, "I'm here to help the guards with their investigation.").also { stage++ }
        } else {
            sendMessage(player!!, "She is ignoring you.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun handle(componentID: Int, buttonID: Int): Boolean {
        val questStage = getQuestStage(player!!, "Murder Mystery")
        when (questStage) {
            in 1..3 -> when (stage) {
                0 -> npcl(FacialExpression.NEUTRAL, "What's so important you need to bother me with then?").also { stage = 2 }
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
                        4 -> playerl(FacialExpression.SUSPICIOUS, "Why'd you buy poison the other day?").also { stage = 9 }
                    }
                }
                4 -> npcl(FacialExpression.NEUTRAL, "Could have been anyone. The old man was an idiot. He's been asking for it for years.").also { stage = END_DIALOGUE }
                5 -> npcl(FacialExpression.NEUTRAL, "I was out.").also { stage++ }
                6 -> playerl(FacialExpression.ASKING, "Care to be any more specific?").also { stage++ }
                7 -> npcl(FacialExpression.NEUTRAL, "Not really. I don't have to justify myself to the likes of you, you know. I know the King personally you know. Now are we finished here?").also { stage = END_DIALOGUE }
                8 -> npcl(FacialExpression.THINKING, "It's some thread. You're not very good at this whole investigation thing are you?").also { stage = END_DIALOGUE }
                9 -> npcl(FacialExpression.NEUTRAL, "There was a nest of mosquitos under the fountain in the garden, which I killed with poison the other day. You can see for yourself if you're capable of managing that, which I somehow doubt.").also { stage++ }
                10 -> playerl(FacialExpression.NEUTRAL, "I hate mosquitos!").also { stage++ }
                11 -> npcl(FacialExpression.NEUTRAL, "Doesn't everyone?").also { stage++ }
                12 -> {
                    end()
                    setQuestStage(player!!, "Murder Mystery", 4)
                }
            }
            
            100 -> when (stage) {
                0 -> npcl(FacialExpression.FRIENDLY, "Apparently you aren't as stupid as you look.").also { stage = END_DIALOGUE }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ELIZABETH_818)
    }
}
