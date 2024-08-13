package content.global.skill.gathering.fishing.data

import core.api.asItem
import core.api.consts.Items
import core.game.node.item.Item

/**
 * Fish
 *
 * @param id Unique identifier for the fish species
 * @param level The level required to catch this fish
 * @param experience The experience points gained from catching this fish
 * @param lowChance The minimum chance of catching this fish
 * @param highChance The maximum chance of catching this fish
 * @constructor Fish Enum class representing different fish species with their attributes
 */
enum class Fish(val id: Int, val level: Int, val experience: Double, val lowChance: Double, val highChance: Double) {
    /**
     * Crayfish.
     */
    CRAYFISH(
        id = Items.RAW_CRAYFISH_13435,
        level = 1,
        experience = 10.0,
        lowChance = 0.15,
        highChance = 0.5
    ),

    /**
     * Shrimp.
     */
    SHRIMP(
        id = Items.RAW_SHRIMPS_317,
        level = 1,
        experience = 10.0,
        lowChance = 0.191,
        highChance = 0.5
    ),

    /**
     * Sardine.
     */
    SARDINE(
        id = Items.RAW_SARDINE_327,
        level = 5,
        experience = 20.0,
        lowChance = 0.148,
        highChance = 0.374
    ),

    /**
     * Karambwanji.
     */
    KARAMBWANJI(
        id = Items.RAW_KARAMBWANJI_3150,
        level = 5,
        experience = 5.0,
        lowChance = 0.4,
        highChance = 0.98
    ),

    /**
     * Herring.
     */
    HERRING(
        id = Items.RAW_HERRING_345,
        level = 10,
        experience = 30.0,
        lowChance = 0.129,
        highChance = 0.504
    ),

    /**
     * Anchovie.
     */
    ANCHOVIE(
        id = Items.RAW_ANCHOVIES_321,
        level = 15,
        experience = 40.0,
        lowChance = 0.098,
        highChance = 0.5
    ),

    /**
     * Mackerel.
     */
    MACKEREL(
        id = Items.RAW_MACKEREL_353,
        level = 16,
        experience = 20.0,
        lowChance = 0.055,
        highChance = 0.258
    ),

    /**
     * Trout.
     */
    TROUT(
        id = Items.RAW_TROUT_335,
        level = 20,
        experience = 50.0,
        lowChance = 0.246,
        highChance = 0.468
    ),

    /**
     * Cod.
     */
    COD(
        id = Items.RAW_COD_341,
        level = 23,
        experience = 45.0,
        lowChance = 0.063,
        highChance = 0.219
    ),

    /**
     * Pike.
     */
    PIKE(
        id = Items.RAW_PIKE_349,
        level = 25,
        experience = 60.0,
        lowChance = 0.14,
        highChance = 0.379
    ),

    /**
     * Slimy Eel.
     */
    SLIMY_EEL(
        id = Items.SLIMY_EEL_3379,
        level = 28,
        experience = 65.0,
        lowChance = 0.117,
        highChance = 0.216
    ),

    /**
     * Salmon.
     */
    SALMON(
        id = Items.RAW_SALMON_331,
        level = 30,
        experience = 70.0,
        lowChance = 0.156,
        highChance = 0.378
    ),

    /**
     * Frog Spawn.
     */
    FROG_SPAWN(
        id = Items.FROG_SPAWN_5004,
        level = 33,
        experience = 75.0,
        lowChance = 0.164,
        highChance = 0.379
    ),

    /**
     * Tuna.
     */
    TUNA(
        id = Items.RAW_TUNA_359,
        level = 35,
        experience = 80.0,
        lowChance = 0.109,
        highChance = 0.205
    ),

    /**
     * Rainbow Fish.
     */
    RAINBOW_FISH(
        id = Items.RAW_RAINBOW_FISH_10138,
        level = 38,
        experience = 80.0,
        lowChance = 0.113,
        highChance = 0.254
    ),

    /**
     * Cave Eel.
     */
    CAVE_EEL(
        id = Items.RAW_CAVE_EEL_5001,
        level = 38,
        experience = 80.0,
        lowChance = 0.145,
        highChance = 0.316
    ),

    /**
     * Lobster.
     */
    LOBSTER(
        id = Items.RAW_LOBSTER_377,
        level = 40,
        experience = 90.0,
        lowChance = 0.16,
        highChance = 0.375
    ),

    /**
     * Bass.
     */
    BASS(
        id = Items.RAW_BASS_363,
        level = 46,
        experience = 100.0,
        lowChance = 0.078,
        highChance = 0.16
    ),

    /**
     * Swordfish.
     */
    SWORDFISH(
        id = Items.RAW_SWORDFISH_371,
        level = 50,
        experience = 100.0,
        lowChance = 0.105,
        highChance = 0.191
    ),

    /**
     * Lava Eel.
     */
    LAVA_EEL(
        id = Items.RAW_LAVA_EEL_2148,
        level = 53,
        experience = 30.0,
        lowChance = 0.227,
        highChance = 0.379
    ),

    /**
     * Monkfish.
     */
    MONKFISH(
        id = Items.RAW_MONKFISH_7944,
        level = 62,
        experience = 120.0,
        lowChance = 0.293,
        highChance = 0.356
    ),

    /**
     * Karambwan.
     */
    KARAMBWAN(
        id = Items.RAW_KARAMBWAN_3142,
        level = 65,
        experience = 105.0,
        lowChance = 0.414,
        highChance = 0.629
    ),

    /**
     * Shark.
     */
    SHARK(
        id = Items.RAW_SHARK_383,
        level = 76,
        experience = 110.0,
        lowChance = 0.121,
        highChance = 0.16
    ),

    /**
     * Sea Turtle.
     */
    SEA_TURTLE(
        id = Items.RAW_SEA_TURTLE_395,
        level = 79,
        experience = 38.0,
        lowChance = 0.0,
        highChance = 0.0
    ),

    /**
     * Manta Ray.
     */
    MANTA_RAY(
        id = Items.RAW_MANTA_RAY_389,
        level = 81,
        experience = 46.0,
        lowChance = 0.0,
        highChance = 0.0
    ),

    /**
     * Seaweed.
     */
    SEAWEED(
        id = Items.SEAWEED_401,
        level = 16,
        experience = 1.0,
        lowChance = 0.63,
        highChance = 0.219
    ),

    /**
     * Casket.
     */
    CASKET(
        id = Items.CASKET_405,
        level = 16,
        experience = 10.0,
        lowChance = 0.63,
        highChance = 0.219
    ),

    /**
     * Oyster.
     */
    OYSTER(
        id = Items.OYSTER_407,
        level = 16,
        experience = 10.0,
        lowChance = 0.63,
        highChance = 0.219
    );

    companion object {
        val fishMap: HashMap<Int, Fish> = HashMap()
        val bigFishMap: HashMap<Fish, Int> = HashMap()

        init {
            for (fish in values()) {
                fishMap[fish.id] = fish
            }
            bigFishMap[BASS] = Items.BIG_BASS_7989
            bigFishMap[SWORDFISH] = Items.BIG_SWORDFISH_7991
            bigFishMap[SHARK] = Items.BIG_SHARK_7993
        }

        @JvmStatic
        fun getBigFish(fish: Fish): Int? {
            return bigFishMap[fish]
        }

        @JvmStatic
        fun forItem(item: Item): Fish? {
            return fishMap[item.id]
        }


    }

    /**
     * Get success chance
     *
     * @param level
     * @return
     */
    fun getSuccessChance(level: Int): Double {
        return (level.toDouble() - 1.0) * ((highChance - lowChance) / (99.0 - 1.0)) + lowChance
    }

    /**
     * Get item
     *
     * @return
     */
    fun getItem(): Item {
        return this.id.asItem()
    }
}
