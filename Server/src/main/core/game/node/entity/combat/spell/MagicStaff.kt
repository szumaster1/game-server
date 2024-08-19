package core.game.node.entity.combat.spell

import core.api.consts.Items

/**
 * Represents a magic staff.
 * @author Emperor
 *
 * @param runeId The ID of the rune associated with the magic staff.
 * @param staves Vararg parameter representing different staff IDs.
 * @constructor Magic staff
 */
enum class MagicStaff(val runeId: Int, vararg val staves: Int) {
    /**
     * Fire Rune
     *
     * @constructor Fire Rune
     */
    FIRE_RUNE(Items.FIRE_RUNE_554, Items.STAFF_OF_FIRE_1387, Items.FIRE_BATTLESTAFF_1393, Items.MYSTIC_FIRE_STAFF_1401, Items.LAVA_BATTLESTAFF_3053, Items.MYSTIC_LAVA_STAFF_3054, Items.LAVA_BATTLESTAFF_3055, Items.MYSTIC_LAVA_STAFF_3056, Items.STEAM_BATTLESTAFF_11736, Items.MYSTIC_STEAM_STAFF_11738),

    /**
     * Water Rune
     *
     * @constructor Water Rune
     */
    WATER_RUNE(Items.WATER_RUNE_555, Items.STAFF_OF_WATER_1383, Items.WATER_BATTLESTAFF_1395, Items.MYSTIC_WATER_STAFF_1403, Items.MYSTIC_MUD_STAFF_6563, Items.STEAM_BATTLESTAFF_11736, Items.MYSTIC_STEAM_STAFF_11738, Items.MUD_BATTLESTAFF_6562),

    /**
     * Air Rune
     *
     * @constructor Air Rune
     */
    AIR_RUNE(Items.AIR_RUNE_556, Items.STAFF_OF_AIR_1381, Items.AIR_BATTLESTAFF_1397, Items.MYSTIC_AIR_STAFF_1405),

    /**
     * Earth Rune
     *
     * @constructor Earth Rune
     */
    EARTH_RUNE(Items.EARTH_RUNE_557, Items.LAVA_BATTLESTAFF_3053, Items.LAVA_BATTLESTAFF_3055, Items.MYSTIC_LAVA_STAFF_3056, Items.STAFF_OF_EARTH_1385, Items.EARTH_BATTLESTAFF_1399, Items.MYSTIC_EARTH_STAFF_1407, Items.MYSTIC_MUD_STAFF_6563, Items.MUD_BATTLESTAFF_6562);

    companion object {

        // A mutable map to hold the mapping of rune IDs to MagicStaff instances
        private val MAGIC_STAVES: MutableMap<Int, MagicStaff> = HashMap()

        // Initialization block to populate the MAGIC_STAVES map
        init {
            for (m in values()) {
                MAGIC_STAVES[m.runeId] = m // Mapping each rune ID to its corresponding MagicStaff
            }
        }

        /**
         * Function to retrieve a MagicStaff instance by its rune ID.
         *
         * @param runeId The ID of the rune to look up.
         * @return The corresponding MagicStaff instance or null if not found.
         */
        @JvmStatic
        fun forId(runeId: Int): MagicStaff? {
            return MAGIC_STAVES[runeId] // Return the MagicStaff associated with the given rune ID
        }
    }
}
