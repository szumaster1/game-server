package content.global.travel.ship;

import core.game.node.entity.player.Player;
import core.game.world.map.Location;

/**
 * The enum Ships.
 */
public enum Ships {
    /**
     * Port sarim to entrana ships.
     */
    PORT_SARIM_TO_ENTRANA(Location.create(2834, 3331, 1), 1, 15, "Entrana"),
    /**
     * The Entrana to port sarim.
     */
    ENTRANA_TO_PORT_SARIM(Location.create(3048, 3234, 0), 2, 15, "Port Sarim"),
    /**
     * Port sarim to crandor ships.
     */
    PORT_SARIM_TO_CRANDOR(Location.create(2849, 3238, 0), 3, 12, "Crandor"),
    /**
     * The Crandor to port sarim.
     */
    CRANDOR_TO_PORT_SARIM(Location.create(2834, 3335, 0), 4, 13, "Port Sarim"),
    /**
     * Port sarim to karamaja ships.
     */
    PORT_SARIM_TO_KARAMAJA(Location.create(2956, 3143, 1), 5, 9, "Karamja"),
    /**
     * The Karamjama to port sarim.
     */
    KARAMJAMA_TO_PORT_SARIM(Location.create(3029, 3217, 0), 6, 8, "Port Sarim"),
    /**
     * Ardougne to brimhaven ships.
     */
    ARDOUGNE_TO_BRIMHAVEN(Location.create(2775, 3234, 1), 7, 4, "Brimhaven"),
    /**
     * Brimhaven to ardougne ships.
     */
    BRIMHAVEN_TO_ARDOUGNE(Location.create(2683, 3268, 1), 8, 4, "Ardougne"),
    /**
     * The Cairn island to port khazard.
     */
    CAIRN_ISLAND_TO_PORT_KHAZARD(Location.create(2676, 3170, 0), 10, 8, "Port Khazard"),
    /**
     * The Port khazard to ship yard.
     */
    PORT_KHAZARD_TO_SHIP_YARD(Location.create(2998, 3043, 0), 11, 23, "the Ship Yard"),
    /**
     * The Ship yard to port khazard.
     */
    SHIP_YARD_TO_PORT_KHAZARD(Location.create(2676, 3170, 0), 12, 23, "Port Khazard"),
    /**
     * The Cairn island to port sarim.
     */
    CAIRN_ISLAND_TO_PORT_SARIM(Location.create(3048, 3234, 0), 13, 17, "Port Sarim"),
    /**
     * The Port sarim to pest control.
     */
    PORT_SARIM_TO_PEST_CONTROL(Location.create(2663, 2676, 1), 14, 12, "Pest Control"),
    /**
     * The Pest to port sarim.
     */
    PEST_TO_PORT_SARIM(Location.create(3041, 3198, 1), 15, 12, "Port Sarim"),
    /**
     * Feldip to karamja ships.
     */
    FELDIP_TO_KARAMJA(Location.create(2763, 2956, 0), 16, 10, "Karamja"),
    /**
     * Karamja to feldip ships.
     */
    KARAMJA_TO_FELDIP(Location.create(2763, 2956, 0), 17, 10, "Feldip");


    Ships(Location location, int config, int delay, final String name) {
        this.location = location;
        this.config = config;
        this.delay = delay;
        this.name = name;
    }

    private final Location location;

    private final int config;

    private final int delay;

    private final String name;

    /**
     * Sail.
     *
     * @param player the player
     * @param ship   the ship
     */
    public static void sail(final Player player, final Ships ship) {
        player.getPulseManager().run(new ShipTravelPulse(player, ship));
    }

    /**
     * Sail.
     *
     * @param player the player
     */
    public void sail(final Player player) {
        player.getPulseManager().run(new ShipTravelPulse(player, this));
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Gets config.
     *
     * @return the config
     */
    public int getConfig() {
        return config;
    }

    /**
     * Gets delay.
     *
     * @return the delay
     */
    public int getDelay() {
        return delay;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

}