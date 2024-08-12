package content.global.skill.support.slayer

import core.api.*
import core.api.consts.Components
import core.api.consts.Graphics
import core.api.consts.Scenery
import core.api.consts.Sounds
import core.game.global.action.ClimbActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.TeleportManager
import core.game.world.map.Location
import core.game.world.update.flag.context.Graphic

/**
 * Slayer listeners.
 */
class SlayerListeners : InteractionListener {

    companion object {
        val NULL = -1 // Constant representing a null value
        private const val FADE_START = Components.FADE_TO_BLACK_115 // Component for fade-in effect
        private const val FADE_END = Components.FADE_FROM_BLACK_170 // Component for fade-out effect
        private const val TRAPDOOR = Scenery.TRAPDOOR_8783 // Scenery constant for trapdoor
        private const val LADDER = Scenery.LADDER_8785 // Scenery constant for ladder
        private const val STAIRS = Scenery.STAIRS_96 // Scenery constant for stairs
        private const val STAIRS_2 = Scenery.STAIRS_35121 // Scenery constant for a second set of stairs
        private const val CAVE_ENTRANCE = Scenery.CAVE_ENTRANCE_15767 // Scenery constant for cave entrance
        private val CAVE_EXIT = intArrayOf(Scenery.CAVE_15811, Scenery.CAVE_15812, Scenery.CAVE_23157, Scenery.CAVE_23158) // Array of cave exit scenery constants
        private val SVENS_DIG_LOCATIONS = arrayOf(
            // Array of locations for digging
            Location(2749, 3733, 0),
            Location(2748, 3733, 0),
            Location(2747, 3733, 0),
            Location(2747, 3734, 0),
            Location(2747, 3735, 0),
            Location(2747, 3736, 0),
            Location(2748, 3736, 0),
            Location(2749, 3736, 0)
        )
    }

    override fun defineDestinationOverrides() {
        // Override destination for specific scenery types
        setDest(IntType.SCENERY, intArrayOf(Scenery.CAVE_23157, Scenery.CAVE_23158), "exit") { _, _ ->
            return@setDest Location(2690, 10124, 0) // Set exit location
        }
        setDest(IntType.SCENERY, intArrayOf(STAIRS), "climb-up") { _, _ ->
            return@setDest Location(2641, 9763, 0) // Set climb-up location for stairs
        }
    }

    private fun enterCavern(player: Player) {
        // Queue a script for the player to enter the cavern
        queueScript(player, 1, QueueStrength.SOFT) { stage: Int ->
            when (stage) {
                0 -> {
                    sendMessage(player, "You dig a hole...") // Notify player they are digging
                    openOverlay(player, FADE_START) // Start fade-in effect
                    return@queueScript delayScript(player, 3) // Delay for 3 seconds
                }

                1 -> {
                    closeOverlay(player) // Close the overlay
                    openInterface(player, FADE_END) // Start fade-out effect
                    teleport(player, Location(2697, 10119, 0), TeleportManager.TeleportType.INSTANT) // Teleport player to cavern
                    return@queueScript keepRunning(player) // Continue the script
                }

                2 -> {
                    playAudio(player, Sounds.STUNNED_2727) // Play stunned sound effect
                    visualize(player, NULL, Graphic(Graphics.STUN_BIRDIES_ABOVE_HEAD_80, 96)) // Visual effect for player
                    sendMessage(player, "...And fall into a dark and slimy pit!") // Notify player of falling
                    return@queueScript stopExecuting(player) // Stop the script
                }

                else -> return@queueScript stopExecuting(player) // Stop the script for any other stage
            }
        }
    }

    override fun defineListeners() {
        // Define listeners for digging locations
        for (location in SVENS_DIG_LOCATIONS) {
            onDig(location) { player: Player ->
                enterCavern(player) // Call enterCavern when player digs
            }
        }

        // Listener for trapdoor interaction
        on(TRAPDOOR, IntType.SCENERY, "open") { player, _ ->
            teleport(player, Location(2044, 4649, 0), TeleportManager.TeleportType.INSTANT) // Teleport player through trapdoor
            return@on true // Indicate successful interaction
        }

        // Listener for ladder interaction
        on(LADDER, IntType.SCENERY, "climb-up") { player, _ ->
            teleport(player, Location(2543, 3327, 0), TeleportManager.TeleportType.INSTANT) // Teleport player up the ladder
            return@on true // Indicate successful interaction
        }

        // Listener for stairs interaction
        on(STAIRS, IntType.SCENERY, "climb-up") { player, _ ->
            ClimbActionHandler.climb(player, null, Location(2649, 9804, 0)) // Handle climbing action for stairs
            return@on true // Indicate successful interaction
        }

        // Listener for second stairs interaction
        on(STAIRS_2, IntType.SCENERY, "climb-up") { player, _ ->
            ClimbActionHandler.climb(player, null, Location(2641, 9763, 0)) // Handle climbing action for second stairs
            return@on true // Indicate successful interaction
        }

        // Listener for cave entrance interaction
        on(CAVE_ENTRANCE, IntType.SCENERY, "enter") { player, _ ->
            if (!hasRequirement(player, "Cabin Fever")) return@on true // Check if player meets requirements
            teleport(player, Location(3748, 9373, 0), TeleportManager.TeleportType.INSTANT) // Teleport player into the cave
            return@on true // Indicate successful interaction
        }

        // Listener for cave exit interaction
        on(CAVE_EXIT, IntType.SCENERY, "exit") { player, node ->
            when (node.id) {
                Scenery.CAVE_23157, Scenery.CAVE_23158 -> teleport(player, Location(2729, 3733, 0), TeleportManager.TeleportType.INSTANT) // Teleport for specific cave exits
                else -> teleport(player, Location(3749, 2973, 0), TeleportManager.TeleportType.INSTANT) // Default teleport for other exits
            }
            return@on true // Indicate successful interaction
        }
    }
}