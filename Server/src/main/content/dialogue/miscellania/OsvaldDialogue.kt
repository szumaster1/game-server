package content.dialogue.miscellania

import cfg.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Osvald dialogue.
 */
@Initializable
class OsvaldDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.FRIENDLY, "Welcome to the Miscellania food store.", "We've only opened recently.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.NEUTRAL, "Would you like to buy anything,", "your Royal Highness?").also { stage++ }
            1 -> options("Could you show me what you have for sale?", "No thank you, I don't need food just now.", "What's it like living down here?").also { stage++ }
            2 -> when (buttonId) {
                1 -> player(FacialExpression.ASKING, "Could you show me what you have for sale?").also { stage++ }
                2 -> player(FacialExpression.NEUTRAL, "No thank you, I don't need food just now.").also { stage = END_DIALOGUE }
                3 -> player(FacialExpression.ASKING, "What's it like living down here?").also { stage = 4 }
            }
            3 -> {
                end()
                openNpcShop(player, NPCs.OSVALD_3923)
            }
            4 -> npc(FacialExpression.FRIENDLY, "The town's thriving.", "I'm sure it'll soon be as busy as Rellekka!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.OSVALD_3923)
    }
}
