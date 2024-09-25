package content.global.skill.crafting.material

import core.api.addDialogueAction
import core.api.amountInInventory
import core.api.sendDialogueOptions
import core.api.setTitle
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.Item
import org.rs.consts.Items
import kotlin.math.min

/**
 * Represents listener for granite split.
 */
class GraniteSplittingListener : InteractionListener {

    override fun defineListeners() {
        onUseWith(IntType.ITEM, graniteIDs, Items.CHISEL_1755) { player, used, _ ->
            setTitle(player, 2)
            sendDialogueOptions(
                player,
                "What would you like to do?",
                "Split the block into smaller pieces.",
                "Nothing."
            )
            addDialogueAction(player) { player, _ ->
                var amount = min(amountInInventory(player, used.id), amountInInventory(player, used.id))
                player.pulseManager.run(GraniteSplittingPulse(player, Item(used.id), amount))
            }
            return@onUseWith true
        }
    }

    companion object {
        val graniteIDs = intArrayOf(Items.GRANITE_2KG_6981, Items.GRANITE_5KG_6983)
    }
}
