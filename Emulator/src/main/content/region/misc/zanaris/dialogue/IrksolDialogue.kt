package content.region.misc.zanaris.dialogue

import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import org.rs.consts.NPCs

/**
 * Represents the Irksol dialogue.
 */
@Initializable
class IrksolDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npc("Selling ruby rings! The best deals on rings in over", "twenty four hundred planes of existence!")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("I'm interested in these deals.", "No thanks, just browsing.").also { stage++ }
            1 -> when (buttonId) {
                1 -> player("I'm interested in these deals.").also { stage++ }
                2 -> player("No thanks, just browsing.").also { stage = 4 }
            }
            2 -> npc("Aha! A connoisseur! Check out these beauties!").also { stage++ }
            3 -> {
                end(); openNpcShop(player, NPCs.IRKSOL_566)
            }
            4 -> npc("Fair enough.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.IRKSOL_566)
    }

}
