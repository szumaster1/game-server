package content.global.skill.support.agility.shortcuts

import core.api.*
import cfg.consts.Animations
import cfg.consts.Scenery
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.entity.skill.Skills
import core.game.world.update.flag.context.Animation

/**
 * Represents the Rock passage shortcut interaction.
 */
class RockPassageShortcut : InteractionListener {

    override fun defineListeners() {
        on(Scenery.ROCK_PASSAGE_29099, IntType.SCENERY, "squeeze-through") { player, _ ->
            if (!hasLevelDyn(player, Skills.AGILITY, 29)) {
                sendMessage(player, "You need an agility level of at least 29 to do this.")
            } else {
                lock(player, 3)
                animate(player, Animation(Animations.GO_INTO_OBSTACLE_PIPE_4855))
                queueScript(player, 2, QueueStrength.SOFT) {
                    when (player.location.y) {
                        2871 -> teleport(player, location(2596, 2869, 0))
                        2869 -> teleport(player, location(2596, 2871, 0))
                    }
                    return@queueScript stopExecuting(player)
                }
            }
            return@on true
        }
    }
}
