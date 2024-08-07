package core.network.packet.context

import core.game.node.entity.player.Player
import core.network.packet.Context

/**
 * The Integer context.
 */
class IntegerContext(private val player: Player, integer: Int) : Context {
    var integer: Int = 0

    init {
        this.integer = integer
    }

    override fun getPlayer(): Player = player
}
