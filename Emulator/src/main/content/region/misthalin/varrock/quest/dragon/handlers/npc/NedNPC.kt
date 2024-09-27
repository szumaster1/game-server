package content.region.misthalin.varrock.quest.dragon.handlers.npc

import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.world.map.Location
import org.rs.consts.NPCs
import org.rs.consts.QuestName

/**
 * Represents the Ned NPC.
 */
class NedNPC : AbstractNPC {

    constructor() : super(0, null)

    private constructor(id: Int, location: Location) : super(id, location)

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return NedNPC(id, location)
    }

    override fun isHidden(player: Player): Boolean {
        return player.getQuestRepository().getQuest(QuestName.DRAGON_SLAYER)
            .getStage(player) != 30 && player.getQuestRepository().getQuest(QuestName.DRAGON_SLAYER).getStage(player) != 40
    }

    override fun getIds(): IntArray {
        return ID
    }

    companion object {
        private val ID = intArrayOf(NPCs.NED_918)
    }
}
