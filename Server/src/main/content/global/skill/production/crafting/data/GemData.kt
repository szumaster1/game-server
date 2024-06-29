package content.global.skill.production.crafting.data

import core.game.node.item.Item
import core.game.world.update.flag.context.Animation

enum class GemData(val uncut: Item, val gem: Item, val level: Int, val animation: Animation, val exp: Double) {
    SAPPHIRE(Item(1623), Item(1607), 20, Animation(888), 50.0),
    EMERALD(Item(1621), Item(1605), 27, Animation(889), 67.0),
    RUBY(Item(1619), Item(1603), 34, Animation(887), 85.0),
    DIAMOND(Item(1617), Item(1601), 43, Animation(886), 107.5),
    DRAGONSTONE(Item(1631), Item(1615), 55, Animation(885), 137.5),
    ONYX(Item(6571), Item(6573), 67, Animation(2717), 168.0),
    OPAL(Item(1625), Item(1609), 1, Animation(890), 10.0),
    JADE(Item(1627), Item(1611), 13, Animation(891), 20.0),
    RED_TOPAZ(Item(1629), Item(1613), 16, Animation(892), 25.0);

    companion object {
        fun forId(item: Item): GemData? {
            for (gem in values()) {
                if (gem.uncut.id == item.id) {
                    return gem
                }
            }
            return null
        }
    }
}
