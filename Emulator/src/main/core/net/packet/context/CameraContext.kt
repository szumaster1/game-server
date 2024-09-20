package core.net.packet.context

import core.game.node.entity.player.Player
import core.net.packet.Context

/**
 * Represents a camera context.
 */
class CameraContext(
    private val player: Player,
    val type: CameraType,
    val x: Int,
    val y: Int,
    val height: Int,
    val speed: Int,
    val zoomSpeed: Int
) : Context {

    enum class CameraType(private val opcode: Int) {
        POSITION(154),
        ROTATION(125),
        SET(187),
        SHAKE(27),
        RESET(24);

        fun opcode(): Int {
            return opcode
        }
    }

    fun transform(player: Player, x: Int, y: Int): CameraContext {
        return CameraContext(player, type, this.x + x, this.y + y, height, speed, zoomSpeed)
    }

    fun transform(heightOffset: Int): CameraContext {
        return CameraContext(player, type, x, y, height + heightOffset, speed, zoomSpeed)
    }

    override fun getPlayer(): Player = player
}
