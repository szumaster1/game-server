package content.region.morytania.handlers

import content.global.skill.support.agility.AgilityHandler
import core.api.*
import core.api.consts.Components
import core.api.consts.NPCs
import core.api.consts.Scenery
import core.game.global.action.ClimbActionHandler
import core.game.global.action.DoorActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.system.task.Pulse
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.network.packet.PacketRepository
import core.network.packet.context.MinimapStateContext
import core.network.packet.outgoing.MinimapState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.random.Random

class MorytaniaListeners : InteractionListener {

    companion object {
        private val swampGate = intArrayOf(Scenery.GATE_3506, Scenery.GATE_3507)
        private val swampBoat = intArrayOf(6970, 6969)
        private val grottoExit = intArrayOf(3525, 3526)
        private val grottoBridge = 3522
        val outside: Location = Location.create(3439, 3337, 0)
        val inside: Location = Location.create(3442, 9734, 1)
        private val swimAnimation = Animation(6988)
        private val jumpAnimation = Animation(1603)
        private val failLocation = Location(3439, 3330)
        private val splashGraphic = Graphic(68)
    }

    override fun defineListeners() {

        /*
            Swamp boat interaction.
            Source: https://www.youtube.com/watch?v=4RXc67VBEiY&ab_channel=noob2smart
         */

        on(swampBoat, IntType.SCENERY, "board", "Board ( Pay 10 )") { player, node ->
            if(!hasRequirement(player, "Nature Spirit")) return@on true
            lock(player, 13)
            openOverlay(player, Components.FADE_TO_BLACK_120)
            queueScript(player, 3, QueueStrength.SOFT) { stage ->
                when (stage) {
                    0 -> {
                        openOverlay(player, Components.SWAMP_BOATJOURNEY_321)
                        PacketRepository.send(MinimapState::class.java, MinimapStateContext(player, 2))
                        return@queueScript delayScript(player, 7)
                    }

                    1 -> {
                        teleport(player, if (node.id == 6970) Location(3522, 3285, 0) else Location(3498, 3380, 0))
                        PacketRepository.send(MinimapState::class.java, MinimapStateContext(player, 0))
                        openInterface(player, Components.FADE_FROM_BLACK_170)
                        return@queueScript keepRunning(player)
                    }

                    2 -> {
                        closeOverlay(player)
                        sendDialogue(player, "You arrive at ${if (node.id == 6970) "Mort'ton." else "the swamp"}.")
                        return@queueScript stopExecuting(player)
                    }

                    else -> return@queueScript stopExecuting(player)
                }
            }
            return@on true
        }

        /*
            Gate to mort myre interaction.
         */

        on(swampGate, IntType.SCENERY, "open") { player, node ->
            if (player.location.y == 3457) {
                DoorActionHandler.handleAutowalkDoor(player, node.asScenery())
                GlobalScope.launch {
                    findLocalNPC(player, NPCs.ULIZIUS_1054)?.sendChat("Oh my! You're still alive!", 2)
                }
            } else {
                if (player.questRepository.hasStarted("Nature Spirit")) {
                    openInterface(player, Components.CWS_WARNING_20_580)
                } else {
                    sendNPCDialogue(player, NPCs.ULIZIUS_1054, "I'm sorry, but I'm afraid it's too dangerous to let you through this gate right now.")
                }
            }
            return@on true
        }

        /*
            Mort myre scenery interactions.
         */

        on(grottoExit, IntType.SCENERY, "exit") { player, _ ->
            teleport(player, Location.create(3439, 3337, 0))
            return@on true
        }

        on(grottoBridge, IntType.SCENERY, "jump") { player, node ->
            val start = node.location
            var failLand = Location(3438, 3331)
            var failAnim = Animation(770)
            var fromGrotto = false

            lock(player, 10)
            if (start.y == 3331) {
                fromGrotto = true
                failAnim = Animation(771)
                failLand = Location(3438, 3328)
            }
            if (AgilityHandler.hasFailed(player, 1, 0.1)) {
                val end = if (fromGrotto) failLocation else start
                AgilityHandler.forceWalk(player, -1, start, end, failAnim, 15, 0.0, null, 0).endAnimation = swimAnimation
                AgilityHandler.forceWalk(player, -1, failLocation, failLand, swimAnimation, 15, 2.0, null, 3)
                submitIndividualPulse(player, object : Pulse(2) {
                    override fun pulse(): Boolean {
                        visualize(player, failAnim, splashGraphic)
                        teleport(player, failLocation)
                        AgilityHandler.fail(player, 0, failLand, swimAnimation, Random.nextInt(1, 7), "You nearly drown in the disgusting swamp.")
                        return true
                    }
                })
            } else {
                val end = if (fromGrotto) start.transform(0, -3, 0) else start.transform(0, 3, 0)
                AgilityHandler.forceWalk(player, -1, start, end, jumpAnimation, 15, 15.0, null, 0)
            }
            return@on true
        }

        on(Scenery.TREE_5005, IntType.SCENERY, "climb up", "climb down") { player, node ->
            if (node.location == Location(3502, 3431)) {
                when (getUsedOption(player)) {
                    "climb up" -> ClimbActionHandler.climb(player, ClimbActionHandler.CLIMB_UP, Location(3502, 3430, 0))
                    "climb down" -> ClimbActionHandler.climb(player, ClimbActionHandler.CLIMB_DOWN, Location(3503, 3431, 0))
                }
            } else {
                when (getUsedOption(player)) {
                    "climb up" -> ClimbActionHandler.climb(player, ClimbActionHandler.CLIMB_UP, Location(3502, 3427, 0))
                    "climb down" -> ClimbActionHandler.climb(player, ClimbActionHandler.CLIMB_DOWN, Location(3502, 3425, 0))
                }
            }
            return@on true
        }

        on(Scenery.ROPE_BRIDGE_5002, IntType.SCENERY, "walk-here") { player, node ->
            if (node.location == Location(3502, 3428)) {
                teleport(player, Location(3502, 3430, 0))
            } else {
                teleport(player, Location(3502, 3427, 0))
            }
            return@on true
        }

    }


}
