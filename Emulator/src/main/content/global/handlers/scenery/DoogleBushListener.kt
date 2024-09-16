package content.global.handlers.scenery

import core.api.addItem
import cfg.consts.Items
import cfg.consts.Scenery
import core.api.sendMessage
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * Handling pick-leaf interaction with Doogle bush.
 */
class DoogleBushListener : InteractionListener {

    override fun defineListeners() {
        on(Scenery.DOOGLE_BUSH_31155, IntType.SCENERY, "pick-leaf") { player, _ ->
            if (!addItem(player, Items.DOOGLE_LEAVES_1573)) {
                sendMessage(player, "You don't have enough space in your inventory.")
            } else {
                sendMessage(player, "You pick some doogle leaves.")
            }
            return@on true
        }
    }
}
