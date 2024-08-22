package content.region.kandarin.quest.murdermystery.dialogue

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
 * Represents the Frank dialogue.
 */
@Initializable
class FrankDialogue(player: Player? = null) : Dialogue(player) {

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
            1 -> when (stage) {
                0 -> npcl(FacialExpression.ANNOYED, "Good for you. Now what do you want? ...And can you spare me any money? I'm a little short...").also { stage = 2 }
                2 -> if (getQuestStage(player, "Murder Mystery") == 3) {
                    options("Who do you think is responsible?", "Where were you when the murder happened?", "Do you recognise this thread?", "Why'd you buy poison the other day?").also { stage = 3 }
                } else {
                    options("Who do you think is responsible?", "Where were you when the murder happened?", "Do you recognise this thread?").also { stage = 3 }
                }

                3 -> if (getQuestStage(player, "Murder Mystery") == 3) {
                    when (buttonID) {
                        1 -> playerl(FacialExpression.SUSPICIOUS, "Who do you think is responsible?").also { stage = 4 }
                        2 -> playerl(FacialExpression.SUSPICIOUS, "Where were you when the murder happened?").also { stage = 5 }
                        3 -> playerl(FacialExpression.SUSPICIOUS, "Do you recognise this thread?").also { stage = 6 }
                    }
                } else {
                    when (buttonID) {
                        1 -> playerl(FacialExpression.SUSPICIOUS, "Who do you think is responsible?").also { stage = 4 }
                        2 -> playerl(FacialExpression.SUSPICIOUS, "Where were you when the murder happened?").also { stage = 5 }
                        3 -> playerl(FacialExpression.SUSPICIOUS, "Do you recognise this thread?").also { stage = 6 }
                        4 -> playerl(FacialExpression.SUSPICIOUS, "Why'd you buy poison the other day?").also { stage = 7 }
                    }
                }
                4 -> npcl(FacialExpression.NEUTRAL, "I don't know. You don't know how long it takes an inheritance to come through do you? I could really use that money pretty soon...").also { stage = END_DIALOGUE }
                5 -> npcl(FacialExpression.NEUTRAL, "I don't know, somewhere around here probably. Could you spare me a few coins? I'll be able to pay you back double tomorrow it's just there's this poker night tonight in town...").also { stage = END_DIALOGUE }
                6 -> npcl(FacialExpression.NEUTRAL, "It looks like thread to me, but I'm not exactly an expert. Is it worth something? Can I have it? Actually, can you spare me a few gold?").also { stage = END_DIALOGUE }
                7 -> npcl(FacialExpression.ANNOYED, "Would you like to buy some? I'm kind of strapped for cash right now, I'll sell it to you cheap, it's hardly been used at all.").also { stage++ }
                8 -> npcl(FacialExpression.ANNOYED, "I just used a bit to clean that family crest outside up a bit. Do you think I can get much money For the family crest, actually? It's cleaned up a bit now.").also { stage++ }
                9 -> {
                    end()
                    setQuestStage(player!!, "Murder Mystery", 4)
                }
            }

            100 -> when (stage) {
                0 -> npcl(FacialExpression.FRIENDLY, "Thank you for all your help in solving the murder.").also { stage = END_DIALOGUE }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.FRANK_819)
    }
}
