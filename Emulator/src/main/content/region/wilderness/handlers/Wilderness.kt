package content.region.wilderness.handlers

import core.api.*
import core.game.global.action.DoorActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.MovementPulse
import core.game.node.Node
import core.game.node.entity.impl.PulseType
import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery
import org.rs.consts.Components
import core.game.node.entity.impl.ForceMovement
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import org.rs.consts.Animations
import org.rs.consts.Sounds

/**
 * Represents the Wilderness listeners.
 */
class Wilderness : InteractionListener {

    override fun defineListeners() {

        /*
         * Handling the wilderness ditch "cross" interaction.
         */

        on(WILDERNESS_DITCH, IntType.SCENERY, "cross") { player, node ->
            lock(player, 3)
            lockInteractions(player, 3)
            if (player.location.getDistance(node.location) < 3) {
                handleDitch(player, node)
            } else {
                player.pulseManager.run(object : MovementPulse(player, node) {
                    override fun pulse(): Boolean {
                        handleDitch(player, node)
                        return true
                    }
                }, PulseType.STANDARD)
            }
            return@on true
        }
    }

    /**
     * Handle ditch scenery.
     *
     * @param [player] the player.
     * @param [node] the node.
     */
    fun handleDitch(player: Player, node: Node) {
        player.faceLocation(node.location)
        val ditch = node as Scenery
        player.setAttribute("wildy-ditch", ditch)
        if (!player.isArtificial) {
            if (ditch.rotation % 2 == 0) {
                if (player.location.y <= node.getLocation().y) {
                    openInterface(player, Components.WILDERNESS_WARNING_382)
                    return
                }
            } else {
                if (player.location.x > node.getLocation().x) {
                    openInterface(player, Components.WILDERNESS_WARNING_382)
                    return
                }
            }
        }
        if (player.getAttribute<Any?>("wildy-ditch") != null) {
            val ditch = player.getAttribute<Scenery>("wildy-ditch")
            removeAttribute(player, "wildy-ditch")
            val l = ditch.location
            val x = player.location.x
            val y = player.location.y
            if (ditch.rotation % 2 == 0) {
                if (y <= l.y) {
                    ForceMovement.run(player, Location.create(x, l.y - 1, 0), Location.create(x, l.y + 2, 0), Animation(
                        Animations.JUMP_OVER_OBSTACLE_6132), 20).endAnimation = null
                } else {
                    ForceMovement.run(player, Location.create(x, l.y + 2, 0), Location.create(x, l.y - 1, 0), Animation(
                        Animations.JUMP_OVER_OBSTACLE_6132), 20).endAnimation = null
                }
            } else {
                if (x > l.x) {
                    ForceMovement.run(player, Location.create(l.x + 2, y, 0), Location.create(l.x - 1, y, 0), Animation(
                        Animations.JUMP_OVER_OBSTACLE_6132), 20).endAnimation = null
                } else {
                    ForceMovement.run(player, Location.create(l.x - 1, y, 0), Location.create(l.x + 2, y, 0), Animation(
                        Animations.JUMP_OVER_OBSTACLE_6132), 20).endAnimation = null
                }
            }
            playAudio(player, Sounds.JUMP2_2462, 10)

        } else {
            if (player.getAttribute<Any?>("wildy-gate") != null) {
                val gate = player.getAttribute<Scenery>("wildy-gate")
                removeAttribute(player, "wildy-gate")
                DoorActionHandler.handleAutowalkDoor(player, gate)
            }
        }
    }

    companion object {
        const val WILDERNESS_DITCH = 23271
    }
}
