package content.minigame.fistofguthix

import core.game.interaction.DestinationFlag
import core.game.interaction.MovementPulse
import core.game.interaction.PluginInteraction
import core.game.interaction.PluginInteractionManager
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * FOG interaction handler.
 */
@Initializable
class FogInteractionHandler : PluginInteraction(30204, 30203) {

    override fun handle(player: Player?, node: Node?): Boolean {
        when (node?.id) {
            30204 -> player?.pulseManager?.run(ClimbPulse(player, node as Scenery)).also { return true }
            30203 -> player?.pulseManager?.run(ClimbPulse(player, node as Scenery)).also { return true }
        }
        return false
    }

    /**
     * Climb pulse for moving the player up a scenery object.
     */
    class ClimbPulse(val player: Player, val obj: Scenery) : MovementPulse(player, obj, DestinationFlag.OBJECT) {
        override fun pulse(): Boolean {
            player.faceLocation(obj.location)
            when (obj.id) {
                30204 -> core.game.global.action.ClimbActionHandler.climbLadder(player, obj, "climb-down")
                30203 -> core.game.global.action.ClimbActionHandler.climbLadder(player, obj, "climb-up")
            }
            return true
        }
    }

    override fun fireEvent(identifier: String?, vararg args: Any?): Any {
        return Unit
    }

    override fun newInstance(arg: Any?): Plugin<Any> {
        PluginInteractionManager.register(this, PluginInteractionManager.InteractionType.OBJECT)
        return this
    }

}