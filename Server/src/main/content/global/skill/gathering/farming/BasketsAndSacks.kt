package content.global.skill.gathering.farming

import core.api.consts.Items

/**
 * Baskets and sacks
 *
 * @property produceID
 * @property baseContainer
 * @property capacity
 * @constructor Baskets and sacks
 */
enum class BasketsAndSacks(val produceID: Int, val baseContainer: Int, val capacity : Int) {
    /**
     * Potato.
     */
    POTATO(Items.POTATO_1942, Items.POTATOES1_5420, 10),

    /**
     * Onion.
     */
    ONION(Items.ONION_1957, Items.ONIONS1_5440, 10),

    /**
     * Cabbage.
     */
    CABBAGE(Items.CABBAGE_1965, Items.CABBAGES1_5460, 10),

    /**
     * Apple.
     */
    APPLE(Items.COOKING_APPLE_1955, Items.APPLES1_5378, 5),

    /**
     * Banana.
     */
    BANANA(Items.BANANA_1963, Items.BANANAS1_5408, 5),

    /**
     * Orange.
     */
    ORANGE(Items.ORANGE_2108, Items.ORANGES1_5388, 5),

    /**
     * Strawberry.
     */
    STRAWBERRY(Items.STRAWBERRY_5504, Items.STRAWBERRIES1_5398, 5),

    /**
     * Tomato.
     */
    TOMATO(Items.TOMATO_1982, Items.TOMATOES1_5960, 5);

    val containers = ArrayList<Int>()

    companion object{
        private val map = HashMap<Int, BasketsAndSacks>()

        init {
            values().map { it.produceID to it }.toMap(BasketsAndSacks.map)
            for(b in values()){
                for(i in 0 until b.capacity){
                    map[b.baseContainer + (i * 2)] = b
                    b.containers.add(b.baseContainer + (i * 2))
                }
            }
        }

        @JvmStatic
        fun forId(itemId: Int): BasketsAndSacks?{
            return BasketsAndSacks.Companion.map[itemId]
        }
    }

    /**
     * Check is last
     *
     * @param containerID
     * @return
     */
    fun checkIsLast(containerID: Int): Boolean {
        return containerID == containers.last()
    }

    /**
     * Check is first
     *
     * @param containerID
     * @return
     */
    fun checkIsFirst(containerID: Int): Boolean {
        return containerID == containers.first()
    }

    /**
     * Check which
     *
     * @param containerID
     * @return
     */
    fun checkWhich(containerID: Int): Int{
        return containers.indexOf(containerID)
    }
}