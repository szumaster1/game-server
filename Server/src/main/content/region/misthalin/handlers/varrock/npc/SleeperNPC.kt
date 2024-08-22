package content.region.misthalin.handlers.varrock.npc

import cfg.consts.NPCs
import core.api.visualize
import core.game.node.entity.npc.NPC
import core.game.node.entity.npc.NPCBehavior
import core.tools.RandomFunction

/**
 * Represents the Sleeper NPC.
 */
class SleeperNPC : NPCBehavior(*sleepyNPC) {

    companion object {
        private val sleepyNPC = intArrayOf(NPCs.MARTINA_SCORSBY_3326, NPCs.JEREMY_CLERKSIN_3327)
    }
    /*
     * Info: Jeremy Clerksin is a man attending Father Lawrence's
     * service in the Varrock branch of the Church of Saradomin.
     * He is asleep in one of the pews, put to sleep by Lawrence's
     * lacklustre preachings, along with Martina Scorsby.
     */

    override fun onCreation(self: NPC) {
        self.isWalks = false
    }

    override fun tick(self: NPC): Boolean {
        if (RandomFunction.random(35) == 5) {
            visualize(self, -1, 1056)
        }
        return true
    }
}
