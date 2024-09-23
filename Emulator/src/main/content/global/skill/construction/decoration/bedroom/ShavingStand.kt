package content.global.skill.construction.decoration.bedroom

import core.api.animate
import core.api.lock
import core.api.openInterface
import core.api.submitIndividualPulse
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.system.task.Pulse

/**
 * Shaving stand listener.
 */
class ShavingStand : InteractionListener {

    private val shavingStandIDs = intArrayOf(13162, 13163, 13168)

    override fun defineListeners() {
        on(shavingStandIDs, IntType.SCENERY, "preen") { player, _ ->
            lock(player, 2)
            submitIndividualPulse(player, object : Pulse() {
                var counter = 0
                override fun pulse(): Boolean {
                    when (counter++) {
                        0 -> animate(player, 535)
                        2 -> {
                            openInterface(player, if (player.appearance.isMale) 596 else 592)
                            return true
                        }
                    }
                    return false
                }
            })
            return@on true
        }
    }
}