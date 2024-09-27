package content.global.skill.summoning

import org.rs.consts.Items

enum class SummoningScroll(val slotId: Int, @JvmField val itemId: Int, val experience: Double, @JvmField val level: Int, @JvmField vararg val items: Int) {
    HOWL_SCROLL(
        level = 0,
        itemId = Items.HOWL_SCROLL_12425,
        experience = 0.1,
        slotId = 1,
        items = intArrayOf(Items.SPIRIT_WOLF_POUCH_12047)
    ),
    DREADFOWL_STRIKE_SCROLL(
        level = 1,
        itemId = Items.DREADFOWL_STRIKE_SCROLL_12445,
        experience = 0.1,
        slotId = 4,
        items = intArrayOf(Items.DREADFOWL_POUCH_12043)
    ),
    EGG_SPAWN_SCROLL(
        slotId = 2,
        itemId = Items.EGG_SPAWN_SCROLL_12428,
        experience = 0.2,
        level = 10,
        items = intArrayOf(Items.SPIRIT_SPIDER_POUCH_12059)
    ),
    SLIME_SPRAY_SCROLL(
        slotId = 3,
        itemId = Items.SLIME_SPRAY_SCROLL_12459,
        experience = 0.2,
        level = 13,
        items = intArrayOf(Items.THORNY_SNAIL_POUCH_12019)
    ),
    STONY_SHELL_SCROLL(
        slotId = 4,
        itemId = Items.STONY_SHELL_SCROLL_12533,
        experience = 0.2,
        level = 16,
        items = intArrayOf(Items.GRANITE_CRAB_POUCH_12009)
    ),
    PESTER_SCROLL(
        slotId = 5,
        itemId = Items.PESTER_SCROLL_12838,
        experience = 0.5,
        level = 17,
        items = intArrayOf(Items.SPIRIT_MOSQUITO_POUCH_12778)
    ),
    ELECTRIC_LASH_SCROLL(
        slotId = 6,
        itemId = Items.ELECTRIC_LASH_SCROLL_12460,
        experience = 0.4,
        level = 18,
        items = intArrayOf(Items.DESERT_WYRM_POUCH_12049)
    ),
    VENOM_SHOT_SCROLL(
        slotId = 7,
        itemId = Items.VENOM_SHOT_SCROLL_12432,
        experience = 0.9,
        level = 19,
        items = intArrayOf(Items.SPIRIT_SCORPION_POUCH_12055)
    ),
    FIREBALL_ASSAULT_SCROLL(
        slotId = 8,
        itemId = Items.FIREBALL_ASSAULT_SCROLL_12839,
        experience = 1.1,
        level = 22,
        items = intArrayOf(Items.SPIRIT_TZ_KIH_POUCH_12808)
    ),
    CHEESE_FEAST_SCROLL(
        slotId = 9,
        itemId = Items.CHEESE_FEAST_SCROLL_12430,
        experience = 2.3,
        level = 23,
        items = intArrayOf(Items.ALBINO_RAT_POUCH_12067)
    ),
    SANDSTORM_SCROLL(
        slotId = 10,
        itemId = Items.SANDSTORM_SCROLL_12446,
        experience = 2.5,
        level = 25,
        items = intArrayOf(Items.SPIRIT_KALPHITE_POUCH_12063)
    ),
    GENERATE_COMPOST_SCROLL(
        slotId = 11,
        itemId = Items.GENERATE_COMPOST_SCROLL_12440,
        experience = 0.6,
        level = 28,
        items = intArrayOf(Items.COMPOST_MOUND_POUCH_12091)
    ),
    EXPLODE_SCROLL(
        slotId = 12,
        itemId = Items.EXPLODE_SCROLL_12834,
        experience = 2.9,
        level = 29,
        items = intArrayOf(Items.GIANT_CHINCHOMPA_POUCH_12800)
    ),
    VAMPYRE_TOUCH_SCROLL(
        slotId = 13,
        itemId = Items.VAMPIRE_TOUCH_SCROLL_12447,
        experience = 1.5,
        level = 31,
        items = intArrayOf(Items.VAMPIRE_BAT_POUCH_12053)
    ),
    INSANE_FEROCITY_SCROLL(
        slotId = 14,
        itemId = Items.INSANE_FEROCITY_SCROLL_12433,
        experience = 1.6,
        level = 32,
        items = intArrayOf(Items.HONEY_BADGER_POUCH_12065)
    ),
    MULTICHOP_SCROLL(
        slotId = 15,
        itemId = Items.MULTICHOP_SCROLL_12429,
        experience = 0.7,
        level = 33,
        items = intArrayOf(Items.BEAVER_POUCH_12021)
    ),
    CALL_TO_ARMS_SCROLL1(
        slotId = 16,
        itemId = Items.CALL_TO_ARMS_SCROLL_12443,
        experience = 0.7,
        level = 34,
        items = intArrayOf(Items.VOID_RAVAGER_POUCH_12818)
    ),
    CALL_TO_ARMS_SCROLL2(
        slotId = 17,
        itemId = Items.CALL_TO_ARMS_SCROLL_12443,
        experience = 0.7,
        level = 34,
        items = intArrayOf(Items.VOID_SHIFTER_POUCH_12814)
    ),
    CALL_TO_ARMS_SCROLL3(
        slotId = 18,
        itemId = Items.CALL_TO_ARMS_SCROLL_12443,
        experience = 0.7,
        level = 34,
        items = intArrayOf(Items.VOID_SPINNER_POUCH_12780)
    ),
    CALL_TO_ARMS_SCROLL4(
        slotId = 19,
        itemId = Items.CALL_TO_ARMS_SCROLL_12443,
        experience = 0.7,
        level = 34,
        items = intArrayOf(Items.VOID_TORCHER_POUCH_12798)
    ),
    BRONZE_BULL_RUSH_SCROLL(
        slotId = 64,
        itemId = Items.BRONZE_BULL_RUSH_SCROLL_12461,
        experience = 3.6,
        level = 36,
        items = intArrayOf(Items.BRONZE_MINOTAUR_POUCH_12073)
    ),
    UNBURDEN_SCROLL(
        slotId = 20,
        itemId = Items.UNBURDEN_SCROLL_12431,
        experience = 0.6,
        level = 40,
        items = intArrayOf(Items.BULL_ANT_POUCH_12087)
    ),
    HERBCALL_SCROLL(
        slotId = 21,
        itemId = Items.HERBCALL_SCROLL_12422,
        experience = 0.8,
        level = 41,
        items = intArrayOf(Items.MACAW_POUCH_12071)
    ),
    EVIL_FLAMES_SCROLL(
        slotId = 22,
        itemId = Items.PETRIFYING_GAZE_SCROLL_12458,
        experience = 2.1,
        level = 42,
        items = intArrayOf(Items.EVIL_TURNIP_POUCH_12051)
    ),
    PETRIFYING_GAZE_SCROLL1(
        slotId = 23,
        itemId = Items.PETRIFYING_GAZE_SCROLL_12458,
        experience = 0.9,
        level = 43,
        items = intArrayOf(Items.SP_COCKATRICE_POUCH_12095)
    ),
    PETRIFYING_GAZE_SCROLL2(
        slotId = 24,
        itemId = Items.PETRIFYING_GAZE_SCROLL_12458,
        experience = 0.9,
        level = 43,
        items = intArrayOf(Items.SP_GUTHATRICE_POUCH_12097)
    ),
    PETRIFYING_GAZE_SCROLL3(
        slotId = 25,
        itemId = Items.PETRIFYING_GAZE_SCROLL_12458,
        experience = 0.9,
        level = 43,
        items = intArrayOf(Items.SP_SARATRICE_POUCH_12099)
    ),
    PETRIFYING_GAZE_SCROLL4(
        slotId = 26,
        itemId = Items.PETRIFYING_GAZE_SCROLL_12458,
        experience = 0.9,
        level = 43,
        items = intArrayOf(Items.SP_ZAMATRICE_POUCH_12101)
    ),
    PETRIFYING_GAZE_SCROLL5(
        slotId = 27,
        itemId = Items.PETRIFYING_GAZE_SCROLL_12458,
        experience = 0.9,
        level = 43,
        items = intArrayOf(Items.SP_PENGATRICE_POUCH_12103)
    ),
    PETRIFYING_GAZE_SCROLL6(
        slotId = 28,
        itemId = Items.PETRIFYING_GAZE_SCROLL_12458,
        experience = 0.9,
        level = 43,
        items = intArrayOf(Items.SP_CORAXATRICE_POUCH_12105)
    ),
    PETRIFYING_GAZE_SCROLL7(
        slotId = 29,
        itemId = Items.PETRIFYING_GAZE_SCROLL_12458,
        experience = 0.9,
        level = 43,
        items = intArrayOf(Items.SP_VULATRICE_POUCH_12107)
    ),
    IRON_BULL_RUSH_SCROLL(
        slotId = 65,
        itemId = Items.IRON_BULL_RUSH_SCROLL_12462,
        experience = 4.6,
        level = 46,
        items = intArrayOf(Items.IRON_MINOTAUR_POUCH_12075)
    ),
    IMMENSE_HEAT_SCROLL(
        slotId = 30,
        itemId = Items.IMMENSE_HEAT_SCROLL_12829,
        experience = 2.3,
        level = 46,
        items = intArrayOf(Items.PYRELORD_POUCH_12816)
    ),
    THIEVING_FINGERS_SCROLL(
        slotId = 31,
        itemId = Items.THIEVING_FINGERS_SCROLL_12426,
        experience = 47.0,
        level = 47,
        items = intArrayOf(Items.MAGPIE_POUCH_12041)
    ),
    BLOOD_DRAIN_SCROLL(
        slotId = 32,
        itemId = Items.BLOOD_DRAIN_SCROLL_12444,
        experience = 2.4,
        level = 49,
        items = intArrayOf(Items.BLOATED_LEECH_POUCH_12061)
    ),
    TIRELESS_RUN_SCROLL(
        slotId = 33,
        itemId = Items.TIRELESS_RUN_SCROLL_12441,
        experience = 0.8,
        level = 52,
        items = intArrayOf(Items.SPIRIT_TERRORBIRD_POUCH_12007)
    ),
    ABYSSAL_DRAIN_SCROLL(
        slotId = 34,
        itemId = Items.ABYSSAL_DRAIN_SCROLL_12454,
        experience = 1.1,
        level = 54,
        items = intArrayOf(Items.ABYSSAL_PARASITE_POUCH_12035)
    ),
    DISSOLVE_SCROLL(
        slotId = 35,
        itemId = Items.DISSOLVE_SCROLL_12453,
        experience = 5.5,
        level = 55,
        items = intArrayOf(Items.SPIRIT_JELLY_POUCH_12027)
    ),
    STEEL_BULL_RUSH_SCROLL(
        slotId = 66,
        itemId = Items.STEEL_BULL_RUSH_SCROLL_12463,
        experience = 5.6,
        level = 56,
        items = intArrayOf(Items.STEEL_MINOTAUR_POUCH_12077)
    ),
    FISH_RAIN_SCROLL(
        slotId = 36,
        itemId = Items.FISH_RAIN_SCROLL_12424,
        experience = 1.1,
        level = 56,
        items = intArrayOf(Items.IBIS_POUCH_12531)
    ),
    AMBUSH_SCROLL(
        slotId = 37,
        itemId = Items.AMBUSH_SCROLL_12836,
        experience = 5.7,
        level = 57,
        items = intArrayOf(Items.SPIRIT_KYATT_POUCH_12812)
    ),
    RENDING_SCROLL(
        slotId = 38,
        itemId = Items.RENDING_SCROLL_12840,
        experience = 5.7,
        level = 57,
        items = intArrayOf(Items.SPIRIT_LARUPIA_POUCH_12784)
    ),
    GOAD_SCROLL(
        slotId = 39,
        itemId = Items.GOAD_SCROLL_12835,
        experience = 5.7,
        level = 57,
        items = intArrayOf(Items.SPIRIT_GRAAHK_POUCH_12810)
    ),
    DOOMSPHERE_SCROLL(
        slotId = 40,
        itemId = Items.DOOMSPHERE_SCROLL_12455,
        experience = 5.8,
        level = 58,
        items = intArrayOf(-1)
    ),
    DUST_CLOUD_SCROLL(
        slotId = 41,
        itemId = Items.DUST_CLOUD_SCROLL_12468,
        experience = 3.0,
        level = 61,
        items = intArrayOf(Items.SMOKE_DEVIL_POUCH_12085)
    ),
    ABYSSAL_STEALTH_SCROLL(
        slotId = 42,
        itemId = Items.ABYSSAL_STEALTH_SCROLL_12427,
        experience = 1.9,
        level = 62,
        items = intArrayOf(Items.ABYSSAL_LURKER_POUCH_12037)
    ),
    OPHIDIAN_INCUBATION_SCROLL(
        slotId = 43,
        itemId = Items.OPH_INCUBATION_SCROLL_12436,
        experience = 3.1,
        level = 63,
        items = intArrayOf(Items.SPIRIT_COBRA_POUCH_12015)
    ),
    POISONOUS_BLAST_SCROLL(
        slotId = 44,
        itemId = Items.POISONOUS_BLAST_SCROLL_12467,
        experience = 3.2,
        level = 64,
        items = intArrayOf(Items.STRANGER_PLANT_POUCH_12045)
    ),
    MITHRIL_BULL_RUSH_SCROLL(
        slotId = 67,
        itemId = Items.MITH_BULL_RUSH_SCROLL_12464,
        experience = 6.6,
        level = 66,
        items = intArrayOf(Items.MITHRIL_MINOTAUR_POUCH_12079)
    ),
    TOAD_BARK_SCROLL(
        slotId = 45,
        itemId = Items.TOAD_BARK_SCROLL_12452,
        experience = 1.0,
        level = 66,
        items = intArrayOf(Items.BARKER_TOAD_POUCH_12123)
    ),
    ESTUDO_SCROLL(
        slotId = 46,
        itemId = Items.TESTUDO_SCROLL_12439,
        experience = 0.7,
        level = 67,
        items = intArrayOf(Items.WAR_TORTOISE_POUCH_12031)
    ),
    SWALLOW_WHOLE_SCROLL(
        slotId = 47,
        itemId = Items.SWALLOW_WHOLE_SCROLL_12438,
        experience = 1.4,
        level = 68,
        items = intArrayOf(Items.BUNYIP_POUCH_12029)
    ),
    FRUITFALL_SCROLL(
        slotId = 48,
        itemId = Items.FRUITFALL_SCROLL_12423,
        experience = 1.4,
        level = 69,
        items = intArrayOf(Items.FRUIT_BAT_POUCH_12033)
    ),
    FAMINE_SCROLL(
        slotId = 49,
        itemId = Items.FAMINE_SCROLL_12830,
        experience = 1.5,
        level = 70,
        items = intArrayOf(Items.RAVENOUS_LOCUST_POUCH_12820)
    ),
    ARCTIC_BLAST_SCROLL(
        slotId = 50,
        itemId = Items.ARCTIC_BLAST_SCROLL_12451,
        experience = 1.1,
        level = 71,
        items = intArrayOf(Items.ARCTIC_BEAR_POUCH_12057)
    ),
    RISE_FROM_THE_ASHES_SCROLL(
        slotId = 51,
        itemId = Items.RISE_FROM_THE_ASHES_SCROLL_14622,
        experience = 8.0,
        level = 277,
        items = intArrayOf(-1)
    ),
    VOLCANIC_STRENGTH_SCROLL(
        slotId = 51,
        itemId = Items.VOLCANIC_STR_SCROLL_12826,
        experience = 7.3,
        level = 73,
        items = intArrayOf(Items.OBSIDIAN_GOLEM_POUCH_12792)
    ),
    CRUSHING_CLAW_SCROLL(
        slotId = 52,
        itemId = Items.CRUSHING_CLAW_SCROLL_12449,
        experience = 3.7,
        level = 74,
        items = intArrayOf(Items.GRANITE_LOBSTER_POUCH_12069)
    ),
    MANTIS_STRIKE_SCROLL(
        slotId = 53,
        itemId = Items.MANTIS_STRIKE_SCROLL_12450,
        experience = 3.7,
        level = 75,
        items = intArrayOf(Items.PRAYING_MANTIS_POUCH_12011)
    ),
    INFERNO_SCROLL(
        slotId = 54,
        itemId = Items.INFERNO_SCROLL_12841,
        experience = 1.5,
        level = 76,
        items = intArrayOf(Items.FORGE_REGENT_POUCH_12782)
    ),
    ADAMANT_BULL_RUSH_SCROLL(
        slotId = 68,
        itemId = Items.ADDY_BULL_RUSH_SCROLL_12465,
        experience = 7.6,
        level = 76,
        items = intArrayOf(Items.ADAMANT_MINOTAUR_POUCH_12081)
    ),
    DEADLY_CLAW_SCROLL(
        slotId = 55,
        itemId = Items.DEADLY_CLAW_SCROLL_12831,
        experience = 11.0,
        level = 77,
        items = intArrayOf(Items.TALON_BEAST_CHARM_12162)
    ),
    ACORN_MISSILE_SCROLL(
        slotId = 56,
        itemId = Items.ACORN_MISSILE_SCROLL_12457,
        experience = 1.6,
        level = 78,
        items = intArrayOf(Items.GIANT_ENT_POUCH_12013)
    ),
    TITANS_CONSTITUTION_SCROLL1(
        slotId = 57,
        itemId = Items.TITANS_CON_SCROLL_12824,
        experience = 7.9,
        level = 79,
        items = intArrayOf(Items.FIRE_TITAN_POUCH_12802)
    ),
    TITANS_CONSTITUTION_SCROLL2(
        slotId = 58,
        itemId = Items.TITANS_CON_SCROLL_12824,
        experience = 7.9,
        level = 79,
        items = intArrayOf(Items.ICE_TITAN_POUCH_12806)
    ),
    TITANS_CONSTITUTION_SCROLL3(
        slotId = 59,
        itemId = Items.TITANS_CON_SCROLL_12824,
        experience = 7.9,
        level = 79,
        items = intArrayOf(Items.MOSS_TITAN_POUCH_12804)
    ),
    REGROWTH_SCROLL(
        slotId = 60,
        itemId = Items.REGROWTH_SCROLL_12442,
        experience = 1.6,
        level = 80,
        items = intArrayOf(Items.HYDRA_POUCH_12025)
    ),
    SPIKE_SHOT_SCROLL(
        slotId = 61,
        itemId = Items.SPIKE_SHOT_SCROLL_12456,
        experience = 4.1,
        level = 83,
        items = intArrayOf(Items.SPIRIT_DAGANNOTH_POUCH_12017)
    ),
    EBON_THUNDER_SCROLL(
        slotId = 62,
        itemId = Items.EBON_THUNDER_SCROLL_12837,
        experience = 8.3,
        level = 83,
        items = intArrayOf(Items.LAVA_TITAN_POUCH_12788)
    ),
    SWAMP_PLAGUE_SCROLL(
        slotId = 63,
        itemId = Items.SWAMP_PLAGUE_SCROLL_12832,
        experience = 4.1,
        level = 85,
        items = intArrayOf(Items.SWAMP_TITAN_POUCH_12776)
    ),
    RUNE_BULL_RUSH_SCROLL(
        slotId = 69,
        itemId = Items.RUNE_BULL_RUSH_SCROLL_12466,
        experience = 8.6,
        level = 86,
        items = intArrayOf(Items.RUNE_MINOTAUR_POUCH_12083)
    ),
    HEALING_AURA_SCROLL(
        slotId = 70,
        itemId = Items.HEALING_AURA_SCROLL_12434,
        experience = 1.8,
        level = 88,
        items = intArrayOf(Items.UNICORN_STALLION_POUCH_12039)
    ),
    BOIL_SCROLL(
        slotId = 71,
        itemId = Items.BOIL_SCROLL_12833,
        experience = 8.9,
        level = 89,
        items = intArrayOf(Items.GEYSER_TITAN_POUCH_12786)
    ),
    MAGIC_FOCUS_SCROLL(
        slotId = 72,
        itemId = Items.MAGIC_FOCUS_SCROLL_12437,
        experience = 4.6,
        level = 92,
        items = intArrayOf(Items.WOLPERTINGER_POUCH_12089)
    ),
    ESSENCE_SHIPMENT_SCROLL(
        slotId = 73,
        itemId = Items.ESSENCE_SHIPMENT_SCROLL_12827,
        experience = 1.9,
        level = 93,
        items = intArrayOf(Items.ABYSSAL_TITAN_POUCH_12796)
    ),
    IRON_WITHIN_SCROLL(
        slotId = 74,
        itemId = Items.IRON_WITHIN_SCROLL_12828,
        experience = 4.7,
        level = 95,
        items = intArrayOf(Items.IRON_TITAN_POUCH_12822)
    ),
    WINTER_STORAGE_SCROLL(
        slotId = 75,
        itemId = Items.WINTER_STORAGE_SCROLL_12435,
        experience = 4.8,
        level = 96,
        items = intArrayOf(Items.PACK_YAK_POUCH_12093)
    ),
    STEEL_OF_LEGENDS_SCROLL(
        slotId = 76,
        itemId = Items.STEEL_OF_LEGENDS_SCROLL_12825,
        experience = 4.9,
        level = 99,
        items = intArrayOf(Items.STEEL_TITAN_POUCH_12790)
    ),
    CLAY_DEPOSIT_SCROLL_1(
        slotId = -1,
        itemId = Items.CLAY_DEPOSIT_SCROLL_14421,
        experience = 0.0,
        level = 1,
        items = intArrayOf(Items.SACRED_CLAY_POUCH_CLASS_1_14422)
    ),
    CLAY_DEPOSIT_SCROLL_2(
        slotId = -1,
        itemId = Items.CLAY_DEPOSIT_SCROLL_14421,
        experience = 0.0,
        level = 20,
        items = intArrayOf(Items.SACRED_CLAY_POUCH_CLASS_2_14424)
    ),
    CLAY_DEPOSIT_SCROLL_3(
        slotId = -1,
        itemId = Items.CLAY_DEPOSIT_SCROLL_14421,
        experience = 0.0,
        level = 40,
        items = intArrayOf(Items.SACRED_CLAY_POUCH_CLASS_3_14426)
    ),
    CLAY_DEPOSIT_SCROLL_4(
        slotId = -1,
        itemId = Items.CLAY_DEPOSIT_SCROLL_14421,
        experience = 0.0,
        level = 60,
        items = intArrayOf(Items.SACRED_CLAY_POUCH_CLASS_4_14428)
    ),
    CLAY_DEPOSIT_SCROLL_5(
        slotId = -1,
        itemId = Items.CLAY_DEPOSIT_SCROLL_14421,
        experience = 0.0,
        level = 80,
        items = intArrayOf(Items.SACRED_CLAY_POUCH_CLASS_5_14430)
    );

    val pouch: Int
        get() = items.firstOrNull() ?: -1

    companion object {
        @JvmStatic
        fun forId(id: Int): SummoningScroll? = values().find { it.slotId == id }

        fun forItemId(id: Int): SummoningScroll? = values().find { it.itemId == id }

        @JvmStatic
        fun forPouch(pouchId: Int): SummoningScroll? = values().find { it.items.firstOrNull() == pouchId }
    }

}