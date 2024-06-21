package content.global.skill.production.runecrafting;

import core.game.node.entity.player.Player;
import core.game.node.item.Item;
import core.game.world.map.Location;

/**
 * The enum Talisman.
 */
public enum Talisman {
    /**
     * The Air.
     */
    AIR(new Item(1438), MysteriousRuin.AIR),
    /**
     * The Mind.
     */
    MIND(new Item(1448), MysteriousRuin.MIND),
    /**
     * The Water.
     */
    WATER(new Item(1444), MysteriousRuin.WATER),
    /**
     * The Earth.
     */
    EARTH(new Item(1440), MysteriousRuin.EARTH),
    /**
     * The Fire.
     */
    FIRE(new Item(1442), MysteriousRuin.FIRE),
    /**
     * The Elemental.
     */
    ELEMENTAL(new Item(5516), null),
    /**
     * The Body.
     */
    BODY(new Item(1446), MysteriousRuin.BODY),
    /**
     * The Cosmic.
     */
    COSMIC(new Item(1454), MysteriousRuin.COSMIC),
    /**
     * The Chaos.
     */
    CHAOS(new Item(1452), MysteriousRuin.CHAOS),
    /**
     * The Nature.
     */
    NATURE(new Item(1462), MysteriousRuin.NATURE),
    /**
     * The Law.
     */
    LAW(new Item(1458), MysteriousRuin.LAW),
    /**
     * The Death.
     */
    DEATH(new Item(1456), MysteriousRuin.DEATH),
    /**
     * The Blood.
     */
    BLOOD(new Item(1450), null),
    /**
     * The Soul.
     */
    SOUL(new Item(1460), null);

    Talisman(final Item talisman, MysteriousRuin ruin) {
        this.talisman = talisman;
        this.ruin = ruin;
    }

    private final Item talisman;
    private final MysteriousRuin ruin;

    /**
     * Gets talisman.
     *
     * @return the talisman
     */
    public Item getTalisman() {
        return talisman;
    }

    /**
     * Locate.
     *
     * @param player the player
     */
    public final void locate(final Player player) {
        if (this == ELEMENTAL || getRuin() == null) {
            player.getPacketDispatch().sendMessage("You cannot tell which direction the Talisman is pulling...");
            return;
        }
        String direction = "";
        Location loc = getRuin().getBase();
        if (player.getLocation().getY() > loc.getY() && player.getLocation().getX() - 1 > loc.getX())
            direction = "south-west";
        else if (player.getLocation().getX() < loc.getX() && player.getLocation().getY() > loc.getY())
            direction = "south-east";
        else if (player.getLocation().getX() > loc.getX() + 1 && player.getLocation().getY() < loc.getY())
            direction = "north-west";
        else if (player.getLocation().getX() < loc.getX() && player.getLocation().getY() < loc.getY())
            direction = "north-east";
        else if (player.getLocation().getY() < loc.getY())
            direction = "north";
        else if (player.getLocation().getY() > loc.getY())
            direction = "south";
        else if (player.getLocation().getX() < loc.getX() + 1)
            direction = "east";
        else if (player.getLocation().getX() > loc.getX() + 1)
            direction = "west";
        player.getPacketDispatch().sendMessage("The talisman pulls towards the " + direction + ".");
    }

    /**
     * Gets ruin.
     *
     * @return the ruin
     */
    public MysteriousRuin getRuin() {
        for (MysteriousRuin ruin : MysteriousRuin.values()) {
            if (ruin.name().equals(name())) {
                return ruin;
            }
        }
        return ruin;
    }

    /**
     * Gets tiara.
     *
     * @return the tiara
     */
    public Tiara getTiara() {
        for (Tiara tiara : Tiara.values()) {
            if (tiara.name().equals(name())) {
                return tiara;
            }
        }
        return null;
    }

    /**
     * For item talisman.
     *
     * @param item the item
     * @return the talisman
     */
    public static Talisman forItem(final Item item) {
        for (Talisman talisman : values()) {
            if (talisman.getTalisman().getId() == item.getId()) {
                return talisman;
            }
        }
        return null;
    }

    /**
     * For name talisman.
     *
     * @param name the name
     * @return the talisman
     */
    public static Talisman forName(final String name) {
        for (Talisman talisman : values()) {
            if (talisman.name().equals(name)) {
                return talisman;
            }
        }
        return null;
    }

}
