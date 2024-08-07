package content.region.kandarin.dialogue.stronghold

import core.api.addItemOrDrop
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.openNpcShop
import core.api.removeItem
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Barman grand tree dialogue.
 */
@Initializable
class BarmanGrandTreeDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npcl(FacialExpression.OLD_NORMAL, "Good day to you. What can I get you to drink?").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("What do you have?", "Nothing thanks.", "Can I buy some ingredients?").also { stage++ }
            1 -> when (buttonId) {
                1 -> playerl(FacialExpression.HALF_ASKING, "What do you have?").also { stage = 2 }
                2 -> playerl(FacialExpression.NEUTRAL, "Nothing thanks.").also { stage = 4 }
                3 -> playerl(FacialExpression.HALF_ASKING, "I was just wanting to buy a cocktail ingredient actually.").also { stage = 5 }
            }

            2 -> npcl(FacialExpression.OLD_NORMAL, "Here, take a look at our menu.").also { stage++ }
            3 -> {
                end()
                openNpcShop(player, NPCs.BARMAN_849)
            }
            4 -> npcl(FacialExpression.OLD_NORMAL, "Okay, take it easy.").also { stage = END_DIALOGUE }
            5 -> npcl(FacialExpression.OLD_NORMAL, "Sure thing, what did you want?").also { stage++ }
            6 -> options("A lemon.", "An orange.", "A cocktail shaker.", "Nothing thanks.").also { stage++ }
            7 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "A lemon.").also { stage = 8 }
                2 -> playerl(FacialExpression.FRIENDLY, "An orange.").also { stage = 10 }
                3 -> playerl(FacialExpression.FRIENDLY, "A cocktail shaker.").also { stage = 12 }
                4 -> playerl(FacialExpression.NEUTRAL, "Nothing thanks.").also { stage = 14 }
            }

            8 -> npcl(FacialExpression.OLD_NORMAL, "20 coins please.").also { stage++ }
            9 -> if (!removeItem(player, Item(Items.COINS_995, 20))) {
                playerl(FacialExpression.FRIENDLY, "Sorry, I don't have enough coins for that.").also { stage = END_DIALOGUE }
            } else {
                end()
                addItemOrDrop(player, Items.LEMON_2102)
            }

            10 -> npcl(FacialExpression.OLD_NORMAL, "20 coins please.").also { stage++ }
            11 -> if (!removeItem(player, Item(Items.COINS_995, 20))) {
                playerl(FacialExpression.FRIENDLY, "Sorry, I don't have enough coins for that.").also { stage = END_DIALOGUE }
            } else {
                end()
                addItemOrDrop(player, Items.ORANGE_2108)
            }

            12 -> npcl(FacialExpression.OLD_NORMAL, "20 coins please.").also { stage++ }
            13 -> if (!removeItem(player, Item(Items.COINS_995, 20))) {
                playerl(FacialExpression.FRIENDLY, "Sorry, I don't have enough coins for that.").also { stage = END_DIALOGUE }
            } else {
                end()
                addItemOrDrop(player, Items.COCKTAIL_SHAKER_2025)
            }
            14 -> playerl(FacialExpression.FRIENDLY, "Actually nothing thanks.").also { stage = END_DIALOGUE }

        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BARMAN_849)
    }
}
