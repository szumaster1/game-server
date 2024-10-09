package content.global.skill.crafting.items.armour.headdress

import org.rs.consts.Items

/**
 * Represents different types of feather headdresses.
 *
 * @property base       The base item id.
 * @property product    The product item id that is created from the base item.
 */
enum class FeatherHeaddress(val base: Int, val product: Int) {
    FEATHER_HEADDRESS_BLUE(Items.BLUE_FEATHER_10089, Items.FEATHER_HEADDRESS_12210),
    FEATHER_HEADDRESS_ORANGE(Items.ORANGE_FEATHER_10091, Items.FEATHER_HEADDRESS_12222),
    FEATHER_HEADDRESS_RED(Items.RED_FEATHER_10088, Items.FEATHER_HEADDRESS_12216),
    FEATHER_HEADDRESS_STRIPY(Items.STRIPY_FEATHER_10087, Items.FEATHER_HEADDRESS_12219),
    FEATHER_HEADDRESS_YELLOW(Items.YELLOW_FEATHER_10090, Items.FEATHER_HEADDRESS_12213);

    companion object {
        val values = enumValues<FeatherHeaddress>()
        val product = values.associateBy { it.base }
    }
}
