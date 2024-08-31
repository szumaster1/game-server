package content.region.misthalin.lumbridge.handlers

import cfg.consts.NPCs
import content.data.item.RepairItem.Companion.forId
import content.region.misthalin.lumbridge.dialogue.BobDialogue
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
        addHandler(NPCs.BOB_519, NPC_TYPE, this)
        addHandler(NPCs.SQUIRE_3797, NPC_TYPE, this)
        // TODO addHandler(NPCs.TINDEL_MARCHANT_1799, NPC_TYPE, this)
        definePlugin(content.region.misthalin.lumbridge.dialogue.BobDialogue())
        return this
    }

    override fun handle(event: NodeUsageEvent): Boolean {
        val player = event.player
        val repair = forId(event.usedItem.id)
        if (repair == null && !content.region.misthalin.lumbridge.dialogue.BobDialogue.BarrowsEquipment.isBarrowsItem(event.usedItem.id)) {
            player.dialogueInterpreter.open(519, event.usedWith, true, true, null)
            return true
        }
        player.dialogueInterpreter.open(519, event.usedWith, true, false, event.usedItem.id, event.usedItem)
        return true
    }
}
