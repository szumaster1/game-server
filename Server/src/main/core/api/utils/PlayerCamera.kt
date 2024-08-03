package core.api.utils

import core.game.node.entity.player.Player
import core.network.packet.PacketRepository
import core.network.packet.context.CameraContext
import core.network.packet.outgoing.CameraViewPacket

class PlayerCamera(val player: Player?) {
    var ctx: CameraContext? = null

    /*
     * Positions the camera to the given region-local coordinates.
     */
    fun setPosition(x: Int, y: Int, height: Int) {
        player ?: return
        ctx = CameraContext(player, CameraContext.CameraType.SET, x, y, height, 0, 0)
        PacketRepository.send(CameraViewPacket::class.java, ctx)
    }

    /*
     * Rotates the camera to face the given region-local coordinates?.
     */
    fun rotateTo(x: Int, y: Int, height: Int, speed: Int) {
        player ?: return
        ctx = CameraContext(player, CameraContext.CameraType.ROTATION, x, y, height, speed, 1)
        PacketRepository.send(CameraViewPacket::class.java, ctx)
    }

    /*
     * Rotates the camera by given region-local coordinates.
     */

    fun rotateBy(diffX: Int, diffY: Int, diffHeight: Int, speed: Int) {
        player ?: return
        ctx ?: return
        ctx = CameraContext(
            player,
            CameraContext.CameraType.ROTATION,
            ctx!!.x + diffX,
            ctx!!.y + diffY,
            ctx!!.height + diffHeight,
            speed,
            1
        )
        PacketRepository.send(CameraViewPacket::class.java, ctx)
    }

    /*
     * Moves the camera to the given region-local coordinates.
     */
    fun panTo(x: Int, y: Int, height: Int, speed: Int) {
        player ?: return
        ctx = CameraContext(player, CameraContext.CameraType.POSITION, x, y, height, speed, 1)
        PacketRepository.send(CameraViewPacket::class.java, ctx)
    }

    /*
     * Camera movement type (0-4) Frequency (0-255).
     */
    fun shake(cameraType: Int, jitter: Int, amplitude: Int, frequency: Int, speed: Int) {
        player ?: return
        ctx = CameraContext(player, CameraContext.CameraType.SHAKE, cameraType, jitter, amplitude, frequency, speed)
        PacketRepository.send(CameraViewPacket::class.java, ctx)
    }

    /*
     * Resets the current camera position.
     */
    fun reset() {
        player ?: return
        ctx = CameraContext(player, CameraContext.CameraType.RESET, -1, -1, -1, -1, -1)
        PacketRepository.send(CameraViewPacket::class.java, ctx)
    }
}
