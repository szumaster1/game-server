package content.global.skill.production.crafting.data

import core.api.consts.Items
import core.game.node.item.Item

enum class SnakeskinData(val product: Item, val level: Int, val experience: Double, val requiredAmount: Int) {
    SNAKESKIN_BOOT(
        product = Item(Items.SNAKESKIN_BOOTS_6328),
        level = 45,
        experience = 30.0,
        requiredAmount = 6
    ),
    SNAKESKIN_VAMBRACES(
        product = Item(Items.SNAKESKIN_VBRACE_6330),
        level = 47,
        experience = 35.0,
        requiredAmount = 8
    ),
    SNAKESKIN_BANDANA(
        product = Item(Items.SNAKESKIN_BANDANA_6326),
        level = 48,
        experience = 45.0,
        requiredAmount = 5
    ),
    SNAKESKIN_CHAPS(
        product = Item(Items.SNAKESKIN_CHAPS_6324),
        level = 51,
        experience = 50.0,
        requiredAmount = 12
    ),
    SNAKESKIN_BODY(
        product = Item(Items.SNAKESKIN_BODY_6322),
        level = 53,
        experience = 55.0,
        requiredAmount = 15
    )
}
