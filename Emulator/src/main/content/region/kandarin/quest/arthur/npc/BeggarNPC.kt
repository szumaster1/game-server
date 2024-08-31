package content.region.kandarin.quest.arthur.npc

import content.region.kandarin.quest.arthur.MerlinUtils
import cfg.consts.NPCs
import core.api.removeAttribute
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.world.map.Location

/**
 * Represents the Beggar NPC.
 *
 * Related to [Merlin Crystal][content.region.kandarin.quest.arthur.MerlinCrystal] quest.
 * @author lostmyphat
 */
class BeggarNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location) {
    var spawnedTicks = 0
    var player: Player? = null
    var finalized = false

    override fun construct(id: Int, location: Location?, vararg objects: Any?): AbstractNPC {
        return BeggarNPC(id, location)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BEGGAR_252)
    }

    override fun tick() {
        super.tick()

        if (!finalized) {
            if (spawnedTicks++ == 100) {
                clear()

                player ?: return
                removeAttribute(player!!, MerlinUtils.TEMP_ATTR_BEGGAR)
            }
        }
    }
}