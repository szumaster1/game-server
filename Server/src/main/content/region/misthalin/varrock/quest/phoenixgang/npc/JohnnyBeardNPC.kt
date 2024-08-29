package content.region.misthalin.varrock.quest.phoenixgang.npc

import content.region.misthalin.varrock.quest.phoenixgang.ShieldofArrav
import cfg.consts.NPCs
import core.game.node.entity.Entity
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.node.item.GroundItemManager
import core.game.world.map.Location

/**
 * Represents the Johnny Beard NPC.
 */
class JohnnyBeardNPC : AbstractNPC {

    constructor() : super(0, null)

    private constructor(id: Int, location: Location) : super(id, location)

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return JohnnyBeardNPC(id, location)
    }

    override fun finalizeDeath(killer: Entity) {
        super.finalizeDeath(killer)
        if(killer is Player) {
            val player = killer.asPlayer()
            val quest = player.getQuestRepository().getQuest("Shield of Arrav")
            if (quest.getStage(player) == 60 && ShieldofArrav.isPhoenixMission(player) && !player.inventory.containsItem(
                    ShieldofArrav.INTEL_REPORT) && !player.bank.containsItem(ShieldofArrav.INTEL_REPORT)) {
                GroundItemManager.create(ShieldofArrav.INTEL_REPORT, getLocation(), player)
            }
        }
    }

    override fun getIds(): IntArray {
        return ID
    }

    companion object {
        private val ID = intArrayOf(NPCs.JONNY_THE_BEARD_645)
    }
}
