package content.global.skill.crafting.rawmaterial

import core.api.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.Item
import org.rs.consts.Items
import kotlin.math.min

class GraniteCuttingListener : InteractionListener {

    override fun defineListeners() {
        onUseWith(IntType.ITEM, Items.CHISEL_1755, *graniteIDs) { player, _, with ->
            setTitle(player, 2)
            sendDialogueOptions(player, "What would you like to do?", "Split the block into smaller pieces.", "Nothing.")
            addDialogueAction(player) { player, button ->
                if(button <= 2) {
                    var amount = min(amountInInventory(player, with.id), amountInInventory(player, with.id))
                    submitIndividualPulse(player, GraniteCuttingPulse(player, Item(with.id), amount))
                } else return@addDialogueAction
            }
            return@onUseWith true
        }
    }

    companion object {
        val graniteIDs = intArrayOf(Items.GRANITE_2KG_6981, Items.GRANITE_5KG_6983)
    }
}
