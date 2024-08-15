package content.region.misc.handlers

import core.api.*
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.consts.Scenery
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.world.map.Location
import core.game.world.repository.Repository.findNPC

/**
 * Entrana listeners.
 */
class EntranaListeners : InteractionListener {

    companion object {
        const val ENTRANA_BOOKCASE = Scenery.BOOKCASE_33964
        const val GLASSBLOWING_BOOK = Items.GLASSBLOWING_BOOK_11656
        const val CAVE_MONK = NPCs.CAVE_MONK_656
        const val MAGIC_DOOR = Scenery.MAGIC_DOOR_2407
    }

    override fun defineListeners() {
        // Glassblowing Book can be found on 1 Floor in the house west of the Furnace on Entrana.
        on(ENTRANA_BOOKCASE, IntType.SCENERY, "Search") { player, _ ->
            if (!inInventory(player, GLASSBLOWING_BOOK)) {
                addItem(player, GLASSBLOWING_BOOK)
                sendMessage(player, "You search the bookcase and find a book named 'Glassblowing Book'.")
            } else if (freeSlots(player) == 0) {
                sendMessage(player, "You don't have enough inventory space.")
            } else {
                sendMessage(player, "You search the bookcase...")
                player.sendMessage("You don't find anything interesting.", 1)
            }
            return@on true
        }

        // Ladder lead to Spirit tree (Lost City quest).
        on(Scenery.LADDER_2408, IntType.SCENERY, "climb-down") { player, _ ->
            openDialogue(player, CAVE_MONK, findNPC(CAVE_MONK)!!.asNpc())
            return@on true
        }

        // Secret doors lead to Brimhaven dungeon.
        on(MAGIC_DOOR, IntType.SCENERY, "open") { player, _ ->
            sendMessage(player, "You feel the world around you dissolve...")
            teleport(player, Location(3208, 3764, 0))
            return@on true
        }
    }
}
