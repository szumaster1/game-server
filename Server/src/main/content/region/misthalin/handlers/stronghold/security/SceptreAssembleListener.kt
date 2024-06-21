package content.region.misthalin.handlers.stronghold.security

import core.api.*
import core.api.consts.Items
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.Item

class SceptreAssembleListener : InteractionListener {

    override fun defineListeners() {

        /*
         * Assemble left skull half with the right skull half.
         */

        onUseWith(IntType.ITEM, Items.LEFT_SKULL_HALF_9008, Items.RIGHT_SKULL_HALF_9007) { player, used, with ->
            if (removeItem(player, used.asItem())) {
                sendItemDialogue(player, Items.STRANGE_SKULL_9009, "The two halves of the skull fit perfectly.")
                replaceSlot(player, with.asItem().index, Item(Items.STRANGE_SKULL_9009, 1))
            }
            return@onUseWith true
        }

        /*
         * Assemble top of sceptre with the bottom of sceptre.
         */

        onUseWith(IntType.ITEM, Items.BOTTOM_OF_SCEPTRE_9011, Items.TOP_OF_SCEPTRE_9010) { player, used, with ->
            if (removeItem(player, used.asItem())) {
                sendItemDialogue(player, Items.RUNED_SCEPTRE_9012, "The two halves of the sceptre fit perfectly.")
                replaceSlot(player, with.asItem().index, Item(Items.RUNED_SCEPTRE_9012, 1))
            }
            return@onUseWith true
        }

        /*
         * Assemble Sceptre using runed sceptre with strange skull.
         */

        onUseWith(IntType.ITEM, Items.STRANGE_SKULL_9009, Items.RUNED_SCEPTRE_9012) { player, used, with ->
            if (removeItem(player, used.asItem())) {
                sendDoubleItemDialogue(player, -1, Items.SKULL_SCEPTRE_9013, "The skull fits perfectly atop the Sceptre.")
                replaceSlot(player, with.asItem().index, Item(Items.SKULL_SCEPTRE_9013, 1))
            }
            return@onUseWith true
        }
    }
}