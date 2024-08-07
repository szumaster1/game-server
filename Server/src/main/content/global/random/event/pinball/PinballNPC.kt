package content.global.random.event.pinball

import content.global.random.RandomEventNPC
import core.api.*
import core.api.consts.Components
import core.api.consts.NPCs
import core.api.utils.WeightBasedTable
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.link.TeleportManager
import core.game.system.task.Pulse
import core.game.system.timer.impl.AntiMacro
import core.tools.RandomFunction

/**
 * Pinball NPC.
 */
class PinballNPC(override var loot: WeightBasedTable? = null) : RandomEventNPC(NPCs.MYSTERIOUS_OLD_MAN_410) {

    override fun init() {
        super.init()
        submitWorldPulse(object : Pulse(1) {
            var counter = 0
            override fun pulse(): Boolean {
                when (counter++) {
                    0 -> sendChat("Good day, ${player.username.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }}, care for a quick game?")
                    3 -> {
                        setAttribute(player, PinballUtils.PINBALL_SAVE_LOCATION, player.location)
                        registerLogoutListener(player, PinballUtils.PINBALL_LOGOUT) { p ->
                            p.location = getAttribute(p, PinballUtils.PINBALL_SAVE_LOCATION, player.location)
                        }
                        PinballUtils.oldMan.init()
                        PinballUtils.oldMan.properties.teleportLocation = PinballUtils.oldMan.properties.spawnLocation
                        teleport(player, PinballUtils.PINBALL_LOCATION, TeleportManager.TeleportType.NORMAL)
                    }
                    7 -> {
                        setMinimapState(player, 2)
                        openOverlay(player, Components.PINBALL_INTERFACE_263)
                        PinballUtils.handlePinballEvent(player)
                        setAttribute(player, PinballUtils.GET_PILLAR, RandomFunction.random(0,4))
                        setAttribute(player, PinballUtils.PINBALL_SCORE, 0)
                        removeTabs(player, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14)
                        findNPC(NPCs.MYSTERIOUS_OLD_MAN_410)
                        face(player, findNPC(NPCs.MYSTERIOUS_OLD_MAN_410)!!)
                        openDialogue(player, OldManDialogue())
                        AntiMacro.terminateEventNpc(player)
                        return true
                    }
                }
                return false
            }
        })
    }

    override fun talkTo(npc: NPC) {
        openDialogue(player, OldManDialogue(), npc)
    }
}
