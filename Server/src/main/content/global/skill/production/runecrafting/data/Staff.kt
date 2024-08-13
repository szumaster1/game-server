package content.global.skill.production.runecrafting.data

import core.api.consts.Items
import core.game.node.item.Item

/**
 * Staff
 *
 * @param item Represents the item associated with the staff member.
 * @param experience Represents the years of experience the staff member has.
 * @constructor Represents a Staff instance with the specified item and experience.
 */
enum class Staff(val item: Item, val experience: Double) {
    /**
     * Air Rc Staff
     *
     * @constructor Air Rc Staff
     */
    AIR_RC_STAFF(
        item = Item(Items.AIR_TALISMAN_STAFF_13630),
        experience = 25.0
    ),

    /**
     * Mind Rc Staff
     *
     * @constructor Mind Rc Staff
     */
    MIND_RC_STAFF(
        item = Item(Items.MIND_TALISMAN_STAFF_13631),
        experience = 27.5
    ),

    /**
     * Water Rc Staff
     *
     * @constructor Water Rc Staff
     */
    WATER_RC_STAFF(
        item = Item(Items.WATER_TALISMAN_STAFF_13632),
        experience = 30.0
    ),

    /**
     * Earth Rc Staff
     *
     * @constructor Earth Rc Staff
     */
    EARTH_RC_STAFF(
        item = Item(Items.EARTH_TALISMAN_STAFF_13633),
        experience = 32.5
    ),

    /**
     * Fire Rc Staff
     *
     * @constructor Fire Rc Staff
     */
    FIRE_RC_STAFF(
        item = Item(Items.FIRE_TALISMAN_STAFF_13634),
        experience = 35.0
    ),

    /**
     * Body Rc Staff
     *
     * @constructor Body Rc Staff
     */
    BODY_RC_STAFF(
        item = Item(Items.BODY_TALISMAN_STAFF_13635),
        experience = 37.5
    ),

    /**
     * Cosmic Rc Staff
     *
     * @constructor Cosmic Rc Staff
     */
    COSMIC_RC_STAFF(
        item = Item(Items.COSMIC_TALISMAN_STAFF_13636),
        experience = 40.0
    ),

    /**
     * Chaos Rc Staff
     *
     * @constructor Chaos Rc Staff
     */
    CHAOS_RC_STAFF(
        item = Item(Items.CHAOS_TALISMAN_STAFF_13637),
        experience = 43.5
    ),

    /**
     * Nature Rc Staff
     *
     * @constructor Nature Rc Staff
     */
    NATURE_RC_STAFF(
        item = Item(Items.NATURE_TALISMAN_STAFF_13638),
        experience = 45.0
    ),

    /**
     * Law Rc Staff
     *
     * @constructor Law Rc Staff
     */
    LAW_RC_STAFF(
        item = Item(Items.LAW_TALISMAN_STAFF_13639),
        experience = 47.5
    ),

    /**
     * Death Rc Staff
     *
     * @constructor Death Rc Staff
     */
    DEATH_RC_STAFF(
        item = Item(Items.DEATH_TALISMAN_STAFF_13640),
        experience = 50.0
    ),

    /**
     * Blood Rc Staff
     *
     * @constructor Blood Rc Staff
     */
    BLOOD_RC_STAFF(
        item = Item(Items.BLOOD_TALISMAN_STAFF_13641),
        experience = 52.5
    );

    companion object {
        fun forStaff(item: Item): Staff? {
            return values().find { it.item.id == item.id }
        }
    }
}