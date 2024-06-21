package content.region.wilderness.handlers

import content.global.interaction.iface.warning.WildernessWarningInterface
import core.api.openInterface
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.MovementPulse
import core.game.node.Node
import core.game.node.entity.impl.PulseType
import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery
import core.api.consts.Components

class WildernessListeners : InteractionListener {

    companion object {
        const val WILDERNESS_DITCH = 23271
    }

    override fun defineListeners() {
        on(WILDERNESS_DITCH, IntType.SCENERY, "cross") { player, node ->
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

    fun handleDitch(player: Player, node: Node) {
        player.faceLocation(node.location)
        val ditch = node as Scenery
        player.setAttribute("wildy_ditch", ditch)
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
        WildernessWarningInterface.handleDitch(player)
    }
}
