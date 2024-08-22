package content.global.skill.combat.magic.iface

import core.api.*
import cfg.consts.Components
import core.game.interaction.InterfaceListener
import core.game.node.entity.player.link.TeleportManager.TeleportType

/**
 * Teleport others interface listener.
 */
class TeleotherInterfaceListener : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.TELEPORT_OTHER_326, 5) { player, _, _, _, _, _ ->
            lock(player, 2)
            if (teleport(player, getAttribute(player, "t-o_location", player.location), TeleportType.TELE_OTHER)) {
                visualize(player, 1816, 342)
            }
            closeInterface(player)
            return@on true
        }
    }
}
