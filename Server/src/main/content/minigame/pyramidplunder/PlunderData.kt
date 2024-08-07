package content.minigame.pyramidplunder

import core.api.consts.Items
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.world.map.Direction
import core.game.world.map.Location

/**
 * Plunder data.
 */
object PlunderData {

    // HashMap to store player locations in Plunder rooms
    val playerLocations = HashMap<Player, PlunderRoom>()

    // ArrayList to store players participating in the Plunder
    val players = ArrayList<Player>()

    // Array of door varbits for Plunder rooms
    val doorVarbits = arrayOf(2366, 2367, 2368, 2369)

    // Array to store door varbits for each room
    val doors = Array(8) { doorVarbits[0] }

    // HashMap to keep track of time left for each player
    val timeLeft = HashMap<Player, Int>()

    // Array of varbits for pyramid entrances
    val pyramidEntranceVarbits = arrayOf(2371, 2372, 2373, 2374)

    // Randomly select current entrance varbit
    var currentEntrance = pyramidEntranceVarbits.random()

    // Timestamp for the next entrance switch
    var nextEntranceSwitch = 0L

    // Initialize a mummy NPC at a specific location
    val mummy = NPC(4476, Location.create(1968, 4428, 2)).also { it.isNeverWalks = true; it.init() }

    // Array of arrays containing artifact items
    val artifacts = arrayOf(
        arrayOf(Items.IVORY_COMB_9026, Items.POTTERY_SCARAB_9032, Items.POTTERY_STATUETTE_9036),
        arrayOf(Items.STONE_SCARAB_9030, Items.STONE_STATUETTE_9038, Items.STONE_SEAL_9042),
        arrayOf(Items.GOLDEN_SCARAB_9028, Items.GOLDEN_STATUETTE_9034, Items.GOLD_SEAL_9040)
    )

    // Array of Plunder rooms with their details
    val rooms = arrayOf(
        PlunderRoom(1, Location.create(1927, 4477, 0), Location.create(1929, 4469, 0), Direction.SOUTH),
        PlunderRoom(2, Location.create(1954, 4477, 0), Location.create(1955, 4467, 0), Direction.SOUTH),
        PlunderRoom(3, Location.create(1977, 4471, 0), Location.create(1975, 4458, 0), Direction.SOUTH),
        PlunderRoom(4, Location.create(1927, 4453, 0), Location.create(1937, 4454, 0), Direction.EAST),
        PlunderRoom(5, Location.create(1965, 4444, 0), Location.create(1955, 4449, 0), Direction.WEST),
        PlunderRoom(6, Location.create(1927, 4424, 0), Location.create(1925, 4433, 0), Direction.NORTH),
        PlunderRoom(7, Location.create(1943, 4421, 0), Location.create(1950, 4427, 0), Direction.NORTH),
        PlunderRoom(8, Location.create(1974, 4420, 0), Location.create(1971, 4431, 0), Direction.NORTH)
    )
}

/**
 * Plunder room.
 *
 * @property room Room number.
 * @property entrance Entrance location.
 * @property mummyLoc Mummy location.
 * @property spearDirection Direction of the spear.
 * @constructor Create a Plunder room.
 */
data class PlunderRoom(val room: Int, val entrance: Location, val mummyLoc: Location, val spearDirection: Direction)
