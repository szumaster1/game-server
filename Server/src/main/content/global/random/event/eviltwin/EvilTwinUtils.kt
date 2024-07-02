package content.global.random.event.eviltwin

import core.api.*
import core.api.utils.PlayerCamera
import core.game.interaction.QueueStrength
import core.game.node.entity.Entity
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.system.task.Pulse
import core.game.world.map.Direction
import core.game.world.map.Location
import core.game.world.map.build.DynamicRegion
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.network.packet.PacketRepository
import core.network.packet.context.CameraContext
import core.network.packet.context.CameraContext.CameraType
import core.network.packet.context.MinimapStateContext
import core.network.packet.outgoing.CameraViewPacket
import core.network.packet.outgoing.MinimapState
import core.utilities.RandomFunction

object EvilTwinUtils {

    val randomEvent = "/save:evil_twin:random"
    val doorDialogue = "evil_twin:door-interact"
    val talkBefore = "evil_twin:npc-interact"
    val originalLocation = "/save:original-loc"
    val logout = "/save:evil_twin:logout"
    val crane_x_loc = "/save:evil_twin:ccx"
    val crane_y_loc = "/save:evil_twin:ccy"

    val rewards = arrayOf(Item(1618, 2), Item(1620, 3), Item(1622, 3), Item(1624, 4))
    val region: DynamicRegion = DynamicRegion.create(7504)

    var success = false
    var tries = 3
    var offsetX = 0
    var offsetY = 0

    var mollyNPC: NPC? = null
    var craneNPC: NPC? = null
    var currentCrane: Scenery? = null

    fun start(player: Player): Boolean {
        region.setMusicId(612)
        currentCrane = Scenery(14976, region.baseLocation.transform(14, 12, 0), 10, 0)
        val color: EvilTwinColors = RandomFunction.getRandomElement(EvilTwinColors.values())
        val model = RandomFunction.random(5)
        val hash = color.ordinal or (model shl 16)
        val npcId = getMollyId(hash)
        setAttribute(player, randomEvent, hash)
        mollyNPC = NPC.create(npcId, Location.getRandomLocation(player.location, 1, true))
        mollyNPC!!.init()
        visualize(mollyNPC!!, Animation(-1), Graphic.create(86, 96))
        sendChat(mollyNPC!!, "I need your help, ${player.username}.")
        mollyNPC!!.faceTemporary(player, 3)
        setAttribute(player, originalLocation, player.location)
        queueScript(player, 4, QueueStrength.SOFT) {
            teleport(player, mollyNPC!!, hash)
            mollyNPC!!.faceLocation(Location(4,18,0))
            mollyNPC!!.locks.lockMovement(3000)
            openDialogue(player, MollyDialogue(3))
            return@queueScript stopExecuting(player)
        }
        return true
    }

    fun teleport(player: Player, npc: NPC, hash: Int) {
        PacketRepository.send(MinimapState::class.java, MinimapStateContext(player, 2))
        npc.properties.teleportLocation = region.baseLocation.transform(4, 15, 0)
        player.properties.teleportLocation = region.baseLocation.transform(4, 16, 0)
        registerLogoutListener(player, logout) { p ->
            p.location = getAttribute(p, originalLocation, player.location)
        }
        spawnSuspects(hash)
        showNPCs(true)
    }

    fun cleanup(player: Player) {
        craneNPC = null
        success = false
        mollyNPC!!.clear()
        PlayerCamera(player).reset()
        restoreTabs(player)
        player.properties.teleportLocation = getAttribute(player, originalLocation, null)
        setMinimapState(player, 0)
        removeAttributes(player, randomEvent, originalLocation, crane_x_loc, crane_y_loc)
        clearLogoutListener(player, logout)
    }

    fun decreaseTries(player: Player) {
        tries--
        setInterfaceText(player, "Tries: $tries", 240, 27)
        if (tries < 1) {
            lock(player, 20)
            closeTabInterface(player)
            openDialogue(player, MollyDialogue(1))
        }
    }

    fun locationUpdate(player: Player, entity: Entity, last: Location?) {
        if (entity == craneNPC && entity.walkingQueue.queue.size > 1 && player.interfaceManager.singleTab != null) {
            val l: Location = entity.location
            PacketRepository.send(CameraViewPacket::class.java, CameraContext(player, CameraType.POSITION, l.x + 2, l.y + 3, 520, 1, 5))
            PacketRepository.send(CameraViewPacket::class.java, CameraContext(player, CameraType.ROTATION, l.x - 3, l.y - 3, 420, 1, 5))
        } else if (entity == player) {
            if (mollyNPC!!.isHidden(player) && entity.location.localX < 9) {
                showNPCs(true)
            } else if (!mollyNPC!!.isHidden(player) && entity.location.localX > 8) {
                showNPCs(false)
            }
        }
        return locationUpdate(player, entity, last)
    }

    fun updateCraneCam(player: Player, x: Int, y: Int) {
        if (player.interfaceManager.singleTab != null) {
            var loc = region.baseLocation.transform(14, 20, 0)
            PacketRepository.send(CameraViewPacket::class.java, CameraContext(player, CameraType.POSITION, loc.x, loc.y, 520, 1, 100))
            loc = region.baseLocation.transform(x, 4 + y - (if (x < 14 || x > 14) (y / 4) else 0), 0)
            PacketRepository.send(CameraViewPacket::class.java, CameraContext(player, CameraType.ROTATION, loc.x, loc.y, 420, 1, 100))
        }
        setAttribute(player, crane_x_loc, x)
        setAttribute(player, crane_y_loc, y)
    }

    fun moveCrane(player: Player, direction: Direction) {
        submitWorldPulse(object : Pulse(1, player) {
            override fun pulse(): Boolean {
                if (!direction.canMove(currentCrane!!.location.transform(direction))) {
                    return true
                }
                val craneX: Int = player.getAttribute(crane_x_loc, 14) + direction.stepX
                val craneY: Int = player.getAttribute(crane_y_loc, 12) + direction.stepY
                updateCraneCam(player, craneX, craneY)
                removeScenery(currentCrane!!)
                addScenery(Scenery(66, currentCrane!!.location, 22, 0))
                currentCrane = currentCrane!!.transform(
                    currentCrane!!.id,
                    currentCrane!!.rotation,
                    region.baseLocation.transform(craneX, craneY, 0)
                )
                addScenery(Scenery(14977, currentCrane!!.location, 22, 0))
                addScenery(currentCrane!!)
                return true
            }
        })
    }

    fun showNPCs(showMolly: Boolean) {
        for (npc in region.planes[0].npcs) {
            if (npc.id in 3852..3891) {
                npc.isInvisible
            } else {
                mollyNPC!!.isInvisible = !showMolly
            }
        }
    }

    fun isEvilTwin(npc: NPC, hash: Int): Boolean {
        val npcId = npc.id - 3852
        val type: Int = npcId / EvilTwinColors.values().size
        val color: Int = npcId - (type * EvilTwinColors.values().size)
        return hash == (color or (type shl 16))
    }

    fun removeSuspects(player: Player) {
        val hash: Int = player.getAttribute(randomEvent, 0)
        for (npc in region.planes[0].npcs) {
            if (npc.id in 3852..3891 && !isEvilTwin(npc, hash)) {
                Graphic.send(Graphic.create(86), npc.location)
                npc.clear()
            }
        }
    }

    fun spawnSuspects(hash: Int) {
        if (region.planes[0].npcs.size > 3) {
            return
        }
        val npcId = 3852 + (hash and 0xFF)
        for (i in 0..4) {
            val location = region.baseLocation.transform(11 + RandomFunction.random(8), 6 + RandomFunction.random(6), 0)
            val suspect = NPC.create(npcId + (i * EvilTwinColors.values().size), location)
            suspect.isWalks = true
            suspect.walkRadius = 6
            suspect.init()
        }
    }

    fun reward(player: Player) {
        val item = RandomFunction.getRandomElement(rewards)
        addItemOrDrop(player, item.id, item.amount)
        runTask(player, 2) {
            sendItemDialogue(player, item.id - 1, "Molly's given you " + item.amount + " " + getItemName(item.id).lowercase() + "s.")
        }
    }

    fun getMollyId(hash: Int): Int {
        return 3892 + (hash and 0xFF) + (((hash shr 16) and 0xFF) * EvilTwinColors.values().size)
    }

}
