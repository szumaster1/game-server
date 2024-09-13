package core.net.packet.context

import core.game.node.entity.player.Player
import core.net.packet.Context

/**
 * Represents the set walk-to option context.
 */
class WalkOptionContext(
    private val player: Player,
    val option: String
) : Context {

    override fun getPlayer(): Player = player
}
