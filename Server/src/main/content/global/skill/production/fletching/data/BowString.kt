package content.global.skill.production.fletching.data

import core.api.consts.Items

/**
 * Enum class representing different types of bow strings.
 *
 * @param unfinished Represents the unfinished state of the bow string.
 * @param product Represents the product ID associated with the bow string.
 * @param level Represents the level required to use the bow string.
 * @param experience Represents the experience gained from using the bow string.
 * @param animation Represents the animation ID associated with the bow string.
 * @constructor
 *
 * @param indicator A byte value indicating a specific characteristic of the bow string.
 */
enum class BowString(indicator: Byte, val unfinished: Int, val product: Int, val level: Int, val experience: Double, val animation: Int) {
    /**
     * Short Bow
     *
     * @constructor Short Bow
     */
    SHORT_BOW(
        indicator = 1,
        unfinished = Items.SHORTBOW_U_50,
        product = Items.SHORTBOW_841,
        level = 5,
        experience = 5.0,
        animation = 6678
    ),

    /**
     * Long Bow
     *
     * @constructor Long Bow
     */
    LONG_BOW(
        indicator = 1,
        unfinished = Items.LONGBOW_U_48,
        product = Items.LONGBOW_839,
        level = 10,
        experience = 10.0,
        animation = 6684
    ),

    /**
     * Oak Shortbow
     *
     * @constructor Oak Shortbow
     */
    OAK_SHORTBOW(
        indicator = 1,
        unfinished = Items.OAK_SHORTBOW_U_54,
        product = Items.OAK_SHORTBOW_843,
        level = 20,
        experience = 16.5,
        animation = 6679
    ),

    /**
     * Oak Longbow
     *
     * @constructor Oak Longbow
     */
    OAK_LONGBOW(
        indicator = 1,
        unfinished = Items.OAK_LONGBOW_U_56,
        product = Items.OAK_LONGBOW_845,
        level = 25,
        experience = 25.0,
        animation = 6685
    ),

    /**
     * Comp Ogre Bow
     *
     * @constructor Comp Ogre Bow
     */
    COMP_OGRE_BOW(
        indicator = 1,
        unfinished = Items.UNSTRUNG_COMP_BOW_4825,
        product = Items.COMP_OGRE_BOW_4827,
        level = 30,
        experience = 40.0,
        animation = -1
    ),

    /**
     * Willow Shortbow
     *
     * @constructor Willow Shortbow
     */
    WILLOW_SHORTBOW(
        indicator = 1,
        unfinished = Items.WILLOW_SHORTBOW_U_60,
        product = Items.WILLOW_SHORTBOW_849,
        level = 35,
        experience = 33.3,
        animation = 6680
    ),

    /**
     * Willow Longbow
     *
     * @constructor Willow Longbow
     */
    WILLOW_LONGBOW(
        indicator = 1,
        unfinished = Items.WILLOW_LONGBOW_U_58,
        product = Items.WILLOW_LONGBOW_847,
        level = 40,
        experience = 41.5,
        animation = 6686
    ),

    /**
     * Maple Shortbow
     *
     * @constructor Maple Shortbow
     */
    MAPLE_SHORTBOW(
        indicator = 1,
        unfinished = Items.MAPLE_SHORTBOW_U_64,
        product = Items.MAPLE_SHORTBOW_853,
        level = 50,
        experience = 50.0,
        animation = 6681
    ),

    /**
     * Maple Longbow
     *
     * @constructor Maple Longbow
     */
    MAPLE_LONGBOW(
        indicator = 1,
        unfinished = Items.MAPLE_LONGBOW_U_62,
        product = Items.MAPLE_LONGBOW_851,
        level = 55,
        experience = 58.3,
        animation = 6687
    ),

    /**
     * Yew Shortbow
     *
     * @constructor Yew Shortbow
     */
    YEW_SHORTBOW(
        indicator = 1,
        unfinished = Items.YEW_SHORTBOW_U_68,
        product = Items.YEW_SHORTBOW_857,
        level = 65,
        experience = 67.5,
        animation = 6682
    ),

    /**
     * Yew Longbow
     *
     * @constructor Yew Longbow
     */
    YEW_LONGBOW(
        indicator = 1,
        unfinished = Items.YEW_LONGBOW_U_66,
        product = Items.YEW_LONGBOW_855,
        level = 70,
        experience = 75.0,
        animation = 6688
    ),

    /**
     * Magic Shortbow
     *
     * @constructor Magic Shortbow
     */
    MAGIC_SHORTBOW(
        indicator = 1,
        unfinished = Items.MAGIC_SHORTBOW_U_72,
        product = Items.MAGIC_SHORTBOW_861,
        level = 80,
        experience = 83.3,
        animation = 6683
    ),

    /**
     * Magic Longbow
     *
     * @constructor Magic Longbow
     */
    MAGIC_LONGBOW(
        indicator = 1,
        unfinished = Items.MAGIC_LONGBOW_U_70,
        product = Items.MAGIC_LONGBOW_859,
        level = 85,
        experience = 91.5,
        animation = 6689
    ),

    /**
     * Bronze Cbow
     *
     * @constructor Bronze Cbow
     */
    BRONZE_CBOW(
        indicator = 2,
        unfinished = Items.BRONZE_CBOW_U_9454,
        product = Items.BRONZE_CROSSBOW_9174,
        level = 9,
        experience = 6.0,
        animation = 6671
    ),

    /**
     * Blurite Cbow
     *
     * @constructor Blurite Cbow
     */
    BLURITE_CBOW(
        indicator = 2,
        unfinished = Items.BLURITE_CBOW_U_9456,
        product = Items.BLURITE_CROSSBOW_9176,
        level = 24,
        experience = 16.0,
        animation = 6672
    ),

    /**
     * Iron Cbow
     *
     * @constructor Iron Cbow
     */
    IRON_CBOW(
        indicator = 2,
        unfinished = Items.IRON_CBOW_U_9457,
        product = Items.IRON_CROSSBOW_9177,
        level = 39,
        experience = 22.0,
        animation = 6673
    ),

    /**
     * Steel Cbow
     *
     * @constructor Steel Cbow
     */
    STEEL_CBOW(
        indicator = 2,
        unfinished = Items.STEEL_CBOW_U_9459,
        product = Items.STEEL_CROSSBOW_9179,
        level = 46,
        experience = 27.0,
        animation = 6674
    ),

    /**
     * Mithiril Cbow
     *
     * @constructor Mithiril Cbow
     */
    MITHIRIL_CBOW(
        indicator = 2,
        unfinished = Items.MITHRIL_CBOW_U_9461,
        product = Items.MITH_CROSSBOW_9181,
        level = 54,
        experience = 32.0,
        animation = 6675
    ),

    /**
     * Adamant Cbow
     *
     * @constructor Adamant Cbow
     */
    ADAMANT_CBOW(
        indicator = 2,
        unfinished = Items.ADAMANT_CBOW_U_9463,
        product = Items.ADAMANT_CROSSBOW_9183,
        level = 61,
        experience = 41.0,
        animation = 6676
    ),

    /**
     * Runite Cbow
     *
     * @constructor Runite Cbow
     */
    RUNITE_CBOW(
        indicator = 2,
        unfinished = Items.RUNITE_CBOW_U_9465,
        product = Items.RUNE_CROSSBOW_9185,
        level = 69,
        experience = 50.0,
        animation = 6677
    );

    var string: Int = 0

    init {
        when (indicator.toInt() and 0xFF) {
            1 -> this.string = Items.BOW_STRING_1777
            2 -> this.string = Items.CROSSBOW_STRING_9438
            else -> {}
        }
    }

    companion object {
        val productMap = HashMap<Int, BowString>()

        init {
            for (product in BowString.values()) {
                productMap[product.unfinished] = product
            }
        }
    }

}