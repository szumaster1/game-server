package content.global.skill.production.runecrafting.data

import org.rs.consts.Items
import core.game.node.item.Item

/**
 * Represents a talisman staff relative information.
 */
enum class TalismanStaff(val item: Item, val staff: Staff, val tiara: Int) {
    AIR(
        item = Item(Items.AIR_TALISMAN_1438),
        staff = Staff.AIR_RC_STAFF,
        tiara = Items.AIR_TIARA_5527
    ),
    MIND(
        item = Item(Items.MIND_TALISMAN_1448),
        staff = Staff.MIND_RC_STAFF,
        tiara = Items.MIND_TIARA_5529
    ),
    WATER(
        item = Item(Items.WATER_TALISMAN_1444),
        staff = Staff.WATER_RC_STAFF,
        tiara = Items.WATER_TIARA_5531
    ),
    EARTH(
        item = Item(Items.EARTH_TALISMAN_1440),
        staff = Staff.EARTH_RC_STAFF,
        tiara = Items.EARTH_TIARA_5535
    ),
    FIRE(
        item = Item(Items.FIRE_TALISMAN_1442),
        staff = Staff.FIRE_RC_STAFF,
        tiara = Items.FIRE_TIARA_5537
    ),
    BODY(
        item = Item(Items.BODY_TALISMAN_1446),
        staff = Staff.BODY_RC_STAFF,
        tiara = Items.BODY_TIARA_5533
    ),
    COSMIC(
        item = Item(Items.COSMIC_TALISMAN_1454),
        staff = Staff.COSMIC_RC_STAFF,
        tiara = Items.COSMIC_TIARA_5539
    ),
    CHAOS(
        item = Item(Items.CHAOS_TALISMAN_1452),
        staff = Staff.CHAOS_RC_STAFF,
        tiara = Items.CHAOS_TIARA_5543
    ),
    NATURE(
        item = Item(Items.NATURE_TALISMAN_1462),
        staff = Staff.NATURE_RC_STAFF,
        tiara = Items.NATURE_TIARA_5541
    ),
    LAW(
        item = Item(Items.LAW_TALISMAN_1458),
        staff = Staff.LAW_RC_STAFF,
        tiara = Items.LAW_TIARA_5545
    ),
    DEATH(
        item = Item(Items.DEATH_TALISMAN_1456),
        staff = Staff.DEATH_RC_STAFF,
        tiara = Items.DEATH_TIARA_5547
    ),
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
