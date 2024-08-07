package core.network.packet.context

import core.game.node.entity.player.Player
import core.network.packet.Context

/**
 * The update scene graph packet context.
 */
open class SceneGraphContext(
    private val player: Player,
    val isLogin: Boolean
) : Context {

    override fun getPlayer(): Player = player
}
