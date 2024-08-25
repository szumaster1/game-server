package content.global.skill.support.agility.shortcuts

import cfg.consts.Animations
import cfg.consts.Scenery
import content.global.skill.support.agility.AgilityHandler
import core.api.lock
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.world.map.Direction
import core.game.world.update.flag.context.Animation

/**
 * Represents the Lumberyard fence shortcut.
 */
class LumberyardFenceShortcut : InteractionListener {

    private val SQUEEZE_UNDER_ANIM = Animation.create(Animations.ENTER_LUMBER_YARD_9221)
    private val BROKEN_FENCE = Scenery.FENCE_31149

    override fun defineListeners() {

        on(BROKEN_FENCE, IntType.SCENERY, "squeeze-under") { player, node ->
            lock(player, 1)
            AgilityHandler.forceWalk(
                player,
                0,
                player.location,
                player.location.transform(
                    if (player.location.x < 3296) Direction.EAST else Direction.WEST, 1
                ),
                SQUEEZE_UNDER_ANIM,
                15,
                0.0,
                ""
            )
            return@on true
        }
    }
}


