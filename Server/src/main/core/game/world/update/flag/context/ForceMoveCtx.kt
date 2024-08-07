package core.game.world.update.flag.context

import core.game.world.map.Direction
import core.game.world.map.Location

/**
 * Force move ctx
 *
 * @property start
 * @property dest
 * @property startArrive
 * @property destArrive
 * @property direction
 * @constructor Force move ctx
 */
data class ForceMoveCtx(
    val start: Location,
    val dest: Location,
    val startArrive: Int,
    val destArrive: Int,
    val direction: Direction
)
