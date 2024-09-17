package core.game.node.entity.player.link

import org.rs.consts.Components
import org.rs.consts.Vars
import core.api.openInterface
import core.api.sendMessage
import core.api.setVarp
import core.game.node.entity.player.Player

/**
 * Handles the warning messages of a player.
 * @author Vexia
 */
class WarningMessages {

    /**
     * Represents the warning messages.
     */
    // List to hold warning messages, initialized with a capacity of 20
    private val messages: MutableList<WarningMessage> = ArrayList(20)

    /**
     * Method used to open the warning messages.
     * @param player the player.
     */
    fun open(player: Player) {
        // Opens the warning interface for the specified player
        openInterface(player, Components.CWS_DOOMSAYER_583)
        // Refreshes the player's interface to show the latest data
        refresh(player)
    }

    /**
     * Method used to refresh the config.
     * @param player the player.
     */
    // Refreshes the player's warning interface with the current configuration
    private fun refresh(player: Player) {
        // Sets the variable for the player to update the interface
        setVarp(player, CONFIG, configValue, true)
    }

    /**
     * Gets a warning message by its index.
     * @param index the index.
     * @return the warning message.
     */
    fun getMessage(index: Int): WarningMessage {
        // Iterates through the list of messages to find the one with the matching index
        for (m in messages) {
            if (m.index == index) {
                return m // Returns the found message
            }
        }
        // Creates a new warning message if not found
        val message = WarningMessage(index)
        // Adds the new message to the list
        messages.add(message)
        return message // Returns the newly created message
    }

    // Returns the configuration value for the warning messages
    private val configValue: Int
        get() = 0

    /**
     * Represents a warning message.
     */
    inner class WarningMessage(val index: Int) {

        /**
         * Toggle
         *
         * @param player The player who is toggling the warning message
         */
        fun toggle(player: Player) {
            // Checks if the warning message is not active
            if (!isActive) {
                // Sends a message to the player if they cannot toggle the warning screen
                sendMessage(player, "You cannot toggle the warning screen on or off. You need to go to the area it")
                sendMessage(player, "is linked to enough times to have the option to do so.")
                return // Exits the function if not active
            }
            // Toggles the state of the warning message
            val on = !isToggled
            // Refreshes the player's interface
            refresh(player)
            // Sends a message to the player indicating the new state of the warning screen
            sendMessage(
                player,
                "You have toggled this warning screen " + (if (on) "on" else "off") + ". You will " + (if (on) "see this interface again." else "no longer see this warning screen.")
            )
        }

        // Returns whether the warning message is currently toggled on
        private val isToggled: Boolean
            get() = false

        // Returns whether the warning message is currently active
        private val isActive: Boolean
            get() = false
    }

    companion object {
        // Constant for the configuration variable related to warning messages
        private const val CONFIG = Vars.VARBIT_IFACE_WARNING_MESSAGES_1045
    }
}
