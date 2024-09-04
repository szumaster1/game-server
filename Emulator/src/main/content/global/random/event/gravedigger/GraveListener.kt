package content.global.random.event.gravedigger

import core.api.*
import cfg.consts.Scenery
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.node.scenery.*
import core.game.world.map.Location

/**
 * Handles the gravedigger random event interaction.
 */
class GraveListener : InteractionListener {
    private val graves = listOf(
        Scenery.GRAVE_12722 to Scenery.GRAVE_12726,
        Scenery.GRAVE_12724 to Scenery.GRAVE_12727,
        Scenery.GRAVE_12721 to Scenery.GRAVE_12728,
        Scenery.GRAVE_12723 to Scenery.GRAVE_12729,
        Scenery.GRAVE_12725 to Scenery.GRAVE_12730
    )
    private val graveLocations = listOf(
        Location(1926, 4999, 0),
        Location(1930, 4999, 0),
        Location(1924, 4996, 0),
        Location(1928, 4996, 0),
        Location(1932, 4996, 0)
    )


    override fun defineListeners() {

        /*
         * Deposit interaction at graveyard.
         */

        on(GraveUtils.MAUSOLEUM, IntType.SCENERY, "deposit") { player, _ ->
            player.bank.openDepositBox()
            player.bank::refreshDepositBoxInterface
            return@on true
        }

        /*
         * Talk with leo interaction.
         */

        on(GraveUtils.LEO, IntType.NPC, "talk-to") { player, npc ->
            val leoCoffinPoints = getAttribute(player, GraveUtils.LEO_COFFIN_POINTS, 0)
            val leoTask = getAttribute(player, GraveUtils.LEO_TASK, 0)
            if (leoCoffinPoints >= 0 || leoTask == 1) {
                openDialogue(player, LeoDialogue(), npc)
            }
            return@on true
        }

        /*
         * Check the coffin interface.
         */

        on(GraveUtils.itemsCoffin, IntType.ITEM, "check") { player, _ ->
            openInterface(player, GraveUtils.COFFIN_INTERFACE)
            GraveUtils.getRandomCoffinContent(player)
            return@on true
        }

        /*
         * Read the content of the coffin.
         */

        on(GraveUtils.graveStones, IntType.SCENERY, "read") { player, _ ->
            openInterface(player, GraveUtils.GRAVESTONE_INTERFACE)
            GraveUtils.getRandomGraveContent(player)
            sendMessage(player, "You look at the gravestone.")
            return@on true
        }

        /*
         * Interaction with item/scenery.
         */

        graves.forEachIndexed { index, (grave, emptyGrave) ->
            on(grave, IntType.SCENERY, "take-coffin") { player, _ ->
                handleCoffinAction(player, grave, emptyGrave, graveLocations[index], index + 1, "take")
                return@on true
            }

            onUseWith(SCENERY, GraveUtils.itemsCoffin, emptyGrave) { player, used, _ ->
                handleCoffinAction(player, grave, emptyGrave, graveLocations[index], index + 1, "drop", used.asItem())
                return@onUseWith true
            }
        }
    }

    /**
     * Handles the interactions with the coffin.
     */
    private fun handleCoffinAction(
        player: Player,
        grave: Int,
        emptyGrave: Int,
        location: Location,
        coffinIndex: Int,
        action: String,
        item: Item? = null
    ) {
        lock(player, 3)
        player.animate(GraveUtils.PICK_AND_DROP_ANIM)
        if (action == "take") {
            replaceScenery(Scenery(grave, location), emptyGrave, -1)
            sendMessage(player, "You take the coffin from the grave.")
            addItemOrDrop(player, GraveUtils.COFFIN_1 + coffinIndex - 1)
        } else if (action == "drop" && item != null) {
            removeItem(player, item)
            replaceScenery(Scenery(emptyGrave, location), grave, -1)
            sendMessage(player, "You put the coffin into the grave.")
            player.incrementAttribute(GraveUtils.LEO_COFFIN_POINTS)
        }
    }
}
