package content.location.miscellania

import cfg.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Runa dialogue.
 */
@Initializable
class RunaDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.FRIENDLY, "Would you like to try some fine Miscellanian ale, ", "your Royal Highness?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.ASKING, "Well I say Miscellanian, but it's actually brewed", "on the mainland.").also { stage++ }
            1 -> npc(FacialExpression.FRIENDLY, "Would you like to try some anyway?").also { stage++ }
            2 -> options("Yes, please.", "No, thank you.", "What's it like living down here?").also { stage++ }
            3 -> when (buttonId) {
                1 -> player(FacialExpression.ASKING, "Yes please.").also { stage++ }
                2 -> player(FacialExpression.NEUTRAL, "No thank you.").also { stage = END_DIALOGUE }
                3 -> player(FacialExpression.ASKING, "What's it like living down here?").also { stage = 5 }
            }
            4 -> {
                end()
                openNpcShop(player, NPCs.RUNA_3920)
            }
            5 -> npc(FacialExpression.HALF_WORRIED, "Business is booming!").also { stage++ }
            6 -> npc(FacialExpression.HALF_WORRIED, "Now, if only I hadn't taken a loss to the beer I sold", "to those teenagers.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.RUNA_3920)
    }
}
