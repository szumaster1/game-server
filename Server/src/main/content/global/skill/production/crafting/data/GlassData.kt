package content.global.skill.production.crafting.data

import core.api.consts.Items

/**
 * Glass data
 *
 * @property buttonId Unique identifier for the button associated with the glass.
 * @property productId Unique identifier for the product represented by the glass.
 * @property amount Current quantity of the product in the glass.
 * @property requiredLevel Minimum level required to use the glass.
 * @property experience Experience points gained from using the glass.
 * @constructor Glass data
 */
enum class GlassData(
    val buttonId: Int,          // Unique identifier for the button associated with the glass.
    val productId: Int,         // Unique identifier for the product represented by the glass.
    var amount: Int,            // Current quantity of the product in the glass.
    val requiredLevel: Int,     // Minimum level required to use the glass.
    val experience: Double       // Experience points gained from using the glass.
) {
    /**
     * Empty Vial.
     */
    EMPTY_VIAL(
        buttonId = 38,
        productId = Items.VIAL_229,
        amount = 1,
        requiredLevel = 33,
        experience = 35.0
    ),

    /**
     * Unpowered Orb.
     */
    UNPOWERED_ORB(
        buttonId = 39,
        productId = Items.UNPOWERED_ORB_567,
        amount = 1,
        requiredLevel = 46,
        experience = 52.5
    ),

    /**
     * Beer Glass.
     */
    BEER_GLASS(
        buttonId = 40,
        productId = Items.BEER_GLASS_1919,
        amount = 1,
        requiredLevel = 1,
        experience = 17.5
    ),

    /**
     * Empty Candle Lantern.
     */
    EMPTY_CANDLE_LANTERN(
        buttonId = 41,
        productId = Items.CANDLE_LANTERN_4527,
        amount = 1,
        requiredLevel = 4,
        experience = 19.0
    ),

    /**
     * Empty Oil Lamp.
     */
    EMPTY_OIL_LAMP(
        buttonId = 42,
        productId = Items.OIL_LAMP_4525,
        amount = 1,
        requiredLevel = 12,
        experience = 25.0
    ),

    /**
     * Lantern Lens.
     */
    LANTERN_LENS(
        buttonId = 43,
        productId = Items.LANTERN_LENS_4542,
        amount = 1,
        requiredLevel = 49,
        experience = 55.0
    ),

    /**
     * Fishbowl.
     */
    FISHBOWL(
        buttonId = 44,
        productId = Items.FISHBOWL_6667,
        amount = 1,
        requiredLevel = 42,
        experience = 42.5
    ),

    /**
     * Empty Light Orb.
     */
    EMPTY_LIGHT_ORB(
        buttonId = 45,
        productId = Items.LIGHT_ORB_10973,
        amount = 1,
        requiredLevel = 87,
        experience = 70.0
    );

    companion object {
        private val BUTTON_MAP = HashMap<Int, GlassData>()
        private val PRODUCT_MAP = HashMap<Int, GlassData>()

        init {
            for (product in values()) {
                BUTTON_MAP[product.buttonId] = product
                PRODUCT_MAP[product.productId] = product
            }
        }

        fun forButtonID(buttonId: Int): GlassData? = BUTTON_MAP[buttonId]
    }
}
