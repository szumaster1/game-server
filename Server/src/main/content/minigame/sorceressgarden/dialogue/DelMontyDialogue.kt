package content.minigame.sorceressgarden.dialogue

import core.game.container.impl.EquipmentContainer
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Del monty dialogue.
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
        return intArrayOf(5563)
    }

    companion object {
        fun hasCatAmulet(player: Player): Boolean {
            val item = player.equipment[EquipmentContainer.SLOT_AMULET] ?: return false
            return item.id == 4677 || item.id == 6544
        }
    }
}