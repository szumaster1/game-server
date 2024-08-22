package content.global.skill.production.crafting.data

import cfg.consts.Items

/**
 * Battlestaff data
 *
 * @param required The minimum level required to use the battlestaff
 * @param productId Unique identifier for the battlestaff product
 * @param amount The quantity of battlestaff available
 * @param requiredLevel The level needed to wield the battlestaff
 * @param experience The experience points gained from using the battlestaff
 * @constructor Battlestaff data
 */
enum class BattlestaffData(
    val required: Int,          // Minimum level required to use the battlestaff
    val productId: Int,        // Unique identifier for the battlestaff product
    val amount: Int,           // Quantity of battlestaff available
    val requiredLevel: Int,    // Level needed to wield the battlestaff
    val experience: Double,     // Experience points gained from using the battlestaff
) {
    /**
     * Water Battlestaff.
     */
    WATER_BATTLESTAFF(
        required = Items.WATER_ORB_571,
        productId = Items.WATER_BATTLESTAFF_1395,
        amount = 1,
        requiredLevel = 54,
        experience = 100.0
    ),

    /**
     * Earth Battlestaff.
     */
    EARTH_BATTLESTAFF(
        required = Items.EARTH_ORB_575,
        productId = Items.EARTH_BATTLESTAFF_1399,
        amount = 1,
        requiredLevel = 58,
        experience = 112.5
    ),

    /**
     * Fire Battlestaff.
     */
    FIRE_BATTLESTAFF(
        required = Items.FIRE_ORB_569,
        productId = Items.FIRE_BATTLESTAFF_1393,
        amount = 1,
        requiredLevel = 62,
        experience = 125.0
    ),

    /**
     * Air Battlestaff.
     */
    AIR_BATTLESTAFF(
        required = Items.AIR_ORB_573,
        productId = Items.AIR_BATTLESTAFF_1397,
        amount = 1,
        requiredLevel = 66,
        experience = 137.5
    );

    companion object {
        val productMap = HashMap<Int, BattlestaffData>()

        init {
            for (product in values()) {
                productMap[product.required] = product
            }
        }
    }
}
