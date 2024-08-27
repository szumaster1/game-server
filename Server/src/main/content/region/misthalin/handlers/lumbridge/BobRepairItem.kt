package content.region.misthalin.handlers.lumbridge

import content.data.item.RepairItem.Companion.forId
import content.location.lumbridge.BobDialogue
import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.plugin.ClassScanner.definePlugin
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Represents the Bob repair item handler.
 */
@Initializable
class BobRepairItem : UseWithHandler() {

    override fun newInstance(arg: Any?): Plugin<Any?> {
        addHandler(519, NPC_TYPE, this)
        addHandler(3797, NPC_TYPE, this)
        definePlugin(BobDialogue())
        return this
    }

    override fun handle(event: NodeUsageEvent): Boolean {
        val player = event.player
        val repair = forId(event.usedItem.id)
        if (repair == null && !BobDialogue.BarrowsEquipment.isBarrowsItem(event.usedItem.id)) {
            player.dialogueInterpreter.open(519, event.usedWith, true, true, null)
            return true
        }
        player.dialogueInterpreter.open(519, event.usedWith, true, false, event.usedItem.id, event.usedItem)
        return true
    }
}
