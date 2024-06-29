package content.global.skill.production.herblore.data.potion

import content.global.skill.production.herblore.data.GrindingItem
import core.api.consts.Items
import core.game.node.item.Item

enum class FinishedPotion(
    val unfinished: UnfinishedPotion,
    val ingredient: Item,
    val level: Int,
    val experience: Double,
    val potion: Item
) {
    ATTACK_POTION(
        unfinished = UnfinishedPotion.GUAM,
        ingredient = Item(Items.EYE_OF_NEWT_221),
        3,
        25.0,
        potion = Item(Items.ATTACK_POTION3_121)
    ),
    ANTIPOISON_POTION(
        unfinished = UnfinishedPotion.MARRENTILL,
        ingredient = Item(Items.UNICORN_HORN_DUST_235),
        level = 5,
        experience = 37.5,
        potion = Item(Items.ANTIPOISON3_175)
    ),
    RELICYM_BALM(
        unfinished = UnfinishedPotion.ROGUE_PURSE,
        ingredient = Item(Items.CLEAN_SNAKE_WEED_1526),
        level = 8,
        experience = 0.0,
        potion = Item(Items.RELICYMS_BALM3_4844)
    ),
    STRENGTH_POTION(
        unfinished = UnfinishedPotion.TARROMIN,
        ingredient = Item(Items.LIMPWURT_ROOT_225),
        level = 12,
        experience = 50.0,
        potion = Item(Items.STRENGTH_POTION3_115)
    ),
    RESTORE_POTION(
        unfinished = UnfinishedPotion.HARRALANDER,
        ingredient = Item(Items.RED_SPIDERS_EGGS_223),
        level = 22,
        experience = 62.5,
        potion = Item(Items.RESTORE_POTION3_127)
    ),
    ENERGY_POTION(
        unfinished = UnfinishedPotion.HARRALANDER,
        ingredient = Item(Items.CHOCOLATE_DUST_1975),
        level = 26,
        experience = 67.5,
        potion = Item(Items.ENERGY_POTION3_3010)
    ),
    DEFENCE_POTION(
        unfinished = UnfinishedPotion.RANARR,
        ingredient = Item(Items.WHITE_BERRIES_239),
        level = 30,
        experience = 45.0,
        potion = Item(Items.DEFENCE_POTION3_133)
    ),
    AGILITY_POTION(
        unfinished = UnfinishedPotion.TOADFLAX,
        ingredient = Item(Items.TOADS_LEGS_2152),
        level = 34,
        experience = 80.0,
        potion = Item(Items.AGILITY_POTION3_3034)
    ),
    COMBAT_POTION(
        unfinished = UnfinishedPotion.HARRALANDER,
        ingredient = Item(Items.GOAT_HORN_DUST_9736),
        level = 36,
        experience = 84.0,
        potion = Item(Items.COMBAT_POTION3_9741)
    ),
    PRAYER_POTION(
        unfinished = UnfinishedPotion.RANARR,
        ingredient = Item(Items.SNAPE_GRASS_231),
        level = 38,
        experience = 87.5,
        potion = Item(Items.PRAYER_POTION3_139)
    ),
    SUMMONING_POTION(
        unfinished = UnfinishedPotion.SPIRIT_WEED,
        ingredient = Item(Items.COCKATRICE_EGG_12109),
        level = 40,
        experience = 92.0,
        potion = Item(Items.SUMMONING_POTION3_12142)
    ),
    SUPER_ATTACK(
        unfinished = UnfinishedPotion.IRIT,
        ingredient = Item(Items.EYE_OF_NEWT_221),
        level = 45,
        experience = 100.0,
        potion = Item(Items.SUPER_ATTACK3_145)
    ),
    SUPER_ANTIPOISON(
        unfinished = UnfinishedPotion.IRIT,
        ingredient = Item(Items.UNICORN_HORN_DUST_235),
        level = 48,
        experience = 106.3,
        potion = Item(Items.SUPER_ANTIPOISON3_181)
    ),
    FISHING_POTION(
        unfinished = UnfinishedPotion.AVANTOE,
        ingredient = Item(Items.SNAPE_GRASS_231),
        level = 50,
        experience = 112.5,
        potion = Item(Items.FISHING_POTION3_151)
    ),
    SUPER_ENERGY(
        unfinished = UnfinishedPotion.AVANTOE,
        ingredient = Item(Items.MORT_MYRE_FUNGUS_2970),
        level = 52,
        experience = 117.5,
        potion = Item(Items.SUPER_ENERGY3_3018)
    ),
    HUNTING_POTION(
        unfinished = UnfinishedPotion.AVANTOE,
        ingredient = Item(Items.KEBBIT_TEETH_DUST_10111),
        level = 53,
        experience = 120.0,
        potion = Item(Items.HUNTER_POTION3_10000)
    ),
    SUPER_STRENGTH(
        unfinished = UnfinishedPotion.KWUARM,
        ingredient = Item(Items.LIMPWURT_ROOT_225),
        level = 55,
        experience = 125.0,
        potion = Item(Items.SUPER_STRENGTH3_157)
    ),
    WEAPON_POISON(
        unfinished = UnfinishedPotion.KWUARM,
        ingredient = Item(Items.DRAGON_SCALE_DUST_241),
        level = 60,
        experience = 137.5,
        potion = Item(Items.WEAPON_POISON_187)
    ),
    SUPER_RESTORE(
        unfinished = UnfinishedPotion.SNAPDRAGON,
        ingredient = Item(Items.RED_SPIDERS_EGGS_223),
        level = 63,
        experience = 142.5,
        potion = Item(Items.SUPER_RESTORE3_3026)
    ),
    SUPER_DEFENCE(
        unfinished = UnfinishedPotion.CADANTINE,
        ingredient = Item(Items.WHITE_BERRIES_239),
        level = 66,
        experience = 160.0,
        potion = Item(Items.SUPER_DEFENCE3_163)
    ),
    ANTIFIRE(
        unfinished = UnfinishedPotion.LANTADYME,
        ingredient = Item(Items.DRAGON_SCALE_DUST_241),
        level = 69,
        experience = 157.5,
        potion = Item(Items.ANTIFIRE_POTION3_2454)
    ),
    SUPER_RANGING_POTION(
        unfinished = UnfinishedPotion.DWARF_WEED,
        ingredient = Item(Items.WINE_OF_ZAMORAK_245),
        level = 72,
        experience = 162.5,
        potion = Item(Items.RANGING_POTION3_169)
    ),
    SUPER_MAGIC(
        unfinished = UnfinishedPotion.LANTADYME,
        ingredient = Item(Items.POTATO_CACTUS_3138),
        level = 76,
        experience = 172.5,
        potion = Item(Items.MAGIC_POTION3_3042)
    ),
    ZAMORAK_BREW(
        unfinished = UnfinishedPotion.TORSTOL,
        ingredient = Item(Items.JANGERBERRIES_247),
        78,
        175.0,
        potion = Item(Items.ZAMORAK_BREW3_189)
    ),
    SARADOMIN_BREW(
        unfinished = UnfinishedPotion.TOADFLAX,
        ingredient = GrindingItem.BIRDS_NEST.product,
        level = 81,
        experience = 180.0,
        potion = Item(Items.SARADOMIN_BREW3_6687)
    ),
    STRONG_WEAPON_POISON(
        unfinished = UnfinishedPotion.STRONG_WEAPON_POISON,
        ingredient = Item(Items.RED_SPIDERS_EGGS_223),
        level = 73,
        experience = 165.0,
        potion = Item(Items.WEAPON_POISON_PLUS_5937)
    ),
    SUPER_STRONG_WEAPON_POISON(
        unfinished = UnfinishedPotion.SUPER_STRONG_WEAPON_POISON,
        ingredient = Item(Items.POISON_IVY_BERRIES_6018),
        level = 82,
        experience = 190.0,
        potion = Item(Items.WEAPON_POISON_PLUS_PLUS_5940)
    ),
    STRONG_ANTIPOISON(
        unfinished = UnfinishedPotion.STRONG_ANTIPOISON,
        ingredient = Item(Items.YEW_ROOTS_6049),
        level = 68,
        experience = 155.0,
        potion = Item(Items.ANTIPOISON_PLUS3_5945)
    ),
    SUPER_STRONG_ANTIPOISON(
        unfinished = UnfinishedPotion.SUPER_STRONG_ANTIPOISON,
        ingredient = Item(Items.MAGIC_ROOTS_6051),
        level = 79,
        experience = 177.5,
        potion = Item(Items.ANTIPOISON_PLUS_PLUS3_5954)
    ),
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
