package content.region.kandarin.quest.murdermystery.dialogue

import core.api.consts.NPCs
import core.api.getQuestStage
import core.api.isQuestComplete
import core.api.sendMessage
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Stanford dialogue.
 */
@Initializable
class StanfordDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if (isQuestComplete(player, "Murder Mystery")) {
            sendMessage(player!!, "He is ignoring you.").also { stage = END_DIALOGUE }
        } else {
            playerl(FacialExpression.FRIENDLY, "I'm here to help the guards with their investigation.").also { stage++ }
        }
        return true
    }

    override fun handle(componentID: Int, buttonID: Int): Boolean {
        val questStage = getQuestStage(player!!, "Murder Mystery")
        when (questStage) {
            1 -> when (stage) {
                0 -> playerl(FacialExpression.SUSPICIOUS, "I'm here to help the guards with their investigation.").also { stage++ }
                1 -> npcl(FacialExpression.FRIENDLY, "How can I help?").also { stage++ }
                2 -> if (getQuestStage(player, "Murder Mystery") == 3) {
                    options("Who do you think is responsible?", "Where were you when the murder happened?", "Did you hear any suspicious noises at all?", "Why'd you buy poison the other day?").also { stage++ }
                } else {
                    options("Who do you think is responsible?", "Where were you when the murder happened?", "Did you hear any suspicious noises at all?").also { stage++ }
                }

                3 -> if (getQuestStage(player, "Murder Mystery") == 3) {
                    when (buttonID) {
                        1 -> playerl(FacialExpression.SUSPICIOUS, "Who do you think is responsible?").also { stage = 4 }
                        2 -> playerl(FacialExpression.SUSPICIOUS, "Where were you when the murder happened?").also { stage = 5 }
                        3 -> playerl(FacialExpression.SUSPICIOUS, "Did you hear any suspicious noises at all?").also { stage = 6 }
                    }
                } else {
                    when (buttonID) {
                        1 -> playerl(FacialExpression.SUSPICIOUS, "Who do you think is responsible?").also { stage = 4 }
                        2 -> playerl(FacialExpression.SUSPICIOUS, "Where were you when the murder happened?").also { stage = 5 }
                        3 -> playerl(FacialExpression.SUSPICIOUS, "Did you hear any suspicious noises at all?").also { stage = 6 }
                        4 -> playerl(FacialExpression.SUSPICIOUS, "Why'd you buy poison the other day?").also { stage = 11 }
                    }
                }
                4 -> npcl(FacialExpression.NEUTRAL, "It was Anna. She is seriously unbalanced. She trashed the garden once then tried to blame it on me! I bet it was her. It's just the kind of thing she'd do! She really hates me and was arguing with Lord Sinclair about trashing the garden a few days ago.").also { stage = END_DIALOGUE }
                5 -> npcl(FacialExpression.NEUTRAL, "Right here, by my little shed. It's very cosy to sit and think in.").also { stage = END_DIALOGUE }
                6 -> npcl(FacialExpression.NEUTRAL, "Not that I remember.").also { stage++ }
                7 -> playerl(FacialExpression.ASKING, "So no sounds of a struggle between Lord Sinclair and an intruder?").also { stage++ }
                8 -> npcl(FacialExpression.NEUTRAL, "Not to the best of my recollection.").also { stage++ }
                9 -> playerl(FacialExpression.ASKING, "How about the guard dog barking?").also { stage++ }
                10 -> npcl(FacialExpression.NEUTRAL, "Not that I can recall.").also { stage = END_DIALOGUE }
                11 -> npcl(FacialExpression.NEUTRAL, "Well, Bob mentioned to me the other day he wanted to get rid of the bees in that hive over there. I think I saw him buying poison").also { stage++ }
                12 -> npcl(FacialExpression.NEUTRAL, "from that poison salesman the other day. I assume it was to sort out those bees. You'd really have to ask him though.").also { stage = END_DIALOGUE }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.STANFORD_811)
    }
}
