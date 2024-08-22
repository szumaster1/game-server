package content.global.handlers.npc

import cfg.consts.NPCs
import core.api.sendChat
import core.game.node.entity.npc.NPC
import core.game.node.entity.npc.NPCBehavior
import core.tools.RandomFunction

/**
 * Represents the Duck NPC.
 */
class DuckNPC : NPCBehavior(NPCs.DUCK_46, NPCs.DUCK_2693, NPCs.DUCK_6113) {

    override fun tick(self: NPC): Boolean {
        if (RandomFunction.roll(100)) {
            sendChat(self, when(self.id) {
                46 -> "Eep!"
                else -> "Quack!"
            })
        }
        return true
    }
}
