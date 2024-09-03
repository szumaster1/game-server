package content.global.skill.production.fletching.data

import cfg.consts.Items

/**
 * Represents the different types of brutal arrows.
 */
enum class BrutalArrow(var base: Int, var product: Int, var level: Int, var experience: Double) {
    /**
     * The bronze brutal arrow.
     */
    BRONZE_BRUTAL(
        base = Items.BRONZE_NAILS_4819,
        product = Items.BRONZE_BRUTAL_4773,
        level = 7,
        experience = 1.4
    ),

    /**
     * The iron brutal arrow.
     */
    IRON_BRUTAL(
        base = Items.IRON_NAILS_4820,
        product = Items.IRON_BRUTAL_4778,
        level = 18,
        experience = 2.6
    ),

    /**
     * The steel brutal arrow.
     */
    STEEL_BRUTAL(
        base = Items.STEEL_NAILS_1539,
        product = Items.STEEL_BRUTAL_4783,
        level = 33,
        experience = 5.1
    ),

    /**
     * The black brutal arrow.
     */
    BLACK_BRUTAL(
        base = Items.BLACK_NAILS_4821,
        product = Items.BLACK_BRUTAL_4788,
        level = 38,
        experience = 6.4
    ),

    /**
     * The mithril brutal arrow.
     */
    MITHRIL_BRUTAL(
        base = Items.MITHRIL_NAILS_4822,
        product = Items.MITHRIL_BRUTAL_4793,
        level = 49,
        experience = 7.5
    ),

    /**
     * The adamant brutal arrow.
     */
    ADAMANT_BRUTAL(
        base = Items.ADAMANTITE_NAILS_4823,
        product = Items.ADAMANT_BRUTAL_4798,
        level = 62,
        experience = 10.1
    ),

    /**
     * The rune brutal arrow.
     */
    RUNE_BRUTAL(
        base = Items.RUNE_NAILS_4824,
        product = Items.RUNE_BRUTAL_4803,
        level = 77,
        experience = 12.5
    );

    companion object {
        val productMap = HashMap<Int, BrutalArrow>()

        init {
            for (product in BrutalArrow.values()) {
                productMap[product.base] = product
            }
        }
    }
}