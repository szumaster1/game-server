package content.global.skill.production.fletching.data

import cfg.consts.Items
import core.game.node.item.Item

/**
 * Represents the Kebbit bolts.
 */
enum class KebbitBolt(val base: Int, val product: Int, val level: Int, val experience: Double) {
    /**
     * The kebbit bolt.
     */
    KEBBIT_BOLT(
        base = Items.KEBBIT_SPIKE_10105,
        product = Items.KEBBIT_BOLTS_10158,
        level = 32,
        experience = 5.80
    ),

    /**
     * The long kebbit bolt.
     */
    LONG_KEBBIT_BOLT(
        base = Items.LONG_KEBBIT_SPIKE_10107,
        product = Items.LONG_KEBBIT_BOLTS_10159,
        level = 83,
        experience = 7.90
    );

    companion object {

        /**
         * Get the bolt.
         *
         * @param item the item id.
         * @return the bolt
         */
        fun forId(item: Item): KebbitBolt? {
            for (k in KebbitBolt.values()) {
                if (k.base == item.id) {
                    return k
                }
            }
            return null
        }
    }
}