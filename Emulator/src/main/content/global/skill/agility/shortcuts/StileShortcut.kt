package content.global.skill.agility.shortcuts

import content.global.skill.agility.AgilityHandler
import core.api.*
import core.api.utils.Vector
import core.game.activity.ActivityManager
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.entity.player.Player
import core.game.world.map.Direction
import core.game.world.map.Location
import org.rs.consts.Animations

/**
 * Represents the Stile shortcut.
 */
class StileShortcut : InteractionListener {

    val ids = intArrayOf(993, 3730, 7527, 12982, 19222, 22302, 29460, 33842, 34776, 39508, 39509, 39510)
    private val FALCONRY_STILE = 19222

    override fun defineListeners() {
        on(ids, IntType.SCENERY, "climb-over") { p, n ->
            val direction = Vector.betweenLocs(p.location, n.location).toDirection()
            val startLoc = p.location.transform(direction, 1)
            val endLoc = p.location.transform(direction, 2)

            p.walkingQueue.reset()

            /*
             * The basic stile cannot be failed.
             * if (AgilityHandler.hasFailed(p, 1, 0.1)) {
             *     animate(p, 1106)
             * }
             */

            p.walkingQueue.addPath(startLoc.x, startLoc.y)
            forceMove(p, startLoc, endLoc, 0, animationCycles(Animations.WALKING_OVER_STILE_OBSTACLE_10980), direction, Animations.WALKING_OVER_STILE_OBSTACLE_10980)
            queueScript(p, 5, QueueStrength.SOFT) { _ ->
                val end = endLoc.transform(direction, 1)
                p.walkingQueue.reset()
                p.walkingQueue.addPath(end.x, end.y)

                if (n.id == FALCONRY_STILE)
                    handleFalconry(p, endLoc)
                return@queueScript stopExecuting(p)
            }
            return@on true
        }

        setDest(IntType.SCENERY, ids, "climb-over") { e, n ->
            return@setDest getInteractLocation(e.location, n.location, getOrientation(n.direction))
        }
    }


    companion object {
        fun getInteractLocation(pLoc: Location, sLoc: Location, orientation: Orientation): Location {
            when (orientation) {
                Orientation.HORIZONTAL -> {
                    if (pLoc.x <= sLoc.x) return sLoc.transform(-1, 0, 0)
                    else return sLoc.transform(2, 0, 0)
                }

                Orientation.VERTICAL -> {
                    if (pLoc.y <= sLoc.y) return sLoc.transform(0, -1, 0)
                    else return sLoc.transform(0, 2, 0)
                }
            }
        }

        fun getOrientation(rotation: Direction): Orientation {
            when (rotation) {
                Direction.EAST, Direction.WEST -> return Orientation.HORIZONTAL
                else -> return Orientation.VERTICAL
            }
        }

        fun handleFalconry(p: Player, endLoc: Location) {
            if (endLoc.y == 3619)
                ActivityManager.start(p, "falconry", false)
            else
                ActivityManager.getActivity("falconry").leave(p, false)
        }
    }

    enum class Orientation {
        HORIZONTAL,
        VERTICAL
    }
}
