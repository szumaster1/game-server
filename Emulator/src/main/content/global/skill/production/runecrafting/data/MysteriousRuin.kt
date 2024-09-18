package content.global.skill.production.runecrafting.data

import core.game.node.scenery.Scenery
import core.game.world.map.Location

/**
 * Represents a mysterious ruin.
 */
enum class MysteriousRuin(
    val objects: IntArray,
    val base: Location,
    val end: Location,
    val talisman: Talisman,
    val tiara: Tiara
) {
    AIR(
        objects = intArrayOf(2452, 7103, 7104),
        base = Location.create(2983, 3292, 0),
        end = Location.create(2841, 4829, 0),
        talisman = Talisman.AIR,
        tiara = Tiara.AIR
    ),
    MIND(
        objects = intArrayOf(2453, 7105, 7106),
        base = Location.create(2980, 3514, 0),
        end = Location.create(2793, 4828, 0),
        talisman = Talisman.MIND,
        tiara = Tiara.MIND
    ),
    WATER(
        objects = intArrayOf(2454, 7107, 7108),
        base = Location.create(3183, 3163, 0),
        end = Location.create(3494, 4832, 0),
        talisman = Talisman.WATER,
        tiara = Tiara.WATER
    ),
    EARTH(
        objects = intArrayOf(2455, 7109, 7110),
        base = Location.create(3304, 3475, 0),
        end = Location.create(2655, 4830, 0),
        talisman = Talisman.EARTH,
        tiara = Tiara.EARTH
    ),
    FIRE(
        objects = intArrayOf(2456, 7111, 7112),
        base = Location.create(3312, 3253, 0),
        end = Location.create(2576, 4845, 0),
        talisman = Talisman.FIRE,
        tiara = Tiara.FIRE
    ),
    BODY(
        objects = intArrayOf(2457, 7113, 7114),
        base = Location.create(3051, 3443, 0),
        end = Location.create(2521, 4834, 0),
        talisman = Talisman.BODY,
        tiara = Tiara.BODY
    ),
    COSMIC(
        objects = intArrayOf(2458, 7115, 7116),
        base = Location.create(2407, 4375, 0),
        end = Location.create(2162, 4833, 0),
        talisman = Talisman.COSMIC,
        tiara = Tiara.COSMIC
    ),
    CHAOS(
        objects = intArrayOf(2461, 7121, 7122),
        base = Location.create(3059, 3589, 0),
        end = Location.create(2281, 4837, 0),
        talisman = Talisman.CHAOS,
        tiara = Tiara.CHAOS
    ),
    NATURE(
        objects = intArrayOf(2460, 7119, 7120),
        base = Location.create(2869, 3021, 0),
        end = Location.create(2400, 4835, 0),
        talisman = Talisman.NATURE,
        tiara = Tiara.NATURE
    ),
    LAW(
        objects = intArrayOf(2459, 7117, 7118),
        base = Location.create(2857, 3379, 0),
        end = Location.create(2464, 4819, 0),
        talisman = Talisman.LAW,
        tiara = Tiara.LAW
    ),
    DEATH(
        objects = intArrayOf(2462, 7123, 7124),
        base = Location.create(1862, 4639, 0),
        end = Location.create(2208, 4830, 0),
        talisman = Talisman.DEATH,
        tiara = Tiara.DEATH
    ),
    BLOOD(
        objects = intArrayOf(2464, 30529, 30530),
        base = Location.create(3561, 9779, 0),
        end = Location.create(2468, 4889, 1),
        talisman = Talisman.BLOOD,
        tiara = Tiara.BLOOD
    );

    /**
     * Gets the talisman.
     * @return The talisman.
     */
    fun getTalisman(): Talisman {
        for (talisman in Talisman.values()) {
            if (talisman.name == name) {
                return talisman
            }
        }
        return talisman
    }

    /**
     * Gets the tiara.
     * @return The tiara.
     */
    fun getTiara(): Tiara {
        for (tiara in Tiara.values()) {
            if (tiara.name == name) {
                return tiara
            }
        }
        return tiara
    }

    companion object {
        /**
         * Method used to get the `MysteriousRuin` by the object.
         * @param object the object.
         * @return the `MysteriousRuin` or `Null`.
         */
        @JvmStatic
        fun forObject(`object`: Scenery): MysteriousRuin? {
            for (ruin in values()) {
                for (i in ruin.objects) {
                    if (i == `object`.id) {
                        return ruin
                    }
                }
            }
            return null
        }

        /**
         * Method used to get the `MysteriousRuin`
         * @param talisman the talisman.
         * @return the `MysteriousRuin`.
         */
        @JvmStatic
        fun forTalisman(talisman: Talisman): MysteriousRuin? {
            for (ruin in values()) {
                if (ruin.getTalisman() == talisman) {
                    return ruin
                }
            }
            return null
        }
    }
}
