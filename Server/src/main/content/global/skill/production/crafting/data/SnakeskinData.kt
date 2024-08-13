package content.global.skill.production.crafting.data

import core.api.consts.Items
import core.game.node.item.Item
/**
 * Snakeskin data
 *
 * @param product Represents the item associated with the snakeskin.
 * @param level Indicates the required level to use or craft the snakeskin.
 * @param experience Denotes the experience points gained from using the snakeskin.
 * @param requiredAmount Specifies the quantity of snakeskin needed for a particular action.
 * @constructor Snakeskin data Represents the properties of the SnakeskinData enum.
 */
enum class SnakeskinData(
    val product: Item,          // The item that corresponds to the snakeskin.
    val level: Int,            // The level required to utilize the snakeskin.
    val experience: Double,     // The experience points awarded for using the snakeskin.
    val requiredAmount: Int     // The amount of snakeskin necessary for a specific task.
) {
    /**
     * Snakeskin Boot.
     */
    SNAKESKIN_BOOT(
        product = Item(Items.SNAKESKIN_BOOTS_6328),
        level = 45,
        experience = 30.0,
        requiredAmount = 6
    ),

    /**
     * Snakeskin Vambraces.
     */
    SNAKESKIN_VAMBRACES(
        product = Item(Items.SNAKESKIN_VBRACE_6330),
        level = 47,
        experience = 35.0,
        requiredAmount = 8
    ),

    /**
     * Snakeskin Bandana.
     */
    SNAKESKIN_BANDANA(
        product = Item(Items.SNAKESKIN_BANDANA_6326),
        level = 48,
        experience = 45.0,
        requiredAmount = 5
    ),

    /**
     * Snakeskin Chaps.
     */
    SNAKESKIN_CHAPS(
        product = Item(Items.SNAKESKIN_CHAPS_6324),
        level = 51,
        experience = 50.0,
        requiredAmount = 12
    ),

    /**
     * Snakeskin Body.
     */
    SNAKESKIN_BODY(
        product = Item(Items.SNAKESKIN_BODY_6322),
        level = 53,
        experience = 55.0,
        requiredAmount = 15
    )
}
