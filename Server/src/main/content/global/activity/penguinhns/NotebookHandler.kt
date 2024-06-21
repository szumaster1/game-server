package content.global.activity.penguinhns

import core.api.consts.Items
import core.api.getAttribute
import core.api.sendDialogue
import core.cache.def.impl.ItemDefinition
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.plugin.Plugin

class NotebookHandler : OptionHandler() {

    override fun handle(player: Player?, node: Node?, option: String?): Boolean {
        val total = getAttribute(player!!, "phns:points", 0)
        sendDialogue(player, "Total points: $total")
        return true
    }

    override fun newInstance(arg: Any?): Plugin<Any> {
        ItemDefinition.forId(Items.SPY_NOTEBOOK_13732).handlers["option:read"] = this
        return this
    }

}