package core.game.node.entity.player.link.request

import core.game.node.entity.player.Player
import core.game.node.entity.player.link.request.assist.AssistSession
import core.game.node.entity.player.link.request.trade.TradeModule

/**
 * Represents a request type.
 * @author Vexia, Emperor, dginovker
 *
 * @param message The message to be displayed for the request.
 * @param requestMessage The formatted request message.
 * @param module The module associated with the request.
 * @constructor [RequestType]
 */
open class RequestType(
    val message: String,
    val requestMessage: String,
    val module: RequestModule
) {
    /**
     * Checks if the request can be made.
     *
     * @param player The player making the request.
     * @param target The player receiving the request.
     * @return True if the request can be made, otherwise false.
     */
    open fun canRequest(player: Player?, target: Player?): Boolean {
        return true
    }

    /**
     * Gets the requesting message formatted with the target's name.
     *
     * @param target The player who is the target of the request.
     * @return The formatted request message including the target's username.
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
