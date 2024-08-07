package content.global.skill.production.crafting.data

import core.api.consts.Animations
import core.api.consts.Items
import core.game.node.item.Item
import core.game.world.update.flag.context.Animation

/**
 * Gem data
 *
 * @property uncut
 * @property gem
 * @property level
 * @property animation
 * @property exp
 * @constructor Gem data
 */
enum class GemData(val uncut: Item, val gem: Item, val level: Int, val animation: Animation, val exp: Double) {
    /**
     * Sapphire
     *
     * @constructor Sapphire
     */
    SAPPHIRE(
        uncut = Item(Items.UNCUT_SAPPHIRE_1623),
        gem = Item(Items.SAPPHIRE_1607),
        level = 20,
        animation = Animation(Animations.CUT_SAPPHIRE_888),
        exp = 50.0
    ),

    /**
     * Emerald
     *
     * @constructor Emerald
     */
    EMERALD(
        uncut = Item(Items.UNCUT_EMERALD_1621),
        gem = Item(Items.EMERALD_1605),
        level = 27,
        animation = Animation(Animations.CUT_EMERALD_889),
        exp = 67.0
    ),

    /**
     * Ruby
     *
     * @constructor Ruby
     */
    RUBY(
        uncut = Item(Items.UNCUT_RUBY_1619),
        gem = Item(Items.RUBY_1603),
        level = 34,
        animation = Animation(Animations.CUT_RUBY_887),
        exp = 85.0
    ),

    /**
     * Diamond
     *
     * @constructor Diamond
     */
    DIAMOND(
        uncut = Item(Items.UNCUT_DIAMOND_1617),
        gem = Item(Items.DIAMOND_1601),
        level = 43,
        animation = Animation(Animations.CUT_DIAMOND_886),
        exp = 107.5
    ),

    /**
     * Dragonstone
     *
     * @constructor Dragonstone
     */
    DRAGONSTONE(
        uncut = Item(Items.UNCUT_DRAGONSTONE_1631),
        gem = Item(Items.DRAGONSTONE_1615),
        level = 55,
        animation = Animation(Animations.CUT_DRAGONSTONE_885),
        exp = 137.5
    ),

    /**
     * Onyx
     *
     * @constructor Onyx
     */
    ONYX(
        uncut = Item(Items.UNCUT_ONYX_6571),
        gem = Item(Items.ONYX_6573),
        level = 67,
        animation = Animation(Animations.CHISEL_ONYX_2717),
        exp = 168.0
    ),

    /**
     * Opal
     *
     * @constructor Opal
     */
    OPAL(
        uncut = Item(Items.UNCUT_OPAL_1625),
        gem = Item(Items.OPAL_1609),
        level = 1,
        animation = Animation(Animations.CUT_OPAL_890),
        exp = 10.0
    ),

    /**
     * Jade
     *
     * @constructor Jade
     */
    JADE(
        uncut = Item(Items.UNCUT_JADE_1627),
        gem = Item(Items.JADE_1611),
        level = 13,
        animation = Animation(Animations.CUT_JADE_891),
        exp = 20.0
    ),

    /**
     * Red Topaz
     *
     * @constructor Red Topaz
     */
    RED_TOPAZ(
        uncut = Item(Items.UNCUT_RED_TOPAZ_1629),
        gem = Item(Items.RED_TOPAZ_1613),
        level = 16,
        animation = Animation(Animations.CUT_TOPAZ_892),
        exp = 25.0
    );

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
