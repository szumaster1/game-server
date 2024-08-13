package content.global.skill.gathering.farming

import core.api.consts.Items

/**
 * Baskets and sacks.
 *
 * @property produceID     The ID of the produce.
 * @property baseContainer The ID of the base container.
 * @property capacity      The capacity of the container.
 * @constructor Represents a new instance of the BasketsAndSacks enum class.
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

        /**
         * Get the BasketsAndSacks instance for the given item ID.
         *
         * @param itemId The ID of the item.
         * @return The BasketsAndSacks instance, or null if not found.
         */
        @JvmStatic
        fun forId(itemId: Int): BasketsAndSacks?{
            return BasketsAndSacks.Companion.map[itemId]
        }
    }

    /**
     * Check if the container is the last one.
     *
     * @param containerID The ID of the container.
     * @return True if the container is the last one, false otherwise.
     */
    fun checkIsLast(containerID: Int): Boolean {
        return containerID == containers.last()
    }

    /**
     * Check if the container is the first one.
     *
     * @param containerID The ID of the container.
     * @return True if the container is the first one, false otherwise.
     */
    fun checkIsFirst(containerID: Int): Boolean {
        return containerID == containers.first()
    }

    /**
     * Check which container the given ID belongs to.
     *
     * @param containerID The ID of the container.
     * @return The index of the container in the containers list.
     */
    fun checkWhich(containerID: Int): Int{
        return containers.indexOf(containerID)
    }
}
