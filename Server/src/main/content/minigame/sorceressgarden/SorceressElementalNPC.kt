package content.minigame.sorceressgarden

import core.api.consts.NPCs
import core.api.setMinimapState
import core.game.component.Component
import core.game.node.entity.Entity
import core.game.node.entity.combat.CombatSwingHandler.Companion.isProjectileClipped
import core.game.node.entity.combat.DeathTask
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.Direction
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Sorceress Elemental NPC.
 */
@Initializable
class SorceressElementalNPC : AbstractNPC {
    internal enum class ElementalDefinition(private val npcId: Int, private val locations: Array<Location>) {
        /**
         * Autumn Elemental 5533.
         */
        AUTUMN_ELEMENTAL_5533(NPCs.AUTUMN_ELEMENTAL_5533, arrayOf(Location(2908, 5460, 0), Location(2898, 5460, 0))),

        /**
         * Autumn Elemental 5534.
         */
        AUTUMN_ELEMENTAL_5534(NPCs.AUTUMN_ELEMENTAL_5534, arrayOf(Location(2900, 5448, 0), Location(2900, 5455, 0))),

        /**
         * Autumn Elemental 5535.
         */
        AUTUMN_ELEMENTAL_5535(NPCs.AUTUMN_ELEMENTAL_5535, arrayOf(Location(2905, 5449, 0), Location(2899, 5449, 0))),

        /**
         * Autumn Elemental 5536.
         */
        AUTUMN_ELEMENTAL_5536(NPCs.AUTUMN_ELEMENTAL_5536, arrayOf(Location(2903, 5451, 0), Location(2903, 5455, 0), Location(2905, 5455, 0), Location(2905, 5451, 0))),

        /**
         * Autumn Elemental 5537.
         */
        AUTUMN_ELEMENTAL_5537(NPCs.AUTUMN_ELEMENTAL_5537, arrayOf(Location(2903, 5457, 0), Location(2917, 5457, 0))),

        /**
         * Autumn Elemental 5538.
         */
        AUTUMN_ELEMENTAL_5538(NPCs.AUTUMN_ELEMENTAL_5538, arrayOf(Location(2908, 5455, 0), Location(2917, 5455, 0))),

        /**
         * Spring Elemental 5539.
         */
        SPRING_ELEMENTAL_5539(NPCs.SPRING_ELEMENTAL_5539, arrayOf(Location(2922, 5471, 0), Location(2922, 5459, 0))),

        /**
         * Spring Elemental 5540.
         */
        SPRING_ELEMENTAL_5540(NPCs.SPRING_ELEMENTAL_5540, arrayOf(Location(2924, 5463, 0), Location(2928, 5463, 0), Location(2928, 5461, 0), Location(2924, 5461, 0))),

        /**
         * Spring Elemental 5541.
         */
        SPRING_ELEMENTAL_5541(NPCs.SPRING_ELEMENTAL_5541, arrayOf(Location(2924, 5461, 0), Location(2926, 5461, 0), Location(2926, 5458, 0), Location(2924, 5458, 0))),

        /**
         * Spring Elemental 5542.
         */
        SPRING_ELEMENTAL_5542(NPCs.SPRING_ELEMENTAL_5542, arrayOf(Location(2928, 5458, 0), Location(2928, 5460, 0), Location(2934, 5460, 0), Location(2934, 5458, 0))),

        /**
         * Spring Elemental 5543.
         */
        SPRING_ELEMENTAL_5543(NPCs.SPRING_ELEMENTAL_5543, arrayOf(Location(2931, 5477, 0), Location(2931, 5470, 0))),

        /**
         * Spring Elemental 5544.
         */
        SPRING_ELEMENTAL_5544(NPCs.SPRING_ELEMENTAL_5544, arrayOf(Location(2935, 5469, 0), Location(2928, 5469, 0))),

        /**
         * Spring Elemental 5545.
         */
        SPRING_ELEMENTAL_5545(NPCs.SPRING_ELEMENTAL_5545, arrayOf(Location(2925, 5464, 0), Location(2925, 5475, 0))),

        /**
         * Spring Elemental 5546.
         */
        SPRING_ELEMENTAL_5546(NPCs.SPRING_ELEMENTAL_5546, arrayOf(Location(2931, 5477, 0), Location(2931, 5470, 0))),

        /**
         * Summer Elemental 5547.
         */
        SUMMER_ELEMENTAL_5547(NPCs.SUMMER_ELEMENTAL_5547, arrayOf(Location(2907, 5488, 0), Location(2907, 5482, 0))),

        /**
         * Summer Elemental 5548.
         */
        SUMMER_ELEMENTAL_5548(NPCs.SUMMER_ELEMENTAL_5548, arrayOf(Location(2907, 5490, 0), Location(2907, 5495, 0))),

        /**
         * Summer Elemental 5549.
         */
        SUMMER_ELEMENTAL_5549(NPCs.SUMMER_ELEMENTAL_5549, arrayOf(Location(2910, 5493, 0), Location(2910, 5487, 0))),

        /**
         * Summer Elemental 5550.
         */
        SUMMER_ELEMENTAL_5550(NPCs.SUMMER_ELEMENTAL_5550, arrayOf(Location(2918, 5483, 0), Location(2918, 5485, 0), Location(2915, 5485, 0), Location(2915, 5483, 0), Location(2912, 5483, 0), Location(2912, 5485, 0), Location(2915, 5485, 0), Location(2915, 5483, 0))),

        /**
         * Summer Elemental 5551.
         */
        SUMMER_ELEMENTAL_5551(NPCs.SUMMER_ELEMENTAL_5551, arrayOf(Location(2921, 5486, 0), Location(2923, 5486, 0), Location(2923, 5490, 0), Location(2923, 5486, 0))),

        /**
         * Summer Elemental 5552.
         */
        SUMMER_ELEMENTAL_5552(NPCs.SUMMER_ELEMENTAL_5552, arrayOf(Location(2921, 5491, 0), Location(2923, 5491, 0), Location(2923, 5495, 0), Location(2921, 5495, 0))),

        /**
         * Winter Elemental 5553.
         */
        WINTER_ELEMENTAL_5553(NPCs.WINTER_ELEMENTAL_5553, arrayOf(Location(2899, 5466, 0), Location(2899, 5468, 0), Location(2897, 5468, 0), Location(2897, 5466, 0), Location(2897, 5468, 0), Location(2899, 5468, 0))),

        /**
         * Winter Elemental 5554.
         */
        WINTER_ELEMENTAL_5554(NPCs.WINTER_ELEMENTAL_5554, arrayOf(Location(2897, 5470, 0), Location(2891, 5470, 0))),

        /**
         * Winter Elemental 5555.
         */
        WINTER_ELEMENTAL_5555(NPCs.WINTER_ELEMENTAL_5555, arrayOf(Location(2897, 5471, 0), Location(2899, 5471, 0), Location(2899, 5478, 0), Location(2897, 5478, 0))),

        /**
         * Winter Elemental 5556.
         */
        WINTER_ELEMENTAL_5556(NPCs.WINTER_ELEMENTAL_5556, arrayOf(Location(2896, 5483, 0), Location(2900, 5483, 0), Location(2900, 5480, 0), Location(2897, 5480, 0), Location(2897, 5481, 0), Location(2896, 5481, 0), Location(2896, 5482, 0))),

        /**
         * Winter Elemental 5557.
         */
        WINTER_ELEMENTAL_5557(NPCs.WINTER_ELEMENTAL_5557, arrayOf(Location(2896, 5483, 0), Location(2896, 5481, 0), Location(2891, 5481, 0), Location(2891, 5483, 0))),

        /**
         * Winter Elemental 5558.
         */
        WINTER_ELEMENTAL_5558(NPCs.WINTER_ELEMENTAL_5558, arrayOf(Location(2889, 5485, 0), Location(2900, 5485, 0)));

        /**
         * Get id.
         *
         * @return The unique identifier for the NPC.
         */
        fun getId(): Int {
            return npcId // Return the unique identifier of the NPC
        }

        /**
         * Get location.
         *
         * @return An array of Location objects representing the NPC's locations.
         */
        fun getLocation(): Array<Location> { // Implementation to retrieve the NPC's locations will go here
            return locations
        }

        companion object {

            fun forId(id: Int): ElementalDefinition? {
                for (def in ElementalDefinition.values()) if (def != null) if (def.getId() == id) return def
                return null
            }
        }
    }

    private var definition: ElementalDefinition? = null

    var tilesIndex = 0

    constructor() : super(5533, null)

    constructor(id: Int, location: Location?) : super(id, location)

    /**
     * Can teleport.
     *
     * @param t the teleport.
     * @return true if teleportation is possible, false otherwise.
     */
    fun canTeleport(t: Entity): Boolean {
        // Check if the direction is EAST and if the target entity is within teleportation range
        if (getDirection() == Direction.EAST && t.location.x - getLocation().x < 4 && t.location.x - getLocation().x > -1 && t.location.y - getLocation().y == 0) return true

        // Check if the direction is WEST and if the target entity is within teleportation range
        if (getDirection() == Direction.WEST && getLocation().x - t.location.x < 4 && getLocation().x - t.location.x > -1 && t.location.y - getLocation().y == 0) return true

        // Check if the direction is NORTH and if the target entity is within teleportation range
        if (getDirection() == Direction.NORTH && t.location.y - getLocation().y < 4 && t.location.y - getLocation().y > -1 && t.location.x - getLocation().x == 0) return true

        // Check if the direction is SOUTH and if the target entity is within teleportation range
        return if (getDirection() == Direction.SOUTH && getLocation().y - t.location.y < 4 && getLocation().y - t.location.y > -1 && t.location.x - getLocation().x == 0) true else false
    }

    override fun configure() {
        super.configure() // Call the superclass's configure method
        definition = ElementalDefinition.forId(id) // Retrieve the elemental definition based on the ID
        if (definition != null) { // Check if the definition is not null
            configureMovementPath(*definition!!.getLocation()) // Configure the movement path using the location from the definition
            isWalks = true // Set the walking state to true
        }
    }

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        // Create and return a new instance of SorceressElementalNPC with the given ID and location
        return SorceressElementalNPC(id, location)
    }

    override fun getIds(): IntArray {
        return intArrayOf(
            NPCs.AUTUMN_ELEMENTAL_5533,
            NPCs.AUTUMN_ELEMENTAL_5534,
            NPCs.AUTUMN_ELEMENTAL_5535,
            NPCs.AUTUMN_ELEMENTAL_5536,
            NPCs.AUTUMN_ELEMENTAL_5537,
            NPCs.AUTUMN_ELEMENTAL_5538,
            NPCs.SPRING_ELEMENTAL_5539,
            NPCs.SPRING_ELEMENTAL_5540,
            NPCs.SPRING_ELEMENTAL_5541,
            NPCs.SPRING_ELEMENTAL_5542,
            NPCs.SPRING_ELEMENTAL_5543,
            NPCs.SPRING_ELEMENTAL_5544,
            NPCs.SPRING_ELEMENTAL_5545,
            NPCs.SPRING_ELEMENTAL_5546,
            NPCs.SUMMER_ELEMENTAL_5547,
            NPCs.SUMMER_ELEMENTAL_5548,
            NPCs.SUMMER_ELEMENTAL_5549,
            NPCs.SUMMER_ELEMENTAL_5550,
            NPCs.SUMMER_ELEMENTAL_5551,
            NPCs.SUMMER_ELEMENTAL_5552,
            NPCs.WINTER_ELEMENTAL_5553,
            NPCs.WINTER_ELEMENTAL_5554,
            NPCs.WINTER_ELEMENTAL_5555,
            NPCs.WINTER_ELEMENTAL_5556,
            NPCs.WINTER_ELEMENTAL_5557,
            NPCs.WINTER_ELEMENTAL_5558
        )
    }

    val respawnLocation: Location?
        get() {
            var respawn: Location? = null
            if (id >= 5533 && id <= 5538) respawn =
                Location.create(2913, 5467, 0) else if (id >= 5539 && id <= 5546) respawn =
                Location.create(2916, 5473, 0) else if (id >= 5547 && id <= 5552) respawn =
                Location.create(2910, 5476, 0) else if (id >= 5553 && id <= 5558) respawn =
                Location.create(2907, 5470, 0)
            return respawn
        }

    override fun tick() {
        super.tick()
        val players = viewport.currentPlane.players
        for (player in players) {
            if (player == null || !player.isActive || player.locks.isInteractionLocked || DeathTask.isDead(player) || !canTeleport(
                    player
                ) || !isProjectileClipped(this, player, false)
            ) {
                continue
            }
            animate(Animation(5803))
            sendTeleport(player)
        }
    }

    @Throws(Throwable::class)
    override fun newInstance(arg: Any?): Plugin<Any> {
        return super.newInstance(arg)
    }

    /**
     * Send teleport.
     *
     * @param player the player.
     */
    fun sendTeleport(player: Player) {
        player.lock()
        Pulser.submit(object : Pulse(1) {
            var counter = 0
            override fun pulse(): Boolean {
                if (counter == 0) {
                    player.packetDispatch.sendMessage("You've been spotted by an elemental and teleported out of its garden.")
                    player.graphics(Graphic(110, 100))
                    player.interfaceManager.openOverlay(Component(115))
                    setMinimapState(player, 2)
                    face(player)
                } else if (counter == 6) {
                    player.properties.teleportLocation = Location.create(respawnLocation)
                    setMinimapState(player, 0)
                    player.interfaceManager.closeOverlay()
                    player.interfaceManager.close()
                    face(null)
                    player.unlock()
                    return true
                }
                counter++
                return false
            }
        })
    }
}