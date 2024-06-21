package content.global.skill.production.runecrafting.staff

import core.game.node.item.Item

enum class TalismanStaff(val item: Item, val staff: Staff?, val tiara: Int) {
    AIR(Item(1438), Staff.AIR_RUNECRAFTING_STAFF,5527),
    MIND(Item(1448), Staff.MIND_RUNECRAFTING_STAFF,5529),
    WATER(Item(1444), Staff.WATER_RUNECRAFTING_STAFF,5531),
    EARTH(Item(1440), Staff.EARTH_RUNECRAFTING_STAFF,5535),
    FIRE(Item(1442), Staff.FIRE_RUNECRAFTING_STAFF,5537),
    BODY(Item(1446), Staff.BODY_RUNECRAFTING_STAFF,5533),
    COSMIC(Item(1454), Staff.COSMIC_RUNECRAFTING_STAFF,5539),
    CHAOS(Item(1452), Staff.CHAOS_RUNECRAFTING_STAFF,5543),
    NATURE(Item(1462), Staff.NATURE_RUNECRAFTING_STAFF,5541),
    LAW(Item(1458), Staff.LAW_RUNECRAFTING_STAFF,5545),
    DEATH(Item(1456), Staff.DEATH_RUNECRAFTING_STAFF,5547),
    BLOOD(Item(1450), Staff.BLOOD_RUNECRAFTING_STAFF,5549);
    companion object {
        infix fun from(item: Item): TalismanStaff? {
            return TalismanStaff.values().firstOrNull { it.item.id == item.id }
        }
    }
}