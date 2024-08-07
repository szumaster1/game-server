package content.region.kandarin.quest.toweroflife.npc

import core.api.consts.NPCs
import core.api.getWorldTicks
import core.api.sendChat
import core.game.node.entity.npc.NPC
import core.game.node.entity.npc.NPCBehavior

/**
 * The guns NPC.
 */
class TheGunsNPC : NPCBehavior(NPCs.THE_GUNS_5592) {
    private var lifts = 0

    override fun onCreation(self: NPC) {
        self.isWalks = false
    }

    override fun tick(self: NPC): Boolean {
        if (getWorldTicks() % 3 == 0)
            sendChat(self, lifts++.toString())
        return true
    }
}
