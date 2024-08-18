package content.region.kandarin.dialogue.ardougne

import core.api.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the Gem Merchant dialogue.
 */
@Initializable
class GemMerchantDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * The gem merchant runs the Ardougne Gem Stall in East Ardougne.
     * He sells sapphires, emeralds, rubies, and diamonds.
     * His stall's default stock is 0.
     * Location: 2669,3303
     */

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HAPPY, "Here, look at my lovely gems.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                end()
                openNpcShop(player, NPCs.GEM_MERCHANT_570)
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GEM_MERCHANT_570)
    }
}