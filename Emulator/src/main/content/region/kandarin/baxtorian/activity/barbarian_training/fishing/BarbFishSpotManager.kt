package content.region.kandarin.baxtorian.activity.barbarian_training.fishing

import content.region.kandarin.baxtorian.activity.barbarian_training.fishing.BarbFishSpotManager.Companion.locations
import content.region.kandarin.baxtorian.activity.barbarian_training.fishing.BarbFishSpotManager.Companion.usedLocations
import core.api.StartupListener
import core.api.TickListener
import core.game.node.entity.npc.NPC
import core.game.world.map.Location
import core.tools.RandomFunction

/**
 * Barb fish spot manager.
 */
class BarbFishSpotManager : TickListener, StartupListener {
    var ticks = 0 // Counter for ticks
    val spots = ArrayList<BarbFishingSpot>() // List to hold fishing spots

    companion object {
        val usedLocations = arrayListOf<Location>() // List of locations that are currently in use
        val locations = listOf( // Predefined list of fishing locations
            Location.create(2506, 3494, 0),
            Location.create(2504, 3497, 0),
            Location.create(2504, 3497, 0),
            Location.create(2500, 3506, 0),
            Location.create(2500, 3509, 0),
            Location.create(2500, 3512, 0),
            Location.create(2504, 3516, 0)
        )
    }

    override fun tick() {
        // Every 50 ticks, clear used locations and update with current spots
        if (ticks % 50 == 0) {
            usedLocations.clear() // Clear the list of used locations
            for (spot in spots) usedLocations.add(spot.loc ?: Location(0, 0, 0)) // Add current spots to used locations
        }
    }

    override fun startup() {
        // Initialize five fishing spots at startup
        for (i in 0 until 5) {
            spots.add(BarbFishingSpot(getNewLoc(), getNewTTL()).also { it.init() }) // Create and initialize new fishing spots
        }
    }
}

/**
 * Get new ttl
 *
 * @return Randomly generated time-to-live value
 */
fun getNewTTL(): Int {
    return RandomFunction.random(400, 2000) // Generate a random TTL between 400 and 2000
}

/**
 * Get new loc
 *
 * @return A random location that is not currently used
 */
fun getNewLoc(): Location {
    val possibleLoc = ArrayList<Location>() // List to hold possible new locations
    for (loc in locations) if (usedLocations.contains(loc)) continue else possibleLoc.add(loc) // Filter out used locations
    val loc = possibleLoc.random() // Select a random location from possible locations
    usedLocations.add(loc) // Mark the selected location as used
    return loc // Return the selected location
}

/**
 * Barb fishing spot
 *
 * @param loc The location of the fishing spot
 * @param ttl The time-to-live for the fishing spot
 * @constructor Barb fishing spot
 */
class BarbFishingSpot(var loc: Location? = null, var ttl: Int) : NPC(1176) {
    init {
        location = loc // Set the initial location of the fishing spot
    }

    val locations = listOf( // Predefined list of fishing locations
        Location.create(2506, 3494, 0),
        Location.create(2504, 3497, 0),
        Location.create(2504, 3497, 0),
        Location.create(2500, 3506, 0),
        Location.create(2500, 3509, 0),
        Location.create(2500, 3512, 0),
        Location.create(2504, 3516, 0)
    )

    override fun handleTickActions() {
        // Handle actions that occur on each tick
        if (location != loc) properties.teleportLocation = loc.also { ttl = getNewTTL() } // Update teleport location and TTL if location has changed
        if (ttl-- <= 0) { // Decrease TTL and check if it has expired
            usedLocations.remove(location) // Remove the location from used locations
            loc = getNewLoc() // Get a new location for the fishing spot
        }
    }
}
