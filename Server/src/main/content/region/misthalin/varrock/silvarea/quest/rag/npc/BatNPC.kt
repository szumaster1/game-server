package content.region.misthalin.varrock.silvarea.quest.rag.npc

import core.api.isQuestInProgress
import core.game.node.entity.Entity
import core.game.node.entity.npc.NPC
import core.game.node.entity.npc.NPCBehavior
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.tools.RandomFunction
import cfg.consts.Items
import cfg.consts.NPCs

/**
 * Represents the Bat NPC.
 */
class BatNPC : NPCBehavior(*BAT_NPC) {

    companion object {
        private val BAT_NPC = intArrayOf(NPCs.BAT_412)
    }

    override fun onDropTableRolled(self: NPC, killer: Entity, drops: ArrayList<Item>) {
        super.onDropTableRolled(self, killer, drops)
        // Drops the Bat Wing during Rag and Bone Man quest
        if (killer is Player && isQuestInProgress(killer, "Rag and Bone Man", 1, 99)) {
            if(RandomFunction.roll(4)) {
                drops.add(Item(Items.BAT_WING_7833))
            }
        }
    }
}
