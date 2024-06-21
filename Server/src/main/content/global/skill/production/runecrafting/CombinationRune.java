package content.global.skill.production.runecrafting;

import core.game.node.item.Item;

/**
 * The enum Combination rune.
 */
public enum CombinationRune {
    /**
     * The Mist.
     */
    MIST(new Item(4695), 6, 8.0, new Altar[]{Altar.WATER, Altar.AIR}, Rune.AIR, Rune.WATER),
    /**
     * The Dust.
     */
    DUST(new Item(4696), 10, 8.3, new Altar[]{Altar.EARTH, Altar.AIR}, Rune.AIR, Rune.EARTH),
    /**
     * The Mud.
     */
    MUD(new Item(4698), 13, 9.3, new Altar[]{Altar.EARTH, Altar.WATER}, Rune.WATER, Rune.EARTH),
    /**
     * The Smoke.
     */
    SMOKE(new Item(4697), 15, 8.5, new Altar[]{Altar.FIRE, Altar.AIR}, Rune.AIR, Rune.FIRE),
    /**
     * The Steam.
     */
    STEAM(new Item(4694), 19, 9.3, new Altar[]{Altar.WATER, Altar.FIRE}, Rune.WATER, Rune.FIRE),
    /**
     * The Lava.
     */
    LAVA(new Item(4699), 23, 10.0, new Altar[]{Altar.FIRE, Altar.EARTH}, Rune.EARTH, Rune.FIRE);

    private final Item rune;
    private final int level;
    private final double experience;
    private final Altar[] altars;
    private final Rune[] runes;

    /**
     * Constructs a new {@code CombinationRune} {@code Object}.
     *
     * @param rune       the rune.
     * @param level      the level.
     * @param experience the base experience/
     * @param runes      the runes.
     */
    CombinationRune(final Item rune, final int level, final double experience, final Altar[] altars, final Rune... runes) {
        this.rune = rune;
        this.level = level;
        this.experience = experience;
        this.altars = altars;
        this.runes = runes;
    }

    /**
     * Gets the rune.
     *
     * @return The rune.
     */
    public final Item getRune() {
        return rune;
    }

    /**
     * Gets the level.
     *
     * @return The level.
     */
    public final int getLevel() {
        return level;
    }

    /**
     * Gets the experience.
     *
     * @return The experience.
     */
    public final double getExperience() {
        return experience;
    }

    /**
     * Gets the runes.
     *
     * @return The runes.
     */
    public final Rune[] getRunes() {
        return runes;
    }

    /**
     * Method used to get the high experience.
     *
     * @return the experience incremented.
     */
    public final double getHighExperience() {
        return (experience % 1 == 0) ? experience + 5 : experience + 8;
    }

    /**
     * Gets the altars.
     *
     * @return The altars.
     */
    public Altar[] getAltars() {
        return altars;
    }

    /**
     * Gets the combination rune.
     *
     * @param altar the altar.
     * @param item  the item
     * @return the combo rune.
     */
    public static CombinationRune forAltar(final Altar altar, final Item item) {
        for (CombinationRune rune : values()) {
            for (Altar alt : rune.getAltars()) {
                if (alt == altar) {
                    String altarElement = alt.name();
                    String talismanElement = item.getName().contains("talisman") ? Talisman.forItem(item).name() : Rune.forItem(item).name();
                    if (altarElement.equals(talismanElement)) {
                        continue;
                    }
                    for (Rune r : rune.getRunes()) {
                        if (r.name().equals(talismanElement)) {
                            return rune;
                        }
                    }
                    continue;
                }
            }
        }
        return null;
    }
}
