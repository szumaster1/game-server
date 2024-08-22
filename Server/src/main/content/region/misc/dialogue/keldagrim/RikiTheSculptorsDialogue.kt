package content.region.misc.dialogue.keldagrim

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE


/**
 * Represents the Riki the sculptors dialogue.
 */
@Initializable
class RikiTheSculptorsDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        player(FacialExpression.FRIENDLY, " I'm glad I don't have to talk to you anymore!")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.OLD_DEFAULT, "Hrm.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(
            NPCs.RIKI_THE_SCULPTORS_MODEL_2143,
            NPCs.RIKI_THE_SCULPTORS_MODEL_2144,
            NPCs.RIKI_THE_SCULPTORS_MODEL_2145,
            NPCs.RIKI_THE_SCULPTORS_MODEL_2146,
            NPCs.RIKI_THE_SCULPTORS_MODEL_2147,
            NPCs.RIKI_THE_SCULPTORS_MODEL_2148,
            NPCs.RIKI_THE_SCULPTORS_MODEL_2149,
            NPCs.RIKI_THE_SCULPTORS_MODEL_2150
        )
    }
}