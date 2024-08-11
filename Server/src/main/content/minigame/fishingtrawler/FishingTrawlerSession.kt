package content.minigame.fishingtrawler

import core.api.*
import core.api.consts.Components
import core.api.consts.Items
import core.game.component.Component
import core.game.node.entity.Entity
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.node.scenery.SceneryBuilder
import core.game.system.command.sets.FISHING_TRAWLER_GAMES_WON
import core.game.system.command.sets.FISHING_TRAWLER_SHIPS_SANK
import core.game.system.command.sets.STATS_BASE
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Location
import core.game.world.map.build.DynamicRegion
import core.game.world.map.zone.ZoneBorders
import core.game.world.map.zone.ZoneRestriction
import core.game.world.update.flag.context.Animation
import core.tools.Log
import core.tools.RandomFunction
import core.tools.secondsToTicks
import core.tools.ticksToSeconds
import java.util.concurrent.TimeUnit
import kotlin.math.ceil
import kotlin.random.Random

private const val OVERLAY_ID = Components.TRAWLER_OVERLAY_366
private const val TUTORIAL_ID = Components.TRAWLER_START_368
private val HOLE_X_COORDS = intArrayOf(29, 30, 31, 32, 33, 34, 35, 36)
private const val HOLE_NORTH_Y = 26
private const val HOLE_SOUTH_Y = 23
private const val LEAKING_ID = 2167
private const val PATCHED_ID = 2168

/**
 * Fishing trawler session.
 *
 * @property activity the activity.
 * @return Fishing trawler session.
 */
class FishingTrawlerSession(val activity: FishingTrawlerActivity? = null) : MapArea {
    constructor(region: DynamicRegion, activity: FishingTrawlerActivity) : this(activity) {
        this.region = region; this.base = region.baseLocation
    }

    var players: ArrayList<Player> = ArrayList()
    var netRipped = false
    var fishAmount = 0
    var timeLeft = secondsToTicks(600)
    lateinit var region: DynamicRegion
    lateinit var base: Location
    var isActive = false
    var boatSank = false
    var hole_locations = ArrayList<Location>()
    var used_locations = ArrayList<Location>()
    var maxHoles = 0
    var waterAmount = 0
    var murphy: NPC? = null
    val murphyLocations = ArrayList<Location>()
    val npcs = ArrayList<NPC>()
    var inactiveTicks = 0

    /**
     * Start.
     *
     * @param pl the players.
     */
    fun start(pl: ArrayList<Player>) {
        // Set the music ID based on a random roll
        if (RandomFunction.roll(2)) {
            region.setMusicId(38)
        } else {
            region.setMusicId(51)
        }
        // Add the players to the session
        this.players.addAll(pl)
        isActive = true
        // Initialize the holes
        initHoles()
        // Initialize Murphy NPC
        initMurphy(29, 25)
        // Initialize the seagulls
        initGulls()
        // Submit the TrawlerPulse task to the GameWorld.Pulser
        GameWorld.Pulser.submit(TrawlerPulse(this))
        // Perform actions for each player in the session
        for (player in pl) {
            // Open the overlay and tutorial interfaces for the player
            player.interfaceManager.openOverlay(Component(OVERLAY_ID))
            player.interfaceManager.open(Component(TUTORIAL_ID))
            // Update the overlay for the player
            updateOverlay(player)
            // Set the teleport location for the player
            player.properties.teleportLocation = base.transform(36, 24, 0)
            // Set the "ft-session" attribute for the player
            setAttribute(player, "ft-session", this)
            // Register a logout listener for the player
            registerLogoutListener(player, "ft-logout") {
                val session =
                    player.getAttribute<FishingTrawlerSession?>("ft-session", null) ?: return@registerLogoutListener
                // Teleport the player to a specific location
                player.location = Location.create(2667, 3161, 0)
                // Remove the player from the session
                session.players.remove(player)
                // Unlock the player's teleport
                player.locks.unlockTeleport()
            }
        }
        // Register the session's region borders
        zone.register(getRegionBorders(region.id))
    }

    /**
     * Swap boat type.
     *
     * @param fromRegion from region.
     */
    fun swapBoatType(fromRegion: Int) {
        // Create a new DynamicRegion based on the fromRegion parameter
        val newRegion = DynamicRegion.create(fromRegion)
        // Submit the SwapBoatPulse task to the GameWorld.Pulser
        GameWorld.Pulser.submit(SwapBoatPulse(players, newRegion))
    }

    /**
     * Swap boat pulse.
     *
     * @property playerList the player list.
     * @property newRegion the region.
     */
    class SwapBoatPulse(val playerList: ArrayList<Player>, val newRegion: DynamicRegion) : Pulse(3) {
        override fun pulse(): Boolean {
            // Get the FishingTrawlerSession from the first player in the playerList
            val session: FishingTrawlerSession? = playerList[0].getAttribute("ft-session", null)
            session ?: return true
            // Update the session's region and base location
            session.region = newRegion
            session.base = newRegion.baseLocation
            // Clear the NPCs in the session
            session.clearNPCs()
            // Initialize Murphy NPC in the new location
            session.initMurphy(26, 26)
            // Initialize the seagulls in the new location
            session.initGulls()
            // Perform actions for each player in the playerList
            for (player in playerList) {
                // Close the overlay interface for the player
                player.interfaceManager.closeOverlay()
                // Set the animations for the player
                player.appearance.setAnimations(Animation(188))
                // Set the teleport location for the player
                player.properties.teleportLocation = session.base.transform(36, 24, 0)
                // Increment the "FISHING_TRAWLER_SHIPS_SANK" attribute for the player
                player.incrementAttribute("/save:$STATS_BASE:$FISHING_TRAWLER_SHIPS_SANK")
            }
            return true
        }
    }

    /**
     * Trawler pulse.
     */
    class TrawlerPulse(val session: FishingTrawlerSession) : Pulse() {
        var ticks = 0
        override fun pulse(): Boolean {
            ticks++
            session.timeLeft--

            if (session.boatSank) {
                session.tickMurphy()
                return true
            }

            if (ticks % 15 == 0 && !session.netRipped) {
                if (RandomFunction.random(100) <= 10) {
                    session.ripNet()
                    session.murphy?.sendChat("Arrh! Check that net!")
                } else {
                    session.fishAmount += 3
                }
            }

            session.waterAmount += (session.getLeakingHoles())
            if (session.waterAmount >= 500) {
                session.boatSank = true
                session.isActive = false
                session.swapBoatType(7755)
                session.zone.unregister(getRegionBorders(session.region.id))
                for (player in session.players) {
                    player.locks.lockTeleport(1000000)
                }
            }

            if (RandomFunction.random(100) <= 9) {
                session.spawnHole()
            }

            if (session.timeLeft <= 0) {
                session.isActive = false
                for (player in session.players) {
                    player.interfaceManager.closeOverlay()
                    player.properties.teleportLocation = Location.create(2666, 3162, 0)
                    player.incrementAttribute("/save:$STATS_BASE:$FISHING_TRAWLER_GAMES_WON")
                    val rolls = ceil(session.fishAmount / session.players.size.toDouble()).toInt()
                    player.removeAttribute("ft-session")
                    player.setAttribute("/save:ft-rolls", rolls)

                    clearLogoutListener(player, "ft-logout")
                }
                session.zone.unregister(getRegionBorders(session.region.id))
            }

            for (player in session.players) {
                session.updateOverlay(player)
            }
            session.tickMurphy()
            return !session.isActive
        }
    }

    /**
     * Init holes.
     */
    fun initHoles() {
        maxHoles = players.size
        if (maxHoles > 16) maxHoles = 16
        if (maxHoles < 5) maxHoles = 5
        val tempLocationList = ArrayList<Location>()
        while (tempLocationList.size < maxHoles) {
            val x = HOLE_X_COORDS.random()
            val y = if (Random.nextBoolean()) HOLE_NORTH_Y else HOLE_SOUTH_Y
            val loc = Location.create(x, y, 0)
            var alreadyHas = false
            for (location in tempLocationList) {
                if (location.equals(loc)) {
                    alreadyHas = true
                    break
                }
            }
            if (!alreadyHas) {
                tempLocationList.add(base.transform(loc.x, loc.y, 0))
            }
        }
        hole_locations.addAll(tempLocationList)
    }

    /**
     * Init murphy.
     */
    fun initMurphy(localX: Int, localY: Int) {
        murphy = NPC(463)
        murphy?.isWalks = false
        murphy?.isPathBoundMovement = true
        //29,25 -> 36,25
        murphy?.location = base.transform(localX, localY, 0)
        murphy?.isRespawn = false
        for (i in 29..36) {
            murphyLocations.add(Location.create(base.transform(i, 25, 0)))
        }
        murphy?.init()
        npcs.add(murphy!!)
    }

    /**
     * Clear NPCs.
     */
    fun clearNPCs() {
        npcs.forEach {
            it.clear()
        }
        npcs.clear()
    }

    /**
     * Tick murphy.
     */
    fun tickMurphy() {
        var phrase = if (boatSank) {
            arrayOf("No fishes for you today!", "Keep your head above water, shipmate.", "Arrrgh! We sunk!", "You'll be joining Davy Jones!").random()
        } else if (waterAmount < 200) {
            arrayOf("Blistering barnacles!", "Let's get a net full of fishes!").random()
        } else {
            arrayOf("We'll all end up in a watery grave!", "My mother could bail better than that!", "It's a fierce sea today traveller.").random()
        }
        if (getLeakingHoles() > 0 && RandomFunction.random(100) <= 15) {
            phrase = "The water is coming in matey!"
        }
        if (RandomFunction.random(100) <= 10) {
            murphy?.sendChat(phrase)
        }
        if (RandomFunction.random(100) <= 6) {
            val dest = murphyLocations.random()
            murphy?.walkingQueue?.reset()
            murphy?.walkingQueue?.addPath(dest.x, dest.y, true)
        }
    }

    /**
     * Init gulls.
     */
    fun initGulls() {
        for (loc in arrayOf(base.transform(38, 17, 0), base.transform(33, 18, 0), base.transform(28, 16, 0), base.transform(28, 30, 0), base.transform(34, 32, 0))) {
            val npc = NPC(1179)
            npc.location = loc
            npcs.add(npc)
            npc.isRespawn = false
            npc.isWalks = true
            npc.walkRadius = 6
            npc.init()
        }
    }

    /**
     * Spawn hole.
     */
    fun spawnHole() {
        if (hole_locations.isEmpty() && used_locations.isEmpty()) return
        val holeLocation = hole_locations.random().also { hole_locations.remove(it) }
        if (!SceneryBuilder.replace(
                Scenery(PATCHED_ID, holeLocation),
                Scenery(LEAKING_ID, holeLocation, if (holeLocation.y == HOLE_NORTH_Y) 1 else 3)
            ) && !SceneryBuilder.replace(
                Scenery(2177, holeLocation),
                Scenery(LEAKING_ID, holeLocation, if (holeLocation.y == HOLE_NORTH_Y) 1 else 3)
            )
        ) {
            maxHoles -= 1
        }
    }

    /**
     * Get leaking holes.
     */
    fun getLeakingHoles(): Int {
        return maxHoles - hole_locations.size
    }

    /**
     * Repair hole.
     */
    fun repairHole(player: Player, obj: Scenery) {
        if (player.inventory.remove(Item(Items.SWAMP_PASTE_1941))) {
            SceneryBuilder.replace(obj, Scenery(PATCHED_ID, obj.location, obj.rotation))
            hole_locations.add(obj.location)
            if (RandomFunction.random(100) <= 30) {
                murphy?.sendChat("That's the stuff! Fill those holes!")
            }
        } else {
            player.dialogueInterpreter.sendDialogue("You need swamp paste to repair this.")
        }
    }

    /**
     * Rip net
     */
    fun ripNet() {
        netRipped = true
    }

    /**
     * Repair net.
     */
    fun repairNet(player: Player) {
        if (player.inventory.remove(Item(Items.ROPE_954))) {
            netRipped = false
            player.dialogueInterpreter.sendDialogue("You repair the net.")
        } else {
            player.dialogueInterpreter.sendDialogue("You need rope to repair this net.")
        }
    }

    /**
     * Update overlay.
     */
    fun updateOverlay(player: Player) {
        FishingTrawlerOverlay.sendUpdate(
            player,
            ((waterAmount / 500.0) * 100).toInt(),
            netRipped,
            fishAmount,
            TimeUnit.SECONDS.toMinutes(ticksToSeconds(timeLeft).toLong()).toInt() + 1
        )
    }

    override fun defineAreaBorders(): Array<ZoneBorders> {
        return arrayOf()
    }

    override fun getRestrictions(): Array<ZoneRestriction> {
        return arrayOf(
            ZoneRestriction.CANNON, ZoneRestriction.FIRES, ZoneRestriction.RANDOM_EVENTS, ZoneRestriction.TELEPORT
        )
    }

    override fun areaEnter(entity: Entity) {
        super.areaEnter(entity)
        log(this::class.java, Log.FINE, "ENTERED FTZ")
    }

    override fun areaLeave(entity: Entity, logout: Boolean) {
        super.areaLeave(entity, logout)
        log(this::class.java, Log.FINE, "EXITED FTZ")
    }
}
