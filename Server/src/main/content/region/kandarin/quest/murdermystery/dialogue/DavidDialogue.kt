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

@Initializable
class DavidDialogue(player: Player? = null) : Dialogue(player) {

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
        when (getQuestStage(player!!, "Murder Mystery")) {
            in 1..3 -> when (stage) {
                0 -> npcl(FacialExpression.ANNOYED, "And? Make this quick. I have better things to do than be interrogated by halfwits all day.").also { stage = 2 }
                2 -> if (getQuestStage(player, "Murder Mystery") == 3) {
                    options("Who do you think is responsible?", "Where were you when the murder happened?", "Do you recognise this thread?", "Why'd you buy poison the other day?").also { stage++ }
                } else {
                    options("Who do you think is responsible?", "Where were you when the murder happened?", "Do you recognise this thread?").also { stage++ }
                }
                3 -> if (getQuestStage(player, "Murder Mystery") == 3) {
                    when (buttonID) {
                        1 -> playerl(FacialExpression.SUSPICIOUS, "Who do you think is responsible?").also { stage++ }
                        2 -> playerl(FacialExpression.SUSPICIOUS, "Where were you when the murder happened?").also { stage = 5 }

                        3 -> playerl(FacialExpression.SUSPICIOUS, "Do you recognise this thread?").also { stage = 6 }
                    }
                } else {
                    when (buttonID) {
                        1 -> playerl(FacialExpression.SUSPICIOUS, "Who do you think is responsible?").also { stage++ }
                        2 -> playerl(FacialExpression.SUSPICIOUS, "Where were you when the murder happened?").also { stage = 5 }
                        3 -> playerl(FacialExpression.SUSPICIOUS, "Do you recognise this thread?").also { stage = 6 }
                        4 -> playerl(FacialExpression.SUSPICIOUS, "Why'd you buy poison the other day?").also { stage = 9 }
                    }
                }
                4 -> npcl(FacialExpression.NEUTRAL, "I don't really know or care. Frankly, the old man deserved to die. There was a suspicious red headed man who came to the house the other day selling poison now I think about it. Last I saw he was headed towards the tavern in the Seers village.").also { stage = END_DIALOGUE }
                5 -> npcl(FacialExpression.NEUTRAL, "That is none of your business. Are we finished now, or are you just going to stand there irritating me with your idiotic questions all day?").also { stage = END_DIALOGUE }
                6 -> npcl(FacialExpression.NEUTRAL, "No. Can I go yet? Your face irritates me.").also { stage = END_DIALOGUE }
                7 -> playerl(FacialExpression.SUSPICIOUS, "So you didn't hear any sounds of a struggle or any barking from the guard dog next to his study window?").also { stage++ }
                8 -> npcl(FacialExpression.NEUTRAL, "Now you mention it, no. It is odd I didn't hear anything like that. But I do sleep very soundly as I said and wouldn't necessarily have heard it if there was any such noise.").also { stage = END_DIALOGUE }
                9 -> npcl(FacialExpression.NEUTRAL, "There was a nest of spiders upstairs between the two servants' quarters. Obviously I had to kill them before our pathetic servants whined at my father some more.").also { stage++ }
                10 -> npcl(FacialExpression.NEUTRAL, "Honestly, it's like they expect to be treated like royalty! If I had my way I would fire the whole workshy lot of them!").also { stage++ }
                11 -> {
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
        return intArrayOf(NPCs.DAVID_817)
    }
}
