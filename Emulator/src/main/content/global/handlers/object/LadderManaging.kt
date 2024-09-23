package content.global.handlers.`object`

import core.api.getUsedOption
import core.game.global.action.ClimbActionHandler.climbLadder
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.scenery.Scenery

class LadderManaging : InteractionListener {

    override fun defineListeners() {

        on(IntType.SCENERY, "climb-up", "climb-down", "climb") { player, node ->
            val option = getUsedOption(player)
            climbLadder(player, node as Scenery, option)
            return@on true
        }

    }

}