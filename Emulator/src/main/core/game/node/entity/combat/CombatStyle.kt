package core.game.node.entity.combat

import core.game.node.entity.player.link.prayer.PrayerType

/**
 * Represents the different styles of combat.
 * @author Emperor
 *
 * @param swingHandler The handler responsible for the combat swing mechanics.
 * @param protectionPrayer The type of prayer that provides protection against this combat style.
 * @constructor Combat style
 */
enum class CombatStyle(val swingHandler: CombatSwingHandler, val protectionPrayer: PrayerType) {
    /**
     * Melee
     */
    MELEE(MeleeSwingHandler(), PrayerType.PROTECT_FROM_MELEE),

    /**
     * Range
     */
    RANGE(RangeSwingHandler(), PrayerType.PROTECT_FROM_MISSILES),

    /**
     * Magic
     */
    MAGIC(MagicSwingHandler(), PrayerType.PROTECT_FROM_MAGIC);

    init {
        swingHandler.type = this
    }
}