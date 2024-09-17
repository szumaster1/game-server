package content.region.misthalin.lumbridge.quest.haunted.handlers

import org.rs.consts.NPCs
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.world.map.Location

/**
 * Represents the Ernest Chicken NPC.
 */
class ErnestChickenNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location) {

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return ErnestChickenNPC(id, location)
    }

    override fun isHidden(player: Player): Boolean {
        val questStage = player.getQuestRepository().getQuest("Ernest the Chicken").getStage(player)
        return questStage == 100 || player.getAttribute("ernest-hide", false)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.CHICKEN_288)
    }
}