package content.region.misthalin.varrock.silvarea.quest.rag.handlers.npc

import org.rs.consts.Items
import org.rs.consts.NPCs
import core.api.isQuestInProgress
import core.game.node.entity.Entity
import core.game.node.entity.npc.NPC
import core.game.node.entity.npc.NPCBehavior
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.tools.RandomFunction
import org.rs.consts.QuestName

/**
 * Represents the Giant rat NPC.
 */
class GiantRatNPC : NPCBehavior(*giantRatIds) {

    companion object {
        private val giantRatIds = intArrayOf(NPCs.GIANT_RAT_4395, NPCs.GIANT_RAT_446, NPCs.GIANT_RAT_4922, NPCs.GIANT_RAT_4923, NPCs.GIANT_RAT_4924, NPCs.GIANT_RAT_4925, NPCs.GIANT_RAT_4926, NPCs.GIANT_RAT_4927, NPCs.GIANT_RAT_4942, NPCs.GIANT_RAT_4943, NPCs.GIANT_RAT_4944, NPCs.GIANT_RAT_4945, NPCs.GIANT_RAT_86, NPCs.GIANT_RAT_87, NPCs.GIANT_RAT_950)
    }

    override fun onDropTableRolled(self: NPC, killer: Entity, drops: ArrayList<Item>) {
        super.onDropTableRolled(self, killer, drops)
        // Drops the Giant Rat Bone during Rag and Bone Man quest
        if (killer is Player && isQuestInProgress(killer, QuestName.RAG_AND_BONE_MAN, 1, 99)) {
            if (RandomFunction.roll(4)) {
                drops.add(Item(Items.GIANT_RAT_BONE_7824))
            }
        }
    }
}
