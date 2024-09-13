package core.net.packet.context

import core.game.node.entity.player.Player
import core.net.packet.Context

/**
 * Represents a camera context.
 */
class CameraContext(
    private val player: Player, // Represents the player associated with the camera context.
    val type: CameraType, // Represents the type of camera context.
    val x: Int, // Represents the x-coordinate of the camera.
    val y: Int, // Represents the y-coordinate of the camera.
    val height: Int, // Represents the height of the camera.
    val speed: Int, // Represents the speed of the camera movement.
    val zoomSpeed: Int // Represents the zoom speed of the camera.
) : Context {

    enum class CameraType(private val opcode: Int) { // Enum class defining different camera types.
        POSITION(154), // Represents the position camera type.
        ROTATION(125), // Represents the rotation camera type.
        SET(187), // Represents the set camera type.
        SHAKE(27), // Represents the shake camera type.
        RESET(24); // Represents the reset camera type.

        fun opcode(): Int { // Returns the opcode associated with the camera type.
            return opcode
        }
    }

    fun transform(player: Player, x: Int, y: Int): CameraContext { // Transforms the camera context by adjusting the x and y coordinates.
        return CameraContext(player, type, this.x + x, this.y + y, height, speed, zoomSpeed)
    }

    fun transform(heightOffset: Int): CameraContext { // Transforms the camera context by adjusting the height.
        return CameraContext(player, type, x, y, height + heightOffset, speed, zoomSpeed)
    }

    override fun getPlayer(): Player = player // Returns the player associated with the camera context.
}
