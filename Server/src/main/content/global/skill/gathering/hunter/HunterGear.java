package content.global.skill.gathering.hunter;

import core.game.node.entity.player.Player;
import core.game.node.item.Item;

/**
 * The enum Hunter gear.
 */
public enum HunterGear {
    /**
     * The Glove of silence.
     */
    GLOVE_OF_SILENCE(3.0, new Item(10075)),
    /**
     * The Spotier cape.
     */
    SPOTIER_CAPE(5.0, new Item(10071)),
    /**
     * The Spotted cape.
     */
    SPOTTED_CAPE(5.0, new Item(10069)),
    /**
     * The Larupia.
     */
    LARUPIA(10.00, new Item(10045), new Item(10043), new Item(10041)),
    /**
     * The Desert gear.
     */
    DESERT_GEAR(10.00, new Item(12568), new Item(10063), new Item(10061)),
    /**
     * The Graahk gear.
     */
    GRAAHK_GEAR(10.00, new Item(10051), new Item(10047), new Item(10049)),
    /**
     * The Kyatt gear.
     */
    KYATT_GEAR(10.00, new Item(10039), new Item(10035), new Item(10037)),
    /**
     * The Jungle gear.
     */
    JUNGLE_GEAR(8.00, new Item(10059), new Item(10057)),
    /**
     * The Polar gear.
     */
    POLAR_GEAR(8.00, new Item(10065), new Item(10067));


    HunterGear(double chanceRate, Item... equipment) {
        this.equipment = equipment;
        this.chanceRate = chanceRate;
    }

    private final Item[] equipment;

    private final double chanceRate;

    /**
     * In gear hunter gear.
     *
     * @param player the player
     * @return the hunter gear
     */
    public static HunterGear inGear(final Player player) {
        int contained = 0;
        for (HunterGear type : values()) {
            for (Item i : type.getEquipment()) {
                if (player.getEquipment().containsItem(i)) {
                    contained += 1;
                }
                if (contained == type.getEquipment().length) {
                    return type;
                }
            }
        }
        return null;
    }

    /**
     * Gets chance rate.
     *
     * @param player the player
     * @return the chance rate
     */
    public static double getChanceRate(Player player) {
        final HunterGear gear = inGear(player);
        if (gear == null) {
            return 0.0;
        }
        return gear.getChanceRate();
    }

    /**
     * Get equipment item [ ].
     *
     * @return the item [ ]
     */
    public Item[] getEquipment() {
        return equipment;
    }

    /**
     * Gets chance rate.
     *
     * @return the chance rate
     */
    public double getChanceRate() {
        return chanceRate;
    }
}