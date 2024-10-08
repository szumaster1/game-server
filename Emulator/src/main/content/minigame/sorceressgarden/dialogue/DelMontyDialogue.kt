package content.minigame.sorceressgarden.dialogue

import core.game.container.impl.EquipmentContainer
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import org.rs.consts.Items
import org.rs.consts.NPCs

/**
 * Represents the Del Monty dialogue.
 */
@Initializable
class DelMontyDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        player("Hey kitty!")
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                npc(FacialExpression.CHILD_NORMAL, "Meow.")
                stage = 1
            }

            1 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.DEL_MONTY_5563)
    }

    companion object {
        fun hasCatAmulet(player: Player): Boolean {
            val item = player.equipment[EquipmentContainer.SLOT_AMULET] ?: return false
            return item.id == Items.CATSPEAK_AMULET_4677 || item.id == Items.CATSPEAK_AMULETE_6544
        }
    }
}