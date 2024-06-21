package content.global.skill.production.herblore.potions;

import content.global.skill.production.herblore.GrindingItem;
import core.api.consts.Items;
import core.game.node.item.Item;

/**
 * The enum Finished potion.
 */
public enum FinishedPotion {
    /**
     * The Attack potion.
     */
    ATTACK_POTION(UnfinishedPotion.GUAM, new Item(221), 3, 25, new Item(121)),
    /**
     * The Antipoison potion.
     */
    ANTIPOISON_POTION(UnfinishedPotion.MARRENTILL, new Item(235), 5, 37.5, new Item(175)),
    /**
     * The Relicym's balm.
     */
    RELICYM_BALM(UnfinishedPotion.ROGUE_PURSE, new Item(1526), 8, 0, new Item(4844)),

    /**
     * The Strength potion.
     */
    STRENGTH_POTION(UnfinishedPotion.TARROMIN, new Item(225), 12, 50, new Item(115)),
    /**
     * The Restore potion.
     */
    RESTORE_POTION(UnfinishedPotion.HARRALANDER, new Item(223), 22, 62.5, new Item(127)),
    /**
     * The Energy potion.
     */
    ENERGY_POTION(UnfinishedPotion.HARRALANDER, new Item(1975), 26, 67.5, new Item(3010)),
    /**
     * The Defence potion.
     */
    DEFENCE_POTION(UnfinishedPotion.RANARR, new Item(239), 30, 45, new Item(133)),
    /**
     * The Agility potion.
     */
    AGILITY_POTION(UnfinishedPotion.TOADFLAX, new Item(2152), 34, 80, new Item(3034)),
    /**
     * The Combat potion.
     */
    COMBAT_POTION(UnfinishedPotion.HARRALANDER, new Item(9736), 36, 84, new Item(9741)),
    /**
     * The Prayer potion.
     */
    PRAYER_POTION(UnfinishedPotion.RANARR, new Item(231), 38, 87.5, new Item(139)),
    /**
     * The Summoning potion.
     */
    SUMMONING_POTION(UnfinishedPotion.SPIRIT_WEED, new Item(12109), 40, 92, new Item(12142)),
    /**
     * The Super attack.
     */
    SUPER_ATTACK(UnfinishedPotion.IRIT, new Item(221), 45, 100, new Item(145)),
    /**
     * The Super antipoison.
     */
    SUPER_ANTIPOISON(UnfinishedPotion.IRIT, new Item(235), 48, 106.3, new Item(181)),
    /**
     * The Fishing potion.
     */
    FISHING_POTION(UnfinishedPotion.AVANTOE, new Item(231), 50, 112.5, new Item(151)),
    /**
     * The Super energy.
     */
    SUPER_ENERGY(UnfinishedPotion.AVANTOE, new Item(2970), 52, 117.5, new Item(3018)),
    /**
     * The Hunting potion.
     */
    HUNTING_POTION(UnfinishedPotion.AVANTOE, new Item(Items.KEBBIT_TEETH_DUST_10111), 53, 120, new Item(10000)),
    /**
     * The Super strength.
     */
    SUPER_STRENGTH(UnfinishedPotion.KWUARM, new Item(225), 55, 125, new Item(157)),
    /**
     * The Weapon poison.
     */
    WEAPON_POISON(UnfinishedPotion.KWUARM, new Item(241), 60, 137.5, new Item(187)),
    /**
     * The Super restore.
     */
    SUPER_RESTORE(UnfinishedPotion.SNAPDRAGON, new Item(223), 63, 142.5, new Item(3026)),
    /**
     * The Super defence.
     */
    SUPER_DEFENCE(UnfinishedPotion.CADANTINE, new Item(239), 66, 160, new Item(163)),
    /**
     * The Antifire.
     */
    ANTIFIRE(UnfinishedPotion.LANTADYME, new Item(241), 69, 157.5, new Item(2454)),
    /**
     * The Super ranging potion.
     */
    SUPER_RANGING_POTION(UnfinishedPotion.DWARF_WEED, new Item(245), 72, 162.5, new Item(169)),
    /**
     * The Super magic.
     */
    SUPER_MAGIC(UnfinishedPotion.LANTADYME, new Item(3138), 76, 172.5, new Item(3042)),
    /**
     * The Zamorak brew.
     */
    ZAMORAK_BREW(UnfinishedPotion.TORSTOL, new Item(247), 78, 175, new Item(189)),
    /**
     * The Saradomin brew.
     */
    SARADOMIN_BREW(UnfinishedPotion.TOADFLAX, GrindingItem.BIRDS_NEST.getProduct(), 81, 180, new Item(6687)),
    /**
     * The Strong weapon poison.
     */
    STRONG_WEAPON_POISON(UnfinishedPotion.STRONG_WEAPON_POISON, new Item(223), 73, 165, new Item(5937)),
    /**
     * The Super strong weapon poison.
     */
    SUPER_STRONG_WEAPON_POISON(UnfinishedPotion.SUPER_STRONG_WEAPON_POISON, new Item(6018), 82, 190, new Item(5940)),
    /**
     * The Strong antipoison.
     */
    STRONG_ANTIPOISON(UnfinishedPotion.STRONG_ANTIPOISON, new Item(6049), 68, 155, new Item(5945)),
    /**
     * The Super strong antipoison.
     */
    SUPER_STRONG_ANTIPOISON(UnfinishedPotion.SUPER_STRONG_ANTIPOISON, new Item(6051), 79, 177.5, new Item(5954)),
    /**
     * The Blamish oil.
     */
    BLAMISH_OIL(UnfinishedPotion.HARRALANDER, new Item(1581), 25, 80, new Item(1582));

    private final UnfinishedPotion unfinished;

    private final Item ingredient;

    private final int level;

    private final double experience;

    private final Item potion;


    FinishedPotion(final UnfinishedPotion unfinished, final Item ingredient, final int level, final double experience, final Item potion) {
        this.unfinished = unfinished;
        this.ingredient = ingredient;
        this.level = level;
        this.experience = experience;
        this.potion = potion;
    }

    /**
     * Gets unfinished.
     *
     * @return the unfinished
     */
    public UnfinishedPotion getUnfinished() {
        return unfinished;
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
     * Gets experience.
     *
     * @return the experience
     */
    public double getExperience() {
        return experience;
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
     * Gets potion.
     *
     * @param unf        the unf
     * @param ingredient the ingredient
     * @return the potion
     */
    public static FinishedPotion getPotion(final Item unf, final Item ingredient) {
        for (FinishedPotion pot : values()) {
            if (pot.getUnfinished().getPotion().getId() == unf.getId() && pot.getIngredient().getId() == ingredient.getId()) {
                return pot;
            }
        }
        return null;
    }
}
