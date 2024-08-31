package content.region.kandarin.port_khazard.quest.arena.cutscenes

import content.region.kandarin.port_khazard.quest.arena.dialogue.HengradDialogueFile
import core.api.face
import core.api.location
import core.api.openDialogue
import core.game.activity.Cutscene
import core.game.dialogue.FacialExpression
import core.game.global.action.DoorActionHandler
import core.game.node.entity.player.Player
import core.game.world.map.Direction

/**
 * Jail after fight cutscene.
 */
class JailAfterFightCutscene(player: Player) : Cutscene(player) {
    override fun setup() {
        setExit(location(2600, 3142, 0))
        if (player.settings.isRunToggled) {
            player.settings.toggleRun()
        }
        loadRegion(10289)
    }

    override fun runStage(stage: Int) {
        when (stage) {
            0 -> {
                addNPC(content.region.kandarin.port_khazard.quest.arena.cutscenes.JailAfterFightCutscene.Companion.KHAZARD_GUARD, 48, 5, Direction.WEST)
                teleport(player, 46, 5)
                timedUpdate(1)
            }

            1 -> {
                moveCamera(43, 3)
                rotateCamera(20, 20)
                timedUpdate(1)
            }

            2 -> {
                move(player, 45, 5)
                timedUpdate(1)
            }

            3 -> {
                move(player, 40, 5)
                move(getNPC(content.region.kandarin.port_khazard.quest.arena.cutscenes.JailAfterFightCutscene.Companion.KHAZARD_GUARD)!!, 41, 5)
                timedUpdate(6)
            }

            4 -> {
                DoorActionHandler.handleAutowalkDoor(player, getObject(40, 5)!!)
                timedUpdate(5)
            }

            5 -> {
                move(player, 40, 6)
                move(getNPC(content.region.kandarin.port_khazard.quest.arena.cutscenes.JailAfterFightCutscene.Companion.KHAZARD_GUARD)!!, 40, 5)
                timedUpdate(2)
            }

            6 -> {
                face(getNPC(content.region.kandarin.port_khazard.quest.arena.cutscenes.JailAfterFightCutscene.Companion.KHAZARD_GUARD)!!, player)
                dialogueUpdate(content.region.kandarin.port_khazard.quest.arena.cutscenes.JailAfterFightCutscene.Companion.KHAZARD_GUARD, FacialExpression.FRIENDLY, "The General seems to have taken a liking to you. He'd normally kill imposters like you without a second thought.")
            }

            7 -> {
                end { openDialogue(player, HengradDialogueFile()) }
            }
        }
    }

    companion object {
        private const val KHAZARD_GUARD = 257
    }
}

