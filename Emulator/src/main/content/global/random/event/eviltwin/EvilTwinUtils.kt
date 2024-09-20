package content.global.random.event.eviltwin

import core.api.*
import org.rs.consts.Items
import org.rs.consts.Regions
import core.api.utils.PlayerCamera
import core.game.node.entity.Entity
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Direction
import core.game.world.map.Location
import core.game.world.map.build.DynamicRegion
import core.game.world.update.flag.context.Graphic
import core.net.packet.PacketRepository
import core.net.packet.context.CameraContext
import core.net.packet.context.CameraContext.CameraType
import core.net.packet.outgoing.CameraViewPacket
import core.tools.RandomFunction

/**
 * Handles the evil twin random event.
 */
object EvilTwinUtils {
    const val randomEvent = "/save:evil_twin:random"
    const val doorDialogue = "evil_twin:door-interact"
    const val talkBefore = "evil_twin:npc-interact"
    const val originalLocation = "/save:original-loc"
    const val logout = "/save:evil_twin:logout"
    const val crane_x_loc = "/save:evil_twin:ccx"
    const val crane_y_loc = "/save:evil_twin:ccy"

    /**
     * Reward on success.
     */
    val rewards = arrayOf(
        Item(Items.UNCUT_DIAMOND_1618, 2),
        Item(Items.UNCUT_RUBY_1620, 3),
        Item(Items.UNCUT_EMERALD_1622, 3),
        Item(Items.UNCUT_SAPPHIRE_1624, 4)
    )

    /**
     * The region id.
     */
    val region: DynamicRegion = DynamicRegion.create(Regions.RANDOM_EVENT_EVIL_TWIN_7504)

    /**
     * The x-offset.
     */
    private var offsetX = 0

    /**
     * The y-offset.
     */
    private var offsetY = 0

    /**
     * The Molly NPC.
     */
    var molly: NPC? = null

    /**
     * The current crane object.
     */
    var currentCrane: Scenery? = null

    /**
     * The amount of tries left.
     */
    var tries = 3

    /**
     * If the event has been completed.
     */
    var success = false

    /**
     * The crane NPC.
     */
    var craneNPC: NPC? = null


    /**
     * Teleports the player and Molly to the random event region.
     */
    fun start(player: Player): Boolean {
        region.isLoaded = true
        region.setMusicId(612)
        currentCrane = Scenery(14976, region.baseLocation.transform(14, 12, 0), 10, 0)
        val color = RandomFunction.getRandomElement(TwinColors.values())
        val model = RandomFunction.random(5)
        val hash = color.ordinal or (model shl 16)
        player.setAttribute(randomEvent, hash)
        val npcId = getMollyId(hash)
        var molly = NPC.create(npcId, Location.getRandomLocation(player.location, 2, true))
        molly!!.init()
        molly.graphics(Graphic.create(86, 96))
        molly.sendChat("I need your help, " + player.username + ".")
        molly.face(player)
        player.lock(4)
        player.setAttribute(originalLocation, player.location)
        GameWorld.submit(object : Pulse(3) {
            override fun pulse(): Boolean {
                teleport(player, molly, hash)
                molly.locks.lockMovement(3000)
                openDialogue(player, MollyDialogue(3))
                return true
            }
        })
        return true
    }

    /**
     * Teleports the player and the NPC to the Evil Twin region.
     *
     * @param player the player to teleport.
     * @param npc    the NPC to teleport.
     * @param hash   the hash value used to identify the Evil Twin.
     */
    fun teleport(player: Player, npc: NPC, hash: Int) {
        setMinimapState(player, 2)
        npc.properties.teleportLocation = region.baseLocation.transform(4, 15, 0)
        npc.direction = Direction.NORTH
        player.properties.teleportLocation = region.baseLocation.transform(4, 16, 0)
        registerLogoutListener(player, logout) { p ->
            p.location = getAttribute(p, originalLocation, player.location)
        }
        spawnSuspects(hash)
        showNPCs(true)
    }

    /**
     * Cleans up after the Evil Twin random event for the player.
     *
     * @param player the player to clean up.
     */
    fun cleanup(player: Player) {
        craneNPC = null
        success = false
        molly!!.clear()
        PlayerCamera(player).reset()
        restoreTabs(player)
        player.properties.teleportLocation = getAttribute(player, originalLocation, null)
        setMinimapState(player, 0)
        removeAttributes(player, randomEvent, originalLocation, crane_x_loc, crane_y_loc)
        clearLogoutListener(player, logout)
    }

    /**
     * Decreases the tries amount.
     */
    fun decreaseTries(player: Player) {
        tries--
        sendString(player, "Tries: $tries", 240, 27)
        if (tries < 1) {
            lock(player, 20)
            closeTabInterface(player)
            openDialogue(player, MollyDialogue(1))
        }
    }

    /**
     * Updates the location of the player and entity.
     *
     * @param player the player.
     * @param entity the entity.
     * @param last   the last location.
     */
    fun locationUpdate(player: Player, entity: Entity, last: Location?) {
        if (entity == craneNPC && entity.walkingQueue.queue.size > 1 && player.interfaceManager.singleTab != null) {
            val l: Location = entity.location
            PacketRepository.send(
                CameraViewPacket::class.java,
                CameraContext(player, CameraType.POSITION, l.x + 2, l.y + 3, 520, 1, 5)
            )
            PacketRepository.send(
                CameraViewPacket::class.java,
                CameraContext(player, CameraType.ROTATION, l.x - 3, l.y - 3, 420, 1, 5)
            )
        } else if (entity == player) {
            if (MollyNPC().isHidden(player) && entity.location.localX < 9) {
                showNPCs(true)
            } else if (!MollyNPC().isHidden(player) && entity.location.localX > 8) {
                showNPCs(false)
            }
        }
        return locationUpdate(player, entity, last)
    }

    /**
     * Method used to handle the camera.
     *
     * @param x the x offset.
     * @param y the y offset.
     */
    fun updateCraneCam(player: Player, x: Int, y: Int) {
        if (player.interfaceManager.singleTab != null) {
            var loc = region.baseLocation.transform(14, 20, 0)
            PacketRepository.send(
                CameraViewPacket::class.java,
                CameraContext(player, CameraType.POSITION, loc.x, loc.y, 520, 1, 100)
            )
            loc = region.baseLocation.transform(x, 4 + y - (if (x < 14 || x > 14) (y / 4) else 0), 0)
            PacketRepository.send(
                CameraViewPacket::class.java,
                CameraContext(player, CameraType.ROTATION, loc.x, loc.y, 420, 1, 100)
            )
        }
        setAttribute(player, crane_x_loc, x)
        setAttribute(player, crane_y_loc, y)
    }

    /**
     * Moves the crane.
     *
     * @param direction the current direction.
     */
    fun moveCrane(player: Player, direction: Direction) {
        submitWorldPulse(object : Pulse(1, player) {
            override fun pulse(): Boolean {
                val newLocation = currentCrane!!.location.transform(direction)
                if (!direction.canMove(newLocation)) {
                    return true
                }

                val craneX = player.getAttribute(crane_x_loc, 14) + direction.stepX
                val craneY = player.getAttribute(crane_y_loc, 12) + direction.stepY

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

    /**
     * Toggles the visibility of the NPCs.
     * @param showMolly If molly should be shown.
     */
    fun showNPCs(showMolly: Boolean) {
        for (npc in region.planes[0].npcs) {
            if (npc.id in 3852..3891) {
                npc.isInvisible
            } else {
                molly!!.isInvisible = !showMolly
            }
        }
    }

    /**
     * Removes the suspects.
     */
    fun removeSuspects(player: Player) {
        val hash: Int = player.getAttribute(randomEvent, 0)
        for (npc in region.planes[0].npcs) {
            if (npc.id in 3852..3891 && !isEvilTwin(npc, hash)) {
                Graphic.send(Graphic.create(86), npc.location)
                npc.clear()
            }
        }
    }

    /**
     * Checks if the NPC is the evil twin NPC.
     *
     * @param npc the NPC.
     * @return `true` if so.
     */
    fun isEvilTwin(npc: NPC, hash: Int): Boolean {
        val npcId = npc.id - 3852
        val (type, color) = npcId.let {
            it / TwinColors.values().size to it % TwinColors.values().size
        }
        return hash == (color or (type shl 16))
    }

    /**
     * Spawns the suspects.
     *
     * @param hash the color & type.
     */
    fun spawnSuspects(hash: Int) {
        if (region.planes[0].npcs.size > 3) {
            return
        }
        val npcId = 3852 + (hash and 0xFF)
        for (i in 0..4) {
            val location = region.baseLocation.transform(11 + RandomFunction.random(8), 6 + RandomFunction.random(6), 0)
            val suspect = NPC.create(npcId + (i * TwinColors.values().size), location)
            suspect.isWalks = true
            suspect.walkRadius = 6
            suspect.init()
        }
    }

    /**
     * Gets the Molly NPC ID based on the hash value.
     *
     * @param hash The hash value used to identify the Evil Twin.
     * @return The Molly NPC ID.
     */
    fun getMollyId(hash: Int): Int {
        return 3892 + (hash and 0xFF) + (((hash shr 16) and 0xFF) * TwinColors.values().size)
    }

}
