package content.region.asgarnia.quest.rd.handlers

import cfg.consts.Items
import cfg.consts.Scenery
import cfg.consts.Sounds
import content.region.asgarnia.quest.rd.RDUtils
import core.api.*
import core.game.dialogue.DialogueBuilder
import core.game.dialogue.DialogueBuilderFile
import core.game.dialogue.DialogueOptionsBuilder
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.world.update.flag.context.Graphic

/**
 * Miss Cheevers room listeners.
 */
class MissCheeversRoomListeners : InteractionListener {

    companion object {
        const val doorVarbit = 686
        const val book = "rd:book"
        const val magnet = "rd:magnet"
        const val tin = "rd:tin"
        const val chisel = "rd:chisel"
        const val wire = "rd:wire"
        const val knife = "rd:knife"
        const val shears = "rd:shears"
        const val vials = "rd:3vialsofliquid"

        val toolIDs = intArrayOf(Items.BRONZE_WIRE_5602, Items.CHISEL_5601, Items.KNIFE_5605)
        val potionIDs = intArrayOf(Items.TIN_ORE_POWDER_5583, Items.CUPRIC_ORE_POWDER_5584)

        enum class Vials(val itemId: Int, val attribute: String) {
            CUPRIC_SULPHATE_5577(Items.CUPRIC_SULPHATE_5577, "rd:cupricsulphate"),
            ACETIC_ACID_5578(Items.ACETIC_ACID_5578, "rd:aceticacid"),
            GYPSUM_5579(Items.GYPSUM_5579, "rd:gypsum"),
            SODIUM_CHLORIDE_5580(Items.SODIUM_CHLORIDE_5580, "rd:sodiumchloride"),
            NITROUS_OXIDE_5581(Items.NITROUS_OXIDE_5581, "rd:nitrousoxide"),
            VIAL_OF_LIQUID_5582(Items.VIAL_OF_LIQUID_5582, "rd:vialofliquid"),
            TIN_ORE_POWDER_5583(Items.TIN_ORE_POWDER_5583, "rd:tinorepowder"),
            CUPRIC_ORE_POWDER_5584(Items.CUPRIC_ORE_POWDER_5584, "rd:cupricorepowder");

            companion object {
                @JvmField
                val vialMap = values().associateBy { it.itemId }
            }
        }

        enum class DoorVials(val itemId: Int, val attribute: String) {
            CUPRIC_SULPHATE_5577(Items.CUPRIC_SULPHATE_5577, "rd:doorcupricsulphate"),
            ACETIC_ACID_5578(Items.ACETIC_ACID_5578, ""), SODIUM_CHLORIDE_5580(Items.SODIUM_CHLORIDE_5580, ""),
            VIAL_OF_LIQUID_5582(Items.VIAL_OF_LIQUID_5582, "rd:doorvialofliquid");

            companion object {
                @JvmField
                val doorVialsArray = values().map { it.itemId }.toIntArray()
                val doorVialsMap = values().associateBy { it.itemId }
                val doorVialsRequiredMap = values().associateBy { it.itemId }.filter { it.value.attribute != "" }
            }
        }

    }

    override fun defineListeners() {
        val searchActions = mapOf(
            Scenery.OLD_BOOKSHELF_7327 to { player: Player ->
                RDUtils.searchingHelper(player, magnet, Items.MAGNET_5604, "You search the bookshelves...", "Hidden amongst the books you find a magnet.")
            },
            Scenery.OLD_BOOKSHELF_7328 to { player: Player ->
                if (getAttribute(player, "/save:rd:help", -1) < 3) {
                    sendMessage(player, "You search the bookshelves...")
                    sendMessageWithDelay(player, "You find nothing of interest.", 1)
                } else {
                    RDUtils.searchingHelper(player, book, Items.ALCHEMICAL_NOTES_5588, "You search the bookshelves...", "You find a book that looks like it might be helpful.")
                }
            },
            Scenery.OLD_BOOKSHELF_7329 to { player: Player ->
                RDUtils.searchingHelper(player, knife, Items.KNIFE_5605, "You search the bookshelves...", "Hidden amongst the books you find a knife.")
            },
            Scenery.OLD_BOOKSHELF_7330 to { player: Player ->
                RDUtils.searchingHelper(player, "", 0, "You search the bookshelves...", "")
            }
        )

        /*
         * Handle searching the bookcases.
         */
        searchActions.forEach { (scenery, action) ->
            on(scenery, IntType.SCENERY, "search") { player, _ ->
                action(player)
                return@on true
            }
        }

        val vialItems = listOf(Items.ACETIC_ACID_5578, Items.CUPRIC_SULPHATE_5577, Items.GYPSUM_5579, Items.SODIUM_CHLORIDE_5580, Items.NITROUS_OXIDE_5581, Items.TIN_ORE_POWDER_5583, Items.CUPRIC_ORE_POWDER_5584)
        val sceneryIDs = (Scenery.SHELVES_7333..Scenery.SHELVES_7339).toList()

        vialItems.forEachIndexed { index, item ->
            on(sceneryIDs[index], IntType.SCENERY, "search") { player, _ ->
                val vialList = mutableListOf<Int>()
                if (!getAttribute(player, Vials.vialMap[item]?.attribute ?: return@on false, false)) {
                    vialList.add(item)
                }
                openDialogue(player, VialShelfDialogueFile(vialList.toIntArray()))
                return@on true
            }
        }

        /*
         * Handle search option for shelves objects.
         */

        on(Scenery.SHELVES_7340, IntType.SCENERY, "search") { player, _ ->
            val vialCount = getAttribute(player, vials, 3)
            val vialList = List(vialCount) { Items.VIAL_OF_LIQUID_5582 }
            openDialogue(player, VialShelfDialogueFile(vialList.toIntArray(), vials))
            return@on true
        }

        val crateInteractions = mapOf(
            Scenery.CRATE_7347 to Pair(tin, Items.TIN_5600),
            Scenery.CRATE_7348 to Pair(chisel, Items.CHISEL_5601),
            Scenery.CRATE_7349 to Pair(wire, Items.BRONZE_WIRE_5602)
        )

        crateInteractions.forEach { (scenery, attributes) ->
            on(scenery, IntType.SCENERY, "search") { player, node ->
                val (attribute, item) = attributes
                if (node.location == RDUtils.getLocationForScenery(node.asScenery())) {
                    RDUtils.searchingHelper(player, attribute, item, "You search the crate...", "Inside the crate you find a ${getItemName(item).lowercase()}.")
                } else {
                    RDUtils.searchingHelper(player, "", 0, "You search the crate...", "")
                }
                return@on true
            }
        }

        /*
         * Handle opening the chest.
         */

        on(Scenery.CLOSED_CHEST_7350, IntType.SCENERY, "open") { _, node ->
            replaceScenery(node.asScenery(), Scenery.OPEN_CHEST_7351, 100)
            return@on true
        }

        /*
         * Handle search option for chest.
         */

        on(Scenery.OPEN_CHEST_7351, IntType.SCENERY, "search") { player, _ ->
            RDUtils.searchingHelper(player, shears, Items.SHEARS_5603, "You search the chest...", "Inside the chest you find some shears.")
            return@on true
        }

        /*
         * Handle close option for chest.
         */

        on(Scenery.OPEN_CHEST_7351, IntType.SCENERY, "close") { _, node ->
            replaceScenery(node.asScenery(), Scenery.CLOSED_CHEST_7350, -1)
            return@on true
        }


        /*
         * Handle using the Tin on Gypsum.
         */

        onUseWith(IntType.ITEM, Items.TIN_5600, Items.GYPSUM_5579) { player, used, with ->
            RDUtils.processItemUsageAndReturn(player, used.asItem(), with.asItem(), Item(Items.TIN_5592))
            return@onUseWith true
        }

        /*
         * Handle using the Tin on Liquid vial.
         */

        onUseWith(IntType.ITEM, Items.TIN_5592, Items.VIAL_OF_LIQUID_5582) { player, used, with ->
            RDUtils.processItemUsage(player, used.asItem(), with.asItem(), Item(Items.TIN_5593))
            sendMessage(player, "You notice the tin gets quite warm as you do this.")
            sendMessageWithDelay(player, "A lumpy white mixture is made, that seems to be hardening.", 1)
            return@onUseWith true
        }

        /*
         * Handle using the Tin on Key.
         */

        onUseWith(IntType.SCENERY, Items.TIN_5593, Scenery.KEY_7346) { player, used, _ ->
            sendMessage(player, "You make an impression of the key as the white mixture hardens.")
            replaceSlot(player, slot = used.index, Item(Items.TIN_5594))
            return@onUseWith true
        }

        /*
         * Handle using the Tin on various potions.
         */

        onUseWith(IntType.ITEM, Items.TIN_5594, *potionIDs) { player, used, with ->
            RDUtils.processItemUsageAndReturn(player, used.asItem(), with.asItem(), Item(Items.TIN_5595))
            return@onUseWith true
        }

        /*
         * Handle using the Tin on various potions.
         */

        onUseWith(IntType.ITEM, Items.TIN_5595, *potionIDs) { player, used, with ->
            RDUtils.processItemUsageAndReturn(player, used.asItem(), with.asItem(), Item(Items.TIN_5596))
            return@onUseWith true
        }

        /*
         * Handle use the tin on Bunsen burner scenery.
         */

        onUseWith(IntType.SCENERY, Items.TIN_5596, Scenery.BUNSEN_BURNER_7332) { player, used, _ ->
            if (removeItem(player, used.id)) {
                sendMessage(player, "You heat the two powdered ores together in the tin.")
                sendMessage(player, "You make a duplicate of the key in bronze.")
                addItemOrDrop(player, Items.TIN_5597)
            }
            return@onUseWith true
        }

        /*
         * Handle use the tin on various tools.
         */

        onUseWith(IntType.ITEM, Items.TIN_5597, *toolIDs) { player, used, _ ->
            if (removeItem(player, used.id)) {
                sendMessage(player, "You prise the duplicate key out of the tin.")
                addItemOrDrop(player, Items.TIN_5600)
                addItemOrDrop(player, Items.BRONZE_KEY_5585)
            }
            return@onUseWith true
        }

        /*
         * Handle use the metal spade on Bunsen burner.
         */

        onUseWith(IntType.SCENERY, Items.METAL_SPADE_5586, Scenery.BUNSEN_BURNER_7332) { player, _, _ ->
            lock(player, 3)
            sendMessage(player, "You burn the wooden handle away from the spade...")
            queueScript(player, 1, QueueStrength.WEAK) { stage: Int ->
                when (stage) {
                    0 -> {
                        visualize(player, -1, Graphic(157, 96))
                        playAudio(player, Sounds.FIREWAVE_HIT_163)
                        keepRunning(player)
                    }

                    1 -> {
                        removeItem(player, Items.METAL_SPADE_5586)
                        keepRunning(player)
                    }

                    2 -> {
                        addItem(player, Items.METAL_SPADE_5587)
                        addItem(player, Items.ASHES_592)
                        sendMessage(player, "...and are left with a metal spade with no handle.")
                        stopExecuting(player)
                    }

                    else -> stopExecuting(player)
                }
            }
            return@onUseWith true
        }

        /*
         * Handle stone door basic interaction.
         */

        on(Scenery.STONE_DOOR_7343, SCENERY, "study") { player, _ ->
            sendDialogueLines(player, "There is a stone slab here obstructing the door.", "There is a small hole in the slab that looks like it might be for a handle.")
            sendMessage(player, "It's nearly a perfect fit!")
            return@on true
        }

        /*
         * Handle use the metal spade on stone doors.
         */

        onUseWith(IntType.SCENERY, Items.METAL_SPADE_5587, Scenery.STONE_DOOR_7343) { player, used, _ ->
            if (removeItem(player, used.id)) {
                playAudio(player, Sounds.RECRUIT_SPADE_1742)
                sendMessage(player, "You slide the spade into the hole in the stone...")
                sendMessageWithDelay(player, "It's nearly a perfect fit!", 1)
                setVarbit(player, doorVarbit, 1)
            }
            return@onUseWith true
        }

        /*
         * Handle pouring various potions onto stone door.
         */

        onUseWith(IntType.SCENERY, DoorVials.doorVialsArray, Scenery.STONE_DOOR_7344) { player, used, _ ->
            RDUtils.handleVialUsage(player, used.asItem())
            return@onUseWith true
        }

        /*
         * Handle pull-spade interaction.
         */
        on(Scenery.STONE_DOOR_7344, SCENERY, "pull-spade") { player, _ ->
            RDUtils.handleSpadePull(player)
            return@on true
        }


        /*
         * Handle walk-through the door.
         */

        on(Scenery.OPEN_DOOR_7345, SCENERY, "walk-through") { player, _ ->
            RDUtils.handleDoorWalkThrough(player)
            return@on true
        }

    }

}

/**
 * Vial Shelf dialogue file.
 */
private class VialShelfDialogueFile(private val flaskIdsArray: IntArray, private val specialAttribute: String? = null) :
    DialogueBuilderFile() {

    override fun create(b: DialogueBuilder) {
        b.onPredicate { true }.branch { flaskIdsArray.size }.let { branch ->
            branch.onValue(3).line("There are three vials on this shelf.").options("Take the vials?")
                .let { optionBuilder -> handleVialOptions(optionBuilder, 3) }
            branch.onValue(2).line("There are two vials on this shelf.").options("Take the vials?")
                .let { optionBuilder -> handleVialOptions(optionBuilder, 2) }
            branch.onValue(1).line("There is a vial on this shelf.").options("Take the vial?")
                .let { optionBuilder -> handleVialOptions(optionBuilder, 1) }
            branch.onValue(0).line("There is nothing of interest on these shelves.")
        }
    }

    private fun handleVialOptions(optionBuilder: DialogueOptionsBuilder, count: Int) {
        val vialsToTake = when (count) {
            3 -> listOf(0, 1, 2)
            2 -> listOf(0, 1)
            1 -> listOf(0)
            else -> emptyList()
        }

        vialsToTake.forEachIndexed { index, vialIndex ->
            optionBuilder.option("Take ${if (count > 1) "the ${ordinal(index + 1)} vial" else "the vial"}")
                .endWith { _, player ->
                    vialsToTake.forEach { addItemOrDrop(player, flaskIdsArray[it]) }
                    updatePlayerAttribute(player, count)
                }
        }

        if (count > 1) {
            optionBuilder.option("Take both vials.").endWith { _, player ->
                vialsToTake.forEach { addItemOrDrop(player, flaskIdsArray[it]) }
                updatePlayerAttribute(player, count)
            }
        }

        optionBuilder.option("Don't take a vial.").end()
    }

    private fun updatePlayerAttribute(player: Player, count: Int) {
        if (specialAttribute != null) {
            setAttribute(player, specialAttribute, getAttribute(player, specialAttribute, count) - count)
        } else {
            flaskIdsArray.forEach { id ->
                setAttribute(player, MissCheeversRoomListeners.Companion.Vials.vialMap[id]!!.attribute, true)
            }
        }
    }

    private fun ordinal(number: Int): String {
        return when (number) {
            1 -> "first"
            2 -> "second"
            3 -> "third"
            else -> number.toString()
        }
    }
}