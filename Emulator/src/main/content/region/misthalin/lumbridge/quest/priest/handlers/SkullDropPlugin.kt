package content.region.misthalin.lumbridge.quest.priest.handlers

import org.rs.consts.Items
import core.api.sendMessage
import core.cache.def.impl.ItemDefinition
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Represents the Skull drop plugin.
 */
@Initializable
class SkullDropPlugin : OptionHandler() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        ItemDefinition.forId(Items.SKULL_964).handlers["option:drop"] = this
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        sendMessage(player, "You can't drop this! Return it to the ghost.")
        return true
    }

    override fun isWalk(): Boolean {
        return false
    }
}
