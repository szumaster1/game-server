package content.global.skill.production.crafting.data

import core.api.consts.Items
/**
 * Spinning data
 *
 * @param button Represents the button identifier for the spinning action.
 * @param need Indicates the amount of resources required to perform the spin.
 * @param product Represents the product obtained from the spinning action.
 * @param level Indicates the level of the spinning action.
 * @param exp Represents the experience points gained from the spinning action.
 * @constructor Spinning data
 */
enum class SpinningData(
    val button: Int,  // The button identifier for the spinning action
    val need: Int,    // The amount of resources required to perform the spin
    val product: Int, // The product obtained from the spinning action
    val level: Int,   // The level of the spinning action
    val exp: Double   // The experience points gained from the spinning action
) {
    /**
     * Wool
     *
     * @constructor Wool
     */
    WOOL(
        button = 19,
        need = Items.WOOL_1737,
        product = Items.BALL_OF_WOOL_1759,
        level = 1,
        exp = 2.5),

    /**
     * Flax
     *
     * @constructor Flax
     */
    FLAX(
        button = 17,
        need = Items.FLAX_1779,
        product = Items.BOW_STRING_1777,
        level = 10,
        exp = 15.0
    ),

    /**
     * Root
     *
     * @constructor Root
     */
    ROOT(
        button = 23,
        need = Items.MAGIC_ROOTS_6051,
        product = Items.MAGIC_STRING_6038,
        level = 19,
        exp = 30.0
    ),

    /**
     * Root1
     *
     * @constructor Root1
     */
    ROOT1(
        button = 23,
        need = Items.OAK_ROOTS_6043,
        product = Items.MAGIC_STRING_6038,
        level = 19,
        exp = 30.0
    ),

    /**
     * Root2
     *
     * @constructor Root2
     */
    ROOT2(
        button = 23,
        need = Items.WILLOW_ROOTS_6045,
        product = Items.MAGIC_STRING_6038,
        level = 19,
        exp = 30.0
    ),

    /**
     * Root3
     *
     * @constructor Root3
     */
    ROOT3(
        button = 23,
        need = Items.MAPLE_ROOTS_6047,
        product = Items.MAGIC_STRING_6038,
        level = 19,
        exp = 30.0
    ),

    /**
     * Root4
     *
     * @constructor Root4
     */
    ROOT4(
        button = 23,
        need = Items.YEW_ROOTS_6049,
        product = Items.MAGIC_STRING_6038,
        level = 19,
        exp = 30.0
    ),

    /**
     * Root5
     *
     * @constructor Root5
     */
    ROOT5(
        button = 23,
        need = Items.SPIRIT_ROOTS_6053,
        product = Items.MAGIC_STRING_6038,
        level = 19,
        exp = 30.0
    ),

    /**
     * Sinew
     *
     * @constructor Sinew
     */
    SINEW(
        button = 27,
        need = Items.SINEW_9436,
        product = Items.CROSSBOW_STRING_9438,
        level = 10,
        exp = 15.0
    ),

    /**
     * Tree Roots
     *
     * @constructor Tree Roots
     */
    TREE_ROOTS(
        button = 31,
        need = Items.OAK_ROOTS_6043,
        product = Items.CROSSBOW_STRING_9438,
        level = 10,
        exp = 15.0
    ),

    /**
     * Yak
     *
     * @constructor Yak
     */
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
