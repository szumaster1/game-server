package content.global.skill.crafting

import org.rs.consts.Items

/**
 * Represents the glass items.
 */
enum class Glass(
    val buttonId: Int,
    val productId: Int,
    val amount: Int = 1,
    val requiredLevel: Int,
    val experience: Double
) {
    EMPTY_VIAL(
        buttonId = 38,
        productId = Items.VIAL_229,
        requiredLevel = 33,
        experience = 35.0
    ),
    UNPOWERED_ORB(
        buttonId = 39,
        productId = Items.UNPOWERED_ORB_567,
        requiredLevel = 46,
        experience = 52.5
    ),
    BEER_GLASS(
        buttonId = 40,
        productId = Items.BEER_GLASS_1919,
        requiredLevel = 1,
        experience = 17.5
    ),
    EMPTY_CANDLE_LANTERN(
        buttonId = 41,
        productId = Items.CANDLE_LANTERN_4527,
        requiredLevel = 4,
        experience = 19.0
    ),
    EMPTY_OIL_LAMP(
        buttonId = 42,
        productId = Items.OIL_LAMP_4525,
        requiredLevel = 12,
        experience = 25.0
    ),
    LANTERN_LENS(
        buttonId = 43,
        productId = Items.LANTERN_LENS_4542,
        requiredLevel = 49,
        experience = 55.0
    ),
    FISHBOWL(
        buttonId = 44,
        productId = Items.FISHBOWL_6667,
        requiredLevel = 42,
        experience = 42.5
    ),
    EMPTY_LIGHT_ORB(
        buttonId = 45,
        productId = Items.LIGHT_ORB_10973,
        requiredLevel = 87,
        experience = 70.0
    );

    companion object {
        private val BUTTON_MAP = mutableMapOf<Int, Glass>()
        private val PRODUCT_MAP = mutableMapOf<Int, Glass>()

        init {
            values().forEach { product ->
                BUTTON_MAP[product.buttonId] = product
                PRODUCT_MAP[product.productId] = product
            }
        }

        @JvmStatic
        fun forButtonID(buttonId: Int): Glass? = BUTTON_MAP[buttonId]
    }
}