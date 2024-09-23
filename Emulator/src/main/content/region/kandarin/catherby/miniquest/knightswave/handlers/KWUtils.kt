package content.region.kandarin.catherby.miniquest.knightswave.handlers

import org.rs.consts.Scenery

object KWUtils {
    val DOORS = intArrayOf(Scenery.LARGE_DOOR_25594, Scenery.LARGE_DOOR_25595)
    var tier = 0
    const val KW_SPAWN = "knights-training:spawn"
    const val KW_TIER = "knights-training:wave"
    const val KW_BEGIN = "/save:knights-training:complete-tutorial"
    const val KW_COMPLETE = "/save:knights-training:complete"
}