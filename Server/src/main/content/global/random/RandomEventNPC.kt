package content.global.random

import content.global.random.event.surpriseexam.SurpriseExamNPC
import core.api.addItem
import core.api.poofClear
import core.api.removeItem
import core.api.utils.WeightBasedTable
import core.api.withinDistance
import core.game.interaction.MovementPulse
import core.game.node.entity.impl.PulseType
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.world.map.Location
import core.game.world.map.RegionManager
import core.game.world.map.path.Pathfinder
import core.game.world.update.flag.context.Graphic
import core.tools.secondsToTicks
import kotlin.random.Random
import kotlin.reflect.full.createInstance

/**
 * Random event NPC.
 */
abstract class RandomEventNPC(id: Int) : NPC(id) {
    lateinit var player: Player
    abstract var loot: WeightBasedTable?
    var spawnLocation: Location? = null
    val smokeGraphics = Graphic(86)
    var initialized = false
    var finalized = false
    var timerPaused = false
    var ticksLeft = secondsToTicks(180)

    /**
     * Create random event npc.
     */
    open fun create(player: Player, loot: WeightBasedTable? = null, type: String = ""): RandomEventNPC {
        val event = this::class.createInstance()
        if (event is SurpriseExamNPC) event.type = type
        event.loot = loot
        event.player = player
        event.spawnLocation = RegionManager.getSpawnLocation(player, this)
        return event
    }

    /**
     * Terminate.
     */
    open fun terminate() {
        finalized = true
        pulseManager.clear(PulseType.STRONG)
        if (initialized) {
            poofClear(this)
        }
    }

    /**
     * Follow.
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
        super.tick()
        if (player.getAttribute<RandomEventNPC?>("re-npc", null) != this) {
            terminate()
            return
        }
        if (!player.getAttribute("random:pause", false)) {
            ticksLeft--
        }

        if (!pulseManager.hasPulseRunning() && !finalized) {
            follow()
        }
        if (!player.isActive || !withinDistance(player, location, 10)) {
            terminate()
        }
        if (ticksLeft <= 0 && initialized) {
            onTimeUp()
            initialized = false
        }
    }

    override fun init() {
        initialized = true
        finalized = false
        timerPaused = false
        spawnLocation ?: terminate()
        location = spawnLocation
        player.setAttribute("re-npc", this)
        super.init()
    }

    /**
     * On time up.
     */
    open fun onTimeUp() {
        noteAndTeleport()
        terminate()
    }

    /**
     * Note and teleport.
     */
    fun noteAndTeleport() {
        player.pulseManager.clear()
        for (item in player.inventory.toArray()) {
            if (item == null) continue
            if (item.definition.isUnnoted) {
                removeItem(player, item)
                addItem(player, item.noteChange, item.amount)
            }
        }
        if (Random.nextBoolean()) {
            player.properties.teleportLocation = Location.create(3197, 3223, 0)
        } else {
            player.properties.teleportLocation = Location.create(3212, 9620, 0)
        }
        player.graphics(smokeGraphics)
    }

    override fun clear() {
        super.clear()
        if (player.getAttribute<RandomEventNPC?>("re-npc", null) == this) player.removeAttribute("re-npc")
    }

    /**
     * Talk to.
     */
    abstract fun talkTo(npc: NPC)
}
