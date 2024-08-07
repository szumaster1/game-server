package content.minigame.bountyhunter

import core.game.node.entity.player.Player
import core.game.world.map.Location
import core.game.world.map.zone.ZoneBorders

/**
 * Represents the crater types.
 * @author Emperor
 *
 * @property level The level of the crater type
 * @property roomLocation The location of the room
 * @property craterLocation The location of the crater
 * @property exitLocation The location of the exit
 * @property zone The zone borders of the crater
 * @constructor Creates a new CraterType
 */
enum class CraterType(
    val level: Int,
    val roomLocation: Location,
    val craterLocation: Location,
    val exitLocation: Location,
    val zone: ZoneBorders
) {
    /**
     * Low Level
     *
     * @constructor Creates a new Low Level CraterType
     */
    LOW_LEVEL(
        3,
        Location.create(1548, 5804, 0),
        Location.create(1548, 5804, 0),
        Location.create(3152, 3672, 0),
        ZoneBorders(2688, 5632, 2879, 5823)
    ),

    /**
     * Mid Level
     *
     * @constructor Creates a new Mid Level CraterType
     */
    MID_LEVEL(
        50,
        Location.create(1558, 5785, 0),
        Location.create(1548, 5804, 0),
        Location.create(3158, 3680, 0),
        ZoneBorders(2944, 5632, 3135, 5823)
    ),

    /**
     * High Level
     *
     * @constructor Creates a new High Level CraterType
     */
    HIGH_LEVEL(
        95,
        Location.create(1570, 5804, 0),
        Location.create(1548, 5804, 0),
        Location.create(3164, 3685, 0),
        ZoneBorders(3200, 5632, 3391, 5823)
    );


    /**
     * Checks if the player can enter the crater
     *
     * @param player The player to check
     * @return True if the player can enter, false otherwise
     */
    fun canEnter(player: Player): Boolean {
        val combatLevel = player.properties.currentCombatLevel
        if (player.ironmanManager.checkRestriction()) {
            return false
        }
        if (ordinal < CraterType.values().size - 1) {
            if (combatLevel > CraterType.values()[ordinal + 1].level + 5) {
                player.packetDispatch.sendMessage("Your combat level has to be below " + (CraterType.values()[ordinal + 1].level + 5) + " to enter this crater.")
                return false
            }
        }
        if (combatLevel < level) {
            player.packetDispatch.sendMessage("You need a combat level of $level to enter this crater.")
            return false
        }
        return true
    }
}
