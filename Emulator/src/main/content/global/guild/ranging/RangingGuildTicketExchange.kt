package content.global.guild.ranging

import core.api.*
import cfg.consts.Components
import cfg.consts.Items
import cfg.consts.NPCs
import core.game.container.access.InterfaceContainer
import core.game.dialogue.FacialExpression
import core.game.interaction.InterfaceListener
import core.game.node.item.Item

/**
 * Ticket exchange.
 *
 * @param item The item to be exchanged.
 * @param tickets The number of tickets required for the exchange.
 * @param slot The slot number of the exchange.
 * @param value The value of the exchange.
 * @constructor Creates a TicketExchange instance.
 */
enum class TicketExchange(val item: Item, val tickets: Int, val slot: Int, val value: String) {
    /**
     * Barb Bolt Tips.
     */
    BARB_BOLT_TIPS(Item(Items.BARB_BOLTTIPS_47, 30), 140, 0, "The 30 Barb Boltips cost 140 Archery Tickets."),

    /**
     * Studded Body.
     */
    STUDDED_BODY(Item(Items.STUDDED_BODY_1133, 1), 150, 1, "The Studded Body costs 150 Archery Tickets."),

    /**
     * Rune Arrow.
     */
    RUNE_ARROW(Item(Items.RUNE_ARROW_892, 50), 2000, 2, "The 50 Rune Arrows cost 2,000 Archery Tickets."),

    /**
     * Coif.
     */
    COIF(Item(Items.COIF_1169, 1), 100, 3, "The Coif costs 100 Archery Tickets."),

    /**
     * Green D Hide.
     */
    GREEN_D_HIDE(Item(Items.GREEN_DHIDE_BODY_1135, 1), 2400, 4, "The Dragonhide body costs 2,400 Archery Tickets."),

    /**
     * Adamant Javelin.
     */
    ADAMANT_JAVELIN(Item(Items.ADAMANT_JAVELIN_829, 20), 2000, 5, "The 20 Adamant Javelins cost 2,000 Archery Tickets.");

    companion object {
        val itemsMap = HashMap<Int, TicketExchange>()

        init {
            for (exchange in values()) {
                itemsMap[exchange.slot] = exchange
            }
        }
    }

}

/**
 * Ticket exchange interface listener.
 */
class TicketExchangeInterfaceListener : InterfaceListener {

    private val itemIds = intArrayOf(Items.BARB_BOLTTIPS_47, Items.STUDDED_BODY_1133, Items.RUNE_ARROW_892, Items.COIF_1169, Items.GREEN_DHIDE_BODY_1135, Items.ADAMANT_JAVELIN_829)

    override fun defineInterfaceListeners() {
        onOpen(Components.RANGING_GUILD_TICKET_EXCHANGE_278) { player, _ ->
            InterfaceContainer.generateItems(player, arrayOf(Item(Items.BARB_BOLTTIPS_47, 30), Item(Items.STUDDED_BODY_1133, 1), Item(Items.RUNE_ARROW_892, 50), Item(Items.COIF_1169, 1), Item(Items.GREEN_DHIDE_BODY_1135, 1), Item(Items.ADAMANT_JAVELIN_829, 20)), arrayOf("Buy", "Value"), Components.RANGING_GUILD_TICKET_EXCHANGE_278, 16, 3, 6)
            return@onOpen true
        }

        on(Components.RANGING_GUILD_TICKET_EXCHANGE_278) { player, _, op, _, slot, _ ->
            val exchange = TicketExchange.itemsMap[slot]
            var tickets = TicketExchange.values().map { it.tickets }.toIntArray()
            when (op) {
                9 -> sendMessage(player, itemDefinition(itemIds[slot]).examine)
                155 -> sendMessage(player, exchange!!.value)
                196 -> {
                    if (freeSlots(player) < 1) {
                        sendNPCDialogue(player, NPCs.TICKET_MERCHANT_694, "Sorry, You don't have enough inventory space.", FacialExpression.HALF_GUILTY)
                        return@on true
                    }
                    if (!removeItem(player, Item(Items.ARCHERY_TICKET_1464, tickets[slot]))) {
                        sendMessage(player, "You don't have enough Archery Tickets.")
                    } else {
                        player.inventory.add(exchange!!.item)
                    }
                    return@on true
                }

            }
            return@on true
        }
    }
}
