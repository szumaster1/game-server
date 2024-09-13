package core.net.packet.context

import core.game.node.entity.player.Player
import core.net.packet.Context

/**
 * The game message packet context.
 */
class GameMessageContext(
    private val player: Player,
    val message: String
) : Context {

    override fun getPlayer(): Player = player
}
