package content.global.skill.production.crafting.data

import core.game.node.item.Item

enum class PotteryData(val unfinished: Item, val product: Item, val level: Int, val exp: Double, val fireExp: Double) {
    POT(Item(1787), Item(1931), 1, 6.3, 6.3),
    DISH(Item(1789), Item(2313), 7, 15.0, 10.0),
    BOWL(Item(1791), Item(1923), 8, 18.0, 15.0),
    PLANT(Item(5352), Item(5350), 19, 20.0, 17.5),
    LID(Item(4438), Item(4440), 25, 20.0, 20.0);

    companion object {
        @JvmStatic
        fun forId(id: Int): PotteryData? {
            for (def in values()) {
                if (def.unfinished.id == id) {
                    return def
                }
            }
            return null
        }
    }
}
