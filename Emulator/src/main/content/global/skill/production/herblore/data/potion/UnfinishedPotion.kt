package content.global.skill.production.herblore.data.potion

import content.global.skill.production.herblore.data.Herb
import content.global.skill.production.herblore.item.HerblorePulse
import cfg.consts.Items
import core.game.node.item.Item

/**
 * Unfinished potion
 *
 * @param base          the base item used for the potion.
 * @param ingredient    the ingredient required for the potion.
 * @param level         the level required to craft this potion.
 * @param potion        the final product of the potion.
 * @return the [UnfinishedPotion].
 */
enum class UnfinishedPotion(val base: Item, val ingredient: Item, val level: Int, val potion: Item) {
    /**
     * The guam.
     */
    GUAM(
        ingredient = Herb.GUAM.product,
        level = 3,
        potion = Item(Items.GUAM_POTIONUNF_91)
    ),

    /**
     * The rogue purse.
     */
    ROGUE_PURSE(
        ingredient = Herb.ROGUES_PUSE.product,
        level = 3,
        potion = Item(Items.ROGUES_PURSE_POTIONUNF_4840)
    ),

    /**
     * The marrentill.
     */
    MARRENTILL(
        ingredient = Herb.MARRENTILL.product,
        level = 5,
        potion = Item(Items.MARRENTILL_POTIONUNF_93)
    ),

    /**
     * The tarromin.
     */
    TARROMIN(
        ingredient = Herb.TARROMIN.product,
        level = 12,
        potion = Item(Items.TARROMIN_POTIONUNF_95)
    ),

    /**
     * The harralander.
     */
    HARRALANDER(
        ingredient = Herb.HARRALANDER.product,
        level = 22,
        potion = Item(Items.HARRALANDER_POTIONUNF_97)
    ),

    /**
     * The ranarr.
     */
    RANARR(
        ingredient = Herb.RANARR.product,
        level = 30,
        potion = Item(Items.RANARR_POTIONUNF_99)
    ),

    /**
     * The toadflax.
     */
    TOADFLAX(
        ingredient = Herb.TOADFLAX.product,
        level = 34,
        potion = Item(Items.TOADFLAX_POTIONUNF_3002)
    ),

    /**
     * The spirit weed.
     */
    SPIRIT_WEED(
        ingredient = Herb.SPIRIT_WEED.product,
        level = 40,
        potion = Item(Items.SPIRIT_WEED_POTIONUNF_12181)
    ),

    /**
     * The irit.
     */
    IRIT(
        ingredient = Herb.IRIT.product,
        level = 45,
        potion = Item(Items.IRIT_POTIONUNF_101)
    ),

    /**
     * The avantoe.
     */
    AVANTOE(
        ingredient = Herb.AVANTOE.product,
        level = 50,
        potion = Item(Items.AVANTOE_POTIONUNF_103)
    ),

    /**
     * The kwuarm.
     */
    KWUARM(
        ingredient = Herb.KWUARM.product,
        level = 55,
        potion = Item(Items.KWUARM_POTIONUNF_105)
    ),

    /**
     * The snapdragon.
     */
    SNAPDRAGON(
        ingredient = Herb.SNAPDRAGON.product,
        level = 63,
        potion = Item(Items.SNAPDRAGON_POTIONUNF_3004)
    ),

    /**
     * The cadantine.
     */
    CADANTINE(
        ingredient = Herb.CADANTINE.product,
        level = 66,
        potion = Item(Items.CADANTINE_POTIONUNF_107)
    ),

    /**
     * The lantadyme.
     */
    LANTADYME(
        ingredient = Herb.LANTADYME.product,
        level = 69,
        potion = Item(Items.LANTADYME_POTIONUNF_2483)
    ),

    /**
     * The dwarf weed.
     */
    DWARF_WEED(
        ingredient = Herb.DWARF_WEED.product,
        level = 72,
        potion = Item(Items.DWARF_WEED_POTIONUNF_109)
    ),

    /**
     * The torstol.
     */
    TORSTOL(
        ingredient = Herb.TORSTOL.product,
        level = 75,
        potion = Item(Items.TORSTOL_POTIONUNF_111)
    ),

    /**
     * The strong weapon poison.
     */
    STRONG_WEAPON_POISON(
        base = HerblorePulse.COCONUT_MILK,
        ingredient = Item(Items.CACTUS_SPINE_6016),
        level = 73,
        potion = Item(Items.WEAPON_POISON_PLUSUNF_5936)
    ),

    /**
     * The super strong weapon poison.
     */
    SUPER_STRONG_WEAPON_POISON(
        base = HerblorePulse.COCONUT_MILK,
        ingredient = Item(Items.CAVE_NIGHTSHADE_2398),
        level = 82,
        potion = Item(Items.WEAPON_POISON_PLUS_PLUSUNF_5939)
    ),

    /**
     * The strong anti-poison.
     */
    STRONG_ANTIPOISON(
        base = HerblorePulse.COCONUT_MILK,
        ingredient = Herb.TOADFLAX.product,
        level = 68,
        potion = Item(Items.ANTIPOISON_PLUSUNF_5942)
    ),

    /**
     * The super strong anti-poison.
     */
    SUPER_STRONG_ANTIPOISON(
        base = HerblorePulse.COCONUT_MILK,
        ingredient = Herb.IRIT.product,
        level = 79,
        potion = Item(Items.ANTIPOISON_PLUS_PLUSUNF_5951)
    );

    /**
     * Constructs a new [UnfinishedPotion].
     *
     * @param ingredient    the ingredient.
     * @param level         the level.
     * @param potion        the potion.
     */
    constructor(ingredient: Item, level: Int, potion: Item) : this(
        HerblorePulse.VIAL_OF_WATER,
        ingredient,
        level,
        potion
    )

    companion object {
        /**
         * Gets the [UnfinishedPotion].
         *
         * @param item      the item.
         * @param base      the base item.
         * @return the unfinished potion.
         */
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
