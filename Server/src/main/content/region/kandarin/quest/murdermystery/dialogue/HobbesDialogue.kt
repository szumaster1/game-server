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
 * Represents the Hobbes dialogue.
 */
@Initializable
class HobbesDialogue(player: Player? = null) : Dialogue(player) {

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
                0 -> playerl(FacialExpression.SUSPICIOUS, "I'm here to help the guards with their investigation.").also { stage++ }
                1 -> npcl(FacialExpression.FRIENDLY, "How can I help?").also { stage++ }
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
                        4 -> playerl(FacialExpression.SUSPICIOUS, "Why'd you buy poison the other day?").also { stage = 12 }
                    }
                }
                4 -> npcl(FacialExpression.NEUTRAL, "Well, in my considered opinion it must be David. The man is nothing more than a bully. And I happen to know that poor Lord Sinclair and David had a massive argument in the living room about the way he treats the staff, the other day.").also { stage++ }
                5 -> npcl(FacialExpression.NEUTRAL, "I did not intend to overhear their conversation, but they were shouting so loudly I could not help but Overhear it. David definitely used the words 'I am going to kill you!' as well. I think he should be the prime suspect. He has a nasty temper that one.").also { stage = END_DIALOGUE }
                6 -> npcl(FacialExpression.NEUTRAL, "I was assisting the cook with the evening meal. I have Mary His Lordships' dinner, and sent her to take it to him, then heard the scream as she found the body.").also { stage = END_DIALOGUE }
                7 -> npcl(FacialExpression.ASKING, "How do you mean 'suspicious'?").also { stage++ }
                8 -> playerl(FacialExpression.ASKING, "Any sounds of a struggle with Lord Sinclair?").also { stage++ }
                9 -> npcl(FacialExpression.NEUTRAL, "No, I definitely didn't hear anything like that.").also { stage++ }
                10 -> playerl(FacialExpression.ASKING, "How about the guard dog barking at all?").also { stage++ }
                11 -> npcl(FacialExpression.ASKING, "You know, now you come to mention it I don't believe I did. I suppose that is Proof enough that it could not have been an intruder who is responsible.").also { stage = END_DIALOGUE }
                12 -> npcl(FacialExpression.NEUTRAL, "Well, I do know that Elizabeth was extremely annoyed by the mosquito nest under the fountain in the garden, and was going to do something about it. I suspect any poison she bought would have been").also { stage++ }
                13 -> npcl(FacialExpression.NEUTRAL, "enough to get rid of it. A Good job too, I hate mosquitos.").also { stage++ }
                14 -> playerl(FacialExpression.NEUTRAL, "Yeah, so do I.").also { stage++ }
                15 -> npcl(FacialExpression.NEUTRAL, "You'd really have to ask her though.").also { stage = END_DIALOGUE }
            }

            100 -> when (stage) {
                0 -> npcl(FacialExpression.FRIENDLY, "Apparently you aren't as stupid as you look.").also { stage = END_DIALOGUE }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.HOBBES_808)
    }
}
