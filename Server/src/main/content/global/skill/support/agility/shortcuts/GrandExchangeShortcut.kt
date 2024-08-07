package content.global.skill.support.agility.shortcuts

import core.api.*
import core.api.consts.Animations
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.impl.ForceMovement
import core.game.node.entity.impl.ForceMovement.direction
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.scenery.Scenery
import core.game.system.task.Pulse
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation

/**
 * Grand exchange shortcut.
 */
class GrandExchangeShortcut : InteractionListener {

    companion object {
        val SHORTCUTS = mapOf(
            9311 to listOf(
                Location.create(3138, 3516, 0),
                Location.create(3143, 3514, 0),
                Location.create(3144, 3514, 0),
            ),
            9312 to listOf(
                Location.create(3144, 3514, 0),
                Location.create(3139, 3516, 0),
                Location.create(3138, 3516, 0),
            )
        )
        private val CLIMB_DOWN = Animation.create(Animations.CRAWL_UNDER_WALL_A_2589)
        private val CRAWL_THROUGH = Animation.create(Animations.CRAWL_UNDER_WALL_B_2590)
        private val CLIMB_UP = Animation.create(Animations.CRAWL_UNDER_WALL_C_2591)
    }

    override fun defineListeners() {
        on(SHORTCUTS.keys.toIntArray(), IntType.SCENERY, "climb-into") { player, node ->
            player.locks.lockComponent(4)
            if (!hasLevelDyn(player, Skills.AGILITY, 21)) {
                sendMessage(player, "You need an agility level of at least 21 to do this.")
                return@on true
            }
            lock(player, 4)
            val o = node as Scenery
            val path = SHORTCUTS[o.id]!!
            ForceMovement.run(player, path[0], o.location, ForceMovement.WALK_ANIMATION, CLIMB_DOWN, direction(path[0], o.location), ForceMovement.WALKING_SPEED, ForceMovement.WALKING_SPEED, false)
            runCrawlPulse(player, path)
            return@on true
        }
    }

    private fun runCrawlPulse(player: Player, path: List<Location>) {
        submitIndividualPulse(player, object : Pulse(1, player) {
            var count = 0
            var reachedStart = false
            override fun pulse(): Boolean {
                // If the player hasn't reached path[0], don't do anything
                if (!reachedStart && player.location != path[0]) {
                    return false
                }
                reachedStart = true

                when (++count) {
                    2 -> {
                        teleport(player, path[1])
                        visualize(player, CRAWL_THROUGH, -1)
                    }

                    3 -> {
                        ForceMovement.run(player, path[1], path[2], CLIMB_UP)
                        unlock(player)
                        return true
                    }
                }
                return false
            }
        })
    }

}
