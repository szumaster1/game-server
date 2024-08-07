package content.region.desert.dialogue.pollnivneach

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Black jack seller dialogue.
 */
@Initializable
class BlackJackSellerDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        sendDialogue("The Blackjack Seller doesn't seem interested in you.").also { stage = END_DIALOGUE }
        return true
    }

    override fun handle(intefaceId: Int, objectId: Int): Boolean {
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BLACKJACK_SELLER_2548)
    }

}
