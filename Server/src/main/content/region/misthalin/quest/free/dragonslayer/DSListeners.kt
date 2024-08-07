package content.region.misthalin.quest.free.dragonslayer

import core.api.consts.NPCs
import core.api.consts.Scenery
import core.api.sendMessage
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.world.map.Location

/**
 * Dragon slayer listeners
 */
class DSListeners : InteractionListener {
    override fun defineListeners() {
        on(Scenery.CELL_DOOR_40184, IntType.SCENERY, "open") { player, _ ->
            sendMessage(player, "It's locked tight.")
            return@on true
        }
        // Talk to Wormbrain
        setDest(IntType.NPC, intArrayOf(NPCs.WORMBRAIN_745), "talk-to") { player, node ->
            val npc = node.asNpc()
            val p = player.asPlayer()
            val dis = player.location.withinMaxnormDistance(npc.location, 1)
            if (dis) {
                return@setDest Location.create(p.location.x, p.location.y, 0)
            } else return@setDest Location.create(npc.location.x, npc.location.y, 0)
        }

    }

}