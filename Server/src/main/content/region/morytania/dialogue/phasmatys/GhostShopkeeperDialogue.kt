package content.region.morytania.dialogue.phasmatys

import core.api.consts.Items
import core.api.consts.NPCs
import core.api.inEquipment
import core.api.openNpcShop
import core.api.sendDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Ghost shopkeeper dialogue.
 */
@Initializable
class GhostShopkeeperDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Info: Runs the Port Phasmatys General Store in Port Phasmatys.
     * Location: 3659,3480
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (!inEquipment(player, Items.GHOSTSPEAK_AMULET_552)) {
            npc(FacialExpression.FRIENDLY, "Woooo wooo wooooo woooo").also { stage = 2 }
        } else {
            npc(FacialExpression.FRIENDLY, "Can I help you at all?")
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Yes, please. What are you selling?", "How should I use your shop?", "No, thanks.").also { stage++ }
            1 -> when (buttonId) {
                1 -> {
                    end()
                    openNpcShop(player, NPCs.GHOST_SHOPKEEPER_1699)
                }
                2 -> npc(FacialExpression.HAPPY, "I'm glad you ask! You can buy as many of the items", "stocked as you wish. You can also sell most items to the", "shop.").also { stage = END_DIALOGUE }
                3 -> end()
            }
            2 -> sendDialogue(player, "You cannot understand the ghost.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GHOST_SHOPKEEPER_1699)
    }

}
