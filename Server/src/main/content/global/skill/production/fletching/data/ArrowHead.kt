package content.global.skill.production.fletching.data

import core.api.consts.Items

enum class ArrowHead(var unfinished: Int, var finished: Int, var level: Int, var experience: Double) {
    BRONZE_ARROW(Items.BRONZE_ARROWTIPS_39, Items.BRONZE_ARROW_882, 1, 2.6),
    IRON_ARROW(Items.IRON_ARROWTIPS_40, Items.IRON_ARROW_884, 15, 3.8),
    STEEL_ARROW(Items.STEEL_ARROWTIPS_41, Items.STEEL_ARROW_886, 30, 6.3),
    MITHRIL_ARROW(Items.MITHRIL_ARROWTIPS_42, Items.MITHRIL_ARROW_888, 45, 8.8),
    ADAMANT_ARROW(Items.ADAMANT_ARROWTIPS_43, Items.ADAMANT_ARROW_890, 60, 11.3),
    RUNE_ARROW(Items.RUNE_ARROWTIPS_44, Items.RUNE_ARROW_892, 75, 13.8),
    DRAGON_ARROW(Items.DRAGON_ARROWTIPS_11237, Items.DRAGON_ARROW_11212, 90, 16.3),
    BROAD_ARROW(Items.BROAD_ARROW_HEADS_13278, Items.BROAD_ARROW_4160, 52, 15.0);

    companion object {
        val productMap = HashMap<Int, ArrowHead>()

        init {
            for (product in ArrowHead.values()) {
                productMap[product.unfinished] = product
            }
        }
    }
}