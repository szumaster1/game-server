package core.network.packet.context

import core.game.node.entity.player.Player
import core.network.packet.Context

/**
 * Represents the set walk-to option context.
 * @author Emperor
 */
class WalkOptionContext(
    private val player: Player,
    val option: String
) : Context {

    override fun getPlayer(): Player = player
}
