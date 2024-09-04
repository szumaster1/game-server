package content.region.misc.tutorial.handlers

import core.api.*
import cfg.consts.NPCs
import core.game.dialogue.FacialExpression
import core.game.global.action.ClimbActionHandler
import core.game.global.action.DoorActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.scenery.Scenery
import core.game.system.task.Pulse
import core.game.world.map.Location
import core.game.world.repository.Repository

/**
 * Tutorial listeners.
 */
class TutorialListeners : InteractionListener {

    private val guideDoor = cfg.consts.Scenery.DOOR_3014
    private val cookDoor = cfg.consts.Scenery.DOOR_3017
    private val cooksExit = cfg.consts.Scenery.DOOR_3018
    private val questDoor = cfg.consts.Scenery.DOOR_3019
    private val questLadder_1 = cfg.consts.Scenery.LADDER_3029
    private val questLadder = cfg.consts.Scenery.LADDER_3028
    private val combatLadder = cfg.consts.Scenery.LADDER_3030
    private val bankDoor = cfg.consts.Scenery.DOOR_3024
    private val financeDoor = cfg.consts.Scenery.DOOR_3025
    private val churchDoor = cfg.consts.Scenery.DOOR_3026
    private val firstGatesIDs = intArrayOf(cfg.consts.Scenery.GATE_3015, cfg.consts.Scenery.GATE_3016)
    private val combatGatesIDs = intArrayOf(cfg.consts.Scenery.GATE_3020, cfg.consts.Scenery.GATE_3021)
    private val ratGatesIDs = intArrayOf(cfg.consts.Scenery.GATE_3022, cfg.consts.Scenery.GATE_3023)

    override fun defineListeners() {

        /*
         * Handling open the guide house door.
         */

        on(guideDoor, IntType.SCENERY, "open") { player, node ->
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

        on(firstGatesIDs, IntType.SCENERY, "open") { player, node ->
            if (getAttribute(player, "tutorial:stage", 0) != 16)
                return@on true

            setAttribute(player, "tutorial:stage", 17)
            TutorialStage.load(player, 17)
            DoorActionHandler.handleAutowalkDoor(player, node as Scenery)
            return@on true
        }

        /*
         * Handling cook door stage doors.
         */

        on(cookDoor, IntType.SCENERY, "open") { player, node ->
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

        on(cooksExit, IntType.SCENERY, "open") { player, node ->
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

        on(questDoor, IntType.SCENERY, "open") { player, node ->
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

        on(questLadder_1, IntType.SCENERY, "climb-down") { player, node ->
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

        on(questLadder, IntType.SCENERY, "climb-up") { player, node ->
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

        on(combatGatesIDs, IntType.SCENERY, "open") { player, node ->
            if (getAttribute(player, "tutorial:stage", 0) != 43)
                return@on true
            setAttribute(player, "tutorial:stage", 44)
            TutorialStage.load(player, 44)
            DoorActionHandler.handleAutowalkDoor(player, node as Scenery)
        }

        /*
         * Handling open the rat gates.
         */

        on(ratGatesIDs, IntType.SCENERY, "open") { player, node ->
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

        on(combatLadder, IntType.SCENERY, "climb-up") { player, node ->
            if (getAttribute(player, "tutorial:stage", 0) != 55)
                return@on true

            setAttribute(player, "tutorial:stage", 56)
            TutorialStage.load(player, 56)
            ClimbActionHandler.climbLadder(player, node.asScenery(), "climb-up")
        }

        /*
         * Handling the open door lead to a bank guide.
         */

        on(bankDoor, IntType.SCENERY, "open") { player, node ->
            if (getAttribute(player, "tutorial:stage", 0) != 57)
                return@on true

            setAttribute(player, "tutorial:stage", 58)
            TutorialStage.load(player, 58)
            DoorActionHandler.handleAutowalkDoor(player, node as Scenery)
        }

        /*
         * Handling opening the finance exit.
         */

        on(financeDoor, IntType.SCENERY, "open") { player, node ->
            if (getAttribute(player, "tutorial:stage", 0) != 59)
                return@on true

            setAttribute(player, "tutorial:stage", 60)
            TutorialStage.load(player, 60)
            DoorActionHandler.handleAutowalkDoor(player, node as Scenery)
        }

        /*
         * Handling opening the church exit.
         */

        on(churchDoor, IntType.SCENERY, "open") { player, node ->
            if (getAttribute(player, "tutorial:stage", 0) != 66)
                return@on true

            setAttribute(player, "tutorial:stage", 67)
            TutorialStage.load(player, 67)
            DoorActionHandler.handleAutowalkDoor(player, node as Scenery)
        }

    }
}