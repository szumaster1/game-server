package content.global.skill.combat.summoning

import core.game.node.item.Item

/**
 * Summoning pouch
 *
 * @param slot The inventory slot where the pouch is stored
 * @param pouchId Unique identifier for the pouch
 * @param levelRequired The level required to use the pouch
 * @param createExperience The experience gained when creating the pouch
 * @param npcId Identifier for the NPC associated with the pouch
 * @param summonExperience The experience gained when summoning from the pouch
 * @param summonCost The cost in resources to summon from the pouch
 * @param peaceful Indicates if the summon is peaceful or aggressive
 * @param items Vararg parameter representing the items contained in the pouch
 */
enum class SummoningPouch(
    val slot: Int,                  // The inventory slot where the pouch is stored
    val pouchId: Int,               // Unique identifier for the pouch
    val levelRequired: Int,         // The level required to use the pouch
    val createExperience: Double,   // The experience gained when creating the pouch
    val npcId: Int,                 // Identifier for the NPC associated with the pouch
    val summonExperience: Double,   // The experience gained when summoning from the pouch
    val summonCost: Int,            // The cost in resources to summon from the pouch
    val peaceful: Boolean,          // Indicates if the summon is peaceful or aggressive
    vararg items: Item              // Vararg parameter representing the items contained in the pouch
) {
    /**
     * Spirit Wolf Pouch.
     */
    SPIRIT_WOLF_POUCH(0, 12047, 1, 4.8, 6829, 0.1, 1, false, Item(12158), Item(12155), Item(2859), Item(12183, 7)),

    /**
     * Dreadfowl Pouch.
     */
    DREADFOWL_POUCH(1, 12043, 4, 9.3, 6825, 0.1, 1, false, Item(12158), Item(12155), Item(2138), Item(12183, 8)),

    /**
     * Spirit Spider Pouch.
     */
    SPIRIT_SPIDER_POUCH(2, 12059, 10, 12.6, 6841, 0.2, 2, false, Item(12158), Item(12155), Item(6291), Item(12183, 8)),

    /**
     * Thorny Snail Pouch.
     */
    THORNY_SNAIL_POUCH(3, 12019, 13, 12.6, 6806, 0.2, 2, false, Item(12158), Item(12155), Item(3363), Item(12183, 9)),

    /**
     * Granite Crab Pouch.
     */
    GRANITE_CRAB_POUCH(4, 12009, 16, 21.6, 6796, 0.2, 2, false, Item(12158), Item(12155), Item(440), Item(12183, 7)),

    /**
     * Spirit Mosquito Pouch.
     */
    SPIRIT_MOSQUITO_POUCH(5, 12778, 17, 46.5, 7331, 0.5, 2, false, Item(12158), Item(12155), Item(6319), Item(12183, 1)),

    /**
     * Desert Wyrm Pouch.
     */
    DESERT_WYRM_POUCH(6, 12049, 18, 31.2, 6831, 0.4, 1, false, Item(12159), Item(12155), Item(1783), Item(12183, 45)),

    /**
     * Spirit Scorpion Pouch.
     */
    SPIRIT_SCORPION_POUCH(7, 12055, 19, 83.2, 6837, 0.9, 2, false, Item(12160), Item(12155), Item(3095), Item(12183, 57)),

    /**
     * Spirit Tz Kih Pouch.
     */
    SPIRIT_TZ_KIH_POUCH(8, 12808, 22, 96.8, 7361, 1.1, 3, false, Item(12160), Item(12168), Item(12155), Item(12183, 64)),

    /**
     * Albino Rat Pouch.
     */
    ALBINO_RAT_POUCH(9, 12067, 23, 202.4, 6847, 2.3, 1, false, Item(12163), Item(12155), Item(2134), Item(12183, 75)),

    /**
     * Spirit Kalphite Pouch.
     */
    SPIRIT_KALPHITE_POUCH(10, 12063, 25, 220.0, 6994, 2.5, 3, false, Item(12163), Item(12155), Item(3138), Item(12183, 51)),

    /**
     * Compost Mound Pouch.
     */
    COMPOST_MOUND_POUCH(11, 12091, 28, 49.8, 6871, 0.6, 6, false, Item(12159), Item(12155), Item(6032), Item(12183, 47)),

    /**
     * Giant Chinchompa Pouch.
     */
    GIANT_CHINCHOMPA_POUCH(12, 12800, 29, 255.2, 7353, 2.9, 1, false, Item(12163), Item(12155), Item(10033), Item(12183, 84)),

    /**
     * Vampire Bat Pouch.
     */
    VAMPIRE_BAT_POUCH(13, 12053, 31, 136.0, 6835, 1.5, 4, false, Item(12160), Item(12155), Item(3325), Item(12183, 81)),

    /**
     * Honey Badger Pouch.
     */
    HONEY_BADGER_POUCH(14, 12065, 32, 140.8, 6845, 1.6, 4, false, Item(12160), Item(12155), Item(12156), Item(12183, 84)),

    /**
     * Beaver Pouch.
     */
    BEAVER_POUCH(15, 12021, 33, 57.6, 6808, 0.7, 4, true, Item(12159), Item(12155), Item(1519), Item(12183, 72)),

    /**
     * Void Ravager Pouch.
     */
    VOID_RAVAGER_POUCH(16, 12818, 34, 59.6, 7370, 0.7, 4, false, Item(12159), Item(12164), Item(12155), Item(12183, 74)),

    /**
     * Void Spinner Pouch.
     */
    VOID_SPINNER_POUCH(17, 12780, 34, 59.6, 7333, 0.7, 4, true, Item(12163), Item(12166), Item(12155), Item(12183, 74)),

    /**
     * Void Torcher Pouch.
     */
    VOID_TORCHER_POUCH(18, 12798, 34, 59.6, 7351, 0.7, 4, false, Item(12163), Item(12167), Item(12155), Item(12183, 74)),

    /**
     * Void Shifter Pouch.
     */
    VOID_SHIFTER_POUCH(19, 12814, 34, 59.6, 7367, 0.7, 4, false, Item(12163), Item(12165), Item(12155), Item(12183, 74)),

    /**
     * Bronze Minotaur Pouch.
     */
    BRONZE_MINOTAUR_POUCH(64, 12073, 36, 316.8, 6853, 3.6, 3, false, Item(12163), Item(12155), Item(2349), Item(12183, 102)),

    /**
     * Iron Minotaur Pouch.
     */
    IRON_MINOTAUR_POUCH(65, 12075, 46, 404.8, 6855, 4.6, 9, false, Item(12163), Item(12155), Item(2351), Item(12183, 125)),

    /**
     * Steel Minotaur Pouch.
     */
    STEEL_MINOTAUR_POUCH(66, 12077, 56, 492.8, 6857, 5.6, 9, false, Item(12163), Item(12155), Item(2353), Item(12183, 141)),

    /**
     * Mithril Minotaur Pouch.
     */
    MITHRIL_MINOTAUR_POUCH(67, 12079, 66, 580.8, 6859, 6.6, 9, false, Item(12163), Item(12155), Item(2359), Item(12183, 152)),

    /**
     * Adamant Minotaur Pouch.
     */
    ADAMANT_MINOTAUR_POUCH(68, 12081, 76, 668.8, 6861, 7.6, 9, false, Item(12163), Item(12155), Item(2361), Item(12183, 144)),

    /**
     * Rune Minotaur Pouch.
     */
    RUNE_MINOTAUR_POUCH(69, 12083, 86, 756.8, 6863, 8.6, 9, false, Item(12163), Item(12155), Item(2363), Item(12183, 1)),

    /**
     * Bull Ant Pouch.
     */
    BULL_ANT_POUCH(20, 12087, 40, 52.8, 6867, 0.6, 5, false, Item(12158), Item(12155), Item(6010), Item(12183, 11)),

    /**
     * Macaw Pouch.
     */
    MACAW_POUCH(21, 12071, 41, 72.4, 6851, 0.8, 5, true, Item(12159), Item(12155), Item(249), Item(12183, 78)),

    /**
     * Evil Turnip Pouch.
     */
    EVIL_TURNIP_POUCH(22, 12051, 42, 184.8, 6833, 2.1, 5, false, Item(12160), Item(12155), Item(12153), Item(12183, 104)),

    /**
     * Spirit Cockatrice Pouch.
     */
    SPIRIT_COCKATRICE_POUCH(23, 12095, 43, 75.2, 6875, 0.9, 5, false, Item(12159), Item(12155), Item(12109), Item(12183, 88)),

    /**
     * Spirit Guthatrice Pouch.
     */
    SPIRIT_GUTHATRICE_POUCH(24, 12097, 43, 75.2, 6877, 0.9, 5, false, Item(12159), Item(12155), Item(12111), Item(12183, 88)),

    /**
     * Spirit Saratrice Pouch.
     */
    SPIRIT_SARATRICE_POUCH(25, 12099, 43, 75.2, 6879, 0.9, 5, false, Item(12159), Item(12155), Item(12113), Item(12183, 88)),

    /**
     * Spirit Zamatrice Pouch.
     */
    SPIRIT_ZAMATRICE_POUCH(26, 12101, 43, 75.2, 6881, 0.9, 5, false, Item(12159), Item(12155), Item(12115), Item(12183, 88)),

    /**
     * Spirit Pengatrice Pouch.
     */
    SPIRIT_PENGATRICE_POUCH(27, 12103, 43, 75.2, 6883, 0.9, 5, false, Item(12159), Item(12155), Item(12117), Item(12183, 88)),

    /**
     * Spirit Coraxatrice Pouch.
     */
    SPIRIT_CORAXATRICE_POUCH(28, 12105, 43, 75.2, 6885, 0.9, 5, false, Item(12159), Item(12155), Item(12119), Item(12183, 88)),

    /**
     * Spirit Vulatrice.
     */
    SPIRIT_VULATRICE(29, 12107, 43, 75.2, 6887, 0.9, 5, false, Item(12159), Item(12155), Item(12121), Item(12183, 88)),

    /**
     * Pyrelord Pouch.
     */
    PYRELORD_POUCH(30, 12816, 46, 202.4, 7377, 2.3, 5, false, Item(12160), Item(12155), Item(590), Item(12183, 111)),

    /**
     * Magpie Pouch.
     */
    MAGPIE_POUCH(31, 12041, 47, 83.2, 6824, 0.9, 5, true, Item(12159), Item(12155), Item(1635), Item(12183, 88)),

    /**
     * Bloated Leech Pouch.
     */
    BLOATED_LEECH_POUCH(32, 12061, 49, 215.2, 6843, 2.4, 5, false, Item(12160), Item(12155), Item(2132), Item(12183, 117)),

    /**
     * Spirit Terrorbird Pouch.
     */
    SPIRIT_TERRORBIRD_POUCH(33, 12007, 52, 68.4, 6794, 0.8, 6, true, Item(12158), Item(12155), Item(9978), Item(12183, 12)),

    /**
     * Abyssal Parasite Pouch.
     */
    ABYSSAL_PARASITE_POUCH(34, true, 12035, 54, 94.8, 6818, 1.1, 6, false, Item(12159), Item(12155), Item(12161), Item(12183, 106)),

    /**
     * Spirit Jelly Pouch.
     */
    SPIRIT_JELLY_POUCH(35, 12027, 55, 484.0, 6992, 5.5, 6, false, Item(12163), Item(12155), Item(1937), Item(12183, 151)),

    /**
     * Ibis Pouch.
     */
    IBIS_POUCH(36, 12531, 56, 98.8, 6991, 1.1, 6, true, Item(12159), Item(12155), Item(311), Item(12183, 109)),

    /**
     * Spirit Kyatt Pouch.
     */
    SPIRIT_KYATT_POUCH(37, 12812, 57, 501.6, 7365, 5.7, 6, false, Item(12163), Item(12155), Item(10103), Item(12183, 153)),

    /**
     * Spirit Larupia Pouch.
     */
    SPIRIT_LARUPIA_POUCH(38, 12784, 57, 501.6, 7337, 5.7, 6, false, Item(12163), Item(12155), Item(10095), Item(12183, 155)),

    /**
     * Spirit Graahk Pouch.
     */
    SPIRIT_GRAAHK_POUCH(39, 12810, 57, 501.6, 7363, 5.7, 6, false, Item(12163), Item(12155), Item(10099), Item(12183, 154)),

    /**
     * Karamthulhu Pouch.
     */
    KARAMTHULHU_POUCH(40, 12023, 58, 510.4, 6809, 5.8, 6, false, Item(12163), Item(12155), Item(6667), Item(12183, 144)),

    /**
     * Smoke Devil Pouch.
     */
    SMOKE_DEVIL_POUCH(41, 12085, 61, 268.0, 6865, 3.0, 7, false, Item(12160), Item(12155), Item(9736), Item(12183, 141)),

    /**
     * Abyssal Lukrer.
     */
    ABYSSAL_LUKRER(42, true, 12037, 62, 109.6, 6820, 1.9, 9, false, Item(12159), Item(12155), Item(12161), Item(12183, 119)),

    /**
     * Spirit Cobra Pouch.
     */
    SPIRIT_COBRA_POUCH(43, 12015, 63, 276.8, 6802, 3.1, 6, false, Item(12160), Item(12155), Item(6287), Item(12183, 116)),

    /**
     * Stranger Plant Pouch.
     */
    STRANGER_PLANT_POUCH(44, 12045, 64, 281.6, 6827, 3.2, 6, false, Item(12160), Item(12155), Item(8431), Item(12183, 128)),

    /**
     * Barker Toad Pouch.
     */
    BARKER_TOAD_POUCH(45, 12123, 66, 87.0, 6889, 1.0, 7, false, Item(12158), Item(12155), Item(2150), Item(12183, 11)),

    /**
     * War Tortoise Pouch.
     */
    WAR_TORTOISE_POUCH(46, 12031, 67, 58.6, 6815, 0.7, 7, true, Item(12158), Item(12155), Item(7939), Item(12183, 1)),

    /**
     * Bunyip Pouch.
     */
    BUNYIP_POUCH(47, 12029, 68, 119.2, 6813, 1.4, 7, true, Item(12159), Item(12155), Item(383), Item(12183, 110)),

    /**
     * Fruit Bat Pouch.
     */
    FRUIT_BAT_POUCH(48, 12033, 69, 121.2, 6817, 1.4, 8, true, Item(12159), Item(12155), Item(1963), Item(12183, 130)),

    /**
     * Ravenous Locust Pouch.
     */
    RAVENOUS_LOCUST_POUCH(49, 12820, 70, 132.0, 7372, 1.5, 4, false, Item(12160), Item(12155), Item(1933), Item(12183, 79)),

    /**
     * Arctic Bear Pouch.
     */
    ARCTIC_BEAR_POUCH(50, 12057, 71, 93.2, 6839, 1.1, 8, false, Item(12158), Item(12155), Item(10117), Item(12183, 14)),

    /**
     * Phoenix Pouch.
     */
    PHOENIX_POUCH(50, 14623, 72, 93.2, 8575, 1.1, 8, false, Item(12160, 1), Item(12183, 165), Item(12155, 1), Item(14616, 1)),

    /**
     * Obsidian Golem Pouch.
     */
    OBSIDIAN_GOLEM_POUCH(51, 12792, 73, 642.4, 7345, 7.3, 8, false, Item(12163), Item(12155), Item(12168), Item(12183, 195)),

    /**
     * Granite Lobster Pouch.
     */
    GRANITE_LOBSTER_POUCH(52, 12069, 74, 325.6, 6849, 3.7, 8, false, Item(12160), Item(12155), Item(6979), Item(12183, 166)),

    /**
     * Praying Mantis Pouch.
     */
    PRAYING_MANTIS_POUCH(53, 12011, 75, 329.6, 6798, 3.6, 8, false, Item(12160), Item(12155), Item(2460), Item(12183, 168)),

    /**
     * Forge Regent Beast.
     */
    FORGE_REGENT_BEAST(54, 12782, 76, 134.0, 7335, 1.5, 9, false, Item(12159), Item(12155), Item(10020), Item(12183, 141)),

    /**
     * Talon Beast Pouch.
     */
    TALON_BEAST_POUCH(55, 12794, 77, 1015.2, 7347, 3.8, 9, false, Item(12160), Item(12155), Item(12162), Item(12183, 174)),

    /**
     * Giant Ent Pouch.
     */
    GIANT_ENT_POUCH(56, 12013, 78, 136.8, 6800, 1.6, 8, false, Item(12159), Item(5933), Item(12155), Item(12183, 124)),

    /**
     * Hydra Pouch.
     */
    HYDRA_POUCH(60, 12025, 80, 140.8, 6811, 1.6, 9, false, Item(12159), Item(571), Item(12155), Item(12183, 128)),

    /**
     * Spirit Dagannoth Pouch.
     */
    SPIRIT_DAGANNOTH_POUCH(61, 12017, 83, 364.8, 6804, 4.1, 9, false, Item(12160), Item(6155), Item(12155), Item(12183, 1)),

    /**
     * Unicorn Stallion Pouch.
     */
    UNICORN_STALLION_POUCH(70, 12039, 88, 154.4, 6822, 1.8, 9, true, Item(12159), Item(237), Item(12155), Item(12183, 140)),

    /**
     * Wolpertinger Pouch.
     */
    WOLPERTINGER_POUCH(72, 12089, 92, 404.8, 6869, 4.5, 10, false, Item(12160), Item(2859), Item(3226), Item(12155), Item(12183, 203)),

    /**
     * Pack Yak Pouch.
     */
    PACK_YAK_POUCH(75, 12093, 96, 422.4, 6873, 4.8, 10, true, Item(12160), Item(10818), Item(12155), Item(12183, 211)),

    /**
     * Fire Titan Pouch.
     */
    FIRE_TITAN_POUCH(57, 12802, 79, 695.2, 7355, 7.9, 9, false, Item(12163), Item(1442), Item(12155), Item(12183, 198)),

    /**
     * Moss Titan Pouch.
     */
    MOSS_TITAN_POUCH(58, 12804, 79, 695.2, 7357, 7.9, 9, false, Item(12163), Item(1440), Item(12155), Item(12183, 198)),

    /**
     * Ice Titan Pouch.
     */
    ICE_TITAN_POUCH(59, 12806, 79, 695.2, 7359, 7.9, 9, false, Item(12163), Item(1438), Item(1444), Item(12155), Item(12183, 198)),

    /**
     * Lava Titan Pouch.
     */
    LAVA_TITAN_POUCH(62, 12788, 83, 730.4, 7341, 8.3, 9, false, Item(12163), Item(12168), Item(12155), Item(12183, 219)),

    /**
     * Swamp Titan Pouch.
     */
    SWAMP_TITAN_POUCH(63, 12776, 85, 373.6, 7329, 4.2, 9, false, Item(12160), Item(10149), Item(12155), Item(12183, 150)),

    /**
     * Geyser Titan Pouch.
     */
    GEYSER_TITAN_POUCH(71, 12786, 89, 783.2, 7339, 8.9, 9, false, Item(12163), Item(1444), Item(12155), Item(12183, 222)),

    /**
     * Abyssal Titan Pouch.
     */
    ABYSSAL_TITAN_POUCH(73, true, 12796, 93, 163.2, 7349, 1.9, 10, false, Item(12159), Item(12161), Item(12155), Item(12183, 113)),

    /**
     * Iron Titan Pouch.
     */
    IRON_TITAN_POUCH(74, 12822, 95, 417.6, 7375, 4.7, 10, false, Item(12160), Item(1115), Item(12155), Item(12183, 198)),

    /**
     * Steel Titan Pouch.
     */
    STEEL_TITAN_POUCH(76, 12790, 99, 435.2, 7343, 4.9, 10, false, Item(12160), Item(1119), Item(12155), Item(12183, 178)),

    /**
     * Sacred Clay Pouch 1.
     */
    SACRED_CLAY_POUCH_1(-1, 14422, 1, 0.0, 8240, 0.0, 1, false, Item(14182)),

    /**
     * Sacred Clay Pouch 2.
     */
    SACRED_CLAY_POUCH_2(-1, 14424, 20, 0.0, 8242, 0.0, 3, false, Item(14184)),

    /**
     * Sacred Clay Pouch 3.
     */
    SACRED_CLAY_POUCH_3(-1, 14426, 40, 0.0, 8244, 0.0, 5, false, Item(14186)),

    /**
     * Sacred Clay Pouch 4.
     */
    SACRED_CLAY_POUCH_4(-1, 14428, 60, 0.0, 8246, 0.0, 7, false, Item(14188)),

    /**
     * Sacred Clay Pouch 5.
     */
    SACRED_CLAY_POUCH_5(-1, 14430, 80, 0.0, 8248, 0.0, 9, false, Item(14190));

    val items: Array<Item> = items as Array<Item>

    var abyssal: Boolean = false

    constructor(slot: Int, abyssal: Boolean, pouchId: Int, levelRequired: Int, createExperience: Double, npcId: Int, summonExperience: Double, summonCost: Int, peaceful: Boolean, vararg items: Item) : this(slot, pouchId, levelRequired, createExperience, npcId, summonExperience, summonCost, peaceful, *items) {
        this.abyssal = abyssal
    }

    companion object {
        private val POUCHES: MutableMap<Int, SummoningPouch> = HashMap()


        init {
            for (pouch in values()) {
                POUCHES[pouch.pouchId] = pouch
            }
        }

        @JvmStatic
        fun get(pouchId: Int): SummoningPouch? {
            return POUCHES[pouchId]
        }

        @JvmStatic
        fun forSlot(slot: Int): SummoningPouch? {
            for (pouch in values()) {
                if (pouch.slot == slot) {
                    return pouch
                }
            }
            return null
        }
    }

}
