package content.region.misthalin.quest.free.dragonslayer.npc

import content.region.misthalin.quest.free.dragonslayer.DragonSlayer
import core.game.node.entity.Entity
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.node.item.GroundItemManager
import core.game.world.map.Location

/**
 * Maze demon NPC.
 */
class MazeDemonNPC : AbstractNPC {

    constructor() : super(0, null)

    private constructor(id: Int, location: Location) : super(id, location)

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return MazeDemonNPC(id, location)
    }

    override fun finalizeDeath(killer: Entity) {
        super.finalizeDeath(killer)
        if (killer.location.withinDistance(LOCATION)) {
            if (killer is Player) {
                GroundItemManager.create(DragonSlayer.GREEN_KEY, getLocation(), killer)
            }
        }
    }

    override fun getIds(): IntArray {
        return ID
    }

    companion object {
        private val ID = intArrayOf(82)

        private val LOCATION: Location = Location.create(2936, 9652, 0)
    }
}
