package content.region.misthalin.varrock.museum.handlers

import core.api.*
import cfg.consts.*
import content.region.misthalin.varrock.museum.dialogue.*
import core.game.global.action.ClimbActionHandler
import core.game.global.action.DoorActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.InterfaceListener
import core.game.node.entity.Entity
import core.game.node.entity.player.Player
import core.game.world.map.Location
import core.game.world.map.zone.ZoneBorders
import core.game.world.update.flag.context.Animation

/**
 * Represents the Museum listeners.
 */
class MuseumListeners : InteractionListener, InterfaceListener, MapArea {

    /**
     * This function is triggered when an entity enters a specific area.
     *
     * @param entity The entity that has entered the area.
     */
    override fun areaEnter(entity: Entity) {
        if (entity is Player) {
            val player = entity.asPlayer()
            openOverlay(player, Components.VM_KUDOS_532)
        }
    }

    /**
     * Area leave function to handle the logic when an entity leaves a specific area.
     *
     * @param entity The entity that is leaving the area.
     * @param logout A boolean indicating whether the entity should be logged out.
     */
    override fun areaLeave(entity: Entity, logout: Boolean) {
        if (entity is Player) {
            val player = entity.asPlayer()
            closeOverlay(player)
        }
    }

    override fun defineAreaBorders(): Array<ZoneBorders> {
        return VARROCK_MUSEUM
    }

    override fun defineListeners() {

        /**
         * Varrock museum interactions.
         */
        on(BUTTON_PLUS_PLAQUES, IntType.SCENERY, "study", "push") { player, node ->
            when (node.id) {
                /*
                 * East side.
                 */
                24605 -> openDialogue(player, TalkAboutLizards())
                24606 -> openDialogue(player, TalkAboutTortoises())
                24607 -> openDialogue(player, TalkAboutDragons())
                24608 -> openDialogue(player, TalkAboutWyverns())
                /*
                 * North side
                 */
                24609 -> openDialogue(player, TalkAboutCamels())
                24610 -> openDialogue(player, TalkAboutLeeches())
                24611 -> openDialogue(player, TalkAboutMoles())
                24612 -> openDialogue(player, TalkAboutPenguins())
                /*
                 * West side
                 */
                24613 -> openDialogue(player, TalkAboutSnails())
                24614 -> openDialogue(player, TalkAboutSnakes())
                24615 -> openDialogue(player, TalkAboutMonkeys())
                24616 -> openDialogue(player, TalkAboutSeaSlugs())
                /*
                 * South side.
                 */
                24617 -> openDialogue(player, TalkAboutTerrorBirds())
                24618 -> openDialogue(player, TalkAboutKalphiteQueen())
            }
            return@on true
        }

        on(Items.MUSEUM_MAP_11184, IntType.ITEM, "look-at") { player, _ ->
            openInterface(player, Components.VM_MUSEUM_MAP_527)
            return@on true
        }

        /*
         * Handle the museum stairs.
         */

        on(MUSEUM_STAIRS, IntType.SCENERY, "walk-up", "walk-down") { player, node ->
            when (node.id) {
                24427 -> ClimbActionHandler.climb(player, Animation(-1), Location(3258, 3452, 0))
                else -> ClimbActionHandler.climb(player, Animation(-1), Location(1759, 4958, 0))
            }
            return@on true
        }

        /*
         * Handle the Museum archaeologists doors.
         */

        on(MUSEUM_DOOR, IntType.SCENERY, "open") { player, node ->
            DoorActionHandler.handleAutowalkDoor(player, node.asScenery())
            return@on true
        }

        /*
         * Handle the Varrock digsite gates.
         */

        on(MUSEUM_GATE, IntType.SCENERY, "open") { player, node ->
            if (player.location.y >= 3447) {
                openDialogue(player, MuseumGuardEntranceDialogue())
            } else {
                DoorActionHandler.handleAutowalkDoor(player, node.asScenery())
            }
            return@on true
        }

        /*
         * Handling interaction with tool rack that contain all items available
         * for staff clean off incoming samples.
         */

        on(TOOL_RACK, IntType.SCENERY, "take") { player, node ->
            face(player, node)
            setTitle(player, 5)
            sendDialogueOptions(player, "Which tool would you like?", "Trowel", "Rock pick", "Specimen brush", "Leather gloves", "Leather boots")
            addDialogueAction(player) { _, button ->
                val item: Int = when (button) {
                    2 -> Items.TROWEL_676
                    3 -> Items.ROCK_PICK_675
                    4 -> Items.SPECIMEN_BRUSH_670
                    5 -> Items.LEATHER_GLOVES_1059
                    6 -> Items.LEATHER_BOOTS_1061
                    else -> return@addDialogueAction
                }
                val name: String = item.asItem().name.lowercase()
                val word: String = if (name.startsWith("leather")) "pair of " else ""
                if (!addItem(player, item)) {
                    sendMessage(player, "You don't have enough space in your inventory.")
                } else {
                    sendItemDialogue(player, item, "You take a $word$name from the rack.")
                }
            }
            return@on true
        }

        /*
         * Handle gates on path to digsite.
         */

        on(intArrayOf(Scenery.GATE_24560, Scenery.GATE_24561), IntType.SCENERY, "open") { player, node ->
            if (!isQuestComplete(player, "The Dig Site")) {
                sendMessage(player, "You can't go through there, it's for Dig Site workmen only.")
                sendChat(findLocalNPC(player, NPCs.MUSEUM_GUARD_5942)!!, "Sorry - workman's gate only.")
            } else {
                DoorActionHandler.handleAutowalkDoor(player, node.asScenery())
            }
            return@on true
        }

        /*
         * Handle the information booth.
         */

        on(Scenery.INFORMATION_BOOTH_24452, IntType.SCENERY, "look-at") { player, _ ->
            openDialogue(player, NPCs.INFORMATION_CLERK_5938)
            return@on true
        }

        /*
         * Museum map interaction.
         */

        on(intArrayOf(Scenery.MAP_24390, Scenery.MAP_24391, Scenery.MAP_24392), IntType.SCENERY, "look-at", "take") { player, node ->
            if (getUsedOption(player) == "take") {
                if (!addItem(player, Items.MUSEUM_MAP_11184)) {
                    sendMessage(player, "You don't have enough space in your inventory.")
                }
            } else {
                when (node.id) {
                    Scenery.MAP_24390 -> setAttribute(player, FLOOR_MAP_ATTRIBUTE, "main")
                    Scenery.MAP_24391 -> setAttribute(player, FLOOR_MAP_ATTRIBUTE, "second")
                    Scenery.MAP_24392 -> setAttribute(player, FLOOR_MAP_ATTRIBUTE, "top")
                }
                openInterface(player, Components.VM_MUSEUM_MAP_527)
            }
            return@on true
        }
    }

    override fun defineInterfaceListeners() {

        /*
         * Opens the museum map.
         */

        onOpen(Components.VM_MUSEUM_MAP_527) { player, _ ->
            showMapFloor(player, getAttribute(player, FLOOR_MAP_ATTRIBUTE, "main"))
            removeAttribute(player, FLOOR_MAP_ATTRIBUTE)
            return@onOpen true
        }

        /*
         * Handling the museum map floors.
         */

        on(Components.VM_MUSEUM_MAP_527) { player, _, _, buttonID, _, _ ->
            showMapFloor(
                player, when (buttonID) {
                    in mapButtonsToBasement -> "basement"
                    in mapButtonsToMainFloor -> "main"
                    in mapButtonsToSecondFloor -> "second"
                    in mapButtonsToTopFloor -> "top"
                    else -> return@on true
                }
            )
            return@on true
        }

        onOpen(NATURAL_HISTORY_EXAM_533) { player, component ->
            // The model for each display is confusing as hell. Some are objects and some are NPCs.
            val model = getScenery(1763, 4937, 0)?.definition?.modelIds?.first()
            player.packetDispatch.sendModelOnInterface(model!!, component.id, 3, 0)

            // Showing this child makes child 28 - 31 visible.
            setComponentVisibility(player, component.id, 27, false)

            // The case number to display.
            sendString(player, "1", component.id, 25)

            // The question text.
            sendString(player, "When will the Natural History Quiz be implemented?", component.id, 28)

            // The choices.
            sendString(player, "Never.", component.id, 29)
            sendString(player, "In 2 days.", component.id, 30)
            sendString(player, "After Barbarian Assault.", component.id, 31)
            return@onOpen true
        }

        on(NATURAL_HISTORY_EXAM_533) { player, _, _, buttonID, _, _ ->
            if (buttonID in 29..31) {
                closeInterface(player)
                setVarbit(player, 3637, 1, false)
                playAudio(player, Sounds.VM_GAIN_KUDOS_3653)
                sendNPCDialogue(player, NPCs.ORLANDO_SMITH_5965, "Nice job, mate. That looks about right.")
            }
            return@on true
        }

        on(Components.VM_LECTERN_794) { player, _, _, buttonID, _, _ ->
            when (buttonID) {
                2 -> updateVarbit(player, 1)
                3 -> updateVarbit(player, -1)
                else -> return@on true
            }
            return@on true
        }

        onClose(Components.VM_LECTERN_794) { player, _ ->
            resetVarbit(player)
            return@onClose true
        }
    }

    companion object {
        private val VARROCK_MUSEUM = arrayOf(ZoneBorders(3253, 3442, 3267, 3455), ZoneBorders(1730, 4932, 1788, 4988))
        private val MUSEUM_DOOR = intArrayOf(24565, 24567)
        private val MUSEUM_STAIRS = intArrayOf(24427, 24428)
        private const val MUSEUM_GATE = 24536
        private val BUTTON_PLUS_PLAQUES = (24588..24618).toIntArray()

        private const val TOOL_RACK = 24535
        private const val NATURAL_HISTORY_EXAM_533 = 533
        private val FLOOR_MAP_ATTRIBUTE = "iface:527:floor"

        private val mapButtonsToBasement = intArrayOf(41, 186)
        private val mapButtonsToMainFloor = intArrayOf(117, 120, 187, 188)
        private val mapButtonsToSecondFloor = intArrayOf(42, 44, 152, 153)
        private val mapButtonsToTopFloor = intArrayOf(42, 44, 118, 119)

        private fun updateVarbit(player: Player, value: Int) {
            val currentVarbitValue = getVarbit(player, Vars.VARBIT_VARROCK_MUSEUM_CENSUS)
            setVarbit(player, Vars.VARBIT_VARROCK_MUSEUM_CENSUS, currentVarbitValue + value)
        }

        private fun resetVarbit(player: Player) {
            setVarbit(player, Vars.VARBIT_VARROCK_MUSEUM_CENSUS, 0)
        }

        private fun showMapFloor(player: Player, floor: String) {
            when (floor) {
                "basement" -> {
                    setComponentVisibility(player, Components.VM_MUSEUM_MAP_527, 2, true)
                    setComponentVisibility(player, Components.VM_MUSEUM_MAP_527, 7, false)
                }

                "main" -> {
                    setComponentVisibility(player, Components.VM_MUSEUM_MAP_527, 3, true)
                    setComponentVisibility(player, Components.VM_MUSEUM_MAP_527, 7, true)
                    setComponentVisibility(player, Components.VM_MUSEUM_MAP_527, 2, false)
                }

                "second" -> {
                    setComponentVisibility(player, Components.VM_MUSEUM_MAP_527, 2, true)
                    setComponentVisibility(player, Components.VM_MUSEUM_MAP_527, 5, true)
                    setComponentVisibility(player, Components.VM_MUSEUM_MAP_527, 3, false)
                }

                "top" -> {
                    setComponentVisibility(player, Components.VM_MUSEUM_MAP_527, 3, true)
                    setComponentVisibility(player, Components.VM_MUSEUM_MAP_527, 5, false)
                }
            }
        }
    }
}
