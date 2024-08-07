package core.game.node.entity.combat

import core.game.node.entity.player.link.prayer.PrayerType

/**
 * Combat style
 *
 * @property swingHandler
 * @property protectionPrayer
 * @constructor Combat style
 */
enum class CombatStyle(val swingHandler: CombatSwingHandler, val protectionPrayer: PrayerType) {
    /**
     * Melee
     *
     * @constructor Melee
     */
    MELEE(MeleeSwingHandler(), PrayerType.PROTECT_FROM_MELEE),

    /**
     * Range
     *
     * @constructor Range
     */
    RANGE(RangeSwingHandler(), PrayerType.PROTECT_FROM_MISSILES),

    /**
     * Magic
     *
     * @constructor Magic
     */
    MAGIC(MagicSwingHandler(), PrayerType.PROTECT_FROM_MAGIC);

    init {
        swingHandler.type = this
    }
}