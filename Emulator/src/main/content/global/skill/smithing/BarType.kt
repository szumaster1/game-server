package content.global.skill.smithing

import org.rs.consts.Items

/**
 * Bar type enum class.
 * @author Emperor
 */
enum class BarType(val bar: Int, val experience: Double, val barName: String) {
    BRONZE(
        bar = Items.BRONZE_BAR_2349,
        experience = 12.5,
        barName = "Bronze Smithing"
    ),
    BLURITE(
        bar = Items.BLURITE_BAR_9467,
        experience = 16.0,
        barName = "Blurite Smithing"
    ),
    IRON(
        bar = Items.IRON_BAR_2351,
        experience = 25.0,
        barName = "Iron Smithing"
    ),
    STEEL(
        bar = Items.STEEL_BAR_2353,
        experience = 37.5,
        barName = "Steel Smithing"
    ),
    MITHRIL(
        bar = Items.MITHRIL_BAR_2359,
        experience = 50.0,
        barName = "Mithril Smithing"
    ),
    ADAMANT(
        bar = Items.ADAMANTITE_BAR_2361,
        experience = 62.5,
        barName = "Adamant Smithing"
    ),
    RUNITE(
        bar = Items.RUNITE_BAR_2363,
        experience = 75.0,
        barName = "Runite Smithing"
    );

    companion object {

        /**
         * Gets the bar type for a given item id.
         *
         * @param itemId the item id.
         * @return the [BarType] for the given [itemId], or `null` if no match is found.
         */
        fun getBarTypeForId(itemId: Int): BarType? {
            when (itemId) {
                Items.BRONZE_BAR_2349 -> return BRONZE
                Items.BLURITE_BAR_9467 -> return BLURITE
                Items.IRON_BAR_2351 -> return IRON
                Items.STEEL_BAR_2353 -> return STEEL
                Items.MITHRIL_BAR_2359 -> return MITHRIL
                Items.ADAMANTITE_BAR_2361 -> return ADAMANT
                Items.RUNITE_BAR_2363 -> return RUNITE
            }
            return null
        }
    }
}
