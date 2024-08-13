package content.global.skill.support.firemaking.data

import core.api.consts.Items

/**
 * Gnomish firelighters
 *
 * @param base The base item ID for the firelighter.
 * @param product The product item ID for the logs associated with the firelighter.
 * @constructor Gnomish firelighters
 */
enum class GnomishFirelighters(val base: Int, val product: Int) {
    /**
     * Red firelighter
     *
     * @constructor Red firelighter with its associated log.
     */
    RED(Items.RED_FIRELIGHTER_7329, Items.RED_LOGS_7404),

    /**
     * Green firelighter
     *
     * @constructor Green firelighter with its associated log.
     */
    GREEN(Items.GREEN_FIRELIGHTER_7330, Items.GREEN_LOGS_7405),

    /**
     * Blue firelighter
     *
     * @constructor Blue firelighter with its associated log.
     */
    BLUE(Items.BLUE_FIRELIGHTER_7331, Items.BLUE_LOGS_7406),

    /**
     * Purple firelighter
     *
     * @constructor Purple firelighter with its associated log.
     */
    PURPLE(Items.PURPLE_FIRELIGHTER_10326, Items.PURPLE_LOGS_10329),

    /**
     * White firelighter
     *
     * @constructor White firelighter with its associated log.
     */
    WHITE(Items.WHITE_FIRELIGHTER_10327, Items.WHITE_LOGS_10328);

    companion object {
        /**
         * Finds a Gnomish firelighter by its product ID.
         *
         * @param product The product ID to search for.
         * @return The corresponding GnomishFirelighter or null if not found.
         */
        fun forProduct(product: Int): GnomishFirelighters? = values().find { it.base == product }
    }
}