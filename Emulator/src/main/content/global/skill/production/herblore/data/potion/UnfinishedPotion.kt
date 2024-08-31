package content.global.skill.production.herblore.data.potion

import content.global.skill.production.herblore.data.Herb
import content.global.skill.production.herblore.item.HerblorePulse
import cfg.consts.Items
import core.game.node.item.Item

/**
 * Unfinished potion
 *
 * @param base The base item used for the potion.
 * @param ingredient The additional ingredient required for the potion.
 * @param level The level of complexity or skill required to create the potion.
 * @param potion The final item that represents the potion.
 * @constructor Unfinished potion
 */
enum class UnfinishedPotion(
    val base: Item, // The base item used for the potion
    val ingredient: Item, // The additional ingredient required for the potion
    val level: Int, // The level of complexity or skill required to create the potion
    val potion: Item // The final item that represents the potion
) {
    /**
     * Guam
     *
     * @constructor Guam
     */
    GUAM(
        ingredient = Herb.GUAM.product,
        level = 3,
        potion = Item(Items.GUAM_POTIONUNF_91)
    ),

    /**
     * Rogue Purse
     *
     * @constructor Rogue Purse
     */
    ROGUE_PURSE(
        ingredient = Herb.ROGUES_PUSE.product,
        level = 3,
        potion = Item(Items.ROGUES_PURSE_POTIONUNF_4840)
    ),

    /**
     * Marrentill
     *
     * @constructor Marrentill
     */
    MARRENTILL(
        ingredient = Herb.MARRENTILL.product,
        level = 5,
        potion = Item(Items.MARRENTILL_POTIONUNF_93)
    ),

    /**
     * Tarromin
     *
     * @constructor Tarromin
     */
    TARROMIN(
        ingredient = Herb.TARROMIN.product,
        level = 12,
        potion = Item(Items.TARROMIN_POTIONUNF_95)
    ),

    /**
     * Harralander
     *
     * @constructor Harralander
     */
    HARRALANDER(
        ingredient = Herb.HARRALANDER.product,
        level = 22,
        potion = Item(Items.HARRALANDER_POTIONUNF_97)
    ),

    /**
     * Ranarr
     *
     * @constructor Ranarr
     */
    RANARR(
        ingredient = Herb.RANARR.product,
        level = 30,
        potion = Item(Items.RANARR_POTIONUNF_99)
    ),

    /**
     * Toadflax
     *
     * @constructor Toadflax
     */
    TOADFLAX(
        ingredient = Herb.TOADFLAX.product,
        level = 34,
        potion = Item(Items.TOADFLAX_POTIONUNF_3002)
    ),

    /**
     * Spirit Weed
     *
     * @constructor Spirit Weed
     */
    SPIRIT_WEED(
        ingredient = Herb.SPIRIT_WEED.product,
        level = 40,
        potion = Item(Items.SPIRIT_WEED_POTIONUNF_12181)
    ),

    /**
     * Irit
     *
     * @constructor Irit
     */
    IRIT(
        ingredient = Herb.IRIT.product,
        level = 45,
        potion = Item(Items.IRIT_POTIONUNF_101)
    ),

    /**
     * Avantoe
     *
     * @constructor Avantoe
     */
    AVANTOE(
        ingredient = Herb.AVANTOE.product,
        level = 50,
        potion = Item(Items.AVANTOE_POTIONUNF_103)
    ),

    /**
     * Kwuarm
     *
     * @constructor Kwuarm
     */
    KWUARM(
        ingredient = Herb.KWUARM.product,
        level = 55,
        potion = Item(Items.KWUARM_POTIONUNF_105)
    ),

    /**
     * Snapdragon
     *
     * @constructor Snapdragon
     */
    SNAPDRAGON(
        ingredient = Herb.SNAPDRAGON.product,
        level = 63,
        potion = Item(Items.SNAPDRAGON_POTIONUNF_3004)
    ),

    /**
     * Cadantine
     *
     * @constructor Cadantine
     */
    CADANTINE(
        ingredient = Herb.CADANTINE.product,
        level = 66,
        potion = Item(Items.CADANTINE_POTIONUNF_107)
    ),

    /**
     * Lantadyme
     *
     * @constructor Lantadyme
     */
    LANTADYME(
        ingredient = Herb.LANTADYME.product,
        level = 69,
        potion = Item(Items.LANTADYME_POTIONUNF_2483)
    ),

    /**
     * Dwarf Weed
     *
     * @constructor Dwarf Weed
     */
    DWARF_WEED(
        ingredient = Herb.DWARF_WEED.product,
        level = 72,
        potion = Item(Items.DWARF_WEED_POTIONUNF_109)
    ),

    /**
     * Torstol
     *
     * @constructor Torstol
     */
    TORSTOL(
        ingredient = Herb.TORSTOL.product,
        level = 75,
        potion = Item(Items.TORSTOL_POTIONUNF_111)
    ),

    /**
     * Strong Weapon Poison
     *
     * @constructor Strong Weapon Poison
     */
    STRONG_WEAPON_POISON(
        base = HerblorePulse.COCONUT_MILK,
        ingredient = Item(Items.CACTUS_SPINE_6016),
        level = 73,
        potion = Item(Items.WEAPON_POISON_PLUSUNF_5936)
    ),

    /**
     * Super Strong Weapon Poison
     *
     * @constructor Super Strong Weapon Poison
     */
    SUPER_STRONG_WEAPON_POISON(
        base = HerblorePulse.COCONUT_MILK,
        ingredient = Item(Items.CAVE_NIGHTSHADE_2398),
        level = 82,
        potion = Item(Items.WEAPON_POISON_PLUS_PLUSUNF_5939)
    ),

    /**
     * Strong Antipoison
     *
     * @constructor Strong Antipoison
     */
    STRONG_ANTIPOISON(
        base = HerblorePulse.COCONUT_MILK,
        ingredient = Herb.TOADFLAX.product,
        level = 68,
        potion = Item(Items.ANTIPOISON_PLUSUNF_5942)
    ),

    /**
     * Super Strong Antipoison
     *
     * @constructor Super Strong Antipoison
     */
    SUPER_STRONG_ANTIPOISON(
        base = HerblorePulse.COCONUT_MILK,
        ingredient = Herb.IRIT.product,
        level = 79,
        potion = Item(Items.ANTIPOISON_PLUS_PLUSUNF_5951)
    );

    constructor(ingredient: Item, level: Int, potion: Item) : this(
        HerblorePulse.VIAL_OF_WATER,
        ingredient,
        level,
        potion
    )

    companion object {
        fun forItem(item: Item, base: Item): UnfinishedPotion? {
            for (potion in values()) {
                if ((potion.ingredient.id == item.id || potion.ingredient.id == base.id) && (item.id == potion.base.id || base.id == potion.base.id)) {
                    return potion
                }
            }
            return null
        }
    }
}
