package content.dialogue.keldagrim

import cfg.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Vigr dialogue.
 */
@Initializable
class VigrDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.OLD_NORMAL, "What do you want, human?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> player(FacialExpression.HALF_ASKING, "Ehm, anything on offer?").also { stage++ }
            1 -> npc(FacialExpression.OLD_NORMAL, "Can you wield a warhammer?", "If not, then go away.").also { stage++ }
            2 -> options("Of course I can.", "I can, but I won't.").also { stage++ }
            3 -> when (buttonId) {
                1 -> player(FacialExpression.FRIENDLY, "Of course I can.").also { stage++ }
                2 -> player(FacialExpression.NEUTRAL, "I can, but I won't.").also { stage = END_DIALOGUE }
            }
            4 -> {
                end()
                openNpcShop(player, NPCs.VIGR_2151)
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.VIGR_2151)
    }
}
