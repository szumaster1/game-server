package content.global.skill.production.cooking.handlers

import core.api.addItemOrDrop
import core.api.animate
import core.api.consts.Animations
import core.api.consts.Items
import core.api.removeItem
import core.api.sendMessage
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * Karamjan rum listener.
 */
class KaramjanRumListener : InteractionListener {

    override fun defineListeners() {

        /**
         * Sliced-banana version.
         */
        onUseWith(IntType.ITEM, Items.SLICED_BANANA_3162, Items.KARAMJAN_RUM_431) { player, used, with ->
            if (removeItem(player, used.asItem()) && removeItem(player, with.asItem())) {
                animate(player, Animations.HUMAN_USE_BANANA_WITH_KARAMJAN_RUM_1195)
                sendMessage(player, "You add the banana slices to the Karamjan rum.")
                addItemOrDrop(player, Items.KARAMJAN_RUM_3164, 1)
            } else {
                sendMessage(player, "Nothing interesting happens.")
            }
            return@onUseWith true
        }

        /**
         * Banana version.
         */
        onUseWith(IntType.ITEM, Items.BANANA_1963, Items.KARAMJAN_RUM_431) { player, used, with ->
            if (removeItem(player, used.asItem()) && removeItem(player, with.asItem())) {
                animate(player, Animations.HUMAN_USE_BANANA_WITH_KARAMJAN_RUM_1195)
                sendMessage(player, "You stuff the banana into the neck of the bottle. You begin to wonder why.")
                addItemOrDrop(player, Items.KARAMJAN_RUM_3165, 1)
            } else {
                sendMessage(player, "Nothing interesting happens.")
            }
            return@onUseWith true
        }

    }

}
