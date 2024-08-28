package content.region.asgarnia.burthope

import cfg.consts.NPCs
import core.game.node.entity.npc.AbstractNPC
import core.game.world.map.Location
import core.plugin.Initializable
import core.tools.RandomFunction

/**
 * Represents the Anton NPC.
 */
@Initializable
class AntonNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location) {

    /*
     * Anton is a smith located on the 1st floor of the Warriors' Guild in Burthorpe.
     * He runs the Warriors' Guild Armoury, selling various melee-based weaponry and armour.
     */

    private val forceChat: Array<String> = arrayOf("Armour and axes to suit your needs.", "Imported weapons from the finest smithys around the lands!", "Ow, my toe! That armour is heavy.")


    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return content.region.asgarnia.burthope.AntonNPC(id, location)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ANTON_4295)
    }

    override fun tick() {
        if (RandomFunction.random(1, 50) == 25) {
            sendChat(forceChat[RandomFunction.random(forceChat.size)])
        }
        super.tick()
    }

}
