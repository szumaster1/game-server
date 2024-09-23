package content.region.misthalin.edgeville.monastery

import org.rs.consts.NPCs
import org.rs.consts.Scenery
import core.game.global.action.ClimbActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.world.map.Location

/**
 * Represents the Monastery listeners.
 */
class Monastery : InteractionListener {

    companion object {
        private const val LADDER = Scenery.LADDER_2641
    }

    override fun defineListeners() {

        /*
         * Entrance leading to the 1st floor of the monastery.
         */

        on(LADDER, IntType.SCENERY, "climb-up"){ player, node ->
            when (node.id) {
                LADDER -> {
                    val option = "climb-up"
                    val abbot = node.location.equals(Location(3057, 3483, 0))
                    if (!player.getSavedData().globalData.isJoinedMonastery()) {
                        player.dialogueInterpreter.open(if (abbot) NPCs.ABBOT_LANGLEY_801 else NPCs.MONK_7727, true)
                    } else {
                        ClimbActionHandler.climbLadder(player, node.asScenery(), option)
                    }
                }
            }
            return@on true
        }
    }
}
