package content.global.skill.production.crafting.data

import cfg.consts.Animations
import cfg.consts.Items
import core.game.node.item.Item
import core.game.world.update.flag.context.Animation

/**
 * Gem data
 *
 * @param uncut Represents the uncut version of the gem.
 * @param gem Represents the cut version of the gem.
 * @param level Indicates the level required to use the gem.
 * @param animation Represents the animation associated with the gem.
 * @param exp Represents the experience points gained from using the gem.
 * @constructor Gem data
 */
enum class Gem(
    val uncut: Item,
    val gem: Item,
    val level: Int,
    val animation: Animation,
    val exp: Double
) {
    /**
     * Sapphire.
     */
    SAPPHIRE(
        uncut = Item(Items.UNCUT_SAPPHIRE_1623),
        gem = Item(Items.SAPPHIRE_1607),
        level = 20,
        animation = Animation(Animations.CUT_SAPPHIRE_888),
        exp = 50.0
    ),

    /**
     * Emerald.
     */
    EMERALD(
        uncut = Item(Items.UNCUT_EMERALD_1621),
        gem = Item(Items.EMERALD_1605),
        level = 27,
        animation = Animation(Animations.CUT_EMERALD_889),
        exp = 67.0
    ),

    /**
     * Ruby.
     */
    RUBY(
        uncut = Item(Items.UNCUT_RUBY_1619),
        gem = Item(Items.RUBY_1603),
        level = 34,
        animation = Animation(Animations.CUT_RUBY_887),
        exp = 85.0
    ),

    /**
     * Diamond.
     */
    DIAMOND(
        uncut = Item(Items.UNCUT_DIAMOND_1617),
        gem = Item(Items.DIAMOND_1601),
        level = 43,
        animation = Animation(Animations.CUT_DIAMOND_886),
        exp = 107.5
    ),

    /**
     * Dragonstone.
     */
    DRAGONSTONE(
        uncut = Item(Items.UNCUT_DRAGONSTONE_1631),
        gem = Item(Items.DRAGONSTONE_1615),
        level = 55,
        animation = Animation(Animations.CUT_DRAGONSTONE_885),
        exp = 137.5
    ),

    /**
     * Onyx.
     */
    ONYX(
        uncut = Item(Items.UNCUT_ONYX_6571),
        gem = Item(Items.ONYX_6573),
        level = 67,
        animation = Animation(Animations.CHISEL_ONYX_2717),
        exp = 168.0
    ),

    /**
     * Opal.
     */
    OPAL(
        uncut = Item(Items.UNCUT_OPAL_1625),
        gem = Item(Items.OPAL_1609),
        level = 1,
        animation = Animation(Animations.CUT_OPAL_890),
        exp = 10.0
    ),

    /**
     * Jade.
     */
    JADE(
        uncut = Item(Items.UNCUT_JADE_1627),
        gem = Item(Items.JADE_1611),
        level = 13,
        animation = Animation(Animations.CUT_JADE_891),
        exp = 20.0
    ),

    /**
     * Red Topaz.
     */
    RED_TOPAZ(
        uncut = Item(Items.UNCUT_RED_TOPAZ_1629),
        gem = Item(Items.RED_TOPAZ_1613),
        level = 16,
        animation = Animation(Animations.CUT_TOPAZ_892),
        exp = 25.0
    );

    companion object {
        fun forId(item: Item): Gem? {
            for (gem in values()) {
                if (gem.uncut.id == item.id) {
                    return gem
                }
            }
            return null
        }
    }
}
