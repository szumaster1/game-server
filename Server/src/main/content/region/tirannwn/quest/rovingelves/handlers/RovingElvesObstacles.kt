package content.region.tirannwn.quest.rovingelves.handlers

import content.global.skill.support.agility.AgilityHandler
import core.api.hasRequirement
import core.api.sendMessage
import core.cache.def.impl.SceneryDefinition
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.world.map.Direction
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.plugin.Plugin

/**
 * Roving elves obstacles.
 */
class RovingElvesObstacles : OptionHandler() {

    private val successMessages = mapOf(
        "leaf" to "You safely jump across.",
        "ladder" to "You climb out of the pit.",
        "stick" to "You manage to skillfully pass the trap.",
        "wire" to "You successfully step over the tripwire."
    )

    private val animations = mapOf(
        "over" to Animation(839),
        "through" to Animation(1237),
        "stickTrap" to Animation(819),
        "leafTrap" to Animation(1115),
        "wireTrap" to Animation(1236)
    )

    private val trapLocations = mapOf(
        "stickTrap" to listOf(Location(0, 2, 0), Location(0, -1, 0), Location(2, 0, 0), Location(-1, 0, 0)),
        "wireTrap" to listOf(Location(0, 2, 0), Location(0, -1, 0), Location(2, 0, 0), Location(-1, 0, 0)),
        "forest" to listOf(Location(0, 2, 0), Location(0, -1, 0), Location(2, 0, 0), Location(-1, 0, 0))
    )

    private val LEAF_TRAP_CLIMB = Location(2274, 3172, 0)
    private val illegalJumpsY = listOf(3174)

    private fun nodeCenter(node: Node): Location {
        return if (node.asScenery().rotation % 2 == 0) {
            node.location.transform(1, 0, 0)
        } else {
            node.location.transform(0, 1, 0)
        }
    }

    override fun newInstance(arg: Any?): Plugin<Any> {
        listOf(3922, 3999, 3939, 3921, 3998, 3938, 3937, 3924, 3925, 8742, 3927).forEach { id ->
            SceneryDefinition.forId(id).handlers["option:${getOptionName(id)}"] = this
        }
        return this
    }

    private fun getOptionName(id: Int): String {
        return when (id) {
            3924, 3925 -> "jump"
            3921 -> "step-over"
            3999, 3939, 3938, 3937 -> "enter"
            8742 -> "pass"
            3927 -> "climb"
            else -> "pass"
        }
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        player.lock(5)
        player.faceLocation(node.asScenery().location)
        val isNorthOrSouth = node.id == 3922 && node.asScenery().rotation % 2 == 0 || node.asScenery().rotation % 2 == 0
        val direction = getDirection(player, node)

        when (node.id) {
            8742 -> handleTeleport(player, direction.eastWest, 2)
            3999 -> AgilityHandler.forceWalk(player, -1, player.location, player.location.transform(direction.northSouth, 3), animations["over"], 25, 0.0, null)
            3921 -> handleWireTrap(player, node, direction, isNorthOrSouth)
            3922 -> handleStickTrap(player, node, direction, isNorthOrSouth)
            in listOf(3998, 3939, 3938) -> handleForestTrap(player, node, direction, isNorthOrSouth, animations["through"]!!)
            3937 -> handleForestTrap(player, node, direction, isNorthOrSouth, animations["over"]!!)
            3924 -> handleLeafTrap(player, node, direction, animations["leafTrap"]!!, successMessages["leaf"]!!)
            3925 -> handleLeafTrap(player, node, direction, animations["leafTrap"]!!, successMessages["leaf"]!!, illegalJumpsY.contains(node.location.y))
            3927 -> {
                player.teleport(LEAF_TRAP_CLIMB)
                sendMessage(player, successMessages["ladder"]!!)
            }
        }
        return true
    }

    private fun getDirection(player: Player, node: Node): DirectionPair {
        val northSouth = if (player.location.y <= node.location.y) Direction.NORTH else Direction.SOUTH
        val eastWest = if (player.location.x <= node.location.x) Direction.EAST else Direction.WEST
        return DirectionPair(northSouth, eastWest)
    }

    private fun handleTeleport(player: Player, direction: Direction, distance: Int) {
        if (!hasRequirement(player, "Mourning's End Part I")) return
        player.teleport(player.location.transform(direction, distance))
    }

    /*
     * Handle the wire trap.
     */

    private fun handleWireTrap(player: Player, node: Node, direction: DirectionPair, isNorthOrSouth: Boolean) {
        val (northSouth, eastWest) = direction
        val (trapStart, trapEnd) = if (isNorthOrSouth) {
            if (northSouth == Direction.NORTH) trapLocations["wireTrap"]!![1] to trapLocations["wireTrap"]!![0] else trapLocations["wireTrap"]!![0] to trapLocations["wireTrap"]!![1]
        } else {
            if (eastWest == Direction.EAST) trapLocations["wireTrap"]!![3] to trapLocations["wireTrap"]!![2] else trapLocations["wireTrap"]!![2] to trapLocations["wireTrap"]!![3]
        }
        AgilityHandler.forceWalk(player, -1, node.location.transform(trapStart), node.location.transform(trapEnd), animations["wireTrap"], 100, 0.0, successMessages["wire"]!!)
    }

    /*
     * Handle the stick trap.
     */

    private fun handleStickTrap(player: Player, node: Node, direction: DirectionPair, isNorthOrSouth: Boolean) {
        val (northSouth, eastWest) = direction
        val (trapStart, trapEnd) = if (isNorthOrSouth) {
            if (northSouth == Direction.NORTH) node.location to node.location.transform(trapLocations["stickTrap"]!![0]) else node.location to node.location.transform(
                trapLocations["stickTrap"]!![1]
            )
        } else {
            if (eastWest == Direction.EAST) node.location.transform(trapLocations["stickTrap"]!![3]) to node.location.transform(
                trapLocations["stickTrap"]!![2]
            ) else node.location.transform(trapLocations["stickTrap"]!![2]) to node.location.transform(trapLocations["stickTrap"]!![3])
        }
        AgilityHandler.forceWalk(player, -1, trapStart, trapEnd, animations["stickTrap"], 25, 0.0, successMessages["stick"]!!)
    }

    /*
     * Handle the forest trap.
     */

    private fun handleForestTrap(player: Player, node: Node, direction: DirectionPair, isNorthOrSouth: Boolean, trapType: Animation) {
        val (northSouth, eastWest) = direction
        val (trapStart, trapEnd) = if (isNorthOrSouth) {
            if (northSouth == Direction.NORTH) nodeCenter(node).transform(trapLocations["forest"]!![1]) to nodeCenter(node).transform(trapLocations["forest"]!![0]) else nodeCenter(node).transform(trapLocations["forest"]!![0]) to nodeCenter(node).transform(trapLocations["forest"]!![1])
        } else {
            if (eastWest == Direction.EAST) nodeCenter(node).transform(trapLocations["forest"]!![3]) to nodeCenter(node).transform(trapLocations["forest"]!![2]) else nodeCenter(node).transform(trapLocations["forest"]!![2]) to nodeCenter(node).transform(trapLocations["forest"]!![3])
        }
        AgilityHandler.forceWalk(player, -1, trapStart, trapEnd, trapType, 25, 0.0, null)
    }

    /*
     * Handle the leaf trap.
     */

    private fun handleLeafTrap(player: Player, node: Node, direction: DirectionPair, trapType: Animation, successMessage: String, condition: Boolean = true) {
        if (!condition) return
        val (northSouth, eastWest) = direction
        val (trapStart, trapEnd) = if (northSouth == Direction.NORTH) {
            node.location.transform(northSouth, -1) to node.location.transform(northSouth, 2)
        } else {
            node.location.transform(eastWest, -2) to node.location.transform(eastWest, 2)
        }
        AgilityHandler.forceWalk(player, -1, trapStart, trapEnd, trapType, 25, 0.0, successMessage)
    }

    data class DirectionPair(val northSouth: Direction, val eastWest: Direction)

    override fun getDestination(node: Node, n: Node): Location? {
        return when (node.id) {
            3999 -> Location(2188, 3162)
            3998 -> Location(2188, 3171)
            else -> null
        }
    }

    override fun isWalk(player: Player, node: Node): Boolean {
        return node !is Item
    }

    override fun isWalk(): Boolean {
        return false
    }
}
