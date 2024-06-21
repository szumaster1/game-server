package content.region.misc.dialogue.keldagrim.library

import core.api.addItemOrDrop
import core.api.consts.Items
import core.api.consts.Scenery
import core.api.freeSlots
import core.api.inInventory
import core.api.sendMessage
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

class LibraryHandler : InteractionListener {
    override fun defineListeners() {
        on(Scenery.BOOKCASE_6091, IntType.SCENERY, "search") { player, _ ->
            if (inInventory(player, Items.EXPLORERS_NOTES_11677)) {
                sendMessage(player, "You search the books...")
                sendMessage(player, "You find nothing of interest to you.")
            } else if (freeSlots(player) < 1) {
                sendMessage(player, "You need at least one free inventory space to take from the shelves.")
            } else {
                sendMessage(player, "You search the bookcase and find a book named 'Explorer's Notes'.")
                addItemOrDrop(player, Items.EXPLORERS_NOTES_11677)
            }
            return@on true
        }
    }

}