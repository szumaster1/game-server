package core.game.global.action

import core.api.*
import core.game.dialogue.Dialogue
import core.game.global.action.SpecialLadders.Companion.getDestination
import core.game.global.action.SpecialLadders.Companion.getSpecialLadder
import core.game.interaction.QueueStrength
import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.Direction
import core.game.world.map.Location
import core.game.world.map.RegionManager.getObject
import core.game.world.map.RegionManager.isTeleportPermitted
import core.game.world.update.flag.context.Animation

/**
 * Handles a ladder climbing reward.
 *
 * @author Emperor
 */
object ClimbActionHandler {
    /**
     * Represents the climb up animation of ladders.
     */
    @JvmField
    val CLIMB_UP: Animation = Animation(828)

    /**
     * Represents the climb down animation of ladders.
     */
    @JvmField
    val CLIMB_DOWN: Animation = Animation(827)

    /**
     * The climb dialogue.
     */
    var CLIMB_DIALOGUE: Dialogue = ClimbDialogue()

    /**
     * Handles the climbing of a rope.
     *
     * @param player The player.
     * @param object The rope object.
     * @param option The option.
     */
    fun climbRope(player: Player?, `object`: Scenery?, option: String?) {
    }

    /**
     * Handles the climbing of a trap door.
     *
     * @param player The player.
     * @param object The trap door object.
     * @param option The option.
     */
    fun climbTrapdoor(player: Player?, `object`: Scenery?, option: String?) {
    }

    /**
     * Handles the climbing of a ladder.
     *
     * @param player      The player.
     * @param startLadder The scenery.
     * @param option      The option.
     * @return True if successfully climbed
     */
    @JvmStatic
    fun climbLadder(player: Player, startLadder: Scenery?, option: String?): Boolean {
        var endLadder: Scenery? = null
        var animation: Animation? = CLIMB_UP
        if (startLadder!!.name.startsWith("Stair")) {
            animation = null
        }
        if (getDestination(startLadder.location) != null) {
            val destination = getDestination(startLadder.location)
            climb(player, animation, destination)
            if (getSpecialLadder(startLadder.location) != null) {
                getSpecialLadder(startLadder.location)!!.checkAchievement(player)
            }
            return true
        }
        when (option) {
            "climb-up", "walk-up" -> endLadder = getLadder(startLadder, false)
            "climb-down", "walk-down" -> {
                if (startLadder.name == "Trapdoor") {
                    animation = CLIMB_DOWN
                }
                endLadder = getLadder(startLadder, true)
            }

            "climb" -> {
                val upperLadder = getLadder(startLadder, false)
                val downLadder = getLadder(startLadder, true)
                if (upperLadder == null && downLadder != null) {
                    return climbLadder(player, startLadder, "climb-down")
                }
                if (upperLadder != null && downLadder == null) {
                    return climbLadder(player, startLadder, "climb-up")
                }
                val dialogue = CLIMB_DIALOGUE.newInstance(player)
                if (dialogue != null && dialogue.open(startLadder)) {
                    player.dialogueInterpreter.dialogue = dialogue
                }
                return false
            }
        }
        val destination = if (endLadder != null) getDestination(endLadder) else null
        if (endLadder == null || destination == null) {
            player.packetDispatch.sendMessage("The ladder doesn't seem to lead anywhere.")
            return false
        }
        climb(player, animation, destination)
        return true
    }

    /**
     * Gets the teleport destination.
     *
     * @param scenery The object to teleport to.
     * @return The teleport destination.
     */
    @JvmStatic
    fun getDestination(scenery: Scenery): Location? {
        var sizeX = scenery.definition.sizeX
        var sizeY = scenery.definition.sizeY
        if (scenery.rotation % 2 != 0) {
            val switcher = sizeX
            sizeX = sizeY
            sizeY = switcher
        }
        val dir = Direction.forWalkFlag(scenery.definition.walkingFlag, scenery.rotation)
        if (dir != null) {
            return getDestination(scenery, sizeX, sizeY, dir, 0)
        }
        when (scenery.rotation) {
            0 -> return getDestination(scenery, sizeX, sizeY, Direction.SOUTH, 0)
            1 -> return getDestination(scenery, sizeX, sizeY, Direction.EAST, 0)
            2 -> return getDestination(scenery, sizeX, sizeY, Direction.NORTH, 0)
            3 -> return getDestination(scenery, sizeX, sizeY, Direction.WEST, 0)
        }
        return null
    }

    /**
     * Gets the destination for the given object.
     *
     * @param scenery The object.
     * @param dir    The preferred direction from the object.
     * @return The teleporting destination.
     */
    private fun getDestination(scenery: Scenery, sizeX: Int, sizeY: Int, dir: Direction, count: Int): Location? {
        val loc = scenery.location
        if (dir.toInteger() % 2 != 0) {
            var x = dir.stepX
            if (x > 0) {
                x *= sizeX
            }
            for (y in 0 until sizeY) {
                val l = loc.transform(x, y, 0)
                if (isTeleportPermitted(l) && dir.canMove(l)) {
                    return l
                }
            }
        } else {
            var y = dir.stepY
            if (y > 0) {
                y *= sizeY
            }
            for (x in 0 until sizeX) {
                val l = loc.transform(x, y, 0)
                if (isTeleportPermitted(l) && dir.canMove(l)) {
                    return l
                }
            }
        }
        if (count == 3) {
            return null
        }
        return getDestination(scenery, sizeX, sizeY, Direction.get((dir.toInteger() + 1) % 4), count + 1)
    }

    /**
     * Executes the climbing reward.
     *
     * @param player      The player.
     * @param animation   The climbing animation.
     * @param destination The destination.
     * @param messages    the messages
     */
    @JvmStatic
    fun climb(player: Player, animation: Animation?, destination: Location?, vararg messages: String?) {
        lock(player, 2)
        player.animate(animation)
        queueScript(player, 1, QueueStrength.SOFT) {
            player.properties.teleportLocation = destination
            for (message in messages) {
                sendMessage(player, message.toString())
            }
            return@queueScript stopExecuting(player)
        }
    }

    /**
     * Gets the ladder the object leads to.
     *
     * @param scenery The ladder object.
     * @param down   If the player is going down a floor.
     * @return The ladder the current ladder object leads to.
     */
    private fun getLadder(scenery: Scenery?, down: Boolean): Scenery? {
        val mod = if (down) -1 else 1
        var ladder = getObject(scenery!!.location.transform(0, 0, mod))
        if (ladder == null || !isLadder(ladder)) {
            if (ladder != null && ladder.name == scenery.name) {
                ladder = getObject(ladder.location.transform(0, 0, mod))
                if (ladder != null) {
                    return ladder
                }
            }
            ladder = findLadder(scenery.location.transform(0, 0, mod))
            if (ladder == null) {
                ladder = getObject(scenery.location.transform(0, mod * -6400, 0))
                if (ladder == null) {
                    ladder = findLadder(scenery.location.transform(0, mod * -6400, 0))
                }
            }
        }
        return ladder
    }

    /**
     * Finds a ladder (by searching a 10x10 area around the given location).
     *
     * @param l The location.
     * @return The ladder.
     */
    private fun findLadder(l: Location): Scenery? {
        for (x in -5..5) {
            for (y in -5..5) {
                val scenery = getObject(l.transform(x, y, 0))
                if (scenery != null && isLadder(scenery)) {
                    return scenery
                }
            }
        }
        return null
    }

    /**
     * Checks if the object is a ladder.
     *
     * @param object The object.
     * @return `True` if so.
     */
    private fun isLadder(`object`: Scenery): Boolean {
        for (option in `object`.definition.options) {
            if (option != null && (option.contains("Climb"))) {
                return true
            }
        }
        return `object`.name == "Trapdoor"
    }

    /**
     * Represents the dialogue plugin used for climbing stairs or a ladder.
     *
     * @author 'Vexia
     * @version 1.0
     */
    internal class ClimbDialogue : Dialogue {
        /**
         * Constructs a new `ClimbDialogue` `Object`.
         */
        constructor()

        /**
         * Constructs a new `ClimbDialogue` `Object`.
         *
         * @param player the player.
         */
        constructor(player: Player?) : super(player)

        override fun newInstance(player: Player): Dialogue {
            return ClimbDialogue(player)
        }

        /**
         * Represents the object to use.
         */
        private var `object`: Scenery? = null

        override fun open(vararg args: Any): Boolean {
            `object` = args[0] as Scenery
            interpreter.sendOptions("What would you like to do?", "Climb Up.", "Climb Down.")
            stage = 0
            return true
        }

        override fun handle(interfaceId: Int, buttonId: Int): Boolean {
            when (stage) {
                0 -> when (buttonId) {
                    1 -> {
                        player.lock(1)
                        Pulser.submit(object : Pulse(1) {
                            override fun pulse(): Boolean {
                                climbLadder(player, `object`, "climb-up")
                                return true
                            }
                        })
                        end()
                    }

                    2 -> {
                        player.lock(1)
                        Pulser.submit(object : Pulse(1) {
                            override fun pulse(): Boolean {
                                climbLadder(player, `object`, "climb-down")
                                return true
                            }
                        })
                        end()
                    }
                }
            }
            return true
        }

        override fun getIds(): IntArray {
            return intArrayOf(ID)
        }

        companion object {
            /**
             * Represents the climbing dialogue id.
             */
            const val ID: Int = 8 shl 16
        }
    }
}
