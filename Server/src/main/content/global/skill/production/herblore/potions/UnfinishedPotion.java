package content.global.skill.production.herblore.potions;

import content.global.skill.production.herblore.Herbs;
import core.game.node.item.Item;

/**
 * The enum Unfinished potion.
 */
public enum UnfinishedPotion {
    /**
     * The Guam.
     */
    GUAM(Herbs.GUAM.getProduct(), 3, new Item(91)),
    /**
     * The Rogue's purse.
     */
    ROGUE_PURSE(Herbs.ROGUES_PUSE.getProduct(), 3, new Item(4840)),
    /**
     * The Marrentill.
     */
    MARRENTILL(Herbs.MARRENTILL.getProduct(), 5, new Item(93)),
    /**
     * The Tarromin.
     */
    TARROMIN(Herbs.TARROMIN.getProduct(), 12, new Item(95)),
    /**
     * The Harralander.
     */
    HARRALANDER(Herbs.HARRALANDER.getProduct(), 22, new Item(97)),
    /**
     * The Ranarr.
     */
    RANARR(Herbs.RANARR.getProduct(), 30, new Item(99)),
    /**
     * The Toadflax.
     */
    TOADFLAX(Herbs.TOADFLAX.getProduct(), 34, new Item(3002)),
    /**
     * The Spirit weed.
     */
    SPIRIT_WEED(Herbs.SPIRIT_WEED.getProduct(), 40, new Item(12181)),
    /**
     * The Irit.
     */
    IRIT(Herbs.IRIT.getProduct(), 45, new Item(101)),
    /**
     * The Avantoe.
     */
    AVANTOE(Herbs.AVANTOE.getProduct(), 50, new Item(103)),
    /**
     * The Kwuarm.
     */
    KWUARM(Herbs.KWUARM.getProduct(), 55, new Item(105)),
    /**
     * The Snapdragon.
     */
    SNAPDRAGON(Herbs.SNAPDRAGON.getProduct(), 63, new Item(3004)),
    /**
     * The Cadantine.
     */
    CADANTINE(Herbs.CADANTINE.getProduct(), 66, new Item(107)),
    /**
     * The Lantadyme.
     */
    LANTADYME(Herbs.LANTADYME.getProduct(), 69, new Item(2483)),
    /**
     * The Dwarf weed.
     */
    DWARF_WEED(Herbs.DWARF_WEED.getProduct(), 72, new Item(109)),
    /**
     * The Torstol.
     */
    TORSTOL(Herbs.TORSTOL.getProduct(), 75, new Item(111)),
    /**
     * The Strong weapon poison.
     */
    STRONG_WEAPON_POISON(HerblorePulse.COCONUT_MILK, new Item(6016), 73, new Item(5936)),
    /**
     * The Super strong weapon poison.
     */
    SUPER_STRONG_WEAPON_POISON(HerblorePulse.COCONUT_MILK, new Item(2398), 82, new Item(5939)),
    /**
     * The Strong antipoison.
     */
    STRONG_ANTIPOISON(HerblorePulse.COCONUT_MILK, Herbs.TOADFLAX.getProduct(), 68, new Item(5942)),
    /**
     * The Super strong antipoison.
     */
    SUPER_STRONG_ANTIPOISON(HerblorePulse.COCONUT_MILK, Herbs.IRIT.getProduct(), 79, new Item(5951));

    private final Item base;

    private final Item ingredient;

    private final int level;

    private final Item potion;


    UnfinishedPotion(final Item base, final Item ingredient, final int level, final Item potion) {
        this.base = base;
        this.ingredient = ingredient;
        this.level = level;
        this.potion = potion;
    }


    UnfinishedPotion(final Item ingredient, final int level, final Item potion) {
        this(HerblorePulse.VIAL_OF_WATER, ingredient, level, potion);
    }

    /**
     * Gets ingredient.
     *
     * @return the ingredient
     */
    public Item getIngredient() {
        return ingredient;
    }

    /**
     * Gets level.
     *
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets potion.
     *
     * @return the potion
     */
    public Item getPotion() {
        return potion;
    }

    /**
     * Gets base.
     *
     * @return the base
     */
    public Item getBase() {
        return base;
    }

    /**
     * For item unfinished potion.
     *
     * @param item the item
     * @param base the base
     * @return the unfinished potion
     */
    public static UnfinishedPotion forItem(final Item item, final Item base) {
        for (UnfinishedPotion potion : values()) {
            if ((potion.getIngredient().getId() == item.getId() || potion.getIngredient().getId() == base.getId()) && (item.getId() == potion.getBase().getId() || base.getId() == potion.getBase().getId())) {
                return potion;
            }
        }
        return null;
    }

}
