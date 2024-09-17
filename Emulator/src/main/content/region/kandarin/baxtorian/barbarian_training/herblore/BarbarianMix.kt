package content.region.kandarin.baxtorian.barbarian_training.herblore

import org.rs.consts.Items

/**
 * Represents the Barbarian mix.
 */
enum class BarbarianMix {
    /**
     * Attack Potion.
     */
    ATTACK_POTION(
        item = Items.ATTACK_POTION2_123,
        level = 4,
        product = Items.ATTACK_MIX2_11429,
        exp = 8.0,
        both = true
    ),

    /**
     * Anti Poision Potion.
     */
    ANTI_POISION_POTION(
        item = Items.ANTIPOISON2_177,
        level = 6,
        product = Items.ANTIPOISON_MIX2_11433,
        exp = 12.0,
        both = true
    ),

    /**
     * Relic.
     */
    RELIC(
        item = Items.RELICYMS_BALM2_4846,
        level = 9,
        product = Items.RELICYMS_MIX2_11437,
        exp = 14.0,
        both = true
    ),

    /**
     * Strength Potion.
     */
    STRENGTH_POTION(
        item = Items.STRENGTH_POTION2_117,
        level = 14,
        product = Items.STRENGTH_MIX2_11443,
        exp = 17.0,
        both = true
    ),

    /**
     * Restore Potion.
     */
    RESTORE_POTION(
        item = Items.RESTORE_POTION2_129,
        level = 24,
        product = Items.RESTORE_MIX2_11449,
        exp = 21.0,
        both = true
    ),

    /**
     * Energy Potion.
     */
    ENERGY_POTION(
        item = Items.ENERGY_POTION2_3012,
        level = 29,
        product = Items.ENERGY_MIX2_11453,
        exp = 23.0,
        both = false
    ),

    /**
     * Defence Potion.
     */
    DEFENCE_POTION(
        item = Items.DEFENCE_POTION2_135,
        level = 33,
        product = Items.DEFENCE_MIX2_11457,
        exp = 25.0,
        both = false
    ),

    /**
     * Agility Potion.
     */
    AGILITY_POTION(
        item = Items.AGILITY_POTION2_3036,
        level = 37,
        product = Items.AGILITY_MIX2_11461,
        exp = 27.0,
        both = false
    ),

    /**
     * Combat Potion.
     */
    COMBAT_POTION(
        item = Items.COMBAT_POTION2_9743,
        level = 40,
        product = Items.COMBAT_MIX2_11445,
        exp = 28.0,
        both = false
    ),

    /**
     * Prayer Potion.
     */
    PRAYER_POTION(
        item = Items.PRAYER_POTION2_141,
        level = 42,
        product = Items.PRAYER_MIX2_11465,
        exp = 29.0,
        both = false
    ),

    /**
     * Super Attack Potion.
     */
    SUPER_ATTACK_POTION(
        item = Items.SUPER_ATTACK2_147,
        level = 47,
        product = Items.SUPERATTACK_MIX2_11469,
        exp = 33.0,
        both = false
    ),

    /**
     * Super Antipoision Potion.
     */
    SUPER_ANTIPOISION_POTION(
        item = Items.SUPER_ANTIPOISON2_183,
        level = 51,
        product = Items.ANTI_P_SUPERMIX2_11473,
        exp = 35.0,
        both = false
    ),

    /**
     * Fishing Potion.
     */
    FISHING_POTION(
        item = Items.FISHING_POTION2_153,
        level = 53,
        product = Items.FISHING_MIX2_11477,
        exp = 38.0,
        both = false
    ),

    /**
     * Super Energy Potion.
     */
    SUPER_ENERGY_POTION(
        item = Items.SUPER_ENERGY2_3020,
        level = 56,
        product = Items.SUP_ENERGY_MIX2_11481,
        exp = 42.0,
        both = false
    ),

    /**
     * Hunter Potion.
     */
    HUNTER_POTION(
        item = Items.HUNTER_POTION2_10002,
        level = 58,
        product = Items.HUNTING_MIX2_11517,
        exp = 40.0,
        both = false
    ),

    /**
     * Super Strength Potion.
     */
    SUPER_STRENGTH_POTION(
        item = Items.SUPER_STRENGTH2_159,
        level = 59,
        product = Items.SUP_STR_MIX2_11485,
        exp = 42.0,
        both = false
    ),

    /**
     * Super Restore.
     */
    SUPER_RESTORE(
        item = Items.SUPER_RESTORE2_3028,
        level = 67,
        product = Items.SUP_RESTORE_MIX2_11493,
        exp = 48.0,
        both = false
    ),

    /**
     * Super Defence Potion.
     */
    SUPER_DEFENCE_POTION(
        item = Items.SUPER_DEFENCE2_165,
        level = 71,
        product = Items.SUP_DEF_MIX2_11497,
        exp = 50.0,
        both = false
    ),

    /**
     * Antidote Plus.
     */
    ANTIDOTE_PLUS(
        item = Items.ANTIPOISON_PLUS2_5947,
        level = 74,
        product = Items.ANTIDOTE_PLUS_MIX2_11501,
        exp = 52.0,
        both = false
    ),

    /**
     * Antifire Potion.
     */
    ANTIFIRE_POTION(
        item = Items.ANTIFIRE_POTION2_2456,
        level = 75,
        product = Items.ANTIFIRE_MIX2_11505,
        exp = 53.0,
        both = false
    ),

    /**
     * Ranging Potion.
     */
    RANGING_POTION(
        item = Items.RANGING_POTION2_171,
        level = 80,
        product = Items.RANGING_MIX2_11509,
        exp = 54.0,
        both = false
    ),

    /**
     * Magic Potion.
     */
    MAGIC_POTION(
        item = Items.MAGIC_POTION2_3044,
        level = 83,
        product = Items.MAGIC_MIX2_11513,
        exp = 57.0,
        both = false
    ),

    /**
     * Zamorak Brew.
     */
    ZAMORAK_BREW(
        item = Items.ZAMORAK_BREW2_191,
        level = 85,
        product = Items.ZAMORAK_MIX2_11521,
        exp = 58.0,
        both = false
    );

    constructor(item: Int, level: Int, product: Int, exp: Double, both: Boolean) {
        this.item = item
        this.level = level
        this.product = product
        this.exp = exp
        this.both = both
    }

    private val item: Int
    private val product: Int
    private val level: Int
    private val exp: Double
    private val both: Boolean

    /**
     * Get the item.
     */
    fun getItem(): Int {
        return item
    }

    /**
     * Get the product.
     */
    fun getProduct(): Int {
        return product
    }

    /**
     * Get the level.
     */
    fun getLevel(): Int {
        return level
    }

    /**
     * Get the exp
     */
    fun getExp(): Double {
        return exp
    }

    /**
     * Gets the both ingredients.
     */
    fun isBoth(): Boolean {
        return both
    }


    companion object {

        /**
         * Get the mix for an id.
         *
         * @param id The id of potion.
         * @return The potion for the id.
         */
        fun forId(id: Int): BarbarianMix? {
            for (pot in BarbarianMix.values()) {
                if (pot.getItem() == id) {
                    return pot
                }
            }
            return null
        }
    }
}
