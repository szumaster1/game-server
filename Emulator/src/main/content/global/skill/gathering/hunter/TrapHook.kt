package content.global.skill.gathering.hunter

import core.game.world.GameWorld.settings
import core.game.world.map.Location
import core.tools.RandomFunction

/**
 * Represents the trap hook data.
 *
 * @param wrapper       the wrapper of the hook.
 * @param locations     the locations for the trap to trigger.
 */
class TrapHook(val wrapper: TrapWrapper, val locations: Array<Location>) {

    /**
     * Gets a location by chance for the npc to go to.
     *
     * @return the location.
     */
    val chanceLocation: Location?
        get() {
            val chance = wrapper.chanceRate
            val roll = RandomFunction.random(99)
            val successChance = (if (settings!!.isDevMode) 100.0 else 55.0) + chance
            if (successChance > roll) {
                return RandomFunction.getRandomElement(locations)
            }
            return null
        }

    /**
     * Checks if the trap is hooked.
     *
     * @param location the location.
     * @return {@code True} if hooked.
     */
    fun isHooked(location: Location): Boolean {
        for (l in locations) {
            if (l == location) {
                return true
            }
        }
        return false
    }
}
