package content.global.skill.support.slayer.npc

import content.global.skill.support.slayer.MirrorShieldHandler
import core.api.consts.NPCs
import core.game.node.entity.combat.BattleState
import core.game.node.entity.combat.CombatSwingHandler
import core.game.node.entity.npc.AbstractNPC
import core.game.world.map.Location
import core.plugin.Initializable

/**
 * Cockatrice NPC.
 */
@Initializable
class CockatriceNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location) {

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return CockatriceNPC(id, location)
    }

    override fun getSwingHandler(swing: Boolean): CombatSwingHandler {
        return MirrorShieldHandler.SINGLETON
    }

    override fun checkImpact(state: BattleState) {
        super.checkImpact(state)
        MirrorShieldHandler.SINGLETON.checkImpact(state)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.COCKATRICE_1620, NPCs.COCKATRICE_1621)
    }
}
