package content.global.skill.combat.summoning.familiar

import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.Direction
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.net.packet.PacketRepository
import core.net.packet.context.CameraContext
import core.net.packet.context.CameraContext.CameraType
import core.net.packet.outgoing.CameraViewPacket
import java.util.*

/**
 * Remote viewer.
 */
class RemoteViewer
/**
 * Instantiates a new Remote viewer.
 *
 * @param player    the player
 * @param familiar  the familiar
 * @param animation the animation
 * @param type      the type
 */(
    /**
     * Gets player.
     *
     * @return the player
     */
    val player: Player,
    /**
     * Gets familiar.
     *
     * @return the familiar
     */
    val familiar: Familiar,
    /**
     * Gets animation.
     *
     * @return the animation
     */
    val animation: Animation,
    /**
     * Gets type.
     *
     * @return the type
     */
    val type: ViewType
) {
    /**
     * Start viewing.
     */
    fun startViewing() {
        player.lock()
        familiar.animate(animation)
        player.packetDispatch.sendMessage(
            "You send the " + familiar.name.lowercase(Locale.getDefault()) + " to fly " + (if (type == ViewType.STRAIGHT_UP) "directly up" else "to the " + type.name.lowercase(
                Locale.getDefault()
            )) + "..."
        )
        Pulser.submit(object : Pulse(5) {
            override fun pulse(): Boolean {
                view()
                return true
            }
        })
    }

    private fun view() {
        if (!canView()) {
            return
        }
        sendCamera(type.xOffset, type.yOffset, type.xRot, type.yRot)
        Pulser.submit(object : Pulse(13) {
            override fun pulse(): Boolean {
                reset()
                return true
            }
        })
    }

    private fun canView(): Boolean {
        player.packetDispatch.sendMessage("There seems to be an obstruction in the direction; the familiar cannot fly there")
        return familiar.isActive
    }

    private fun reset() {
        familiar.call()
        player.unlock()
        PacketRepository.send(
            CameraViewPacket::class.java,
            CameraContext(player, CameraType.RESET, 0, 0, HEIGHT, 1, 100)
        )
    }

    private fun sendCamera(xOffset: Int, yOffset: Int, xRot: Int, yRot: Int) {
        val location = type.getLocationTransform(player)
        val x = location.x + xOffset
        val y = location.y + yOffset
        PacketRepository.send(
            CameraViewPacket::class.java,
            CameraContext(player, CameraType.POSITION, x, y, HEIGHT, 1, 100)
        )
        PacketRepository.send(
            CameraViewPacket::class.java,
            CameraContext(player, CameraType.ROTATION, x + xRot, y + yRot, HEIGHT, 1, 90)
        )
    }

    /**
     * The enum View type.
     */
    enum class ViewType(
        /**
         * Gets direction.
         *
         * @return the direction
         */
        val direction: Direction?,
        /**
         * Get data int [ ].
         *
         * @return the int [ ]
         */
        vararg val data: Int
    ) {
        /**
         * North view type.
         */
        NORTH(Direction.NORTH, 0, 0, 0, 0),

        /**
         * East view type.
         */
        EAST(Direction.WEST, 0, 0, 0, 0),

        /**
         * South view type.
         */
        SOUTH(Direction.SOUTH, 0, 0, 0, 0),

        /**
         * West view type.
         */
        WEST(Direction.EAST, 0, 0, 0, 0),

        /**
         * Straight up view type.
         */
        STRAIGHT_UP(null, 0, 0, 0, 0);

        /**
         * Gets location transform.
         *
         * @param player the player
         * @return the location transform
         */
        fun getLocationTransform(player: Player): Location {
            if (this == STRAIGHT_UP) {
                return player.location
            }
            return player.location.transform(direction, 10)
        }

        val xOffset: Int
            /**
             * Gets x offset.
             *
             * @return the x offset
             */
            get() = data[0]

        val yOffset: Int
            /**
             * Gets y offset.
             *
             * @return the y offset
             */
            get() = data[1]

        val xRot: Int
            /**
             * Gets x rot.
             *
             * @return the x rot
             */
            get() = data[2]

        val yRot: Int
            /**
             * Gets y rot.
             *
             * @return the y rot
             */
            get() = data[3]
    }

    companion object {
        /**
         * The constant DIALOGUE_NAME.
         */
        const val DIALOGUE_NAME: String = "remote-view"

        /**
         * The constant HEIGHT.
         */
        const val HEIGHT: Int = 1000

        /**
         * Create remote viewer.
         *
         * @param player    the player
         * @param familiar  the familiar
         * @param animation the animation
         * @param type      the type
         * @return the remote viewer
         */
        fun create(player: Player, familiar: Familiar, animation: Animation, type: ViewType): RemoteViewer {
            return RemoteViewer(player, familiar, animation, type)
        }

        /**
         * Open dialogue.
         *
         * @param player   the player
         * @param familiar the familiar
         */
        fun openDialogue(player: Player, familiar: Familiar?) {
            player.dialogueInterpreter.open(DIALOGUE_NAME, familiar)
        }
    }
}
