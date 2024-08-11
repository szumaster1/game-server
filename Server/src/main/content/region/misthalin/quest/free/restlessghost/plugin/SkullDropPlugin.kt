package content.region.misthalin.quest.free.restlessghost.plugin

import core.cache.def.impl.ItemDefinition
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Skull drop plugin.
 */
@Initializable
class SkullDropPlugin : OptionHandler() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        ItemDefinition.forId(964).handlers["option:drop"] = this
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        player.packetDispatch.sendMessage("You can't drop this! Return it to the ghost.")
        return true
    }

    override fun isWalk(): Boolean {
        return false
    }
}
