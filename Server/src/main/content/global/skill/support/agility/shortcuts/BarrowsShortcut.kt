package content.global.skill.support.agility.shortcuts

import content.global.skill.support.agility.AgilityHandler
import core.api.consts.Animations
import core.api.consts.Scenery
import core.api.hasRequirement
import core.api.queueScript
import core.api.sendDialogue
import core.api.stopExecuting
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.world.map.Direction
import core.game.world.update.flag.context.Animation

class BarrowsShortcut : InteractionListener {

    override fun defineListeners() {

        on(Scenery.BROKEN_FENCE_18411, IntType.SCENERY, "climb-over") { player, _ ->

            if (!hasRequirement(player, "In Aid of the Myreque")) {
                sendDialogue(player, "Um... those vampyres don't look very nice. I'm not going through here.")
                return@on true
            }

            queueScript(player, 1, QueueStrength.SOFT) {
                AgilityHandler.forceWalk(
                    player, -1, player.location, player.location.transform(
                        if (player.location.y < 3264) Direction.NORTH else Direction.SOUTH, 1
                    ), Animation(Animations.WALKING_OVER_STILE_OBSTACLE_10980), 10, 0.0, null
                )
                return@queueScript stopExecuting(player)

            }
            return@on true
        }
    }

}
