package content.activity.oldman

import cfg.consts.Items
import core.tools.RandomFunction

/**
 * Item delivery.
 *
 * @param item The item being delivered.
 * @param min The minimum quantity of the item.
 * @param max The maximum quantity of the item.
 * @constructor Creates an instance of ItemDelivery.
 */
enum class ItemDelivery(val item: Int, val min: Int, val max: Int) {
    /**
     * Anchovies.
     */
    ANCHOVIES(
        item = Items.ANCHOVIES_319,
        min = 1,
        max = 15
    ),

    /**
     * Ball Of Wool.
     */
    BALL_OF_WOOL(
        item = Items.BALL_OF_WOOL_1759,
        min = 1,
        max = 15
    ),

    /**
     * Beer Glass.
     */
    BEER_GLASS(
        item = Items.BEER_GLASS_1919,
        min = 1,
        max = 15
    ),

    /**
     * Bones.
     */
    BONES(
        item = Items.BONES_526,
        min = 1,
        max = 15
    ),

    /**
     * Bread.
     */
    BREAD(
        item = Items.BREAD_2309,
        min = 1,
        max = 15
    ),

    /**
     * Bronze Arrow.
     */
    BRONZE_ARROW(
        item = Items.BRONZE_ARROW_882,
        min = 1,
        max = 15
    ),

    /**
     * Bronze Bar.
     */
    BRONZE_BAR(
        item = Items.BRONZE_BAR_2349,
        min = 1,
        max = 15
    ),

    /**
     * Bronze Dagger.
     */
    BRONZE_DAGGER(
        item = Items.BRONZE_DAGGER_1205,
        min = 1,
        max = 15
    ),

    /**
     * Bronze Axe.
     */
    BRONZE_AXE(
        item = Items.BRONZE_AXE_1351,
        min = 1,
        max = 15
    ),

    /**
     * Bronze Full Helm.
     */
    BRONZE_FULL_HELM(
        item = Items.BRONZE_FULL_HELM_1155,
        min = 1,
        max = 15
    ),

    /**
     * Bronze Knife.
     */
    BRONZE_KNIFE(
        item = Items.BRONZE_KNIFE_864,
        min = 1,
        max = 15
    ),

    /**
     * Bronze Mace.
     */
    BRONZE_MACE(
        item = Items.BRONZE_MACE_1422,
        min = 1,
        max = 15
    ),

    /**
     * Bronze Medium Helm.
     */
    BRONZE_MEDIUM_HELM(
        item = Items.BRONZE_MED_HELM_1139,
        min = 1,
        max = 15
    ),

    /**
     * Bronze Spear.
     */
    BRONZE_SPEAR(
        item = Items.BRONZE_SPEAR_1237,
        min = 1,
        max = 15
    ),

    /**
     * Bronze Sword.
     */
    BRONZE_SWORD(
        item = Items.BRONZE_SWORD_1277,
        min = 1,
        max = 15
    ),

    /**
     * Bronze Warhammer.
     */
    BRONZE_WARHAMMER(
        item = Items.BRONZE_WARHAMMER_1337,
        min = 1,
        max = 15
    ),

    /**
     * Bronze Wire.
     */
    BRONZE_WIRE(
        item = Items.BRONZE_WIRE_1794,
        min = 1,
        max = 15
    ),

    /**
     * Beer.
     */
    BEER(
        item = Items.BEER_1917,
        min = 1,
        max = 15
    ),

    /**
     * Bowstring.
     */
    BOWSTRING(
        item = Items.BOW_STRING_1777,
        min = 1,
        max = 15
    ),

    /**
     * Cadava Berries.
     */
    CADAVA_BERRIES(
        item = Items.CADAVA_BERRIES_753,
        min = 1,
        max = 15
    ),

    /**
     * Cooked Chicken.
     */
    COOKED_CHICKEN(
        item = Items.COOKED_CHICKEN_2140,
        min = 1,
        max = 15
    ),

    /**
     * Cooked Meat.
     */
    COOKED_MEAT(
        item = Items.COOKED_MEAT_2142,
        min = 1,
        max = 15
    ),

    /**
     * Copper Ore.
     */
    COPPER_ORE(
        item = Items.COPPER_ORE_436,
        min = 1,
        max = 15
    ),

    /**
     * Cowhide.
     */
    COWHIDE(
        item = Items.COWHIDE_1739,
        min = 1,
        max = 15
    ),

    /**
     * Egg.
     */
    EGG(
        item = Items.EGG_1944,
        min = 1,
        max = 15
    ),

    /**
     * Feathers.
     */
    FEATHERS(
        item = Items.FEATHER_314,
        min = 1,
        max = 15
    ),

    /**
     * Grain.
     */
    GRAIN(
        item = Items.GRAIN_1947,
        min = 1,
        max = 15
    ),

    /**
     * Headless Arrows.
     */
    HEADLESS_ARROWS(
        item = Items.HEADLESS_ARROW_53,
        min = 1,
        max = 15
    ),

    /**
     * Iron Arrowtips.
     */
    IRON_ARROWTIPS(
        item = Items.IRON_ARROWTIPS_40,
        min = 1,
        max = 15
    ),

    /**
     * Iron Mace.
     */
    IRON_MACE(
        item = Items.IRON_MACE_1420,
        min = 1,
        max = 15
    ),

    /**
     * Iron Ore.
     */
    IRON_ORE(
        item = Items.IRON_ORE_440,
        min = 1,
        max = 15
    ),

    /**
     * Iron Throwing Knives.
     */
    IRON_THROWING_KNIVES(
        item = Items.IRON_THROWNAXE_801,
        min = 1,
        max = 15
    ),

    /**
     * Iron Warhammer.
     */
    IRON_WARHAMMER(
        item = Items.IRON_WARHAMMER_1335,
        min = 1,
        max = 15
    ),

    /**
     * Leather Boots.
     */
    LEATHER_BOOTS(
        item = Items.LEATHER_BOOTS_1061,
        min = 1,
        max = 15
    ),

    /**
     * Leather Cowl.
     */
    LEATHER_COWL(
        item = Items.LEATHER_COWL_1167,
        min = 1,
        max = 15
    ),

    /**
     * Leather Gloves.
     */
    LEATHER_GLOVES(
        item = Items.LEATHER_GLOVES_1059,
        min = 1,
        max = 15
    ),

    /**
     * Logs.
     */
    LOGS(
        item = Items.LOGS_1511,
        min = 1,
        max = 15
    ),

    /**
     * Molten Glass.
     */
    MOLTEN_GLASS(
        item = Items.MOLTEN_GLASS_1775,
        min = 1,
        max = 15
    ),

    /**
     * Pot Of Flour.
     */
    POT_OF_FLOUR(
        item = Items.POT_OF_FLOUR_1933,
        min = 1,
        max = 15
    ),

    /**
     * Potatoes.
     */
    POTATOES(
        item = Items.POTATO_1942,
        min = 1,
        max = 15
    ),

    /**
     * Raw Anchovies.
     */
    RAW_ANCHOVIES(
        item = Items.RAW_ANCHOVIES_321,
        min = 1,
        max = 15
    ),

    /**
     * Raw Rat Meat.
     */
    RAW_RAT_MEAT(
        item = Items.RAW_RAT_MEAT_2134,
        min = 1,
        max = 15
    ),

    /**
     * Rune Essence.
     */
    RUNE_ESSENCE(
        item = Items.RUNE_ESSENCE_1436,
        min = 1,
        max = 15
    ),

    /**
     * Pure Essence.
     */
    PURE_ESSENCE(
        item = Items.PURE_ESSENCE_7936,
        min = 1,
        max = 15
    ),

    /**
     * Shrimp.
     */
    SHRIMP(
        item = Items.SHRIMPS_315,
        min = 1,
        max = 15
    ),

    /**
     * Silk.
     */
    SILK(
        item = Items.SILK_950,
        min = 1,
        max = 15
    ),

    /**
     * Soft Clay.
     */
    SOFT_CLAY(
        item = Items.SOFT_CLAY_1761,
        min = 1,
        max = 15
    );

    // Generates a random amount between min and max
    val amount = RandomFunction.random(min, max)

    companion object {
        val deliveryMap = HashMap<Int, ItemDelivery>()

        init {
            // Populates the deliveryMap with the item and its corresponding ItemDelivery enum
            for (delivery in ItemDelivery.values()) {
                deliveryMap[delivery.item] = delivery
            }

            // Populates the deliveryMap with the amount and its corresponding ItemDelivery enum
            for (amount in ItemDelivery.values()){
                deliveryMap[amount.item] = amount
            }
        }

        /**
         * Returns the ItemDelivery enum for the given item ID.
         *
         * @param task The item ID.
         * @return The corresponding ItemDelivery enum, or null if not found.
         */
        fun forId(task: Int): ItemDelivery? {
            for (id in ItemDelivery.values()) {
                if (id.item == task) {
                    return id
                }
            }
            return null
        }
    }
}