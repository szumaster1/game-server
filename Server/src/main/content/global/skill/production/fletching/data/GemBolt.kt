package content.global.skill.production.fletching.data

import core.api.consts.Items

/**
 * Gem bolt
 *
 * @property base Represents the base value of the gem bolt.
 * @property gem Represents the type of gem used in the bolt.
 * @property tip Represents the type of tip used in the bolt.
 * @property product Represents the resulting product from combining the base, gem, and tip.
 * @property level Represents the required level to create the gem bolt.
 * @property experience Represents the experience gained from creating the gem bolt.
 * @constructor Gem bolt Represents a new instance of the GemBolt enum class with specified properties.
 */
enum class GemBolt(var base: Int, var gem: Int, var tip: Int, var product: Int, var level: Int, var experience: Double) {
    /**
     * Opal
     *
     * @constructor Opal
     */
    OPAL(
        base = Items.BRONZE_BOLTS_877,
        gem = Items.OPAL_1609,
        tip = Items.OPAL_BOLT_TIPS_45,
        product = Items.OPAL_BOLTS_879,
        level = 11,
        experience = 1.6
    ),

    /**
     * Pearl
     *
     * @constructor Pearl
     */
    PEARL(
        base = Items.IRON_BOLTS_9140,
        gem = Items.OYSTER_PEARL_411,
        tip = Items.PEARL_BOLT_TIPS_46,
        product = Items.PEARL_BOLTS_880,
        level = 41,
        experience = 3.2
    ),

    /**
     * Pearls
     *
     * @constructor Pearls
     */
    PEARLS(
        base = Items.IRON_BOLTS_9140,
        gem = Items.OYSTER_PEARLS_413,
        tip = Items.PEARL_BOLT_TIPS_46,
        product = Items.PEARL_BOLTS_880,
        level = 41,
        experience = 3.2
    ),

    /**
     * Jade
     *
     * @constructor Jade
     */
    JADE(
        base = Items.BLURITE_BOLTS_9139,
        gem = Items.JADE_1611,
        tip = Items.JADE_BOLT_TIPS_9187,
        product = Items.JADE_BOLTS_9335,
        level = 26,
        experience = 2.4
    ),

    /**
     * Red Topaz
     *
     * @constructor Red Topaz
     */
    RED_TOPAZ(
        base = Items.STEEL_BOLTS_9141,
        gem = Items.RED_TOPAZ_1613,
        tip = Items.TOPAZ_BOLT_TIPS_9188,
        product = Items.TOPAZ_BOLTS_9336,
        level = 48,
        experience = 3.9
    ),

    /**
     * Sapphire
     *
     * @constructor Sapphire
     */
    SAPPHIRE(
        base = Items.MITHRIL_BOLTS_9142,
        gem = Items.SAPPHIRE_1607,
        tip = Items.SAPPHIRE_BOLT_TIPS_9189,
        product = Items.SAPPHIRE_BOLTS_9337,
        level = 56,
        experience = 4.7
    ),

    /**
     * Emerald
     *
     * @constructor Emerald
     */
    EMERALD(
        base = Items.MITHRIL_BOLTS_9142,
        gem = Items.EMERALD_1605,
        tip = Items.EMERALD_BOLT_TIPS_9190,
        product = Items.EMERALD_BOLTS_9338,
        level = 58,
        experience = 5.5
    ),

    /**
     * Ruby
     *
     * @constructor Ruby
     */
    RUBY(
        base = Items.ADAMANT_BOLTS_9143,
        gem = Items.RUBY_1603,
        tip = Items.RUBY_BOLT_TIPS_9191,
        product = Items.RUBY_BOLTS_9339,
        level = 63,
        experience = 6.3
    ),

    /**
     * Diamond
     *
     * @constructor Diamond
     */
    DIAMOND(
        base = Items.ADAMANT_BOLTS_9143,
        gem = Items.DIAMOND_1601,
        tip = Items.DIAMOND_BOLT_TIPS_9192,
        product = Items.DIAMOND_BOLTS_9340,
        level = 65,
        experience = 7.0
    ),

    /**
     * Dragonstone
     *
     * @constructor Dragonstone
     */
    DRAGONSTONE(
        base = Items.RUNE_BOLTS_9144,
        gem = Items.DRAGONSTONE_1615,
        tip = Items.DRAGON_BOLT_TIPS_9193,
        product = Items.DRAGON_BOLTS_9341,
        level = 71,
        experience = 8.2
    ),

    /**
     * Onyx
     *
     * @constructor Onyx
     */
    ONYX(
        base = Items.RUNE_BOLTS_9144,
        gem = Items.ONYX_6573,
        tip = Items.ONYX_BOLT_TIPS_9194,
        product = Items.ONYX_BOLTS_9342,
        level = 73,
        experience = 9.4
    );

    companion object {
        val productMap = HashMap<Int, GemBolt>()

        init {
            for (gem in GemBolt.values()) {
                productMap[gem.gem] = gem
            }
            for (tip in GemBolt.values()) {
                productMap[tip.gem] = tip
            }
        }
    }
}
