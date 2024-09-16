package content.global.skill.production.fletching.data

import cfg.consts.Items

/**
 * Represents the enum storing the arrow head information.
 */
enum class ArrowHead(val unfinished: Int, val finished: Int, val level: Int, val experience: Double) {
    BRONZE_ARROW(
        unfinished = Items.BRONZE_ARROWTIPS_39,
        finished = Items.BRONZE_ARROW_882,
        level = 1,
        experience = 1.3
    ),
    IRON_ARROW(
        unfinished = Items.IRON_ARROWTIPS_40,
        finished = Items.IRON_ARROW_884,
        level = 15,
        experience = 2.5
    ),
    STEEL_ARROW(
        unfinished = Items.STEEL_ARROWTIPS_41,
        finished = Items.STEEL_ARROW_886,
        level = 30,
        experience = 5.0
    ),
    MITHRIL_ARROW(
        unfinished = Items.MITHRIL_ARROWTIPS_42,
        finished = Items.MITHRIL_ARROW_888,
        level = 45,
        experience = 7.5
    ),
    ADAMANT_ARROW(
        unfinished = Items.ADAMANT_ARROWTIPS_43,
        finished = Items.ADAMANT_ARROW_890,
        level = 60,
        experience = 10.0
    ),
    RUNE_ARROW(
        unfinished = Items.RUNE_ARROWTIPS_44,
        finished = Items.RUNE_ARROW_892,
        level = 75,
        experience = 12.5
    ),
    DRAGON_ARROW(
        unfinished = Items.DRAGON_ARROWTIPS_11237,
        finished = Items.DRAGON_ARROW_11212,
        level = 90,
        experience = 15.0
    ),
    BROAD_ARROW(
        unfinished = Items.BROAD_ARROW_HEADS_13278,
        finished = Items.BROAD_ARROW_4160,
        level = 52,
        experience = 15.0
    );

    companion object {
        val productMap: Map<Int, ArrowHead> = values().associateBy { it.unfinished }
    }
}
