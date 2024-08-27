package content.location.keldagrim.directors

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Purple director dialogue.
 */
@Initializable
class PurpleDirectorDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        when((0..7).random()){
            0 -> npcl(FacialExpression.OLD_DEFAULT, "My secretary is sitting just over there, thank you.").also { stage++ }
            1 -> npcl(FacialExpression.OLD_DEFAULT, "Today is not a good day, can you come back tomorrow?").also { stage++ }
            2 -> npcl(FacialExpression.OLD_DEFAULT, "I'm busy, please leave.").also { stage++ }
            3 -> npcl(FacialExpression.OLD_DEFAULT, "If you're looking for the market, it's downstairs.").also { stage++ }
            4 -> npcl(FacialExpression.OLD_DEFAULT, "I can't help you, go and see my secretary.").also { stage++ }
            5 -> npcl(FacialExpression.OLD_DEFAULT, "Talk to my secretary. I don't have time to deal with you.").also { stage++ }
            6 -> npcl(FacialExpression.OLD_DEFAULT, "My secretary will deal with any inquiries you have about the Purple Pewter.").also { stage++ }
            7 -> npcl(FacialExpression.OLD_DEFAULT, "Do you mind? You're blocking my view.").also { stage++ }
        }
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> player("But...").also { stage++ }
            1 -> when((0..1).random()){
                0 -> npcl(FacialExpression.OLD_ANGRY1, "I said go!").also { stage = END_DIALOGUE }
                1 -> npcl(FacialExpression.OLD_ANGRY2, "I told you to leave, human!").also { stage = END_DIALOGUE }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.PURPLE_PEWTER_DIRECTOR_2100)
    }
}
