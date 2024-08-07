package content.region.wilderness.handlers

import core.cache.def.impl.SceneryDefinition
import core.game.global.action.ClimbActionHandler.climb
import core.game.global.action.ClimbActionHandler.climbLadder
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery
import core.game.world.map.Location
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Lava maze plugin.
 */
@Initializable
class LavaMazePlugin : OptionHandler() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        SceneryDefinition.forId(1767).handlers["option:climb-down"] = this
        SceneryDefinition.forId(1768).handlers["option:climb-up"] = this
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        when (node.id) {
            1767 -> {
                if (node.location.x == 3069) {
                    climb(player, null, Location.create(3017, 10248, 0))
                    return true
                }
                climbLadder(player, node as Scenery, option)
                return true
            }

            1768 -> {
                if (node.location.x == 3017) {
                    climb(player, null, Location.create(3069, 3857, 0))
                    return true
                }
                climbLadder(player, node as Scenery, option)
                return true
            }
        }
        return true
    }
}