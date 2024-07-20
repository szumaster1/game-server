package content.global.skill.support.firemaking.data

import core.api.consts.Items

enum class GnomishFirelighters(val base: Int, val product: Int) {
    RED(Items.RED_FIRELIGHTER_7329, Items.RED_LOGS_7404),
    GREEN(Items.GREEN_FIRELIGHTER_7330, Items.GREEN_LOGS_7405),
    BLUE(Items.BLUE_FIRELIGHTER_7331, Items.BLUE_LOGS_7406),
    PURPLE(Items.PURPLE_FIRELIGHTER_10326, Items.PURPLE_LOGS_10329),
    WHITE(Items.WHITE_FIRELIGHTER_10327, Items.WHITE_LOGS_10328);

    companion object {
        fun forProduct(product: Int): GnomishFirelighters? = values().find { it.base == product }
    }
}