package content.region.asgarnia.handlers.falador

import content.region.misc.handlers.zanaris.FairyRing
import content.global.skill.gathering.farming.FarmingPatch
import content.global.skill.production.crafting.data.LightSourceData
import content.region.asgarnia.dialogue.falador.RisingSunInnBartenderDialogue
import core.api.*
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.consts.Scenery
import core.game.diary.AreaDiaryTask
import core.game.diary.DiaryEventHookBase
import core.game.diary.DiaryLevel
import core.game.event.*
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.entity.skill.Skills
import core.game.world.map.zone.ZoneBorders

class FaladorAchievementDiary : DiaryEventHookBase(DiaryType.FALADOR) {
    companion object {
        private const val ATTRIBUTE_PINK_DYE_FROM_BETTY = "diary:falador:pink-dye-from-betty"
        private const val ATTRIBUTE_BLACK_CHAINBODY_PURCHASED = "diary:falador:black-chain-bought"
        private const val ATTRIBUTE_FEED_RIDGELEY_WITH_CHEESE = "diary:falador:feed-ridgeley-with-cheese"

        private val FALADOR_PARTY_ROOM = 12084
        private val PORT_SARIM_CHURCH_ALTAR = 11825
        private val MINING_GUILD_AREA = ZoneBorders(3016, 9731, 3055, 9756)
        private val DARK_WIZARDS_TOWER_ROOF_AREA = ZoneBorders(2904, 3331, 2911, 3338, 2)
        private val WHITE_KNIGHTS_CASTLE_ROOF_AREA = ZoneBorders(2955, 3333, 2996, 3354, 3)
        private val PARK_TREE_PATCH_AREA = ZoneBorders(3002, 3371, 3006, 3375)
        private val PARK_POND_AREA = ZoneBorders(2987, 3381, 2994, 3386)
        private val WAYNES_CHAINS_AREA = ZoneBorders(2969, 3310, 2975, 3314)
        private val SARAHS_FARMING_SHOP_AREA = ZoneBorders(3021, 3285, 3040, 3296)
        private val FALADOR_GENERAL_AREA = ZoneBorders(2934, 3399, 3399, 3307)
        private val CHEMIST_AREA = ZoneBorders(2929, 3213, 2936, 3207)
        private val PORT_SARIM_FLOWER_PATCH_AREA = ZoneBorders(3053, 3306, 3056, 3309)

        private val PROSELYTE_FULL_ARMOR_MALE = intArrayOf(Items.PROSELYTE_SALLET_9672, Items.PROSELYTE_HAUBERK_9674, Items.PROSELYTE_CUISSE_9676)
        private val PROSELYTE_FULL_ARMOR_FEMALE = intArrayOf(Items.PROSELYTE_SALLET_9672, Items.PROSELYTE_HAUBERK_9674, Items.PROSELYTE_TASSET_9678)
        private val PARTY_BALLOONS = intArrayOf(Scenery.PARTY_BALLOON_115, Scenery.PARTY_BALLOON_116, Scenery.PARTY_BALLOON_117, Scenery.PARTY_BALLOON_118, Scenery.PARTY_BALLOON_119, Scenery.PARTY_BALLOON_120, Scenery.PARTY_BALLOON_121, Scenery.PARTY_BALLOON_122)
        private val PARK_DUCKS = intArrayOf(NPCs.DUCK_46, NPCs.DUCK_2693)
        private val FALADOR_GUARD = intArrayOf(NPCs.GUARD_9, NPCs.GUARD_3230, NPCs.GUARD_3228, NPCs.GUARD_3229)
        private val SKELETAL_WYVERNS = intArrayOf(NPCs.SKELETAL_WYVERN_3068, NPCs.SKELETAL_WYVERN_3069, NPCs.SKELETAL_WYVERN_3070, NPCs.SKELETAL_WYVERN_3071)
        private val CAPES = intArrayOf(Items.CAPE_1019, Items.CAPE_1007, Items.CAPE_1021, Items.CAPE_1023, Items.CAPE_1027, Items.CAPE_1029, Items.CAPE_1031)

        object EasyTasks {
            // Buy a Farming amulet from Sarah on the farm north of Port Sarim.
            const val PORT_SARIM_SARAH_BUY_FARMING_AMULET = 0

            // Buy a stat-boosting beer from a waitress in the Rising Sun tavern.
            const val RISING_SUN_BUY_A_STATBOOST = 1

            // Buy a black chainbody from Wayne's Chains, and try it on in the shop.
            const val WAYNE_BUY_AND_WEAR_BLACK_CHAINBODY = 2

            // Climb to the top of the White Knights' Castle.
            const val WHITE_KNIGHTS_CASTLE_CLIMB_TO_TOP = 3

            // Discover your family crest from Sir Renitee.
            const val SIR_RENITEE_FAMILY_CREST_DISCOVER = 4

            // Enter the mole's lair under Falador Park.
            const val PARK_ENTER_MOLE_LAIR = 5

            // Feed Ridgeley, the hairdresser's pet.
            const val FEED_RIDGELEY_AT_HAIRDRESSERS = 6

            // Fill a bucket from the pump north of the west Falador bank.
            const val FILL_BUCKET_FROM_NORTHERN_PUMP = 7

            // Heal an elemental wizard by casting an appropriate elemental spell on him (Air, Water, Earth, Fire).
            const val HEAL_ELEMENTAL_WIZARD_WITH_SPELL = 8

            // Kill a duck in Falador Park.
            const val PARK_KILL_A_DUCK = 9

            // Kill a highwayman on the road south of Falador.
            const val SOUTHERN_ROAD_KILL_HIGHWAYMAN = 10

            // Make an air tiara.
            const val MAKE_AIR_TIARA = 11

            // Pop a party balloon.
            const val POP_PARTY_BALLOON = 12

            // Recharge your Prayer points at the altar south-west of Port Sarim.
            const val PORT_SARIM_RECHARGE_PRAYER_POINTS = 13

            // Take the boat to Entrana.
            const val TAKE_BOAT_TO_ENTRANA = 14

        }

        object MediumTasks {
            // Craft a Fruit basket using the loom at the farm north of Port Sarim.
            const val PORT_SARIM_CRAFT_FRUIT_BASKET = 0

            // Crawl under Falador's south wall.
            const val CRAWL_UNDER_SOUTHERN_WALL = 1

            // Grapple up, and then jump off the north Falador wall.
            const val GRAPPLE_AND_JUMP_OFF_NORTHERN_WALL = 2

            // Increase your reputation with the White Knights by killing a black knight.
            const val INCREASE_WHITE_KNIGHT_REPUTATION = 3

            // Kill an ice giant in the Asgarnian Ice Dungeon.
            const val ICE_DUNGEON_KILL_ICE_GIANT = 4

            // Light a bullseye lantern in the chemist's.
            const val CHEMISTS_LIGHT_BULLSEYE_LANTERN = 5

            // Pickpocket a Falador guard.
            const val PICKPOCKET_GUARD = 6

            // Place a scarecrow to protect your sweetcorn as it grows in the patch north of Port Sarim.
            const val PORT_SARIM_NORTHERN_PATCH_PLACE_SCARECROW = 7

            // Salute Sir Tiffy Cashien while wearing full initiate armour.
            const val SALUTE_SIR_TIFFY_CASHIEN_IN_INITIATE_ARMOR = 8

            // Smith blurite crossbow limbs on Thurgo's anvil.
            const val SMITH_BLURITE_CROSSBOW_LIMBS_ON_THURGOS_ANVIL = 9

            // Travel from Port Sarim to Musa Point for free (with a little help from Charos).
            const val PORT_SARIM_CHAROS_TRAVEL_TO_MUSA_POINT = 10

            // Visit the Port Sarim rat pits.
            const val PORT_SARIM_VISIT_RAT_PITS = 11

        }

        object HardTasks {
            // Ascend the Dark Wizards' Tower while wearing full proselyte armour.
            const val DARK_WIZARDS_TOWER_ASCEND_IN_FULL_PROSELYTE_ARMOR = 0

            // Change your family crest to the Saradomin symbol.
            const val CHANGE_FAMILY_CREST_TO_SARADOMIN = 1

            // Craft 196 or more air runes simultaneously.
            const val CRAFT_196_AIR_RUNES_AT_ONCE = 2

            // Cut down a Yew tree or Magic tree that you grew in Falador Park.
            const val CUT_DOWN_GROWN_YEW_OR_MAGIC_TREE = 3

            // Dial to the fairy ring on Mudskipper Point.
            const val DIAL_FAIRY_RING_MUDSKIPPER_POINT = 4

            // Dye a cape pink with Pink dye from Betty in Port Sarim.
            const val PORT_SARIM_BETTY_DYE_CAPE_PINK = 5

            // Enter the Mining Guild.
            const val ENTER_MINING_GUILD = 6

            // Kill a Mogre at Mudskipper Point.
            const val MUDSKIPPER_POINT_KILL_MOGRE = 7

            // Kill a Skeletal wyvern in the Asgarnian Ice Dungeon.
            const val ICE_DUNGEON_KILL_SKELETAL_WYVERN = 8

            // Summon an Ibis in the Port Sarim fish store.
            const val PORT_SARIM_FISH_STORE_SUMMON_IBIS = 9

        }
    }

    override val areaTasks get() = arrayOf(
        AreaDiaryTask(
            WHITE_KNIGHTS_CASTLE_ROOF_AREA,
            DiaryLevel.EASY,
            EasyTasks.WHITE_KNIGHTS_CASTLE_CLIMB_TO_TOP
        ),

        AreaDiaryTask(
            MINING_GUILD_AREA,
            DiaryLevel.HARD,
            HardTasks.ENTER_MINING_GUILD
        ),

        AreaDiaryTask(
            DARK_WIZARDS_TOWER_ROOF_AREA,
            DiaryLevel.HARD,
            HardTasks.DARK_WIZARDS_TOWER_ASCEND_IN_FULL_PROSELYTE_ARMOR
        ) {
            player -> allInEquipment(player, *PROSELYTE_FULL_ARMOR_MALE) || allInEquipment(player, *PROSELYTE_FULL_ARMOR_FEMALE)
        }
    )

    override fun onInteracted(player: Player, event: InteractionEvent) {
        when (player.viewport.region.regionId) {
            FALADOR_PARTY_ROOM -> {
                if (event.option == "burst" && event.target.id in PARTY_BALLOONS) {
                    finishTask(
                        player,
                        DiaryLevel.EASY,
                        EasyTasks.POP_PARTY_BALLOON
                    )
                }
            }
            PORT_SARIM_CHURCH_ALTAR -> {
                if (player.skills.prayerPoints < getDynLevel(player, Skills.PRAYER)) {
                    if (event.option == "pray" && event.target.id == Scenery.ALTAR_39842) {
                        finishTask(
                            player,
                            DiaryLevel.EASY,
                            EasyTasks.PORT_SARIM_RECHARGE_PRAYER_POINTS
                        )
                    }
                }
            }
        }

        if (event.option == "pickpocket" && (event.target.id in FALADOR_GUARD && inBorders(player, FALADOR_GENERAL_AREA)) && getStatLevel(player, Skills.THIEVING) >= 40) {
            finishTask(
                player,
                DiaryLevel.MEDIUM,
                MediumTasks.PICKPOCKET_GUARD
            )
        }
    }

    override fun onDialogueOptionSelected(player: Player, event: DialogueOptionSelectionEvent) {
        when (event.dialogue) {
            is RisingSunInnBartenderDialogue -> {
                if (event.currentStage in 12..14) {
                    finishTask(
                        player,
                        DiaryLevel.EASY,
                        EasyTasks.RISING_SUN_BUY_A_STATBOOST
                    )
                }
            }
        }
    }

    override fun onResourceProduced(player: Player, event: ResourceProducedEvent) {
        when (player.viewport.region.id) {
            11828 -> when (event.itemId) {
                Items.YEW_LOGS_1515, Items.MAGIC_LOGS_1513 -> {
                    if (inBorders(player, PARK_TREE_PATCH_AREA)) {
                        finishTask(
                            player,
                            DiaryLevel.HARD,
                            HardTasks.CUT_DOWN_GROWN_YEW_OR_MAGIC_TREE
                        )
                    }
                }
            }

            11825 -> when(event.itemId) {
                Items.BLURITE_LIMBS_9422 -> finishTask(
                    player,
                    DiaryLevel.MEDIUM,
                    MediumTasks.SMITH_BLURITE_CROSSBOW_LIMBS_ON_THURGOS_ANVIL
                )
            }
        }
    }

    override fun onNpcKilled(player: Player, event: NPCKillEvent) {
        when (player.viewport.region.id) {
            11828 -> if (event.npc.id in PARK_DUCKS && inBorders(event.npc, PARK_POND_AREA)) {
                finishTask(
                    player,
                    DiaryLevel.EASY,
                    EasyTasks.PARK_KILL_A_DUCK
                )
            }

            11824 -> if(event.npc.id == NPCs.MOGRE_114) {
                finishTask(
                    player,
                    DiaryLevel.HARD,
                    HardTasks.MUDSKIPPER_POINT_KILL_MOGRE
                )
            }

            12181 -> if (event.npc.id in SKELETAL_WYVERNS) {
                finishTask(
                    player,
                    DiaryLevel.HARD,
                    HardTasks.ICE_DUNGEON_KILL_SKELETAL_WYVERN
                )
            }
        }
    }

    override fun onItemPurchasedFromShop(player: Player, event: ItemShopPurchaseEvent) {
        when {
            inBorders(player, WAYNES_CHAINS_AREA) && (event.itemId == Items.BLACK_CHAINBODY_1107) -> {
                fulfillTaskRequirement(
                    player,
                    DiaryLevel.EASY,
                    EasyTasks.WAYNE_BUY_AND_WEAR_BLACK_CHAINBODY,
                    ATTRIBUTE_BLACK_CHAINBODY_PURCHASED
                )
            }

            inBorders(player, SARAHS_FARMING_SHOP_AREA) && (event.itemId == Items.AMULET_OF_FARMING8_12622) -> {
                finishTask(
                    player,
                    DiaryLevel.EASY,
                    EasyTasks.PORT_SARIM_SARAH_BUY_FARMING_AMULET
                )
            }
        }
    }

    override fun onItemEquipped(player: Player, event: ItemEquipEvent) {
        when {
            inBorders(player, WAYNES_CHAINS_AREA) && (event.itemId == Items.BLACK_CHAINBODY_1107) -> {
                whenTaskRequirementFulfilled(player, ATTRIBUTE_BLACK_CHAINBODY_PURCHASED) {
                    finishTask(
                        player,
                        DiaryLevel.EASY,
                        EasyTasks.WAYNE_BUY_AND_WEAR_BLACK_CHAINBODY
                    )
                }
            }
        }
    }

    override fun onFairyRingDialed(player: Player, event: FairyRingDialEvent) {
        if (event.fairyRing == FairyRing.AIQ) {
            finishTask(
                player,
                DiaryLevel.HARD,
                HardTasks.DIAL_FAIRY_RING_MUDSKIPPER_POINT
            )
        }
    }

    override fun onLightSourceLit(player: Player, event: LitLightSourceEvent) {
        when {
            inBorders(player, CHEMIST_AREA) && (event.litLightSourceId == LightSourceData.BULLSEYE_LANTERN.litId) -> {
                finishTask(
                    player,
                    DiaryLevel.MEDIUM,
                    MediumTasks.CHEMISTS_LIGHT_BULLSEYE_LANTERN
                )
            }
        }
    }

    override fun onUsedWith(player: Player, event: UseWithEvent) {
        when {
            inBorders(player, PORT_SARIM_FLOWER_PATCH_AREA) -> {
                if (event.used == Items.SCARECROW_6059 && event.with == 7847 && hasLevelStat(player, Skills.FARMING, 23) && FarmingPatch.forObjectID(7840)?.getPatchFor(player)?.plantable == null) {
                    finishTask(
                        player,
                        DiaryLevel.MEDIUM,
                        MediumTasks.PORT_SARIM_NORTHERN_PATCH_PLACE_SCARECROW
                    )
                }
            }
        }

        if(event.used == Items.PINK_DYE_6955 && event.with in CAPES) {
            whenTaskRequirementFulfilled(player, ATTRIBUTE_PINK_DYE_FROM_BETTY) {
                finishTask(
                    player,
                    DiaryLevel.HARD,
                    HardTasks.PORT_SARIM_BETTY_DYE_CAPE_PINK
                )
            }
        }

        if(event.used == Items.CHEESE_1985 && event.with == Scenery.TREADMILL_11677) {
            runTask(player, 1){
                whenTaskRequirementFulfilled(player, ATTRIBUTE_FEED_RIDGELEY_WITH_CHEESE) {
                    finishTask(
                        player,
                        DiaryLevel.EASY,
                        EasyTasks.FEED_RIDGELEY_AT_HAIRDRESSERS
                    )
                }
            }
        }
    }
}
