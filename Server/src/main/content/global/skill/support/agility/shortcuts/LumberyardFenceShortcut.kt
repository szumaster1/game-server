package content.global.skill.support.agility.shortcuts

import core.api.*
import core.api.consts.Animations
import core.api.consts.Scenery
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.world.map.Direction
import core.game.world.update.flag.context.Animation

class LumberyardFenceShortcut : InteractionListener {

    private val SQUEEZE_UNDER_ANIM = Animation.create(Animations.ENTER_LUMBER_YARD_9221)
    private val BROKEN_FENCE = Scenery.FENCE_31149

    override fun defineListeners() {
        /**
         * Squeeze-under hole on the yard's west wall.
         */
        on(BROKEN_FENCE, IntType.SCENERY, "squeeze-under") { player, node ->
            lock(player, 1)
            queueScript(player, 0, QueueStrength.SOFT) { stage: Int ->
                when (stage) {
                    0 -> {
                        if (player.location.y != 3498)
                            forceWalk(player, node.location, "Smart")
                        return@queueScript keepRunning(player)
                    }

                    1 -> {
                        forceMove(
                            player,
                            player.location,
                            player.location.transform(
                                if (player.location.x < 3296) Direction.EAST else Direction.WEST, 1
                            ),
                            0,
                            30,
                            null,
                            SQUEEZE_UNDER_ANIM.id
                        )
                        return@queueScript stopExecuting(player)

                    }

                    else -> return@queueScript stopExecuting(player)
                }
            }
            return@on true
        }
    }


}