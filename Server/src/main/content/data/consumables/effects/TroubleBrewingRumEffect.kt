package content.data.consumables.effects

import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.map.Location

class TroubleBrewingRumEffect(private val forceChatMessage: String) : ConsumableEffect() {
    override fun activate(p: Player) {
        val teleportation: Pulse = object : Pulse(6) {
            override fun pulse(): Boolean {
                p.teleport(TROUBLE_BREWING_MINIGAME)
                return true
            }
        }
        val mainPulse: Pulse = object : Pulse(4) {
            override fun pulse(): Boolean {
                p.sendChat(forceChatMessage)
                p.pulseManager.run(teleportation)
                return true
            }
        }
        p.pulseManager.run(mainPulse)
    }

    companion object {
        private val TROUBLE_BREWING_MINIGAME = Location(3813, 3022)
    }

}
