package core.game.world.map.zone.impl

import org.rs.consts.Components
import org.rs.consts.Regions
import core.api.closeOverlay
import core.api.getRegionBorders
import core.api.inBorders
import core.api.openOverlay
import core.game.node.entity.Entity
import core.game.node.entity.player.Player
import core.game.world.map.zone.MapZone
import core.game.world.map.zone.ZoneBorders

/**
 * Represents the Snow zone area.
 */
class SnowZone : MapZone("snow", true) {

    private val BASE_ZONE = ZoneBorders(2728, 3716, 2732, 3731)
    private val MIDDLE_ZONE = ZoneBorders(2733, 3716, 2738, 3730)
    private val WINDSWEPT_AREA = ZoneBorders(2751, 3740, 2735, 3712)
    private val TROLLWEIS_MOUNTAIN_0 = getRegionBorders(Regions.TROLLWEISS_MOUNTAIN_11066)
    private val TROLLWEIS_MOUNTAIN_1 = getRegionBorders(Regions.TROLLWEISS_MOUNTAIN_11067)
    private val TROLLWEIS_MOUNTAIN_2 = getRegionBorders(Regions.TROLLWEISS_MOUNTAIN_11068)

    override fun configure() {
        register(BASE_ZONE)
        register(MIDDLE_ZONE)
        register(WINDSWEPT_AREA)
        register(TROLLWEIS_MOUNTAIN_0)
        register(TROLLWEIS_MOUNTAIN_1)
        register(TROLLWEIS_MOUNTAIN_2)
    }

    override fun enter(e: Entity): Boolean {
        if (e is Player) {
            openSnowOverlay(e)
        }
        return true
    }

    override fun leave(e: Entity, logout: Boolean): Boolean {
        if (!logout && e is Player) {
            closeOverlay(e)
        }
        return true
    }

    private fun openSnowOverlay(player: Player) {
        val overlayComponent = when {
            inBorders(player, BASE_ZONE) -> Components.BLUE_OVERLAY_483
            inBorders(player, MIDDLE_ZONE) -> Components.SNOW_A_OVERLAY_482
            else -> Components.SNOW_B_OVERLAY_481
        }
        openOverlay(player, overlayComponent)
    }

}