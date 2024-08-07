package content.region.misthalin.quest.free.dragonslayer.npc

import core.api.consts.NPCs
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.world.map.Location

/**
 * Ned NPC.
 */
class NedNPC : AbstractNPC {

    constructor() : super(0, null)

    private constructor(id: Int, location: Location) : super(id, location)

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return NedNPC(id, location)
    }

    override fun isHidden(player: Player): Boolean {
        return player.getQuestRepository().getQuest("Dragon Slayer")
            .getStage(player) != 30 && player.getQuestRepository().getQuest("Dragon Slayer").getStage(player) != 40
    }

    override fun getIds(): IntArray {
        return ID
    }

    companion object {
        private val ID = intArrayOf(NPCs.NED_918)
    }
}
