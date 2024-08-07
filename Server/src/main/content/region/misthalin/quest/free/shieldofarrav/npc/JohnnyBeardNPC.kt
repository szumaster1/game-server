package content.region.misthalin.quest.free.shieldofarrav.npc

import content.region.misthalin.quest.free.shieldofarrav.ShieldofArrav
import core.api.consts.NPCs
import core.game.node.entity.Entity
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.node.item.GroundItemManager
import core.game.world.map.Location

/**
 * Johnny beard NPC.
 */
class JohnnyBeardNPC : AbstractNPC {

    constructor() : super(0, null)

    private constructor(id: Int, location: Location) : super(id, location)

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return JohnnyBeardNPC(id, location)
    }

    override fun finalizeDeath(killer: Entity) {
        super.finalizeDeath(killer)
        val p = (killer as Player)
        val quest = p.getQuestRepository().getQuest("Shield of Arrav")
        if (quest.getStage(p) == 60 && ShieldofArrav.isPhoenixMission(p) && !p.inventory.containsItem(ShieldofArrav.INTEL_REPORT) && !p.bank.containsItem(ShieldofArrav.INTEL_REPORT)) {
            GroundItemManager.create(ShieldofArrav.INTEL_REPORT, getLocation(), p)
        }
    }

    override fun getIds(): IntArray {
        return ID
    }

    companion object {
        private val ID = intArrayOf(NPCs.JONNY_THE_BEARD_645)
    }
}
