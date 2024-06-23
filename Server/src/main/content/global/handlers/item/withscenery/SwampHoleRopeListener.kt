package content.global.handlers.item.withscenery

import core.api.consts.Items
import core.api.consts.Scenery
import core.api.removeItem
import core.api.sendDialogue
import core.api.sendItemDialogue
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

class SwampHoleRopeListener : InteractionListener {

    override fun defineListeners() {
        onUseWith(IntType.SCENERY, Items.ROPE_954, Scenery.DARK_HOLE_5947) { player, used, _ ->
            if (player.savedData.globalData.hasTiedLumbridgeRope()) {
                sendDialogue(player, "There is already a rope tied to the entrance.")
                return@onUseWith true
            }
            if (!removeItem(player, used)) {
                return@onUseWith false
            }
            sendItemDialogue(player, used, "You tie the rope to the top of the entrance and throw it down.")
            player.savedData.globalData.setLumbridgeRope(true)
            return@onUseWith true
        }
    }

}
