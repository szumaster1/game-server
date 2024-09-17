package content.global.random.event.kissthefrog

import core.api.*
import org.rs.consts.Animations
import org.rs.consts.Components
import org.rs.consts.Items
import org.rs.consts.NPCs
import core.game.dialogue.FacialExpression
import core.game.node.Node
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic

/**
 * Frog utils.
 */
object FrogUtils {
    // Attributes
    const val ATTRIBUTE_FROG_RANDOM_EVENT = "frog:task"
    const val ATTRIBUTE_FROG_LOCATION = "/save:original-loc"
    const val ATTRIBUTE_FROG_LOGOUT = "frog:logout"
    const val ATTRIBUTE_FROG_TASK_FAIL = "frog:fail"

    // NPCs
    const val FROG_NPC = NPCs.FROG_2469
    const val FROG_APPEARANCE_NPC = NPCs.FROG_2473
    const val FROG_PRINCE_NPC = NPCs.FROG_PRINCE_2474
    const val FROG_PRINCESS_NPC = NPCs.FROG_PRINCESS_2475

    // Animations
    const val TRANSFORMATION_ANIM = Animations.GET_INTO_FROG_POSITION_2377
    const val FIRST_PHASE_TRANSFORM_ANIM = 2374
    const val SECOND_PHASE_TRANSFORM_ANIM = 2375
    const val BLOW_KISS_ANIM = 1374

    // Graphics
    const val BLOW_KISS_GFX = 1702

    /**
     * Cleanup.
     *
     * @param player The player to clean up after the frog event.
     */
    fun cleanup(player: Player) {
        // Restore player's teleport location
        player.properties.teleportLocation = getAttribute(player, ATTRIBUTE_FROG_LOCATION, null)

        // Restore player's tabs
        restoreTabs(player)

        // Transform player's appearance back to normal
        player.appearance.transformNPC(-1)

        // Clear logout listener
        clearLogoutListener(player, ATTRIBUTE_FROG_LOGOUT)

        // Remove frog event attributes from player
        removeAttributes(player, ATTRIBUTE_FROG_RANDOM_EVENT, ATTRIBUTE_FROG_TASK_FAIL, ATTRIBUTE_FROG_LOCATION, ATTRIBUTE_FROG_LOGOUT)

        // Unlock player
        unlock(player)
    }

    /**
     * Kiss the frog
     *
     * @param player The player who is kissing the frog.
     * @param node The frog NPC node.
     */
    fun kissTheFrog(player: Player, node: Node) {
        val npc = node as NPC

        // Lock player and interactions for 100 game ticks
        lock(player, 100)
        lockInteractions(player, 100)

        // Run a pulse to perform the frog transformation
        player.pulseManager.run(object : Pulse() {
            var counter = 0
            override fun pulse(): Boolean {
                when (counter++) {
                    1 -> {
                        // Face the player and the frog NPC towards each other
                        face(player, npc)
                        face(npc, player)

                        // Animate the player and the frog NPC for the transformation
                        animate(player, Animation(TRANSFORMATION_ANIM))
                        animate(npc, Animation(FIRST_PHASE_TRANSFORM_ANIM))
                    }

                    2 -> animate(npc, Animation(SECOND_PHASE_TRANSFORM_ANIM))

                    3 -> {
                        // Transform the local frog NPC into a frog prince or princess
                        findLocalNPC(player, FROG_NPC)!!.transform(if (player.isMale) FROG_PRINCESS_NPC else FROG_PRINCE_NPC)

                        // Visualize the transformation with a graphic
                        visualize(npc, -1, Graphic(85, 100))
                    }

                    5 -> {
                        // Send dialogues to the player
                        player.dialogueInterpreter.sendDialogues(
                            if (player.isMale) FROG_PRINCESS_NPC else FROG_PRINCE_NPC,
                            FacialExpression.HAPPY,
                            "Thank you so much, ${player.username}.",
                            "I must return to my fairy tale kingdom now, but I will",
                            "leave you a reward for your kindness.",
                        )
                    }

                    6 -> visualize(npc, BLOW_KISS_ANIM, BLOW_KISS_GFX)
                    9 -> openInterface(player, Components.FADE_TO_BLACK_120)
                    12 -> {
                        // Close the interface and re-transform the frog NPC
                        closeInterface(player)
                        npc.reTransform()

                        // Clean up after the frog event
                        cleanup(player)

                        // Give the player a reward
                        reward(player)

                        // Open an interface to show the reward
                        openInterface(player, Components.FADE_FROM_BLACK_170)

                        // Send a message to the player
                        sendMessage(player, "You've received a random event gift!")
                    }
                }
                return false
            }
        })
        return
    }

    /**
     * Reward
     *
     * @param player The player to reward.
     */
    fun reward(player: Player) {
        // Add the frog token item to the player's inventory or drop it on the ground
        addItemOrDrop(player, Items.FROG_TOKEN_6183)
    }
}
