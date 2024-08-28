package content.dialogue.varrock

import cfg.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Varrock shopkeeper dialogue.
 */
@Initializable
class VarrockShopKeeperDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Info: Runs the Varrock General Store, located in Varrock.
     * He is assisted by a shop assistant.
     * Location: 3218,3415
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HAPPY, "Can I help you at all?").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Yes, please. What are you selling?", "How should I use your shop?", "No, thanks.").also { stage++ }
            1 -> when (buttonId) {
                1 -> {
                    end()
                    openNpcShop(player, npc.id)
                }
                2 -> npc(FacialExpression.HAPPY, "I'm glad you ask! You can buy as many of the items", "stocked as you wish. You can also sell most items to the", "shop.").also { stage = END_DIALOGUE }
                3 -> end()
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SHOPKEEPER_522, NPCs.SHOP_ASSISTANT_523)
    }

}
