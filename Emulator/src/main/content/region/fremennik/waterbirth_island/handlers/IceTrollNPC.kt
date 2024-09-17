package content.region.fremennik.waterbirth_island.handlers

import org.rs.consts.NPCs
import core.api.findLocalNPC
import core.game.node.entity.npc.AbstractNPC
import core.game.world.map.Location
import core.plugin.Initializable

/**
 * Represents the Ice Troll NPC.
 */
@Initializable
class IceTrollNPC : AbstractNPC {

    constructor() : super(NPCs.ICE_TROLL_MALE_5474, null, true)
    private constructor(id: Int, location: Location) : super(id, location)

    override fun construct(id: Int, location: Location, vararg objects: Any?): AbstractNPC {
        return IceTrollNPC(id, location)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ICE_TROLL_MALE_5474, NPCs.ICE_TROLL_RUNT_5473, NPCs.ICE_TROLL_FEMALE_5475)
    }

    override fun tick() {
        val nearbyMiner = findLocalNPC(this, NPCs.MINER_5497) ?: return super.tick()
        if (!inCombat() && nearbyMiner.location.withinDistance(location, 8)) {
            attack(nearbyMiner)
        }
        super.tick()
    }


}