package core.game.node.entity.combat.spell

import core.api.consts.Items
import core.game.node.item.Item

/**
 * Represents the constants of runes.
 * @author Vexia
 *
 * @param id Unique identifier for the rune
 * @constructor Runes Enum representing different types of runes
 */
enum class Runes(val id: Int) {
    /**
     * Air Rune
     *
     * @constructor Represents the Air Rune
     */
    AIR_RUNE(Items.AIR_RUNE_556),

    /**
     * Water Rune
     *
     * @constructor Represents the Water Rune
     */
    WATER_RUNE(Items.WATER_RUNE_555),

    /**
     * Earth Rune
     *
     * @constructor Represents the Earth Rune
     */
    EARTH_RUNE(Items.EARTH_RUNE_557),

    /**
     * Fire Rune
     *
     * @constructor Represents the Fire Rune
     */
    FIRE_RUNE(Items.FIRE_RUNE_554),

    /**
     * Mind Rune
     *
     * @constructor Represents the Mind Rune
     */
    MIND_RUNE(Items.MIND_RUNE_558),

    /**
     * Nature Rune
     *
     * @constructor Represents the Nature Rune
     */
    NATURE_RUNE(Items.NATURE_RUNE_561),

    /**
     * Chaos Rune
     *
     * @constructor Represents the Chaos Rune
     */
    CHAOS_RUNE(Items.CHAOS_RUNE_562),

    /**
     * Death Rune
     *
     * @constructor Represents the Death Rune
     */
    DEATH_RUNE(Items.DEATH_RUNE_560),

    /**
     * Cosmic Rune
     *
     * @constructor Represents the Cosmic Rune
     */
    COSMIC_RUNE(Items.COSMIC_RUNE_564),

    /**
     * Blood Rune
     *
     * @constructor Represents the Blood Rune
     */
    BLOOD_RUNE(Items.BLOOD_RUNE_565),

    /**
     * Soul Rune
     *
     * @constructor Represents the Soul Rune
     */
    SOUL_RUNE(Items.SOUL_RUNE_566),

    /**
     * Astral Rune
     *
     * @constructor Represents the Astral Rune
     */
    ASTRAL_RUNE(Items.ASTRAL_RUNE_9075),

    /**
     * Law Rune
     *
     * @constructor Represents the Law Rune
     */
    LAW_RUNE(Items.LAW_RUNE_563),

    /**
     * Body Rune
     *
     * @constructor Represents the Body Rune
     */
    BODY_RUNE(Items.BODY_RUNE_559),

    /**
     * Saradomin Staff
     *
     * @constructor Represents the Saradomin Staff
     */
    SARADOMIN_STAFF(Items.SARADOMIN_STAFF_2415),

    /**
     * Guthix Staff
     *
     * @constructor Represents the Guthix Staff
     */
    GUTHIX_STAFF(Items.GUTHIX_STAFF_2416),

    /**
     * Zamorak Staff
     *
     * @constructor Represents the Zamorak Staff
     */
    ZAMORAK_STAFF(Items.ZAMORAK_STAFF_2417),

    /**
     * Zuriels Staff
     *
     * @constructor Represents the Zuriels Staff
     */
    ZURIELS_STAFF(Items.ZURIELS_STAFF_13867);

    /**
     * Transform
     *
     * @return Item created from the rune's id
     */
    fun transform(): Item {
        return Item(id) // Creates an Item instance using the rune's id
    }

    /**
     * Get item
     *
     * @param amount Number of items to create
     * @return Item created from the rune's id with specified amount
     */
    fun getItem(amount: Int): Item {
        return Item(id, amount) // Creates an Item instance with the specified amount
    }

    companion object {

        fun forId(id: Int): Runes? {
            for (rune in values()) { // Iterates through all rune values
                if (rune.id == id) { // Checks if the current rune's id matches the provided id
                    return rune // Returns the matching rune
                }
            }
            return null // Returns null if no matching rune is found
        }

        fun isInfinite(rune: Runes?, weapon: Item?, vararg type: SpellType): Boolean {
            if (weapon == null || rune == null) { // Checks if weapon or rune is null
                return false // Returns false if either is null
            }
            if (type != null) { // Checks if type is not null
                if (weapon.id == 2415 && rune == SARADOMIN_STAFF && type.size == 1) { // Checks for Saradomin Staff
                    if (type[0] === SpellType.GOD_STRIKE) { // Checks if the spell type is GOD_STRIKE
                        return true // Returns true for infinite use
                    }
                }
                if (weapon.id == 2416 && rune == GUTHIX_STAFF && type.size == 1) { // Checks for Guthix Staff
                    if (type[0] === SpellType.GOD_STRIKE) { // Checks if the spell type is GOD_STRIKE
                        return true // Returns true for infinite use
                    }
                }
                if (weapon.id == 13867 && rune == ZURIELS_STAFF && type.size == 1) { // Checks for Zuriels Staff
                    if (type[0] === SpellType.BARRAGE || type[0] === SpellType.BLITZ || type[0] === SpellType.RUSH || type[0] === SpellType.BURST) { // Checks for specific spell types
                        return true // Returns true for infinite use
                    }
                }
                if (weapon.id == 2417 && rune == ZAMORAK_STAFF && type.size == 1) { // Checks for Zamorak Staff
                    if (type[0] === SpellType.GOD_STRIKE) { // Checks if the spell type is GOD_STRIKE
                        return true // Returns true for infinite use
                    }
                }
            }
            // Checks for specific staff and rune combinations for infinite use
            if (rune == AIR_RUNE) {
                if (weapon.id == Items.STAFF_OF_AIR_1381 || weapon.id == Items.AIR_BATTLESTAFF_1397) // Checks for Air Staff
                    return true // Returns true for infinite use
            } else if (rune == WATER_RUNE) {
                if (weapon.id == Items.STAFF_OF_WATER_1383 || weapon.id == Items.WATER_BATTLESTAFF_1395) // Checks for Water Staff
                    return true // Returns true for infinite use
            } else if (rune == EARTH_RUNE) {
                if (weapon.id == Items.STAFF_OF_EARTH_1385 || weapon.id == Items.EARTH_BATTLESTAFF_1399) // Checks for Earth Staff
                    return true // Returns true for infinite use
            } else if (rune == FIRE_RUNE) {
                if (weapon.id == Items.STAFF_OF_FIRE_1387 || weapon.id == Items.FIRE_BATTLESTAFF_1393) // Checks for Fire Staff
                    return true // Returns true for infinite use
            }
            return false // Returns false if no infinite use conditions are met
        }
    }
}