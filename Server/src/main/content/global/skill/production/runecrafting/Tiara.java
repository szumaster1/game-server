package content.global.skill.production.runecrafting;

import core.game.node.item.Item;

/**
 * The enum Tiara.
 */
public enum Tiara {
    /**
     * The Air.
     */
    AIR(new Item(5527), 25),
    /**
     * The Mind.
     */
    MIND(new Item(5529), 27.5),
    /**
     * The Water.
     */
    WATER(new Item(5531), 30),
    /**
     * The Earth.
     */
    EARTH(new Item(5535), 32.5),
    /**
     * The Fire.
     */
    FIRE(new Item(5537), 35),
    /**
     * The Body.
     */
    BODY(new Item(5533), 37.5),
    /**
     * The Cosmic.
     */
    COSMIC(new Item(5539), 40),
    /**
     * The Chaos.
     */
    CHAOS(new Item(5543), 43.5),
    /**
     * The Astral.
     */
    ASTRAL(new Item(9106), 43.5),
    /**
     * The Nature.
     */
    NATURE(new Item(5541), 45),
    /**
     * The Law.
     */
    LAW(new Item(5545), 47.5),
    /**
     * The Death.
     */
    DEATH(new Item(5547), 50),
    /**
     * The Blood.
     */
    BLOOD(new Item(5549), 52.5),
    /**
     * The Soul.
     */
    SOUL(new Item(5551), 55);

    Tiara(final Item tiara, final double experience) {
        this.tiara = tiara;
        this.experience = experience;
    }

    private final Item tiara;

    private final double experience;

    /**
     * Gets tiara.
     *
     * @return the tiara
     */
    public Item getTiara() {
        return tiara;
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
     * Gets talisman.
     *
     * @return the talisman
     */
    public Talisman getTalisman() {
        for (Talisman talisman : Talisman.values()) {
            if (talisman.name().equals(name())) {
                return talisman;
            }
        }
        return null;
    }

    /**
     * For item tiara.
     *
     * @param item the item
     * @return the tiara
     */
    public static Tiara forItem(final Item item) {
        for (Tiara tiara : values()) {
            if (tiara.getTiara().getId() == item.getId()) {
                return tiara;
            }
        }
        return null;
    }
}
