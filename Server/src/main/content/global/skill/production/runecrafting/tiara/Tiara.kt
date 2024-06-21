package content.global.skill.production.runecrafting.tiara

import core.game.node.item.Item

enum class Tiara(val item: Item, val experience: Double) {
    AIR(Item(5527), 25.0),
    MIND(Item(5529), 27.5),
    WATER(Item(5531), 30.0),
    EARTH(Item(5535), 32.5),
    FIRE(Item(5537), 35.0),
    BODY(Item(5533), 37.5),
    COSMIC(Item(5539), 40.0),
    CHAOS(Item(5543), 43.5),
    NATURE(Item(5541), 45.0),
    LAW(Item(5545), 47.5),
    DEATH(Item(5547), 50.0),
    BLOOD(Item(5549), 52.5);

    companion object {
        infix fun from(item: Item): Tiara? {
            return values()
                .firstOrNull { it.item == item }
        }
    }
}