package content.region.misthalin.quest.member.gertrudescat.npc

import core.api.consts.NPCs
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.world.map.Location
import core.plugin.Initializable

/**
 * Fluff NPC.
 */
@Initializable
class FluffNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location) {

    // Constructor for FluffNPC class.
    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return FluffNPC(id, location)
    }

    /**
     * Checks if fluff is hidden for the player.
     *
     * @param player the player to check.
     * @return true if fluff is hidden, false otherwise.
     */
    override fun isHidden(player: Player): Boolean {
        if (player.getQuestRepository().getQuest("Gertrude's Cat").getStage(player) < 20) {
            return true // Hide fluff if player's quest stage is less than 20.
        }
        return player.getAttribute("hidefluff", 0L) > System.currentTimeMillis() // Check if fluff should be hidden based on time.
    }

    // Get the NPC IDs.
    override fun getIds(): IntArray {
        return ID
    }

    companion object {
        private val ID = intArrayOf(NPCs.GERTRUDES_CAT_2997) // Array of NPC IDs.
    }
}
