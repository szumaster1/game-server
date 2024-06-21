package content.region.misthalin.handlers.barbvillage

import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.npc.AbstractNPC
import core.game.world.map.Location
import core.plugin.Initializable
import core.utilities.RandomFunction

@Initializable
class BarbarianNPC : AbstractNPC {
    constructor() : super(0, null, true)
    private constructor(id: Int, location: Location) : super(id, location, true)

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return BarbarianNPC(id, location)
    }

    override fun onImpact(entity: Entity, state: BattleState) {
        if (RandomFunction.random(8) == 1) {
            sendChat("YEEEEEEEEAARRRGGGGHHHHHHHH")
        }
        super.onImpact(entity, state)
    }

    override fun getIds(): IntArray {
        return ID
    }

    companion object {
        private val ID = intArrayOf(
            12,
            3246,
            3247,
            3248,
            3249,
            3250,
            3251,
            3252,
            3253,
            3255,
            3256,
            3257,
            3258,
            3259,
            3260,
            3261,
            3262,
            3263,
            5909
        )
    }
}
