import core.cache.def.impl.SceneryDefinition
import core.game.global.action.ClimbActionHandler
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery
import core.game.world.map.Location
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Represents the plugin used for managing ladders.
 */
@Initializable
class LadderManagingHandler : OptionHandler() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        val options = listOf("climb-up", "climb-down", "climb", "walk-up", "walk-down")

        options.forEach { option ->
            SceneryDefinition.setOptionHandler(option, this)
        }

        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        if (node !is Scenery) return false

        ClimbActionHandler.climbLadder(player, node, option)
        return true
    }

    override fun getDestination(n: Node, obj: Node): Location {
        if (obj !is Scenery) throw IllegalArgumentException("")

        return ClimbActionHandler.getDestination(obj)
    }
}