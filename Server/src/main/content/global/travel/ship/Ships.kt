package content.global.travel.ship

import core.game.node.entity.player.Player
import core.game.world.map.Location


enum class Ships(
    @JvmField val location: Location,
    val config: Int,
    val delay: Int,
    name: String
) {
    PORT_SARIM_TO_ENTRANA(Location.create(2834, 3331, 1), 1, 15, "Entrana"),
    ENTRANA_TO_PORT_SARIM(Location.create(3048, 3234, 0), 2, 15, "Port Sarim"),
    PORT_SARIM_TO_CRANDOR(Location.create(2849, 3238, 0), 3, 12, "Crandor"),
    CRANDOR_TO_PORT_SARIM(Location.create(2834, 3335, 0), 4, 13, "Port Sarim"),
    PORT_SARIM_TO_KARAMAJA(Location.create(2956, 3143, 1), 5, 9, "Karamja"),
    KARAMJAMA_TO_PORT_SARIM(Location.create(3029, 3217, 0), 6, 8, "Port Sarim"),
    ARDOUGNE_TO_BRIMHAVEN(Location.create(2775, 3234, 1), 7, 4, "Brimhaven"),
    BRIMHAVEN_TO_ARDOUGNE(Location.create(2683, 3268, 1), 8, 4, "Ardougne"),
    CAIRN_ISLAND_TO_PORT_KHAZARD(Location.create(2676, 3170, 0), 10, 8, "Port Khazard"),
    PORT_KHAZARD_TO_SHIP_YARD(Location.create(2998, 3043, 0), 11, 23, "the Ship Yard"),
    SHIP_YARD_TO_PORT_KHAZARD(Location.create(2676, 3170, 0), 12, 23, "Port Khazard"),
    CAIRN_ISLAND_TO_PORT_SARIM(Location.create(3048, 3234, 0), 13, 17, "Port Sarim"),
    PORT_SARIM_TO_PEST_CONTROL(Location.create(2663, 2676, 1), 14, 12, "Pest Control"),
    PEST_TO_PORT_SARIM(Location.create(3041, 3198, 1), 15, 12, "Port Sarim"),
    FELDIP_TO_KARAMJA(Location.create(2763, 2956, 0), 16, 10, "Karamja"),
    KARAMJA_TO_FELDIP(Location.create(2763, 2956, 0), 17, 10, "Feldip");

    fun sail(player: Player) {
        player.pulseManager.run(ShipTravelPulse(player, this))
    }

    companion object {
        fun sail(player: Player, ship: Ships) {
            player.pulseManager.run(ShipTravelPulse(player, ship))
        }
    }
}
