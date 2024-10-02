package content.minigame.blastfurnace

import core.game.world.map.Location

/**
 * Blast utils.
 */
object BlastUtils {
    val ENTRANCE_LOC = Location(1940, 4958, 0)
    val EXIT_LOC = Location(2931, 10197, 0)
    val STAIRLOC_ENTRANCE = Location(2930, 10196)
    val STAIRLOC_EXIT = Location(1939, 4956)
    const val STAIR_ENTRANCE_ID = 9084
    const val STAIR_EXIT_ID = 9138
    const val BELT = 9100
    const val PEDALS = 9097
    val STOVE = intArrayOf(BFSceneryController.STOVE_COLD, BFSceneryController.STOVE_WARM, BFSceneryController.STOVE_HOT)
    const val PUMP = 9090
    const val COKE = 9088
    const val TEMP_GAUGE = 9089
    const val SINK = 9143
    val DISPENSER = intArrayOf(9093, 9094, 9095, 9096)
    const val SMITH_REQ = 60
    const val ENTRANCE_FEE = 2500
    const val FEE_ENTRANCE_DURATION = 1000
    const val COAL_LIMIT = 226
    const val ORE_LIMIT = 28
    const val BAR_LIMIT = 28
    const val COKE_LIMIT = 15
}