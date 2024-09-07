package content.region.asgarnia.dwarvenmines.dialogue

import cfg.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the Dwarven shopkeeper dialogue.
 */
@Initializable
class DwarvenShopkeeperDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * This dwarf is the shopkeeper of the Dwarven Shopping Store in
     * the Dwarven Mine.
     */

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.OLD_HAPPY, "Can I help you at all?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Yes please, what are you selling?", "How should I use your shop?", "No thanks.").also { stage++ }
            1 -> when (buttonId) {
                1 -> {
                    end()
                    openNpcShop(player, NPCs.DWARF_582)
                }
                2 -> player("How should I use your shop?").also { stage++ }
                3 -> player("No, thanks.").also { stage = 3 }
            }
            2 -> npc(FacialExpression.OLD_HAPPY, "I'm glad you ask! You can buy as many of the items", "stocked as you wish. You can also sell most items to", "the shop.").also { stage = 0 }
            3 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.DWARF_582)
    }
}
