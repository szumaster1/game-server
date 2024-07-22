package content.global.plugins.item

import core.cache.def.impl.ItemDefinition
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.plugin.Plugin

class ConsumableOptionPlugin : OptionHandler() {

    override fun newInstance(arg: Any?): Plugin<Any?> {
        ItemDefinition.setOptionHandler("eat", this)
        ItemDefinition.setOptionHandler("drink", this)
        return this
    }

    var lastEaten = -1

    override fun handle(player: Player, node: Node, option: String): Boolean {
        return true
    }
}
