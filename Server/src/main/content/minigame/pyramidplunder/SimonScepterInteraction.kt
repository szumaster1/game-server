package content.minigame.pyramidplunder

import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.game.node.entity.npc.NPC
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Simon scepter interaction.
 */
@Initializable
class SimonScepterInteraction : UseWithHandler(9044, 9046, 9048, 9050) {

    @Throws(Throwable::class)
    override fun newInstance(arg: Any?): Plugin<Any> {
        addHandler(3123, NPC_TYPE, this)
        //PluginManager.definePlugin(new SimonScepterInteraction());
        return this
    }

    override fun handle(event: NodeUsageEvent): Boolean {
        val player = event.player
        player.dialogueInterpreter.open(3123, event.usedWith as NPC, true, false, event.usedItem.id)
        return true
    }
}