package core.game.world.update.flag.context

import core.game.world.map.Direction
import core.game.world.map.Location

/**
 * Force move ctx
 *
 * @param start
 * @param dest
 * @param startArrive
 * @param destArrive
 * @param direction
 * @constructor Force move ctx
 */
data class ForceMoveCtx(
    val start: Location,
    val dest: Location,
    val startArrive: Int,
    val destArrive: Int,
    val direction: Direction
)
