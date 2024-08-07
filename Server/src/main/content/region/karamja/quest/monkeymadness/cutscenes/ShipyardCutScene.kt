package content.region.karamja.quest.monkeymadness.cutscenes

import core.api.consts.Components
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.openOverlay
import core.game.activity.Cutscene
import core.game.component.Component
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Direction
import core.game.world.map.Location

/**
 * Shipyard cut scene.
 */
class ShipyardCutScene(player: Player) : Cutscene(player) {

    val interfaceId = Components.QUEST_COMPLETE_SCROLL_277

    override fun setup() {
        setExit(player.location.transform(0, 0, 0))
        if (player.settings.isRunToggled) {
            player.settings.toggleRun()
        }
        loadRegion(11823)
        addNPC(NPCs.FOREMAN_1470, 49, 49, Direction.WEST)
        addNPC(NPCs.GLO_CARANOCK_1427, 47, 49, Direction.EAST)
    }

    override fun runStage(stage: Int) {
        when (stage) {
            0 -> {
                openOverlay(player, Components.FADE_TO_BLACK_120)
                player.interfaceManager.open(Component(interfaceId))
                player.packetDispatch.sendItemZoomOnInterface(Items.MSPEAK_AMULET_4022, 230, interfaceId, 5)
                for (i in 0..17) {
                    when (i) {
                        9 -> player.packetDispatch.sendString("Meanwhile, far away in Karamja...", interfaceId, i)
                        else -> player.packetDispatch.sendString("", interfaceId, i)
                    }
                }

                GameWorld.Pulser.submit(object : Pulse(4) {
                    override fun pulse(): Boolean {
                        teleport(player, 48, 53)
                        moveCamera(48, 53)
                        rotateCamera(48, 50)
                        return true
                    }
                })

                GameWorld.Pulser.submit(object : Pulse(6) {
                    override fun pulse(): Boolean {
                        openOverlay(player, Components.FADE_FROM_BLACK_170)
                        player.interfaceManager.close(Component(interfaceId))
                        timedUpdate(4)
                        return true
                    }
                })
            }
            1 -> {
                dialogueUpdate(NPCs.FOREMAN_1470, FacialExpression.WORRIED, "The workers are getting restless, Caranock.")
                timedUpdate(6)
            }
            2 -> {
                dialogueUpdate(NPCs.GLO_CARANOCK_1427, FacialExpression.NEUTRAL, "I know...")
                timedUpdate(4)
            }
            3 -> {
                dialogueUpdate(NPCs.FOREMAN_1470, FacialExpression.WORRIED, "All this talk to Glough being replaced doesn't bode well for ... how shall i put this ... their morale.")
                timedUpdate(8)
            }
            4 -> {
                dialogueUpdate(NPCs.GLO_CARANOCK_1427, FacialExpression.NEUTRAL, "Look, I know.")
                timedUpdate(4)
            }
            5 -> {
                dialogueUpdate(NPCs.FOREMAN_1470, FacialExpression.WORRIED, "Those are all men with children to feed. Famished families. Worried wives. All of us relly on this shipyard.")
                timedUpdate(10)
            }
            6 -> {
                dialogueUpdate(NPCs.FOREMAN_1470, FacialExpression.WORRIED, "If something isn't done soon, there will be revolt. And i won't be able to stop it.")
                timedUpdate(8)
            }
            7 -> {
                dialogueUpdate(NPCs.GLO_CARANOCK_1427, FacialExpression.NEUTRAL, "Stop worrying. I'm working on something -")
                timedUpdate(6)
            }
            8 -> {
                dialogueUpdate(NPCs.FOREMAN_1470, FacialExpression.WORRIED, "What something? You're always working on something. All we ever hear is bad news.")
                timedUpdate(10)
            }
            9 -> {
                dialogueUpdate(NPCs.FOREMAN_1470, FacialExpression.WORRIED, "First Glough disappeared. Then news of a missing squad of the Royal Guard in our area. And what about that human sent by the King?")
                timedUpdate(14)
            }
            10 -> {
                dialogueUpdate(NPCs.GLO_CARANOCK_1427, FacialExpression.NEUTRAL, "The human means nothing. If it becomes too much trouble, I will simply have it ... removed. In the meantime, let it continue to search for what blasted 10th squad.")
                timedUpdate(14)
            }
            11 -> {
                dialogueUpdate(NPCs.FOREMAN_1470, FacialExpression.WORRIED, "I am still worried. What am I meant to tell the men?")
                timedUpdate(8)
            }
            12 -> {
                dialogueUpdate(NPCs.GLO_CARANOCK_1427, FacialExpression.NEUTRAL, "Stop worrying. I'm working on something - Glough left a few of his agents in the Gnome airforce.")
                timedUpdate(12)
            }
            13 -> {
                dialogueUpdate(NPCs.GLO_CARANOCK_1427, FacialExpression.NEUTRAL, "For now tell to you men to continue work on the battleships. Give me some time.")
                timedUpdate(12)
            }
            14 -> {
                dialogueUpdate(NPCs.FOREMAN_1470, FacialExpression.WORRIED, "I hope you're right, Caranock, for your sake. My sake. For all of our sakes...")
                timedUpdate(12)
            }
            15 -> {
                exitLocation = Location.create(2802, 2707, 0)

                end {
                    player.interfaceManager.open(Component(interfaceId))
                    player.packetDispatch.sendItemZoomOnInterface(Items.MSPEAK_AMULET_4022, 230, interfaceId, 5)

                    for (i in 0..17) {
                        when (i) {
                            3 -> player.packetDispatch.sendString("Monkey Madness: Chapter 2", interfaceId, i)
                            9 -> player.packetDispatch.sendString("In which our ${if (player.isMale) "hero fids himself" else "heroine finds herself"} engaging in severe", interfaceId, i)
                            10 -> player.packetDispatch.sendString("quantity of monkey business.", interfaceId, i)
                            else -> player.packetDispatch.sendString("", interfaceId, i)
                        }
                    }
                }
            }
        }
    }
}
