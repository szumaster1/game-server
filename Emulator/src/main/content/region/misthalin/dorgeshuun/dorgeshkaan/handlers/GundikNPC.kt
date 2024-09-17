package content.region.misthalin.dorgeshuun.dorgeshkaan.handlers

import core.api.*
import core.game.node.entity.npc.NPC
import core.game.node.entity.npc.NPCBehavior
import core.tools.RandomFunction
import org.rs.consts.NPCs

/**
 * Represents the Gundik NPC.
 */
class GundikNPC : NPCBehavior(NPCs.GUNDIK_5796) {

    private val forceChat = arrayOf(
        "Spicy kebabs!",
        "Bat kebabs!",
        "Spicy bat kebabs!",
        "Bat shish kebabs!",
        "Kebabs!"
    )

    override fun tick(self: NPC): Boolean {
        if (RandomFunction.random(5) == 1)
            sendChat(self, forceChat.random())
        return super.tick(self)
    }
}
