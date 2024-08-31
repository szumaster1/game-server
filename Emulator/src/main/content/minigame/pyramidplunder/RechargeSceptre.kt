package content.minigame.pyramidplunder

import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Represents the Recharge sceptre.
 */
@Initializable
class RechargeSceptre : UseWithHandler(9046, 9048, 9050) {

    /**
     * Represents the plugin.
     * Adds a handler for a specific NPC type.
     */
    @Throws(Throwable::class)
    override fun newInstance(arg: Any?): Plugin<Any> {
        addHandler(4476, NPC_TYPE, this)
        return this
    }

    /**
     * Handles the event when the sceptre is used.
     * Opens a dialogue for the player with a specific ID.
     */
    override fun handle(event: NodeUsageEvent): Boolean {
        val player = event.player
        player.dialogueInterpreter.open(999876, event.usedItem)
        return true
    }

}
