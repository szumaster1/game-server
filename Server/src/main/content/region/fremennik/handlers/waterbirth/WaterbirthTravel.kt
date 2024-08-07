package content.region.fremennik.handlers.waterbirth

import core.api.*
import core.api.consts.Components
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.map.Location

/**
 * Waterbirth travel.
 */
object WaterbirthTravel {

    @JvmStatic
    fun sail(player: Player, destination: TravelDestination) {
        lock(player, destination.shipAnim + 3)
        lockInteractions(player, destination.shipAnim)
        sendMessage(player, "You board the longship...")
        openOverlay(player, Components.FADE_TO_BLACK_115)
        openInterface(player, Components.MISC_SHIPJOURNEY_224)
        animateInterface(player, Components.MISC_SHIPJOURNEY_224, 7, destination.shipAnim)
        teleport(player, destination.destinationLoc)
        val animDuration = animationDuration(getAnimation(destination.shipAnim))
        submitWorldPulse(object : Pulse(animDuration + 3) {
            override fun pulse(): Boolean {
                sendMessage(player, "The ship arrives at ${destination.destName}.")
                closeInterface(player)
                closeOverlay(player)
                unlock(player)
                return true
            }
        })
        return
    }
}

enum class TravelDestination(val destName: String, val destinationLoc: Location, val shipAnim: Int) {
    RELLEKA_TO_MISCELLANIA(
        destName = "Miscellania",
        destinationLoc = Location.create(2581, 3845, 0),
        shipAnim = 1372
    ),
    MISCELLANIA_TO_RELLEKKA(
        destName = "Rellekka",
        destinationLoc = Location.create(2629, 3693, 0),
        shipAnim = 1373
    ),
    RELLEKKA_TO_JATIZSO(
        destName = "Jatizso",
        destinationLoc = Location.create(2421, 3781, 0),
        shipAnim = 5766
    ),
    JATIZSO_TO_RELLEKKA(
        destName = "Rellekka",
        destinationLoc = Location.create(2644, 3710, 0),
        shipAnim = 5767
    ),
    RELLEKKA_TO_NEITIZNOT(
        destName = "Neitiznot",
        destinationLoc = Location.create(2310, 3782, 0),
        shipAnim = 5764
    ),
    NEITIZNOT_TO_RELLEKKA(
        destName = "Rellekka",
        destinationLoc = Location.create(2644, 3710, 0),
        shipAnim = 5765
    ),
    WATERBIRTH_TO_RELLEKKA(
        destName = "Rellekka",
        destinationLoc = Location.create(2620, 3685, 0),
        shipAnim = 2345
    ),
    RELLEKKA_TO_WATERBIRTH(
        destName = "Waterbirth Island",
        destinationLoc = Location.create(2544, 3759, 0),
        shipAnim = 2344
    );
}
