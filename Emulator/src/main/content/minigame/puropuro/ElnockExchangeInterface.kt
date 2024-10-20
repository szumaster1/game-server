package content.minigame.puropuro

import core.api.*
import core.game.interaction.InterfaceListener
import org.rs.consts.Components

class ElnockExchangeInterface : InterfaceListener {
    override fun defineInterfaceListeners() {
        onOpen(Components.ELNOCK_EXCHANGE_540) { player, _ ->
            val values = intArrayOf(22, 25, 28, 31)
            for (i in ElnockExchange.values().indices) {
                val e = ElnockExchange.values()[i]
                sendItemZoomOnInterface(player, e.sendItem, 115, 540, values[i])
            }
            return@onOpen true
        }

        on(Components.ELNOCK_EXCHANGE_540) { player, _, _, buttonID, _, _ ->
            var exchange = player.getAttribute<ElnockExchange>("exchange", null)
            if (buttonID == 34) {
                setVarp(player, 1018, 0)
                if (exchange == null) {
                    sendMessage(player, "Making a selection before confirming.")
                    return@on true
                }
                if (!exchange.hasItems(player)) {
                    sendMessage(player,"You don't have the required implings in a jar to trade for this.")
                    return@on true
                }
                if (exchange == ElnockExchange.JAR_GENERATOR && player.hasItem(ElnockExchange.JAR_GENERATOR.reward)) {
                    sendMessage(player,"You can't have more than one jar generator at a time.")
                    return@on true
                }
                if (!hasSpaceFor(player, exchange.reward)) {
                    sendMessage(player,"You don't have enough inventory space.")
                    closeInterface(player)
                    removeAttribute(player, "exchange")
                    return@on true
                }
                if (if (exchange == ElnockExchange.IMPLING_JAR) player.inventory.remove(ElnockExchange.getItem(player)) else player.inventory.remove(*exchange.required)) {
                    closeInterface(player)
                    removeAttribute(player, "exchange")
                    player.inventory.add(exchange.reward, player)
                }
                return@on true
            }

            exchange = ElnockExchange.forButton(buttonID)
            if (exchange != null) {
                setAttribute(player, "exchange", exchange)
                setVarp(player, 1018, exchange.configValue)
            }
            return@on true
        }
    }
}