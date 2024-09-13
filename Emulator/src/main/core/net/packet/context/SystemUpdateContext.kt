package core.net.packet.context

import core.game.node.entity.player.Player
import core.net.packet.Context

/**
 * Represents a system update.
 */
class SystemUpdateContext(
    private val player: Player,
    time: Int
) : Context {

    var time: Int = 0

    init {
        this.time = time
    }

    override fun getPlayer(): Player = player
}
