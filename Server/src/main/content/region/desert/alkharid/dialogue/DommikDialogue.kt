package content.region.desert.alkharid.dialogue

import cfg.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Dommik dialogue.
 */
@Initializable
class DommikDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npcl(FacialExpression.HAPPY, "Would you like to buy some Crafting equipment?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("No, thanks, I've got all the Crafting equipment I need.", "Let's see what you've got, then.").also { stage++ }
            1 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "No, thanks, I've got all the Crafting equipment I need.").also { stage++ }
                2 -> {
                    end()
                    openNpcShop(player, NPCs.DOMMIK_545)
                }
            }
            2 -> npcl(FacialExpression.FRIENDLY, "Okay. Fare well on your travels.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.DOMMIK_545)
    }
}
