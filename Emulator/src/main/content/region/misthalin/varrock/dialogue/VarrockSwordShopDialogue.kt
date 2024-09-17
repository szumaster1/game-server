package content.region.misthalin.varrock.dialogue

import org.rs.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Varrock sword shop dialogue.
 */
@Initializable
class VarrockSwordShopDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HAPPY, "Hello, adventurer. Can I interest you in some swords?").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options( "Yes, please.", "No, I'm okay for swords right now.").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.HAPPY, "Yes, please.").also { stage++ }
                2 -> player(FacialExpression.FRIENDLY, "No, I'm okay for swords right now.").also { stage += 2 }
            }
            2 -> end().also { openNpcShop(player, npc.id) }
            3 -> npc(FacialExpression.FRIENDLY, "Come back if you need any.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SHOPKEEPER_551, NPCs.SHOP_ASSISTANT_552)
    }
}