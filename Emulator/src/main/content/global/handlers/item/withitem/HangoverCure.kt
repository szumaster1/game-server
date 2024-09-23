package content.global.handlers.item.withitem

import core.api.addItem
import core.api.removeItem
import core.api.sendItemDialogue
import core.api.sendMessage
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import org.rs.consts.Items

/**
 * Handles the hangover cure creation.
 */
class HangoverCure : InteractionListener {

    override fun defineListeners() {

        /*
         * Creating chocolate milk.
         */

        onUseWith(IntType.ITEM, Items.CHOCOLATE_DUST_1975, Items.BUCKET_OF_MILK_1927) { player, _, _ ->
            if (removeItem(player, Items.CHOCOLATE_DUST_1975) && removeItem(player, Items.BUCKET_OF_MILK_1927)) {
                sendItemDialogue(player, Items.CHOCOLATEY_MILK_1977, "You mix the chocolate into the bucket.")
                addItem(player, Items.CHOCOLATEY_MILK_1977)
            } else {
                sendMessage(player, "Nothing interesting happens.")
            }
            return@onUseWith true
        }

        /*
         * Creating hangover cure.
         */

        onUseWith(IntType.ITEM, Items.SNAPE_GRASS_231, Items.CHOCOLATEY_MILK_1977) { player, _, _ ->
            if (removeItem(player, Items.SNAPE_GRASS_231) && removeItem(player, Items.CHOCOLATEY_MILK_1977)) {
                sendItemDialogue(player, Items.HANGOVER_CURE_1504, "You mix the snape grass into the bucket.")
                addItem(player, Items.HANGOVER_CURE_1504)
            } else {
                sendMessage(player, "Nothing interesting happens.")
            }
            return@onUseWith true
        }
    }
}
