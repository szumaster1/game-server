package content.global.skill.production.crafting.data

import core.api.consts.Items

enum class SpinningData(
    val button: Int,
    val need: Int,
    val product: Int,
    val level: Int,
    val exp: Double
) {
    WOOL(
        button = 19,
        need = Items.WOOL_1737,
        product = Items.BALL_OF_WOOL_1759,
        level = 1,
        exp = 2.5),
    FLAX(
        button = 17,
        need = Items.FLAX_1779,
        product = Items.BOW_STRING_1777,
        level = 10,
        exp = 15.0
    ),
    ROOT(
        button = 23,
        need = Items.MAGIC_ROOTS_6051,
        product = Items.MAGIC_STRING_6038,
        level = 19,
        exp = 30.0
    ),
    ROOT1(
        button = 23,
        need = Items.OAK_ROOTS_6043,
        product = Items.MAGIC_STRING_6038,
        level = 19,
        exp = 30.0
    ),
    ROOT2(
        button = 23,
        need = Items.WILLOW_ROOTS_6045,
        product = Items.MAGIC_STRING_6038,
        level = 19,
        exp = 30.0
    ),
    ROOT3(
        button = 23,
        need = Items.MAPLE_ROOTS_6047,
        product = Items.MAGIC_STRING_6038,
        level = 19,
        exp = 30.0
    ),
    ROOT4(
        button = 23,
        need = Items.YEW_ROOTS_6049,
        product = Items.MAGIC_STRING_6038,
        level = 19,
        exp = 30.0
    ),
    ROOT5(
        button = 23,
        need = Items.SPIRIT_ROOTS_6053,
        product = Items.MAGIC_STRING_6038,
        level = 19,
        exp = 30.0
    ),
    SINEW(
        button = 27,
        need = Items.SINEW_9436,
        product = Items.CROSSBOW_STRING_9438,
        level = 10,
        exp = 15.0
    ),
    TREE_ROOTS(
        button = 31,
        need = Items.OAK_ROOTS_6043,
        product = Items.CROSSBOW_STRING_9438,
        level = 10,
        exp = 15.0
    ),
    YAK(
        button = 35,
        need = Items.HAIR_10814,
        product = Items.ROPE_954,
        level = 30,
        exp = 25.0
    );


    companion object {
        @JvmStatic
        fun forId(id: Int): SpinningData? {
            for (spin in values()) {
                if (spin.button == id) {
                    return spin
                }
            }
            return null
        }
    }
}
