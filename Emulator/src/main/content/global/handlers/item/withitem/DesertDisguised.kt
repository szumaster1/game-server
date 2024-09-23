package content.global.handlers.item.withitem

import core.api.addItemOrDrop
import core.api.removeItem
import core.api.sendMessage
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import org.rs.consts.Items

/**
 * Creating desert disguise (The Feud quest).
 */
class DesertDisguised : InteractionListener {

    override fun defineListeners() {
        onUseWith(IntType.ITEM, Items.KARIDIAN_HEADPIECE_4591, Items.FAKE_BEARD_4593) { player, used, with ->
            if (removeItem(player, used.asItem()) && removeItem(player, with.asItem())) {
                addItemOrDrop(player, Items.DESERT_DISGUISE_4611)
            } else {
                sendMessage(player, "Nothing interesting happens.")
            }
            return@onUseWith true
        }
    }

}
