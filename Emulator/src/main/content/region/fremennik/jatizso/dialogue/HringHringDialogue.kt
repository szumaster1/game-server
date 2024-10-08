package content.region.fremennik.jatizso.dialogue

import org.rs.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Hring Hring dialogue.
 */
@Initializable
class HringHringDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc("Oh, hello again. Want some ore?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("I'll have a look.", "Not right now.").also { stage++ }
            1 -> when (buttonId) {
                1 -> player("I'll have a look.").also { stage++ }
                2 -> player("Not right now.").also { stage = END_DIALOGUE }
            }
            2 -> {
                end()
                openNpcShop(player, NPCs.HRING_HRING_5483)
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.HRING_HRING_5483)
    }

}
