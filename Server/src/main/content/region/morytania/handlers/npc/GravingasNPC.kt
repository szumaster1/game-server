package content.region.morytania.handlers.npc

import core.api.consts.NPCs
import core.game.node.entity.npc.AbstractNPC
import core.game.world.map.Location
import core.utilities.RandomFunction

class GravingasNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location) {

    private val forceChat = arrayOf(
        "Down with Necrovaus!!",
        "Rise up my fellow ghosts, and we shall be victorious!",
        "Power to the Ghosts!!",
        "Rise together, Ghosts without a cause!!",
        "United we conquer - divided we fall!!",
        "We shall overcome!!",
        "Let Necrovarus know we want out!!",
        "Don't stay silent - victory in numbers!!"
    )

    override fun handleTickActions() {
        super.handleTickActions()
        if (RandomFunction.random(45) == 5) {
            sendChat(forceChat[RandomFunction.random(forceChat.size)])
        }
    }

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return GravingasNPC(id, location)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GRAVINGAS_1685)
    }

}
