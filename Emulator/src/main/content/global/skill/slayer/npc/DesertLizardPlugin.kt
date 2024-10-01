package content.global.skill.slayer.npc

import content.global.skill.slayer.items.IcecoolerHandler
import core.game.node.entity.combat.BattleState
import core.game.node.entity.npc.AbstractNPC
import core.game.world.map.Location
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Represents the Desert lizard plugin.
 */
@Initializable
class DesertLizardPlugin : Plugin<Any?> {

    @Throws(Throwable::class)
    override fun newInstance(arg: Any?): Plugin<Any?> {
        if (arg != null) {
            IcecoolerHandler().newInstance(arg)
        }
        DezertLizardNPC().newInstance(arg)
        return this
    }

    override fun fireEvent(identifier: String, vararg args: Any): Any? {
        return null
    }

    /**
     * Dezert lizard npc.
     */
    inner class DezertLizardNPC : AbstractNPC {
        /**
         * Instantiates a new Dezert lizard npc.
         *
         * @param id       the id
         * @param location the location
         */
        constructor(id: Int, location: Location?) : super(id, location)

        /**
         * Instantiates a new Dezert lizard npc.
         */
        constructor() : super(0, null)

        override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
            return DezertLizardNPC(id, location)
        }

        override fun checkImpact(state: BattleState) {
            super.checkImpact(state)
            var lifepoints = getSkills().lifepoints
            if (state.estimatedHit > -1) {
                lifepoints -= state.estimatedHit
                if (lifepoints < 1) {
                    state.estimatedHit = lifepoints - 1
                }
                if (state.estimatedHit < 0) {
                    state.estimatedHit = 0
                    getSkills().lifepoints = 2
                }
            }
            if (state.secondaryHit > -1) {
                lifepoints -= state.secondaryHit
                if (lifepoints < 1) {
                    state.secondaryHit = lifepoints - 1
                }
                if (state.secondaryHit < 0) {
                    state.secondaryHit = 0
                }
            }
        }

        override fun getIds(): IntArray {
            return IDS
        }
    }

    companion object {
        private val IDS = intArrayOf(2803, 2804, 2805, 2806, 2807, 2808)
    }
}
