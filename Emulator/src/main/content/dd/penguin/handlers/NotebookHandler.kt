package content.dd.penguin.handlers

import cfg.consts.Items
import core.api.getAttribute
import core.api.sendDialogue
import core.cache.def.impl.ItemDefinition
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.plugin.Plugin

/**
 * Handles the notebook interacts.
 */
class NotebookHandler : OptionHandler() {

    // Handles the action when a player interacts with a notebook.
    override fun handle(player: Player?, node: Node?, option: String?): Boolean {
        val total = getAttribute(player!!, "phns:points", 0)
        sendDialogue(player, "Total points: $total")
        return true
    }

    // Represents a new instance of the NotebookHandler plugin.
    override fun newInstance(arg: Any?): Plugin<Any> {
        // Assigns the read option to the NotebookHandler for the spy notebook item.
        ItemDefinition.forId(Items.SPY_NOTEBOOK_13732).handlers["option:read"] = this
        return this
    }

}
