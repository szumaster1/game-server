package core.network.packet.context

import core.game.node.entity.player.Player
import core.network.packet.Context

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
