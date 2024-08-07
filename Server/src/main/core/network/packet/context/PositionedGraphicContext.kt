package core.network.packet.context

import core.game.node.entity.player.Player
import core.game.world.map.Location
import core.game.world.update.flag.context.Graphic
import core.network.packet.Context

/**
 * The packet context for the positioned graphic packet.
 */
class PositionedGraphicContext(
    private val player: Player,
    @JvmField val graphic: Graphic,
    @JvmField val location: Location,
    @JvmField var offsetX: Int,
    @JvmField var offsetY: Int
) : Context {

    @JvmField
    var sceneX: Int = location.getSceneX(player.playerFlags.lastSceneGraph)
    @JvmField
    var sceneY: Int = location.getSceneY(player.playerFlags.lastSceneGraph)

    override fun getPlayer(): Player = player
}
