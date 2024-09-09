package content.global.activity.jobs.impl

import cfg.consts.NPCs

/**
 * An enum of NPCs that can assign the player a job.
 */
enum class Employers(val npcId: Int) {
    /**
     * Woodcutting Tutor.
     */
    WOODCUTTING_TUTOR(NPCs.WOODCUTTING_TUTOR_4906),

    /**
     * Magic Tutor.
     */
    MAGIC_TUTOR(NPCs.MAGIC_TUTOR_4707),

    /**
     * Melee Tutor.
     */
    MELEE_TUTOR(NPCs.MELEE_TUTOR_705),

    /**
     * Ranged Tutor.
     */
    RANGED_TUTOR(NPCs.RANGED_TUTOR_1861),

    /**
     * Cooking Tutor.
     */
    COOKING_TUTOR(NPCs.COOKING_TUTOR_4899),

    /**
     * Crafting Tutor.
     */
    CRAFTING_TUTOR(NPCs.CRAFTING_TUTOR_4900),

    /**
     * Fishing Tutor.
     */
    FISHING_TUTOR(NPCs.FISHING_TUTOR_4901),

    /**
     * Mining Tutor.
     */
    MINING_TUTOR(NPCs.MINING_TUTOR_4902),

    /**
     * Smelting Tutor.
     */
    SMELTING_TUTOR(NPCs.SMELTING_TUTOR_4904),

    /**
     * Prayer Tutor.
     */
    PRAYER_TUTOR(NPCs.PRAYER_TUTOR_4903),

    /**
     * Hans.
     */
    HANS(NPCs.HANS_0),

    /**
     * Gee.
     */
    GEE(NPCs.GEE_2237),

    /**
     * Donie.
     */
    DONIE(NPCs.DONIE_2238),

    /**
     * Victoria.
     */
    VICTORIA(NPCs.VICTORIA_7872),

    /**
     * Iain.
     */
    IAIN(NPCs.IAIN_7868),

    /**
     * Samuel.
     */
    SAMUEL(NPCs.SAMUEL_7871),

    /**
     * Gillie Groats.
     */
    GILLIE_GROATS(NPCs.GILLIE_GROATS_3807),

    /**
     * Aggie.
     */
    AGGIE(NPCs.AGGIE_922);
}