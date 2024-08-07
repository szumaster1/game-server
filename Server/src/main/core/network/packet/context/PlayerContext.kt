package core.network.packet.context

import core.game.node.entity.player.Player
import core.network.packet.Context

/**
 * The default packet context.
 */
class PlayerContext(private val player: Player) : Context {

    override fun getPlayer(): Player = player
}
