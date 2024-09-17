package core.game.worldevents.holiday

import org.rs.consts.Sounds
import core.api.playGlobalAudio
import core.api.poofClear
import core.game.interaction.MovementPulse
import core.game.node.entity.impl.PulseType
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.world.map.Location
import core.game.world.map.RegionManager
import core.game.world.map.path.Pathfinder
import core.tools.secondsToTicks
import kotlin.reflect.full.createInstance

/**
 * Holiday random event NPC.
 * @author Zerken
 */
abstract class HolidayRandomEventNPC(id: Int) : NPC(id) {
    lateinit var player: Player
    var spawnLocation: Location? = null
    var initialized = false
    var finalized = false
    var timerPaused = false
    var ticksLeft = secondsToTicks(30)

    /**
     * Create a new instance of HolidayRandomEventNPC
     *
     * @param player The player interacting with the NPC
     * @param type Type of the event (default is an empty string)
     * @return Newly created instance of HolidayRandomEventNPC
     */
    open fun create(player: Player, type: String = ""): HolidayRandomEventNPC {
        val event = this::class.createInstance()
        event.player = player
        event.spawnLocation = RegionManager.getSpawnLocation(player, this)
        return event
    }

    /**
     * Terminate the NPC event
     *
     */
    open fun terminate() {
        pulseManager.clear(PulseType.STANDARD)
        if (initialized && !finalized) {
            poofClear(this)
            playGlobalAudio(this.location, Sounds.SMOKEPUFF2_1931, 100)
        }
        finalized = true
    }

    /**
     * Follow the player
     *
     */
    open fun follow() {
        pulseManager.run((object : MovementPulse(this, player, Pathfinder.DUMB) {
            override fun pulse(): Boolean {
                face(player)
                return false
            }
        }), PulseType.STANDARD)
    }

    override fun tick() {
        super.tick() // Call the superclass tick method
        if (player.getAttribute<HolidayRandomEventNPC?>("holiday-npc", null) != this) { // Check if the NPC is still associated with the player
            terminate() // Terminate if not associated
            return // Exit the method
        }
        if (!player.getAttribute("holidayrandom:pause", false)) { // Check if the timer is not paused
            ticksLeft-- // Decrement the ticks left
        }
        if (!pulseManager.hasPulseRunning() && !finalized) { // Check if no pulse is running and not finalized
            follow() // Call follow method
        }
        if (!player.isActive || !player.location.withinDistance(location, 10)) { // Check if the player is inactive or out of range
            terminate() // Terminate the NPC
        }
        if (ticksLeft <= 0 && initialized) { // Check if time is up and initialized
            terminate() // Terminate the NPC
            initialized = false // Reset initialized flag
        }
    }

    override fun init() {
        initialized = true // Set initialized flag to true
        finalized = false // Reset finalized flag
        timerPaused = false // Reset timer paused flag
        spawnLocation ?: terminate() // Terminate if spawn location is null
        location = spawnLocation // Set NPC location to spawn location
        player.setAttribute("holiday-npc", this) // Associate the NPC with the player
        super.init() // Call the superclass init method
    }

    override fun clear() {
        super.clear() // Call the superclass clear method
        if (player.getAttribute<HolidayRandomEventNPC?>("holiday-npc", null) == this) // Check if the NPC is associated with the player
            player.removeAttribute("holiday-npc") // Remove the association
    }

    /**
     * Talk to the NPC
     *
     * @param npc The NPC to talk to
     */
    abstract fun talkTo(npc: NPC)
}
