package content.global.skill.production.runecrafting.data

import cfg.consts.Items
import core.game.node.item.Item

/**
 * Represents the talisman staff.
 */
enum class Staff(val item: Item, val experience: Double) {
    /**
     * The air talisman staff.
     */
    AIR_RC_STAFF(
        item = Item(Items.AIR_TALISMAN_STAFF_13630),
        experience = 25.0
    ),

    /**
     * The mind talisman staff.
     */
    MIND_RC_STAFF(
        item = Item(Items.MIND_TALISMAN_STAFF_13631),
        experience = 27.5
    ),

    /**
     * The water talisman staff.
     */
    WATER_RC_STAFF(
        item = Item(Items.WATER_TALISMAN_STAFF_13632),
        experience = 30.0
    ),

    /**
     * The earth talisman staff.
     */
    EARTH_RC_STAFF(
        item = Item(Items.EARTH_TALISMAN_STAFF_13633),
        experience = 32.5
    ),

    /**
     * The fire talisman staff.
     */
    FIRE_RC_STAFF(
        item = Item(Items.FIRE_TALISMAN_STAFF_13634),
        experience = 35.0
    ),

    /**
     * The body talisman staff.
     */
    BODY_RC_STAFF(
        item = Item(Items.BODY_TALISMAN_STAFF_13635),
        experience = 37.5
    ),

    /**
     * The cosmic talisman staff.
     */
    COSMIC_RC_STAFF(
        item = Item(Items.COSMIC_TALISMAN_STAFF_13636),
        experience = 40.0
    ),

    /**
     * The chaos talisman staff.
     */
    CHAOS_RC_STAFF(
        item = Item(Items.CHAOS_TALISMAN_STAFF_13637),
        experience = 43.5
    ),

    /**
     * The nature talisman staff.
     */
    NATURE_RC_STAFF(
        item = Item(Items.NATURE_TALISMAN_STAFF_13638),
        experience = 45.0
    ),

    /**
     * The law talisman staff.
     */
    LAW_RC_STAFF(
        item = Item(Items.LAW_TALISMAN_STAFF_13639),
        experience = 47.5
    ),

    /**
     * The death talisman staff.
     */
    DEATH_RC_STAFF(
        item = Item(Items.DEATH_TALISMAN_STAFF_13640),
        experience = 50.0
    ),

    /**
     * The blood talisman staff.
     */
    BLOOD_RC_STAFF(
        item = Item(Items.BLOOD_TALISMAN_STAFF_13641),
        experience = 52.5
    );

    companion object {
        @JvmStatic
        fun forStaff(item: Item): Staff? {
            return values().find { it.item.id == item.id }
        }
    }
}