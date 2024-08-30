package content.region.kandarin.port_khazard.quest.arena.cutscenes

import content.region.kandarin.port_khazard.quest.arena.FightArenaListeners.Companion.Jeremy
import content.region.kandarin.quest.arena.npc.OgreNPC.Companion.spawnOgre
import core.api.*
import cfg.consts.Animations
import cfg.consts.NPCs
import core.game.activity.Cutscene
import core.game.dialogue.FacialExpression
import core.game.global.action.DoorActionHandler
import core.game.node.entity.player.Player
import core.game.world.map.Direction

/**
 * Jeremy rescue cutscene.
 */
class JeremyRescueCutscene(player: Player) : Cutscene(player) {
    override fun setup() {
        setExit(location(2603, 3155, 0))
        if (player.settings.isRunToggled) {
            player.settings.toggleRun()
        }
    }

    override fun runStage(stage: Int) {
        when (stage) {
            0 -> {
                face(player, Jeremy, 1)
                playerDialogueUpdate(FacialExpression.NEUTRAL, "Jeremy look, I have the keys.")
            }

            1 -> {
                face(Jeremy, player, 1)
                dialogueUpdate(
                    NPCs.JEREMY_SERVIL_265,
                    FacialExpression.CHILD_NORMAL,
                    "Wow! Please set me free, then we can find my dad. I overheard a guard talking. I think they're taken him to the arena."
                )
            }

            2 -> {
                resetFace(Jeremy)
                resetFace(player)
                playerDialogueUpdate(FacialExpression.NEUTRAL, "Ok, we'd better hurry.")
            }

            3 -> {
                move(Jeremy, 56, 31)
                face(player, location(2616, 3167, 0))
                animate(player, Animations.HUMAN_OPEN_CELL_DOOR_2098, forced = true)
                timedUpdate(2, 4)
            }

            4 -> {
                move(player, 57, 32)
                timedUpdate(1)
            }

            5 -> {
                DoorActionHandler.handleAutowalkDoor(Jeremy, getObject(57, 31, 0)!!)
                timedUpdate(3)
            }

            6 -> {
                face(player, Jeremy, 1)
                sendChat(Jeremy, "I'll run ahead.")
                move(Jeremy, 57, 20)
                timedUpdate(6)
            }

            7 -> {
                teleport(Jeremy, 56, 31)
                loadRegion(10289)
                addNPC(content.region.kandarin.port_khazard.quest.arena.cutscenes.JeremyRescueCutscene.Companion.JEREMYRESCUE, 41, 17, Direction.NORTH)
                addNPC(content.region.kandarin.port_khazard.quest.arena.cutscenes.JeremyRescueCutscene.Companion.GENERAL, 45, 19, Direction.NORTH)
                addNPC(content.region.kandarin.port_khazard.quest.arena.cutscenes.JeremyRescueCutscene.Companion.OGRE, 48, 30, Direction.NORTH)
                addNPC(content.region.kandarin.port_khazard.quest.arena.cutscenes.JeremyRescueCutscene.Companion.JUSTIN, 41, 32, Direction.EAST)
                timedUpdate(-1, 8)
            }

            8 -> {
                teleport(player, 47, 15)
                moveCamera(47, 20)
                rotateCamera(45, 15)
                timedUpdate(2)
            }

            9 -> {
                DoorActionHandler.handleAutowalkDoor(player, getObject(46, 16)!!)
                timedUpdate(1)
            }

            10 -> {
                moveCamera(41, 26, 300, 4)
                rotateCamera(45, 15, 300, 4)
                timedUpdate(1)
            }

            11 -> {
                move(player, 43, 18)
                timedUpdate(1)
            }

            12 -> {
                move(player, 43, 19)
                timedUpdate(6)
            }

            13 -> {
                face(player, getNPC(content.region.kandarin.port_khazard.quest.arena.cutscenes.JeremyRescueCutscene.Companion.JEREMYRESCUE)!!, 1)
                playerDialogueUpdate(FacialExpression.HALF_ASKING, "Jeremy, where's your father?")
            }

            14 -> {
                move(getNPC(content.region.kandarin.port_khazard.quest.arena.cutscenes.JeremyRescueCutscene.Companion.JUSTIN)!!, 42, 32)
                face(getNPC(content.region.kandarin.port_khazard.quest.arena.cutscenes.JeremyRescueCutscene.Companion.JEREMYRESCUE)!!, player, 1)
                dialogueUpdate(
                    content.region.kandarin.port_khazard.quest.arena.cutscenes.JeremyRescueCutscene.Companion.JEREMYRESCUE,
                    FacialExpression.CHILD_SAD,
                    "Quick help him! That beast will kill him. He's too old to fight."
                )
            }

            15 -> {
                moveCamera(40, 25, 400)
                rotateCamera(44, 29)
                teleport(getNPC(content.region.kandarin.port_khazard.quest.arena.cutscenes.JeremyRescueCutscene.Companion.OGRE)!!, 47, 29)
                registerHintIcon(player, getNPC(content.region.kandarin.port_khazard.quest.arena.cutscenes.JeremyRescueCutscene.Companion.OGRE)!!)
                timedUpdate(-1, 16)
            }

            16 -> {
                DoorActionHandler.handleAutowalkDoor(getNPC(content.region.kandarin.port_khazard.quest.arena.cutscenes.JeremyRescueCutscene.Companion.OGRE)!!, getObject(46, 29)!!)
                timedUpdate(2)
            }

            17 -> {
                move(getNPC(content.region.kandarin.port_khazard.quest.arena.cutscenes.JeremyRescueCutscene.Companion.OGRE)!!, 45, 29)
                move(getNPC(content.region.kandarin.port_khazard.quest.arena.cutscenes.JeremyRescueCutscene.Companion.OGRE)!!, 43, 31)
                timedUpdate(3)
            }

            18 -> {
                rotateCamera(44, 30)
                timedUpdate(1)
            }

            19 -> {
                rotateCamera(44, 38)
                timedUpdate(1)
            }

            20 -> {
                getNPC(content.region.kandarin.port_khazard.quest.arena.cutscenes.JeremyRescueCutscene.Companion.OGRE)!!.faceLocation(getNPC(
                    content.region.kandarin.port_khazard.quest.arena.cutscenes.JeremyRescueCutscene.Companion.JUSTIN
                )!!.location)
                animate(getNPC(content.region.kandarin.port_khazard.quest.arena.cutscenes.JeremyRescueCutscene.Companion.OGRE)!!, 359, forced = true)
                animate(getNPC(content.region.kandarin.port_khazard.quest.arena.cutscenes.JeremyRescueCutscene.Companion.JUSTIN)!!, 404, forced = true)
                timedUpdate(1)
            }

            21 -> {
                end {
                    clearHintIcon(player)
                    spawnOgre(player)
                }
            }
        }
    }

    companion object {
        private const val GENERAL = 258
        private const val JEREMYRESCUE = 266
        private const val JUSTIN = 267
        private const val OGRE = 270
    }
}

