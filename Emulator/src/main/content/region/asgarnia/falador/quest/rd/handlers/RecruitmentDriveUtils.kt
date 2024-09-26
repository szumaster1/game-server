package content.region.asgarnia.falador.quest.rd.handlers

import org.rs.consts.*
import core.api.*
import core.game.interaction.QueueStrength
import core.game.node.Node
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation

/**
 * Utility functions for Recruitment Drive quest.
 */
object RecruitmentDriveUtils {

    /*
     * Varbits for "Chicken, Fox and Grain" puzzle.
     */
    const val VARBIT_FOX_EAST = 680
    const val VARBIT_FOX_WEST = 681
    const val VARBIT_CHICKEN_EAST = 682
    const val VARBIT_CHICKEN_WEST = 683
    const val VARBIT_GRAIN_EAST = 684
    const val VARBIT_GRAIN_WEST = 685

    /*
     * Varbit for Sir Leye NPC.
     */
    const val ATTRIBUTE_NPC_SPAWN = "rd:generatedsirleye"

    /**
     * Gets the location associated with a node.
     *
     * @param node The node for which the location is to be retrieved.
     * @return The location corresponding to the scenery node.
     */
    fun getLocationForScenery(node: Node): Location {
        return when (node.asScenery().id) {
            Scenery.CRATE_7347 -> Location(2476, 4943)
            Scenery.CRATE_7348 -> Location(2476, 4937)
            Scenery.CRATE_7349 -> Location(2475, 4943)
            else -> Location(0, 0)
        }
    }

    /**
     * Utility for process item usage.
     *
     * @param player The player using the item.
     * @param used The item that is being used.
     * @param with The item that is being used with.
     * @param newItem The new item that will replace the used item.
     */
    fun processItemUsage(player: Player, used: Item, with: Item, newItem: Item) {
        replaceSlot(player, slot = used.index, Item(newItem.id))
        replaceSlot(player, slot = with.index, Item(Items.VIAL_229))
        animate(player, Animation(Animations.HUMAN_USE_PESTLE_AND_MORTAR_364))
        playAudio(player, Sounds.VIALPOUR_2613)
        sendMessage(player, "You empty the vial into the tin.")
    }

    /**
     * Handles the usage of a vial.
     *
     * @param player The player using the vial.
     * @param used The vial that is being used.
     */
    fun handleVialUsage(player: Player, used: Item) {
        lock(player, 5)
        lockInteractions(player, 5)

        if (removeItem(player, used.id)) {
            animate(player, Animation(Animations.POUR_VIAL_2259))
            playAudio(player, Sounds.VIALPOUR_2613)

            val doorVial = MissCheeversRoomListener.Companion.DoorVials.doorVialsMap[used.id]
            if (doorVial != null) {
                setAttribute(player, doorVial.attribute, true)
                sendMessage(player, "You pour the vial onto the flat part of the spade.")
                addItem(player, Items.VIAL_229)
            } else {
                sendMessage(player, "The vial has no effect.")
            }
        } else {
            sendMessage(player, "You do not have the vial to use.")
        }

        if (MissCheeversRoomListener.Companion.DoorVials.doorVialsRequiredMap.all { getAttribute(player, it.value.attribute, false) }) {
            animate(player, Animation(2259))
            playAudio(player, Sounds.VIALPOUR_2613)
            sendMessage(player, "Something caused a reaction when mixed!")
            sendMessage(player, "The spade gets hotter, and expands slightly.")
            setVarbit(player, MissCheeversRoomListener.doorVarbit, 2)
        }
    }

    /**
     * Handles pull the metal spade.
     *
     * @param player The player pulling the spade.
     */
    fun handleSpadePull(player: Player) {
        lock(player, 3)
        lockInteractions(player, 3)

        if (MissCheeversRoomListener.Companion.DoorVials.doorVialsRequiredMap.all { getAttribute(player, it.value.attribute, false) }) {
            sendMessage(player, "You pull on the spade...")
            sendMessage(player, "It works as a handle, and you swing the stone door open.")
            setVarbit(player, MissCheeversRoomListener.doorVarbit, 3)
        } else {
            sendMessage(player, "You pull on the spade...")
            sendMessage(player, "It comes loose, and slides out of the hole in the stone.")
            addItemOrDrop(player, Items.METAL_SPADE_5587)
            setVarbit(player, MissCheeversRoomListener.doorVarbit, 0)
        }
    }

    /**
     * Handles the walk through a metal door.
     *
     * @param player The player.
     */
    fun handleDoorWalkThrough(player: Player) {
        when {
            inBorders(player, 2476, 4941, 2477, 4939) ->
                forceMove(player, player.location, Location(2478, 4940, 0), 20, 80)
            inBorders(player, 2477, 4941, 2478, 4939) ->
                forceMove(player, player.location, Location(2476, 4940, 0), 20, 80)
        }
    }

    /**
     * A helper function for searching.
     *
     * @param player The player performing the search.
     * @param attributeCheck The attribute to check for the player.
     * @param item The item to add if the search is successful.
     * @param searchingDescription The description to show while searching.
     * @param objectDescription The description to show if the search is successful.
     */
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

    /**
     * Processes item usage and returns the result item to the player.
     *
     * @param player The player using the item.
     * @param used The item that is being used.
     * @param with The item that is being used with.
     * @param resultItem The item that will be returned as a result.
     */
    fun processItemUsageAndReturn(player: Player, used: Item, with: Item, resultItem: Item) {
        processItemUsage(player, used.asItem(), with.asItem(), resultItem)
    }

    /**
     * Resets the stage for the given player.
     *
     * @param player The player.
     */
    fun resetStage(player: Player) {
        setVarbit(player, VARBIT_FOX_EAST, 0)
        setVarbit(player, VARBIT_FOX_WEST, 0)
        setVarbit(player, VARBIT_CHICKEN_EAST, 0)
        setVarbit(player, VARBIT_CHICKEN_WEST, 0)
        setVarbit(player, VARBIT_GRAIN_EAST, 0)
        setVarbit(player, VARBIT_GRAIN_WEST, 0)
        removeItem(player, Items.GRAIN_5607, Container.EQUIPMENT)
        removeItem(player, Items.FOX_5608, Container.EQUIPMENT)
        removeItem(player, Items.CHICKEN_5609, Container.EQUIPMENT)
    }

    /**
     * Spawn boss
     *
     * @param player The player.
     */
    fun spawnBoss(player: Player) {
        var boss = getAttribute(player, ATTRIBUTE_NPC_SPAWN, NPC(0))
        setAttribute(player, ATTRIBUTE_NPC_SPAWN, boss)

        if (boss.id != 0) {
            boss.clear()
        }

        boss = NPC.create(NPCs.SIR_LEYE_2285, player.location).apply {
            this.isRespawn = false
            this.isWalks = true
            this.isAggressive = true
            this.location = Location.create(2457, 4966, 0)
            this.init()
        }
        registerHintIcon(player, boss)
        sendChat(boss, "No man may defeat me!")
        boss.asNpc().attack(player)
    }
}