package content.global.skill.support.thieving.data

import core.api.consts.Items
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.tools.RandomFunction
import java.util.*

/**
 * Enum class representing a Stall with various properties and states.
 *
 * @param level The current level of the stall.
 * @param rewards An array of items that can be rewarded.
 * @param experience The experience points gained from the stall.
 * @param delay The time delay associated with the stall's operation.
 * @param message A message that can be displayed regarding the stall.
 * @param full An array representing the stall's full state.
 * @param empty An array representing the stall's empty state.
 */
enum class Stall(
    full: Array<Int>, // Represents the stall's full state with an array of integers.
    empty: Array<Int>, // Represents the stall's empty state with an array of integers.
    var level: Int, // The current level of the stall, mutable to allow changes.
    var rewards: Array<Item>, // An array of items that can be rewarded, mutable to allow updates.
    var experience: Double, // The experience points gained from the stall, mutable for adjustments.
    var delay: Int, // The time delay for stall operations, mutable to modify as needed.
    var message: String // A message related to the stall's operation, mutable for flexibility.
) {
    /**
     * Vegetable Stall.
     */
    VEGETABLE_STALL(
        full = arrayOf(4706, 4708),
        empty = arrayOf(634),
        level = 2,
        rewards = arrayOf(Item(1957, 1), Item(1965, 1), Item(1942, 1), Item(1982, 1), Item(1550, 1)),
        experience = 10.0,
        delay = 4,
        message = "vegetables"
    ),

    /**
     * Baker Stall.
     */
    BAKER_STALL(
        full = arrayOf(2561, 6163, 34384),
        empty = arrayOf(634, 6984, 34381),
        level = 5,
        rewards = arrayOf(Item(1891, 1), Item(2309, 1), Item(1901, 1)),
        experience = 16.0,
        delay = 4,
        message = "pastries"
    ),

    /**
     * Crafting Stall.
     */
    CRAFTING_STALL(
        full = arrayOf(4874, 6166),
        empty = arrayOf(4797, 6984),
        level = 5,
        rewards = arrayOf(Item(1592, 1), Item(1597, 1), Item(1755, 1)),
        experience = 16.0,
        delay = 12,
        message = "crafting supplies"
    ),

    /**
     * Tea Stall.
     */
    TEA_STALL(
        full = arrayOf(635, 6574),
        empty = arrayOf(634, 6573),
        level = 5,
        rewards = arrayOf(Item(712, 1)),
        experience = 16.0,
        delay = 12,
        message = "tea"
    ),

    /**
     * Silk Stall.
     */
    SILK_STALL(
        full = arrayOf(34383, 2560),
        empty = arrayOf(34381, 634),
        level = 20,
        rewards = arrayOf(Item(950, 1)),
        experience = 24.0,
        delay = 13,
        message = "silk"
    ),

    /**
     * Wine Stall.
     */
    WINE_STALL(
        full = arrayOf(14011),
        empty = arrayOf(634),
        level = 22,
        rewards = arrayOf(Item(1935, 1), Item(1937, 1), Item(1993, 1), Item(7919, 1)),
        experience = 27.0,
        delay = 27,
        message = "wine"
    ),

    /**
     * Market Seed Stall.
     */
    MARKET_SEED_STALL(
        full = arrayOf(7053),
        empty = arrayOf(634),
        level = 27,
        rewards = arrayOf(
            Item(5096, 1),
            Item(5097, 1),
            Item(5101, 1),
            Item(5318, 1),
            Item(5319, 1),
            Item(5324, 1)
        ),
        experience = 10.0,
        delay = 19,
        message = "seeds"
    ),

    /**
     * Fur Stall.
     */
    FUR_STALL(
        full = arrayOf(34387, 2563, 4278),
        empty = arrayOf(34381, 634, 634),
        level = 35,
        rewards = arrayOf(Item(6814, 1), Item(958, 1)),
        experience = 36.0,
        delay = 25,
        message = "fur"
    ),

    /**
     * Fish Stall.
     */
    FISH_STALL(
        full = arrayOf(4277, 4705, 4707),
        empty = arrayOf(634, 634, 634),
        level = 42,
        rewards = arrayOf(Item(331, 1), Item(359, 1), Item(377, 1)),
        experience = 42.0,
        delay = 27,
        message = "fish"
    ),

    /**
     * Crossbow Stall.
     */
    CROSSBOW_STALL(
        full = arrayOf(17031),
        empty = arrayOf(6984),
        level = 49,
        rewards = arrayOf(Item(877, 3), Item(9420, 1), Item(9440, 1)),
        experience = 52.0,
        delay = 19,
        message = "equipment"
    ),

    /**
     * Silver Stall.
     */
    SILVER_STALL(
        full = arrayOf(2565, 6164, 34382),
        empty = arrayOf(634, 6984, 34381),
        level = 50,
        rewards = arrayOf(Item(442, 1)),
        experience = 54.0,
        delay = 50,
        message = "jewellery"
    ),

    /**
     * Spice Stall.
     */
    SPICE_STALL(
        full = arrayOf(34386, 6166),
        empty = arrayOf(34381, 6984),
        level = 65,
        rewards = arrayOf(Item(2007, 1)),
        experience = 81.0,
        delay = 134,
        message = "spices"
    ),

    /**
     * Gem Stall.
     */
    GEM_STALL(
        full = arrayOf(2562, 6162, 34385),
        empty = arrayOf(634, 6984, 34381),
        level = 75,
        rewards = arrayOf(Item(1623, 1), Item(1605, 1), Item(1603, 1), Item(1601, 1)),
        experience = 160.0,
        delay = 300,
        message = "gems"
    ),

    /**
     * Scimitar Stall, (Ape Atoll Stalls).
     */
    SCIMITAR_STALL(
        full = arrayOf(4878),
        empty = arrayOf(4797),
        level = 65,
        rewards = arrayOf(Item(1323, 1)),
        experience = 100.0,
        delay = 134,
        message = "equipment"
    ),

    /**
     * Magic Stall.
     */
    MAGIC_STALL(
        full = arrayOf(4877),
        empty = arrayOf(4797),
        level = 65,
        rewards = arrayOf(Item(556, 1), Item(557, 1), Item(554, 1), Item(555, 1), Item(563, 1)),
        experience = 100.0,
        delay = 134,
        message = "equipment"
    ),

    /**
     * General Stall.
     */
    GENERAL_STALL(
        full = arrayOf(4876),
        empty = arrayOf(4797),
        level = 5,
        rewards = arrayOf(Item(1931, 1), Item(2347, 1), Item(590, 1)),
        experience = 16.0,
        delay = 12,
        message = "goods"
    ),

    /**
     * Food Stall.
     */
    FOOD_STALL(
        full = arrayOf(4875),
        empty = arrayOf(4797),
        level = 5,
        rewards = arrayOf(Item(1963, 1)),
        experience = 16.0,
        delay = 12,
        message = "food"
    ),

    /**
     * Candles.
     */
    CANDLES(
        full = arrayOf(core.api.consts.Scenery.CANDLES_19127),
        empty = arrayOf(core.api.consts.Scenery.CANDLES_19127),
        level = 20,
        rewards = arrayOf(Item(Items.CANDLE_36, 1)),
        experience = 20.0,
        delay = 0,
        message = "candles"
    );

    /*
     * CRAFTING_STALL (Ape Atoll) shares same drops/exp as regular crafting stall.
     * Quest Stalls Rocking Out
     * CUSTOMS_EVIDENCE_FILES(new Integer[]{FIND OBJ ID}, FIND OBJ EMPTY ID, 63, new Item[]{new Item(1333, 1), new Item(1617, 1), new Item(1619, 1), new Item(1623, 1), new Item(385, 1), new Item(2359, 1), new Item(2357, 1), new Item(2351, 1), new Item(7114, 1), new Item(7134, 1), new Item(1025, 1), new Item(1281, 1), new Item(1325, 1), new Item(1323, 1), new Item(1321, 1), new Item(995, 300)}, 75, 100);
     */

    var fullIDs: List<Int> = ArrayList(Arrays.asList(*full))
    var empty_ids: List<Int> = ArrayList(Arrays.asList(*empty))

    /**
     * Get empty
     *
     * @param id The identifier for which we want to retrieve the empty ID.
     * @return The empty ID corresponding to the provided identifier.
     */
    fun getEmpty(id: Int): Int {
        // Find the index of the provided ID in the fullIDs list
        val fullIndex = fullIDs.indexOf(id)
        // Return the empty ID at the found index
        return empty_ids[fullIndex]
    }

    // Property to get a random loot item from the rewards list
    val randomLoot: Item
        // Use a custom random function to select an item from the rewards
        get() = rewards[RandomFunction.random(rewards.size)]

    companion object {
        // A HashMap to map IDs to their corresponding Stall objects
        var idMap: HashMap<Int, Stall> = HashMap()

        // Initialization block to populate the idMap with Stall entries
        init {
            // Stream through all Stall values
            Arrays.stream(values()).forEach { entry: Stall ->
                // For each full ID in the entry, add it to the idMap if it doesn't already exist
                entry.fullIDs.stream().forEach { id: Int -> idMap.putIfAbsent(id, entry) }
            }
        }

        // Function to retrieve a Stall object based on the provided scenery
        fun forScenery(scenery: Scenery): Stall? {
            // Return the Stall object associated with the scenery's ID from the idMap
            return idMap[scenery.id]
        }
    }
}