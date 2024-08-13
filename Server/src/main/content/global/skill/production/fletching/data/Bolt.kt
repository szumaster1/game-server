package content.global.skill.production.fletching.data

import core.api.consts.Items

/**
 * Bolt
 *
 * @param unfinished Represents the number of unfinished tasks associated with the Bolt.
 * @param finished Represents the number of completed tasks associated with the Bolt.
 * @param level Indicates the current level of the Bolt.
 * @param experience Represents the experience points accumulated by the Bolt.
 * @constructor Bolt Represents a new instance of the Bolt enum with specified properties.
 */
enum class Bolt(var unfinished: Int, var finished: Int, var level: Int, var experience: Double) {
    /**
     * Bronze Bolt
     *
     * @constructor Bronze Bolt
     */
    BRONZE_BOLT(
        unfinished = Items.BRONZE_BOLTS_UNF_9375,
        finished = Items.BRONZE_BOLTS_877,
        level = 9,
        experience = 0.5
    ),

    /**
     * Blurite Bolt
     *
     * @constructor Blurite Bolt
     */
    BLURITE_BOLT(
        unfinished = Items.BLURITE_BOLTS_UNF_9376,
        finished = Items.BLURITE_BOLTS_9139,
        level = 24,
        experience = 1.0
    ),

    /**
     * Iron Bolt
     *
     * @constructor Iron Bolt
     */
    IRON_BOLT(
        unfinished = Items.IRON_BOLTS_UNF_9377,
        finished = Items.IRON_BOLTS_9140,
        level = 39,
        experience = 1.5
    ),

    /**
     * Silver Bolt
     *
     * @constructor Silver Bolt
     */
    SILVER_BOLT(
        unfinished = Items.SILVER_BOLTS_UNF_9382,
        finished = Items.SILVER_BOLTS_9145,
        level = 43,
        experience = 2.5
    ),

    /**
     * Steel Bolt
     *
     * @constructor Steel Bolt
     */
    STEEL_BOLT(
        unfinished = Items.STEEL_BOLTS_UNF_9378,
        finished = Items.STEEL_BOLTS_9141,
        level = 46,
        experience = 3.5
    ),

    /**
     * Mithril Bolt
     *
     * @constructor Mithril Bolt
     */
    MITHRIL_BOLT(
        unfinished = Items.MITHRIL_BOLTS_UNF_9379,
        finished = Items.MITHRIL_BOLTS_9142,
        level = 54,
        experience = 5.0
    ),

    /**
     * Broad Bolt
     *
     * @constructor Broad Bolt
     */
    BROAD_BOLT(
        unfinished = Items.BROAD_BOLTS_UNF_13279,
        finished = Items.BROAD_TIPPED_BOLTS_13280,
        level = 55,
        experience = 3.0
    ),

    /**
     * Adamantite Bolt
     *
     * @constructor Adamantite Bolt
     */
    ADAMANTITE_BOLT(
        unfinished = Items.ADAMANT_BOLTS_UNF_9380,
        finished = Items.ADAMANT_BOLTS_9143,
        level = 61,
        experience = 7.0
    ),

    /**
     * Runite Bolt
     *
     * @constructor Runite Bolt
     */
    RUNITE_BOLT(
        unfinished = Items.RUNITE_BOLTS_UNF_9381,
        finished = Items.RUNE_BOLTS_9144,
        level = 69,
        experience = 10.0
    );

    companion object {
        val productMap = HashMap<Int, Bolt>()

        init {
            for (product in Bolt.values()) {
                productMap[product.unfinished] = product
            }
        }

        fun isBolt(id: Int): Boolean {
            return productMap[id] != null
        }
    }
}