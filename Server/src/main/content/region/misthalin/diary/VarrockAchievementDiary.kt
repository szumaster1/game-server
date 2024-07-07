package content.region.misthalin.diary

import content.global.skill.combat.magic.TeleportMethod
import content.global.skill.combat.prayer.Bones
import content.global.travel.canoe.CanoeListeners
import content.region.misc.handlers.zanaris.FairyRing
import content.region.misthalin.dialogue.varrock.BennyDialogue
import content.region.misthalin.dialogue.varrock.ElsieDialogue
import content.region.misthalin.dialogue.varrock.museum.CuratorHaigHalenDialogue
import content.region.misthalin.dialogue.varrock.museum.OrlandoSmithDialogue
import content.region.misthalin.quest.member.familycrest.dialogue.DimintheisDialogue
import core.api.*
import core.api.consts.Components
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.consts.Scenery
import core.game.diary.AreaDiaryTask
import core.game.diary.DiaryEventHookBase
import core.game.diary.DiaryLevel
import core.game.event.*
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.SpellBookManager
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.map.zone.ZoneBorders

class VarrockAchievementDiary : DiaryEventHookBase(DiaryType.VARROCK) {
    companion object {

        private const val ATTRIBUTE_CRAFT_AIR_BATTLESTAFF = "diary:varrock:craft-air-battlestaff"

        private val VARROCK_ROOF_AREA = ZoneBorders(3201, 3467, 3225, 3497, 3)
        private val SOS_LEVEL_2_AREA = ZoneBorders(2040, 5241, 2046, 5246)
        private val VARROCK_PALACE_AREA = ZoneBorders(3201, 3456, 3227, 3468)
        private val CHAMPIONS_GUILD_AREA = ZoneBorders(3188, 3361, 3194, 3362)
        private val OZIACH_SHOP_AREA = ZoneBorders(3066, 3514, 3070, 3518)
        private val AIR_OBELISK_AREA = ZoneBorders(3087, 3568, 3089, 3570)
        private val FARMING_PATCH_AREA = ZoneBorders(3180, 3356, 3183, 3359)

        private val STRAY_DOGS = arrayOf(NPCs.STRAY_DOG_4766, NPCs.STRAY_DOG_4767, NPCs.STRAY_DOG_5917, NPCs.STRAY_DOG_5918)

        object EasyTasks {
            // Have Thessalia show you what outfits you can wear.
            const val THESSALIA_BROWSE_CLOTHES = 0

            // Have Aubury teleport you to the essence mine.
            const val AUBURY_TELEPORT_ESSENCE_MINE = 1

            // Mine some iron ore in the Mining spot, south-east of Varrock.
            const val MINE_IRON_SOUTHEAST = 2

            // Make a plank at the sawmill.
            const val MAKE_PLANK_SAWMILL = 3

            // Enter the second level of the Stronghold of Security.
            const val VISIT_SOS_LEVEL2 = 4

            // Jump over the fence, south of Varrock.
            const val JUMP_OVER_FENCE_SOUTH = 5

            // Chop down a dying tree in the Lumberyard.
            const val LUMBERYARD_CHOP_DYING_TREE = 6

            // Buy a copy of the Varrock Herald.
            const val BUY_VARROCK_HERALD = 7

            // Give a stray dog a bone.
            const val GIVE_STRAY_DOG_A_BONE = 8

            // Make a bowl on the pottery wheel then fire it in the pottery oven, all in the Barbarian Village.
            const val BARBARIAN_VILLAGE_SPIN_A_BOWL = 9

            // Enter Edgeville Dungeon using the entrance to the south of Edgeville.
            const val EDGEVILLE_ENTER_DUNGEON_SOUTH = 10

            // Move your player-owned house portal to a different location using the Varrock estate agent.
            const val MOVE_POH_TO_VARROCK = 11

            // Speak to Haig Halen after obtaining at least 50 kudos* (*Quest points atm).
            const val SPEAK_TO_HAIG_HALEN_50QP = 12

            // Enter the Earth Altar using an earth tiara or talisman.
            const val ENTER_EARTH_ALTAR = 13

            // Have Elsie tell you a story.
            const val ELSIE_TELL_A_STORY = 14

            // Mine some limestone near Paterdomus temple, to the east of Varrock.
            const val PATERDOMUS_MINE_LIMESTONE = 15

            // Catch a trout in the river to the east of Barbarian Village.
            const val BARBARIAN_VILLAGE_CATCH_TROUT = 16

            // Venture through the cobwebbed corridor in Varrock Sewers.
            const val SEWERS_CUT_COBWEB = 17

            // Find the highest point in Varrock.
            const val FIND_HIGHEST_POINT = 18

        }

        object MediumTasks {
            // Have the Apothecary in Varrock make you a Strength potion.
            const val APOTHECARY_MAKE_STRENGTH_POTION = 0

            // Enter the Champions' Guild.
            const val CHAMPIONS_GUILD_VISIT = 1

            // Take the Dagon'Hai shortcut to the chaos portal.
            const val TAKE_DAGONHAI_CHAOS_PORTAL_SHORTCUT = 2

            // Get a full complement of rats on your rat pole.
            const val RAT_POLE_FULL_RAT_COMPLEMENT = 3

            // Escape from the spider lair in Varrock Sewers with some red spider eggs.
            const val SEWER_GATHER_RED_SPIDERS_EGGS = 4

            // Use the spirit tree north of Varrock.
            const val USE_SPIRIT_TREE_NORTH = 5

            // Perform the four emotes from the Stronghold of Security.
            const val PERFORM_ALL_SOS_EMOTES = 6

            // Select a colour for a new kitten.
            const val SELECT_KITTEN_COLOR = 7

            // Use the shortcut under the wall, north-west of the Grand Exchange.
            const val USE_GE_UNDER_WALL_SHORTCUT = 8

            // Enter the A Soul's Bane rift.
            const val ENTER_SOULBANE_RIFT = 9

            // Teleport to the Digsite using a Digsite pendant.
            const val DIGSITE_PENDANT_TELEPORT = 10

            // Craft an earth tiara on the Earth Altar.
            const val CRAFT_EARTH_TIARA = 11

            // Pickpocket a guard in the Varrock Palace courtyard.
            const val PALACE_PICKPOCKET_GUARD = 12

            // Use the Teleport to Varrock spell.
            const val CAST_VARROCK_TELEPORT_SPELL = 13

            // Get a Slayer task from Vannaka.
            const val VANNAKA_GET_SLAYER_TASK = 14

            // Buy twenty mahogany planks from the Sawmill Operator in one transaction.
            const val SAWMILL_BUY_20_MAHOGANY_PLANKS = 15

            // Pick a fruit from the White Tree.
            const val PICK_FROM_WHITE_TREE = 16

            // Use the hot air balloon to travel from Varrock to somewhere else.
            const val HOTAIR_BALLOON_TRAVEL_SOMEWHERE = 17

            // Get a cat training medal from Gertrude.
            const val GERTRUDE_GET_CAT_TRAINING_MEDAL = 18

            // Dial to the fairy ring west of Varrock.
            const val DIAL_FAIRY_RING_WEST = 19

            // Browse through Oziach's Armour Shop.
            const val OZIACH_BROWSE_STORE = 20

        }

        object HardTasks {
            // Pick poison ivy from your bush Farming patch in Varrock (west of Champions' Guild).
            const val PICK_POISON_IVY_FARMING_PATCH = 0

            // Use the pipe shortcut in Varrock Sewers, near the moss giants.
            const val USE_MOSS_GIANT_PIPE_SHORTCUT = 1

            // Trade furs with the Fancy Dress Seller for a spottier cape (located south-east of the eastern Varrock bank).
            const val FANCY_DRESS_SELLER_TRADE_FURS = 2

            // Smith an adamantite medium helm on the south-east anvil in Varrock, next to Aubury's Rune Shop.
            const val SMITH_ADAMANT_MED_HELM_SOUTHEAST = 3

            // Speak to Orlando Smith when you have achieved 153 kudos.
            const val SPEAK_TO_ORLANDO_SMITH_153_KUDOS = 4

            // Talk to Romily Weaklax and give him a wild pie.
            const val GIVE_WEAKLAX_A_PIE = 5

            // Craft an air battlestaff.
            const val CRAFT_AIR_BATTLESTAFF = 6

            // Give your player-owned house a fancy stone or tropical wood finish at the Varrock estate agent's.
            const val GIVE_POH_TROPICAL_WOOD_OR_FANCY_STONE_FINISH = 7

            // Make a Varrock teleport tablet on a mahogany lectern.
            const val MAKE_VARROCK_TELEPORT_TABLET_OR_MAHOGANY_LECTERN = 8

            // Obtain a new set of Family Crest gauntlets from Dimintheis.
            const val OBTAIN_NEW_SET_OF_FAMILY_CREST_GAUNTLETS = 9

            // Make a waka canoe near Edgeville.
            const val EDGEVILLE_MAKE_WAKA_CANOE = 10

            // Use the Home Teleport spell in the Ancient Magicks spellbook to teleport to Edgeville.
            const val EDGEVILLE_TELEPORT_USING_ANCIENT_MAGICKS = 11

            // Use the skull sceptre to teleport to Barbarian Village.
            const val BARBARIAN_VILLAGE_TELEPORT_USING_SKULL_SCEPTRE = 12

        }

    }

    override val areaTasks get() = arrayOf(
        AreaDiaryTask(
            VARROCK_ROOF_AREA,
            DiaryLevel.EASY,
            EasyTasks.FIND_HIGHEST_POINT
        ),
        AreaDiaryTask(
            SOS_LEVEL_2_AREA,
            DiaryLevel.EASY,
            EasyTasks.VISIT_SOS_LEVEL2
        ),
        AreaDiaryTask(
            CHAMPIONS_GUILD_AREA,
            DiaryLevel.MEDIUM,
            MediumTasks.CHAMPIONS_GUILD_VISIT
        )
    )

    override fun onResourceProduced(player: Player, event: ResourceProducedEvent) {
        when (player.viewport.region.id) {
            12341 -> when (event.itemId) {
                Items.RAW_TROUT_335 ->
                    finishTask(
                        player,
                        DiaryLevel.EASY,
                        EasyTasks.BARBARIAN_VILLAGE_CATCH_TROUT
                    )
            }

            12853 -> when(event.itemId) {
                Items.ADAMANT_MED_HELM_1145 ->
                    finishTask(
                        player,
                        DiaryLevel.HARD,
                        HardTasks.SMITH_ADAMANT_MED_HELM_SOUTHEAST
                    )
            }

            13108 -> when (event.itemId) {
                Items.IRON_ORE_440 ->
                    finishTask(
                        player,
                        DiaryLevel.EASY,
                        EasyTasks.MINE_IRON_SOUTHEAST
                    )
            }

            13110 -> when (event.itemId) {
                Items.LOGS_1511 -> if (event.source.id == Scenery.DYING_TREE_24168) {
                    finishTask(
                        player,
                        DiaryLevel.EASY,
                        EasyTasks.LUMBERYARD_CHOP_DYING_TREE
                    )
                }
            }

            13366 -> when (event.itemId) {
                Items.LIMESTONE_3211 ->
                    finishTask(
                        player,
                        DiaryLevel.EASY,
                        EasyTasks.PATERDOMUS_MINE_LIMESTONE
                    )
            }

            10571 -> when(event.itemId) {
                Items.EARTH_TIARA_5535 ->
                    finishTask(
                        player,
                        DiaryLevel.MEDIUM,
                        MediumTasks.CRAFT_EARTH_TIARA
                    )
            }
        }
    }

    override fun onTeleported(player: Player, event: TeleportEvent) {
        when (event.source) {
            is NPC -> if (event.method == TeleportMethod.NPC && event.source.id == NPCs.AUBURY_553) {
                finishTask(
                    player,
                    DiaryLevel.EASY,
                    EasyTasks.AUBURY_TELEPORT_ESSENCE_MINE
                )
            }

            is Item -> if(event.source.id in 11190..11194) {
                finishTask(
                    player,
                    DiaryLevel.MEDIUM,
                    MediumTasks.DIGSITE_PENDANT_TELEPORT
                )
            }
        }
    }

    override fun onInteracted(player: Player, event: InteractionEvent) {
        when (player.viewport.region.id) {
            12342 -> if (event.target.id == Scenery.TRAPDOOR_26934) {
                finishTask(
                    player,
                    DiaryLevel.EASY,
                    EasyTasks.EDGEVILLE_ENTER_DUNGEON_SOUTH
                )
            }

            12598 -> if (event.target.id == Scenery.UNDERWALL_TUNNEL_9312 && hasLevelStat(player, Skills.AGILITY, 21)) {
                finishTask(
                    player,
                    DiaryLevel.MEDIUM,
                    MediumTasks.USE_GE_UNDER_WALL_SHORTCUT
                )
            }

            12698 -> if (event.target.id == Scenery.PIPE_29370 && hasLevelStat(player, Skills.AGILITY, 51)) {
                finishTask(
                    player,
                    DiaryLevel.HARD,
                    HardTasks.USE_MOSS_GIANT_PIPE_SHORTCUT
                )
            }
        }

        if (event.option == "pickpocket" && (event.target.id == NPCs.GUARD_5920 && inBorders(player, VARROCK_PALACE_AREA)) && hasLevelStat(player, Skills.THIEVING, 40)) {
            finishTask(
                player,
                DiaryLevel.MEDIUM,
                MediumTasks.PALACE_PICKPOCKET_GUARD
            )
        }

        if (isQuestComplete(player, "Dragon Slayer")) {
            if (event.target.id == NPCs.OZIACH_747 && event.option == "trade" && inBorders(player, OZIACH_SHOP_AREA)) {
                finishTask(
                    player,
                    DiaryLevel.MEDIUM,
                    MediumTasks.OZIACH_BROWSE_STORE
                )
            }
        }

        if (event.target.id == Items.SKULL_SCEPTRE_9013 && event.option == "invoke") {
            finishTask(
                player,
                DiaryLevel.HARD,
                HardTasks.BARBARIAN_VILLAGE_TELEPORT_USING_SKULL_SCEPTRE
            )
        }
    }

    override fun onButtonClicked(player: Player, event: ButtonClickEvent) {
        player.viewport.region?.let {
            when (it.id) {
                12342 -> {
                    if (event.iface == CanoeListeners.CANOE_SHAPING_INTERFACE && event.buttonId == CanoeListeners.CANOE_SHAPING_BUTTONS[CanoeListeners.Companion.Canoes.WAKA.ordinal]) {
                        finishTask(
                            player,
                            DiaryLevel.HARD,
                            HardTasks.EDGEVILLE_MAKE_WAKA_CANOE
                        )
                    }
                }
            }
        }
    }

    override fun onDialogueOptionSelected(player: Player, event: DialogueOptionSelectionEvent) {
        when (event.dialogue) {
            is BennyDialogue -> if (event.currentStage == 14) {
                if (inInventory(player, Items.COINS_995, 50)) {
                    finishTask(
                        player,
                        DiaryLevel.EASY,
                        EasyTasks.BUY_VARROCK_HERALD
                    )
                }
            }

            is ElsieDialogue -> if (event.currentStage == 12) {
                finishTask(
                    player,
                    DiaryLevel.EASY,
                    EasyTasks.ELSIE_TELL_A_STORY
                )
            }

            is DimintheisDialogue -> if (event.currentStage == 6000) {
                finishTask(
                    player,
                    DiaryLevel.HARD,
                    HardTasks.OBTAIN_NEW_SET_OF_FAMILY_CREST_GAUNTLETS
                )
            }

            is OrlandoSmithDialogue -> {
                if (event.currentStage >= 1) {
                    finishTask(player,
                        DiaryLevel.HARD,
                        HardTasks.SPEAK_TO_ORLANDO_SMITH_153_KUDOS
                    )
                }
            }
        }
    }

    override fun onUsedWith(player: Player, event: UseWithEvent) {
        when (event.used) {
            in Bones.array -> if (event.with in STRAY_DOGS) {
                finishTask(
                    player,
                    DiaryLevel.EASY,
                    EasyTasks.GIVE_STRAY_DOG_A_BONE
                )
            }
        }
        when {
            inBorders(player, AIR_OBELISK_AREA) -> {
                if (event.used == Items.BATTLESTAFF_1391 && event.with == Items.AIR_ORB_573) {
                    whenTaskRequirementFulfilled(player, ATTRIBUTE_CRAFT_AIR_BATTLESTAFF) {
                        finishTask(
                            player,
                            DiaryLevel.HARD,
                            HardTasks.CRAFT_AIR_BATTLESTAFF
                        )
                    }
                }
            }
        }
    }

    override fun onInterfaceOpened(player: Player, event: InterfaceOpenEvent) {
        when (event.component.id) {
            Components.THESSALIA_CLOTHES_MALE_591,
            Components.THESSALIA_CLOTHES_FEMALE_594 -> if (player.viewport.region.id == 12853) {
                finishTask(
                    player,
                    DiaryLevel.EASY,
                    EasyTasks.THESSALIA_BROWSE_CLOTHES
                )
            }
        }
    }

    override fun onSpellCast(player: Player, event: SpellCastEvent) {
        if (event.spellBook == SpellBookManager.SpellBook.MODERN && event.spellId == 15 && getStatLevel(player, Skills.MAGIC) >= 25) {
            finishTask(
                player,
                DiaryLevel.MEDIUM,
                MediumTasks.CAST_VARROCK_TELEPORT_SPELL
            )
        }
        when {
            inBorders(player, AIR_OBELISK_AREA) -> {
                if (event.spellBook == SpellBookManager.SpellBook.MODERN && event.spellId == 49) {
                    fulfillTaskRequirement(
                        player,
                        DiaryLevel.HARD,
                        HardTasks.CRAFT_AIR_BATTLESTAFF,
                        ATTRIBUTE_CRAFT_AIR_BATTLESTAFF
                    )
                }
            }
        }
    }

    override fun onFairyRingDialed(player: Player, event: FairyRingDialEvent) {
        if (event.fairyRing == FairyRing.DKR) {
            finishTask(
                player,
                DiaryLevel.MEDIUM,
                MediumTasks.DIAL_FAIRY_RING_WEST
            )
        }
    }

    override fun onPickedUp(player: Player, event: PickUpEvent) {
        when {
            inBorders(player, FARMING_PATCH_AREA) -> {
                if (event.itemId == Items.POISON_IVY_BERRIES_6018) {
                    finishTask(
                        player,
                        DiaryLevel.HARD,
                        HardTasks.PICK_POISON_IVY_FARMING_PATCH
                    )
                }
            }
        }
    }

    override fun onDialogueOpened(player: Player, event: DialogueOpenEvent) {
        when (event.dialogue) {
            is CuratorHaigHalenDialogue -> {
                if (getQuestPoints(player) == 50) {
                    finishTask(
                        player,
                        DiaryLevel.EASY,
                        EasyTasks.SPEAK_TO_HAIG_HALEN_50QP
                    )
                }
            }
        }
    }

}
