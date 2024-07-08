package content.global.skill.production.runecrafting.data

import core.api.consts.Items
import core.game.node.item.Item

enum class Tiara(val item: Item, val experience: Double) {
    AIR(
        item = Item(Items.AIR_TIARA_5527),
        experience = 25.0
    ),
    MIND(
        item = Item(Items.MIND_TIARA_5529),
        experience = 27.5
    ),
    WATER(
        item = Item(Items.WATER_TIARA_5531),
        experience = 30.0
    ),
    EARTH(
        item = Item(Items.EARTH_TIARA_5535),
        experience = 32.5
    ),
    FIRE(
        item = Item(Items.FIRE_TIARA_5537),
        experience = 35.0
    ),
    BODY(
        item = Item(Items.BODY_TIARA_5533),
        experience = 37.5
    ),
    COSMIC(
        item = Item(Items.COSMIC_TIARA_5539),
        experience = 40.0
    ),
    CHAOS(
        item = Item(Items.CHAOS_TIARA_5543),
        experience = 43.5
    ),
    NATURE(
        item = Item(Items.NATURE_TIARA_5541),
        experience = 45.0
    ),
    LAW(
        item = Item(Items.LAW_TIARA_5545),
        experience = 47.5
    ),
    DEATH(
        item = Item(Items.DEATH_TIARA_5547),
        experience = 50.0
    ),
    BLOOD(
        item = Item(Items.BLOOD_TIARA_5549),
        experience = 52.5
    );

    val talisman: Talisman?
        get() = Talisman.values().find { it.name == name }

    companion object {
        infix fun from(item: Item): Tiara? = values().firstOrNull { it.item == item }
        fun forItem(item: Item): Tiara? = values().find { it.item == item }
    }
}
