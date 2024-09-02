package content.global.skill.combat.prayer

import cfg.consts.Components
import content.miniquest.knightwave.handlers.KWUtils
import core.api.getAttribute
import core.api.sendDialogue
import core.api.sendMessage
import core.game.interaction.InterfaceListener
import core.game.node.entity.player.link.prayer.PrayerType

/**
 * Prayer interface listener.
 */
class PrayerInterfaceListener : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.PRAYER_271) { player, _, _, buttonID, _, _ ->
            val type = PrayerType.get(buttonID) ?: return@on true
            /*
             * Lock to prevent use of prayer when entering knight's training area.
             */
            if (getAttribute(player, KWUtils.PRAYER_LOCK, false)) {
                sendMessage(player, "You can't use it right now.")
                return@on true
            }
            /*
             * Lock when player does not meet the requirements.
             */
            if (type == PrayerType.CHIVALRY || type == PrayerType.PIETY) {
                if (!getAttribute(player, KWUtils.KW_COMPLETE, false))
                    sendDialogue(player, if (type == PrayerType.CHIVALRY) "You need a Prayer level of 60, a Defence level of ${PrayerType.CHIVALRY.defenceReq} and have completed the King's Ransom quest's Knight Wave reward to use Chivalry." else "You need a Prayer level of 70, a Defence level of ${PrayerType.PIETY.defenceReq} and to have completed the King's Ransom quest's Knight Wave reward to use Piety.")
                return@on true
            }
            player.prayer.toggle(type)
            return@on true
        }
    }
}
