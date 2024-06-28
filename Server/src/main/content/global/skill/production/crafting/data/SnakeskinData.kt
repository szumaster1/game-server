package content.global.skill.production.crafting.data

import core.api.consts.Items
import core.game.node.item.Item

enum class SnakeskinData(val product: Item, val level: Int, val experience: Double, val requiredAmount: Int) {
    SNAKESKIN_BOOT(Item(Items.SNAKESKIN_BOOTS_6328), 45, 30.0, 6),
    SNAKESKIN_VAMBRACES(Item(Items.SNAKESKIN_VBRACE_6330), 47, 35.0, 8),
    SNAKESKIN_BANDANA(Item(Items.SNAKESKIN_BANDANA_6326), 48, 45.0, 5),
    SNAKESKIN_CHAPS(Item(Items.SNAKESKIN_CHAPS_6324), 51, 50.0, 12),
    SNAKESKIN_BODY(Item(Items.SNAKESKIN_BODY_6322), 53, 55.0, 15)
}
