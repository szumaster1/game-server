package content.global.skill.production.runecrafting.data

import core.api.consts.Items
import core.game.node.item.Item

enum class Tiara(val item: Item, val experience: Double) {
    AIR(
        item = Item(Items.AIR_TIARA_5527),
        experience = 25.0
    ),
    MIND(
        item = Item(5529),
        experience = 27.5
    ),
    WATER(
        item = Item(5531),
        experience = 30.0
    ),
    EARTH(
        item = Item(5535),
        experience = 32.5
    ),
    FIRE(
        item = Item(5537),
        experience = 35.0
    ),
    BODY(
        item = Item(5533),
        experience = 37.5
    ),
    COSMIC(
        item = Item(5539),
        experience = 40.0
    ),
    CHAOS(
        item = Item(5543),
        experience = 43.5
    ),
    NATURE(
        item = Item(5541),
        experience = 45.0
    ),
    LAW(
        item = Item(5545),
        experience = 47.5
    ),
    DEATH(
        item = Item(5547),
        experience = 50.0
    ),
    BLOOD(
        item = Item(5549),
        experience = 52.5
    );

    val talisman: Talisman?
        get() {
            for (talisman in Talisman.values()) {
                if (talisman.name == name) {
                    return talisman
                }
            }
            return null
        }

    companion object {
        infix fun from(item: Item): Tiara? {
            return values()
                .firstOrNull { it.item == item }
        }

        fun forItem(item: Item): Tiara? {
            for (tiara in values()) {
                if (tiara.item == item) {
                    return tiara
                }
            }
            return null
        }
    }
}
