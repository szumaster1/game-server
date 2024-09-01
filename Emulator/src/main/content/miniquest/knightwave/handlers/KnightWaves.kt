package content.miniquest.knightwave.handlers

import cfg.consts.Scenery

object KnightWaves {
    val DOORS = intArrayOf(Scenery.LARGE_DOOR_25594, Scenery.LARGE_DOOR_25595)
    var tier = 0
    const val KW_SPAWN = "/save:kw-miniquest:spawn"
    const val KW_TIER = "/save:kw-miniquest:wave"
    const val KW_KC = "/save:kw-miniquest:kill"
    const val KW_BEGIN = "/save:kw-miniquest:tutorial"
    const val KW_COMPLETE = "/save:kw-miniquest:completed"
}