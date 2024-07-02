package content.region.kandarin.handlers.npc

import core.api.consts.NPCs
import core.api.sendChat
import core.game.node.entity.npc.NPC
import core.game.node.entity.npc.NPCBehavior
import core.utilities.RandomFunction

class GuardCaptainNPC : NPCBehavior(NPCs.GUARD_CAPTAIN_3109) {

    /*
     * Guard inside The Dragon Inn (Yanille).
     */

    override fun tick(self: NPC): Boolean {
        if (RandomFunction.random(1, 15) == 5) {
            sendChat(self, "*HIC*")
        }
        return true
    }
}
