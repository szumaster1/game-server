package content.region.kandarin.quest.makinghistory.dialogue

import content.region.kandarin.quest.makinghistory.handlers.MakingHistoryUtils
import org.rs.consts.NPCs
import core.api.getQuestStage
import core.api.getVarbit
import core.api.setVarbit
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import org.rs.consts.QuestName

/**
 * Represents the Blanin dialogue.
 */
@Initializable
class BlaninDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        if (getQuestStage(player, QuestName.MAKING_HISTORY) < 1) {
            playerl(FacialExpression.FRIENDLY, "Excuse me.")
            stage = 0
            return true
        } else if (getQuestStage(player, QuestName.MAKING_HISTORY) >= 1) {
            playerl(FacialExpression.FRIENDLY, "Hello there. Are you the brother of Dron?")
            stage = 1
            return true
        }

        if (getVarbit(player, MakingHistoryUtils.DRON_PROGRESS) == 2) {
            playerl(FacialExpression.FRIENDLY, "Excuse me.")
            stage = 13
            return true
        }
        if (getVarbit(player, MakingHistoryUtils.DRON_PROGRESS) == 4) {
            playerl(FacialExpression.FRIENDLY, "That's one less thing to worry about.")
            stage = 20
            return true
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.FRIENDLY, "Look, I don't have time for weaklings, if you want conversation, talk to my brother Blanin!").also { stage = END_DIALOGUE }
            1 -> npcl(FacialExpression.FRIENDLY, "That I am. Why? Has he killed one of your family?").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "Not that I know of...").also { stage++ }
            3 -> npcl(FacialExpression.FRIENDLY, "Oh good, how can I help you?").also { stage++ }
            4 -> playerl(FacialExpression.FRIENDLY, "Well I'd like to talk to your brother Dron about the outpost north of Ardougne.").also { stage++ }
            5 -> npcl(FacialExpression.FRIENDLY, "I'm afraid he's not easy to talk to, so it's good that you came to see me. You'll need to remember a few things when talking to him.").also { stage++ }
            6 -> playerl(FacialExpression.FRIENDLY, "Like?").also { stage++ }
            7 -> npcl(FacialExpression.FRIENDLY, "You must be firm with him and don't mention that I sent you. In case he asks, he wields an iron mace in battle, eats rats for breakfast, kittens for lunch and bunnies for tea!").also { stage++ }
            8 -> npcl(FacialExpression.FRIENDLY, "His favorite drink is red spider blood, he's 36 years, 8 months and 21 days old, studies famous battles of the Fourth and Fifth ages,").also { stage++ }
            9 -> npcl(FacialExpression.FRIENDLY, "lives on the northeast side of town and his, erm... pet cat is called Fluffy.").also { stage++ }
            10 -> playerl(FacialExpression.FRIENDLY, "O...kay....").also { stage++ }
            11 -> npcl(FacialExpression.FRIENDLY, "I know this sounds strange but Dron won't talk to anyone unless they know him well - he's a secretive guy.").also { stage++ }
            12 -> {
                end()
                setVarbit(player, MakingHistoryUtils.DRON_PROGRESS, 3, true)
            }
            13 -> npcl(FacialExpression.FRIENDLY, "Making progress?").also { stage++ }
            14 -> playerl(FacialExpression.FRIENDLY, "Not the best. Can you give me those hints again for speaking with Dron?").also { stage++ }
            15 -> npcl(FacialExpression.FRIENDLY, "Sure. You must be firm with him and don't mention that I sent you. In case he asks, he wields an iron mace in battle, eats rats for breakfast,").also { stage++ }
            16 -> npcl(FacialExpression.FRIENDLY, "kittens for lunch and bunnies for His favorite drink is red spider blood, he's 36 years, 8 months and 21 days old, he studies famous battles of the Fourth and Fifth ages,").also { stage++ }
            17 -> npcl(FacialExpression.FRIENDLY, "lives on the North East side of town and his, erm... pet cat is called Fluffy.").also { stage++ }
            18 -> playerl(FacialExpression.FRIENDLY, "I think I can remember all that.").also { stage++ }
            19 -> npcl(FacialExpression.FRIENDLY, "Just remember those points when you speak to Dron. He's nearby, so you should still be able to find him.").also { stage = END_DIALOGUE }
            20 -> npcl(FacialExpression.FRIENDLY, "Glad I could help.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BLANIN_2940)
    }
}

