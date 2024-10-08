package content.global.skill.slayer.npc

import core.game.node.entity.combat.BattleState
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.world.map.Location
import core.plugin.Initializable
import org.rs.consts.Items
import org.rs.consts.NPCs

/**
 * Represents the Bug swarm NPC.
 */
@Initializable
class BugSwarmNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location) {

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return BugSwarmNPC(id, location)
    }

    override fun checkImpact(state: BattleState) {
        super.checkImpact(state)
        if (state.attacker is Player) {
            val player = state.attacker as Player
            if (!player.equipment.containsItem(LIT_LANTERN)) {
                if (state.estimatedHit > -1) {
                    state.estimatedHit = 0
                }
                if (state.secondaryHit > -1) {
                    state.secondaryHit = 0
                }
            }
        }
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.HARPIE_BUG_SWARM_3153)
    }

    companion object {
        private val LIT_LANTERN = Item(Items.LIT_BUG_LANTERN_7053)
    }
}
