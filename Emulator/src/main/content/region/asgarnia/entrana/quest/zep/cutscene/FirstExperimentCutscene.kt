package content.region.asgarnia.entrana.quest.zep.cutscene

import org.rs.consts.Animations
import org.rs.consts.NPCs
import core.api.face
import core.api.location
import core.api.setQuestStage
import core.api.visualize
import core.game.activity.Cutscene
import core.game.dialogue.FacialExpression
import core.game.node.entity.impl.Projectile
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.world.map.Direction

/**
 * Represents the First experiment cutscene.
 */
class FirstExperimentCutscene(player: Player) : Cutscene(player) {
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

            2 -> {
                fadeFromBlack()
                teleport(player, 56, 27)
                rotateCamera(55, 25)
                player.faceLocation(location(184, 26, 0))
                addNPC(NPCs.AUGUSTE_5049, 57, 27, Direction.SOUTH_WEST)
                timedUpdate(3)
            }

            3 -> {
                visualize(player, Animations.HUMAN_RELEASE_A_BALLOON_5142, 880)
                timedUpdate(2)
            }

            4 -> {
                Projectile.create(player, null, 881, 45, 45, 1, 70, 0).transform(
                    player,
                    player.location.transform(Direction.SOUTH, player.direction.ordinal + 1),
                    false,
                    70,
                    140
                ).send()
                timedUpdate(3)
            }

            5 -> {
                moveCamera(55, 22)
                rotateCamera(54, 27)
                timedUpdate(4)
            }

            6 -> {
                face(player, AUGUSTE, 2)
                timedUpdate(1)
            }

            7 -> {
                face(AUGUSTE, player, 2)
                timedUpdate(1)
            }

            8 -> {
                dialogueUpdate(NPCs.AUGUSTE_5049, FacialExpression.HAPPY, "That was perfect. My hypothesis was right!")
            }

            9 -> {
                playerDialogueUpdate(FacialExpression.SCARED, "Did you not see the burning?")
            }

            10 -> {
                dialogueUpdate(NPCs.AUGUSTE_5049, FacialExpression.HAPPY, "One more test. Then we shall proceed.")
            }

            11 -> {
                playerDialogueUpdate(FacialExpression.SCARED, "Burning? Fire? Hello?")
            }

            12 -> {
                dialogueUpdate(
                    NPCs.AUGUSTE_5049,
                    FacialExpression.HAPPY,
                    "We shall meekly go! No...no...it needs to sound grander. We shall cautiously go..."
                )
            }

            13 -> {
                playerDialogueUpdate(FacialExpression.SCARED, "We're doomed.")
            }

            14 -> {
                end()
                setQuestStage(player, "Enlightened Journey", 4)
            }
        }
    }

    companion object {
        private val AUGUSTE = NPC(NPCs.AUGUSTE_5049)
    }
}

