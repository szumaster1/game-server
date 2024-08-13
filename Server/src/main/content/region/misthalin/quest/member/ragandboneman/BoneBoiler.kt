package content.region.misthalin.quest.member.ragandboneman

import content.region.misthalin.quest.member.ragandboneman.BoneBoiler.values
import core.api.consts.Items

/**
 * Bone boiler.
 *
 * @param bone The ID of the bone item.
 * @param boneInVinegar The ID of the bone in vinegar item.
 * @param polishedBone The ID of the polished bone item.
 * @param boneDescription The description of the bone.
 * @constructor Creates a BoneBoiler enum instance.
 */
enum class BoneBoiler(val bone: Int, val boneInVinegar: Int, val polishedBone: Int, val boneDescription: String) {
    /**
     * Goblin Skull.
     */
    GOBLIN_SKULL(Items.GOBLIN_SKULL_7812, Items.BONE_IN_VINEGAR_7813, Items.GOBLIN_SKULL_7814, "goblin bone"),

    /**
     * Bear Ribs.
     */
    BEAR_RIBS(Items.BEAR_RIBS_7815, Items.BONE_IN_VINEGAR_7816, Items.BEAR_RIBS_7817, "bear bone"),

    /**
     * Ram Skull.
     */
    RAM_SKULL(Items.RAM_SKULL_7818, Items.BONE_IN_VINEGAR_7819, Items.RAM_SKULL_7820, "ram bone"),

    /**
     * Unicorn Bone.
     */
    UNICORN_BONE(Items.UNICORN_BONE_7821, Items.BONE_IN_VINEGAR_7822, Items.UNICORN_BONE_7823, "unicorn bone"),

    /**
     * Giant Rat Bone.
     */
    GIANT_RAT_BONE(Items.GIANT_RAT_BONE_7824, Items.BONE_IN_VINEGAR_7825, Items.GIANT_RAT_BONE_7826, "giant rat bone"),

    /**
     * Giant Bat Wing.
     */
    GIANT_BAT_WING(Items.GIANT_BAT_WING_7827, Items.BONE_IN_VINEGAR_7828, Items.GIANT_BAT_WING_7829, "giant bat bone"),

    /**
     * Wolf Bone.
     */
    WOLF_BONE(Items.WOLF_BONE_7830, Items.BONE_IN_VINEGAR_7831, Items.WOLF_BONE_7832, "wolf bone"),

    /**
     * Bat Wing.
     */
    BAT_WING(Items.BAT_WING_7833, Items.BONE_IN_VINEGAR_7834, Items.BAT_WING_7835, "bat bone"),

    /**
     * Rat Bone.
     */
    RAT_BONE(Items.RAT_BONE_7836, Items.BONE_IN_VINEGAR_7837, Items.RAT_BONE_7838, "rat bone"),

    /**
     * Baby Dragon Bone.
     */
    BABY_DRAGON_BONE(Items.BABY_DRAGON_BONE_7839, Items.BONE_IN_VINEGAR_7840, Items.BABY_DRAGON_BONE_7841, "baby blue dragon bone"),

    /**
     * Ogre Ribs.
     */
    OGRE_RIBS(Items.OGRE_RIBS_7842, Items.BONE_IN_VINEGAR_7843, Items.OGRE_RIBS_7844, "ogre bone"),

    /**
     * Jogre Bone.
     */
    JOGRE_BONE(Items.JOGRE_BONE_7845, Items.BONE_IN_VINEGAR_7846, Items.JOGRE_BONE_7847, "jogre bone"),

    /**
     * Zogre Bone.
     */
    ZOGRE_BONE(Items.ZOGRE_BONE_7848, Items.BONE_IN_VINEGAR_7849, Items.ZOGRE_BONE_7850, "zogre bone"),

    /**
     * Mogre Bone.
     */
    MOGRE_BONE(Items.MOGRE_BONE_7851, Items.BONE_IN_VINEGAR_7852, Items.MOGRE_BONE_7853, "mogre bone"),

    /**
     * Monkey Paw.
     */
    MONKEY_PAW(Items.MONKEY_PAW_7854, Items.BONE_IN_VINEGAR_7855, Items.MONKEY_PAW_7856, "monkey bone"),

    /**
     * Dagannoth Ribs.
     */
    DAGANNOTH_RIBS(Items.DAGANNOTH_RIBS_7857, Items.BONE_IN_VINEGAR_7858, Items.DAGANNOTH_RIBS_7859, "Dagannoth bone"),

    /**
     * Snake Spine.
     */
    SNAKE_SPINE(Items.SNAKE_SPINE_7860, Items.BONE_IN_VINEGAR_7861, Items.SNAKE_SPINE_7862, "snake bone"),

    /**
     * Zombie Bone.
     */
    ZOMBIE_BONE(Items.ZOMBIE_BONE_7863, Items.BONE_IN_VINEGAR_7864, Items.ZOMBIE_BONE_7865, "zombie bone"),

    /**
     * Werewolf Bone.
     */
    WEREWOLF_BONE(Items.WEREWOLF_BONE_7866, Items.BONE_IN_VINEGAR_7867, Items.WEREWOLF_BONE_7868, "werewolf bone"),

    /**
     * Moss Giant Bone.
     */
    MOSS_GIANT_BONE(Items.MOSS_GIANT_BONE_7869, Items.BONE_IN_VINEGAR_7870, Items.MOSS_GIANT_BONE_7871, "moss giant bone"),

    /**
     * Fire Giant Bone.
     */
    FIRE_GIANT_BONE(Items.FIRE_GIANT_BONE_7872, Items.BONE_IN_VINEGAR_7873, Items.FIRE_GIANT_BONE_7874, "fire giant bone"),

    /**
     * Ice Giant Ribs.
     */
    ICE_GIANT_RIBS(Items.ICE_GIANT_RIBS_7875, Items.BONE_IN_VINEGAR_7876, Items.ICE_GIANT_RIBS_7877, "ice giant bone"),

    /**
     * Terrorbird Wing.
     */
    TERRORBIRD_WING(Items.TERRORBIRD_WING_7878, Items.BONE_IN_VINEGAR_7879, Items.TERRORBIRD_WING_7880, "terrorbird bone"),

    /**
     * Ghoul Bone.
     */
    GHOUL_BONE(Items.GHOUL_BONE_7881, Items.BONE_IN_VINEGAR_7882, Items.GHOUL_BONE_7883, "ghoul bone"),

    /**
     * Troll Bone.
     */
    TROLL_BONE(Items.TROLL_BONE_7884, Items.BONE_IN_VINEGAR_7885, Items.TROLL_BONE_7886, "troll bone"),

    /**
     * Seagull Wing.
     */
    SEAGULL_WING(Items.SEAGULL_WING_7887, Items.BONE_IN_VINEGAR_7888, Items.SEAGULL_WING_7889, "seagull bone"),

    /**
     * Undead Cow Ribs.
     */
    UNDEAD_COW_RIBS(Items.UNDEAD_COW_RIBS_7890, Items.BONE_IN_VINEGAR_7891, Items.UNDEAD_COW_RIBS_7892, "undead cow bone"),

    /**
     * Experiment Bone.
     */
    EXPERIMENT_BONE(Items.EXPERIMENT_BONE_7893, Items.BONE_IN_VINEGAR_7894, Items.EXPERIMENT_BONE_7895, "experiment bone"),

    /**
     * Rabbit Bone.
     */
    RABBIT_BONE(Items.RABBIT_BONE_7896, Items.BONE_IN_VINEGAR_7897, Items.RABBIT_BONE_7898, "rabbit bone"),

    /**
     * Basilisk Bone.
     */
    BASILISK_BONE(Items.BASILISK_BONE_7899, Items.BONE_IN_VINEGAR_7900, Items.BASILISK_BONE_7901, "basilisk bone"),

    /**
     * Desert Lizard Bone.
     */
    DESERT_LIZARD_BONE(Items.DESERT_LIZARD_BONE_7902, Items.BONE_IN_VINEGAR_7903, Items.DESERT_LIZARD_BONE_7904, "desert lizard bone"),

    /**
     * Cave Goblin Skull.
     */
    CAVE_GOBLIN_SKULL(Items.CAVE_GOBLIN_SKULL_7905, Items.BONE_IN_VINEGAR_7906, Items.CAVE_GOBLIN_SKULL_7907, "cave goblin bone"),

    /**
     * Big Frog Leg.
     */
    BIG_FROG_LEG(Items.BIG_FROG_LEG_7908, Items.BONE_IN_VINEGAR_7909, Items.BIG_FROG_LEG_7910, "big frog bone"),

    /**
     * Vulture Wing.
     */
    VULTURE_WING(Items.VULTURE_WING_7911, Items.BONE_IN_VINEGAR_7912, Items.VULTURE_WING_7913, "vulture bone"),

    /**
     * Jackal Bone.
     */
    JACKAL_BONE(Items.JACKAL_BONE_7914, Items.BONE_IN_VINEGAR_7915, Items.JACKAL_BONE_7916, "jackal bone");

    companion object {
        @JvmField
        val boneList = values().map { it.bone }.toIntArray()

        @JvmField
        val boneInVinegarList = values().map { it.boneInVinegar }.toIntArray()

        @JvmField
        val boneMap = values().associateBy { it.bone }

        @JvmField
        val boneInVinegarMap = values().associateBy { it.boneInVinegar }

        /**
         * Returns the BoneBoiler instance for the given bone ID.
         *
         * @param id The ID of the bone.
         * @return The BoneBoiler instance or null if not found.
         */
        @JvmStatic
        fun forBone(id: Int): BoneBoiler? {
            return boneMap[id]
        }

        /**
         * Returns the BoneBoiler instance for the given bone in vinegar ID.
         *
         * @param id The ID of the bone in vinegar.
         * @return The BoneBoiler instance or null if not found.
         */
        @JvmStatic
        fun forBoneInVinegar(id: Int): BoneBoiler? {
            return boneInVinegarMap[id]
        }
    }
}