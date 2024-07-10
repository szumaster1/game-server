package core.game.global.action

import core.api.finishDiaryTask
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.world.map.Location
import java.util.*

enum class SpecialLadders(private val ladderLoc: Location, private val destLoc: Location) : LadderAchievementCheck {
    ALKHARID_CRAFTING_DOWN(Location.create(3314, 3187, 1), Location.create(3310, 3187, 0)),
    ALKHARID_CRAFTING_UP(Location.create(3311, 3187, 0), Location.create(3314, 3187, 1)),
    ALKHARID_SOCRCERESS_DOWN(Location.create(3325, 3139, 1), Location.create(3325, 3143, 0)),
    ALKHARID_SOCRCERESS_UP(Location.create(3325, 3142, 0), Location.create(3325, 3139, 1)),
    ALKHARID_ZEKE_DOWN(Location.create(3284, 3190, 1), Location.create(3284, 3186, 0)),
    ALKHARID_ZEKE_UP(Location.create(3284, 3186, 0), Location.create(3284, 3190, 1)),

    BEAR_CAGE_DOWN(Location.create(3230, 3508, 0), Location.create(3229, 9904, 0)),
    BEAR_CAGE_UP(Location.create(3230, 9904, 0), Location.create(3231, 3504, 0)),

    CATHERBY_BLACK_DRAGONS_LADDER_UP(Location.create(2841, 9824, 0), Location.create(2842, 3423, 0)),

    TRAVERLEY_DUNGEON_LADDER_UP(Location.create(2884, 9797, 0), Location.create(2884, 3398, 0)),

    CASTLEWARS_SARADOMIN_MAIN_FLOOR_STAIRS_DOWN(Location.create(2419, 3080, 1), Location.create(2419, 3077, 0)),
    CASTLEWARS_SARADOMIN_MAIN_FLOOR_STAIRS_UP(Location.create(2428, 3081, 1), Location.create(2430, 3080, 2)),
    CASTLEWARS_SARADOMIN_OUTER_WALL_STAIRS_DOWN(Location.create(2417, 3075, 0), Location.create(2417, 3078, 0)),
    CASTLEWARS_SARADOMIN_OUTER_WALL_STAIRS_UP(Location.create(2417, 3077, 0), Location.create(2416, 3075, 0)),
    CASTLEWARS_ZAMORAK_MAIN_FLOOR_STAIRS_UP(Location.create(2380, 3129, 0), Location.create(2379, 3127, 1)),
    CASTLEWARS_ZAMORAK_OUTERWALL_STAIRS_UP(Location.create(2382, 3130, 0), Location.create(2383, 3132, 0)),
    CASTLEWARS_ZAMORAK_TOP_FLOOR_DOWN(Location.create(2374, 3133, 3), Location.create(2374, 3130, 2)),
    CASTLEWARS_ZAMOUTER_WALL_STAIRS_DOWN(Location.create(2382, 3132, 0), Location.create(2382, 3129, 0)),
    CLOCKTOWER_HIDDEN_LADDER(Location.create(2572, 9631, 0), Location.create(2572, 3230, 0)),

    DRAYNOR_SEWER_NORTHWEST_DOWN(Location.create(3084, 3272, 0), Location.create(3085, 9672, 0)),
    DRAYNOR_SEWER_NORTHWEST_UP(Location.create(3084, 9672, 0), Location.create(3084, 3271, 0)),
    DRAYNOR_SEWER_SOUTHEAST_DOWN(Location.create(3118, 3244, 0), Location.create(3118, 9643, 0)),
    DRAYNOR_SEWER_SOUTHEAST_UP(Location.create(3118, 9643, 0), Location.create(3118, 3243, 0)),

    ENTRANA_GLASSBLOWING_PIPE_HOUSE_DOWN(Location.create(2816, 3352, 1), Location.create(2817, 3352, 0)),

    EDGEVILLE_CLIMB_UP(Location.create(3097, 9867, 0), Location.create(3096, 3468, 0)),

    FISHING_GUILD_HOUSE_UP(Location.create(2615, 3394, 0), Location.create(2615, 3395, 1)),
    FISHING_GUILD_HOUSE_DOWN(Location.create(2615, 3394, 1), Location.create(2615, 3395, 0)),

    FOG_ENTER(Location.create(3240, 3575, 0), Location.create(1675, 5599, 0)),
    FOG_LEAVE(Location.create(1673, 5598, 0), Location.create(3242, 3574, 0)),

    GEM_MINE(Location.create(2821, 2996, 0), Location.create(2838, 9387, 0)),
    GEM_MINE_UP(Location.create(2838, 9388, 0), Location.create(2820, 2996, 0)),

    GLARIAL_EXIT(Location.create(2556, 9844, 0), Location.create(2557, 3444, 0)),

    HADLEY_HOUSE_STAIRSCASE_UP(Location.create(2517, 3429, 0), Location.create(2518, 3431, 1)),

    HAM_STORAGE_UP(Location.create(2567, 5185, 0), Location.create(3166, 9623, 0)),

    HEROES_GUILD_LADDER_UP(Location.create(2890, 3508, 1), Location.create(2890, 3507, 2)),
    HEROES_GUILD_STAIRCASE_UP(Location.create(2895, 3513, 0), Location.create(2897, 3513, 1)),

    HORROR_FROM_THE_DEEP_BASEMENT_UP(Location.create(2519, 4618, 1), Location.create(2510, 3644, 0)),
    HORROR_FROM_THE_DEEP_BASEMENT_AFTER_QUEST_UP(Location.create(2519, 9994, 1), Location.create(2510, 3644, 0)),

    INTRO_LEAVE(Location.create(2522, 4999, 0), Location.create(3230, 3240, 0)),

    JATIZSO_MINE_DOWN(Location.create(2397, 3812, 0), Location.create(2405, 10188, 0)),
    JATIZSO_MINE_UP(Location.create(2406, 10188, 0), Location.create(2397, 3811, 0)),
    JATIZSO_SHOUT_TOWER_DOWN(Location.create(2373, 3800, 0), Location.create(2374, 3800, 2)),
    JATIZSO_SHOUT_TOWER_UP(Location.create(2373, 3800, 2), Location.create(2374, 3800, 0)),

    KELDAGRIM_LIBRARY_UP(Location.create(2865, 10222, 0), Location.create(2865, 10224, 1)),

    MOVARIO_LADDER_UP(Location.create(2036, 4379, 0), Location.create(2502, 3255, 0)),

    OBSERVATORY_STAIRS_CLIMB_DOWN(Location.create(2443, 3159, 1), Location.create(2444, 3162, 0)),
    OBSERVATORY_STAIRS_CLIMB_UP(Location.create(2444, 3159, 0), Location.create(2442, 3159, 1)),

    OURANIA_LADDER_CLIMB_UP(Location.create(3271, 4862, 0), Location.create(2452, 3230, 0)),
    OURANIA_LADDER_CLIMB_DOWN(Location.create(2452, 3231, 0), Location.create(3271, 4862, 0)),

    PATERDOMUS_TEMPLE_STAIRCASE_NORTH_UP(Location.create(3415, 3490, 0), Location.create(3415, 3491, 1)),
    PATERDOMUS_TEMPLE_STAIRCASE_NORTH_DOWN(Location.create(3415, 3491, 1), Location.create(3414, 3491, 0)),
    PATERDOMUS_TEMPLE_STAIRCASE_SOUTH_DOWN(Location.create(3415, 3486, 1), Location.create(3414, 3486, 0)),

    PHASMATYS_BAR_DOWN(Location.create(3681, 3498, 0), Location.create(3682, 9961, 0)),
    PHASMATYS_BAR_UP(Location.create(3682, 9962, 0), Location.create(3681, 3497, 0)),

    PIRATES_COVE_SHIP_CENTER_STAIRS_DOWN(Location.create(2225, 3807, 1), Location.create(2225, 3809, 0)),
    PIRATES_COVE_SHIP_CENTER_STAIRS_UP(Location.create(2225, 3808, 0), Location.create(2225, 3806, 1)),
    PIRATES_COVE_SHIP_NORTH_EAST_STAIRS_DOWN(Location.create(2227, 3806, 3), Location.create(2227, 3805, 2)),
    PIRATES_COVE_SHIP_NORTH_EAST_STAIRS_UP(Location.create(2227, 3806, 2), Location.create(2227, 3808, 3)),
    PIRATES_COVE_SHIP_NORTH_WEST_STAIRS_DOWN(Location.create(2221, 3806, 3), Location.create(2221, 3805, 2)),
    PIRATES_COVE_SHIP_NORTH_WEST_STAIRS_UP(Location.create(2221, 3806, 2), Location.create(2221, 3808, 3)),
    PIRATES_COVE_SHIP_SOUTH_EAST_STAIRS_DOWN(Location.create(2227, 3793, 3), Location.create(2227, 3795, 2)),
    PIRATES_COVE_SHIP_SOUTH_EAST_STAIRS_UP(Location.create(2227, 3794, 2), Location.create(2227, 3792, 3)),
    PIRATES_COVE_SHIP_SOUTH_LEFT_STAIRS_DOWN(Location.create(2221, 3792, 2), Location.create(2223, 3792, 1)),
    PIRATES_COVE_SHIP_SOUTH_LEFT_STAIRS_UP(Location.create(2222, 3792, 1), Location.create(2220, 3792, 2)),
    PIRATES_COVE_SHIP_SOUTH_RIGHT_STAIRS_DOWN(Location.create(2226, 3792, 2), Location.create(2225, 3792, 1)),
    PIRATES_COVE_SHIP_SOUTH_RIGHT_STAIRS_UP(Location.create(2226, 3792, 1), Location.create(2228, 3792, 2)),
    PIRATES_COVE_SHIP_SOUTH_WEST_STAIRS_DOWN(Location.create(2221, 3793, 3), Location.create(2221, 3795, 2)),
    PIRATES_COVE_SHIP_SOUTH_WEST_STAIRS_UP(Location.create(2221, 3794, 2), Location.create(2221, 3792, 3)),

    PORT_SARIM_RAT_PITS_UP(Location.create(2962, 9651, 0), Location.create(3018, 3231, 0)),
    PORT_SARIM_RAT_PITS_DOWN(Location.create(3018, 3232, 0), Location.create(2962, 9650, 0)) {
        override fun checkAchievement(player: Player) {
            finishDiaryTask(player, DiaryType.FALADOR, 1, 11)
        }
    },

    SEERS_VILLAGE_SPINNING_HOUSE_ROOFTOP_DOWN(Location.create(2715, 3472, 3), Location.create(2714, 3472, 1)),
    SEERS_VILLAGE_SPINNING_HOUSE_ROOFTOP_UP(Location.create(2715, 3472, 1), Location.create(2714, 3472, 3)) {
        override fun checkAchievement(player: Player) {
            finishDiaryTask(player, DiaryType.SEERS_VILLAGE, 1, 3)
        }
    },

    SWENSEN_DOWN(Location.create(2644, 3657, 0), Location.create(2631, 10006, 0)),
    SWENSEN_UP(Location.create(2665, 10037, 0), Location.create(2649, 3661, 0)),

    TREE_GNOME_STRONGHOLD_WEST_BAR_STAIRS_DOWN(Location.create(2418, 3491, 1), Location.create(2419, 3491, 0)),
    TREE_GNOME_STRONGHOLD_WEST_BAR_STAIRS_UP(Location.create(2417, 3490, 0), Location.create(2418, 3492, 1)),

    WATERBIRTH_ISLAND_DUNGEON_SUBLEVEL_2_WALLASALKI_3_LADDER_DOWN(Location.create(1799, 4387, 2), Location.create(1799, 4388, 1)),
    WATERBIRTH_LADDER_WAY_TO_LIGHTHOUSE_1_UP(Location.create(1932, 4378, 0), Location.create(1932, 4380, 2)),
    WATERBIRTH_LADDER_WAY_TO_LIGHTHOUSE_2_UP(Location.create(1961, 4391, 2), Location.create(1961, 4393, 3)),
    WATERBIRTH_LADDER_WAY_TO_LIGHTHOUSE_3_UP(Location.create(1975, 4408, 3), Location.create(2510, 3644, 0)),

    WEREWOLF_AGILITY_COURSE_LADDER_UP(Location.create(3549, 9864, 0), Location.create(3543, 3463, 0)),

    WITCH_HOUSE_TO_BASEMENT_DOWN(Location.create(2907, 3476, 0), Location.create(2906, 9876, 0)),
    WITCH_HOUSE_TO_BASEMENT_UP(Location.create(2907, 9876, 0), Location.create(2906, 3476, 0)),

    WIZARD_TOWER_LADDER_UP(Location.create(3103, 9576, 0), Location.create(3105, 3162, 0)),

    WHITE_WOLF_MOUNTAIN_FAKE_LADDER_1_UP(Location.create(2837, 9927, 0), Location.create(2837, 3527, 0)),
    WHITE_WOLF_MOUNTAIN_FAKE_LADDER_2_UP(Location.create(2823, 9930, 0), Location.create(2823, 3529, 0)),

    DRAYNOR_MANOR_LADDER_DOWN(Location.create(3092, 3362, 0), Location.create(3117, 9753, 0)),
    DRAYNOR_MANOR_LADDER_UP(Location.create(3117, 9754, 0), Location.create(3092, 3361, 0)),

    VARROCK_HOUSE_UP(Location(3230,3383,0), Location(3230, 3382, 1)),
    VARROCK_HOUSE_DOWN(Location(3230, 3383, 1), Location(3230, 3386, 0)),

    VARROCK_SEWERS_DOWN(Location(3237, 3458,0), Location(3237, 9858, 0)),
    VARROCK_SEWERS_UP(Location(3237,9858,0), Location.create(3238, 3458, 0))
    ;

    companion object {
        private val destinationMap = HashMap<Location, Location>()
        private val ladderMap = HashMap<Location, SpecialLadders>()

        init {
            Arrays.stream(values()).forEach { entry: SpecialLadders ->
                destinationMap.putIfAbsent(entry.ladderLoc, entry.destLoc)
            }
            Arrays.stream(values()).forEach { entry: SpecialLadders ->
                ladderMap.putIfAbsent(entry.ladderLoc, entry)
            }
        }

        fun add(from: Location, to: Location) {
            destinationMap[from] = to
        }

        @JvmStatic
        fun getDestination(loc: Location): Location? {
            return destinationMap[loc]
        }

        @JvmStatic
        fun getSpecialLadder(loc: Location): SpecialLadders? {
            return ladderMap[loc]
        }
    }
}
