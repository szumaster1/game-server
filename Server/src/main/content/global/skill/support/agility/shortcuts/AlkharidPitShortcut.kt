package content.global.skill.support.agility.shortcuts

import content.global.skill.support.agility.AgilityShortcut
import core.game.node.Node
import core.game.node.entity.impl.ForceMovement
import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery
import core.game.world.map.Direction
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable

/**
 * Al kharid pit shortcut.
 */
@Initializable
class AlKharidPitShortcut : AgilityShortcut(intArrayOf(9331, 9332), 38, 0.0, "climb") {

    companion object {
        private val ANIMATION = Animation(1148)
        private val SCALE = Animation(740)
    }

    override fun run(player: Player, scenery: Scenery, option: String, failed: Boolean) {
        when (scenery.id) {
            9331 -> ForceMovement.run(player, player.location, Location.create(3303, 3315, 0), ANIMATION, ANIMATION, Direction.EAST, 13).endAnimation = Animation.RESET
            9332 -> ForceMovement.run(player, player.location, Location.create(3307, 3315, 0), SCALE, SCALE, Direction.EAST, 13).endAnimation = Animation.RESET
        }
    }

    override fun getDestination(node: Node, n: Node): Location? {
        val scenery = n as Scenery
        return if (scenery.id == 9331) {
            scenery.location.transform(1, 0, 0)
        } else {
            null
        }
    }
}
