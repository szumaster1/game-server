package content.global.skill.production.crafting.data

import core.api.consts.Items
import core.game.node.item.Item

/**
 * Snakeskin data
 *
 * @property product
 * @property level
 * @property experience
 * @property requiredAmount
 * @constructor Snakeskin data
 */
enum class SnakeskinData(val product: Item, val level: Int, val experience: Double, val requiredAmount: Int) {
    /**
     * Snakeskin Boot
     *
     * @constructor Snakeskin Boot
     */
    SNAKESKIN_BOOT(
        product = Item(Items.SNAKESKIN_BOOTS_6328),
        level = 45,
        experience = 30.0,
        requiredAmount = 6
    ),

    /**
     * Snakeskin Vambraces
     *
     * @constructor Snakeskin Vambraces
     */
    SNAKESKIN_VAMBRACES(
        product = Item(Items.SNAKESKIN_VBRACE_6330),
        level = 47,
        experience = 35.0,
        requiredAmount = 8
    ),

    /**
     * Snakeskin Bandana
     *
     * @constructor Snakeskin Bandana
     */
    SNAKESKIN_BANDANA(
        product = Item(Items.SNAKESKIN_BANDANA_6326),
        level = 48,
        experience = 45.0,
        requiredAmount = 5
    ),

    /**
     * Snakeskin Chaps
     *
     * @constructor Snakeskin Chaps
     */
    SNAKESKIN_CHAPS(
        product = Item(Items.SNAKESKIN_CHAPS_6324),
        level = 51,
        experience = 50.0,
        requiredAmount = 12
    ),

    /**
     * Snakeskin Body
     *
     * @constructor Snakeskin Body
     */
    SNAKESKIN_BODY(
        product = Item(Items.SNAKESKIN_BODY_6322),
        level = 53,
        experience = 55.0,
        requiredAmount = 15
    )
}
