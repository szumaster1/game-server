package content.global.interaction.item.withscenery

import core.api.consts.Items
import core.api.consts.Scenery
import core.api.removeItem
import core.api.setVarbit
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

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