package content.region.kandarin.quest.merlinsquest.npc

import content.region.kandarin.quest.merlinsquest.MerlinUtils
import core.api.consts.NPCs
import core.api.poofClear
import core.api.removeAttribute
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.world.map.Location

/**
 * Morgan le faye NPC.
 */
class MorganLeFayeNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location) {
    var spawnedTicks = 0
    var player: Player? = null
    var finalized = false

    override fun construct(id: Int, location: Location?, vararg objects: Any?): AbstractNPC {
        return MorganLeFayeNPC(id, location)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MORGAN_LE_FAYE_248)
    }

    override fun tick() {
        super.tick()

        if (!finalized) {
            poofClear(this)
            finalized = true

            player ?: return
            removeAttribute(player!!, MerlinUtils.TEMP_ATTR_MORGAN)
        }
    }
}