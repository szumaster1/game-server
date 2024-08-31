package content.region.karamja.achievement

import cfg.consts.Items
import cfg.consts.NPCs
import cfg.consts.Scenery
import core.api.inBorders
import core.game.diary.AreaDiaryTask
import core.game.diary.DiaryEventHookBase
import core.game.diary.DiaryLevel
import core.game.event.InteractionEvent
import core.game.event.NPCKillEvent
import core.game.event.PickUpEvent
import core.game.event.ResourceProducedEvent
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.world.map.zone.ZoneBorders

/**
 * Represents the Karamja achievement diary.
 */
class KaramjaAchievementDiary : DiaryEventHookBase(DiaryType.KARAMJA) {

    companion object {
        private const val ATTRIBUTE_SEAWEED_PICKED = "diary:karamja:seaweed-picked"
        private const val ATTRIBUTE_PALM_LEAF_PICKED = "diary:karamja:palm-leaf-picked"
        private const val ATTRIBUTE_BANANA_PICKED = "diary:karamja:banana-picked"

        private val CAIRN_ISLE_AREA = ZoneBorders(2752, 2963, 2774, 2992)
        private val MAIN_ISLAND_AREA = ZoneBorders(2749, 2886, 2972, 3132)
        private val BANANA_FISHING_SPOT_AREA = ZoneBorders(2923, 3173, 2928, 3182)
        private val TAI_BWO_WANNAI_WOODCUTTING_AREA = ZoneBorders(2817, 3076, 2829, 3090)

        private val KET_ZEKS = arrayOf(NPCs.KET_ZEK_2743, NPCs.KET_ZEK_2744)
        private val METAL_DRAGONS = arrayOf(NPCs.BRONZE_DRAGON_1590, NPCs.IRON_DRAGON_1591, NPCs.STEEL_DRAGON_1592)

        private val FRUIT_TREES = arrayOf(Scenery.BANANA_TREE_7999, Scenery.APPLE_TREE_7948, Scenery.CURRY_TREE_8033, Scenery.ORANGE_TREE_8064, Scenery.PINEAPPLE_PLANT_7979, Scenery.PALM_TREE_8091, Scenery.PAPAYA_TREE_8118)

        object EasyTasks {

            /*
             * Pick 5 bananas from the plantation located east of the volcano.
             */
            const val PICK_5_BANANAS = 0

            /*
             * Use the rope swing to travel to the small island north-west of Karamja, where the Moss Giants are.
             */
            const val TRAVEL_TO_MOSS_GIANTS_VIA_ROPESWING = 1

            /*
             * Mine some gold from the rocks on the north-west peninsula of Karamja (not the ones near Brimhaven Dungeon).
             */
            const val BRIMHAVEN_MINE_GOLD = 2

            /*
             * Travel to Port Sarim via the dock, east of Musa Point.
             */
            const val MUSA_POINT_CHARTER_SHIP_TO_PORT_SARIM = 3

            /*
             * Travel to Ardougne via the port near Brimhaven.
             */
            const val BRIMHAVEN_CHARTER_SHIP_TO_ARDOUGNE = 4

            /*
             * Explore Cairn Island to the west of Karamja.
             */
            const val CAIRN_ISLE_VISIT = 5

            /*
             * Use the Fishing spots north of the banana plantation.
             */
            const val USE_FISHING_SPOTS_BANANA_PLANTATION = 6

            /*
             * Collect 5 seaweed from anywhere on Karamja.
             */
            const val PICK_5_SEAWEED = 7

            /*
             * Attempt the TzHaar Fight Pits or Fight Cave.
             */
            const val ATTEMPT_TZHAAR_FIGHT_PITS_OR_FIGHT_CAVE = 8

            /*
             * Kill a Jogre in the Pothole dungeon.
             */
            const val POTHOLE_DUNGEON_KILL_JOGRE = 9

        }

        object MediumTasks {
            /*
             * Claim a ticket from the Agility arena in Brimhaven.
             */
            const val BRIMHAVEN_CLAIM_AGILITY_ARENA_TICKET = 0

            /*
             * Discover hidden wall in the dungeon below the volcano.
             */
            const val FIND_HIDDEN_WALL_BELOW_VOLCANO = 1

            /*
             * Visit the Isle of Crandor via the dungeon below the volcano.
             */
            const val CRANDOR_ISLAND_VISIT = 2

            /*
             * Use Vigroy and Hajedy's cart service.
             */
            const val USE_VIGROY_HAJEDY_CARTS = 3

            /*
             * Earn 100% favour in the village of Tai Bwo Wannai Cleanup.
             */
            const val TAI_BWO_WANNAI_EARN_FULL_FAVOR = 4
            /*
             * Cook a spider on stick.
             */
            const val COOK_SPIDER_ON_STICK = 5

            /*
             * Charter the Lady of the Waves from Cairn Isle to Port Khazard.
             */
            const val CAIRN_ISLE_CHARTER_LADY_OF_THE_WAVES = 6

            /*
             * Cut a log from a teak tree.
             */
            const val CUT_TEAK_TREE = 7

            /*
             * Cut a log from a mahogany tree.
             */
            const val CUT_MAHOGANY_TREE = 8

            /*
             * Catch a karambwan.
             */
            const val CATCH_KARAMBWAN = 9

            /*
             * Exchange gems, a gout tuber, trading sticks for a machete
             * (talk to Safta Doc in anvil house in Tai Bwo Wannai).
             */
            const val EXCHANGE_GEMS_TUBER_TRADING_STICKS_FOR_MACHETE = 10

            /*
             * Use the gnome glider to travel to Karamja.
             */
            const val GNOME_GLIDE_TO_KARAMJA = 11

            /*
             * Grow a healthy fruit tree in the patch near Brimhaven.
             */
            const val BRIMHAVEN_GROW_HEALTHY_FRUIT_TREE = 12

            /*
             * Trap a Horned Graahk.
             */
            const val TRAP_HORNED_GRAAHK = 13

            /*
             * Chop the vines to gain deeper access to Brimhaven Dungeon.
             */
            const val BRIMHAVEN_DUNGEON_CHOP_VINES = 14

            /*
             * Cross the lava using the stepping stones within Brimhaven Dungeon.
             */
            const val BRIMHAVEN_DUNGEON_CROSS_LAVA_STEPPING_STONES = 15

            /*
             * Climb the stairs within Brimhaven Dungeon. They're in the north,
             * and lead up to Greater Demons.
             */
            const val BRIMHAVEN_DUNGEON_CLIMB_STAIRS = 16

            /*
             * Charter a ship from the shipyard in the far east of Karamja (Southern-most dock).
             */
            const val CHARTER_SHIP_EAST = 17

            /*
             * Mine a red topaz from a gem rock
             * (can be mined from Tai Bwo Wannai Cleanup as well as the Shilo Village gem mine).
             */
            const val MINE_RED_TOPAZ = 18

        }

        object HardTasks {

            /*
             * Become the Champion of the Fight Pits.
             */
            const val FIGHT_PITS_BECOME_CHAMPION = 0

            /*
             * Successfully kill a Ket-Zek in the Fight Caves.
             */
            const val FIGHT_CAVE_KILL_KET_ZEK = 1

            /*
             * Eat an oomlie wrap.
             */
            const val EAT_OOMLIE_WRAP = 2

            /*
             * Craft some nature runes.
             */
            const val CRAFT_NATURE_RUNES = 3

            /*
             * Cook a karambwan thoroughly.
             */
            const val COOK_KARAMBWAN_PROPERLY = 4

            /*
             * Kill a deathwing in the dungeon (south-west of shilo) under
             * the Kharazi Jungle (south of the door in Shaman's Cave).
             */
            const val KHARAZI_JUNGLE_KILL_DEATHWING = 5

            /*
             * Use the crossbow shortcut south of the volcano.
             */
            const val VOLCANO_USE_CROSSBOW_SHORTCUT_SOUTH = 6

            /*
             * Collect 5 palm leaves (south of Shilo near oomlie).
             */
            const val PICK_5_PALM_LEAVES = 7

            /*
             * Be assigned a Slayer task by Duradel
             * (or his replacement Lapalok if you have completed While Guthix Sleeps) in Shilo Village.
             */
            const val DURADEL_LAPALOK_GET_SLAYER_TASK = 8

            /*
             * Kill a metal dragon in Brimhaven Dungeon.
             */
            const val BRIMHAVEN_DUNGEON_KILL_METAL_DRAGON = 9

        }
    }

    override val areaTasks get() = arrayOf(
        AreaDiaryTask(
            CAIRN_ISLE_AREA,
            DiaryLevel.EASY,
            EasyTasks.CAIRN_ISLE_VISIT
        ),
    )

    override fun onResourceProduced(player: Player, event: ResourceProducedEvent) {
        when (event.itemId){
            6297 -> {
                finishTask(
                    player,
                    DiaryLevel.MEDIUM,
                    MediumTasks.COOK_SPIDER_ON_STICK
                )
            }
        }

        when (player.viewport.region.id) {
            10802 -> if (event.itemId == Items.GOLD_ORE_444) {
                finishTask(
                    player, DiaryLevel.EASY, EasyTasks.BRIMHAVEN_MINE_GOLD
                )
            }

            11310, 11410 -> if (event.itemId == Items.UNCUT_RED_TOPAZ_1629) {
                finishTask(
                    player,
                    DiaryLevel.MEDIUM,
                    MediumTasks.MINE_RED_TOPAZ
                )
            }

            11569 -> if (event.itemId == Items.BANANA_1963) {
                progressIncrementalTask(
                    player,
                    DiaryLevel.EASY,
                    EasyTasks.PICK_5_BANANAS,
                    ATTRIBUTE_BANANA_PICKED, 5
                )
            }
        }

        when {
            inBorders(player, BANANA_FISHING_SPOT_AREA) -> {
                when (event.source.id) {
                    NPCs.FISHING_SPOT_323, NPCs.FISHING_SPOT_333 -> {
                        finishTask(
                            player,
                            DiaryLevel.EASY,
                            EasyTasks.USE_FISHING_SPOTS_BANANA_PLANTATION
                        )
                    }
                }
            }

            inBorders(player, TAI_BWO_WANNAI_WOODCUTTING_AREA) -> {
                when (event.itemId) {
                    Items.MAHOGANY_LOGS_6332 -> {
                        finishTask(
                            player,
                            DiaryLevel.MEDIUM,
                            MediumTasks.CUT_MAHOGANY_TREE
                        )
                    }

                    Items.TEAK_LOGS_6333 -> {
                        finishTask(
                            player,
                            DiaryLevel.MEDIUM,
                            MediumTasks.CUT_TEAK_TREE
                        )
                    }
                }
            }
        }
    }

    override fun onNpcKilled(player: Player, event: NPCKillEvent) {
        when (player.viewport.region.id) {
            10899, 10900 -> if (event.npc.id in METAL_DRAGONS) {
                finishTask(
                    player,
                    DiaryLevel.HARD,
                    HardTasks.BRIMHAVEN_DUNGEON_KILL_METAL_DRAGON
                )
            }

            11412 -> if (event.npc.id == NPCs.JOGRE_113) {
                finishTask(
                    player,
                    DiaryLevel.EASY,
                    EasyTasks.POTHOLE_DUNGEON_KILL_JOGRE
                )
            }


            else -> if (event.npc.id in KET_ZEKS) {
                finishTask(
                    player,
                    DiaryLevel.HARD,
                    HardTasks.FIGHT_CAVE_KILL_KET_ZEK
                )
            }
        }
    }

    override fun onPickedUp(player: Player, event: PickUpEvent) {
        when {
            inBorders(player, MAIN_ISLAND_AREA) -> {
                if (event.itemId == Items.SEAWEED_401) {
                    progressIncrementalTask(
                        player,
                        DiaryLevel.EASY,
                        EasyTasks.PICK_5_SEAWEED,
                        ATTRIBUTE_SEAWEED_PICKED, 5
                    )
                }

                if (event.itemId == Items.PALM_LEAF_2339) {
                    progressIncrementalTask(
                        player, DiaryLevel.HARD,
                        HardTasks.PICK_5_PALM_LEAVES,
                        ATTRIBUTE_PALM_LEAF_PICKED, 5
                    )
                }
            }
        }
    }

    override fun onInteracted(player: Player, event: InteractionEvent) {
        when (player.viewport.region.id) {
            10644 -> if (event.target.id == Scenery.STAIRS_5097 && event.option == "walk-up") {
                finishTask(
                    player,
                    DiaryLevel.EASY,
                    MediumTasks.BRIMHAVEN_DUNGEON_CLIMB_STAIRS
                )
            }

            11058 -> {
                if (event.target.id == NPCs.HAJEDY_510 && event.option == "pay-fare") {
                    finishTask(
                        player,
                        DiaryLevel.MEDIUM,
                        MediumTasks.USE_VIGROY_HAJEDY_CARTS
                    )
                }
                if (event.target.id in FRUIT_TREES) {
                    finishTask(
                        player,
                        DiaryLevel.MEDIUM,
                        MediumTasks.BRIMHAVEN_GROW_HEALTHY_FRUIT_TREE
                    )
                }
            }

            11054, 11055 -> if (event.target.id == 19231) {
                finishTask(
                    player,
                    DiaryLevel.MEDIUM,
                    MediumTasks.TRAP_HORNED_GRAAHK
                )
            }
        }
    }
}
