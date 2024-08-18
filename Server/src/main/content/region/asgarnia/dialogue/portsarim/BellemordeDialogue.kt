package content.region.asgarnia.dialogue.portsarim

import core.api.anyInEquipment
import core.api.consts.Items
import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Bellemorde dialogue.
 */
@Initializable
class BellemordeDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * The cat is a close companion of The Face,
     * and can be found north of Gerrant's Fishy Business.
     */

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        player("Hello puss.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                if (!anyInEquipment(player, Items.CATSPEAK_AMULET_4677, Items.CATSPEAK_AMULETE_6544)) {
                    npc(FacialExpression.CHILD_FRIENDLY, "Hiss!").also { stage = END_DIALOGUE }
                } else {
                    npc(FacialExpression.CHILD_FRIENDLY, "Hello human.").also { stage++ }
                }
            }
            1 -> player("Would you like a fish?").also { stage++ }
            2 -> npc(FacialExpression.CHILD_FRIENDLY, "I don't want your fish. I hunt and eat what I", "need by myself.").also { stage = END_DIALOGUE }
        }
        return true
    }
    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BELLEMORDE_2942)
    }

}
