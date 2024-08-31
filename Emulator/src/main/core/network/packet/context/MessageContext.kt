package core.network.packet.context

import core.game.node.entity.player.Player
import core.game.node.entity.player.info.Rights
import core.network.packet.Context

/**
 * Packet context for communication message packets.
 */
class MessageContext : Context {

    private val player: Player
    val other: String
    val chatIcon: Int
    val opcode: Int
    val message: String

    constructor(player: Player, other: Player, opcode: Int, message: String) {
        this.player = player
        this.other = other.name
        this.chatIcon = Rights.getChatIcon(other)
        this.opcode = opcode
        this.message = message
    }

    constructor(player: Player, other: String, chatIcon: Int, opcode: Int, message: String) {
        this.player = player
        this.other = other
        this.chatIcon = chatIcon
        this.opcode = opcode
        this.message = message
    }

    override fun getPlayer(): Player = player

    companion object {
        const val SEND_MESSAGE: Int = 71
        const val RECEIVE_MESSAGE: Int = 0
        const val CLAN_MESSAGE: Int = 54
    }
}
