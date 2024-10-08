package content.global.skill.slayer.npc

import content.global.skill.slayer.SlayerUtils
import core.game.node.entity.combat.BattleState
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.world.map.Location
import core.plugin.Initializable
import org.rs.consts.NPCs

/**
 * Represents the Kurask NPC.
 */
@Initializable
class KuraskNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location) {

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return KuraskNPC(id, location)
    }

    override fun checkImpact(state: BattleState) {
        super.checkImpact(state)
        var effective = false
        if (state.attacker is Player) {
            val player = state.attacker as Player
            effective = SlayerUtils.hasBroadWeaponEquipped(player, state)
        }
        if (!effective) {
            state.estimatedHit = 0
            if (state.secondaryHit > 0) {
                state.secondaryHit = 0
            }
        }
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.KURASK_1608, NPCs.KURASK_1609, NPCs.KURASK_4229)
    }
}
