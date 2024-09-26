package content.global.skill.runecrafting.items

import org.rs.consts.Items

/**
 * Represents a talisman staff.
 */
enum class Staff(val item: Int, val experience: Double) {
    AIR_RC_STAFF(
        item = Items.AIR_TALISMAN_STAFF_13630,
        experience = 25.0
    ),
    MIND_RC_STAFF(
        item = Items.MIND_TALISMAN_STAFF_13631,
        experience = 27.5
    ),
    WATER_RC_STAFF(
        item = Items.WATER_TALISMAN_STAFF_13632,
        experience = 30.0
    ),
    EARTH_RC_STAFF(
        item = Items.EARTH_TALISMAN_STAFF_13633,
        experience = 32.5
    ),
    FIRE_RC_STAFF(
        item = Items.FIRE_TALISMAN_STAFF_13634,
        experience = 35.0
    ),
    BODY_RC_STAFF(
        item = Items.BODY_TALISMAN_STAFF_13635,
        experience = 37.5
    ),
    COSMIC_RC_STAFF(
        item = Items.COSMIC_TALISMAN_STAFF_13636,
        experience = 40.0
    ),
    CHAOS_RC_STAFF(
        item = Items.CHAOS_TALISMAN_STAFF_13637,
        experience = 43.5
    ),
    NATURE_RC_STAFF(
        item = Items.NATURE_TALISMAN_STAFF_13638,
        experience = 45.0
    ),
    LAW_RC_STAFF(
        item = Items.LAW_TALISMAN_STAFF_13639,
        experience = 47.5
    ),
    DEATH_RC_STAFF(
        item = Items.DEATH_TALISMAN_STAFF_13640,
        experience = 50.0
    ),
    BLOOD_RC_STAFF(
        item = Items.BLOOD_TALISMAN_STAFF_13641,
        experience = 52.5
    );

    companion object {
        @JvmStatic
        fun forStaff(item: Int): Staff? = values().firstOrNull { it.item == item }
    }
}