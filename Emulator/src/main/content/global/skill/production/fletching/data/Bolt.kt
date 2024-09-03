package content.global.skill.production.fletching.data

import cfg.consts.Items

/**
 * Represents the bolt data.
 */
enum class Bolt(var unfinished: Int, var finished: Int, var level: Int, var experience: Double) {
    /**
     * the bronze bolt.
     */
    BRONZE_BOLT(
        unfinished = Items.BRONZE_BOLTS_UNF_9375,
        finished = Items.BRONZE_BOLTS_877,
        level = 9,
        experience = 0.5
    ),

    /**
     * the blurite bolt.
     */
    BLURITE_BOLT(
        unfinished = Items.BLURITE_BOLTS_UNF_9376,
        finished = Items.BLURITE_BOLTS_9139,
        level = 24,
        experience = 1.0
    ),

    /**
     * the iron bolt.
     */
    IRON_BOLT(
        unfinished = Items.IRON_BOLTS_UNF_9377,
        finished = Items.IRON_BOLTS_9140,
        level = 39,
        experience = 1.5
    ),

    /**
     * the silver bolt.
     */
    SILVER_BOLT(
        unfinished = Items.SILVER_BOLTS_UNF_9382,
        finished = Items.SILVER_BOLTS_9145,
        level = 43,
        experience = 2.5
    ),

    /**
     * the steel bolt.
     */
    STEEL_BOLT(
        unfinished = Items.STEEL_BOLTS_UNF_9378,
        finished = Items.STEEL_BOLTS_9141,
        level = 46,
        experience = 3.5
    ),

    /**
     * the mithril bolt.
     */
    MITHRIL_BOLT(
        unfinished = Items.MITHRIL_BOLTS_UNF_9379,
        finished = Items.MITHRIL_BOLTS_9142,
        level = 54,
        experience = 5.0
    ),

    /**
     * the broad bolt.
     */
    BROAD_BOLT(
        unfinished = Items.BROAD_BOLTS_UNF_13279,
        finished = Items.BROAD_TIPPED_BOLTS_13280,
        level = 55,
        experience = 3.0
    ),

    /**
     * the adamant bolt.
     */
    ADAMANT_BOLT(
        unfinished = Items.ADAMANT_BOLTS_UNF_9380,
        finished = Items.ADAMANT_BOLTS_9143,
        level = 61,
        experience = 7.0
    ),

    /**
     * the runite bolt.
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