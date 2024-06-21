package content.region.desert.dialogue.pollnivneach

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.utilities.END_DIALOGUE

@Initializable
class BlackJackSellerDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc("I'm not interested in selling to you. Not yet...").also { stage = END_DIALOGUE }
        stage = 0
        return true
    }

    override fun handle(intefaceId: Int, objectId: Int): Boolean {
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BLACKJACK_SELLER_2548)
    }

}
