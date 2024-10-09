package content.global.skill.summoning

import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.game.world.map.Location
import org.rs.consts.NPCs

class SummoningListener : InteractionListener {

    companion object {
        val PikkupstixA = NPC(NPCs.PIKKUPSTIX_6971, Location.create(2926, 3443, 0))
        val Summoning_2 = NPC(NPCs.PIKKUPSTIX_6970, Location.create(2926, 3443, 0))
    }

    override fun defineListeners() {
        // {2926,3443,0,0,5}
    }
}