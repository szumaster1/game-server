package content.region.misthalin.varrock.museum.handlers

import org.rs.consts.NPCs
import core.api.sendChat
import core.game.node.entity.npc.NPC
import core.game.node.entity.npc.NPCBehavior
import core.tools.RandomFunction

/**
 * Represents the Museum worker NPC.
 */
class MuseumWorkerNPC : NPCBehavior(*archelogistsNPCs) {

    companion object {
        private val archelogistsNPCs = intArrayOf(
            NPCs.BARNABUS_HURMA_5932, NPCs.THIAS_LEACKE_5935,
            NPCs.MARIUS_GISTE_5933, NPCs.TINSE_TORPE_5937,
            NPCs.CADEN_AZRO_5934, NPCs.SINCO_DOAR_5936,
        )
    }
    private val forceChat =
        arrayOf(
            "How's it going, officers?",
            "Another lot for ya!",
            "Off we go again!",
            "Alright, thanks!"
        )

    override fun onCreation(self: NPC) {
        self.isWalks = false
    }

    override fun tick(self: NPC): Boolean {
        if (RandomFunction.random(35) == 5) {
            sendChat(self, forceChat.random())
        }
        return true
    }
}
