package content.global.skill.production.crafting.data

import core.api.consts.Items

enum class BattlestaffData(
    val required: Int,
    val productId: Int,
    val amount: Int,
    val requiredLevel: Int,
    val experience: Double,
) {
    WATER_BATTLESTAFF(
        required = Items.WATER_ORB_571,
        productId = Items.WATER_BATTLESTAFF_1395,
        amount = 1,
        requiredLevel = 54,
        experience = 100.0
    ),
    EARTH_BATTLESTAFF(
        required = Items.EARTH_ORB_575,
        productId = Items.EARTH_BATTLESTAFF_1399,
        amount = 1,
        requiredLevel = 58,
        experience = 112.5
    ),
    FIRE_BATTLESTAFF(
        required = Items.FIRE_ORB_569,
        productId = Items.FIRE_BATTLESTAFF_1393,
        amount = 1,
        requiredLevel = 62,
        experience = 125.0
    ),
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
