package content.global.skill.support.agility.shortcuts

import content.global.skill.support.agility.AgilityHandler
import core.api.consts.Animations
import core.api.consts.Scenery
import core.api.queueScript
import core.api.stopExecuting
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.world.map.Direction
import core.game.world.update.flag.context.Animation

/**
 * Represents the Mc grubor fence shortcut interaction.
 */
class McGruborFenceShortcut : InteractionListener {

    override fun defineListeners() {

        /**
         * Shortcut in backyard of Mc grubor area.
         */
        on(Scenery.LOOSE_RAILING_51, IntType.SCENERY, "squeeze-through") { player, _ ->
            queueScript(player, 1, QueueStrength.SOFT) {
                AgilityHandler.forceWalk(
                    player,
                    -1,
                    player.location,
                    player.location.transform(if (player.location.x < 2662) Direction.EAST else Direction.WEST, 1),
                    Animation(Animations.SIDE_STEP_TO_CRAWL_THROUGH_MCGRUBOR_S_WOODS_FENCE_3844),
                    5,
                    0.0,
                    "You squeeze through the loose railing."
                )
                return@queueScript stopExecuting(player)
            }
            return@on true
        }
    }
}
