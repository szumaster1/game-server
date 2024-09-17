package content.global.handlers.item.withscenery

import org.rs.consts.Items
import org.rs.consts.Scenery
import core.api.removeItem
import core.api.sendMessage
import core.api.setAttribute
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * Hairdresser cheese listener.
 */
class HairdresserCheeseListener : InteractionListener {

    override fun defineListeners() {
        onUseWith(IntType.SCENERY, Items.CHEESE_1985, Scenery.TREADMILL_11677) { player, used, _ ->
            if (removeItem(player, used)) {
                sendMessage(player, "You throw the cheese to Ridgeley, for which he appears grateful.")
                setAttribute(player, "diary:falador:feed-ridgeley-with-cheese", true)
            }
            return@onUseWith true
        }
    }
}
