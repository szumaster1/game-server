package content.global.skill.support.slayer.npc

import cfg.consts.NPCs
import core.game.node.entity.Entity
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.world.map.Location
import core.plugin.Initializable

/**
 * Cave bug NPC.
 */
@Initializable
class CaveBugNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location) {

    override fun finalizeDeath(killer: Entity) {
        super.finalizeDeath(killer)
        if (killer is Player) {
            val p = killer.asPlayer()
        }
    }

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return CaveBugNPC(id, location)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.CAVE_BUG_1832, NPCs.CAVE_BUG_LARVA_1833, NPCs.CAVE_BUG_5750)
    }
}
