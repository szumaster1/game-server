package content.global.skill.gathering.farming

import cfg.consts.Items

/**
 * Baskets and sacks.
 */
enum class BasketsAndSacks(val produceID: Int, val baseContainer: Int, val capacity: Int) {
    POTATO(
        Items.POTATO_1942,
        Items.POTATOES1_5420,
        10
    ),
    ONION(
        Items.ONION_1957,
        Items.ONIONS1_5440,
        10
    ),
    CABBAGE(
        Items.CABBAGE_1965,
        Items.CABBAGES1_5460,
        10
    ),
    APPLE(
        Items.COOKING_APPLE_1955,
        Items.APPLES1_5378,
        5
    ),
    BANANA(
        Items.BANANA_1963,
        Items.BANANAS1_5408,
        5
    ),
    ORANGE(
        Items.ORANGE_2108,
        Items.ORANGES1_5388,
        5
    ),
    STRAWBERRY(
        Items.STRAWBERRY_5504,
        Items.STRAWBERRIES1_5398,
        5
    ),
    TOMATO(
        Items.TOMATO_1982,
        Items.TOMATOES1_5960,
        5
    );

    val containers = ArrayList<Int>()

    companion object {
        private val map = HashMap<Int, BasketsAndSacks>()

        init {
            values().map { it.produceID to it }.toMap(BasketsAndSacks.map)
            for (b in values()) {
                for (i in 0 until b.capacity) {
                    map[b.baseContainer + (i * 2)] = b
                    b.containers.add(b.baseContainer + (i * 2))
                }
            }
        }

        /**
         * Get the BasketsAndSacks instance for the given item id.
         *
         * @param itemId The id of the item.
         * @return The BasketsAndSacks instance, or null if not found.
         */
        @JvmStatic
        fun forId(itemId: Int): BasketsAndSacks? {
            return BasketsAndSacks.Companion.map[itemId]
        }
    }

    /**
     * Check if the container is the last one.
     *
     * @param containerID The id of the container.
     * @return True if the container is the last one, false otherwise.
     */
    fun checkIsLast(containerID: Int): Boolean {
        return containerID == containers.last()
    }

    /**
     * Check if the container is the first one.
     *
     * @param containerID The id of the container.
     * @return True if the container is the first one, false otherwise.
     */
    fun checkIsFirst(containerID: Int): Boolean {
        return containerID == containers.first()
    }

    /**
     * Check which container the given id belongs to.
     *
     * @param containerID The id of the container.
     * @return The index of the container in the containers list.
     */
    fun checkWhich(containerID: Int): Int {
        return containers.indexOf(containerID)
    }
}
