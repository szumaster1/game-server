package content.global.handlers.iface.warning

import core.api.*
import core.api.consts.Components
import core.game.interaction.InterfaceListener
import core.game.world.GameWorld

class CorporealBeastWarningInterface : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.CWS_WARNING_30_650, 17) { player, _, _, _, _, _ ->
            if (!hasRequirement(player, "Summer's End")) return@on true
            if (getAttribute(player, "corp-beast-cave-delay", 0) <= GameWorld.ticks) {
                closeInterface(player)
                teleport(player, player.location.transform(4, 0, 0))
                setAttribute(player, "corp-beast-cave-delay", GameWorld.ticks + 5)
            } else {
                closeInterface(player)
            }
            return@on true
        }
    }
}
