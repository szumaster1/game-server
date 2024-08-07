package content.data.consumables.effects

import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.map.Location

/**
 * Trouble brewing rum effect.
 *
 * @property forceChatMessage The message to be displayed when the effect is activated.
 * @constructor Initializes the Trouble brewing rum effect with a force chat message.
 */
class TroubleBrewingRumEffect(val forceChatMessage: String) : ConsumableEffect() {

    override fun activate(player: Player) {
        // Creating a Pulse for teleportation
        val teleportation: Pulse = object : Pulse(6) {
            override fun pulse(): Boolean {
                player.teleport(TROUBLE_BREWING_MINIGAME)
                return true
            }
        }
        // Creating a main Pulse for chat message and teleportation
        val mainPulse: Pulse = object : Pulse(4) {
            override fun pulse(): Boolean {
                player.sendChat(forceChatMessage)
                player.pulseManager.run(teleportation)
                return true
            }
        }
        // Running the main Pulse
        player.pulseManager.run(mainPulse)
    }

    companion object {
        private val TROUBLE_BREWING_MINIGAME = Location(3813, 3022)
    }

}
