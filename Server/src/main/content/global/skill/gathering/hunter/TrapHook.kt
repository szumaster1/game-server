package content.global.skill.gathering.hunter

import core.game.world.GameWorld.settings
import core.game.world.map.Location
import core.tools.RandomFunction

/**
 * Trap hook
 *
 * @property wrapper
 * @property locations
 * @constructor Trap hook
 */
class TrapHook(val wrapper: TrapWrapper, val locations: Array<Location>) {

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
     * Is hooked
     *
     * @param location
     * @return
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
