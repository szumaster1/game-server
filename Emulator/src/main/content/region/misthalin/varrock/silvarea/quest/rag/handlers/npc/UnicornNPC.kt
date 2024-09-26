package content.region.misthalin.varrock.silvarea.quest.rag.handlers.npc

import core.api.isQuestInProgress
import core.game.node.entity.Entity
import core.game.node.entity.npc.NPC
import core.game.node.entity.npc.NPCBehavior
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.tools.RandomFunction
import org.rs.consts.Items
import org.rs.consts.NPCs
import org.rs.consts.QuestName

/**
 * Represents the Unicorn NPC.
 */
class UnicornNPC : NPCBehavior(*unicornIds) {

    companion object {
        private val unicornIds = intArrayOf(NPCs.UNICORN_89, NPCs.UNICORN_987, NPCs.BLACK_UNICORN_133)
    }

    override fun onDropTableRolled(self: NPC, killer: Entity, drops: ArrayList<Item>) {
        super.onDropTableRolled(self, killer, drops)
        // Drops the Unicorn Bone during Rag and Bone Man quest
        if (killer is Player && isQuestInProgress(killer, QuestName.RAG_AND_BONE_MAN, 1, 99)) {
            if (RandomFunction.roll(4)) {
                drops.add(Item(Items.UNICORN_BONE_7821))
            }
        }
    }
}
