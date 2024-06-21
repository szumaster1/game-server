package content.global.interaction.item.withscenery

import core.api.Container
import core.api.addItem
import core.api.consts.Items
import core.api.consts.Scenery
import core.api.removeItem
import core.api.sendMessage
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

class SackOnHayListener : InteractionListener {

    companion object {
        const val SACK_ITEM = Items.EMPTY_SACK_5418
        private val hayScenery = intArrayOf(
            Scenery.HAY_BALE_36892,
            Scenery.HAY_BALES_36894,
            Scenery.HAY_BALES_36896,
            Scenery.HAYSTACK_300,
            Scenery.HAY_BALES_34593,
            Scenery.HAY_BALES_298,
            Scenery.HAY_BALES_299
        )
    }

    override fun defineListeners() {
        onUseWith(IntType.SCENERY, SACK_ITEM, *hayScenery) { player, used, _ ->
            if (removeItem(player, used.asItem(), Container.INVENTORY)) {
                addItem(player, Items.HAY_SACK_6057, 1)
                sendMessage(player, "You fill the sack with hay.")
            }
            return@onUseWith true
        }
    }
}