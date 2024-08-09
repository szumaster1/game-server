package content.global.handlers.item.withitem

import core.api.addItemOrDrop
import core.api.consts.Items
import core.api.removeItem
import core.api.sendDialogue
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * Doogle sardine listener.
 */
class DoogleSardineListener : InteractionListener {

    private val rawSardine = Items.RAW_SARDINE_327
    private val doogleLeaves = Items.DOOGLE_LEAVES_1573
    private val doogleSardine = Items.DOOGLE_SARDINE_1552

    override fun defineListeners() {

        /*
         * Creating doodle sardine interaction.
         */

        onUseWith(IntType.ITEM, rawSardine, doogleLeaves) { player, used, with ->
            if (removeItem(player, used.asItem()) && removeItem(player, with.asItem())) {
                sendDialogue(player, "You rub the doogle leaves over the sardine.")
                addItemOrDrop(player, doogleSardine)
            }
            return@onUseWith true
        }
    }
}
