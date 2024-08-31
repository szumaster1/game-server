package content.global.travel.charter

import core.api.inBorders
import core.game.node.entity.npc.NPC
import core.game.node.entity.npc.NPCBehavior
import core.game.world.map.Location
import core.game.world.map.zone.ZoneBorders

/**
 * Trader crewmember NPC.
 */
class TraderCrewmemberNPC : NPCBehavior(4651,4654) {

    override fun onCreation(self: NPC) {
        if (inBorders(self, PORT_TYRAS_ZONE)) {
            val movementPath = arrayOf(
                Location.create(2141, 3122, 0),
                Location.create(2143, 3122, 0),
                Location.create(2145, 3121, 0),
                Location.create(2146, 3122, 0)
            )
            self.configureMovementPath(*movementPath)
            self.isWalks = true
        } else if(inBorders(self, PORT_KHAZARD)){
            val movementPath = arrayOf(
                Location.create(2675, 3144, 0),
                Location.create(2673, 3146, 0),
                Location.create(2674, 3144, 0),
                Location.create(2674, 3146, 0)
            )
            self.configureMovementPath(*movementPath)
            self.isWalks = true
        } else if(inBorders(self, CATHERBY)){
            val movementPath = arrayOf(
                Location.create(2794, 3414, 0),
                Location.create(2792, 3414, 0),
                Location.create(2793, 3413, 0),
                Location.create(2791, 3414, 0)
            )
            self.configureMovementPath(*movementPath)
            self.isWalks = true
        } else if(inBorders(self, BRIMHAVEN)){
            val movementPath = arrayOf(
                Location.create(2760, 3236, 0),
                Location.create(2760, 3238, 0),
                Location.create(2759, 3239, 0),
                Location.create(2760, 3237, 0)
            )
            self.configureMovementPath(*movementPath)
            self.isWalks = true
        }
    }

    companion object {
        val PORT_TYRAS_ZONE = ZoneBorders(2141, 3121, 2148, 3123)
        val PORT_KHAZARD = ZoneBorders(2673, 3144, 2674, 3148)
        val CATHERBY = ZoneBorders(2791, 3413, 2796, 3414)
        val BRIMHAVEN =ZoneBorders(2759, 3234, 2760, 3239)
    }

}
