package content.global.transport.shortcuts

import cfg.consts.Animations
import cfg.consts.Scenery
import content.global.skill.support.agility.AgilityHandler
import core.api.queueScript
import core.api.stopExecuting
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.entity.player.Player
import core.game.world.map.Direction
import core.game.world.update.flag.context.Animation

/**
 * Represents the McGrubor's Wood fence shortcut.
 */
class LooseRailingShortcut : InteractionListener {

    override fun defineListeners() {
        on(Scenery.LOOSE_RAILING_51, IntType.SCENERY, "squeeze-through") { player, _ ->
            handleSqueezeThrough(player)
            return@on true
        }
    }

    private fun handleSqueezeThrough(player: Player) {
        queueScript(player, 1, QueueStrength.SOFT) {
            val newDirection = when {
                player.location.x < 2662 -> Direction.EAST
                player.location.x >= 2662 -> Direction.WEST
                else -> Direction.NORTH
            }
            AgilityHandler.forceWalk(
                player,
                -1,
                player.location,
                player.location.transform(newDirection, 1),
                Animation(Animations.SIDE_STEP_TO_CRAWL_THROUGH_MCGRUBOR_S_WOODS_FENCE_3844),
                5,
                0.0,
                "You squeeze through the loose railing."
            )
            return@queueScript stopExecuting(player)
        }
    }
}
