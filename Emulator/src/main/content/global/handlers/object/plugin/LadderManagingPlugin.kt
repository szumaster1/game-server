package content.global.handlers.`object`.plugin

import core.cache.def.impl.SceneryDefinition
import core.game.global.action.ClimbActionHandler.climbLadder
import core.game.global.action.ClimbActionHandler.getDestination
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery
import core.game.world.map.Location
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Ladder managing plugin.
 */
@Initializable
class LadderManagingPlugin : OptionHandler() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        SceneryDefinition.setOptionHandler("climb-up", this)
        SceneryDefinition.setOptionHandler("climb-down", this)
        SceneryDefinition.setOptionHandler("climb", this)
        SceneryDefinition.setOptionHandler("walk-up", this)
        SceneryDefinition.setOptionHandler("walk-down", this)
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        climbLadder(player, node as Scenery, option)
        return true
    }

    override fun getDestination(n: Node, `object`: Node): Location {
        return getDestination((`object` as Scenery))!!
    }
}
