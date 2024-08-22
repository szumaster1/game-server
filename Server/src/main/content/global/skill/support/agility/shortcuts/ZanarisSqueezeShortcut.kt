package content.global.skill.support.agility.shortcuts

import content.global.skill.support.agility.AgilityHandler
import core.api.*
import cfg.consts.Scenery
import cfg.consts.Sounds
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.Node
import core.game.node.entity.combat.ImpactHandler
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Direction
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.tools.RandomUtils

/**
 * Represents the Zanaris squeeze shortcut interaction.
 */
class ZanarisSqueezeShortcut : InteractionListener {

    override fun defineListeners() {
        on(Scenery.JUTTING_WALL_12127, IntType.SCENERY, "squeeze-past") { player, node ->
            handleSqueezeAttempt(player, node.asScenery())
            return@on true
        }
    }

    /**
     * Handles the player's attempt to squeeze past an obstacle.
     *
     * @param player The player attempting the squeeze.
     * @param node The scenery node representing the obstacle.
     */
    private fun handleSqueezeAttempt(player: Player, node: Node) {
        val scenery = node.asScenery()
        val direction = Direction.getLogicalDirection(player.location, scenery.location)
        val end = player.location.transform(direction.stepX * 2, direction.stepY * 2, 0)

        sendMessage(player, "You try to squeeze past.")

        if (!isAgilityLevelSufficient(player, scenery.location)) return

        playAudio(player, Sounds.SQUEEZE_THROUGH_ROCKS_1310, 1, 2)

        if (AgilityHandler.hasFailed(player, 10, 0.00300)) {
            handleFailure(player, direction, scenery.rotation, end)
        } else {
            AgilityHandler.walk(player, -1, player.location, end, Animation.create(157 - calculateRotation(scenery.rotation, direction)), 0.0, null)
        }
    }

    /**
     * Checks if the player's agility level is sufficient for the obstacle.
     *
     * @param player The player whose agility level is being checked.
     * @param location The location of the obstacle.
     * @return True if the player's agility level is sufficient, false otherwise.
     */
    private fun isAgilityLevelSufficient(player: Player, location: Location): Boolean {
        val agilityLevel = getStatLevel(player, Skills.AGILITY)
        val requiredLevel = when (location) {
            Location(2400, 4403) -> 46
            Location(2415, 4402), Location(2408, 4395) -> 66
            else -> return true
        }
        return if (agilityLevel < requiredLevel) {
            sendMessageWithDelay(player, "You need an agility level of $requiredLevel to negotiate this obstacle.", 1)
            false
        } else true
    }

    /**
     * Calculates the rotation needed for the animation based on scenery rotation and direction.
     *
     * @param sceneryRotation The rotation of the scenery.
     * @param direction The direction of the player's movement.
     * @return The calculated rotation for the animation.
     */
    private fun calculateRotation(sceneryRotation: Int, direction: Direction): Int {
        return when {
            sceneryRotation == 3 && direction == Direction.SOUTH -> 1
            sceneryRotation == 3 && direction == Direction.NORTH -> 0
            direction == Direction.NORTH || (direction == Direction.SOUTH && sceneryRotation != 0) -> 1
            else -> 0
        }
    }

    /**
     * Handles the failure scenario when the player fails to squeeze past the obstacle.
     *
     * @param player The player who failed the squeeze attempt.
     * @param direction The direction of the squeeze.
     * @param sceneryRotation The rotation of the scenery.
     * @param end The end location after the squeeze attempt.
     */
    private fun handleFailure(player: Player, direction: Direction, sceneryRotation: Int, end: Location) {
        val forceChat = arrayOf("Arrgghhhh!", "Owww...", "Ahhh...")
        var repeat = 3
        var rand = RandomUtils.random(4)
        AgilityHandler.walk(player, -1, player.location, end, Animation.create(157 - calculateRotation(sceneryRotation, direction)), 0.0, null)
        lock(player, 4)
        GameWorld.Pulser.submit(object : Pulse(repeat, player) {
            override fun pulse(): Boolean {
                runTask(player, repeat-3, repeat) { sendChat(player, forceChat[--repeat]); player.impactHandler.manualHit(player, rand, ImpactHandler.HitsplatType.NORMAL) }
                AgilityHandler.fail(player, 1, end, Animation.create(761 - calculateRotation(sceneryRotation, direction)), -1, null)
                return true
            }
        })
    }

}