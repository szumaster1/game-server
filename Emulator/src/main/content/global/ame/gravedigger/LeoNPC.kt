package content.global.ame.gravedigger

import content.global.ame.RandomEventNPC
import core.api.registerLogoutListener
import core.api.utils.WeightBasedTable
import core.game.node.entity.npc.NPC
import core.game.system.timer.impl.AntiMacro
import core.game.world.map.Location
import core.net.packet.PacketRepository
import core.net.packet.context.MinimapStateContext
import core.net.packet.outgoing.MinimapState
import core.tools.RandomFunction
import org.rs.consts.NPCs

/**
 * Leo NPC.
 */
class LeoNPC(override var loot: WeightBasedTable? = null) : RandomEventNPC(NPCs.LEO_3508) {

    override fun init() {
        super.init()
        sendChat("Can I borrow you for a minute, ${player.username}?")
    }

    override fun tick() {
        super.tick()
        if (RandomFunction.random(1, 10) == 5) {
            sendChat("Can I borrow you for a minute, ${player.username}?")
            core.api.setAttribute(player, GraveUtils.LEO_LOCATION, player.location)
            registerLogoutListener(player, GraveUtils.LEO_LOGOUT) { p ->
                p.location = core.api.getAttribute(p, GraveUtils.LEO_LOCATION, player.location)
            }
            PacketRepository.send(MinimapState::class.java, MinimapStateContext(player, 2))
            player.properties.teleportLocation = Location.create(1928, 5002, 0)
            AntiMacro.terminateEventNpc(player)
        }
    }

    override fun talkTo(npc: NPC) {
        player.dialogueInterpreter.open(LeoDialogue(), npc)
    }
}
