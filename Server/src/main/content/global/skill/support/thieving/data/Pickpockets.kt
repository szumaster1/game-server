package content.global.skill.support.thieving.data

import core.api.consts.Items
import core.api.consts.NPCs
import core.api.utils.WeightBasedTable
import core.api.utils.WeightedItem
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.tools.RandomFunction
import java.util.stream.IntStream

/**
 * Pickpockets
 *
 * @param ids An array of integers representing the IDs of the pickpocketing targets.
 * @param requiredLevel The level required to attempt pickpocketing.
 * @param low The minimum amount of loot that can be obtained.
 * @param high The maximum amount of loot that can be obtained.
 * @param experience The experience gained from successful pickpocketing.
 * @param stunDamageMin The minimum damage inflicted when stunning the target.
 * @param stunDamageMax The maximum damage inflicted when stunning the target.
 * @param stunTime The duration for which the target is stunned.
 * @param message An optional message to display upon successful pickpocketing.
 * @param table A WeightBasedTable that determines the probability of success based on weights.
 */
enum class Pickpockets(
    val ids: IntArray, // Array of IDs for the pickpocketing targets
    val requiredLevel: Int, // Level required to perform the pickpocketing action
    val low: Double, // Minimum loot value obtainable
    val high: Double, // Maximum loot value obtainable
    val experience: Double, // Experience points awarded for successful pickpocketing
    val stunDamageMin: Int, // Minimum damage dealt to stun the target
    val stunDamageMax: Int, // Maximum damage dealt to stun the target
    val stunTime: Int, // Duration of the stun effect on the target
    val message: String?, // Optional message displayed upon success
    val table: WeightBasedTable // Table that defines success probabilities based on weights
) {
    /**
     * Man.
     */
    MAN(
        ids = intArrayOf(NPCs.MAN_1, NPCs.MAN_2, NPCs.MAN_3, NPCs.WOMAN_4, NPCs.WOMAN_5, NPCs.WOMAN_6, NPCs.MAN_16, NPCs.MAN_24, NPCs.WOMAN_25, NPCs.MAN_170, NPCs.MAN_1086, NPCs.MAN_3224, NPCs.MAN_3915, NPCs.WOMAN_3226, NPCs.WOMAN_3227, NPCs.WOMAN_5924, NPCs.MAN_5923),
        requiredLevel = 1,
        low = 180.0,
        high = 240.0,
        experience = 8.0,
        stunDamageMin = 1,
        stunDamageMax = 1,
        stunTime = 5,
        message = "What do you think you're doing?",
        table = WeightBasedTable.create(
            WeightedItem(id = Items.COINS_995, minAmt = 3, maxAmt = 3, weight = 1.0, guaranteed = true))
    ),

    /**
     * Curator Haig Helen.
     */
    CURATOR_HAIG_HELEN(
        ids = intArrayOf(NPCs.CURATOR_HAIG_HALEN_646),
        requiredLevel = 1,
        low = 180.0,
        high = 240.0,
        experience = 8.0,
        stunDamageMin = 1,
        stunDamageMax = 1,
        stunTime = 5,
        message = null,
        table = WeightBasedTable.create(
            WeightedItem(id = Items.DISPLAY_CABINET_KEY_4617, minAmt = 1, maxAmt = 1, weight = 1.0, guaranteed = true))
    ),

    /**
     * Gang Of Thieves.
     */
    GANG_OF_THIEVES(
        ids = intArrayOf(NPCs.CUFFS_3237, NPCs.NARF_3238, NPCs.RUSTY_3239, NPCs.JEFF_3240),
        requiredLevel = 1,
        low = 180.0,
        high = 240.0,
        experience = 8.0,
        stunDamageMin = 1,
        stunDamageMax = 1,
        stunTime = 5,
        message = "What do you think you're doing?",
        table = WeightBasedTable.create(
            WeightedItem(id = Items.COINS_995, minAmt = 3, maxAmt = 3, weight = 1.0, guaranteed = true))
    ),

    /**
     * Tower Of Life quest related NPC's.
     */
    TOWER_OF_LIFE_QUEST_NPCS(
        ids = intArrayOf(NPCs.BLACK_EYE_5589, NPCs.NO_FINGERS_5590, NPCs.GUMMY_5591, NPCs.THE_GUNS_5592),
        requiredLevel = 1,
        low = 180.0,
        high = 240.0,
        experience = 8.0,
        stunDamageMin = 1,
        stunDamageMax = 1,
        stunTime = 5,
        message = null,
        table = WeightBasedTable.create(
            WeightedItem(id = Items.TRIANGLE_SANDWICH_6962, minAmt = 1, maxAmt = 1, weight = 1.0, guaranteed = true))
    ),

    /**
     * Farmer.
     */
    FARMER(
        ids = intArrayOf(NPCs.FARMER_7, NPCs.FARMER_1757, NPCs.FARMER_1758),
        requiredLevel = 10,
        low = 180.0,
        high = 240.0,
        experience = 14.5,
        stunDamageMin = 1,
        stunDamageMax = 1,
        stunTime = 5,
        message = "What do you think you're doing?",
        table = WeightBasedTable.create(
            WeightedItem(id = Items.COINS_995, minAmt = 9, maxAmt = 9, weight = 1.0, guaranteed = true),
            WeightedItem(id = Items.POTATO_SEED_5318, minAmt = 1, maxAmt = 1, weight = 1.0, guaranteed = true)
        )
    ),

    /**
     * Male H.A.M. Member.
     */
    MALE_HAM_MEMBER(
        ids = intArrayOf(NPCs.HAM_MEMBER_1714),
        requiredLevel = 20,
        low = 117.0,
        high = 240.0,
        experience = 22.5,
        stunDamageMin = 1,
        stunDamageMax = 3,
        stunTime = 4,
        message = "What do you think you're doing?",
        table = WeightBasedTable.create(
            WeightedItem(id = Items.COINS_995, minAmt = 1, maxAmt = 21, weight = 5.5),
            WeightedItem(id = Items.TINDERBOX_590, minAmt = 1, maxAmt = 1, weight = 5.0),
            WeightedItem(id = Items.LOGS_1511, minAmt = 1, maxAmt = 1, weight = 7.0),
            WeightedItem(id = Items.UNCUT_JADE_1627, minAmt = 1, maxAmt = 1, weight = 2.5),
            WeightedItem(id = Items.UNCUT_OPAL_1625, minAmt = 1, maxAmt = 1, weight = 2.5),
            WeightedItem(id = Items.RAW_ANCHOVIES_321, minAmt = 1, maxAmt = 1, weight = 7.0),
            WeightedItem(id = Items.RAW_CHICKEN_2138, minAmt = 1, maxAmt = 1, weight = 3.5),
            WeightedItem(id = Items.HAM_CLOAK_4304, minAmt = 1, maxAmt = 1, weight = 0.25),
            WeightedItem(id = Items.HAM_HOOD_4302, minAmt = 1, maxAmt = 1, weight = 0.25),
            WeightedItem(id = Items.HAM_LOGO_4306, minAmt = 1, maxAmt = 1, weight = 0.25),
            WeightedItem(id = Items.HAM_ROBE_4300, minAmt = 1, maxAmt = 1, weight = 0.25),
            WeightedItem(id = Items.HAM_SHIRT_4298, minAmt = 1, maxAmt = 1, weight = 0.25),
            WeightedItem(id = Items.BOOTS_4310, minAmt = 1, maxAmt = 1, weight = 1.0),
            WeightedItem(id = Items.GLOVES_4308, minAmt = 1, maxAmt = 1, weight = 1.0),
            WeightedItem(id = Items.BRONZE_PICKAXE_1265, minAmt = 1, maxAmt = 1, weight = 5.0),
            WeightedItem(id = Items.IRON_PICKAXE_1267, minAmt = 1, maxAmt = 1, weight = 5.0),
            WeightedItem(id = Items.STEEL_PICKAXE_1269, minAmt = 1, maxAmt = 1, weight = 2.5),
            WeightedItem(id = Items.GRIMY_GUAM_199, minAmt = 1, maxAmt = 1, weight = 2.0),
            WeightedItem(id = Items.GRIMY_HARRALANDER_205, minAmt = 1, maxAmt = 1, weight = 2.0),
            WeightedItem(id = Items.GRIMY_KWUARM_213, minAmt = 1, maxAmt = 1, weight = 2.0),
            WeightedItem(id = Items.GRIMY_MARRENTILL_201, minAmt = 1, maxAmt = 1, weight = 1.5),
            WeightedItem(id = Items.RUSTY_SWORD_686, minAmt = 1, maxAmt = 1, weight = 3.5),
            WeightedItem(id = Items.BROKEN_ARMOUR_698, minAmt = 1, maxAmt = 1, weight = 3.5),
            WeightedItem(id = Items.BROKEN_STAFF_689, minAmt = 1, maxAmt = 1, weight = 3.2),
            WeightedItem(id = Items.BROKEN_ARROW_687, minAmt = 1, maxAmt = 1, weight = 3.1),
            WeightedItem(id = Items.BUTTONS_688, minAmt = 1, maxAmt = 1, weight = 3.0)
        ).insertEasyClue(1.0)
    ),

    /**
     * Female H.A.M. Member.
     */
    FEMALE_HAM_MEMBER(
        ids = intArrayOf(NPCs.HAM_MEMBER_1715),
        requiredLevel = 15,
        low = 135.0,
        high = 240.0,
        experience = 18.5,
        stunDamageMin = 1,
        stunDamageMax = 3,
        stunTime = 4,
        message = "Stop! @name is a thief!",
        table = WeightBasedTable.create(WeightedItem(Items.COINS_995, 1, 21, 5.5), WeightedItem(Items.TINDERBOX_590, 1, 1, 5.0), WeightedItem(Items.LOGS_1511, 1, 1, 7.0), WeightedItem(Items.UNCUT_JADE_1627, 1, 1, 2.5), WeightedItem(Items.UNCUT_OPAL_1625, 1, 1, 2.5), WeightedItem(Items.RAW_ANCHOVIES_321, 1, 1, 7.0), WeightedItem(Items.RAW_CHICKEN_2138, 1, 1, 3.5), WeightedItem(Items.HAM_CLOAK_4304, 1, 1, 0.25), WeightedItem(Items.HAM_HOOD_4302, 1, 1, 0.25), WeightedItem(Items.HAM_LOGO_4306, 1, 1, 0.25), WeightedItem(Items.HAM_SHIRT_4298, 1, 1, 0.25), WeightedItem(Items.HAM_ROBE_4300, 1, 1, 0.25), WeightedItem(Items.BOOTS_4310, 1, 1, 1.0), WeightedItem(Items.GLOVES_4308, 1, 1, 1.0), WeightedItem(Items.BRONZE_PICKAXE_1265, 1, 1, 5.0), WeightedItem(Items.IRON_PICKAXE_1267, 1, 1, 5.0), WeightedItem(Items.STEEL_PICKAXE_1269, 1, 1, 2.5), WeightedItem(Items.GRIMY_GUAM_199, 1, 1, 2.0), WeightedItem(Items.GRIMY_HARRALANDER_205, 1, 1, 2.0), WeightedItem(Items.GRIMY_KWUARM_213, 1, 1, 2.0), WeightedItem(Items.GRIMY_MARRENTILL_201, 1, 1, 1.5), WeightedItem(Items.RUSTY_SWORD_686, 1, 1, 3.5), WeightedItem(Items.BROKEN_ARMOUR_698, 1, 1, 3.5), WeightedItem(Items.BROKEN_STAFF_689, 1, 1, 3.2), WeightedItem(Items.BROKEN_ARROW_687, 1, 1, 3.1), WeightedItem(Items.BUTTONS_688, 1, 1, 3.0)).insertEasyClue(1.0)
    ),

    /**
     * Warrior.
     */
    WARRIOR(
        ids = intArrayOf(NPCs.WARRIOR_WOMAN_15, NPCs.AL_KHARID_WARRIOR_18),
        requiredLevel = 25,
        low = 84.0,
        high = 240.0,
        experience = 26.0,
        stunDamageMin = 2,
        stunDamageMax = 2,
        stunTime = 5,
        message = "What do you think you're doing?",
        table = WeightBasedTable.create(
            WeightedItem(id = Items.COINS_995, minAmt = 18, maxAmt = 18, weight = 1.0, guaranteed = true))
    ),

    /**
     * Rogue.
     */
    ROGUE(
        ids = intArrayOf(NPCs.ROGUE_187, NPCs.ROGUE_GUARD_2267, NPCs.ROGUE_GUARD_2268, NPCs.ROGUE_GUARD_2269, NPCs.ROGUE_8122),
        requiredLevel = 32,
        low = 74.0,
        high = 240.0,
        experience = 35.5,
        stunDamageMin = 2,
        stunDamageMax = 2,
        stunTime = 5,
        message = "What do you think you're doing?",
        table = WeightBasedTable.create(
            WeightedItem(id = Items.COINS_995, minAmt = 25, maxAmt = 40, weight = 5.0, guaranteed = true),
            WeightedItem(id = Items.JUG_OF_WINE_1993, minAmt = 1, maxAmt = 1, weight = 6.0),
            WeightedItem(id = Items.AIR_RUNE_556, minAmt = 8, maxAmt = 8, weight = 8.0),
            WeightedItem(id = Items.LOCKPICK_1523, minAmt = 1, maxAmt = 1, weight = 5.0),
            WeightedItem(id = Items.IRON_DAGGERP_1219, minAmt = 1, maxAmt = 1, weight = 1.0)
        )
    ),

    /**
     * Cave Goblin.
     */
    CAVE_GOBLIN(
        ids = IntStream.rangeClosed(NPCs.CAVE_GOBLIN_5752, NPCs.CAVE_GOBLIN_5768).toArray(),
        requiredLevel = 36,
        low = 72.0,
        high = 240.0,
        experience = 40.0,
        stunDamageMin = 1,
        stunDamageMax = 1,
        stunTime = 5,
        message = null,
        table = WeightBasedTable.create(
            WeightedItem(id = Items.BAT_SHISH_10964, minAmt = 1, maxAmt = 1, weight = 2.5),
            WeightedItem(id = Items.FINGERS_10965, minAmt = 1, maxAmt = 1, weight = 2.5),
            WeightedItem(id = Items.COATED_FROGS_LEGS_10963, minAmt = 1, maxAmt = 1, weight = 2.5),
            WeightedItem(id = Items.COINS_995, minAmt = 30, maxAmt = 30, weight = 6.5),
            WeightedItem(id = Items.OIL_LAMP_4522, minAmt = 1, maxAmt = 1, weight = 0.5),
            WeightedItem(id = Items.BULLSEYE_LANTERN_4544, minAmt = 1, maxAmt = 1, weight = 0.5),
            WeightedItem(id = Items.UNLIT_TORCH_596, minAmt = 1, maxAmt = 1, weight = 0.5),
            WeightedItem(id = Items.TINDERBOX_590, minAmt = 1, maxAmt = 1, weight = 0.5),
            WeightedItem(id = Items.SWAMP_TAR_1939, minAmt = 1, maxAmt = 1, weight = 0.5),
            WeightedItem(id = Items.IRON_ORE_441, minAmt = 1, maxAmt = 4, weight = 0.25)
        )
    ),

    /**
     * Master Farmer.
     */
    MASTER_FARMER(
        ids = intArrayOf(NPCs.MASTER_FARMER_2234, NPCs.MASTER_FARMER_2235, NPCs.MARTIN_THE_MASTER_GARDENER_3299),
        requiredLevel = 38,
        low = 90.0,
        high = 240.0,
        experience = 43.0,
        stunDamageMin = 3,
        stunDamageMax = 3,
        stunTime = 5,
        message = "Cor blimey, mate! What are ye doing in me pockets?",
        table = WeightBasedTable.create(
            WeightedItem(id = Items.POTATO_SEED_5318, minAmt = 1, maxAmt = 3, weight = 50.0),
            WeightedItem(id = Items.ONION_SEED_5319, minAmt = 1, maxAmt = 3, weight = 50.0),
            WeightedItem(id = Items.CABBAGE_SEED_5324, minAmt = 1, maxAmt = 3, weight = 50.0),
            WeightedItem(id = Items.TOMATO_SEED_5322, minAmt = 1, maxAmt = 2, weight = 50.0),
            WeightedItem(id = Items.SWEETCORN_SEED_5320, minAmt = 1, maxAmt = 2, weight = 50.0),
            WeightedItem(id = Items.STRAWBERRY_SEED_5323, minAmt = 1, maxAmt = 1, weight = 25.0),
            WeightedItem(id = Items.WATERMELON_SEED_5321, minAmt = 1, maxAmt = 1, weight = 8.0),
            WeightedItem(id = Items.BARLEY_SEED_5305, minAmt = 1, maxAmt = 4, weight = 50.0),
            WeightedItem(id = Items.HAMMERSTONE_SEED_5307, minAmt = 1, maxAmt = 3, weight = 50.0),
            WeightedItem(id = Items.ASGARNIAN_SEED_5308, minAmt = 1, maxAmt = 3, weight = 50.0),
            WeightedItem(id = Items.JUTE_SEED_5306, minAmt = 1, maxAmt = 3, weight = 50.0),
            WeightedItem(id = Items.YANILLIAN_SEED_5309, minAmt = 1, maxAmt = 2, weight = 25.0),
            WeightedItem(id = Items.KRANDORIAN_SEED_5310, minAmt = 1, maxAmt = 2, weight = 25.0),
            WeightedItem(id = Items.WILDBLOOD_SEED_5311, minAmt = 1, maxAmt = 1, weight = 8.0),
            WeightedItem(id = Items.MARIGOLD_SEED_5096, minAmt = 1, maxAmt = 1, weight = 50.0),
            WeightedItem(id = Items.NASTURTIUM_SEED_5098, minAmt = 1, maxAmt = 1, weight = 50.0),
            WeightedItem(id = Items.ROSEMARY_SEED_5097, minAmt = 1, maxAmt = 1, weight = 50.0),
            WeightedItem(id = Items.WOAD_SEED_5099, minAmt = 1, maxAmt = 1, weight = 50.0),
            WeightedItem(id = Items.LIMPWURT_SEED_5100, minAmt = 1, maxAmt = 1, weight = 25.0),
            WeightedItem(id = Items.REDBERRY_SEED_5101, minAmt = 1, maxAmt = 1, weight = 50.0),
            WeightedItem(id = Items.CADAVABERRY_SEED_5102, minAmt = 1, maxAmt = 1, weight = 50.0),
            WeightedItem(id = Items.DWELLBERRY_SEED_5103, minAmt = 1, maxAmt = 1, weight = 25.0),
            WeightedItem(id = Items.JANGERBERRY_SEED_5104, minAmt = 1, maxAmt = 1, weight = 25.0),
            WeightedItem(id = Items.WHITEBERRY_SEED_5105, minAmt = 1, maxAmt = 1, weight = 25.0),
            WeightedItem(id = Items.GUAM_SEED_5291, minAmt = 1, maxAmt = 1, weight = 50.0),
            WeightedItem(id = Items.MARRENTILL_SEED_5292, minAmt = 1, maxAmt = 1, weight = 50.0),
            WeightedItem(id = Items.TARROMIN_SEED_5293, minAmt = 1, maxAmt = 1, weight = 50.0),
            WeightedItem(id = Items.HARRALANDER_SEED_5294, minAmt = 1, maxAmt = 1, weight = 25.0),
            WeightedItem(id = Items.RANARR_SEED_5295, minAmt = 1, maxAmt = 1, weight = 8.0),
            WeightedItem(id = Items.TOADFLAX_SEED_5296, minAmt = 1, maxAmt = 1, weight = 8.0),
            WeightedItem(id = Items.IRIT_SEED_5297, minAmt = 1, maxAmt = 1, weight = 8.0),
            WeightedItem(id = Items.AVANTOE_SEED_5298, minAmt = 1, maxAmt = 1, weight = 8.0),
            WeightedItem(id = Items.KWUARM_SEED_5299, minAmt = 1, maxAmt = 1, weight = 8.0),
            WeightedItem(id = Items.SNAPDRAGON_SEED_5300, minAmt = 1, maxAmt = 1, weight = 5.0),
            WeightedItem(id = Items.CADANTINE_SEED_5301, minAmt = 1, maxAmt = 1, weight = 8.0),
            WeightedItem(id = Items.LANTADYME_SEED_5302, minAmt = 1, maxAmt = 1, weight = 5.0),
            WeightedItem(id = Items.DWARF_WEED_SEED_5303, minAmt = 1, maxAmt = 1, weight = 5.0),
            WeightedItem(id = Items.TORSTOL_SEED_5304, minAmt = 1, maxAmt = 1, weight = 5.0)
        )
    ),

    /**
     * Guard.
     */
    GUARD(
        ids = intArrayOf(NPCs.GUARD_9, NPCs.GUARD_32, NPCs.GUARD_206, NPCs.GUARD_296, NPCs.GUARD_297, NPCs.GUARD_298, NPCs.GUARD_299, NPCs.GUARD_344, NPCs.GUARD_345, NPCs.GUARD_346, NPCs.GUARD_368, NPCs.GUARD_678, NPCs.GUARD_812, NPCs.GUARD_9, NPCs.GUARD_32, NPCs.GUARD_296, NPCs.GUARD_297, NPCs.GUARD_298, NPCs.GUARD_299, NPCs.GUARD_2699, NPCs.GUARD_2700, NPCs.GUARD_2701, NPCs.GUARD_2702, NPCs.GUARD_2703, NPCs.GUARD_3228, NPCs.GUARD_3229, NPCs.GUARD_3230, NPCs.GUARD_3231, NPCs.GUARD_3232, NPCs.GUARD_3233, NPCs.GUARD_3241, NPCs.GUARD_3407, NPCs.GUARD_3408, NPCs.GUARD_4307, NPCs.GUARD_4308, NPCs.GUARD_4309, NPCs.GUARD_4310, NPCs.GUARD_4311, NPCs.GUARD_5919, NPCs.GUARD_5920),
        requiredLevel = 40,
        low = 50.0,
        high = 240.0,
        experience = 46.5,
        stunDamageMin = 2,
        stunDamageMax = 2,
        stunTime = 5,
        message = "What do you think you're doing?",
        table = WeightBasedTable.create(
            WeightedItem(id = Items.COINS_995, minAmt = 30, maxAmt = 30, weight = 1.0, guaranteed = true)
        )
    ),

    /**
     * Fremennik Citizen.
     */
    FREMENNIK_CITIZEN(
        ids = intArrayOf(NPCs.AGNAR_1305, NPCs.FREIDIR_1306, NPCs.BORROKAR_1307, NPCs.LANZIG_1308, NPCs.PONTAK_1309, NPCs.FREYGERD_1310, NPCs.LENSA_1311, NPCs.JENNELLA_1312, NPCs.SASSILIK_1313, NPCs.INGA_1314),
        requiredLevel = 45,
        low = 65.0,
        high = 240.0,
        experience = 65.0,
        stunDamageMin = 2,
        stunDamageMax = 2,
        stunTime = 5,
        message = "You stay away from me outerlander!",
        table = WeightBasedTable.create(
            WeightedItem(id = Items.COINS_995, minAmt = 40, maxAmt = 40, weight = 1.0, guaranteed = true)
        )
    ),

    /**
     * Bearded Bandit.
     */
    BEARDED_BANDIT(
        ids = intArrayOf(NPCs.BANDIT_1880, NPCs.BANDIT_1881, NPCs.BANDIT_6174, NPCs.BANDIT_6388),
        requiredLevel = 45,
        low = 50.0,
        high = 240.0,
        experience = 65.0,
        stunDamageMin = 5,
        stunDamageMax = 5,
        stunTime = 5,
        message = "What do you think you're doing?",
        table = WeightBasedTable.create(
            WeightedItem(id = Items.ANTIPOISON4_2446, minAmt = 1, maxAmt = 1, weight = 1.0),
            WeightedItem(id = Items.LOCKPICK_1523, minAmt = 1, maxAmt = 1, weight = 2.0),
            WeightedItem(id = Items.COINS_995, minAmt = 1, maxAmt = 1, weight = 4.0)
        )
    ),

    /**
     * Desert Bandit.
     */
    DESERT_BANDIT(
        ids = intArrayOf(NPCs.BANDIT_1926, NPCs.BARTENDER_1921),
        requiredLevel = 53,
        low = 50.0,
        high = 240.0,
        experience = 79.5,
        stunDamageMin = 3,
        stunDamageMax = 3,
        stunTime = 5,
        message = "I'll kill you for that!",
        table = WeightBasedTable.create(
            WeightedItem(Items.COINS_995, 50, 1, 3.0),
            WeightedItem(Items.ANTIPOISON4_2446, 1, 1, 1.0),
            WeightedItem(Items.LOCKPICK_1523, 1, 1, 1.0)
        )
    ),

    /**
     * Knight Of Adrougne.
     */
    KNIGHT_OF_ADROUGNE(
        ids = intArrayOf(NPCs.KNIGHT_OF_ARDOUGNE_23, NPCs.KNIGHT_OF_ARDOUGNE_26),
        requiredLevel = 55,
        low = 50.0,
        high = 240.0,
        experience = 84.3,
        stunDamageMin = 3,
        stunDamageMax = 3,
        stunTime = 6,
        message = null,
        table = WeightBasedTable.create(
            WeightedItem(Items.COINS_995, 50, 50, 1.0, true)
        )
    ),

    /**
     * Yanille Watchman.
     */
    YANILLE_WATCHMAN(
        ids = intArrayOf(NPCs.WATCHMAN_34),
        requiredLevel = 65,
        low = 50.0,
        high = 240.0,
        experience = 137.5,
        stunDamageMin = 3,
        stunDamageMax = 3,
        stunTime = 5,
        message = "What do you think you're doing?",
        table = WeightBasedTable.create(
            WeightedItem(Items.COINS_995, 60, 60, 1.0, true),
            WeightedItem(Items.BREAD_2309, 1, 1, 1.0, true)
        )
    ),

    /**
     * Menaphite Thug.
     */
    MENAPHITE_THUG(
        ids = intArrayOf(NPCs.MENAPHITE_THUG_1905),
        requiredLevel = 65,
        low = 50.0,
        high = 240.0,
        experience = 137.5,
        stunDamageMin = 5,
        stunDamageMax = 5,
        stunTime = 5,
        message = "I'll kill you for that!",
        table = WeightBasedTable.create(
            WeightedItem(Items.COINS_995, 60, 60, 1.0, true)
        )
    ),

    /**
     * Paladin.
     */
    PALADIN(
        ids = intArrayOf(NPCs.PALADIN_20, NPCs.PALADIN_2256),
        requiredLevel = 70,
        low = 50.0,
        high = 150.0,
        experience = 151.75,
        stunDamageMin = 3,
        stunDamageMax = 3,
        stunTime = 5,
        message = "Hey! Get your hands off there!",
        table = WeightBasedTable.create(
            WeightedItem(Items.COINS_995, 80, 80, 1.0, true),
            WeightedItem(Items.CHAOS_RUNE_562, 2, 2, 1.0, true)
        )
    ),

    /**
     * Gnome.
     */
    GNOME(
        ids = intArrayOf(NPCs.GNOME_66, NPCs.GNOME_67, NPCs.GNOME_68, NPCs.GNOME_WOMAN_168, NPCs.GNOME_WOMAN_169, NPCs.GNOME_2249, NPCs.GNOME_2250, NPCs.GNOME_2251, NPCs.GNOME_2371, NPCs.GNOME_2649, NPCs.GNOME_2650, NPCs.GNOME_6002, NPCs.GNOME_6004),
        requiredLevel = 75,
        low = 8.0,
        high = 120.0,
        experience = 198.5,
        stunDamageMin = 1,
        stunDamageMax = 1,
        stunTime = 5,
        message = "What do you think you're doing?",
        table = WeightBasedTable.create(
            WeightedItem(id = Items.COINS_995, minAmt = 300, maxAmt = 300, weight = 2.5),
            WeightedItem(id = Items.EARTH_RUNE_557, minAmt = 1, maxAmt = 1, weight = 3.5),
            WeightedItem(id = Items.GOLD_ORE_445, minAmt = 1, maxAmt = 1, weight = 1.0),
            WeightedItem(id = Items.FIRE_ORB_569, minAmt = 1, maxAmt = 1, weight = 5.0),
            WeightedItem(id = Items.SWAMP_TOAD_2150, minAmt = 1, maxAmt = 1, weight = 8.0),
            WeightedItem(id = Items.KING_WORM_2162, minAmt = 1, maxAmt = 1, weight = 9.0)
        )
    ),

    /**
     * Hero.
     */
    HERO(
        ids = intArrayOf(NPCs.HERO_21),
        requiredLevel = 80,
        low = 6.0,
        high = 100.0,
        experience = 273.3,
        stunDamageMin = 6,
        stunDamageMax = 6,
        stunTime = 6,
        message = "What do you think you're doing?",
        table = WeightBasedTable.create(
            WeightedItem(id = Items.COINS_995, minAmt = 200, maxAmt = 300, weight = 1.5),
            WeightedItem(id = Items.DEATH_RUNE_560, minAmt = 2, maxAmt = 2, weight = 1.0),
            WeightedItem(id = Items.BLOOD_RUNE_565, minAmt = 1, maxAmt = 1, weight = 0.5),
            WeightedItem(id = Items.FIRE_ORB_569, minAmt = 1, maxAmt = 1, weight = 2.5),
            WeightedItem(id = Items.DIAMOND_1601, minAmt = 1, maxAmt = 1, weight = 2.0),
            WeightedItem(id = Items.GOLD_ORE_444, minAmt = 1, maxAmt = 1, weight = 1.5),
            WeightedItem(id = Items.JUG_OF_WINE_1993, minAmt = 1, maxAmt = 1, weight = 3.0)
        )
    );


    companion object {
        val idMap = HashMap<Int, Pickpockets>(values().size * 5)

        init {
            values().forEach {
                it.ids.forEach { id -> idMap[id] = it }
            }
        }

        fun forID(id: Int): Pickpockets? {
            return idMap[id]
        }
    }

    /**
     * Get success chance
     *
     * @param player The player whose success chance is to be calculated
     * @return The calculated success chance as a Double
     */
    fun getSuccessChance(player: Player): Double {
        return RandomFunction.getSkillSuccessChance(low, high, player.skills.getLevel(Skills.THIEVING))
    }

}
