package content.global.handlers.iface.warning

import core.api.closeInterface
import core.api.consts.Animations
import core.api.consts.Components
import core.api.consts.NPCs
import core.api.sendChat
import core.game.global.action.ClimbActionHandler
import core.game.interaction.InterfaceListener
import core.game.world.map.Location
import core.game.world.map.RegionManager.getLocalNpcs
import core.game.world.update.flag.context.Animation

class RangingGuildWarningInterface : InterfaceListener {

    override fun defineInterfaceListeners() {

        on(Components.CWS_WARNING_23_564) { player, _, _, buttonID, _, _ ->
            when (buttonID) {
                17 -> {
                    ClimbActionHandler.climb(player, Animation(Animations.USE_LADDER_828), Location(2668, 3427, 2))
                    closeInterface(player)
                    val npc = getLocalNpcs(Location.create(2668, 3427, 2))
                    var dir = ""
                    for (n in npc) if (n.id >= NPCs.TOWER_ADVISOR_684 && n.id <= NPCs.TOWER_ADVISOR_687) {
                        when (n.id) {
                            NPCs.TOWER_ADVISOR_684 -> dir = "north"
                            NPCs.TOWER_ADVISOR_685 -> dir = "east"
                            NPCs.TOWER_ADVISOR_686 -> dir = "south"
                            NPCs.TOWER_ADVISOR_687 -> dir = "west"
                        }
                        sendChat(n, "The $dir tower is occupied, get them!")
                    }
                }

                18 -> closeInterface(player)
            }

            return@on true
        }
    }
}
