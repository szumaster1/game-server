package content.region.asgarnia.dialogue.portsarim

import core.api.anyInEquipment
import core.api.consts.Items
import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.utilities.END_DIALOGUE

@Initializable
class BellemordeDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        player("Hello puss.")
        stage = 0
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
