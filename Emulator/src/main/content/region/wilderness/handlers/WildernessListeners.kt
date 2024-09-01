package content.region.wilderness.handlers

import content.global.handlers.iface.WarningInterfaceListener
import core.api.openInterface
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.MovementPulse
import core.game.node.Node
import core.game.node.entity.impl.PulseType
import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery
import cfg.consts.Components
import core.api.lock
import core.api.lockInteractions

/**
 * Represents the Wilderness listeners.
 */
class WildernessListeners : InteractionListener {

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
     * @param player Represents the player interacting with the ditch.
     * @param node Represents the specific node associated with the ditch.
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
        WarningInterfaceListener.handleDitch(player)
    }

    companion object {
        const val WILDERNESS_DITCH = 23271
    }
}
