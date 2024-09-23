package content.global.ame.prisonpete

import content.global.ame.RandomEventNPC
import org.rs.consts.NPCs
import core.api.lock
import core.api.lockInteractions
import core.api.sendMessage
import core.api.utils.WeightBasedTable
import core.game.node.entity.npc.NPC

/**
 * Prison pete NPC.
 */
class PrisonPeteNPC(override var loot: WeightBasedTable? = null) : RandomEventNPC(NPCs.EVIL_BOB_2478) {

    override fun init() {
        super.init()
        lock(player, 6)
        lockInteractions(player, 6)
        content.global.ame.prisonpete.PrisonPeteUtils.teleport(player)
        sendMessage(player, "Welcome to ScapeRune.")
    }

    override fun talkTo(npc: NPC) {}
}