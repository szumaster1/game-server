package core.game.global.action

import core.api.finishDiaryTask
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.world.map.Location
import java.util.*

/**
 * Special ladders
 *
 * @property ladderLoc
 * @property destLoc
 * @constructor Special ladders
 */
enum class SpecialLadders(private val ladderLoc: Location, private val destLoc: Location) : LadderAchievementCheck {
    /**
     * Alkharid Crafting Down
     *
     * @constructor Alkharid Crafting Down
     */
    ALKHARID_CRAFTING_DOWN(Location.create(3314, 3187, 1), Location.create(3310, 3187, 0)),

    /**
     * Alkharid Crafting Up
     *
     * @constructor Alkharid Crafting Up
     */
    ALKHARID_CRAFTING_UP(Location.create(3311, 3187, 0), Location.create(3314, 3187, 1)),

    /**
     * Alkharid Socrceress Down
     *
     * @constructor Alkharid Socrceress Down
     */
    ALKHARID_SOCRCERESS_DOWN(Location.create(3325, 3139, 1), Location.create(3325, 3143, 0)),

    /**
     * Alkharid Socrceress Up
     *
     * @constructor Alkharid Socrceress Up
     */
    ALKHARID_SOCRCERESS_UP(Location.create(3325, 3142, 0), Location.create(3325, 3139, 1)),

    /**
     * Alkharid Zeke Down
     *
     * @constructor Alkharid Zeke Down
     */
    ALKHARID_ZEKE_DOWN(Location.create(3284, 3190, 1), Location.create(3284, 3186, 0)),

    /**
     * Alkharid Zeke Up
     *
     * @constructor Alkharid Zeke Up
     */
    ALKHARID_ZEKE_UP(Location.create(3284, 3186, 0), Location.create(3284, 3190, 1)),

    /**
     * Bear Cage Down
     *
     * @constructor Bear Cage Down
     */
    BEAR_CAGE_DOWN(Location.create(3230, 3508, 0), Location.create(3229, 9904, 0)),

    /**
     * Bear Cage Up
     *
     * @constructor Bear Cage Up
     */
    BEAR_CAGE_UP(Location.create(3230, 9904, 0), Location.create(3231, 3504, 0)),

    /**
     * Catherby Black Dragons Ladder Up
     *
     * @constructor Catherby Black Dragons Ladder Up
     */
    CATHERBY_BLACK_DRAGONS_LADDER_UP(Location.create(2841, 9824, 0), Location.create(2842, 3423, 0)),

    /**
     * Traverley Dungeon Ladder Up
     *
     * @constructor Traverley Dungeon Ladder Up
     */
    TRAVERLEY_DUNGEON_LADDER_UP(Location.create(2884, 9797, 0), Location.create(2884, 3398, 0)),

    /**
     * Champion Challenge Ladders Up
     *
     * @constructor Champion Challenge Ladders Up
     */
    CHAMPION_CHALLENGE_LADDERS_UP(Location.create(3190,9758,0),Location.create(3191, 3355, 0)),

    /**
     * Champion Challenge Ladders Down
     *
     * @constructor Champion Challenge Ladders Down
     */
    CHAMPION_CHALLENGE_LADDERS_DOWN(Location.create(3190, 3355, 0), Location.create(3189, 9758, 0)),

    /**
     * Castlewars Saradomin Main Floor Stairs Down
     *
     * @constructor Castlewars Saradomin Main Floor Stairs Down
     */
    CASTLEWARS_SARADOMIN_MAIN_FLOOR_STAIRS_DOWN(Location.create(2419, 3080, 1), Location.create(2419, 3077, 0)),

    /**
     * Castlewars Saradomin Main Floor Stairs Up
     *
     * @constructor Castlewars Saradomin Main Floor Stairs Up
     */
    CASTLEWARS_SARADOMIN_MAIN_FLOOR_STAIRS_UP(Location.create(2428, 3081, 1), Location.create(2430, 3080, 2)),

    /**
     * Castlewars Saradomin Outer Wall Stairs Down
     *
     * @constructor Castlewars Saradomin Outer Wall Stairs Down
     */
    CASTLEWARS_SARADOMIN_OUTER_WALL_STAIRS_DOWN(Location.create(2417, 3075, 0), Location.create(2417, 3078, 0)),

    /**
     * Castlewars Saradomin Outer Wall Stairs Up
     *
     * @constructor Castlewars Saradomin Outer Wall Stairs Up
     */
    CASTLEWARS_SARADOMIN_OUTER_WALL_STAIRS_UP(Location.create(2417, 3077, 0), Location.create(2416, 3075, 0)),

    /**
     * Castlewars Zamorak Main Floor Stairs Up
     *
     * @constructor Castlewars Zamorak Main Floor Stairs Up
     */
    CASTLEWARS_ZAMORAK_MAIN_FLOOR_STAIRS_UP(Location.create(2380, 3129, 0), Location.create(2379, 3127, 1)),

    /**
     * Castlewars Zamorak Outerwall Stairs Up
     *
     * @constructor Castlewars Zamorak Outerwall Stairs Up
     */
    CASTLEWARS_ZAMORAK_OUTERWALL_STAIRS_UP(Location.create(2382, 3130, 0), Location.create(2383, 3132, 0)),

    /**
     * Castlewars Zamorak Top Floor Down
     *
     * @constructor Castlewars Zamorak Top Floor Down
     */
    CASTLEWARS_ZAMORAK_TOP_FLOOR_DOWN(Location.create(2374, 3133, 3), Location.create(2374, 3130, 2)),

    /**
     * Castlewars Zamouter Wall Stairs Down
     *
     * @constructor Castlewars Zamouter Wall Stairs Down
     */
    CASTLEWARS_ZAMOUTER_WALL_STAIRS_DOWN(Location.create(2382, 3132, 0), Location.create(2382, 3129, 0)),

    /**
     * Clocktower Hidden Ladder
     *
     * @constructor Clocktower Hidden Ladder
     */
    CLOCKTOWER_HIDDEN_LADDER(Location.create(2572, 9631, 0), Location.create(2572, 3230, 0)),

    /**
     * Draynor Sewer Northwest Down
     *
     * @constructor Draynor Sewer Northwest Down
     */
    DRAYNOR_SEWER_NORTHWEST_DOWN(Location.create(3084, 3272, 0), Location.create(3085, 9672, 0)),

    /**
     * Draynor Sewer Northwest Up
     *
     * @constructor Draynor Sewer Northwest Up
     */
    DRAYNOR_SEWER_NORTHWEST_UP(Location.create(3084, 9672, 0), Location.create(3084, 3271, 0)),

    /**
     * Draynor Sewer Southeast Down
     *
     * @constructor Draynor Sewer Southeast Down
     */
    DRAYNOR_SEWER_SOUTHEAST_DOWN(Location.create(3118, 3244, 0), Location.create(3118, 9643, 0)),

    /**
     * Draynor Sewer Southeast Up
     *
     * @constructor Draynor Sewer Southeast Up
     */
    DRAYNOR_SEWER_SOUTHEAST_UP(Location.create(3118, 9643, 0), Location.create(3118, 3243, 0)),

    /**
     * Entrana Glassblowing Pipe House Down
     *
     * @constructor Entrana Glassblowing Pipe House Down
     */
    ENTRANA_GLASSBLOWING_PIPE_HOUSE_DOWN(Location.create(2816, 3352, 1), Location.create(2817, 3352, 0)),

    /**
     * Edgeville Climb Up
     *
     * @constructor Edgeville Climb Up
     */
    EDGEVILLE_CLIMB_UP(Location.create(3097, 9867, 0), Location.create(3096, 3468, 0)),

    /**
     * Fishing Guild House Up
     *
     * @constructor Fishing Guild House Up
     */
    FISHING_GUILD_HOUSE_UP(Location.create(2615, 3394, 0), Location.create(2615, 3395, 1)),

    /**
     * Fishing Guild House Down
     *
     * @constructor Fishing Guild House Down
     */
    FISHING_GUILD_HOUSE_DOWN(Location.create(2615, 3394, 1), Location.create(2615, 3395, 0)),

    /**
     * Fog Enter
     *
     * @constructor Fog Enter
     */
    FOG_ENTER(Location.create(3240, 3575, 0), Location.create(1675, 5599, 0)),

    /**
     * Fog Leave
     *
     * @constructor Fog Leave
     */
    FOG_LEAVE(Location.create(1673, 5598, 0), Location.create(3242, 3574, 0)),

    /**
     * Gem Mine
     *
     * @constructor Gem Mine
     */
    GEM_MINE(Location.create(2821, 2996, 0), Location.create(2838, 9387, 0)),

    /**
     * Gem Mine Up
     *
     * @constructor Gem Mine Up
     */
    GEM_MINE_UP(Location.create(2838, 9388, 0), Location.create(2820, 2996, 0)),

    /**
     * Glarial Exit
     *
     * @constructor Glarial Exit
     */
    GLARIAL_EXIT(Location.create(2556, 9844, 0), Location.create(2557, 3444, 0)),

    /**
     * Hadley House Stairscase Up
     *
     * @constructor Hadley House Stairscase Up
     */
    HADLEY_HOUSE_STAIRSCASE_UP(Location.create(2517, 3429, 0), Location.create(2518, 3431, 1)),

    /**
     * Ham Storage Up
     *
     * @constructor Ham Storage Up
     */
    HAM_STORAGE_UP(Location.create(2567, 5185, 0), Location.create(3166, 9623, 0)),

    /**
     * Heroes Guild Ladder Up
     *
     * @constructor Heroes Guild Ladder Up
     */
    HEROES_GUILD_LADDER_UP(Location.create(2890, 3508, 1), Location.create(2890, 3507, 2)),

    /**
     * Heroes Guild Staircase Up
     *
     * @constructor Heroes Guild Staircase Up
     */
    HEROES_GUILD_STAIRCASE_UP(Location.create(2895, 3513, 0), Location.create(2897, 3513, 1)),

    /**
     * Horror From The Deep Basement Up
     *
     * @constructor Horror From The Deep Basement Up
     */
    HORROR_FROM_THE_DEEP_BASEMENT_UP(Location.create(2519, 4618, 1), Location.create(2510, 3644, 0)),

    /**
     * Horror From The Deep Basement After Quest Up
     *
     * @constructor Horror From The Deep Basement After Quest Up
     */
    HORROR_FROM_THE_DEEP_BASEMENT_AFTER_QUEST_UP(Location.create(2519, 9994, 1), Location.create(2510, 3644, 0)),

    /**
     * Jatizso Mine Down
     *
     * @constructor Jatizso Mine Down
     */
    JATIZSO_MINE_DOWN(Location.create(2397, 3812, 0), Location.create(2405, 10188, 0)),

    /**
     * Jatizso Mine Up
     *
     * @constructor Jatizso Mine Up
     */
    JATIZSO_MINE_UP(Location.create(2406, 10188, 0), Location.create(2397, 3811, 0)),

    /**
     * Jatizso Shout Tower Down
     *
     * @constructor Jatizso Shout Tower Down
     */
    JATIZSO_SHOUT_TOWER_DOWN(Location.create(2373, 3800, 2), Location.create(2374, 3800, 0)),

    /**
     * Jatizso Shout Tower Up
     *
     * @constructor Jatizso Shout Tower Up
     */
    JATIZSO_SHOUT_TOWER_UP(Location.create(2373, 3800, 0), Location.create(2374, 3800, 2)),

    /**
     * Keldagrim Library Up
     *
     * @constructor Keldagrim Library Up
     */
    KELDAGRIM_LIBRARY_UP(Location.create(2865, 10222, 0), Location.create(2865, 10224, 1)),

    /**
     * Lumbridge Learning The Ropes Down
     *
     * @constructor Lumbridge Learning The Ropes Down
     */
    LUMBRIDGE_LEARNING_THE_ROPES_DOWN(Location.create(3230,3241,0), Location.create(3290, 4936, 0)),

    /**
     * Lumbridge Learning The Ropes Up
     *
     * @constructor Lumbridge Learning The Ropes Up
     */
    LUMBRIDGE_LEARNING_THE_ROPES_UP(Location.create(3290, 4935, 0), Location.create(3230, 3240, 0)),

    /**
     * Movario Ladder Up
     *
     * @constructor Movario Ladder Up
     */
    MOVARIO_LADDER_UP(Location.create(2036, 4379, 0), Location.create(2502, 3255, 0)),

    /**
     * Observatory Stairs Climb Down
     *
     * @constructor Observatory Stairs Climb Down
     */
    OBSERVATORY_STAIRS_CLIMB_DOWN(Location.create(2443, 3159, 1), Location.create(2444, 3162, 0)),

    /**
     * Observatory Stairs Climb Up
     *
     * @constructor Observatory Stairs Climb Up
     */
    OBSERVATORY_STAIRS_CLIMB_UP(Location.create(2444, 3159, 0), Location.create(2442, 3159, 1)),

    /**
     * Ourania Ladder Climb Up
     *
     * @constructor Ourania Ladder Climb Up
     */
    OURANIA_LADDER_CLIMB_UP(Location.create(3271, 4862, 0), Location.create(2452, 3230, 0)),

    /**
     * Ourania Ladder Climb Down
     *
     * @constructor Ourania Ladder Climb Down
     */
    OURANIA_LADDER_CLIMB_DOWN(Location.create(2452, 3231, 0), Location.create(3271, 4862, 0)),

    /**
     * Paterdomus Temple Staircase North Up
     *
     * @constructor Paterdomus Temple Staircase North Up
     */
    PATERDOMUS_TEMPLE_STAIRCASE_NORTH_UP(Location.create(3415, 3490, 0), Location.create(3415, 3491, 1)),

    /**
     * Paterdomus Temple Staircase North Down
     *
     * @constructor Paterdomus Temple Staircase North Down
     */
    PATERDOMUS_TEMPLE_STAIRCASE_NORTH_DOWN(Location.create(3415, 3491, 1), Location.create(3414, 3491, 0)),

    /**
     * Paterdomus Temple Staircase South Down
     *
     * @constructor Paterdomus Temple Staircase South Down
     */
    PATERDOMUS_TEMPLE_STAIRCASE_SOUTH_DOWN(Location.create(3415, 3486, 1), Location.create(3414, 3486, 0)),

    /**
     * Phasmatys Bar Down
     *
     * @constructor Phasmatys Bar Down
     */
    PHASMATYS_BAR_DOWN(Location.create(3681, 3498, 0), Location.create(3682, 9961, 0)),

    /**
     * Phasmatys Bar Up
     *
     * @constructor Phasmatys Bar Up
     */
    PHASMATYS_BAR_UP(Location.create(3682, 9962, 0), Location.create(3681, 3497, 0)),

    /**
     * Pirates Cove Ship Center Stairs Down
     *
     * @constructor Pirates Cove Ship Center Stairs Down
     */
    PIRATES_COVE_SHIP_CENTER_STAIRS_DOWN(Location.create(2225, 3807, 1), Location.create(2225, 3809, 0)),

    /**
     * Pirates Cove Ship Center Stairs Up
     *
     * @constructor Pirates Cove Ship Center Stairs Up
     */
    PIRATES_COVE_SHIP_CENTER_STAIRS_UP(Location.create(2225, 3808, 0), Location.create(2225, 3806, 1)),

    /**
     * Pirates Cove Ship North East Stairs Down
     *
     * @constructor Pirates Cove Ship North East Stairs Down
     */
    PIRATES_COVE_SHIP_NORTH_EAST_STAIRS_DOWN(Location.create(2227, 3806, 3), Location.create(2227, 3805, 2)),

    /**
     * Pirates Cove Ship North East Stairs Up
     *
     * @constructor Pirates Cove Ship North East Stairs Up
     */
    PIRATES_COVE_SHIP_NORTH_EAST_STAIRS_UP(Location.create(2227, 3806, 2), Location.create(2227, 3808, 3)),

    /**
     * Pirates Cove Ship North West Stairs Down
     *
     * @constructor Pirates Cove Ship North West Stairs Down
     */
    PIRATES_COVE_SHIP_NORTH_WEST_STAIRS_DOWN(Location.create(2221, 3806, 3), Location.create(2221, 3805, 2)),

    /**
     * Pirates Cove Ship North West Stairs Up
     *
     * @constructor Pirates Cove Ship North West Stairs Up
     */
    PIRATES_COVE_SHIP_NORTH_WEST_STAIRS_UP(Location.create(2221, 3806, 2), Location.create(2221, 3808, 3)),

    /**
     * Pirates Cove Ship South East Stairs Down
     *
     * @constructor Pirates Cove Ship South East Stairs Down
     */
    PIRATES_COVE_SHIP_SOUTH_EAST_STAIRS_DOWN(Location.create(2227, 3793, 3), Location.create(2227, 3795, 2)),

    /**
     * Pirates Cove Ship South East Stairs Up
     *
     * @constructor Pirates Cove Ship South East Stairs Up
     */
    PIRATES_COVE_SHIP_SOUTH_EAST_STAIRS_UP(Location.create(2227, 3794, 2), Location.create(2227, 3792, 3)),

    /**
     * Pirates Cove Ship South Left Stairs Down
     *
     * @constructor Pirates Cove Ship South Left Stairs Down
     */
    PIRATES_COVE_SHIP_SOUTH_LEFT_STAIRS_DOWN(Location.create(2221, 3792, 2), Location.create(2223, 3792, 1)),

    /**
     * Pirates Cove Ship South Left Stairs Up
     *
     * @constructor Pirates Cove Ship South Left Stairs Up
     */
    PIRATES_COVE_SHIP_SOUTH_LEFT_STAIRS_UP(Location.create(2222, 3792, 1), Location.create(2220, 3792, 2)),

    /**
     * Pirates Cove Ship South Right Stairs Down
     *
     * @constructor Pirates Cove Ship South Right Stairs Down
     */
    PIRATES_COVE_SHIP_SOUTH_RIGHT_STAIRS_DOWN(Location.create(2226, 3792, 2), Location.create(2225, 3792, 1)),

    /**
     * Pirates Cove Ship South Right Stairs Up
     *
     * @constructor Pirates Cove Ship South Right Stairs Up
     */
    PIRATES_COVE_SHIP_SOUTH_RIGHT_STAIRS_UP(Location.create(2226, 3792, 1), Location.create(2228, 3792, 2)),

    /**
     * Pirates Cove Ship South West Stairs Down
     *
     * @constructor Pirates Cove Ship South West Stairs Down
     */
    PIRATES_COVE_SHIP_SOUTH_WEST_STAIRS_DOWN(Location.create(2221, 3793, 3), Location.create(2221, 3795, 2)),

    /**
     * Pirates Cove Ship South West Stairs Up
     *
     * @constructor Pirates Cove Ship South West Stairs Up
     */
    PIRATES_COVE_SHIP_SOUTH_WEST_STAIRS_UP(Location.create(2221, 3794, 2), Location.create(2221, 3792, 3)),

    /**
     * Port Sarim Rat Pits Up
     *
     * @constructor Port Sarim Rat Pits Up
     */
    PORT_SARIM_RAT_PITS_UP(Location.create(2962, 9651, 0), Location.create(3018, 3231, 0)),

    /**
     * Port Sarim Rat Pits Down
     *
     * @constructor Port Sarim Rat Pits Down
     */
    PORT_SARIM_RAT_PITS_DOWN(Location.create(3018, 3232, 0), Location.create(2962, 9650, 0)) {
        override fun checkAchievement(player: Player) {
            finishDiaryTask(player, DiaryType.FALADOR, 1, 11)
        }
    },

    /**
     * Seers Village Spinning House Rooftop Down
     *
     * @constructor Seers Village Spinning House Rooftop Down
     */
    SEERS_VILLAGE_SPINNING_HOUSE_ROOFTOP_DOWN(Location.create(2715, 3472, 3), Location.create(2714, 3472, 1)),

    /**
     * Seers Village Spinning House Rooftop Up
     *
     * @constructor Seers Village Spinning House Rooftop Up
     */
    SEERS_VILLAGE_SPINNING_HOUSE_ROOFTOP_UP(Location.create(2715, 3472, 1), Location.create(2714, 3472, 3)) {
        override fun checkAchievement(player: Player) {
            finishDiaryTask(player, DiaryType.SEERS_VILLAGE, 1, 3)
        }
    },

    /**
     * Sophanem Altar Ladder Up
     *
     * @constructor Sophanem Altar Ladder Up
     */
    SOPHANEM_ALTAR_LADDER_UP(Location.create(3279,2771,1), Location.create(3279, 2770, 3)),

    /**
     * Sophanem Altar Ladder Down
     *
     * @constructor Sophanem Altar Ladder Down
     */
    SOPHANEM_ALTAR_LADDER_DOWN(Location.create(3279,2771,3), Location.create(3279, 2772, 1)),

    /**
     * Swensen Down
     *
     * @constructor Swensen Down
     */
    SWENSEN_DOWN(Location.create(2644, 3657, 0), Location.create(2631, 10006, 0)),

    /**
     * Swensen Up
     *
     * @constructor Swensen Up
     */
    SWENSEN_UP(Location.create(2665, 10037, 0), Location.create(2649, 3661, 0)),

    /**
     * Tree Gnome Stronghold West Bar Stairs Down
     *
     * @constructor Tree Gnome Stronghold West Bar Stairs Down
     */
    TREE_GNOME_STRONGHOLD_WEST_BAR_STAIRS_DOWN(Location.create(2418, 3491, 1), Location.create(2419, 3491, 0)),

    /**
     * Tree Gnome Stronghold West Bar Stairs Up
     *
     * @constructor Tree Gnome Stronghold West Bar Stairs Up
     */
    TREE_GNOME_STRONGHOLD_WEST_BAR_STAIRS_UP(Location.create(2417, 3490, 0), Location.create(2418, 3492, 1)),

    /**
     * Waterbirth Island Dungeon Sublevel 2 Wallasalki 3 Ladder Down
     *
     * @constructor Waterbirth Island Dungeon Sublevel 2 Wallasalki 3 Ladder Down
     */
    WATERBIRTH_ISLAND_DUNGEON_SUBLEVEL_2_WALLASALKI_3_LADDER_DOWN(Location.create(1799, 4387, 2), Location.create(1799, 4388, 1)),

    /**
     * Waterbirth Ladder Way To Lighthouse 1 Up
     *
     * @constructor Waterbirth Ladder Way To Lighthouse 1 Up
     */
    WATERBIRTH_LADDER_WAY_TO_LIGHTHOUSE_1_UP(Location.create(1932, 4378, 0), Location.create(1932, 4380, 2)),

    /**
     * Waterbirth Ladder Way To Lighthouse 2 Up
     *
     * @constructor Waterbirth Ladder Way To Lighthouse 2 Up
     */
    WATERBIRTH_LADDER_WAY_TO_LIGHTHOUSE_2_UP(Location.create(1961, 4391, 2), Location.create(1961, 4393, 3)),

    /**
     * Waterbirth Ladder Way To Lighthouse 3 Up
     *
     * @constructor Waterbirth Ladder Way To Lighthouse 3 Up
     */
    WATERBIRTH_LADDER_WAY_TO_LIGHTHOUSE_3_UP(Location.create(1975, 4408, 3), Location.create(2510, 3644, 0)),

    /**
     * Werewolf Agility Course Ladder Up
     *
     * @constructor Werewolf Agility Course Ladder Up
     */
    WEREWOLF_AGILITY_COURSE_LADDER_UP(Location.create(3549, 9864, 0), Location.create(3543, 3463, 0)),

    /**
     * Witch House To Basement Down
     *
     * @constructor Witch House To Basement Down
     */
    WITCH_HOUSE_TO_BASEMENT_DOWN(Location.create(2907, 3476, 0), Location.create(2906, 9876, 0)),

    /**
     * Witch House To Basement Up
     *
     * @constructor Witch House To Basement Up
     */
    WITCH_HOUSE_TO_BASEMENT_UP(Location.create(2907, 9876, 0), Location.create(2906, 3476, 0)),

    /**
     * Wizard Tower Ladder Up
     *
     * @constructor Wizard Tower Ladder Up
     */
    WIZARD_TOWER_LADDER_UP(Location.create(3103, 9576, 0), Location.create(3105, 3162, 0)),

    /**
     * White Wolf Mountain Fake Ladder 1 Up
     *
     * @constructor White Wolf Mountain Fake Ladder 1 Up
     */
    WHITE_WOLF_MOUNTAIN_FAKE_LADDER_1_UP(Location.create(2837, 9927, 0), Location.create(2837, 3527, 0)),

    /**
     * White Wolf Mountain Fake Ladder 2 Up
     *
     * @constructor White Wolf Mountain Fake Ladder 2 Up
     */
    WHITE_WOLF_MOUNTAIN_FAKE_LADDER_2_UP(Location.create(2823, 9930, 0), Location.create(2823, 3529, 0)),

    /**
     * Draynor Manor Ladder Down
     *
     * @constructor Draynor Manor Ladder Down
     */
    DRAYNOR_MANOR_LADDER_DOWN(Location.create(3092, 3362, 0), Location.create(3117, 9753, 0)),

    /**
     * Draynor Manor Ladder Up
     *
     * @constructor Draynor Manor Ladder Up
     */
    DRAYNOR_MANOR_LADDER_UP(Location.create(3117, 9754, 0), Location.create(3092, 3361, 0)),

    /**
     * Varrock House Up
     *
     * @constructor Varrock House Up
     */
    VARROCK_HOUSE_UP(Location(3230,3383,0), Location(3230, 3382, 1)),

    /**
     * Varrock House Down
     *
     * @constructor Varrock House Down
     */
    VARROCK_HOUSE_DOWN(Location(3230, 3383, 1), Location(3230, 3386, 0)),

    /**
     * Varrock King Rat Ladder Up
     *
     * @constructor Varrock King Rat Ladder Up
     */
    VARROCK_KING_RAT_LADDER_UP(Location(3268,3379,0), Location.create(3269, 3379, 1)),

    /**
     * Varrock King Rat Ladder Down
     *
     * @constructor Varrock King Rat Ladder Down
     */
    VARROCK_KING_RAT_LADDER_DOWN(Location(3268,3379,1), Location.create(3267, 3379, 0)),

    /**
     * Varrock Sewers Down
     *
     * @constructor Varrock Sewers Down
     */
    VARROCK_SEWERS_DOWN(Location(3237, 3458,0), Location(3237, 9858, 0)),

    /**
     * Varrock Sewers Up
     *
     * @constructor Varrock Sewers Up
     */
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
