package core.game.node.entity.combat.spell

import core.api.consts.Items

/**
 * Combination rune
 *
 * @param id
 * @param types
 * @constructor Combination rune
 */
enum class CombinationRune(@JvmField var id: Int, vararg var types: Runes) {
    /**
     * Lava Rune
     *
     * @constructor Lava Rune
     */
    LAVA_RUNE(Items.LAVA_RUNE_4699, Runes.FIRE_RUNE, Runes.EARTH_RUNE),

    /**
     * Steam Rune
     *
     * @constructor Steam Rune
     */
    STEAM_RUNE(Items.STEAM_RUNE_4694, Runes.FIRE_RUNE, Runes.WATER_RUNE),

    /**
     * Mist Rune
     *
     * @constructor Mist Rune
     */
    MIST_RUNE(Items.MIST_RUNE_4695, Runes.WATER_RUNE, Runes.AIR_RUNE),

    /**
     * Dust Rune
     *
     * @constructor Dust Rune
     */
    DUST_RUNE(Items.DUST_RUNE_4696, Runes.AIR_RUNE, Runes.EARTH_RUNE),

    /**
     * Smoke Rune
     *
     * @constructor Smoke Rune
     */
    SMOKE_RUNE(Items.SMOKE_RUNE_4697, Runes.FIRE_RUNE, Runes.AIR_RUNE),

    /**
     * Mud Rune
     *
     * @constructor Mud Rune
     */
    MUD_RUNE(Items.MUD_RUNE_4698, Runes.EARTH_RUNE, Runes.WATER_RUNE),

    /**
     * Elemental Rune
     *
     * @constructor Elemental Rune
     */
    ELEMENTAL_RUNE(Items.ELEMENTAL_RUNE_12850, Runes.AIR_RUNE, Runes.WATER_RUNE, Runes.EARTH_RUNE, Runes.FIRE_RUNE),

    /**
     * Catalytic Rune
     *
     * @constructor Catalytic Rune
     */
    CATALYTIC_RUNE(Items.CATALYTIC_RUNE_12851, Runes.MIND_RUNE, Runes.CHAOS_RUNE, Runes.DEATH_RUNE, Runes.BLOOD_RUNE, Runes.SOUL_RUNE, Runes.ASTRAL_RUNE);

    companion object {

        @JvmStatic
        fun eligibleFor(rune: Runes): List<CombinationRune> {
            val runes: MutableList<CombinationRune> = ArrayList(20)
            for (r in values()) {
                for (ru in r.types) {
                    if (ru == rune) {
                        runes.add(r)
                    }
                }
            }
            return runes
        }
    }
}
