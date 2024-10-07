package content.region.misthalin.varrock.quest.dragon.handlers.npc

import core.api.getQuestStage
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
        return getQuestStage(player, QuestName.DRAGON_SLAYER) != 30 && getQuestStage(player, QuestName.DRAGON_SLAYER) != 40
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.NED_918)
    }

}
