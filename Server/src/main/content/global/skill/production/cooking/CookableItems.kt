package content.global.skill.production.cooking

import core.api.consts.Items
import core.game.node.item.Item

/**
 * Cookable items
 *
 * @property cooked
 * @property raw
 * @property burnt
 * @property level
 * @property experience
 * @property low
 * @property high
 * @property lowRange
 * @property highRange
 * @constructor Cookable items
 */
enum class CookableItems(val cooked: Int, val raw: Int, val burnt: Int, val level: Int, val experience: Double, val low: Int, val high: Int, val lowRange: Int, val highRange: Int) {
    /**
     * Chicken
     *
     * @constructor Chicken
     */
    CHICKEN(
        cooked = 2140,
        raw = 2138,
        burnt = 2144,
        level = 1,
        experience = 30.0,
        low = 128,
        high = 512,
        lowRange = 128,
        highRange = 512
    ),

    /**
     * Ugthanki
     *
     * @constructor Ugthanki
     */
    UGTHANKI(
        cooked = 2140,
        raw = 2138,
        burnt = 2144,
        level = 1,
        experience = 40.0,
        low = 40,
        high = 252,
        lowRange = 30,
        highRange = 253
    ),

    /**
     * Rabbit
     *
     * @constructor Rabbit
     */
    RABBIT(
        cooked = 3228,
        raw = 3226,
        burnt = 7222,
        level = 1,
        experience = 30.0,
        low = 128,
        high = 512,
        lowRange = 128,
        highRange = 512
    ),

    /**
     * Crab
     *
     * @constructor Crab
     */
    CRAB(
        cooked = 7521,
        raw = 7518,
        burnt = 7520,
        level = 21,
        experience = 100.0,
        low = 57,
        high = 377,
        lowRange = 57,
        highRange = 377
    ),

    /**
     * Chompy
     *
     * @constructor Chompy
     */
    CHOMPY(
        cooked = 2878,
        raw = 2876,
        burnt = 2880,
        level = 30,
        experience = 100.0,
        low = 200,
        high = 255,
        lowRange = 200,
        highRange = 255
    ),

    /**
     * Jubbly
     *
     * @constructor Jubbly
     */
    JUBBLY(
        cooked = 7568,
        raw = 7566,
        burnt = 7570,
        level = 41,
        experience = 160.0,
        low = 195,
        high = 250,
        lowRange = 195,
        highRange = 250
    ),

    /**
     * Crayfish
     *
     * @constructor Crayfish
     */
    CRAYFISH(
        cooked = 13433,
        raw = 13435,
        burnt = 13437,
        level = 1,
        experience = 30.0,
        low = 128,
        high = 512,
        lowRange = 128,
        highRange = 512
    ),

    /**
     * Shrimp
     *
     * @constructor Shrimp
     */
    SHRIMP(
        cooked = 315,
        raw = 317,
        burnt = 7954,
        level = 1,
        experience = 30.0,
        low = 128,
        high = 512,
        lowRange = 128,
        highRange = 512
    ),

    /**
     * Karambwanji
     *
     * @constructor Karambwanji
     */
    KARAMBWANJI(
        cooked = 3151,
        raw = 3150,
        burnt = 592,
        level = 1,
        experience = 10.0,
        low = 200,
        high = 400,
        lowRange = 200,
        highRange = 400
    ),

    /**
     * Sardine
     *
     * @constructor Sardine
     */
    SARDINE(
        cooked = 325,
        raw = 327,
        burnt = 369,
        level = 1,
        experience = 40.0,
        low = 118,
        high = 492,
        lowRange = 118,
        highRange = 492
    ),

    /**
     * Anchovies
     *
     * @constructor Anchovies
     */
    ANCHOVIES(
        cooked = 319,
        raw = 321,
        burnt = 323,
        level = 1,
        experience = 30.0,
        low = 128,
        high = 512,
        lowRange = 128,
        highRange = 512
    ),

    /**
     * Herring
     *
     * @constructor Herring
     */
    HERRING(
        cooked = 347,
        raw = 345,
        burnt = 357,
        level = 5,
        experience = 50.0,
        low = 108,
        high = 472,
        lowRange = 108,
        highRange = 472
    ),

    /**
     * Mackerel
     *
     * @constructor Mackerel
     */
    MACKEREL(
        cooked = 355,
        raw = 353,
        burnt = 357,
        level = 10,
        experience = 60.0,
        low = 98,
        high = 452,
        lowRange = 98,
        highRange = 452
    ),

    /**
     * Trout
     *
     * @constructor Trout
     */
    TROUT(
        cooked = 333,
        raw = 335,
        burnt = 343,
        level = 15,
        experience = 70.0,
        low = 88,
        high = 432,
        lowRange = 88,
        highRange = 432
    ),

    /**
     * Cod
     *
     * @constructor Cod
     */
    COD(
        cooked = 339,
        raw = 341,
        burnt = 343,
        level = 18,
        experience = 75.0,
        low = 83,
        high = 422,
        lowRange = 88,
        highRange = 432
    ),

    /**
     * Pike
     *
     * @constructor Pike
     */
    PIKE(
        cooked = 351,
        raw = 349,
        burnt = 343,
        level = 20,
        experience = 80.0,
        low = 78,
        high = 412,
        lowRange = 78,
        highRange = 412
    ),

    /**
     * Salmon
     *
     * @constructor Salmon
     */
    SALMON(
        cooked = 329,
        raw = 331,
        burnt = 343,
        level = 25,
        experience = 90.0,
        low = 68,
        high = 392,
        lowRange = 68,
        highRange = 392
    ),

    /**
     * Slimy Eel
     *
     * @constructor Slimy Eel
     */
    SLIMY_EEL(
        cooked = 3381,
        raw = 3379,
        burnt = 3383,
        level = 28,
        experience = 95.0,
        low = 63,
        high = 382,
        lowRange = 63,
        highRange = 382
    ),

    /**
     * Tuna
     *
     * @constructor Tuna
     */
    TUNA(
        cooked = 361,
        raw = 359,
        burnt = 367,
        level = 30,
        experience = 100.0,
        low = 58,
        high = 372,
        lowRange = 58,
        highRange = 372
    ),

    /**
     * Rainbow Fish
     *
     * @constructor Rainbow Fish
     */
    RAINBOW_FISH(
        cooked = 10136,
        raw = 10138,
        burnt = 10140,
        level = 35,
        experience = 110.0,
        low = 56,
        high = 370,
        lowRange = 56,
        highRange = 370
    ),

    /**
     * Cave Eel
     *
     * @constructor Cave Eel
     */
    CAVE_EEL(
        cooked = 5003,
        raw = 5001,
        burnt = 5002,
        level = 38,
        experience = 115.0,
        low = 38,
        high = 332,
        lowRange = 38,
        highRange = 332
    ),

    /**
     * Lobster
     *
     * @constructor Lobster
     */
    LOBSTER(
        cooked = 379,
        raw = 377,
        burnt = 381,
        level = 40,
        experience = 120.0,
        low = 38,
        high = 332,
        lowRange = 38,
        highRange = 332
    ),

    /**
     * Bass
     *
     * @constructor Bass
     */
    BASS(
        cooked = 365,
        raw = 363,
        burnt = 367,
        level = 43,
        experience = 130.0,
        low = 33,
        high = 312,
        lowRange = 33,
        highRange = 312
    ),

    /**
     * Swordfish
     *
     * @constructor Swordfish
     */
    SWORDFISH(
        cooked = 373,
        raw = 371,
        burnt = 375,
        level = 45,
        experience = 140.0,
        low = 18,
        high = 292,
        lowRange = 30,
        highRange = 310
    ),

    /**
     * Lava Eel
     *
     * @constructor Lava Eel
     */
    LAVA_EEL(
        cooked = 2149,
        raw = 2148,
        burnt = 3383,
        level = 53,
        experience = 30.0,
        low = 256,
        high = 256,
        lowRange = 256,
        highRange = 256
    ),

    /**
     * Monkfish
     *
     * @constructor Monkfish
     */
    MONKFISH(
        cooked = 7946,
        raw = 7944,
        burnt = 7948,
        level = 62,
        experience = 150.0,
        low = 11,
        high = 275,
        lowRange = 13,
        highRange = 280
    ),

    /**
     * Shark
     *
     * @constructor Shark
     */
    SHARK(
        cooked = 385,
        raw = 383,
        burnt = 387,
        level = 80,
        experience = 210.0,
        low = 1,
        high = 202,
        lowRange = 1,
        highRange = 232
    ),

    /**
     * Sea Turtle
     *
     * @constructor Sea Turtle
     */
    SEA_TURTLE(
        cooked = 397,
        raw = 395,
        burnt = 399,
        level = 82,
        experience = 212.0,
        low = 1,
        high = 202,
        lowRange = 1,
        highRange = 222
    ),

    /**
     * Manta Ray
     *
     * @constructor Manta Ray
     */
    MANTA_RAY(
        cooked = 391,
        raw = 389,
        burnt = 393,
        level = 91,
        experience = 216.0,
        low = 1,
        high = 202,
        lowRange = 1,
        highRange = 222
    ),

    /**
     * Karambwan
     *
     * @constructor Karambwan
     */
    KARAMBWAN(
        cooked = 3144,
        raw = 3142,
        burnt = 3146,
        level = 30,
        experience = 190.0,
        low = 70,
        high = 255,
        lowRange = 70,
        highRange = 255
    ),

    /**
     * Thin Snail
     *
     * @constructor Thin Snail
     */
    THIN_SNAIL(
        cooked = 3369,
        raw = 3363,
        burnt = 3375,
        level = 12,
        experience = 70.0,
        low = 93,
        high = 444,
        lowRange = 93,
        highRange = 444
    ),

    /**
     * Lean Snail
     *
     * @constructor Lean Snail
     */
    LEAN_SNAIL(
        cooked = 3371,
        raw = 3365,
        burnt = 3375,
        level = 17,
        experience = 80.0,
        low = 85,
        high = 428,
        lowRange = 93,
        highRange = 444
    ),

    /**
     * Fat Snail
     *
     * @constructor Fat Snail
     */
    FAT_SNAIL(
        cooked = 3373,
        raw = 3367,
        burnt = 3375,
        level = 22,
        experience = 95.0,
        low = 73,
        high = 402,
        lowRange = 73,
        highRange = 402
    ),

    /**
     * Bread
     *
     * @constructor Bread
     */
    BREAD(
        cooked = 2309,
        raw = 2307,
        burnt = 2311,
        level = 1,
        experience = 40.0,
        low = 118,
        high = 492,
        lowRange = 118,
        highRange = 492
    ),

    /**
     * Pitta Bread
     *
     * @constructor Pitta Bread
     */
    PITTA_BREAD(
        cooked = 1865,
        raw = 1863,
        burnt = 1867,
        level = 58,
        experience = 40.0,
        low = 118,
        high = 492,
        lowRange = 118,
        highRange = 492
    ),

    /**
     * Cake
     *
     * @constructor Cake
     */
    CAKE(
        cooked = 1891,
        raw = 1889,
        burnt = 1903,
        level = 40,
        experience = 180.0,
        low = 0,
        high = 0,
        lowRange = 38,
        highRange = 332
    ),

    /**
     * Beef
     *
     * @constructor Beef
     */
    BEEF(
        cooked = 2142,
        raw = 2132,
        burnt = 2146,
        level = 1,
        experience = 30.0,
        low = 128,
        high = 512,
        lowRange = 128,
        highRange = 512
    ),

    /**
     * Rat Meat
     *
     * @constructor Rat Meat
     */
    RAT_MEAT(
        cooked = 2142,
        raw = 2134,
        burnt = 2146,
        level = 1,
        experience = 30.0,
        low = 128,
        high = 512,
        lowRange = 128,
        highRange = 512
    ),

    /**
     * Bear Meat
     *
     * @constructor Bear Meat
     */
    BEAR_MEAT(
        cooked = 2142,
        raw = 2136,
        burnt = 2146,
        level = 1,
        experience = 30.0,
        low = 128,
        high = 512,
        lowRange = 128,
        highRange = 512
    ),

    /**
     * Yak Meat
     *
     * @constructor Yak Meat
     */
    YAK_MEAT(
        cooked = 2142,
        raw = 10816,
        burnt = 2146,
        level = 1,
        experience = 30.0,
        low = 128,
        high = 512,
        lowRange = 128,
        highRange = 512
    ),

    /**
     * Skewer Chompy
     *
     * @constructor Skewer Chompy
     */
    SKEWER_CHOMPY(
        cooked = 2878,
        raw = 7230,
        burnt = 2880,
        level = 30,
        experience = 100.0,
        low = 200,
        high = 255,
        lowRange = 200,
        highRange = 255
    ),

    /**
     * Skewer Roast Rabbit
     *
     * @constructor Skewer Roast Rabbit
     */
    SKEWER_ROAST_RABBIT(
        cooked = 7223,
        raw = 7224,
        burnt = 7222,
        level = 16,
        experience = 72.0,
        low = 160,
        high = 255,
        lowRange = 160,
        highRange = 255
    ),

    /**
     * Skewer Roast Bird
     *
     * @constructor Skewer Roast Bird
     */
    SKEWER_ROAST_BIRD(
        cooked = 9980,
        raw = 9984,
        burnt = 9982,
        level = 11,
        experience = 62.0,
        low = 155,
        high = 255,
        lowRange = 155,
        highRange = 255
    ),

    /**
     * Skewer Roast Beast
     *
     * @constructor Skewer Roast Beast
     */
    SKEWER_ROAST_BEAST(
        cooked = 9988,
        raw = 9992,
        burnt = 9990,
        level = 21,
        experience = 82.5,
        low = 180,
        high = 255,
        lowRange = 180,
        highRange = 255
    ),

    /**
     * Redberry Pie
     *
     * @constructor Redberry Pie
     */
    REDBERRY_PIE(
        cooked = 2325,
        raw = 2321,
        burnt = 2329,
        level = 10,
        experience = 78.0,
        low = 0,
        high = 0,
        lowRange = 98,
        highRange = 452
    ),

    /**
     * Meat Pie
     *
     * @constructor Meat Pie
     */
    MEAT_PIE(
        cooked = 2327,
        raw = 2319,
        burnt = 2329,
        level = 20,
        experience = 110.0,
        low = 0,
        high = 0,
        lowRange = 78,
        highRange = 412
    ),

    /**
     * Mud Pie
     *
     * @constructor Mud Pie
     */
    MUD_PIE(
        cooked = 7170,
        raw = 7168,
        burnt = 2329,
        level = 29,
        experience = 128.0,
        low = 0,
        high = 0,
        lowRange = 58,
        highRange = 372
    ),

    /**
     * Apple Pie
     *
     * @constructor Apple Pie
     */
    APPLE_PIE(
        cooked = 2323,
        raw = 2317,
        burnt = 2329,
        level = 30,
        experience = 130.0,
        low = 0,
        high = 0,
        lowRange = 58,
        highRange = 372
    ),

    /**
     * Garden Pie
     *
     * @constructor Garden Pie
     */
    GARDEN_PIE(
        cooked = 7178,
        raw = 7176,
        burnt = 2329,
        level = 34,
        experience = 138.0,
        low = 0,
        high = 0,
        lowRange = 48,
        highRange = 352
    ),

    /**
     * Fish Pie
     *
     * @constructor Fish Pie
     */
    FISH_PIE(
        cooked = 7188,
        raw = 7186,
        burnt = 2329,
        level = 47,
        experience = 164.0,
        low = 0,
        high = 0,
        lowRange = 38,
        highRange = 332
    ),

    /**
     * Admiral Pie
     *
     * @constructor Admiral Pie
     */
    ADMIRAL_PIE(
        cooked = 7198,
        raw = 7196,
        burnt = 2329,
        level = 70,
        experience = 210.0,
        low = 0,
        high = 0,
        lowRange = 15,
        highRange = 270
    ),

    /**
     * Wild Pie
     *
     * @constructor Wild Pie
     */
    WILD_PIE(
        cooked = 7208,
        raw = 7206,
        burnt = 2329,
        level = 85,
        experience = 240.0,
        low = 0,
        high = 0,
        lowRange = 1,
        highRange = 222
    ),

    /**
     * Summer Pie
     *
     * @constructor Summer Pie
     */
    SUMMER_PIE(
        cooked = 7218,
        raw = 7216,
        burnt = 2329,
        level = 95,
        experience = 260.0,
        low = 0,
        high = 0,
        lowRange = 1,
        highRange = 212
    ),

    /**
     * Pizza Plain
     *
     * @constructor Pizza Plain
     */
    PIZZA_PLAIN(
        cooked = 2289,
        raw = 2287,
        burnt = 2305,
        level = 35,
        experience = 143.0,
        low = 0,
        high = 0,
        lowRange = 48,
        highRange = 352
    ),

    /**
     * Bowl Stew
     *
     * @constructor Bowl Stew
     *//*
     * Food in bowl.
     */
    BOWL_STEW(
        cooked = 2003,
        raw = 2001,
        burnt = 2005,
        level = 25,
        experience = 117.0,
        low = 68,
        high = 392,
        lowRange = 68,
        highRange = 392
    ),

    /**
     * Bowl Curry
     *
     * @constructor Bowl Curry
     */
    BOWL_CURRY(
        cooked = 2011,
        raw = 2009,
        burnt = 2013,
        level = 60,
        experience = 280.0,
        low = 38,
        high = 332,
        lowRange = 38,
        highRange = 332
    ),

    /**
     * Bowl Nettle
     *
     * @constructor Bowl Nettle
     */
    BOWL_NETTLE(
        cooked = 4239,
        raw = 4237,
        burnt = 4239,
        level = 20,
        experience = 52.0,
        low = 78,
        high = 412,
        lowRange = 78,
        highRange = 412
    ),

    /**
     * Bowl Egg
     *
     * @constructor Bowl Egg
     */
    BOWL_EGG(
        cooked = 7078,
        raw = 7076,
        burnt = 7090,
        level = 13,
        experience = 50.0,
        low = 0,
        high = 0,
        lowRange = 90,
        highRange = 438
    ),

    /**
     * Bowl Onion
     *
     * @constructor Bowl Onion
     */
    BOWL_ONION(
        cooked = 7084,
        raw = 1871,
        burnt = 7092,
        level = 43,
        experience = 60.0,
        low = 36,
        high = 322,
        lowRange = 36,
        highRange = 322
    ),

    /**
     * Bowl Mushroom
     *
     * @constructor Bowl Mushroom
     */
    BOWL_MUSHROOM(
        cooked = 7082,
        raw = 7080,
        burnt = 7094,
        level = 46,
        experience = 60.0,
        low = 16,
        high = 282,
        lowRange = 16,
        highRange = 282
    ),

    /**
     * Baked Potato
     *
     * @constructor Baked Potato
     */
    BAKED_POTATO(
        cooked = 6701,
        raw = 1942,
        burnt = 6699,
        level = 7,
        experience = 15.0,
        low = 0,
        high = 0,
        lowRange = 108,
        highRange = 472
    ),

    /**
     * Sweetcorn
     *
     * @constructor Sweetcorn
     */
    SWEETCORN(
        cooked = 5988,
        raw = 5986,
        burnt = 5990,
        level = 28,
        experience = 104.0,
        low = 78,
        high = 412,
        lowRange = 78,
        highRange = 412
    ),

    /**
     * Barley Malt
     *
     * @constructor Barley Malt
     */
    BARLEY_MALT(
        cooked = 6008,
        raw = 6006,
        burnt = 6008,
        level = 1,
        experience = 1.0,
        low = 0,
        high = 0,
        lowRange = 0,
        highRange = 0
    ),

    /**
     * Raw Oomlie
     *
     * @constructor Raw Oomlie
     */
    RAW_OOMLIE(
        cooked = 2337,
        raw = 0,
        burnt = 2426,
        level = 50,
        experience = 0.0,
        low = 0,
        high = 0,
        lowRange = 0,
        highRange = 0
    ),

    /**
     * Oomlie Wrap
     *
     * @constructor Oomlie Wrap
     */
    OOMLIE_WRAP(
        cooked = 2343,
        raw = 2341,
        burnt = 2345,
        level = 50,
        experience = 30.0,
        low = 106,
        high = 450,
        lowRange = 112,
        highRange = 476
    ),

    /**
     * Seaweed
     *
     * @constructor Seaweed
     */
    SEAWEED(
        cooked = 401,
        raw = 0,
        burnt = 1781,
        level = 0,
        experience = 0.0,
        low = 0,
        high = 0,
        lowRange = 0,
        highRange = 0
    ),

    /**
     * Sinew
     *
     * @constructor Sinew
     */
    SINEW(
        cooked = 9436,
        raw = 2132,
        burnt = 9436,
        level = 0,
        experience = 3.0,
        low = 0,
        high = 0,
        lowRange = 0,
        highRange = 0
    ),

    /**
     * Swamp Paste
     *
     * @constructor Swamp Paste
     */
    SWAMP_PASTE(
        cooked = 1941,
        raw = 1940,
        burnt = 1941,
        level = 0,
        experience = 2.0,
        low = 0,
        high = 0,
        lowRange = 0,
        highRange = 0
    );


    companion object {
        val cookingMap: HashMap<Int, CookableItems> = HashMap()
        val intentionalBurnMap: HashMap<Int, CookableItems?> = HashMap()
        val gauntletValues: HashMap<Int, IntArray> = HashMap()
        val lumbridgeRangeValues: HashMap<Int, IntArray> = HashMap()

        init {
            for (item in values()) {
                cookingMap.putIfAbsent(item.raw, item)
                intentionalBurnMap.putIfAbsent(item.cooked, item)
            }

            gauntletValues[Items.RAW_LOBSTER_377] = intArrayOf(55, 368)
            gauntletValues[Items.RAW_SWORDFISH_371] = intArrayOf(30, 310)
            gauntletValues[Items.RAW_MONKFISH_7944] = intArrayOf(24, 290)
            gauntletValues[Items.RAW_SHARK_383] = intArrayOf(15, 270)

            lumbridgeRangeValues[Items.BREAD_DOUGH_2307] = intArrayOf(128, 512)
            lumbridgeRangeValues[Items.RAW_BEEF_2132] = intArrayOf(138, 532)
            lumbridgeRangeValues[Items.RAW_RAT_MEAT_2134] = intArrayOf(138, 532)
            lumbridgeRangeValues[Items.RAW_BEAR_MEAT_2136] = intArrayOf(138, 532)
            lumbridgeRangeValues[Items.RAW_YAK_MEAT_10816] = intArrayOf(138, 532)
            lumbridgeRangeValues[Items.RAW_CHICKEN_2138] = intArrayOf(138, 532)
            lumbridgeRangeValues[Items.RAW_SHRIMPS_317] = intArrayOf(138, 532)
            lumbridgeRangeValues[Items.RAW_ANCHOVIES_321] = intArrayOf(138, 532)
            lumbridgeRangeValues[Items.RAW_SARDINE_327] = intArrayOf(128, 512)
            lumbridgeRangeValues[Items.RAW_HERRING_345] = intArrayOf(118, 492)
            lumbridgeRangeValues[Items.RAW_MACKEREL_353] = intArrayOf(108, 472)
            lumbridgeRangeValues[Items.UNCOOKED_BERRY_PIE_2321] = intArrayOf(108, 462)
            lumbridgeRangeValues[Items.THIN_SNAIL_3363] = intArrayOf(103, 464)
            lumbridgeRangeValues[Items.RAW_TROUT_335] = intArrayOf(98, 452)
            lumbridgeRangeValues[Items.LEAN_SNAIL_3365] = intArrayOf(95, 448)
            lumbridgeRangeValues[Items.RAW_COD_341] = intArrayOf(93, 442)
            lumbridgeRangeValues[Items.RAW_PIKE_349] = intArrayOf(88, 432)
            lumbridgeRangeValues[Items.UNCOOKED_MEAT_PIE_2319] = intArrayOf(88, 432)
            lumbridgeRangeValues[Items.FAT_SNAIL_3367] = intArrayOf(83, 422)
            lumbridgeRangeValues[Items.UNCOOKED_STEW_2001] = intArrayOf(78, 412)
            lumbridgeRangeValues[Items.RAW_SALMON_331] = intArrayOf(78, 402)
        }

        @JvmStatic
        fun forId(id: Int): CookableItems? {
            return cookingMap[id]
        }

        @JvmStatic
        fun getBurnt(id: Int): Item {
            return Item(cookingMap[id]!!.burnt)
        }

        @JvmStatic
        fun intentionalBurn(id: Int): Boolean {
            return (intentionalBurnMap[id] != null)
        }

        @JvmStatic
        fun getIntentionalBurn(id: Int): Item {
            return Item(intentionalBurnMap[id]!!.burnt)
        }
    }
}
