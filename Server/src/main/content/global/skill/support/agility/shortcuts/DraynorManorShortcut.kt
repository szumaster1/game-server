package content.global.skill.support.agility.shortcuts

import content.global.skill.support.agility.AgilityHandler
import core.api.consts.Animations
import core.api.consts.Scenery
import core.api.hasLevelDyn
import core.api.queueScript
import core.api.sendDialogue
import core.api.stopExecuting
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.entity.skill.Skills
import core.game.world.map.Direction
import core.game.world.update.flag.context.Animation

/**
 * Draynor manor shortcut.
 */
class DraynorManorShortcut : InteractionListener {

    override fun defineListeners() {

        /**
         * Squeeze through fence.
         */
        on(Scenery.BROKEN_RAILING_37703, IntType.SCENERY, "squeeze-through") { player, _ ->
            if (!hasLevelDyn(player, Skills.AGILITY, 28)) {
                sendDialogue(player, "You need an agility level of at least 28 to do this.")
                return@on true
            }
            queueScript(player, 1, QueueStrength.SOFT) {
                AgilityHandler.forceWalk(
                    player,
                    -1,
                    player.location,
                    player.location.transform(if (player.location.x >= 3086) Direction.WEST else Direction.EAST, 1),
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
