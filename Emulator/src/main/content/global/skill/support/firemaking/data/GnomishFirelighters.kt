package content.global.skill.support.firemaking.data

import org.rs.consts.Items

/**
 * Represents the gnomish firelighters.
 */
enum class GnomishFirelighters(val base: Int, val product: Int) {
    RED(
        base = Items.RED_FIRELIGHTER_7329,
        product = Items.RED_LOGS_7404
    ),
    GREEN(
        base = Items.GREEN_FIRELIGHTER_7330,
        product = Items.GREEN_LOGS_7405
    ),
    BLUE(
        base = Items.BLUE_FIRELIGHTER_7331,
        product = Items.BLUE_LOGS_7406
    ),
    PURPLE(
        base = Items.PURPLE_FIRELIGHTER_10326,
        product = Items.PURPLE_LOGS_10329
    ),
    WHITE(
        base = Items.WHITE_FIRELIGHTER_10327,
        product = Items.WHITE_LOGS_10328
    );

    companion object {
        fun forProduct(product: Int): GnomishFirelighters? = values().find { it.base == product }
    }
}
