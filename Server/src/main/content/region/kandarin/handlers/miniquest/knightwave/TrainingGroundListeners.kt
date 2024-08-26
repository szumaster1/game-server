package content.region.kandarin.handlers.miniquest.knightwave

import core.api.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.world.map.Location
import core.tools.RED

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
                sendMessage(player, RED + "This miniquest can only be done once.")
                return@on false
            }
            setAttribute(player, KnightWave.KW_TIER, 0)
            runTask(player, 1) {
                teleport(player, Location.create(2753, 3507, 2))
                sendMessageWithDelay(player, "Remember, only melee combat is allowed in here.", 1)
            }
            if(player.location.x >= 2752){
                teleport(player,Location.create(2751, 3507, 2))
            }
            return@on true
        }
    }
}