package core.network.packet.context

import core.game.node.entity.player.Player
import core.network.packet.Context

/**
 * The game message packet context.
 * @author Emperor
 */
class GameMessageContext(
    private val player: Player,
    val message: String
) : Context {

    override fun getPlayer(): Player = player
}
