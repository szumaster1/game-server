package content.global.skill.production.smithing.data

import cfg.consts.Items
import core.game.node.item.Item

/**
 * Represents the [Bar] that can be created.
 *
 * @param level         the level.
 * @param experience    the experience.
 * @param product       the product.
 * @param ores          the ores.
 */
enum class Bar(
    val level: Int,
    val experience: Double,
    val product: Item,
    vararg ores: Item
) {
    /**
     * The bronze.
     */
    BRONZE(1, 6.2, Item(Items.BRONZE_BAR_2349, 1), Item(Items.COPPER_ORE_436, 1), Item(Items.TIN_ORE_438, 1)),

    /**
     * The blurite.
     */
    BLURITE(8, 8.0, Item(Items.BLURITE_BAR_9467, 1), Item(Items.BLURITE_ORE_668, 1)),

    /**
     * The iron.
     */
    IRON(15, 12.5, Item(Items.IRON_BAR_2351, 1), Item(Items.IRON_ORE_440)),

    /**
     * The silver.
     */
    SILVER(20, 13.7, Item(Items.SILVER_BAR_2355, 1), Item(Items.SILVER_ORE_442, 1)),

    /**
     * The steel.
     */
    STEEL(30, 17.5, Item(Items.STEEL_BAR_2353, 1), Item(Items.COAL_453, 2), Item(Items.IRON_ORE_440, 1)),

    /**
     * The gold.
     */
    GOLD(40, 22.5, Item(Items.GOLD_BAR_2357, 1), Item(Items.GOLD_ORE_444, 1)),

    /**
     * The mithril.
     */
    MITHRIL(50, 30.0, Item(Items.MITHRIL_BAR_2359, 1), Item(Items.MITHRIL_ORE_447, 1), Item(Items.COAL_453, 4)),

    /**
     * The adamant.
     */
    ADAMANT(70, 37.5, Item(Items.ADAMANTITE_BAR_2361, 1), Item(Items.ADAMANTITE_ORE_449, 1), Item(Items.COAL_453, 6)),

    /**
     * The runite.
     */
    RUNITE(85, 50.0, Item(Items.RUNITE_BAR_2363, 1), Item(Items.RUNITE_ORE_451, 1), Item(Items.COAL_453, 8));

    val ores: Array<Item> = ores as Array<Item>

    companion object {

        /**
         * Gets a bar based on the provided product.
         *
         * @param id    The ID of the product to search for.
         * @return The [Bar] associated with the given ID, or null if not found.
         */
        @JvmStatic
        fun forId(id: Int): Bar? {
            for (bar in values()) {
                if (bar.product.id == id) {
                    return bar
                }
            }
            return null
        }

        /**
         * Gets a bar based on the provided ore.
         *
         * @param id    The ID of the ore to search for.
         * @return The [Bar] associated with the given ore ID, or null if not found.
         */
        @JvmStatic
        fun forOre(id: Int): Bar? {
            for (bar in values()) {
                for (i in bar.ores) {
                    if (i.id == id) {
                        return bar
                    }
                }
            }
            return null
        }
    }
}
