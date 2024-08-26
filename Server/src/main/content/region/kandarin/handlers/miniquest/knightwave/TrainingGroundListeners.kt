package content.region.kandarin.handlers.miniquest.knightwave

import core.api.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.Location

/**
 * Training Ground listeners.
 *
 * Sources
 * 1. [Transcript of Squire Dialogue](https://www.youtube.com/watch?v=XBQ7muFJ2xM)
 * 2. [Knight Waves](https://runescape.wiki/w/Knight_Waves_training_ground?oldid=854304)
 */
class TrainingGroundListeners : InteractionListener {

    override fun defineListeners() {
        on(KnightWave.DOORS, IntType.SCENERY, "open") { player, _ ->
            if (!hasRequirement(player, "King's Ransom")) return@on true
            if (getAttribute(player, KnightWave.KW_COMPLETE, false)) {

            }
            setAttribute(player, KnightWave.KW_TIER, 0)
            startTrainingGroundPulse(player)
            return@on true
        }
    }

    private fun startTrainingGroundPulse(player: Player) {
        Pulser.submit(object : Pulse(1) {
            private var counter = 0

            override fun pulse(): Boolean {
                return when (counter++) {
                    1 -> {
                        teleport(player, Location.create(2753, 3507, 2))
                        sendMessage(player, "Remember, only melee combat is allowed in here.")
                        false
                    }

                    3 -> {
                        if (player.getExtension<Any?>(TrainingGroundSession::class.java) == null) {
                            TrainingGroundSession.create(player).start()
                        }
                        true
                    }

                    else -> false
                }
            }
        })
    }
}
