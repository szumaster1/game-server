package content.global.skill.production.fletching.data

import cfg.consts.Items

/**
 * Represents the enum storing the arrow head information.
 */
enum class ArrowHead(var unfinished: Int, var finished: Int, var level: Int, var experience: Double) {
    /**
     * The bronze arrow.
     */
    BRONZE_ARROW(
        unfinished = Items.BRONZE_ARROWTIPS_39,
        finished = Items.BRONZE_ARROW_882,
        level = 1,
        experience = 2.6
    ),

    /**
     * The iron arrow.
     */
    IRON_ARROW(
        unfinished = Items.IRON_ARROWTIPS_40,
        finished = Items.IRON_ARROW_884,
        level = 15,
        experience = 3.8
    ),

    /**
     * The steel arrow.
     */
    STEEL_ARROW(
        unfinished = Items.STEEL_ARROWTIPS_41,
        finished = Items.STEEL_ARROW_886,
        level = 30,
        experience = 6.3
    ),

    /**
     * The Mithril arrow.
     */
    MITHRIL_ARROW(
        unfinished = Items.MITHRIL_ARROWTIPS_42,
        finished = Items.MITHRIL_ARROW_888,
        level = 45,
        experience = 8.8
    ),

    /**
     * The adamant arrow.
     */
    ADAMANT_ARROW(
        unfinished = Items.ADAMANT_ARROWTIPS_43,
        finished = Items.ADAMANT_ARROW_890,
        level = 60,
        experience = 11.3
    ),

    /**
     * The rune arrow.
     */
    RUNE_ARROW(
        unfinished = Items.RUNE_ARROWTIPS_44,
        finished = Items.RUNE_ARROW_892,
        level = 75,
        experience = 13.8
    ),

    /**
     * The dragon arrow.
     */
    DRAGON_ARROW(
        unfinished = Items.DRAGON_ARROWTIPS_11237,
        finished = Items.DRAGON_ARROW_11212,
        level = 90,
        experience = 16.3
    ),

    /**
     * The broad arrow.
     */
    BROAD_ARROW(
        unfinished = Items.BROAD_ARROW_HEADS_13278,
        finished = Items.BROAD_ARROW_4160,
        level = 52,
        experience = 15.0
    );

    companion object {
        val productMap = HashMap<Int, ArrowHead>()

        init {
            for (product in ArrowHead.values()) {
                productMap[product.unfinished] = product
            }
        }
    }
}