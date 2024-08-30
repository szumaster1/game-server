package content.region.fremennik.miscellania.dialogue

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Donal dialogue.
 */
@Initializable
class DonalDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.OLD_DEFAULT, "What do you want?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> player(FacialExpression.THINKING, "Just wondering if you were still here.").also { stage++ }
            1 -> npc(FacialExpression.OLD_DEFAULT, "Of course I'm still here.").also { stage++ }
            2 -> npc(FacialExpression.OLD_DISTRESSED, "I'm not going near that crack in the wall again.").also { stage++ }
            3 -> npc(FacialExpression.OLD_DISTRESSED, "Rock falls and so on are fine, ", "but sea monsters in caves - never!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.DONAL_3938)
    }
}
