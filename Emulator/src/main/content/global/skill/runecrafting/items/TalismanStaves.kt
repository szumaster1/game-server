package content.global.skill.runecrafting.items

import core.game.node.item.Item
import org.rs.consts.Items

/**
 * Represents a talisman staffs.
 *
 * @property items      The item associated with the talisman.
 * @property staves      The staff associated with the talisman.
 * @property tiara      The id of the tiara associated with the talisman.
 */
enum class TalismanStaves(val items: Item, val staves: Staves, val tiara: Int) {
    AIR(
        items = Item(Items.AIR_TALISMAN_1438),
        staves = Staves.AIR_RC_STAFF,
        tiara = Items.AIR_TIARA_5527
    ),
    MIND(
        items = Item(Items.MIND_TALISMAN_1448),
        staves = Staves.MIND_RC_STAFF,
        tiara = Items.MIND_TIARA_5529
    ),
    WATER(
        items = Item(Items.WATER_TALISMAN_1444),
        staves = Staves.WATER_RC_STAFF,
        tiara = Items.WATER_TIARA_5531
    ),
    EARTH(
        items = Item(Items.EARTH_TALISMAN_1440),
        staves = Staves.EARTH_RC_STAFF,
        tiara = Items.EARTH_TIARA_5535
    ),
    FIRE(
        items = Item(Items.FIRE_TALISMAN_1442),
        staves = Staves.FIRE_RC_STAFF,
        tiara = Items.FIRE_TIARA_5537
    ),
    BODY(
        items = Item(Items.BODY_TALISMAN_1446),
        staves = Staves.BODY_RC_STAFF,
        tiara = Items.BODY_TIARA_5533
    ),
    COSMIC(
        items = Item(Items.COSMIC_TALISMAN_1454),
        staves = Staves.COSMIC_RC_STAFF,
        tiara = Items.COSMIC_TIARA_5539
    ),
    CHAOS(
        items = Item(Items.CHAOS_TALISMAN_1452),
        staves = Staves.CHAOS_RC_STAFF,
        tiara = Items.CHAOS_TIARA_5543
    ),
    NATURE(
        items = Item(Items.NATURE_TALISMAN_1462),
        staves = Staves.NATURE_RC_STAFF,
        tiara = Items.NATURE_TIARA_5541
    ),
    LAW(
        items = Item(Items.LAW_TALISMAN_1458),
        staves = Staves.LAW_RC_STAFF,
        tiara = Items.LAW_TIARA_5545
    ),
    DEATH(
        items = Item(Items.DEATH_TALISMAN_1456),
        staves = Staves.DEATH_RC_STAFF,
        tiara = Items.DEATH_TIARA_5547
    ),
    BLOOD(
        items = Item(Items.BLOOD_TALISMAN_1450),
        staves = Staves.BLOOD_RC_STAFF,
        tiara = Items.BLOOD_TIARA_5549
    );

    companion object {
        /**
         * Gets a talisman staff based on the [item].
         *
         * @param item The Item to search for.
         * @return The corresponding [TalismanStaves] instance or `null` if not found.
         */
        infix fun from(item: Item): TalismanStaves? {
            return TalismanStaves.values().firstOrNull { it.items.id == item.id }
        }
    }
}
