package content.global.skill.support.agility.shortcuts

import content.global.skill.support.agility.AgilityHandler
import content.global.skill.support.agility.AgilityShortcut
import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery
import core.game.world.map.Direction
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Represents the Bar squeeze shortcut interaction.
 */
@Initializable
class BarSqueezeShortcut : AgilityShortcut {

    constructor() : super(intArrayOf(9334, 9337), 66, 1.0, "squeeze-through")

    constructor(ids: IntArray, level: Int, exp: Double, option: String) : super(ids, level, exp, option)

    override fun newInstance(arg: Any?): Plugin<Any> {
        configure(BarSqueezeShortcut(intArrayOf(2186), 1, 0.0, "squeeze-through"))
        return super.newInstance(arg)
    }

    override fun run(player: Player, obj: Scenery, option: String, failed: Boolean) {
        var dir = Direction.getLogicalDirection(player.location, obj.location)
        var start = player.location
        when {
            obj.id == 9334 && dir == Direction.NORTH -> {
                dir = Direction.WEST
                start = Location.create(3424, 3476, 0)
            }
            obj.id == 9337 && dir == Direction.NORTH -> {
                dir = Direction.SOUTH
                if (player.location.y < obj.location.y) {
                    dir = Direction.NORTH
                }
            }
            obj.id == 2186 && player.location.y >= 3161 -> {
                dir = Direction.SOUTH
            }
        }
        AgilityHandler.forceWalk(player, -1, start, player.location.transform(dir, 1), Animation.create(2240), 10, 0.0, null)
    }

    override fun checkRequirements(player: Player): Boolean {
        if (!player.questRepository.isComplete("Priest in Peril") && !(player.location.y >= 3159 && player.location.y <= 3161)) {
            player.dialogueInterpreter.sendDialogue("You need to have completed Priest in Peril in order to do this.")
            return false
        }
        return super.checkRequirements(player)
    }
}
