package content.region.asgarnia.handlers.burthope.npc

import core.api.consts.NPCs
import core.game.node.entity.npc.AbstractNPC
import core.game.world.map.Location
import core.plugin.Initializable
import core.tools.RandomFunction

@Initializable
class ShanomiNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location) {

    /*
        A warrior mage from the east. Shanomi runs a minigame
        in the Warriors' Guild in which the player must defeat
        animated suits of armour to win warrior guild tokens.
     */

    private val forceChat: Array<String> = arrayOf("Those things which cannot be seen, perceive them.", "Do nothing which is of no use.", "Think not dishonestly.", "The Way in training is.", "Gain and loss between you must distinguish.", "Trifles pay attention even to.", "Way of the warrior this is.", "Acquainted with every art become.", "Ways of all professions know you.", "Judgment and understanding for everything develop you must.")


    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return ShanomiNPC(id, location)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SHANOMI_4290)
    }

    override fun tick() {
        if (RandomFunction.random(1, 50) == 25) {
            sendChat(forceChat[RandomFunction.random(forceChat.size)])
        }
        super.tick()
    }

}
