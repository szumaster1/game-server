package content.region.asgarnia.quest.rd

import core.api.*
import cfg.consts.Animations
import cfg.consts.Items
import cfg.consts.Scenery
import cfg.consts.Sounds
import core.game.dialogue.DialogueBuilder
import core.game.dialogue.DialogueBuilderFile
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic

/**
 * Miss cheevers room listeners.
 */
class MissCheeversRoomListeners : InteractionListener {

    companion object {

        const val DOOR_VARBIT = 686

        const val ATTRIBUTE_BOOK = "rd:book"
        const val ATTRIBUTE_MAGNET = "rd:magnet"
        const val ATTRIBUTE_TIN = "rd:tin"
        const val ATTRIBUTE_CHISEL = "rd:chisel"
        const val ATTRIBUTE_WIRE = "rd:wire"
        const val ATTRIBUTE_KNIFE = "rd:knife"
        const val ATTRIBUTE_SHEARS = "rd:shears"

        const val ATTRIBUTE_VIALS = "rd:3vialsofliquid"

        val TOOLS = intArrayOf(Items.BRONZE_WIRE_5602, Items.CHISEL_5601, Items.KNIFE_5605)
        val MIXTURES = intArrayOf(Items.TIN_ORE_POWDER_5583, Items.CUPRIC_ORE_POWDER_5584)

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


        fun searchingHelper(player: Player, attributeCheck: String, item: Int, searchingDescription: String, objectDescription: String) {
            sendMessage(player, searchingDescription)
            queueScript(player, 1, QueueStrength.WEAK) {
                if (attributeCheck != "" && !getAttribute(player, attributeCheck, false)) {
                    setAttribute(player, attributeCheck, true)
                    addItem(player, item)
                    sendMessage(player, objectDescription)
                } else {
                    sendMessage(player, "You don't find anything interesting.")
                }
                return@queueScript stopExecuting(player)
            }
        }
    }

    override fun defineListeners() {

        on(Scenery.OLD_BOOKSHELF_7327, IntType.SCENERY, "search") { player, _ ->
            searchingHelper(player, ATTRIBUTE_MAGNET, Items.MAGNET_5604, "You search the bookshelves...", "Hidden amongst the books you find a magnet.")
            return@on true
        }

        on(Scenery.OLD_BOOKSHELF_7328, IntType.SCENERY, "search") { player, _ ->
            searchingHelper(player, ATTRIBUTE_BOOK, Items.ALCHEMICAL_NOTES_5588, "You search the bookshelves...", "You find a book that looks like it might be helpful.")
            return@on true
        }

        on(Scenery.OLD_BOOKSHELF_7329, IntType.SCENERY, "search") { player, _ ->
            searchingHelper(player, ATTRIBUTE_KNIFE, Items.KNIFE_5605, "You search the bookshelves...", "Hidden amongst the books you find a knife.")
            return@on true
        }

        on(Scenery.OLD_BOOKSHELF_7330, IntType.SCENERY, "search") { player, _ ->
            searchingHelper(player, "", 0, "You search the bookshelves...", "")
            return@on true
        }

        on(Scenery.SHELVES_7333, IntType.SCENERY, "search") { player, _ ->
            val vialList = ArrayList<Int>()
            if (!getAttribute(player, Vials.vialMap[Items.ACETIC_ACID_5578]!!.attribute, false)) {
                vialList.add(Items.ACETIC_ACID_5578)
            }
            if (!getAttribute(player, Vials.vialMap[Items.VIAL_OF_LIQUID_5582]!!.attribute, false)) {
                vialList.add(Items.VIAL_OF_LIQUID_5582)
            }
            openDialogue(player, VialShelfDialogueFile(vialList.toIntArray()))
            return@on true
        }

        on(Scenery.SHELVES_7334, IntType.SCENERY, "search") { player, _ ->
            val vialList = ArrayList<Int>()
            if (!getAttribute(player, Vials.vialMap[Items.CUPRIC_SULPHATE_5577]!!.attribute, false)) {
                vialList.add(Items.CUPRIC_SULPHATE_5577)
            }
            openDialogue(player, VialShelfDialogueFile(vialList.toIntArray()))
            return@on true
        }

        on(Scenery.SHELVES_7335, IntType.SCENERY, "search") { player, _ ->
            val vialList = ArrayList<Int>()
            if (!getAttribute(player, Vials.vialMap[Items.GYPSUM_5579]!!.attribute, false)) {
                vialList.add(Items.GYPSUM_5579)
            }
            openDialogue(player, VialShelfDialogueFile(vialList.toIntArray()))
            return@on true
        }

        on(Scenery.SHELVES_7336, IntType.SCENERY, "search") { player, _ ->
            val vialList = ArrayList<Int>()
            if (!getAttribute(player, Vials.vialMap[Items.SODIUM_CHLORIDE_5580]!!.attribute, false)) {
                vialList.add(Items.SODIUM_CHLORIDE_5580)
            }
            openDialogue(player, VialShelfDialogueFile(vialList.toIntArray()))
            return@on true
        }

        on(Scenery.SHELVES_7337, IntType.SCENERY, "search") { player, _ ->
            val vialList = ArrayList<Int>()
            if (!getAttribute(player, Vials.vialMap[Items.NITROUS_OXIDE_5581]!!.attribute, false)) {
                vialList.add(Items.NITROUS_OXIDE_5581)
            }
            openDialogue(player, VialShelfDialogueFile(vialList.toIntArray()))
            return@on true
        }

        on(Scenery.SHELVES_7338, IntType.SCENERY, "search") { player, _ ->
            val vialList = ArrayList<Int>()
            if (!getAttribute(player, Vials.vialMap[Items.TIN_ORE_POWDER_5583]!!.attribute, false)) {
                vialList.add(Items.TIN_ORE_POWDER_5583)
            }
            openDialogue(player, VialShelfDialogueFile(vialList.toIntArray()))
            return@on true
        }

        on(Scenery.SHELVES_7339, IntType.SCENERY, "search") { player, _ ->
            val vialList = ArrayList<Int>()
            if (!getAttribute(player, Vials.vialMap[Items.CUPRIC_ORE_POWDER_5584]!!.attribute, false)) {
                vialList.add(Items.CUPRIC_ORE_POWDER_5584)
            }
            openDialogue(player, VialShelfDialogueFile(vialList.toIntArray()))
            return@on true
        }

        on(Scenery.SHELVES_7340, IntType.SCENERY, "search") { player, _ ->
            val vialList = ArrayList<Int>()
            val total = getAttribute(player, ATTRIBUTE_VIALS, 3)
            for (i in 1..total) {
                vialList.add(Items.VIAL_OF_LIQUID_5582)
            }
            openDialogue(player, VialShelfDialogueFile(vialList.toIntArray(), ATTRIBUTE_VIALS))
            return@on true
        }

        on(Scenery.CRATE_7347, IntType.SCENERY, "search") { player, node ->
            if (node.location == Location(2476, 4943)) {
                searchingHelper(player, ATTRIBUTE_TIN, Items.TIN_5600, "You search the crate...", "Inside the crate you find a tin.")
            } else {
                searchingHelper(player, "", 0, "You search the crate...", "")
            }
            return@on true
        }

        on(Scenery.CRATE_7348, IntType.SCENERY, "search") { player, node ->
            if (node.location == Location(2476, 4937)) {
                searchingHelper(player, ATTRIBUTE_CHISEL, Items.CHISEL_5601, "You search the crate...", "Inside the crate you find a chisel.")
            } else {
                searchingHelper(player, "", 0, "You search the crate...", "")
            }
            return@on true
        }

        on(Scenery.CRATE_7349, IntType.SCENERY, "search") { player, node ->
            if (node.location == Location(2475, 4943)) {
                searchingHelper(player, ATTRIBUTE_WIRE, Items.BRONZE_WIRE_5602, "You search the crate...", "Inside the crate you find some wire.")
            } else {
                searchingHelper(player, "", 0, "You search the crate...", "")
            }
            return@on true
        }

        on(Scenery.CLOSED_CHEST_7350, IntType.SCENERY, "open") { _, node ->
            replaceScenery(node.asScenery(), Scenery.OPEN_CHEST_7351, 100)
            return@on true
        }

        on(Scenery.OPEN_CHEST_7351, IntType.SCENERY, "search") { player, _ ->
            searchingHelper(player, ATTRIBUTE_SHEARS, Items.SHEARS_5603, "You search the chest...", "Inside the chest you find some shears.")
            return@on true
        }

        on(Scenery.OPEN_CHEST_7351, IntType.SCENERY, "close") { _, node ->
            replaceScenery(node.asScenery(), Scenery.CLOSED_CHEST_7350, -1)
            return@on true
        }

        onUseWith(IntType.ITEM, Items.TIN_5600, Items.GYPSUM_5579) { player, used, with ->
            replaceSlot(player, slot = used.index, Item(Items.TIN_5592))
            replaceSlot(player, slot = with.index, Item(Items.VIAL_229))
            animate(player, Animation(Animations.HUMAN_USE_PESTLE_AND_MORTAR_364))
            playAudio(player, Sounds.VIALPOUR_2613)
            sendMessage(player, "You empty the vial into the tin.")
            return@onUseWith true
        }

        onUseWith(IntType.ITEM, Items.TIN_5592, Items.VIAL_OF_LIQUID_5582) { player, used, with ->
            replaceSlot(player, slot = used.index, Item(Items.TIN_5593))
            replaceSlot(player, slot = with.index, Item(Items.VIAL_229))
            animate(player, Animation(Animations.HUMAN_USE_PESTLE_AND_MORTAR_364))
            playAudio(player, Sounds.VIALPOUR_2613)
            sendMessage(player, "You empty the vial into the tin.")
            sendMessage(player, "You notice the tin gets quite warm as you do this.")
            sendMessageWithDelay(player, "A lumpy white mixture is made, that seems to be hardening.", 1)
            return@onUseWith true
        }

        onUseWith(IntType.SCENERY, Items.TIN_5593, Scenery.KEY_7346) { player, used, _ ->
            sendMessage(player, "You make an impression of the key as the white mixture hardens.")
            replaceSlot(player, slot = used.index, Item(Items.TIN_5594))
            return@onUseWith true
        }

        onUseWith(IntType.ITEM, Items.TIN_5594, *MIXTURES) { player, used, with ->
            replaceSlot(player, slot = used.index, Item(Items.TIN_5595))
            replaceSlot(player, slot = with.index, Item(Items.VIAL_229))
            playAudio(player, Sounds.VIALPOUR_2613)
            animate(player, Animation(Animations.HUMAN_USE_PESTLE_AND_MORTAR_364))
            sendMessage(player, "You pour the vial into the impression of the key.")
            return@onUseWith true
        }

        onUseWith(IntType.ITEM, Items.TIN_5595, *MIXTURES) { player, used, with ->
            replaceSlot(player, slot = used.index, Item(Items.TIN_5596))
            replaceSlot(player, slot = with.index, Item(Items.VIAL_229))
            playAudio(player, Sounds.VIALPOUR_2613)
            animate(player, Animation(Animations.HUMAN_USE_PESTLE_AND_MORTAR_364))
            sendMessage(player, "You pour the vial into the impression of the key.")
            return@onUseWith true
        }

        onUseWith(IntType.SCENERY, Items.TIN_5596, Scenery.BUNSEN_BURNER_7332) { player, used, _ ->
            if (removeItem(player, used.id)) {
                sendMessage(player, "You heat the two powdered ores together in the tin.")
                sendMessage(player, "You make a duplicate of the key in bronze.")
                addItemOrDrop(player, Items.TIN_5597)
            }
            return@onUseWith true
        }

        onUseWith(IntType.ITEM, Items.TIN_5597, *TOOLS) { player, used, _ ->
            if (removeItem(player, used.id)) {
                sendMessage(player, "You prise the duplicate key out of the tin.")
                addItemOrDrop(player, Items.TIN_5600)
                addItemOrDrop(player, Items.BRONZE_KEY_5585)
            }
            return@onUseWith true
        }

        onUseWith(IntType.SCENERY, Items.METAL_SPADE_5586, Scenery.BUNSEN_BURNER_7332) { player, _, _ ->
            lock(player,3)
            sendMessage(player, "You burn the wooden handle away from the spade...")
            queueScript(player, 1, QueueStrength.WEAK) { stage : Int ->
                when(stage){
                    0 -> {
                        visualize(player, -1, Graphic(157, 96))
                        playAudio(player, Sounds.FIREWAVE_HIT_163)
                        return@queueScript keepRunning(player)
                    }
                    1 -> {
                        removeItem(player, Items.METAL_SPADE_5586)
                        return@queueScript keepRunning(player)
                    }
                    2 -> {
                        addItem(player, Items.METAL_SPADE_5587).also { addItem(player, Items.ASHES_592) }
                        sendMessage(player, "...and are left with a metal spade with no handle.")
                        return@queueScript stopExecuting(player)
                    }
                    else -> return@queueScript stopExecuting(player)
                }
            }
            return@onUseWith true
        }


        on(Scenery.STONE_DOOR_7343, SCENERY, "study") { player, _ ->
            sendDialogueLines(player, "There is a stone slab here obstructing the door.", "There is a small hole in the slab that looks like it might be for a handle.")
            sendMessage(player, "It's nearly a perfect fit!")
            return@on true
        }

        onUseWith(IntType.SCENERY, Items.METAL_SPADE_5587, Scenery.STONE_DOOR_7343) { player, used, _ ->
            if (removeItem(player, used.id)) {
                playAudio(player, Sounds.RECRUIT_SPADE_1742)
                sendMessage(player, "You slide the spade into the hole in the stone...")
                sendMessageWithDelay(player, "It's nearly a perfect fit!", 1)
                setVarbit(player, DOOR_VARBIT, 1)
            }
            return@onUseWith true
        }

        onUseWith(IntType.SCENERY, DoorVials.doorVialsArray, Scenery.STONE_DOOR_7344) { player, used, _ ->
            lock(player, 5)
            lockInteractions(player, 5)
            if (removeItem(player, used.id)) {
                animate(player, Animation(2259))
                playAudio(player, Sounds.VIALPOUR_2613)
                setAttribute(player, DoorVials.doorVialsMap[used.id]!!.attribute, true)
                sendMessage(player, "You pour the vial onto the flat part of the spade.")
                addItem(player, Items.VIAL_229)
            }
            if (DoorVials.doorVialsRequiredMap.all { getAttribute(player, it.value.attribute, false) }) {
                animate(player, Animation(2259))
                playAudio(player, Sounds.VIALPOUR_2613)
                sendMessage(player, "Something caused a reaction when mixed!")
                sendMessage(player, "The spade gets hotter, and expands slightly.")
                setVarbit(player, DOOR_VARBIT, 2)
            }
            return@onUseWith true
        }

        on(Scenery.STONE_DOOR_7344, SCENERY, "pull-spade") { player, _ ->
            lock(player, 3)
            lockInteractions(player, 3)
            if (DoorVials.doorVialsRequiredMap.all { getAttribute(player, it.value.attribute, false) }) {
                sendMessage(player, "You pull on the spade...")
                sendMessage(player, "It works as a handle, and you swing the stone door open.")
                setVarbit(player, DOOR_VARBIT, 3)
            } else {
                sendMessage(player, "You pull on the spade...")
                sendMessage(player, "It comes loose, and slides out of the hole in the stone.")
                addItemOrDrop(player, Items.METAL_SPADE_5587)
                setVarbit(player, DOOR_VARBIT, 0)
            }
            return@on true
        }

        on(Scenery.OPEN_DOOR_7345, SCENERY, "walk-through") { player, _ ->
            if (inBorders(player, 2476, 4941, 2477, 4939)) {
                forceMove(player, player.location, Location(2478, 4940, 0), 20,80)
            } else if (inBorders(player, 2477, 4941, 2478, 4939)) {
                forceMove(player, player.location, Location(2476, 4940, 0), 20,80)
            }
            return@on true
        }


    }

}

private class VialShelfDialogueFile(private val flaskIdsArray: IntArray, private val specialAttribute: String? = null) :
    DialogueBuilderFile() {
    override fun create(b: DialogueBuilder) {
        b.onPredicate { _ -> true }.branch { _ -> flaskIdsArray.size }.let { branch ->

            branch.onValue(3)
                // This is the only shelf with 3 vials of water.
                .line("There are three vials on this shelf.").options("Take the vials?").let { optionBuilder ->
                    optionBuilder.option("Take one vial.").endWith { _, player ->
                        addItemOrDrop(player, flaskIdsArray[0])
                        if (specialAttribute != null) {
                            setAttribute(player, specialAttribute, getAttribute(player, specialAttribute, 3) - 1)
                            print(getAttribute(player, specialAttribute, 3))
                        }
                    }
                    optionBuilder.option("Take two vials.").endWith { _, player ->
                        addItemOrDrop(player, flaskIdsArray[0])
                        addItemOrDrop(player, flaskIdsArray[1])
                        if (specialAttribute != null) {
                            setAttribute(player, specialAttribute, getAttribute(player, specialAttribute, 3) - 2)
                        }
                    }
                    optionBuilder.option("Take all three vials.").endWith { _, player ->
                        addItemOrDrop(player, flaskIdsArray[0])
                        addItemOrDrop(player, flaskIdsArray[1])
                        addItemOrDrop(player, flaskIdsArray[2])
                        if (specialAttribute != null) {
                            setAttribute(player, specialAttribute, getAttribute(player, specialAttribute, 3) - 3)
                        }
                    }
                    optionBuilder.option("Don't take a vial.").end()
                }
            branch.onValue(2).line("There are two vials on this shelf.").options("Take the vials?")
                .let { optionBuilder ->
                    optionBuilder.option("Take the first vial.").endWith { _, player ->
                        addItemOrDrop(player, flaskIdsArray[0])
                        if (specialAttribute != null) {
                            setAttribute(player, specialAttribute, getAttribute(player, specialAttribute, 2) - 1)
                        } else {
                            setAttribute(player, MissCheeversRoomListeners.Companion.Vials.vialMap[flaskIdsArray[0]]!!.attribute, true)
                        }
                    }
                    optionBuilder.option("Take the second vial.").endWith { _, player ->
                        addItemOrDrop(player, flaskIdsArray[1])
                        if (specialAttribute != null) {
                            setAttribute(player, specialAttribute, getAttribute(player, specialAttribute, 2) - 1)
                        } else {
                            setAttribute(player, MissCheeversRoomListeners.Companion.Vials.vialMap[flaskIdsArray[1]]!!.attribute, true)
                        }
                    }
                    optionBuilder.option("Take both vials.").endWith { _, player ->
                        addItemOrDrop(player, flaskIdsArray[0])
                        addItemOrDrop(player, flaskIdsArray[1])
                        if (specialAttribute != null) {
                            setAttribute(player, specialAttribute, getAttribute(player, specialAttribute, 2) - 2)
                        } else {
                            setAttribute(player, MissCheeversRoomListeners.Companion.Vials.vialMap[flaskIdsArray[0]]!!.attribute, true)
                            setAttribute(player, MissCheeversRoomListeners.Companion.Vials.vialMap[flaskIdsArray[1]]!!.attribute, true)
                        }
                    }
                }

            branch.onValue(1).line("There is a vial on this shelf.").options("Take the vial?").let { optionBuilder ->
                optionBuilder.option("YES").endWith { _, player ->
                    addItemOrDrop(player, flaskIdsArray[0])
                    if (specialAttribute != null) {
                        setAttribute(player, specialAttribute, getAttribute(player, specialAttribute, 1) - 1)
                    } else {
                        setAttribute(
                            player,
                            MissCheeversRoomListeners.Companion.Vials.vialMap[flaskIdsArray[0]]!!.attribute,
                            true
                        )
                    }
                }
                optionBuilder.option("NO").end()
            }

            branch.onValue(0).line("There is nothing of interest on these shelves.")
        }
    }
}