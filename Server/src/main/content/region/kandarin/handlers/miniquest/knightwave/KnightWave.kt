package content.region.kandarin.handlers.miniquest.knightwave

import cfg.consts.Scenery

object KnightWave {
    val DOORS = intArrayOf(Scenery.LARGE_DOOR_25594, Scenery.LARGE_DOOR_25595)
    var tier = 0
    const val KW_TIER = "/save:kw-miniquest:wave-"
    const val KW_BEGIN = "/save:kw-miniquest:learn-basics"
    const val KW_COMPLETE = "/save:kw-miniquest:completed"
}