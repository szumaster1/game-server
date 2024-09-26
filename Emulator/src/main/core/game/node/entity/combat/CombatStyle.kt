package core.game.node.entity.combat

import core.game.node.entity.player.link.prayer.PrayerType

/**
 * Represents the different styles of combat.
 *
 * @param swingHandler The handler responsible for the combat swing mechanics.
 * @param protectionPrayer The type of prayer that provides protection against this combat style.
 */
enum class CombatStyle(val swingHandler: CombatSwingHandler, val protectionPrayer: PrayerType) {
    MELEE(MeleeSwingHandler(), PrayerType.PROTECT_FROM_MELEE),
    RANGE(RangeSwingHandler(), PrayerType.PROTECT_FROM_MISSILES),
    MAGIC(MagicSwingHandler(), PrayerType.PROTECT_FROM_MAGIC);

    init {
        swingHandler.type = this
    }
}