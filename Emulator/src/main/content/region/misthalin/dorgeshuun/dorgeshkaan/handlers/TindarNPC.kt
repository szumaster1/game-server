package content.region.misthalin.dorgeshuun.dorgeshkaan.handlers

import core.api.*
import core.game.node.entity.npc.NPC
import core.game.node.entity.npc.NPCBehavior
import core.tools.RandomFunction
import org.rs.consts.NPCs

/**
 * Represents the Tindar NPC.
 */
class TindarNPC : NPCBehavior(NPCs.TINDAR_5795) {

    private val forceChat = arrayOf(
        "Crispy!",
        "Creeeespy frogs' legs!",
        "Get your crispy frogs' legs!",
        "Frogs' legs!",
        "Crispy frogs' legs!"
    )

    override fun tick(self: NPC): Boolean {
        if (RandomFunction.random(10) == 3)
            sendChat(self, forceChat.random())
        return super.tick(self)
    }
}
