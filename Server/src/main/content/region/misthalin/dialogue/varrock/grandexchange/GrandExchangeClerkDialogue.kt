package content.region.misthalin.dialogue.varrock.grandexchange

import core.api.consts.NPCs
import content.global.handlers.iface.ge.ExchangeItemSetsListener
import content.global.handlers.iface.ge.StockMarketInterfaceListener
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.ge.GrandExchangeRecords.Companion.getInstance
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Grand exchange clerk dialogue.
 */
@Initializable
class GrandExchangeClerkDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        player(FacialExpression.HALF_GUILTY, "Hi there.")
        stage = 1
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            1 -> npc("Good day to you, sir, How can I help?").also { stage++ }
            2 -> options("I want to access the Grand Exchange, please.", "I want to collect my items.", "Can I see a history of my offers?", "Can you help me with item sets?", "I'm fine, actually.").also { stage++ }

            3 -> when (buttonId) {
                1 -> player(FacialExpression.HALF_GUILTY, "I want to access the Grand Exchange, please.").also { stage = 10 }
                2 -> player(FacialExpression.HALF_GUILTY, "I want to collect my items.").also { stage = 20 }
                3 -> player(FacialExpression.HALF_GUILTY, "Can I see history of my offers?").also { stage = 30 }
                4 -> playerl(FacialExpression.HALF_GUILTY, "Can you help me with item sets?").also { stage = 40 }
                5 -> player(FacialExpression.HALF_GUILTY, "I'm fine actually.").also { stage = 50 }
            }
            10 -> npc(FacialExpression.HALF_GUILTY, "Only too happy to help you, sir.").also { stage++ }
            11 -> {
                end()
                StockMarketInterfaceListener.openFor(player!!)
            }
            20 -> npc(FacialExpression.HALF_GUILTY, "As you wish, sir.").also { stage++ }
            21 -> {
                end()
                getInstance(player).openCollectionBox()
            }
            30 -> npc(FacialExpression.HALF_GUILTY, "If that is your wish.").also { stage++ }
            31 -> {
                end()
                getInstance(player).openHistoryLog(player!!)
            }
            40 -> npc(FacialExpression.HALF_GUILTY, "It would be my pleasure, sir.").also { stage++ }
            41 -> {
                end()
                ExchangeItemSetsListener.openFor(player!!)
            }
            50 -> npc(FacialExpression.HALF_GUILTY, "If you say so, sir.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun newInstance(player: Player): Dialogue {
        return GrandExchangeClerkDialogue(player)
    }

    override fun getIds(): IntArray {
        return intArrayOf(
            NPCs.GRAND_EXCHANGE_CLERK_6528,
            NPCs.GRAND_EXCHANGE_CLERK_6529,
            NPCs.GRAND_EXCHANGE_CLERK_6530,
            NPCs.GRAND_EXCHANGE_CLERK_6531,
        )
    }

}
