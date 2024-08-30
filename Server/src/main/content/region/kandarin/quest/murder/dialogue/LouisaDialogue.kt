package content.region.kandarin.quest.murder.dialogue

import cfg.consts.NPCs
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
 * Represents the Louisa dialogue.
 */
@Initializable
class LouisaDialogue(player: Player? = null) : Dialogue(player) {

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
        when (getQuestStage(player!!, "Murder Mystery")) {
            in 1..3 -> when (stage) {
                0 -> npcl(FacialExpression.FRIENDLY, "How can I help?").also { stage = 2 }
                2 -> if (getQuestStage(player, "Murder Mystery") == 3) {
                    options("Who do you think is responsible?", "Where were you when the murder happened?", "Did you hear any suspicious noises at all?", "Why'd you buy poison the other day?").also { stage++ }
                } else {
                    options("Who do you think is responsible?", "Where were you when the murder happened?", "Did you hear any suspicious noises at all?").also { stage++ }
                }

                3 -> if (getQuestStage(player, "Murder Mystery") == 3) {
                    when (buttonID) {
                        1 -> playerl(FacialExpression.SUSPICIOUS, "Who do you think is responsible?").also { stage++ }
                        2 -> playerl(FacialExpression.SUSPICIOUS, "Where were you when the murder happened?").also { stage = 5 }
                        3 -> playerl(FacialExpression.SUSPICIOUS, "Did you hear any suspicious noises at all?").also { stage = 6 }
                    }
                } else {
                    when (buttonID) {
                        1 -> playerl(FacialExpression.SUSPICIOUS, "Who do you think is responsible?").also { stage++ }
                        2 -> playerl(FacialExpression.SUSPICIOUS, "Where were you when the murder happened?").also { stage = 5 }
                        3 -> playerl(FacialExpression.SUSPICIOUS, "Did you hear any suspicious noises at all?").also { stage = 6 }
                        4 -> playerl(FacialExpression.SUSPICIOUS, "Why'd you buy poison the other day?").also { stage = 11 }
                    }
                }

                4 -> npcl(FacialExpression.NEUTRAL, "Elizabeth. Her father confronted her about her constant petty thieving, and was devastated to find she had stolen a silver needle which had meant a lot to him. You could hear their argument from Lumbridge!").also { stage = END_DIALOGUE }
                5 -> npcl(FacialExpression.NEUTRAL, "I was right here with Hobbes and Mary. You can't suspect me surely!").also { stage = END_DIALOGUE }
                6 -> npcl(FacialExpression.ASKING, "Suspicious? What do you mean suspicious?").also { stage++ }
                7 -> playerl(FacialExpression.ASKING, "Any sounds of a struggle with an intruder for example?").also { stage++ }
                8 -> npcl(FacialExpression.ASKING, "No, I'm sure I don't recall any such thing.").also { stage++ }
                9 -> playerl(FacialExpression.ASKING, "How about the guard dog barking at an intruder?").also { stage++ }
                10 -> npcl(FacialExpression.HALF_WORRIED, "No, I didn't. If you don't have anything else to ask can You go and leave me alone now? I have a lot of cooking to do for this evening.").also { stage = END_DIALOGUE }
                11 -> npcl(FacialExpression.HALF_WORRIED, "I told Carol to buy some from that strange poison salesman and clean the drains before they began to smell any worse. She was the one who blocked them in the first place with a load").also { stage++ }
                12 -> npcl(FacialExpression.HALF_WORRIED, "of beans that she bought for some reason. There were far too many to eat, and they were almost rotten when she bought them anyway! You'd really have to ask her though.").also { stage = END_DIALOGUE }
            }

            100 -> when (stage) {
                0 -> npcl(FacialExpression.FRIENDLY, "Apparently you aren't as stupid as you look.").also { stage = END_DIALOGUE }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.LOUISA_809)
    }
}

