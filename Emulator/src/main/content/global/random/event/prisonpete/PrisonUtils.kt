package content.global.random.event.prisonpete

import core.api.*
import cfg.consts.Items
import cfg.consts.NPCs
import cfg.consts.Sounds
import core.api.utils.WeightBasedTable
import core.api.utils.WeightedItem
import core.game.interaction.QueueStrength
import core.game.node.entity.player.Player
import core.game.system.timer.impl.AntiMacro
import core.game.world.GameWorld
import core.game.world.map.Location
import core.game.world.map.zone.ZoneBorders
import core.game.world.update.flag.context.Graphic
import core.tools.RandomFunction

/**
 * Prison utils.
 */
object PrisonUtils {

    // The location of the prison
    val PRISON_LOCATION: Location = Location.create(2086, 4462, 0)

    // Save keys for different actions
    const val GET_REWARD = "/save:prisonpete:reward"
    const val RE_LOGOUT = "/save:prisonpete:logout"
    const val POP_KEY = "/save:prisonpete:pop-animals"
    const val POP_KEY_FALSE = "/save:prisonpete:pop-incorrect"
    const val POP_KEY_VALUE = "/save:prisonpete:value"
    const val PREVIOUS_LOCATION = "/save:original-loc"

    // The IDs of the animals in the prison
    val ANIMAL_ID = intArrayOf(3119, 3120, 3121, 3122)

    // The borders of the prison zone
    val PRISON_ZONE = ZoneBorders(2075, 4458, 2096, 4474)

    /**
     * Clean all data and attributes related to this random.
     *
     * @param player The player to clean up.
     */
    fun cleanup(player: Player) {
        // Reset the player's teleport location
        player.properties.teleportLocation = getAttribute(player, PREVIOUS_LOCATION, null)

        // Clear the logout listener
        clearLogoutListener(player, RE_LOGOUT)

        // Remove all saved attributes related to the prison event
        removeAttributes(player, POP_KEY, GET_REWARD, PREVIOUS_LOCATION, RE_LOGOUT, POP_KEY_FALSE, POP_KEY_VALUE)

        // Send a welcome back message to the player
        sendMessage(player, "Welcome back to ${GameWorld.settings!!.name}.")

        // Remove any prison keys from the player's inventory
        if (anyInInventory(player, Items.PRISON_KEY_6966)) {
            removeAll(player, Items.PRISON_KEY_6966)
        }
    }

    /**
     * Handles bringing key to pete.
     *
     * @param player The player to bring the key.
     */
    fun bringKey(player: Player) {
        queueScript(player, 1, QueueStrength.SOFT) { stage: Int ->
            when (stage) {
                0 -> {
                    // Force the player to walk to the specified location
                    forceWalk(player, Location.create(2084, 4461, 0), "smart")
                    return@queueScript keepRunning(player)
                }

                1 -> {
                    // Face the Prison Pete NPC and open the dialogue
                    face(player, findNPC(NPCs.PRISON_PETE_3118)!!.location)
                    openDialogue(player, PrisonPeteDialogue(dialOpt = 2))
                    return@queueScript stopExecuting(player)
                }

                else -> return@queueScript stopExecuting(player)
            }
        }
    }

    /**
     * Teleport method.
     *
     * @param player The player to teleport.
     */
    fun teleport(player: Player) {
        queueScript(player, 1, QueueStrength.SOFT) { stage: Int ->
            when (stage) {
                0 -> {
                    // Save the player's previous location
                    setAttribute(player, PREVIOUS_LOCATION, player.location)

                    // Visualize the teleportation with a graphic and play a sound
                    visualize(player, 714, Graphic(308, 85, 50))
                    playAudio(player, Sounds.TELEPORT_ALL_200)
                    return@queueScript delayScript(player, 3)
                }

                1 -> {
                    // Register a logout listener to return the player to their previous location upon logout
                    registerLogoutListener(player, RE_LOGOUT) { p ->
                        p.location = getAttribute(p, PREVIOUS_LOCATION, player.location)
                    }

                    // Teleport the player to the prison location
                    teleport(player, PRISON_LOCATION)
                    return@queueScript delayScript(player, 1)
                }

                2 -> {
                    // Reset the pop key attribute and terminate the AntiMacro event NPC
                    setAttribute(player, POP_KEY, 0)
                    AntiMacro.terminateEventNpc(player)
                    resetAnimator(player)
                    return@queueScript stopExecuting(player)
                }

                else -> return@queueScript stopExecuting(player)
            }
        }
    }

    /**
     * Gets the reward.
     *
     * @param player The player to reward.
     * @return reward.
     */
    fun reward(player: Player) {
        AntiMacro.rollEventLoot(player).forEach { addItemOrDrop(player, it.id, it.amount) }
    }
}