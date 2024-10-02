package content.minigame.allfiredup

import core.api.getVarbit
import core.api.log
import core.api.setVarbit
import core.game.node.entity.player.Player
import core.game.world.map.Location
import core.tools.Log

/**
 * Various data for beacons.
 * @author Ceikry
 */
enum class AFUBeacon(val title: String, val fmLevel: Int, val varbit: Int, val location: Location, val experience: Double, val keeper: Int = 0) {
    RIVER_SALVE(
        title = "",
        fmLevel = 43,
        varbit = 5146,
        location = Location.create(3396, 3464, 0),
        experience = 216.2,
        keeper = 8065
    ),
    RAG_AND_BONE(
        title = "",
        fmLevel = 43,
        varbit = 5147,
        location = Location.create(3343, 3510, 0),
        experience = 235.8,
        keeper = 8066
    ),
    JOLLY_BOAR(
        title = "",
        fmLevel = 48,
        varbit = 5148,
        location = Location.create(3278, 3525, 0),
        experience = 193.8,
        keeper = 8067
    ),
    NORTH_VARROCK_CASTLE(
        title = "",
        fmLevel = 53,
        varbit = 5149,
        location = Location.create(3236, 3527, 0),
        experience = 178.5,
        keeper = 8068
    ),
    GRAND_EXCHANGE(
        title = "",
        fmLevel = 59,
        varbit = 5150,
        location = Location.create(3170, 3536, 0),
        experience = 194.3,
        keeper = 8069
    ),
    EDGEVILLE(
        title = "",
        fmLevel = 62,
        varbit = 5151,
        location = Location.create(3087, 3516, 0),
        experience = 86.7,
        keeper = 8070
    ),
    MONASTERY(
        title = "",
        fmLevel = 68,
        varbit = 5152,
        location = Location.create(3034, 3518, 0),
        experience = 224.4,
        keeper = 8071
    ),
    GOBLIN_VILLAGE(
        title = "",
        fmLevel = 72,
        varbit = 5153,
        location = Location.create(2968, 3516, 0),
        experience = 194.8,
        keeper = 8072
    ),
    BURTHORPE(
        title = "",
        fmLevel = 76,
        varbit = 5154,
        location = Location.create(2940, 3565, 0),
        experience = 195.3,
        keeper = 8073
    ),
    DEATH_PLATEAU(
        title = "",
        fmLevel = 79,
        varbit = 5155,
        location = Location.create(2944, 3622, 0),
        experience = 249.9,
        keeper = 8074
    ),
    TROLLHEIM(
        title = "",
        fmLevel = 83,
        varbit = 5156,
        location = Location.create(2939, 3680, 0),
        experience = 201.0,
        keeper = 8075
    ),
    GWD(
        title = "",
        fmLevel = 87,
        varbit = 5157,
        location = Location.create(2937, 3773, 0),
        experience = 255.0,
        keeper = 8076
    ),
    TEMPLE(
        title = "",
        fmLevel = 89,
        varbit = 5158,
        location = Location.create(2946, 3836, 0),
        experience = 198.9
    ),
    PLATEAU(
        title = "",
        fmLevel = 92,
        varbit = 5159,
        location = Location.create(2964, 3931, 0),
        experience = 147.9
    );

    companion object {
        /*
         * Returns the AFUBeacon object for a given location.
         */
        fun forLocation(location: Location): AFUBeacon {
            for (beacon in values()) {
                if (beacon.location == location) return beacon
            }
            return RIVER_SALVE.also {
                log(this::class.java, Log.WARN, "Unhandled Beacon Location $location")
            }
        }

        /*
         * Resets all the beacons for a given player.
         */
        fun resetAllBeacons(player: Player) {
            for (beacon in values()) {
                setVarbit(player, beacon.varbit, 0)
            }
        }
    }

    /**
     * Light.
     *
     * @param player The player object.
     */
    fun light(player: Player) {
        setVarbit(player, varbit, 2)
    }

    /**
     * Diminish.
     *
     * @param player The player object.
     */
    fun diminish(player: Player) {
        setVarbit(player, varbit, 3)
    }

    /**
     * Extinguish.
     *
     * @param player The player object.
     */
    fun extinguish(player: Player) {
        setVarbit(player, varbit, 0)
    }

    /**
     * Light gnomish.
     *
     * @param player The player object.
     */
    fun lightGnomish(player: Player) {
        setVarbit(player, varbit, 4)
    }

    /**
     * Fill with logs.
     *
     * @param player The player object.
     */
    fun fillWithLogs(player: Player) {
        setVarbit(player, varbit, 1, true)
    }

    /**
     * Get state.
     *
     * @param player The player object.
     * @return The BeaconState object.
     */
    fun getState(player: Player): BeaconState {
        return BeaconState.values()[getVarbit(player, varbit)]
    }
}

/**
 * Beacon state.
 */
enum class BeaconState {
    EMPTY,
    FILLED,
    LIT,
    DYING,
    WARNING
}
