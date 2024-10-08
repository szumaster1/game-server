package content.global.handlers.npc

import org.rs.consts.NPCs
import core.api.sendChat
import core.game.node.entity.npc.NPC
import core.game.node.entity.npc.NPCBehavior
import core.tools.RandomFunction

/**
 * Represents the Cow NPC.
 */
class CowNPC : NPCBehavior(NPCs.COW_81, NPCs.COW_397, NPCs.COW_955, NPCs.COW_CALF_1766, NPCs.COW_1767, NPCs.COW_3309) {

    override fun tick(self: NPC): Boolean {
        if (RandomFunction.random(45) == 5 && self.id != NPCs.COW_CALF_1766) {
            sendChat(self, "Moo")
        }
        return true
    }

}
