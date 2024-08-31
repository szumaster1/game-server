package content.global.handlers.item.withscenery

import cfg.consts.Items
import cfg.consts.Scenery
import core.api.removeItem
import core.api.setVarbit
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * GWD Entrance rope listener.
 */
class GWDEntranceRopeListener : InteractionListener {

    override fun defineListeners() {
        onUseWith(IntType.SCENERY, Items.ROPE_954, Scenery.HOLE_26340) { player, used, _ ->
            if (!removeItem(player, used)) {
                return@onUseWith false
            }
            setVarbit(player, 3932, 1, true)
            return@onUseWith true
        }
    }

}
