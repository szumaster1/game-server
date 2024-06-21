package content.global.skill.production.runecrafting.staff

import core.game.node.item.Item

enum class Staff(val item: Item, val experience: Double) {
    AIR_RUNECRAFTING_STAFF(Item(13630), 25.0),
    MIND_RUNECRAFTING_STAFF(Item(13631), 27.5),
    WATER_RUNECRAFTING_STAFF(Item(13632), 30.0),
    EARTH_RUNECRAFTING_STAFF(Item(13633), 32.5),
    FIRE_RUNECRAFTING_STAFF(Item(13634), 35.0),
    BODY_RUNECRAFTING_STAFF(Item(13635), 37.5),
    COSMIC_RUNECRAFTING_STAFF(Item(13636), 40.0),
    CHAOS_RUNECRAFTING_STAFF(Item(13637), 43.5),
    NATURE_RUNECRAFTING_STAFF(Item(13638), 45.0),
    LAW_RUNECRAFTING_STAFF(Item(13639), 47.5),
    DEATH_RUNECRAFTING_STAFF(Item(13640), 50.0),
    BLOOD_RUNECRAFTING_STAFF(Item(13641), 52.5);

    companion object {
        infix fun from(item: Item): Staff? {
            return Staff.values()
                .firstOrNull { it.item == item }
        }
    }
}