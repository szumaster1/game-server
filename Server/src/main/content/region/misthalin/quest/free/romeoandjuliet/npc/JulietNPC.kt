package content.region.misthalin.quest.free.romeoandjuliet.npc

import cfg.consts.NPCs
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.world.map.Location
import core.plugin.Initializable

/**
 * Represents the Juliet NPC.
 */
@Initializable
class JulietNPC : AbstractNPC {

    constructor() : super(0, null)

    private constructor(id: Int, location: Location) : super(id, location)

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return JulietNPC(id, location)
    }

    override fun isHidden(player: Player): Boolean {
        return player.getQuestRepository().getQuest("Romeo & Juliet")
            .getStage(player) > 60 && player.getQuestRepository().getQuest("Romeo & Juliet").getStage(player) < 100
    }

    override fun getIds(): IntArray {
        return ID
    }

    companion object {
        private val ID = intArrayOf(NPCs.JULIET_637)
    }
}
