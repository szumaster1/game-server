package content.region.misc.tutorial.handlers

import core.api.getAttribute
import core.api.sendChat
import core.api.setAttribute
import core.api.submitWorldPulse
import core.game.dialogue.FacialExpression
import core.game.global.action.ClimbActionHandler
import core.game.global.action.DoorActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.scenery.Scenery
import core.game.system.task.Pulse
import core.game.world.map.Location
import core.game.world.repository.Repository
import org.rs.consts.NPCs

class TutorialListener : InteractionListener {

    override fun defineListeners() {

        /*
         * Handling open the guide house door.
         */

        on(RS_GUIDE_DOOR, IntType.SCENERY, "open") { player, node ->
            if (getAttribute(player, "tutorial:stage", 0) != 3)
                return@on true
            setAttribute(player, "tutorial:stage", 4)
            TutorialStage.load(player, 4)
            DoorActionHandler.handleAutowalkDoor(player, node as Scenery, Location.create(3098, 3107, 0))
            return@on true
        }

        /*
         * Handling first stage doors.
         */

        on(WOODEN_GATE, IntType.SCENERY, "open") { player, node ->
            if (getAttribute(player, "tutorial:stage", 0) != 16)
                return@on true
//
            setAttribute(player, "tutorial:stage", 17)
            TutorialStage.load(player, 17)
            DoorActionHandler.autowalkFence(player, node as Scenery, node.asScenery().id, node.asScenery().id)
            return@on true
        }

        /*
         * Handling cook door stage doors.
         */

        on(COOK_GUIDE_DOOR, IntType.SCENERY, "open") { player, node ->
            if (getAttribute(player, "tutorial:stage", 0) != 17)
                return@on true

            setAttribute(player, "tutorial:stage", 18)
            TutorialStage.load(player, 18)
            DoorActionHandler.handleAutowalkDoor(player, node as Scenery)
            return@on true
        }

        /*
         * Handling cook door stage doors.
         */

        on(COOK_GUIDE_DOOR_EXIT, IntType.SCENERY, "open") { player, node ->
            if (getAttribute(player, "tutorial:stage", 0) != 22)
                return@on true

            setAttribute(player, "tutorial:stage", 23)
            TutorialStage.load(player, 23)
            DoorActionHandler.handleAutowalkDoor(player, node as Scenery)
            return@on true
        }

        /*
         * Handling open door leads to the quest guide area.
         */

        on(QUEST_GUIDE_DOOR, IntType.SCENERY, "open") { player, node ->
            if (getAttribute(player, "tutorial:stage", 0) != 26)
                return@on true

            setAttribute(player, "tutorial:stage", 27)
            TutorialStage.load(player, 27)
            DoorActionHandler.handleAutowalkDoor(player, node as Scenery)
            return@on true
        }

        /*
         * Handling climb the quest ladder.
         */

        on(QUEST_LADDER_DOWN, IntType.SCENERY, "climb-down") { player, node ->
            if (getAttribute(player, "tutorial:stage", 0) < 29)
                return@on true

            if (getAttribute(player, "tutorial:stage", 0) == 29) {
                setAttribute(player, "tutorial:stage", 30)
                TutorialStage.load(player, 30)
            }
            ClimbActionHandler.climbLadder(player, node.asScenery(), "climb-down")
        }

        /*
         * Handling exit from the quest stage.
         */

        on(QUEST_LADDER_UP, IntType.SCENERY, "climb-up") { player, node ->
            ClimbActionHandler.climbLadder(player, node.asScenery(), "climb-up")
            submitWorldPulse(object : Pulse(2) {
                override fun pulse(): Boolean {
                    val questTutor = Repository.findNPC(NPCs.QUEST_GUIDE_949) ?: return true
                    sendChat(questTutor, "What are you doing, ${player.username}? Get back down the ladder.")
                    return true
                }
            })

            return@on true
        }

        /*
         * Handling open the combat gates.
         */

        on(COMBAT_GATE, IntType.SCENERY, "open") { player, node ->
            if (getAttribute(player, "tutorial:stage", 0) != 43)
                return@on true
            setAttribute(player, "tutorial:stage", 44)
            TutorialStage.load(player, 44)
            DoorActionHandler.handleAutowalkDoor(player, node as Scenery)
        }

        /*
         * Handling open the rat gates.
         */

        on(GIANT_RAT_GATE, IntType.SCENERY, "open") { player, node ->
            val stage = getAttribute(player, "tutorial:stage", 0)
            if (stage !in 50..53) {
                player.dialogueInterpreter.sendDialogues(
                    NPCs.COMBAT_INSTRUCTOR_944,
                    FacialExpression.ANGRY,
                    "Oi, get away from there!",
                    "Don't enter my rat pen unless I say so!"
                )
                return@on true
            }
            if (stage == 50) {
                setAttribute(player, "tutorial:stage", 51)
                TutorialStage.load(player, 51)
            }
            DoorActionHandler.handleAutowalkDoor(player, node as Scenery)
            return@on true
        }

        /*
         * Handling the climb up from combat tutorial.
         */

        on(COMBAT_LADDER, IntType.SCENERY, "climb-up") { player, node ->
            if (getAttribute(player, "tutorial:stage", 0) != 55)
                return@on true

            setAttribute(player, "tutorial:stage", 56)
            TutorialStage.load(player, 56)
            ClimbActionHandler.climbLadder(player, node.asScenery(), "climb-up")
        }

        /*
         * Handling the open door lead to a bank guide.
         */

        on(BANK_GUIDE_DOOR, IntType.SCENERY, "open") { player, node ->
            if (getAttribute(player, "tutorial:stage", 0) != 57)
                return@on true

            setAttribute(player, "tutorial:stage", 58)
            TutorialStage.load(player, 58)
            DoorActionHandler.handleAutowalkDoor(player, node as Scenery)
        }

        /*
         * Handling opening the finance exit.
         */

        on(FINANCE_GUIDE_DOOR, IntType.SCENERY, "open") { player, node ->
            if (getAttribute(player, "tutorial:stage", 0) != 59)
                return@on true

            setAttribute(player, "tutorial:stage", 60)
            TutorialStage.load(player, 60)
            DoorActionHandler.handleAutowalkDoor(player, node as Scenery)
        }

        /*
         * Handling opening the church exit.
         */

        on(CHURCH_DOOR, IntType.SCENERY, "open") { player, node ->
            if (getAttribute(player, "tutorial:stage", 0) != 66)
                return@on true

            setAttribute(player, "tutorial:stage", 67)
            TutorialStage.load(player, 67)
            DoorActionHandler.handleAutowalkDoor(player, node as Scenery)
        }

    }

    companion object {
        private const val RS_GUIDE_DOOR = org.rs.consts.Scenery.DOOR_3014
        private const val COOK_GUIDE_DOOR = org.rs.consts.Scenery.DOOR_3017
        private const val COOK_GUIDE_DOOR_EXIT = org.rs.consts.Scenery.DOOR_3018
        private const val QUEST_GUIDE_DOOR = org.rs.consts.Scenery.DOOR_3019
        private const val QUEST_LADDER_DOWN = org.rs.consts.Scenery.LADDER_3029
        private const val QUEST_LADDER_UP = org.rs.consts.Scenery.LADDER_3028
        private const val COMBAT_LADDER = org.rs.consts.Scenery.LADDER_3030
        private const val BANK_GUIDE_DOOR = org.rs.consts.Scenery.DOOR_3024
        private const val FINANCE_GUIDE_DOOR = org.rs.consts.Scenery.DOOR_3025
        private const val CHURCH_DOOR = org.rs.consts.Scenery.DOOR_3026
        private val WOODEN_GATE = intArrayOf(org.rs.consts.Scenery.GATE_3015, org.rs.consts.Scenery.GATE_3016)
        private val COMBAT_GATE = intArrayOf(org.rs.consts.Scenery.GATE_3020, org.rs.consts.Scenery.GATE_3021)
        private val GIANT_RAT_GATE = intArrayOf(org.rs.consts.Scenery.GATE_3022, org.rs.consts.Scenery.GATE_3023)
    }
}