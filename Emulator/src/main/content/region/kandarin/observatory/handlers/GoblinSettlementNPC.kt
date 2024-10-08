package content.region.kandarin.observatory.handlers

import org.rs.consts.NPCs
import core.api.sendChat
import core.game.node.entity.npc.NPC
import core.game.node.entity.npc.NPCBehavior
import core.tools.RandomFunction

/**
 * Goblin Observatory behavior.
 */
class GoblinSettlementNPC : NPCBehavior(NPCs.GREASYCHEEKS_6127, NPCs.SMELLYTOES_6128, NPCs.CREAKYKNEES_6129) {

    /*
     * Goblins that sit in front of the entrance to
     * the observatory dungeon.
     */

    companion object {
        private val greasyOverheadDialogues = arrayOf("This is gonna taste sooo good", "Cook, cook, cook!", "I'm so hungry!")
        private val smellyOverheadDialogues = arrayOf("La la la. Do di dum dii!", "Doh ray meeee laa doh faaa!")
        private val creakyOverheadDialogues = arrayOf("Come on! Please light!", "Was that a spark?", "I'm so hungry!")
    }

    override fun onCreation(self: NPC) {
        self.isNeverWalks = false
        self.isWalks = false
    }

    override fun tick(self: NPC): Boolean {
        if (RandomFunction.roll(25))
            when (self.id) {
                NPCs.GREASYCHEEKS_6127 -> sendChat(self, greasyOverheadDialogues.random())
                NPCs.SMELLYTOES_6128 -> sendChat(self, smellyOverheadDialogues.random())
                NPCs.CREAKYKNEES_6129 -> sendChat(self, creakyOverheadDialogues.random())
            }
        return true
    }
}
