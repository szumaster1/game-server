package core.game.node.entity.player.link.request

import core.game.node.entity.player.Player
import core.game.node.entity.player.link.request.assist.AssistSession
import core.game.node.entity.player.link.request.trade.TradeModule

/**
 * Request type
 *
 * @param message
 * @param requestMessage
 * @param module
 * @constructor Request type
 */
open class RequestType(
    val message: String,
    val requestMessage: String,
    val module: RequestModule
) {
    /**
     * Can request
     *
     * @param player
     * @param target
     * @return
     *//*
     * Checks if the request can be made.
     */
    open fun canRequest(player: Player?, target: Player?): Boolean {
        return true
    }

    /**
     * Get request message
     *
     * @param target
     * @return
     *//*
     * Gets the requesting message formated with the targets name.
     */
    fun getRequestMessage(target: Player): String {
        return target.username + requestMessage
    }

    companion object {
        /*
         * The trade request type.
         */
        val TRADE = RequestType("Sending a trade offer...", ":tradereq:", TradeModule(null, null))

        /*
         * The assist request type.
         */
        val ASSIST = RequestType("Sending assistance request...", ":assistreq:", AssistSession())
    }
}