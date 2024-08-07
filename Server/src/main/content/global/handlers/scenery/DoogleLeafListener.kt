package content.global.handlers.scenery

import core.api.addItem
import core.api.consts.Items
import core.api.consts.Scenery
import core.api.sendMessage
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * Doogle leaf listener
 *
 * @constructor Doogle leaf listener
 */
class DoogleLeafListener : InteractionListener {

    override fun defineListeners() {
        on(Scenery.DOOGLE_BUSH_31155, IntType.SCENERY, "pick-leaf") { player, node ->
            if (!addItem(player, Items.DOOGLE_LEAVES_1573)) {
                sendMessage(player, "You don't have enough space in your inventory.")
            } else {
                sendMessage(player, "You pick some doogle leaves.")
            }
            return@on true
        }
    }
}
