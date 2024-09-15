package content.global.skill.production.herblore.data.potion

import content.global.skill.production.herblore.data.GrindingItem
import cfg.consts.Items
import core.game.node.item.Item

/**
 * Represents a finished potion.
 *
 * @param unfinished    the unfinished potion base.
 * @param ingredient    the ingredient.
 * @param level         the level.
 * @param experience    the experience.
 * @param potion        the potion.
 * @return the [FinishedPotion].
 */
enum class FinishedPotion(val unfinished: UnfinishedPotion, val ingredient: Item, val level: Int, val experience: Double, val potion: Item) {
    /**
     * The attack.
     */
    ATTACK_POTION(
        unfinished = UnfinishedPotion.GUAM,
        ingredient = Item(Items.EYE_OF_NEWT_221),
        level = 3,
        experience = 25.0,
        potion = Item(Items.ATTACK_POTION3_121)
    ),

    /**
     * The anti-poison.
     */
    ANTIPOISON_POTION(
        unfinished = UnfinishedPotion.MARRENTILL,
        ingredient = Item(Items.UNICORN_HORN_DUST_235),
        level = 5,
        experience = 37.5,
        potion = Item(Items.ANTIPOISON3_175)
    ),

    /**
     * The relicyms balm.
     */
    RELICYM_BALM(
        unfinished = UnfinishedPotion.ROGUE_PURSE,
        ingredient = Item(Items.CLEAN_SNAKE_WEED_1526),
        level = 8,
        experience = 0.0,
        potion = Item(Items.RELICYMS_BALM3_4844)
    ),

    /**
     * The strength potion.
     */
    STRENGTH_POTION(
        unfinished = UnfinishedPotion.TARROMIN,
        ingredient = Item(Items.LIMPWURT_ROOT_225),
        level = 12,
        experience = 50.0,
        potion = Item(Items.STRENGTH_POTION3_115)
    ),

    /**
     * The restore potion.
     */
    RESTORE_POTION(
        unfinished = UnfinishedPotion.HARRALANDER,
        ingredient = Item(Items.RED_SPIDERS_EGGS_223),
        level = 22,
        experience = 62.5,
        potion = Item(Items.RESTORE_POTION3_127)
    ),

    /**
     * The energy potion.
     */
    ENERGY_POTION(
        unfinished = UnfinishedPotion.HARRALANDER,
        ingredient = Item(Items.CHOCOLATE_DUST_1975),
        level = 26,
        experience = 67.5,
        potion = Item(Items.ENERGY_POTION3_3010)
    ),

    /**
     * The defence potion.
     */
    DEFENCE_POTION(
        unfinished = UnfinishedPotion.RANARR,
        ingredient = Item(Items.WHITE_BERRIES_239),
        level = 30,
        experience = 45.0,
        potion = Item(Items.DEFENCE_POTION3_133)
    ),

    /**
     * The agility potion.
     */
    AGILITY_POTION(
        unfinished = UnfinishedPotion.TOADFLAX,
        ingredient = Item(Items.TOADS_LEGS_2152),
        level = 34,
        experience = 80.0,
        potion = Item(Items.AGILITY_POTION3_3034)
    ),

    /**
     * The combat potion.
     */
    COMBAT_POTION(
        unfinished = UnfinishedPotion.HARRALANDER,
        ingredient = Item(Items.GOAT_HORN_DUST_9736),
        level = 36,
        experience = 84.0,
        potion = Item(Items.COMBAT_POTION3_9741)
    ),

    /**
     * The prayer potion.
     */
    PRAYER_POTION(
        unfinished = UnfinishedPotion.RANARR,
        ingredient = Item(Items.SNAPE_GRASS_231),
        level = 38,
        experience = 87.5,
        potion = Item(Items.PRAYER_POTION3_139)
    ),

    /**
     * The summoning potion.
     */
    SUMMONING_POTION(
        unfinished = UnfinishedPotion.SPIRIT_WEED,
        ingredient = Item(Items.COCKATRICE_EGG_12109),
        level = 40,
        experience = 92.0,
        potion = Item(Items.SUMMONING_POTION3_12142)
    ),

    /**
     * The super attack.
     */
    SUPER_ATTACK(
        unfinished = UnfinishedPotion.IRIT,
        ingredient = Item(Items.EYE_OF_NEWT_221),
        level = 45,
        experience = 100.0,
        potion = Item(Items.SUPER_ATTACK3_145)
    ),

    /**
     * The super anti-poison.
     */
    SUPER_ANTIPOISON(
        unfinished = UnfinishedPotion.IRIT,
        ingredient = Item(Items.UNICORN_HORN_DUST_235),
        level = 48,
        experience = 106.3,
        potion = Item(Items.SUPER_ANTIPOISON3_181)
    ),

    /**
     * The fishing potion.
     */
    FISHING_POTION(
        unfinished = UnfinishedPotion.AVANTOE,
        ingredient = Item(Items.SNAPE_GRASS_231),
        level = 50,
        experience = 112.5,
        potion = Item(Items.FISHING_POTION3_151)
    ),

    /**
     * The super energy.
     */
    SUPER_ENERGY(
        unfinished = UnfinishedPotion.AVANTOE,
        ingredient = Item(Items.MORT_MYRE_FUNGUS_2970),
        level = 52,
        experience = 117.5,
        potion = Item(Items.SUPER_ENERGY3_3018)
    ),

    /**
     * The hunter potion.
     */
    HUNTING_POTION(
        unfinished = UnfinishedPotion.AVANTOE,
        ingredient = Item(Items.KEBBIT_TEETH_DUST_10111),
        level = 53,
        experience = 120.0,
        potion = Item(Items.HUNTER_POTION3_10000)
    ),

    /**
     * The super strength.
     */
    SUPER_STRENGTH(
        unfinished = UnfinishedPotion.KWUARM,
        ingredient = Item(Items.LIMPWURT_ROOT_225),
        level = 55,
        experience = 125.0,
        potion = Item(Items.SUPER_STRENGTH3_157)
    ),

    /**
     * The weapon poison.
     */
    WEAPON_POISON(
        unfinished = UnfinishedPotion.KWUARM,
        ingredient = Item(Items.DRAGON_SCALE_DUST_241),
        level = 60,
        experience = 137.5,
        potion = Item(Items.WEAPON_POISON_187)
    ),

    /**
     * The super Restore.
     */
    SUPER_RESTORE(
        unfinished = UnfinishedPotion.SNAPDRAGON,
        ingredient = Item(Items.RED_SPIDERS_EGGS_223),
        level = 63,
        experience = 142.5,
        potion = Item(Items.SUPER_RESTORE3_3026)
    ),

    /**
     * The super defence.
     */
    SUPER_DEFENCE(
        unfinished = UnfinishedPotion.CADANTINE,
        ingredient = Item(Items.WHITE_BERRIES_239),
        level = 66,
        experience = 160.0,
        potion = Item(Items.SUPER_DEFENCE3_163)
    ),

    /**
     * The anti-fire.
     */
    ANTIFIRE(
        unfinished = UnfinishedPotion.LANTADYME,
        ingredient = Item(Items.DRAGON_SCALE_DUST_241),
        level = 69,
        experience = 157.5,
        potion = Item(Items.ANTIFIRE_POTION3_2454)
    ),

    /**
     * The super ranging potion.
     */
    SUPER_RANGING_POTION(
        unfinished = UnfinishedPotion.DWARF_WEED,
        ingredient = Item(Items.WINE_OF_ZAMORAK_245),
        level = 72,
        experience = 162.5,
        potion = Item(Items.RANGING_POTION3_169)
    ),

    /**
     * The super magic potion.
     */
    SUPER_MAGIC(
        unfinished = UnfinishedPotion.LANTADYME,
        ingredient = Item(Items.POTATO_CACTUS_3138),
        level = 76,
        experience = 172.5,
        potion = Item(Items.MAGIC_POTION3_3042)
    ),

    /**
     * The zamorak brew.
     */
    ZAMORAK_BREW(
        unfinished = UnfinishedPotion.TORSTOL,
        ingredient = Item(Items.JANGERBERRIES_247),
        level = 78,
        experience = 175.0,
        potion = Item(Items.ZAMORAK_BREW3_189)
    ),

    /**
     * The saradomin brew.
     */
    SARADOMIN_BREW(
        unfinished = UnfinishedPotion.TOADFLAX,
        ingredient = GrindingItem.BIRDS_NEST.product,
        level = 81,
        experience = 180.0,
        potion = Item(Items.SARADOMIN_BREW3_6687)
    ),

    /**
     * The strong weapon poison.
     */
    STRONG_WEAPON_POISON(
        unfinished = UnfinishedPotion.STRONG_WEAPON_POISON,
        ingredient = Item(Items.RED_SPIDERS_EGGS_223),
        level = 73,
        experience = 165.0,
        potion = Item(Items.WEAPON_POISON_PLUS_5937)
    ),

    /**
     * The super strong weapon poison.
     */
    SUPER_STRONG_WEAPON_POISON(
        unfinished = UnfinishedPotion.SUPER_STRONG_WEAPON_POISON,
        ingredient = Item(Items.POISON_IVY_BERRIES_6018),
        level = 82,
        experience = 190.0,
        potion = Item(Items.WEAPON_POISON_PLUS_PLUS_5940)
    ),

    /**
     * The strong anti-poison.
     */
    STRONG_ANTI_POISON(
        unfinished = UnfinishedPotion.STRONG_ANTIPOISON,
        ingredient = Item(Items.YEW_ROOTS_6049),
        level = 68,
        experience = 155.0,
        potion = Item(Items.ANTIPOISON_PLUS3_5945)
    ),

    /**
     * The super strong anti-poison.
     */
    SUPER_STRONG_ANTI_POISON(
        unfinished = UnfinishedPotion.SUPER_STRONG_ANTIPOISON,
        ingredient = Item(Items.MAGIC_ROOTS_6051),
        level = 79,
        experience = 177.5,
        potion = Item(Items.ANTIPOISON_PLUS_PLUS3_5954)
    ),

    /**
     * The blamish oil.
     */
    BLAMISH_OIL(
        unfinished = UnfinishedPotion.HARRALANDER,
        ingredient = Item(Items.BLAMISH_SNAIL_SLIME_1581),
        level = 25,
        experience = 80.0,
        potion = Item(Items.BLAMISH_OIL_1582)
    );

    companion object {
        /**
         * Gets the finished potion by the unfinished potion and the ingredient.
         *
         * @param unf           the unf-potion.
         * @param ingredient    the ingredient.
         * @return the finished potion.
         */
        fun getPotion(unf: Item, ingredient: Item): FinishedPotion? {
            for (pot in values()) {
                if (pot.unfinished.potion.id == unf.id && pot.ingredient.id == ingredient.id) {
                    return pot
                }
            }
            return null
        }
    }
}
