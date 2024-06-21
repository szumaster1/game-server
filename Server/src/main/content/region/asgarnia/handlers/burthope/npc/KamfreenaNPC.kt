package content.region.asgarnia.handlers.burthope.npc

import core.api.consts.NPCs
import core.game.node.entity.npc.AbstractNPC
import core.game.world.map.Location
import core.plugin.Initializable
import core.utilities.RandomFunction

@Initializable
class KamfreenaNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location) {

    /*
        Kamfreena can be found at the top floor of the Warriors' Guild.
        If you have more than 200 Warrior Guild Tokens, she will let you
        enter a room filled with Cyclopes, which have a rare chance to
        drop Defenders of various kinds.
     */

    private val forceChat: Array<String> = arrayOf("When you aim for perfection, you discover it's a moving target.", "Patience and persistence can bring down the tallest tree.", "Be master of mind rather than mastered by mind.", "A reflection on a pool of water does not reveal its depth.", "Life isn't fair, that doesn't mean you can't win.")

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return KamfreenaNPC(id, location)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.KAMFREENA_4289)
    }

    override fun tick() {
        if (RandomFunction.random(1, 50) == 25) {
            sendChat(forceChat[RandomFunction.random(forceChat.size)])
        }
        super.tick()
    }

}
