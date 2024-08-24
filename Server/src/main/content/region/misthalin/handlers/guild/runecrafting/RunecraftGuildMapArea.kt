package content.region.misthalin.handlers.guild.runecrafting

import core.api.MapArea
import core.game.world.map.zone.ZoneBorders
import core.game.world.map.zone.ZoneRestriction

/**
 * Represents the Runecraft guild map area.
 */
class RunecraftGuildMapArea : MapArea {

    override fun defineAreaBorders(): Array<ZoneBorders> {
        return arrayOf(ZoneBorders.forRegion(6741))
    }

    override fun getRestrictions(): Array<ZoneRestriction> {
        return arrayOf(
            ZoneRestriction.CANNON,
            ZoneRestriction.RANDOM_EVENTS,
            ZoneRestriction.GRAVES,
            ZoneRestriction.FIRES
        )
    }

}
