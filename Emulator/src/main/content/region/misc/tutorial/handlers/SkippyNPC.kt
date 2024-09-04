package content.region.misc.tutorial.handlers

import cfg.consts.NPCs
import core.game.node.entity.npc.AbstractNPC
import core.game.world.map.Location
import core.plugin.Initializable
import core.tools.RandomFunction

/**
 * Represents the Skippy NPC.
 */
@Initializable
class SkippyNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location) {

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return SkippyNPC(id, location)
    }

    override fun tick() {
        if (RandomFunction.random(100) < 15) {
            sendChat("You can skip the tutorial by talking to me!")
        }
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SKIPPY_2796)
    }
}
