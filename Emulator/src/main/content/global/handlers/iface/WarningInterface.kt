package content.global.handlers.iface

import core.api.*
import core.game.dialogue.FacialExpression
import core.game.global.action.ClimbActionHandler
import core.game.global.action.DoorActionHandler
import core.game.interaction.InterfaceListener
import core.game.node.entity.impl.ForceMovement
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.scenery.Scenery
import core.game.world.GameWorld
import core.game.world.map.Location
import core.game.world.map.RegionManager.getLocalNpcs
import core.game.world.update.flag.context.Animation
import org.rs.consts.*

class WarningInterface : InterfaceListener {

    override fun defineInterfaceListeners() {

        /*
         * Doomsayer (warning npc manager).
         */

        on(Components.CWS_DOOMSAYER_583) { player, _, _, buttonID, _, _ ->
            if (buttonID in 46..85) {
                player.warningMessages.getMessage(buttonID).toggle(player)
            }
            return@on true
        }

        /*
         * Giant mole cave.
         */

        on(Components.CWS_WARNING_3_568) { player, _, _, buttonID, _, _ ->
            if(buttonID == 17) {
                player.properties.teleportLocation = Location.create(1752, 5237, 0)
                playAudio(player, Sounds.ROOF_COLLAPSE_1384)
                sendMessage(player, "You seem to have dropped down into a network of mole tunnels.")
                if (!hasDiaryTaskComplete(player, DiaryType.FALADOR, 0, 5)) {
                    finishDiaryTask(player, DiaryType.FALADOR, 0, 5)
                }
            } else {
                closeInterface(player)
            }
            return@on true
        }

        /*
         * Waterbirth & Ice cavern.
         */

        on(Components.CWS_WARNING_1_574) { player, _, _, buttonID, _, _ ->
            if (buttonID == 17) {
                closeInterface(player)
                if (inBorders(player, getRegionBorders(10042))) {
                    player.properties.teleportLocation = Location.create(2443, 10146, 0)
                } else {
                    sendMessage(player, "You venture into the icy cavern.")
                    teleport(player, Location(3056, 9555, 0))
                }
            } else {
                closeInterface(player)
            }
            return@on true
        }

        /*
         * Corporeal Beast entrance.
         */

        on(Components.CWS_WARNING_30_650) { player, _, _, buttonID, _, _ ->
            if (!hasRequirement(player, "Summer's End")) return@on true
            if (buttonID != 18) {
                return@on true
            }
            if (getAttribute(player, "corp-beast-cave-delay", 0) <= GameWorld.ticks) {
                closeInterface(player)
                teleport(player, player.location.transform(4, 0, 0))
                setAttribute(player, "corp-beast-cave-delay", GameWorld.ticks + 5)
            }
            return@on true
        }

        /*
         * Mort Myre gate.
         */

        on(Components.CWS_WARNING_20_580) { player, _, _, buttonID, _, _ ->
            if(buttonID == 17) {
                closeInterface(player)
                sendMessage(player, "You pass through the holy barrier.")
                DoorActionHandler.handleAutowalkDoor(
                    player,
                    if (player.location.x > 3443) getScenery(3444, 3458, 0)!! else getScenery(3443, 3458, 0)!!
                )
                sendMessageWithDelay(player, "You walk into the gloomy atmosphere of Mort Myre.", 3)
            } else {
                closeInterface(player)
            }
            return@on true
        }

        /*
         * Observatory dungeon stairs.
         */

        on(Components.CWS_WARNING_9_560) { player, _, _, buttonID, _, _ ->
            if(buttonID == 17) {
                closeInterface(player)
                runTask(player, 2) {
                    teleport(player, Location(2355, 9394, 0))
                }
            } else {
                closeInterface(player)
            }
            return@on true
        }

        /*
         * Destroy items at POH.
         */

        on(Components.CWS_WARNING_5_563) { player, _, _, buttonID, _, _ ->
            if(buttonID == 17) {
                closeInterface(player)
                player.houseManager.toggleBuildingMode(player, true)
            } else {
                closeInterface(player)
            }
            return@on true
        }

        /*
         * Ranging guild stairs.
         */

        on(Components.CWS_WARNING_23_564) { player, _, _, buttonID, _, _ ->
            if(buttonID == 17) {
                closeInterface(player)
                ClimbActionHandler.climb(player, Animation(Animations.USE_LADDER_828), Location(2668, 3427, 2))
                val npc = getLocalNpcs(Location.create(2668, 3427, 2))
                var dir = ""
                for (n in npc) if (n.id >= NPCs.TOWER_ADVISOR_684 && n.id <= NPCs.TOWER_ADVISOR_687) {
                    when (n.id) {
                        NPCs.TOWER_ADVISOR_684 -> dir = "north"
                        NPCs.TOWER_ADVISOR_685 -> dir = "east"
                        NPCs.TOWER_ADVISOR_686 -> dir = "south"
                        NPCs.TOWER_ADVISOR_687 -> dir = "west"
                    }
                    sendChat(n, "The $dir tower is occupied, get them!")
                }
            } else {
                closeInterface(player)
            }
            return@on true
        }

        /*
         * Shantay pass.
         */

        on(Components.CWS_WARNING_10_565) { player, _, _, buttonID, _, _ ->
            if(buttonID == 17) {
                closeInterface(player)
                if (!inInventory(player, Items.SHANTAY_PASS_1854)) {
                    sendNPCDialogueLines(player, NPCs.SHANTAY_GUARD_838, FacialExpression.NEUTRAL, false, "You need a Shantay pass to get through this gate. See", "Shantay, he will sell you one for a very reasonable", "price.")
                } else {
                    openDialogue(player, NPCs.SHANTAY_GUARD_838, true)
                }
            } else {
                closeInterface(player)
                sendDialogue(player, "You decide that your visit to the desert can be postponed. Perhaps indefinitely.")
            }
            return@on true
        }

        /*
         * Wilderness ditch.
         */

        on(Components.WILDERNESS_WARNING_382) { player, _, _, buttonID, _, _ ->
            closeInterface(player)
            if (buttonID != 18) {
                return@on true
            }
            if (player.getAttribute<Any?>("wildy-ditch") != null) {
                val ditch = player.getAttribute<Scenery>("wildy-ditch")
                removeAttribute(player, "wildy-ditch")
                val l = ditch.location
                val x = player.location.x
                val y = player.location.y
                if (ditch.rotation % 2 == 0) {
                    if (y <= l.y) {
                        ForceMovement.run(player, Location.create(x, l.y - 1, 0), Location.create(x, l.y + 2, 0), Animation(Animations.JUMP_OVER_OBSTACLE_6132), 20).endAnimation = null
                    } else {
                        ForceMovement.run(player, Location.create(x, l.y + 2, 0), Location.create(x, l.y - 1, 0), Animation(Animations.JUMP_OVER_OBSTACLE_6132), 20).endAnimation = null
                    }
                } else {
                    if (x > l.x) {
                        ForceMovement.run(player, Location.create(l.x + 2, y, 0), Location.create(l.x - 1, y, 0), Animation(Animations.JUMP_OVER_OBSTACLE_6132), 20).endAnimation = null
                    } else {
                        ForceMovement.run(player, Location.create(l.x - 1, y, 0), Location.create(l.x + 2, y, 0), Animation(Animations.JUMP_OVER_OBSTACLE_6132), 20).endAnimation = null
                    }
                }
                playAudio(player, Sounds.JUMP2_2462, 10)

            } else {
                if (player.getAttribute<Any?>("wildy-gate") != null) {
                    val gate = player.getAttribute<Scenery>("wildy-gate")
                    removeAttribute(player, "wildy-gate")
                    DoorActionHandler.handleAutowalkDoor(player, gate)
                }
            }
            return@on true

        }
    }

}
