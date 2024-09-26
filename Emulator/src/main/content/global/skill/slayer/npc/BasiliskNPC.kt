package content.global.skill.slayer.npc

import content.global.skill.slayer.items.equipment.MirrorShieldHandler
import core.game.node.entity.combat.BattleState
import core.game.node.entity.combat.CombatSwingHandler
import core.game.node.entity.npc.AbstractNPC
import core.game.world.map.Location
import core.plugin.Initializable
import org.rs.consts.NPCs

/**
 * Represents the Basilisk NPC.
 */
@Initializable
class BasiliskNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location) {

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return BasiliskNPC(id, location)
    }

    override fun getSwingHandler(swing: Boolean): CombatSwingHandler {
        return MirrorShieldHandler.SINGLETON
    }

    override fun checkImpact(state: BattleState) {
        super.checkImpact(state)
        MirrorShieldHandler.SINGLETON.checkImpact(state)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BASILISK_1616, NPCs.BASILISK_1617)
    }
}
