package content.global.skill.production.fletching.data

import core.api.consts.Items

enum class KebbitBolt(val base: Int, val product: Int, val level: Int, val experience: Double) {
    KEBBIT_BOLT(
        base = Items.KEBBIT_SPIKE_10105,
        product = Items.KEBBIT_BOLTS_10158,
        level = 32,
        experience = 5.80
    ),
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