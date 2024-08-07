package content.global.skill.production.crafting.data

import core.api.consts.Items

/**
 * Battlestaff data
 *
 * @property required
 * @property productId
 * @property amount
 * @property requiredLevel
 * @property experience
 * @constructor Battlestaff data
 */
enum class BattlestaffData(
    val required: Int,
    val productId: Int,
    val amount: Int,
    val requiredLevel: Int,
    val experience: Double,
) {
    /**
     * Water Battlestaff
     *
     * @constructor Water Battlestaff
     */
    WATER_BATTLESTAFF(
        required = Items.WATER_ORB_571,
        productId = Items.WATER_BATTLESTAFF_1395,
        amount = 1,
        requiredLevel = 54,
        experience = 100.0
    ),

    /**
     * Earth Battlestaff
     *
     * @constructor Earth Battlestaff
     */
    EARTH_BATTLESTAFF(
        required = Items.EARTH_ORB_575,
        productId = Items.EARTH_BATTLESTAFF_1399,
        amount = 1,
        requiredLevel = 58,
        experience = 112.5
    ),

    /**
     * Fire Battlestaff
     *
     * @constructor Fire Battlestaff
     */
    FIRE_BATTLESTAFF(
        required = Items.FIRE_ORB_569,
        productId = Items.FIRE_BATTLESTAFF_1393,
        amount = 1,
        requiredLevel = 62,
        experience = 125.0
    ),

    /**
     * Air Battlestaff
     *
     * @constructor Air Battlestaff
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
