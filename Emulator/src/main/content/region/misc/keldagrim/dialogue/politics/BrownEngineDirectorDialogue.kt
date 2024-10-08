package content.region.misc.keldagrim.dialogue.politics

import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE
import org.rs.consts.NPCs

/**
 * Represents the Brown engine director dialogue.
 */
@Initializable
class BrownEngineDirectorDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            START_DIALOGUE -> when ((0..9).random()) {
                0 -> npc(FacialExpression.OLD_NORMAL, "I'm too important to talk to the likes of you,", "leave.").also { stage++ }
                1 -> npc(FacialExpression.OLD_NORMAL, "My secretary will deal with any inquiries you", "have about the Brown Engine.").also { stage++ }
                2 -> npc(FacialExpression.OLD_NORMAL, "My secretary is sitting just over there,", "thank you.").also { stage++ }
                3 -> npc(FacialExpression.OLD_NORMAL, "I'm busy, please leave.").also { stage++ }
                4 -> npc(FacialExpression.OLD_NORMAL, "Today is not a good day, can you come back", "tomorrow?").also { stage++ }
                5 -> npc(FacialExpression.OLD_NORMAL, "If you're looking for the market,", "it's downstairs.").also { stage++ }
                6 -> npc(FacialExpression.OLD_NORMAL, "Do you mind? You're blocking my view.").also { stage++ }
                7 -> npc(FacialExpression.OLD_NORMAL, "How did you get in here? I don't have time", "to talk to you!").also { stage++ }
                8 -> npc(FacialExpression.OLD_NORMAL, "I can't help you, go and see my secretary.").also { stage++ }
                9 -> npc(FacialExpression.OLD_NORMAL, "Talk to my secretary, I don't have time to", "deal with you.").also { stage++ }
            }
            1 -> player(FacialExpression.SAD, "But...").also { stage++ }
            2 -> when ((1..2).random()) {
                1 -> npc(FacialExpression.OLD_NORMAL, "I said go!").also { stage = END_DIALOGUE }
                2 -> npc(FacialExpression.OLD_NORMAL, "I told you to leave, human!").also { stage = END_DIALOGUE }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BROWN_ENGINE_DIRECTOR_2106)
    }
}
