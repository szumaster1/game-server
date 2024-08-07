package content.region.tirannwn.handlers

import core.api.*
import core.api.consts.Items
import core.api.consts.Scenery
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.link.TeleportManager
import core.game.world.map.Location

/**
 * Isafdar listeners.
 */
class IsafdarListeners : InteractionListener {

    override fun defineListeners() {

        on(Scenery.CAVE_ENTRANCE_4006, IntType.SCENERY, "enter") { player, _ ->
            teleport(player, Location(2314, 9624, 0), TeleportManager.TeleportType.INSTANT)
            return@on true
        }

        on(Scenery.CAVE_EXIT_4007, IntType.SCENERY, "exit") { player, _ ->
            teleport(player, Location(2312, 3217, 0), TeleportManager.TeleportType.INSTANT)
            return@on true
        }

        /**
         * Search the bookcase in Lletya.
         */
        on(Scenery.BOOKCASE_8752, IntType.SCENERY, "search") { player, _ ->
            if (!inInventory(player, Items.PRIFDDINAS_HISTORY_6073)) {
                sendMessage(player, "You search the bookshelves...")
                sendMessageWithDelay(player, "and find a book named 'Prifddinas History'.", 1)
                addItem(player, Items.PRIFDDINAS_HISTORY_6073)
            } else {
                sendMessage(player, "You search the bookshelves...")
                sendMessageWithDelay(player,"You don't find anything interesting.", 1)
            }
            return@on true
        }
    }

}