package content.region.misthalin.handlers

import core.game.global.action.ClimbActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.world.map.Location

class MonasteryListeners : InteractionListener {

    companion object {
        private const val STAIRS = 2641
    }

    override fun defineListeners() {

        /*
         * Entrance leading to the 1st floor of the monastery.
         */

        on(STAIRS, IntType.SCENERY, "climb-up"){ player, node ->
            when (node.id) {
                2641 -> {
                    val option = "climb-up"
                    val abbot = node.location.equals(Location(3057, 3483, 0))
                    if (!player.getSavedData().globalData.isJoinedMonastery()) {
                        player.dialogueInterpreter.open(if (abbot) 801 else 7727, true)
                    } else {
                        ClimbActionHandler.climbLadder(player, node.asScenery(), option)
                    }
                }
            }
            return@on true
        }
    }
}
