package content.global.random.event.eviltwin

import core.api.*
import org.rs.consts.Components
import core.game.interaction.InterfaceListener
import core.game.node.scenery.Scenery
import core.game.node.scenery.SceneryBuilder
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Direction
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic

/**
 * Handles the evil twin crane interface.
 */
class EvilTwinInterface : InterfaceListener {

    // Method to define interface listeners for the Evil Twin event
    override fun defineInterfaceListeners() {

        /*
         * Open crane controls and lock the player.
         */
        onOpen(Components.CRANE_CONTROL_240) { player, _ ->
            player.lock()
            return@onOpen true
        }

        /*
         * Unlock player on exit from the crane controls interface.
         */
        onClose(Components.CRANE_CONTROL_240) { player, _ ->
            player.unlock()
            return@onClose true
        }

        /*
         * Handles button interactions within the crane control interface.
         */
        on(Components.CRANE_CONTROL_240) { player, component, _, buttonId, _, _ ->
            if (EvilTwinUtils.success) {
                return@on false
            }
            when (component.id) {
                240 -> when (buttonId) {
                    28 -> {
                        player.lock(5)
                        player.locks.lockComponent(5)
                        var success = false
                        for (npc in EvilTwinUtils.region.planes[0].npcs) {
                            if (npc.location == EvilTwinUtils.currentCrane!!.location) {
                                val evilTwin = EvilTwinUtils.isEvilTwin(npc, player.getAttribute("ame:evil_twin", 0))
                                if (evilTwin) {
                                    success = true
                                    player.packetDispatch.sendMessage("You caught the Evil twin!")
                                } else {
                                    player.packetDispatch.sendMessage("You caught an innocent civilian!")
                                }
                                npc.visualize(Animation.create(4001), Graphic(666))
                                npc.lock(10)
                                player.lock(10)
                                player.locks.lockComponent(15)
                                EvilTwinUtils.updateCraneCam(player, 10, 4)
                                GameWorld.submit(object : Pulse(5, player) {
                                    var cycle = 0

                                    override fun pulse(): Boolean {
                                        when (cycle++) {
                                            0 -> {
                                                SceneryBuilder.remove(EvilTwinUtils.currentCrane!!)
                                                SceneryBuilder.add(
                                                    Scenery(
                                                        66,
                                                        EvilTwinUtils.currentCrane!!.location,
                                                        22,
                                                        0
                                                    )
                                                )
                                                npc.transform(npc.id + 20)
                                                npc.lock(20)
                                                npc.walkingQueue.reset()
                                                npc.walkingQueue.addPath(
                                                    EvilTwinUtils.region.baseLocation.x + 10,
                                                    EvilTwinUtils.region.baseLocation.y + 4
                                                )
                                                setDelay(npc.walkingQueue.queue.size + 1)
                                                EvilTwinUtils.craneNPC = npc
                                            }

                                            1 -> {
                                                EvilTwinUtils.craneNPC = null
                                                npc.animate(Animation.create(4003))
                                                setDelay(3)
                                            }

                                            2 -> {
                                                npc.reTransform()
                                                npc.faceLocation(player.location)
                                                if (evilTwin) {
                                                    EvilTwinUtils.removeSuspects(player)
                                                    npc.animator.forceAnimation(Animation.create(859))
                                                } else {
                                                    npc.sendChat("You're putting me in prison?!")
                                                }
                                                EvilTwinUtils.currentCrane = EvilTwinUtils.currentCrane!!.transform(
                                                    EvilTwinUtils.currentCrane!!.id,
                                                    EvilTwinUtils.currentCrane!!.rotation,
                                                    EvilTwinUtils.region.baseLocation.transform(14, 12, 0)
                                                )
                                                SceneryBuilder.add(
                                                    Scenery(
                                                        14977,
                                                        EvilTwinUtils.currentCrane!!.location,
                                                        22,
                                                        0
                                                    )
                                                )
                                                SceneryBuilder.add(EvilTwinUtils.currentCrane)
                                            }

                                            3 -> {
                                                EvilTwinUtils.updateCraneCam(player, 14, 12)
                                                if (!success) {
                                                    player.locks.unlockComponent()
                                                    player.lock(5)
                                                    EvilTwinUtils.tries--
                                                    if (EvilTwinUtils.tries < 1) {
                                                        EvilTwinUtils.molly!!.sendChat("You've had your last chance, " + player.username + ".")
                                                        EvilTwinUtils.molly!!.sendChat("Let me teleport you out of here...")
                                                        player.dialogueInterpreter.open(
                                                            "ame:molly",
                                                            EvilTwinUtils.molly,
                                                            1
                                                        )
                                                        success = true
                                                    } else {
                                                        player.packetDispatch.sendString(
                                                            "Tries: $EvilTwinUtils.tries",
                                                            240,
                                                            27
                                                        )
                                                        animateScenery(EvilTwinUtils.currentCrane!!, 4002)
                                                    }
                                                } else {
                                                    npc.faceLocation(player.location)
                                                    player.dialogueInterpreter.open("ame:molly", npc, 2)
                                                }
                                            }

                                            else -> return true
                                        }
                                        return false
                                    }
                                })
                                return@on true
                            }
                        }
                        player.locks.unlockComponent()
                        return@on true
                    }

                    29 -> EvilTwinUtils.moveCrane(player, Direction.SOUTH)
                    30 -> EvilTwinUtils.moveCrane(player, Direction.NORTH)
                    31 -> EvilTwinUtils.moveCrane(player, Direction.EAST)
                    32 -> EvilTwinUtils.moveCrane(player, Direction.WEST)
                    33 -> closeTabInterface(player)
                }
            }
            return@on false
        }
    }
}
