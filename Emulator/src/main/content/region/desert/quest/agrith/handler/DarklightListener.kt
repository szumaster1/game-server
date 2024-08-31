package content.region.desert.quest.agrith.handler

import core.api.addItem
import cfg.consts.Items
import core.api.hasRequirement
import core.api.inInventory
import core.api.removeItem
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * Darklight listener.
 */
class DarklightListener : InteractionListener {
    override fun defineListeners() {
        onUseWith(IntType.ITEM, Items.BLACK_MUSHROOM_INK_4622, Items.SILVERLIGHT_2402) { player, used, with ->
            if (!hasRequirement(player, "Shadow of the Storm") || (!inInventory(
                    player,
                    Items.BLACK_MUSHROOM_INK_4622,
                    1
                ) && (!inInventory(player, Items.SILVERLIGHT_2402, 1)))
            )
                return@onUseWith false
            if (removeItem(player, used.id) && removeItem(player, with.id))
                addItem(player, Items.DARKLIGHT_6746)
            return@onUseWith true
        }
    }
}