package content.global.skill.production.herblore.data.potion

import content.global.skill.production.herblore.data.GrindingItem
import cfg.consts.Items
import core.game.node.item.Item

/**
 * Finished potion
 *
 * @param unfinished Represents the unfinished potion that is being completed.
 * @param ingredient The item used to finish the potion.
 * @param level The required level to create the finished potion.
 * @param experience The experience gained from creating the finished potion.
 * @param potion The final item that represents the finished potion.
 * @constructor Finished potion
 */
enum class FinishedPotion(
    val unfinished: UnfinishedPotion, // Holds the reference to the unfinished potion
    val ingredient: Item, // Represents the ingredient used to complete the potion
    val level: Int, // Indicates the level required to craft the potion
    val experience: Double, // The experience points awarded for crafting the potion
    val potion: Item // The resulting finished potion item
) {
    /**
     * Attack Potion
     *
     * @constructor Attack Potion
     */
    ATTACK_POTION(
        unfinished = UnfinishedPotion.GUAM,
        ingredient = Item(Items.EYE_OF_NEWT_221),
        level = 3,
        experience = 25.0,
        potion = Item(Items.ATTACK_POTION3_121)
    ),

    /**
     * Antipoison Potion
     *
     * @constructor Antipoison Potion
     */
    ANTIPOISON_POTION(
        unfinished = UnfinishedPotion.MARRENTILL,
        ingredient = Item(Items.UNICORN_HORN_DUST_235),
        level = 5,
        experience = 37.5,
        potion = Item(Items.ANTIPOISON3_175)
    ),

    /**
     * Relicym Balm
     *
     * @constructor Relicym Balm
     */
    RELICYM_BALM(
        unfinished = UnfinishedPotion.ROGUE_PURSE,
        ingredient = Item(Items.CLEAN_SNAKE_WEED_1526),
        level = 8,
        experience = 0.0,
        potion = Item(Items.RELICYMS_BALM3_4844)
    ),

    /**
     * Strength Potion
     *
     * @constructor Strength Potion
     */
    STRENGTH_POTION(
        unfinished = UnfinishedPotion.TARROMIN,
        ingredient = Item(Items.LIMPWURT_ROOT_225),
        level = 12,
        experience = 50.0,
        potion = Item(Items.STRENGTH_POTION3_115)
    ),

    /**
     * Restore Potion
     *
     * @constructor Restore Potion
     */
    RESTORE_POTION(
        unfinished = UnfinishedPotion.HARRALANDER,
        ingredient = Item(Items.RED_SPIDERS_EGGS_223),
        level = 22,
        experience = 62.5,
        potion = Item(Items.RESTORE_POTION3_127)
    ),

    /**
     * Energy Potion
     *
     * @constructor Energy Potion
     */
    ENERGY_POTION(
        unfinished = UnfinishedPotion.HARRALANDER,
        ingredient = Item(Items.CHOCOLATE_DUST_1975),
        level = 26,
        experience = 67.5,
        potion = Item(Items.ENERGY_POTION3_3010)
    ),

    /**
     * Defence Potion
     *
     * @constructor Defence Potion
     */
    DEFENCE_POTION(
        unfinished = UnfinishedPotion.RANARR,
        ingredient = Item(Items.WHITE_BERRIES_239),
        level = 30,
        experience = 45.0,
        potion = Item(Items.DEFENCE_POTION3_133)
    ),

    /**
     * Agility Potion
     *
     * @constructor Agility Potion
     */
    AGILITY_POTION(
        unfinished = UnfinishedPotion.TOADFLAX,
        ingredient = Item(Items.TOADS_LEGS_2152),
        level = 34,
        experience = 80.0,
        potion = Item(Items.AGILITY_POTION3_3034)
    ),

    /**
     * Combat Potion
     *
     * @constructor Combat Potion
     */
    COMBAT_POTION(
        unfinished = UnfinishedPotion.HARRALANDER,
        ingredient = Item(Items.GOAT_HORN_DUST_9736),
        level = 36,
        experience = 84.0,
        potion = Item(Items.COMBAT_POTION3_9741)
    ),

    /**
     * Prayer Potion
     *
     * @constructor Prayer Potion
     */
    PRAYER_POTION(
        unfinished = UnfinishedPotion.RANARR,
        ingredient = Item(Items.SNAPE_GRASS_231),
        level = 38,
        experience = 87.5,
        potion = Item(Items.PRAYER_POTION3_139)
    ),

    /**
     * Summoning Potion
     *
     * @constructor Summoning Potion
     */
    SUMMONING_POTION(
        unfinished = UnfinishedPotion.SPIRIT_WEED,
        ingredient = Item(Items.COCKATRICE_EGG_12109),
        level = 40,
        experience = 92.0,
        potion = Item(Items.SUMMONING_POTION3_12142)
    ),

    /**
     * Super Attack
     *
     * @constructor Super Attack
     */
    SUPER_ATTACK(
        unfinished = UnfinishedPotion.IRIT,
        ingredient = Item(Items.EYE_OF_NEWT_221),
        level = 45,
        experience = 100.0,
        potion = Item(Items.SUPER_ATTACK3_145)
    ),

    /**
     * Super Antipoison
     *
     * @constructor Super Antipoison
     */
    SUPER_ANTIPOISON(
        unfinished = UnfinishedPotion.IRIT,
        ingredient = Item(Items.UNICORN_HORN_DUST_235),
        level = 48,
        experience = 106.3,
        potion = Item(Items.SUPER_ANTIPOISON3_181)
    ),

    /**
     * Fishing Potion
     *
     * @constructor Fishing Potion
     */
    FISHING_POTION(
        unfinished = UnfinishedPotion.AVANTOE,
        ingredient = Item(Items.SNAPE_GRASS_231),
        level = 50,
        experience = 112.5,
        potion = Item(Items.FISHING_POTION3_151)
    ),

    /**
     * Super Energy
     *
     * @constructor Super Energy
     */
    SUPER_ENERGY(
        unfinished = UnfinishedPotion.AVANTOE,
        ingredient = Item(Items.MORT_MYRE_FUNGUS_2970),
        level = 52,
        experience = 117.5,
        potion = Item(Items.SUPER_ENERGY3_3018)
    ),

    /**
     * Hunting Potion
     *
     * @constructor Hunting Potion
     */
    HUNTING_POTION(
        unfinished = UnfinishedPotion.AVANTOE,
        ingredient = Item(Items.KEBBIT_TEETH_DUST_10111),
        level = 53,
        experience = 120.0,
        potion = Item(Items.HUNTER_POTION3_10000)
    ),

    /**
     * Super Strength
     *
     * @constructor Super Strength
     */
    SUPER_STRENGTH(
        unfinished = UnfinishedPotion.KWUARM,
        ingredient = Item(Items.LIMPWURT_ROOT_225),
        level = 55,
        experience = 125.0,
        potion = Item(Items.SUPER_STRENGTH3_157)
    ),

    /**
     * Weapon Poison
     *
     * @constructor Weapon Poison
     */
    WEAPON_POISON(
        unfinished = UnfinishedPotion.KWUARM,
        ingredient = Item(Items.DRAGON_SCALE_DUST_241),
        level = 60,
        experience = 137.5,
        potion = Item(Items.WEAPON_POISON_187)
    ),

    /**
     * Super Restore
     *
     * @constructor Super Restore
     */
    SUPER_RESTORE(
        unfinished = UnfinishedPotion.SNAPDRAGON,
        ingredient = Item(Items.RED_SPIDERS_EGGS_223),
        level = 63,
        experience = 142.5,
        potion = Item(Items.SUPER_RESTORE3_3026)
    ),

    /**
     * Super Defence
     *
     * @constructor Super Defence
     */
    SUPER_DEFENCE(
        unfinished = UnfinishedPotion.CADANTINE,
        ingredient = Item(Items.WHITE_BERRIES_239),
        level = 66,
        experience = 160.0,
        potion = Item(Items.SUPER_DEFENCE3_163)
    ),

    /**
     * Antifire
     *
     * @constructor Antifire
     */
    ANTIFIRE(
        unfinished = UnfinishedPotion.LANTADYME,
        ingredient = Item(Items.DRAGON_SCALE_DUST_241),
        level = 69,
        experience = 157.5,
        potion = Item(Items.ANTIFIRE_POTION3_2454)
    ),

    /**
     * Super Ranging Potion
     *
     * @constructor Super Ranging Potion
     */
    SUPER_RANGING_POTION(
        unfinished = UnfinishedPotion.DWARF_WEED,
        ingredient = Item(Items.WINE_OF_ZAMORAK_245),
        level = 72,
        experience = 162.5,
        potion = Item(Items.RANGING_POTION3_169)
    ),

    /**
     * Super Magic
     *
     * @constructor Super Magic
     */
    SUPER_MAGIC(
        unfinished = UnfinishedPotion.LANTADYME,
        ingredient = Item(Items.POTATO_CACTUS_3138),
        level = 76,
        experience = 172.5,
        potion = Item(Items.MAGIC_POTION3_3042)
    ),

    /**
     * Zamorak Brew
     *
     * @constructor Zamorak Brew
     */
    ZAMORAK_BREW(
        unfinished = UnfinishedPotion.TORSTOL,
        ingredient = Item(Items.JANGERBERRIES_247),
        level = 78,
        experience = 175.0,
        potion = Item(Items.ZAMORAK_BREW3_189)
    ),

    /**
     * Saradomin Brew
     *
     * @constructor Saradomin Brew
     */
    SARADOMIN_BREW(
        unfinished = UnfinishedPotion.TOADFLAX,
        ingredient = GrindingItem.BIRDS_NEST.product,
        level = 81,
        experience = 180.0,
        potion = Item(Items.SARADOMIN_BREW3_6687)
    ),

    /**
     * Strong Weapon Poison
     *
     * @constructor Strong Weapon Poison
     */
    STRONG_WEAPON_POISON(
        unfinished = UnfinishedPotion.STRONG_WEAPON_POISON,
        ingredient = Item(Items.RED_SPIDERS_EGGS_223),
        level = 73,
        experience = 165.0,
        potion = Item(Items.WEAPON_POISON_PLUS_5937)
    ),

    /**
     * Super Strong Weapon Poison
     *
     * @constructor Super Strong Weapon Poison
     */
    SUPER_STRONG_WEAPON_POISON(
        unfinished = UnfinishedPotion.SUPER_STRONG_WEAPON_POISON,
        ingredient = Item(Items.POISON_IVY_BERRIES_6018),
        level = 82,
        experience = 190.0,
        potion = Item(Items.WEAPON_POISON_PLUS_PLUS_5940)
    ),

    /**
     * Strong Antipoison
     *
     * @constructor Strong Antipoison
     */
    STRONG_ANTIPOISON(
        unfinished = UnfinishedPotion.STRONG_ANTIPOISON,
        ingredient = Item(Items.YEW_ROOTS_6049),
        level = 68,
        experience = 155.0,
        potion = Item(Items.ANTIPOISON_PLUS3_5945)
    ),

    /**
     * Super Strong Antipoison
     *
     * @constructor Super Strong Antipoison
     */
    SUPER_STRONG_ANTIPOISON(
        unfinished = UnfinishedPotion.SUPER_STRONG_ANTIPOISON,
        ingredient = Item(Items.MAGIC_ROOTS_6051),
        level = 79,
        experience = 177.5,
        potion = Item(Items.ANTIPOISON_PLUS_PLUS3_5954)
    ),

    /**
     * Blamish Oil
     *
     * @constructor Blamish Oil
     */
    BLAMISH_OIL(
        unfinished = UnfinishedPotion.HARRALANDER,
        ingredient = Item(Items.BLAMISH_SNAIL_SLIME_1581),
        level = 25,
        experience = 80.0,
        potion = Item(Items.BLAMISH_OIL_1582)
    );

    companion object {
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
