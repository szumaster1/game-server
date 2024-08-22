package content.region.misthalin.handlers.dorgeshuun.npc

import core.api.*
import core.game.node.entity.npc.NPC
import core.game.node.entity.npc.NPCBehavior
import core.tools.RandomFunction
import cfg.consts.NPCs

/**
 * Represents the Zenkog NPC.
 */
class ZenkogNPC : NPCBehavior(NPCs.ZENKOG_5797) {

    private val forceChat = arrayOf(
        "Fingers!",
        "Wall beast fingers!",
        "Fresh wall beast fingers!",
        "Lovely wall beast fingers!",
        "Tasty wall beast fingers!"
    )

    override fun tick(self: NPC): Boolean {
        if (RandomFunction.random(35) == 10)
            sendChat(self, forceChat.random())
        return super.tick(self)
    }
}
