package content.global.skill.slayer.npc

import core.game.node.entity.combat.BattleState
import core.game.node.entity.npc.AbstractNPC
import core.game.world.map.Location
import core.plugin.Initializable
import org.rs.consts.NPCs

val npcIDs = intArrayOf(NPCs.LIZARD_2803, NPCs.DESERT_LIZARD_2804, NPCs.DESERT_LIZARD_2805, NPCs.DESERT_LIZARD_2806, NPCs.SMALL_LIZARD_2807, NPCs.SMALL_LIZARD_2808)

@Initializable
class DesertLizardNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location) {

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return DesertLizardNPC(id, location)
    }

    override fun checkImpact(state: BattleState) {
        super.checkImpact(state)
        var lifepoints = getSkills().lifepoints
        if (state.estimatedHit > -1) {
            lifepoints -= state.estimatedHit
            if (lifepoints < 1) {
                state.estimatedHit = lifepoints - 1
            }
            if (state.estimatedHit < 0) {
                state.estimatedHit = 0
                getSkills().lifepoints = 2
            }
        }
        if (state.secondaryHit > -1) {
            lifepoints -= state.secondaryHit
            if (lifepoints < 1) {
                state.secondaryHit = lifepoints - 1
            }
            if (state.secondaryHit < 0) {
                state.secondaryHit = 0
            }
        }
    }

    override fun getIds(): IntArray {
        return npcIDs
    }

}


