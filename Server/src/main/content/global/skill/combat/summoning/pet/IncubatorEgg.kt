package content.global.skill.combat.summoning.pet

import cfg.consts.Items
import core.game.node.item.Item

/**
 * Incubator egg
 *
 * @param egg Represents the item that is the egg.
 * @param level Indicates the level required for incubation.
 * @param incubationTime Specifies the time needed for the egg to incubate.
 * @param product Represents the item produced after incubation.
 * @constructor Incubator egg
 */
enum class IncubatorEgg(
    val egg: Item, // The egg item being incubated
    val level: Int, // The level required for successful incubation
    val incubationTime: Int, // The duration of the incubation process
    val product: Item // The resulting item after incubation
) {
    /**
     * Penguin
     *
     * @constructor Penguin
     */
    PENGUIN(
        egg = Item(Items.PENGUIN_EGG_12483),
        level = 30,
        incubationTime = 30,
        product = Item(Items.BABY_PENGUIN_12481)
    ),

    /**
     * Raven
     *
     * @constructor Raven
     */
    RAVEN(
        egg = Item(Items.RAVEN_EGG_11964),
        level = 50,
        incubationTime = 30,
        product = Item(Items.RAVEN_CHICK_12484)
    ),

    /**
     * Saradomin Owl
     *
     * @constructor Saradomin Owl
     */
    SARADOMIN_OWL(
        egg = Item(Items.BIRDS_EGG_5077),
        level = 70,
        incubationTime = 60,
        product = Item(Items.SARADOMIN_CHICK_12503)
    ),

    /**
     * Zamorak Hawk
     *
     * @constructor Zamorak Hawk
     */
    ZAMORAK_HAWK(
        egg = Item(Items.BIRDS_EGG_5076),
        level = 70,
        incubationTime = 60,
        product = Item(Items.ZAMORAK_CHICK_12506)
    ),

    /**
     * Guthix Raptor
     *
     * @constructor Guthix Raptor
     */
    GUTHIX_RAPTOR(
        egg = Item(Items.BIRDS_EGG_5078),
        level = 70,
        incubationTime = 60,
        product = Item(Items.GUTHIX_CHICK_12509)
    ),

    /**
     * Vulture
     *
     * @constructor Vulture
     */
    VULTURE(
        egg = Item(Items.VULTURE_EGG_11965),
        level = 85,
        incubationTime = 60,
        product = Item(Items.VULTURE_CHICK_12498)
    ),

    /**
     * Chameleon
     *
     * @constructor Chameleon
     */
    CHAMELEON(
        egg = Item(Items.CHAMELEON_EGG_12494),
        level = 90,
        incubationTime = 60,
        product = Item(Items.BABY_CHAMELEON_12492)
    ),

    /**
     * Red Dragon
     *
     * @constructor Red Dragon
     */
    RED_DRAGON(
        egg = Item(Items.RED_DRAGON_EGG_12477),
        level = 99,
        incubationTime = 60,
        product = Item(Items.HATCHLING_DRAGON_12469)
    ),

    /**
     * Black Dragon
     *
     * @constructor Black Dragon
     */
    BLACK_DRAGON(
        egg = Item(Items.BLACK_DRAGON_EGG_12480),
        level = 99,
        incubationTime = 60,
        product = Item(Items.HATCHLING_DRAGON_12475)
    ),

    /**
     * Blue Dragon
     *
     * @constructor Blue Dragon
     */
    BLUE_DRAGON(
        egg = Item(Items.BLUE_DRAGON_EGG_12478),
        level = 99,
        incubationTime = 60,
        product = Item(Items.HATCHLING_DRAGON_12471)
    ),

    /**
     * Green Dragon
     *
     * @constructor Green Dragon
     */
    GREEN_DRAGON(
        egg = Item(Items.GREEN_DRAGON_EGG_12479),
        level = 99,
        incubationTime = 60,
        product = Item(Items.HATCHLING_DRAGON_12473)
    );

    companion object {

        fun forItem(item: Item): IncubatorEgg? {
            for (e in values()) {
                if (e.egg.id == item.id) {
                    return e
                }
            }
            return null
        }
    }
}