package content.region.misthalin.wizardstower.handlers.rcguild

import core.api.MapArea
import core.game.world.map.zone.ZoneBorders
import core.game.world.map.zone.ZoneRestriction

class RunecraftingGuildArea : MapArea {

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
