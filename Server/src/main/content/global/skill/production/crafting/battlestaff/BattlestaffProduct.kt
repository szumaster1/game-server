package content.global.skill.production.crafting.battlestaff

import core.api.consts.Items

enum class BattlestaffProduct(val requiredOrbItemId: Int, val producedItemId: Int, val amountProduced: Int, val minimumLevel: Int, val experience: Double, ) {

    WATER_BATTLESTAFF(
        Items.WATER_ORB_571,
        Items.WATER_BATTLESTAFF_1395,
        1, 54, 100.0),

    EARTH_BATTLESTAFF(
        Items.EARTH_ORB_575,
        Items.EARTH_BATTLESTAFF_1399,
        1, 58, 112.5),

    FIRE_BATTLESTAFF(
        Items.FIRE_ORB_569,
        Items.FIRE_BATTLESTAFF_1393,
        1, 62, 125.0),

    AIR_BATTLESTAFF(
        Items.AIR_ORB_573,
        Items.AIR_BATTLESTAFF_1397,
        1, 66, 137.5);


    companion object {
        val productMap = HashMap<Int, BattlestaffProduct>()

        init {
            for (product in values()) {
                productMap[product.requiredOrbItemId] = product
            }
        }
    }
}