package content.global.skill.production.herblore.data.potion

import content.global.skill.production.herblore.data.Herb
import content.global.skill.production.herblore.item.HerblorePulse
import cfg.consts.Items
import core.game.node.item.Item

/**
 * Represents an unfinished potion with its
 * base item, ingredient, required level, and final product.
 */
enum class UnfinishedPotion(val base: Item, val ingredient: Item, val level: Int, val potion: Item) {
    GUAM(
        base = HerblorePulse.VIAL_OF_WATER,
        ingredient = Herb.GUAM.product,
        level = 3,
        potion = Item(Items.GUAM_POTIONUNF_91)
    ),
    ROGUE_PURSE(
        base = HerblorePulse.VIAL_OF_WATER,
        ingredient = Herb.ROGUES_PUSE.product,
        level = 3,
        potion = Item(Items.ROGUES_PURSE_POTIONUNF_4840)
    ),
    MARRENTILL(
        base = HerblorePulse.VIAL_OF_WATER,
        ingredient = Herb.MARRENTILL.product,
        level = 5,
        potion = Item(Items.MARRENTILL_POTIONUNF_93)
    ),
    TARROMIN(
        base = HerblorePulse.VIAL_OF_WATER,
        ingredient = Herb.TARROMIN.product,
        level = 12,
        potion = Item(Items.TARROMIN_POTIONUNF_95)
    ),
    HARRALANDER(
        base = HerblorePulse.VIAL_OF_WATER,
        ingredient = Herb.HARRALANDER.product,
        level = 22,
        potion = Item(Items.HARRALANDER_POTIONUNF_97)
    ),
    RANARR(
        base = HerblorePulse.VIAL_OF_WATER,
        ingredient = Herb.RANARR.product,
        level = 30,
        potion = Item(Items.RANARR_POTIONUNF_99)
    ),
    TOADFLAX(
        base = HerblorePulse.VIAL_OF_WATER,
        ingredient = Herb.TOADFLAX.product,
        level = 34,
        potion = Item(Items.TOADFLAX_POTIONUNF_3002)
    ),
    SPIRIT_WEED(
        base = HerblorePulse.VIAL_OF_WATER,
        ingredient = Herb.SPIRIT_WEED.product,
        level = 40,
        potion = Item(Items.SPIRIT_WEED_POTIONUNF_12181)
    ),
    IRIT(
        base = HerblorePulse.VIAL_OF_WATER,
        ingredient = Herb.IRIT.product,
        level = 45,
        potion = Item(Items.IRIT_POTIONUNF_101)
    ),
    AVANTOE(
        base = HerblorePulse.VIAL_OF_WATER,
        ingredient = Herb.AVANTOE.product,
        level = 50,
        potion = Item(Items.AVANTOE_POTIONUNF_103)
    ),
    KWUARM(
        base = HerblorePulse.VIAL_OF_WATER,
        ingredient = Herb.KWUARM.product,
        level = 55,
        potion = Item(Items.KWUARM_POTIONUNF_105)
    ),
    SNAPDRAGON(
        base = HerblorePulse.VIAL_OF_WATER,
        ingredient = Herb.SNAPDRAGON.product,
        level = 63,
        potion = Item(Items.SNAPDRAGON_POTIONUNF_3004)
    ),
    CADANTINE(
        base = HerblorePulse.VIAL_OF_WATER,
        ingredient = Herb.CADANTINE.product,
        level = 66,
        potion = Item(Items.CADANTINE_POTIONUNF_107)
    ),
    LANTADYME(
        base = HerblorePulse.VIAL_OF_WATER,
        ingredient = Herb.LANTADYME.product,
        level = 69,
        potion = Item(Items.LANTADYME_POTIONUNF_2483)
    ),
    DWARF_WEED(
        base = HerblorePulse.VIAL_OF_WATER,
        ingredient = Herb.DWARF_WEED.product,
        level = 72,
        potion = Item(Items.DWARF_WEED_POTIONUNF_109)
    ),
    TORSTOL(
        base = HerblorePulse.VIAL_OF_WATER,
        ingredient = Herb.TORSTOL.product,
        level = 75,
        potion = Item(Items.TORSTOL_POTIONUNF_111)
    ),
    STRONG_WEAPON_POISON(
        base = HerblorePulse.COCONUT_MILK,
        ingredient = Item(Items.CACTUS_SPINE_6016),
        level = 73,
        potion = Item(Items.WEAPON_POISON_PLUSUNF_5936)
    ),
    SUPER_STRONG_WEAPON_POISON(
        base = HerblorePulse.COCONUT_MILK,
        ingredient = Item(Items.CAVE_NIGHTSHADE_2398),
        level = 82,
        potion = Item(Items.WEAPON_POISON_PLUS_PLUSUNF_5939)
    ),
    STRONG_ANTIPOISON(
        base = HerblorePulse.COCONUT_MILK,
        ingredient = Herb.TOADFLAX.product,
        level = 68,
        potion = Item(Items.ANTIPOISON_PLUSUNF_5942)
    ),
    SUPER_STRONG_ANTIPOISON(
        base = HerblorePulse.COCONUT_MILK,
        ingredient = Herb.IRIT.product,
        level = 79,
        potion = Item(Items.ANTIPOISON_PLUS_PLUSUNF_5951)
    );

    companion object {
        /**
         * Get the [UnfinishedPotion] for a given [item] and [base] item.
         *
         * @param item      the item to check.
         * @param base      the base item to check.
         * @return the [UnfinishedPotion], or `null` if not found.
         */
        fun forItem(item: Item, base: Item): UnfinishedPotion? {
            return values().find { potion ->
                (potion.ingredient.id == item.id || potion.ingredient.id == base.id) &&
                        (item.id == potion.base.id || base.id == potion.base.id)
            }
        }
    }
}
