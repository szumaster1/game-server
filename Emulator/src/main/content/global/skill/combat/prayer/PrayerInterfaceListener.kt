package content.global.skill.combat.prayer

import cfg.consts.Components
import content.miniquest.knightwave.handlers.KWUtils
import core.api.getAttribute
import core.api.sendDialogue
import core.api.sendMessage
import core.game.interaction.InterfaceListener
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.prayer.PrayerType

/**
 * Represents the Prayer tab interface interaction listener.
 */
class PrayerInterfaceListener : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.PRAYER_271) { player, _, _, buttonID, _, _ ->
            val type = PrayerType.get(buttonID) ?: return@on true

            if (isPrayerLocked(player) || !canUsePrayer(player, type)) {
                return@on true
            }

            player.prayer.toggle(type)
            return@on true
        }
    }

    private fun isPrayerLocked(player: Player): Boolean {
        if (getAttribute(player, KWUtils.PRAYER_LOCK, false)) {
            sendMessage(player, "You can't use it right now.")
            return true
        }
        return false
    }

    /**
     * Can use chivalry or piety.
     *
     * @param player the player.
     * @param type the prayer type.
     * @return if meets requirements true else false
     */
    private fun canUsePrayer(player: Player, type: PrayerType): Boolean {
        return when (type) {
            PrayerType.CHIVALRY -> {
                if (!getAttribute(player, KWUtils.KW_COMPLETE, false)) {
                    sendDialogue(player, "You need a Prayer level of 60, a Defence level of ${PrayerType.CHIVALRY.defenceReq} and have completed the King's Ransom quest's Knight Wave reward to use Chivalry.")
                    false
                } else true
            }
            PrayerType.PIETY -> {
                if (!getAttribute(player, KWUtils.KW_COMPLETE, false)) {
                    sendDialogue(player, "You need a Prayer level of 70, a Defence level of ${PrayerType.PIETY.defenceReq} and to have completed the King's Ransom quest's Knight Wave reward to use Piety.")
                    false
                } else true
            }
            else -> true
        }
    }
}