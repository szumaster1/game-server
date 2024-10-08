package content.region.asgarnia.dwarvenmines.dialogue

import org.rs.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Drogo dialogue.
 */
@Initializable
class DrogoDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.OLD_DEFAULT, "'Ello. Welcome to my Mining shop, friend.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Do you want to trade?", "Hello, shorty.", "Why don't you ever restock ores and bars?").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.FRIENDLY, "Do you want to trade?").also { stage++ }
                2 -> player(FacialExpression.FRIENDLY, "Hello, shorty.").also { stage = 3 }
                3 -> player(FacialExpression.FRIENDLY, "Why don't you ever restock ores and bars?").also { stage = 4 }
            }
            2 -> {
                end()
                openNpcShop(player, NPCs.DROGO_DWARF_579)
            }
            3 -> npc(FacialExpression.OLD_ANGRY1, "I may be short, but at least I've got manners.").also { stage = END_DIALOGUE }
            4 -> npc(FacialExpression.OLD_DEFAULT, "The only ores and bars I sell are those sold to me.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.DROGO_DWARF_579)
    }
}
