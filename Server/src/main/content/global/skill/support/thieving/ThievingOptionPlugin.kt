package content.global.skill.support.thieving

import content.global.skill.support.thieving.data.Stall
import core.api.lockInteractions
import core.cache.def.impl.SceneryDefinition
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * @author Vexia 22/10/2013
 */
@Initializable
class ThievingOptionPlugin : OptionHandler() {

    override fun newInstance(arg: Any?): Plugin<Any?> {
        SceneryDefinition.setOptionHandler("steal-from", this)
        SceneryDefinition.setOptionHandler("steal from", this)
        SceneryDefinition.setOptionHandler("steal", this)
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        when (option) {
            "steal-from", "steal from", "steal" -> {
                player.pulseManager.run(ThievingStallPulse(player, node as Scenery, Stall.forScenery(node)))
                lockInteractions(player,6)
            }
        }
        return true
    }
}
