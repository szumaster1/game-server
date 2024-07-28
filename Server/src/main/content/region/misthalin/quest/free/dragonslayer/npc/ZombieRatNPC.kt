package content.region.misthalin.quest.free.dragonslayer.npc

import content.region.misthalin.quest.free.dragonslayer.DragonSlayer
import core.game.node.entity.Entity
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.node.item.GroundItemManager
import core.game.node.item.Item
import core.game.world.map.Location
import core.tools.RandomFunction

class ZombieRatNPC : AbstractNPC {

    constructor() : super(0, null)

    private constructor(id: Int, location: Location) : super(id, location)

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return ZombieRatNPC(id, location)
    }

    override fun finalizeDeath(killer: Entity) {
        super.finalizeDeath(killer)
        if (killer is Player) {
            val p = killer
            var quest = p.getQuestRepository().getQuest("Dragon Slayer")
            if (RandomFunction.random(0, 4) == 2) {
                GroundItemManager.create(DragonSlayer.RED_KEY, getLocation(), killer)
            }
            quest = p.getQuestRepository().getQuest("Witch's Potion")
            if (quest.getStage(p) > 0 && quest.getStage(p) < 100) {
                GroundItemManager.create(RAT_TAIL, getLocation(), p)
            }
            GroundItemManager.create(Item(526), getLocation(), p)
        }
    }

    override fun getIds(): IntArray {
        return ID
    }

    companion object {
        private val ID = intArrayOf(6088, 6089, 6090)
        private val RAT_TAIL = Item(300, 1)
    }
}
