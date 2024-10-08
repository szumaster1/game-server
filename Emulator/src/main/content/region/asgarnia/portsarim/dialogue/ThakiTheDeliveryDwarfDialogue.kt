package content.region.asgarnia.portsarim.dialogue

import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Thaki the delivery dwarf dialogue.
 */
@Initializable
class ThakiTheDeliveryDwarfDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Thaki the delivery dwarf is a delivery dwarf
     * featured in the Catapult Construction quest.
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.OLD_NORMAL, "Arrr!")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> player(FacialExpression.HAPPY, "Hi little fellow.").also { stage++ }
            1 -> npc(FacialExpression.OLD_NORMAL, "What did you just say to me!?").also { stage++ }
            2 -> player(FacialExpression.GUILTY, "Arrr! nothing, nothing at all..").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.THAKI_THE_DELIVERY_DWARF_7115)
    }
}
