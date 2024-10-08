package content.region.kandarin.witchaven.fplatform

import core.api.*
import org.rs.consts.Components
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.map.Location

/**
 * Fishing platform.
 */
object FishingPlatform {

    @JvmStatic
    fun sail(player: Player, travel: Travel) {
        player.lock()
        submitWorldPulse(object : Pulse() {
            var counter = 0
            override fun pulse(): Boolean {
                when (counter++) {
                    0 -> openOverlay(player, Components.FADE_TO_BLACK_115)
                    3 -> teleport(player, travel.destinationLoc)
                    4 -> openInterface(player, Components.FADE_FROM_BLACK_170)
                    5 -> {
                        sendDialogue(player, "The boat arrives ${travel.destName}.")
                        closeInterface(player)
                        closeOverlay(player)
                        player.unlock()
                        return true
                    }
                }
                return false
            }
        })
    }

    enum class Travel(val destName: String, val destinationLoc: Location) {
        WITCHAVEN_TO_FISHING_PLATFORM("at the fishing platform", Location.create(2782, 3273, 0)),
        FISHING_PLATFORM_TO_WITCHAVEN("at Witchaven", Location.create(2719, 3301, 0)),
        FISHING_PLATFORM_TO_SMALL_ISLAND("on a small island", Location.create(2800, 3320, 0)),
        SMALL_ISLAND_TO_FISHING_PLATFORM("at the fishing platform", Location.create(2782, 3273, 0));
    }

}