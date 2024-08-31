package content.global.skill.production.runecrafting.data

import cfg.consts.Items
import core.game.node.item.Item

/**
 * Talisman staff
 *
 * @param item Represents the item associated with the Talisman staff.
 * @param staff Represents the staff associated with the Talisman staff.
 * @param tiara Represents the integer value of the tiara associated with the Talisman staff.
 * @constructor Talisman staff Represents a new instance of the Talisman staff with the specified item, staff, and tiara.
 */
enum class TalismanStaff(val item: Item, val staff: Staff, val tiara: Int) {
    /**
     * Air
     *
     * @constructor Air
     */
    AIR(
        item = Item(Items.AIR_TALISMAN_1438),
        staff = Staff.AIR_RC_STAFF,
        tiara = Items.AIR_TIARA_5527
    ),

    /**
     * Mind
     *
     * @constructor Mind
     */
    MIND(
        item = Item(Items.MIND_TALISMAN_1448),
        staff = Staff.MIND_RC_STAFF,
        tiara = Items.MIND_TIARA_5529
    ),

    /**
     * Water
     *
     * @constructor Water
     */
    WATER(
        item = Item(Items.WATER_TALISMAN_1444),
        staff = Staff.WATER_RC_STAFF,
        tiara = Items.WATER_TIARA_5531
    ),

    /**
     * Earth
     *
     * @constructor Earth
     */
    EARTH(
        item = Item(Items.EARTH_TALISMAN_1440),
        staff = Staff.EARTH_RC_STAFF,
        tiara = Items.EARTH_TIARA_5535
    ),

    /**
     * Fire
     *
     * @constructor Fire
     */
    FIRE(
        item = Item(Items.FIRE_TALISMAN_1442),
        staff = Staff.FIRE_RC_STAFF,
        tiara = Items.FIRE_TIARA_5537
    ),

    /**
     * Body
     *
     * @constructor Body
     */
    BODY(
        item = Item(Items.BODY_TALISMAN_1446),
        staff = Staff.BODY_RC_STAFF,
        tiara = Items.BODY_TIARA_5533
    ),

    /**
     * Cosmic
     *
     * @constructor Cosmic
     */
    COSMIC(
        item = Item(Items.COSMIC_TALISMAN_1454),
        staff = Staff.COSMIC_RC_STAFF,
        tiara = Items.COSMIC_TIARA_5539
    ),

    /**
     * Chaos
     *
     * @constructor Chaos
     */
    CHAOS(
        item = Item(Items.CHAOS_TALISMAN_1452),
        staff = Staff.CHAOS_RC_STAFF,
        tiara = Items.CHAOS_TIARA_5543
    ),

    /**
     * Nature
     *
     * @constructor Nature
     */
    NATURE(
        item = Item(Items.NATURE_TALISMAN_1462),
        staff = Staff.NATURE_RC_STAFF,
        tiara = Items.NATURE_TIARA_5541
    ),

    /**
     * Law
     *
     * @constructor Law
     */
    LAW(
        item = Item(Items.LAW_TALISMAN_1458),
        staff = Staff.LAW_RC_STAFF,
        tiara = Items.LAW_TIARA_5545
    ),

    /**
     * Death
     *
     * @constructor Death
     */
    DEATH(
        item = Item(Items.DEATH_TALISMAN_1456),
        staff = Staff.DEATH_RC_STAFF,
        tiara = Items.DEATH_TIARA_5547
    ),

    /**
     * Blood
     *
     * @constructor Blood
     */
    BLOOD(
        item = Item(Items.BLOOD_TALISMAN_1450),
        staff = Staff.BLOOD_RC_STAFF,
        tiara = Items.BLOOD_TIARA_5549
    );

    companion object {
        infix fun from(item: Item): TalismanStaff? {
            return TalismanStaff.values().firstOrNull { it.item.id == item.id }
        }
    }
}
