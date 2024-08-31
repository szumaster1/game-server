package core.game.world.update.flag.context

import core.game.world.map.Direction
import core.game.world.map.Location

/**
 * Force move ctx
 *
 * @param start The starting location for the force move
 * @param dest The destination location for the force move
 * @param startArrive The time or condition when the start location is reached
 * @param destArrive The time or condition when the destination location is reached
 * @param direction The direction of the force move
 * @constructor Force move ctx
 */
data class ForceMoveCtx(
    val start: Location, // The starting point of the move
    val dest: Location, // The endpoint of the move
    val startArrive: Int, // Indicates when the move starts
    val destArrive: Int, // Indicates when the move ends
    val direction: Direction // Specifies the direction of the move
)
