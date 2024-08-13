package content.global.skill.production.fletching.data

import core.api.consts.Items

/**
 * Kebbit bolt
 *
 * @property base The base value of the Kebbit bolt, representing its fundamental attribute.
 * @property product The product value of the Kebbit bolt, indicating its output or result.
 * @property level The level required to utilize or craft the Kebbit bolt.
 * @property experience The experience gained from using or crafting the Kebbit bolt.
 * @constructor Kebbit bolt Represents a new instance of the Kebbit bolt with specified attributes.
 */
enum class KebbitBolt(val base: Int, val product: Int, val level: Int, val experience: Double) {
    /**
     * Kebbit Bolt
     *
     * @constructor Kebbit Bolt
     */
    KEBBIT_BOLT(
        base = Items.KEBBIT_SPIKE_10105,
        product = Items.KEBBIT_BOLTS_10158,
        level = 32,
        experience = 5.80
    ),

    /**
     * Long Kebbit Bolt
     *
     * @constructor Long Kebbit Bolt
     */
    LONG_KEBBIT_BOLT(
        base = Items.LONG_KEBBIT_SPIKE_10107,
        product = Items.LONG_KEBBIT_BOLTS_10159,
        level = 83,
        experience = 7.90
    );

    companion object {
        val productMap = HashMap<Int, KebbitBolt>()

        init {
            for (product in KebbitBolt.values()) {
                productMap[product.base] = product
            }
        }
    }
}