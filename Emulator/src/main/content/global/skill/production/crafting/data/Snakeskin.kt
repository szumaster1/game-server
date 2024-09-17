package content.global.skill.production.crafting.data

import org.rs.consts.Items
import core.game.node.item.Item

/**
 * Represents the Snakeskin data.
 */
enum class Snakeskin(
    val product: Item,
    val level: Int,
    val experience: Double,
    val amount: Int
) {
    SNAKESKIN_BOOT(
        product = Item(Items.SNAKESKIN_BOOTS_6328),
        level = 45,
        experience = 30.0,
        amount = 6
    ),
    SNAKESKIN_VAMBRACES(
        product = Item(Items.SNAKESKIN_VBRACE_6330),
        level = 47,
        experience = 35.0,
        amount = 8
    ),
    SNAKESKIN_BANDANA(
        product = Item(Items.SNAKESKIN_BANDANA_6326),
        level = 48,
        experience = 45.0,
        amount = 5
    ),
    SNAKESKIN_CHAPS(
        product = Item(Items.SNAKESKIN_CHAPS_6324),
        level = 51,
        experience = 50.0,
        amount = 12
    ),
    SNAKESKIN_BODY(
        product = Item(Items.SNAKESKIN_BODY_6322),
        level = 53,
        experience = 55.0,
        amount = 15
    )
}