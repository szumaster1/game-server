package content.global.skill.combat.prayer

import cfg.consts.Components
import core.game.interaction.InterfaceListener
import core.game.node.entity.player.link.prayer.PrayerType

/**
 * Prayer interface listener.
 */
class PrayerInterfaceListener : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.PRAYER_271){ player, _, _, buttonID, _, _ ->
            val type = PrayerType.get(buttonID) ?: return@on true
            player.prayer.toggle(type)
            return@on true
        }
    }
}
