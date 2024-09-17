package content.region.misthalin.lumbridge.handlers

import org.rs.consts.NPCs
import content.data.item.RepairItem.Companion.forId
import content.region.misthalin.lumbridge.dialogue.BobDialogue
import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Represents the Repair item handler.
 */
@Initializable
class RepairItemHandler : UseWithHandler() {

    override fun newInstance(arg: Any?): Plugin<Any?> {
        registerHandlers()
        return this
    }

    private fun registerHandlers() {
        val npcIds = listOf(NPCs.BOB_519, NPCs.SQUIRE_3797, NPCs.TINDEL_MARCHANT_1799)
        npcIds.forEach { addHandler(it, NPC_TYPE, this) }
    }

    override fun handle(event: NodeUsageEvent): Boolean {
        val player = event.player
        val repairItem = forId(event.usedItem.id)

        if (repairItem == null && !isBarrowsItem(event.usedItem.id)) {
            player.dialogueInterpreter.open(NPCs.BOB_519, event.usedWith, true, true, null)
            return true
        }

        player.dialogueInterpreter.open(NPCs.BOB_519, event.usedWith, true, false, event.usedItem.id, event.usedItem)
        return true
    }

    private fun isBarrowsItem(itemId: Int): Boolean {
        return BobDialogue.BarrowsEquipment.isBarrowsItem(itemId)
    }
}