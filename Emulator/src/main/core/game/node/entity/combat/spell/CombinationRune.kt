package core.game.node.entity.combat.spell

import cfg.consts.Items

/**
 * Combination rune
 *
 * @param id Unique identifier for the combination rune
 * @param types Types of runes that can be combined to create this rune
 * @constructor Combination rune
 */
enum class CombinationRune(var id: Int, vararg var types: Runes) {
    /**
     * Lava Rune
     *
     * @constructor Lava Rune
     */
    LAVA_RUNE(Items.LAVA_RUNE_4699, Runes.FIRE_RUNE, Runes.EARTH_RUNE), // Represents the Lava Rune, combining Fire and Earth runes

    /**
     * Steam Rune
     *
     * @constructor Steam Rune
     */
    STEAM_RUNE(Items.STEAM_RUNE_4694, Runes.FIRE_RUNE, Runes.WATER_RUNE), // Represents the Steam Rune, combining Fire and Water runes

    /**
     * Mist Rune
     *
     * @constructor Mist Rune
     */
    MIST_RUNE(Items.MIST_RUNE_4695, Runes.WATER_RUNE, Runes.AIR_RUNE), // Represents the Mist Rune, combining Water and Air runes

    /**
     * Dust Rune
     *
     * @constructor Dust Rune
     */
    DUST_RUNE(Items.DUST_RUNE_4696, Runes.AIR_RUNE, Runes.EARTH_RUNE), // Represents the Dust Rune, combining Air and Earth runes

    /**
     * Smoke Rune
     *
     * @constructor Smoke Rune
     */
    SMOKE_RUNE(Items.SMOKE_RUNE_4697, Runes.FIRE_RUNE, Runes.AIR_RUNE), // Represents the Smoke Rune, combining Fire and Air runes

    /**
     * Mud Rune
     *
     * @constructor Mud Rune
     */
    MUD_RUNE(Items.MUD_RUNE_4698, Runes.EARTH_RUNE, Runes.WATER_RUNE), // Represents the Mud Rune, combining Earth and Water runes

    /**
     * Elemental Rune
     *
     * @constructor Elemental Rune
     */
    ELEMENTAL_RUNE(Items.ELEMENTAL_RUNE_12850, Runes.AIR_RUNE, Runes.WATER_RUNE, Runes.EARTH_RUNE, Runes.FIRE_RUNE), // Represents the Elemental Rune, combining all four basic rune types

    /**
     * Catalytic Rune
     *
     * @constructor Catalytic Rune
     */
    CATALYTIC_RUNE(Items.CATALYTIC_RUNE_12851, Runes.MIND_RUNE, Runes.CHAOS_RUNE, Runes.DEATH_RUNE, Runes.BLOOD_RUNE, Runes.SOUL_RUNE, Runes.ASTRAL_RUNE); // Represents the Catalytic Rune, combining various advanced runes

    companion object {

        /**
         * Function to determine which combination runes are eligible for a given rune type
         *
         * @param rune The rune type to check eligibility against
         * @return A list of combination runes that can be created with the specified rune
         */
        @JvmStatic
        fun eligibleFor(rune: Runes): List<CombinationRune> {
            val runes: MutableList<CombinationRune> = ArrayList(20) // Initialize a mutable list to hold eligible combination runes
            for (r in values()) { // Iterate through all combination runes
                for (ru in r.types) { // Iterate through the types of each combination rune
                    if (ru == rune) { // Check if the current type matches the specified rune
                        runes.add(r) // Add the combination rune to the list if it matches
                    }
                }
            }
            return runes // Return the list of eligible combination runes
        }
    }
}