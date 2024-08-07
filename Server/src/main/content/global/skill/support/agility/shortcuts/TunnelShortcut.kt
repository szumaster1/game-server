package content.global.skill.support.agility.shortcuts

import content.global.skill.support.agility.AgilityShortcut
import core.api.hasRequirement
import core.api.lock
import core.api.lockInteractions
import core.game.node.Node
import core.game.node.entity.impl.ForceMovement
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.scenery.Scenery
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Direction
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Tunnel shortcut.
 */
@Initializable
class TunnelShortcut : AgilityShortcut {

    private val CLIMB_DOWN = Animation.create(2589)
    private val CRAWL_THROUGH = Animation.create(2590)
    private val CLIMB_UP = Animation.create(2591)
    private var offset: Int

    constructor() : super(intArrayOf(), 0, 0.0) {
        offset = 0
    }

    constructor(ids: IntArray, level: Int, experience: Double, offset: Int, vararg options: String) : super(ids, level, experience, *options) {
        this.offset = offset
    }

    override fun newInstance(arg: Any?): Plugin<Any?> {
        configure(TunnelShortcut(intArrayOf(9309, 9310), 26, 0.0, 0, "climb-into"))
        configure(TunnelShortcut(intArrayOf(9302, 9301), 16, 0.0, 1, "climb-into", "climb-under"))
        configure(TunnelShortcut(intArrayOf(14922), 1, 0.0, 1, "enter"))
        return this
    }

    override fun run(player: Player, obj: Scenery, option: String, failed: Boolean) {
        if (obj.id == 14922) {
            if (!hasRequirement(player, "Swan Song")) {
                return
            }
        }
        player.lock(6)
        val objectLocation = obj.location
        val start = player.location
        val direction = Direction.getDirection(start, objectLocation)
        if (objectLocation.x == 2575) {
            offset = 1
        }
        lock(player, 7)
        lockInteractions(player, 7)
        ForceMovement.run(player, start, objectLocation, CLIMB_DOWN, 8)
        GameWorld.Pulser.submit(object : Pulse(1, player) {
            var count = 0
            override fun pulse(): Boolean {
                when (++count) {
                    2 -> {
                        player.animate(CRAWL_THROUGH)
                        player.properties.teleportLocation = start.transform(direction, 2 + offset)
                    }

                    5 -> ForceMovement.run(player, player.location, start.transform(direction, 4 + offset), CLIMB_UP, 19)

                    6 -> {
                        player.animate(ForceMovement.WALK_ANIMATION)
                        if ((obj.id == 9309 || obj.id == 9310) && !player.achievementDiaryManager.getDiary(DiaryType.FALADOR)!!.isComplete(1, 1)) {
                            player.achievementDiaryManager.getDiary(DiaryType.FALADOR)!!.updateTask(player, 1, 1, true)
                        }
                        return true
                    }
                }
                return false
            }
        })
    }

    override fun getDestination(node: Node, n: Node): Location {
        if (n.id == 14922) {
            return n.location.transform(getObjectDirection(n.asScenery().direction), 1)
        }
        return getStart(n.location, n.direction)
    }

    private fun getStart(location: Location, dir: Direction): Location {
        return when (dir) {
            Direction.NORTH -> location
            Direction.SOUTH -> location
            Direction.EAST -> location.transform(0, if (location.y == 3111) 1 else -1, 0)
            Direction.WEST -> location.transform(0, 1, 0)
            else -> location
        }
    }
}