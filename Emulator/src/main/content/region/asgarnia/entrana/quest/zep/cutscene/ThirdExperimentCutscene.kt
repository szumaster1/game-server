package content.region.asgarnia.entrana.quest.zep.cutscene

import org.rs.consts.Music
import org.rs.consts.NPCs
import core.api.location
import core.api.setQuestStage
import core.game.activity.Cutscene
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.game.world.map.Direction
import org.rs.consts.QuestName

/**
 * Represents the Third experiment cutscene.
 */
class ThirdExperimentCutscene(player: Player) : Cutscene(player) {
    override fun setup() {
        setExit(location(2808, 3355, 0))
        if (player.settings.isRunToggled) {
            player.settings.toggleRun()
        }
        loadRegion(11060)
    }

    override fun runStage(stage: Int) {
        when (stage) {
            0 -> {
                fadeToBlack()
                timedUpdate(6)
            }

            1 -> {
                fadeFromBlack()
                teleport(player, 56, 27)
                player.faceLocation(location(184, 26, 0))
                addNPC(AUGUSTE, 57, 27, Direction.SOUTH_WEST)
                timedUpdate(5)
            }

            2 -> {
                playerDialogueUpdate(FacialExpression.FRIENDLY, "Well, that went down like a lead balloon.")
            }

            3 -> {
                end()
                setQuestStage(player, QuestName.ENLIGHTENED_JOURNEY, 9)
                player.musicPlayer.unlock(Music.FLOATING_FREE_206, true)
            }
        }
    }

    companion object {
        private const val AUGUSTE = NPCs.AUGUSTE_5049
    }
}

