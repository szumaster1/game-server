package content.region.misc.handlers.tutorial

import core.api.consts.NPCs
import core.game.node.entity.Entity
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.GroundItemManager
import core.game.node.item.Item
import core.game.world.map.Location
import core.plugin.Initializable

/**
 * Represents the Giant rat NPC.
 */
@Initializable
class GiantRatNPC : AbstractNPC {

    constructor() : super(0, null) {
        this.isAggressive = false
    }

    private constructor(id: Int, location: Location) : super(id, location, true)

    override fun init() {
        super.init()
        isAggressive = false
        getSkills().setLevel(Skills.HITPOINTS, 5)
        getSkills().setStaticLevel(Skills.HITPOINTS, 5)
        getSkills().lifepoints = 5
    }

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return GiantRatNPC(id, location)
    }

    override fun tick() {
        super.tick()
    }

    override fun finalizeDeath(killer: Entity) {
        super.finalizeDeath(killer)
        if (killer !is Player) {
            return
        }
        val p = killer
        if (killer is Player) {
            if (p.getQuestRepository().getQuest("Witch's Potion").isStarted(p)) {
                GroundItemManager.create(Item(300), getLocation(), p)
            }
        }
    }

    override fun getIds(): IntArray {
        return ID
    }

    companion object {
        private val ID = intArrayOf(NPCs.GIANT_RAT_86)
    }
}
