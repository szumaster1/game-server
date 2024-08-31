package content.region.karamja.brimhaven.dialogue

import core.api.addItem
import cfg.consts.Items
import cfg.consts.NPCs
import core.api.removeItem
import core.api.sendMessage
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Brimhaven Bartender dialogue.
 */
@Initializable
class BrimhavenBartenderDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HAPPY, "Yohoho me hearty what would you like to drink?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Nothing, thank you.", "A pint of Grog please.", "A bottle of rum please.").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.FRIENDLY, "Nothing, thank you.").also { stage = END_DIALOGUE }
                2 -> player(FacialExpression.HAPPY, "A pint of Grog please..").also { stage = 20 }
                3 -> player(FacialExpression.HAPPY, "A bottle of rum please.").also { stage = 30 }
            }
            20 -> npc(FacialExpression.FRIENDLY, "One grog coming right up, that'll be three coins.").also { stage++ }
            21 -> {
                if (!removeItem(player, Item(Items.COINS_995, 3))) {
                    end()
                    player("Sorry, I don't seem to have enough coins.")
                } else {
                    end()
                    addItem(player, Items.GROG_1915)
                    sendMessage(player, "You buy a pint of Grog.")
                }
            }
            30 -> npc(FacialExpression.FRIENDLY, "That'll be 27 coins.").also { stage++ }
            31 -> {
                if (!removeItem(player, Item(Items.COINS_995, 27))) {
                    end()
                    player("Sorry, I don't seem to have enough coins.")
                } else {
                    end()
                    addItem(player, Items.RUM_8940)
                    sendMessage(player, "You buy a bottle of rum.")
                }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BARTENDER_735)
    }

}
