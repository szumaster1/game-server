package content.global.skill.support.thieving.data

import org.rs.consts.Items
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.tools.RandomFunction
import java.util.*

/**
 * Representing a stall.
 */
enum class Stall(
    full: Array<Int>,
    empty: Array<Int>,
    var level: Int,
    var rewards: Array<Item>,
    var experience: Double,
    var delay: Int,
    var message: String
) {
    VEGETABLE_STALL(
        full = arrayOf(4706, 4708),
        empty = arrayOf(634),
        level = 2,
        rewards = arrayOf(Item(1957, 1), Item(1965, 1), Item(1942, 1), Item(1982, 1), Item(1550, 1)),
        experience = 10.0,
        delay = 4,
        message = "vegetables"
    ),
    BAKER_STALL(
        full = arrayOf(2561, 6163, 34384),
        empty = arrayOf(634, 6984, 34381),
        level = 5,
        rewards = arrayOf(Item(1891, 1), Item(2309, 1), Item(1901, 1)),
        experience = 16.0,
        delay = 4,
        message = "pastries"
    ),
    CRAFTING_STALL(
        full = arrayOf(4874, 6166),
        empty = arrayOf(4797, 6984),
        level = 5,
        rewards = arrayOf(Item(1592, 1), Item(1597, 1), Item(1755, 1)),
        experience = 16.0,
        delay = 12,
        message = "crafting supplies"
    ),
    TEA_STALL(
        full = arrayOf(635, 6574),
        empty = arrayOf(634, 6573),
        level = 5,
        rewards = arrayOf(Item(712, 1)),
        experience = 16.0,
        delay = 12,
        message = "tea"
    ),
    SILK_STALL(
        full = arrayOf(34383, 2560),
        empty = arrayOf(34381, 634),
        level = 20,
        rewards = arrayOf(Item(950, 1)),
        experience = 24.0,
        delay = 13,
        message = "silk"
    ),
    WINE_STALL(
        full = arrayOf(14011),
        empty = arrayOf(634),
        level = 22,
        rewards = arrayOf(Item(1935, 1), Item(1937, 1), Item(1993, 1), Item(7919, 1)),
        experience = 27.0,
        delay = 27,
        message = "wine"
    ),
    MARKET_SEED_STALL(
        full = arrayOf(7053),
        empty = arrayOf(634),
        level = 27,
        rewards = arrayOf(Item(5096, 1), Item(5097, 1), Item(5101, 1), Item(5318, 1), Item(5319, 1), Item(5324, 1)),
        experience = 10.0,
        delay = 19,
        message = "seeds"
    ),
    FUR_STALL(
        full = arrayOf(34387, 2563, 4278),
        empty = arrayOf(34381, 634, 634),
        level = 35,
        rewards = arrayOf(Item(6814, 1), Item(958, 1)),
        experience = 36.0,
        delay = 25,
        message = "fur"
    ),
    FISH_STALL(
        full = arrayOf(4277, 4705, 4707),
        empty = arrayOf(634, 634, 634),
        level = 42,
        rewards = arrayOf(Item(331, 1), Item(359, 1), Item(377, 1)),
        experience = 42.0,
        delay = 27,
        message = "fish"
    ),
    CROSSBOW_STALL(
        full = arrayOf(17031),
        empty = arrayOf(6984),
        level = 49,
        rewards = arrayOf(Item(877, 3), Item(9420, 1), Item(9440, 1)),
        experience = 52.0,
        delay = 19,
        message = "equipment"
    ),
    SILVER_STALL(
        full = arrayOf(2565, 6164, 34382),
        empty = arrayOf(634, 6984, 34381),
        level = 50,
        rewards = arrayOf(Item(442, 1)),
        experience = 54.0,
        delay = 50,
        message = "jewellery"
    ),
    SPICE_STALL(
        full = arrayOf(34386, 6166),
        empty = arrayOf(34381, 6984),
        level = 65,
        rewards = arrayOf(Item(2007, 1)),
        experience = 81.0,
        delay = 134,
        message = "spices"
    ),
    GEM_STALL(
        full = arrayOf(2562, 6162, 34385),
        empty = arrayOf(634, 6984, 34381),
        level = 75,
        rewards = arrayOf(Item(1623, 1), Item(1605, 1), Item(1603, 1), Item(1601, 1)),
        experience = 160.0,
        delay = 300,
        message = "gems"
    ),
    SCIMITAR_STALL(
        full = arrayOf(4878),
        empty = arrayOf(4797),
        level = 65,
        rewards = arrayOf(Item(1323, 1)),
        experience = 100.0,
        delay = 134,
        message = "equipment"
    ),
    MAGIC_STALL(
        full = arrayOf(4877),
        empty = arrayOf(4797),
        level = 65,
        rewards = arrayOf(Item(556, 1), Item(557, 1), Item(554, 1), Item(555, 1), Item(563, 1)),
        experience = 100.0,
        delay = 134,
        message = "equipment"
    ),
    GENERAL_STALL(
        full = arrayOf(4876),
        empty = arrayOf(4797),
        level = 5,
        rewards = arrayOf(Item(1931, 1), Item(2347, 1), Item(590, 1)),
        experience = 16.0,
        delay = 12,
        message = "goods"
    ),
    FOOD_STALL(
        full = arrayOf(4875),
        empty = arrayOf(4797),
        level = 5,
        rewards = arrayOf(Item(1963, 1)),
        experience = 16.0,
        delay = 12,
        message = "food"
    ),
    CANDLES(
        full = arrayOf(org.rs.consts.Scenery.CANDLES_19127),
        empty = arrayOf(org.rs.consts.Scenery.CANDLES_19127),
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

    fun getEmpty(id: Int): Int {
        val fullIndex = fullIDs.indexOf(id)
        return empty_ids[fullIndex]
    }

    val randomLoot: Item
        get() = rewards[RandomFunction.random(rewards.size)]

    companion object {
        var idMap: HashMap<Int, Stall> = HashMap()

        init {
            Arrays.stream(values()).forEach { entry: Stall ->
                entry.fullIDs.stream().forEach { id: Int -> idMap.putIfAbsent(id, entry) }
            }
        }

        fun forScenery(scenery: Scenery): Stall? {
            return idMap[scenery.id]
        }
    }
}