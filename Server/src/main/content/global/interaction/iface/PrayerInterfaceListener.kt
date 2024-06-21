package content.global.interaction.iface

import core.api.consts.Components
import core.game.interaction.InterfaceListener
import core.game.node.entity.player.link.prayer.PrayerType

class PrayerInterfaceListener : InterfaceListener {

    private val prayerTab = Components.PRAYER_271

    override fun defineInterfaceListeners() {
        on(prayerTab){ player, _, _, buttonID, _, _ ->
            val type = PrayerType.get(buttonID) ?: return@on true
            player.prayer.toggle(type)
            return@on true
        }
    }
}
