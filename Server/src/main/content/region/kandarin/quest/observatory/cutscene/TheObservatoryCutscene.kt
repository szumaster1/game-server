package content.region.kandarin.quest.observatory.cutscene

import core.api.*
import cfg.consts.Animations
import cfg.consts.NPCs
import cfg.consts.Vars
import core.game.activity.Cutscene
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.game.world.map.Direction
import core.game.world.map.Location
import core.tools.DARK_RED

/**
 * The observatory cutscene.
 */
class TheObservatoryCutscene(player: Player) : Cutscene(player) {

    override fun setup() {
        setExit(END_LOCATION)
        if (player.settings.isRunToggled) {
            player.settings.toggleRun()
        }
        loadRegion(OBSERVATORY)
        addNPC(PROFESSOR, 7, 25, Direction.SOUTH)
    }

    override fun runStage(stage: Int) {
        when (stage) {
            0 -> {
                fadeToBlack()
                timedUpdate(6)
            }

            1 -> {
                teleport(player, 6, 27)
                fadeFromBlack()
                moveCamera(13, 30, 500, 100)
                rotateCamera(10, 27, 500, 100)
                timedUpdate(3)
            }

            2 -> {
                sendDialogueLines(player, "${DARK_RED}~ The Observatory ~", "The great eye into the heavens.")
                timedUpdate(6)
            }

            3 -> {
                resetCamera()
                moveCamera(6, 22, 300, 100)
                rotateCamera(11, 26, 300, 100)
                playerDialogueUpdate(FacialExpression.HAPPY, "Hi, professor!")
            }

            4 -> {
                face(getNPC(PROFESSOR)!!, player, 2)
                animate(getNPC(PROFESSOR)!!, Animations.HUMAN_WAVE_863)
                dialogueUpdate(PROFESSOR, FacialExpression.HAPPY, "Oh, hi there.")
            }

            5 -> dialogueUpdate(PROFESSOR, FacialExpression.FRIENDLY, "I'm just adding the finishing touches.")
            6 -> playerDialogueUpdate(FacialExpression.FRIENDLY, "Okay, don't let me interrupt!")
            7 -> dialogueUpdate(PROFESSOR, FacialExpression.FRIENDLY, "Thank you.")
            8 -> dialogueUpdate(PROFESSOR, FacialExpression.FRIENDLY, "Right, let's get this finished.")
            9 -> {
                animate(getNPC(PROFESSOR)!!, Animations.HUMAN_THINK_857)
                sendChat(getNPC(PROFESSOR)!!, "Hmmmm...")
                timedUpdate(4)
            }

            10 -> {
                move(getNPC(PROFESSOR)!!, 7, 23)
                timedUpdate(2)
            }

            11 -> {
                move(getNPC(PROFESSOR)!!, 10, 23)
                timedUpdate(2)
            }

            12 -> {
                move(getNPC(PROFESSOR)!!, 10, 25)
                timedUpdate(4)
            }

            13 -> {
                face(getNPC(PROFESSOR)!!, player, 3)
                animate(getNPC(PROFESSOR)!!, Animations.HUMAN_FIX_TELESCOPE_6847)
                sendChat(getNPC(PROFESSOR)!!, "Bit of a tap here...")
                timedUpdate(8)
            }

            14 -> {
                move(getNPC(PROFESSOR)!!, 11, 25)
                timedUpdate(2)
            }

            15 -> {
                face(getNPC(PROFESSOR)!!, player, 2)
                dialogueUpdate(
                    PROFESSOR,
                    FacialExpression.FRIENDLY,
                    "${player.username}, I'm just going upstairs to finish off."
                )
            }

            16 -> playerDialogueUpdate(FacialExpression.FRIENDLY, "Right-oh.")
            17 -> {
                resetCamera()
                teleport(player, 6, 23, 1)
                teleport(getNPC(PROFESSOR)!!, 10, 23, 1)
                moveCamera(6, 23, 400, 100)
                rotateCamera(10, 23, 400, 100)
                timedUpdate(5)
            }

            18 -> {
                move(getNPC(PROFESSOR)!!, 7, 23)
                timedUpdate(10)
            }

            19 -> {
                animate(getNPC(PROFESSOR)!!, Animations.HUMAN_FIX_TELESCOPE_6848)
                sendChat(getNPC(PROFESSOR)!!, "In here the lens.")
                timedUpdate(6)
            }

            20 -> dialogueUpdate(PROFESSOR, FacialExpression.FRIENDLY, "Looking good.")
            21 -> {
                move(getNPC(PROFESSOR)!!, 7, 29)
                sendChat(getNPC(PROFESSOR)!!, "And one final adjustment.")
                timedUpdate(4)
            }

            22 -> {
                move(getNPC(PROFESSOR)!!, 10, 26)
                timedUpdate(4)
            }

            23 -> {
                animate(getNPC(PROFESSOR)!!, Animations.HUMAN_FIX_TELESCOPE_6845)
                timedUpdate(6)
            }

            24 -> {
                sendChat(getNPC(PROFESSOR)!!, "And all our work pays off.")
                timedUpdate(3)
            }

            25 -> {
                animate(getNPC(PROFESSOR)!!, Animations.HUMAN_CHEER_862)
                timedUpdate(3)
            }

            26 -> {
                end()
                setVarbit(player, Vars.VARBIT_QUEST_OBSERVATORY_TELESCOPE_REPAIRED, 1, true)
                setQuestStage(player, "Observatory Quest", 13)
            }
        }
    }

    companion object {
        val PROFESSOR = NPCs.OBSERVATORY_PROFESSOR_6121
        val OBSERVATORY = 9777
        val END_LOCATION = Location(2438, 3163, 0)
    }
}
