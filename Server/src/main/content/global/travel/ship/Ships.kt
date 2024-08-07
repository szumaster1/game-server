package content.global.travel.ship

import core.game.node.entity.player.Player
import core.game.world.map.Location

/**
 * Ships
 *
 * @property location
 * @property config
 * @property delay
 * @property destination
 * @constructor Ships
 */
enum class Ships(
    @JvmField val location: Location,
    val config: Int,
    val delay: Int,
    val destination: String
) {
    /**
     * Port Sarim To Entrana
     *
     * @constructor Port Sarim To Entrana
     */
    PORT_SARIM_TO_ENTRANA(
        location = Location.create(2834, 3331, 1),
        config = 1,
        delay = 15,
        destination = "Entrana"
    ),

    /**
     * Entrana To Port Sarim
     *
     * @constructor Entrana To Port Sarim
     */
    ENTRANA_TO_PORT_SARIM(
        location = Location.create(3048, 3234, 0),
        config = 2,
        delay = 15,
        destination = "Port Sarim"
    ),

    /**
     * Port Sarim To Crandor
     *
     * @constructor Port Sarim To Crandor
     */
    PORT_SARIM_TO_CRANDOR(
        location = Location.create(2849, 3238, 0),
        config = 3,
        delay = 12,
        destination = "Crandor"
    ),

    /**
     * Crandor To Port Sarim
     *
     * @constructor Crandor To Port Sarim
     */
    CRANDOR_TO_PORT_SARIM(
        location = Location.create(2834, 3335, 0),
        config = 4,
        delay = 13,
        destination = "Port Sarim"
    ),

    /**
     * Port Sarim To Karamaja
     *
     * @constructor Port Sarim To Karamaja
     */
    PORT_SARIM_TO_KARAMAJA(
        location = Location.create(2956, 3143, 1),
        config = 5,
        delay = 9,
        destination = "Karamja"
    ),

    /**
     * Karamjama To Port Sarim
     *
     * @constructor Karamjama To Port Sarim
     */
    KARAMJAMA_TO_PORT_SARIM(
        location = Location.create(3029, 3217, 0),
        config = 6,
        delay = 8,
        destination = "Port Sarim"
    ),

    /**
     * Ardougne To Brimhaven
     *
     * @constructor Ardougne To Brimhaven
     */
    ARDOUGNE_TO_BRIMHAVEN(
        location = Location.create(2775, 3234, 1),
        config = 7,
        delay = 4,
        destination = "Brimhaven"
    ),

    /**
     * Brimhaven To Ardougne
     *
     * @constructor Brimhaven To Ardougne
     */
    BRIMHAVEN_TO_ARDOUGNE(
        location = Location.create(2683, 3268, 1),
        config = 8,
        delay = 4,
        destination = "Ardougne"
    ),

    /**
     * Cairn Island To Port Khazard
     *
     * @constructor Cairn Island To Port Khazard
     */
    CAIRN_ISLAND_TO_PORT_KHAZARD(
        location = Location.create(2676, 3170, 0),
        config = 10,
        delay = 8,
        destination = "Port Khazard"
    ),

    /**
     * Port Khazard To Ship Yard
     *
     * @constructor Port Khazard To Ship Yard
     */
    PORT_KHAZARD_TO_SHIP_YARD(
        location = Location.create(2998, 3043, 0),
        config = 11,
        delay = 23,
        destination = "the Ship Yard"
    ),

    /**
     * Ship Yard To Port Khazard
     *
     * @constructor Ship Yard To Port Khazard
     */
    SHIP_YARD_TO_PORT_KHAZARD(
        location = Location.create(2676, 3170, 0),
        config = 12,
        delay = 23,
        destination = "Port Khazard"
    ),

    /**
     * Port Sarim
     *
     * @constructor Port Sarim
     */
    PORT_SARIM(
        location = Location.create(3048, 3234, 0),
        config = 13,
        delay = 17,
        destination = "Port Sarim"
    ),

    /**
     * Port Sarim To Pest Control
     *
     * @constructor Port Sarim To Pest Control
     */
    PORT_SARIM_TO_PEST_CONTROL(
        location = Location.create(2663, 2676, 1),
        config = 14,
        delay = 12,
        destination = "Pest Control"
    ),

    /**
     * Pest To Port Sarim
     *
     * @constructor Pest To Port Sarim
     */
    PEST_TO_PORT_SARIM(
        location = Location.create(3041, 3198, 1),
        config = 15,
        delay = 12,
        destination = "Port Sarim"
    ),

    /**
     * Feldip To Karamja
     *
     * @constructor Feldip To Karamja
     */
    FELDIP_TO_KARAMJA(
        location = Location.create(2763, 2956, 0),
        config = 16,
        delay = 10,
        destination = "Karamja"
    ),

    /**
     * Karamja To Feldip
     *
     * @constructor Karamja To Feldip
     */
    KARAMJA_TO_FELDIP(
        location = Location.create(2763, 2956, 0),
        config = 17,
        delay = 10,
        destination = "Feldip"
    );

    /**
     * Sail
     *
     * @param player
     */
    fun sail(player: Player) {
        player.pulseManager.run(ShipTravelPulse(player, this))
    }

    companion object {
        fun sail(player: Player, ship: Ships) {
            player.pulseManager.run(ShipTravelPulse(player, ship))
        }
    }
}
