package content.region.misthalin.handlers.varrock

import content.region.misthalin.dialogue.varrock.museum.*
import core.api.*
import core.api.consts.Items
import core.game.global.action.ClimbActionHandler
import core.game.global.action.DoorActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation

class MuseumListeners : InteractionListener {

    override fun defineListeners() {
        on(BUTTONS, IntType.SCENERY, "study") { player, node ->
            when (node.id) {
                // East
                24605 -> openDialogue(player, TalkAboutLizards())
                24606 -> openDialogue(player, TalkAboutTortoises())
                24607 -> openDialogue(player, TalkAboutDragons())
                24608 -> openDialogue(player, TalkAboutWyverns())
                // North
                24609 -> openDialogue(player, TalkAboutCamels())
                24610 -> openDialogue(player, TalkAboutLeeches())
                24611 -> openDialogue(player, TalkAboutMoles())
                24612 -> openDialogue(player, TalkAboutPenguins())
                // West
                24613 -> openDialogue(player, TalkAboutSnails())
                24614 -> openDialogue(player, TalkAboutSnakes())
                24615 -> openDialogue(player, TalkAboutMonkeys())
                24616 -> openDialogue(player, TalkAboutSeaSlugs())
                // South
                24617 -> openDialogue(player, TalkAboutTerrorBirds())
                24618 -> openDialogue(player, TalkAboutKalphiteQueen())
            }
            return@on true
        }

        /*
            Museum stairs.
         */

        on(MUSEUM_STAIRS, IntType.SCENERY, "walk-up", "walk-down") { player, node ->
            if (node.id == 24427) {
                ClimbActionHandler.climb(player, Animation(-1), Location(3258, 3452, 0))
            } else {
                ClimbActionHandler.climb(player, Animation(-1), Location(1759, 4958, 0))
            }
            if (node.id == 24359 && getUsedOption(player) == "climb-down") {
                ClimbActionHandler.climb(player, ClimbActionHandler.CLIMB_DOWN, Location(3231, 3386, 0))
            }
            if (node.id == 24357 && getUsedOption(player) == "climb-up") {
                ClimbActionHandler.climb(player, Animation(828), Location(3155, 3435, 1))
            } else {
                ClimbActionHandler.climb(player, Animation(828), Location(3188, 3354, 1))
            }
            return@on true
        }

        /*
            Museum archaeologists doors.
         */

        on(MUSEUM_DOOR, IntType.SCENERY, "open") { player, node ->
            DoorActionHandler.handleAutowalkDoor(player, node.asScenery())
            return@on true
        }

        /*
            Varrock digsite gates.
         */

        on(MUSEUM_GATE, IntType.SCENERY, "open") { player, node ->
            if (player.location.y >= 3447) {
                openDialogue(player, 5941)
            } else {
                DoorActionHandler.handleAutowalkDoor(player, node.asScenery())
            }
            return@on true
        }

        /*
            All items available for staff clean off incoming samples.
            Author: @joshking071
         */

        on(TOOL_RACK, IntType.SCENERY, "take") { player, node ->
            face(player, node)
            setTitle(player, 5)
            sendDialogueOptions(player, "Which tool would you like?", "Trowel", "Rock pick", "Specimen brush", "Leather gloves", "Leather boots")
            addDialogueAction(player) { _, button ->
                val item = when (button) {
                    2 -> Items.TROWEL_676
                    3 -> Items.ROCK_PICK_675
                    4 -> Items.SPECIMEN_BRUSH_670
                    5 -> Items.LEATHER_GLOVES_1059
                    6 -> Items.LEATHER_BOOTS_1061
                    else -> return@addDialogueAction
                }
                val name = item.asItem().name.lowercase()
                val word = if (name.startsWith("leather")) "pair of " else ""
                if (!addItem(player, item)) {
                    sendMessage(player, "You don't have enough space in your inventory.")
                } else {
                    sendItemDialogue(player, item, "You take a $word$name from the rack.")
                }
            }
            return@on true
        }
    }

    companion object {
        private val BUTTONS = (24605..24618).toIntArray()
        private val MUSEUM_DOOR = intArrayOf(24565, 24567)
        private val MUSEUM_STAIRS = intArrayOf(24427, 24428, 24359, 24357)
        private const val MUSEUM_GATE = 24536
        private const val TOOL_RACK = 24535
    }

}