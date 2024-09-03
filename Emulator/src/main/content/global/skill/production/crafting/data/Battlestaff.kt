package content.global.skill.production.crafting.data

import cfg.consts.Items

/**
 * Represents the Battlestaff's.
 */
enum class Battlestaff(val required: Int, val productId: Int, val amount: Int, val requiredLevel: Int, val experience: Double, ) {
    /**
     * the water battlestaff.
     */
    WATER_BATTLESTAFF(
        required = Items.WATER_ORB_571,
        productId = Items.WATER_BATTLESTAFF_1395,
        amount = 1,
        requiredLevel = 54,
        experience = 100.0
    ),

    /**
     * The earth battlestaff.
     */
    EARTH_BATTLESTAFF(
        required = Items.EARTH_ORB_575,
        productId = Items.EARTH_BATTLESTAFF_1399,
        amount = 1,
        requiredLevel = 58,
        experience = 112.5
    ),

    /**
     * The fire battlestaff.
     */
    FIRE_BATTLESTAFF(
        required = Items.FIRE_ORB_569,
        productId = Items.FIRE_BATTLESTAFF_1393,
        amount = 1,
        requiredLevel = 62,
        experience = 125.0
    ),

    /**
     * The air battlestaff.
     */
    AIR_BATTLESTAFF(
        required = Items.AIR_ORB_573,
        productId = Items.AIR_BATTLESTAFF_1397,
        amount = 1,
        requiredLevel = 66,
        experience = 137.5
    );

    companion object {
        @JvmStatic
        fun forId(itemId: Int): Battlestaff? {
            for (product in Battlestaff.values()) {
                if (product.required == itemId) {
                    return product
                }
            }
            return null
        }
    }
}
