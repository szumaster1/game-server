package core.game.node.entity.player.info.login

import core.api.LoginListener
import core.api.reinitVarps
import core.auth.AuthResponse
import core.game.node.entity.player.Player
import core.game.node.entity.player.info.PlayerDetails
import core.game.system.SystemManager
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.GameWorld.loginListeners
import core.game.world.repository.Repository
import java.util.function.Consumer

/**
 * Login parser
 *
 * @param details The details of the player attempting to log in.
 * @constructor Initializes the LoginParser with player details.
 */
class LoginParser(val details: PlayerDetails) {
    /**
     * Initialize the login process for a player.
     *
     * @param player The player attempting to log in.
     * @param reconnect Indicates if the player is reconnecting.
     */
    fun initialize(player: Player, reconnect: Boolean) {
        // Validate the login request before proceeding
        if (!validateRequest()) return

        // Declare a parser for player save data
        lateinit var parser: PlayerSaveParser

        try {
            // Attempt to parse the player's save data
            parser = PlayerParser.parse(player)
                ?: throw IllegalStateException("Failed parsing save for: " + player.username) // Handle parsing failure
        } catch (e: Exception) {
            // Print the stack trace for debugging
            e.printStackTrace()
            // Remove the player from the repository if parsing fails
            Repository.removePlayer(player)
            // Flag the response as an error loading the profile
            flag(AuthResponse.ErrorLoadingProfile)
        }

        // Submit a pulse task to handle the login process asynchronously
        GameWorld.Pulser.submit(object : Pulse(1) {
            override fun pulse(): Boolean {
                try {
                    // Check if the player's session is active
                    if (details.session.isActive) {
                        // Notify all login listeners of the login event
                        loginListeners.forEach(Consumer { listener: LoginListener -> listener.login(player) }) // Execute login hooks
                        // Run hooks for any saved content associated with the player
                        parser.runContentHooks() // Execute saved content parsing hooks
                        // Set the player's session object
                        player.details.session.`object` = (player)

                        // Handle reconnection logic
                        if (reconnect) {
                            reconnect(player) // Reconnect the player
                        } else {
                            // Flag the response as successful and initialize the player
                            flag(AuthResponse.Success)
                            player.init() // Initialize player state
                            reinitVarps(player) // Reinitialize variable states for the player
                        }
                    } else {
                        // Remove the player if the session is not active
                        Repository.removePlayer(player)
                    }
                } catch (t: Throwable) {
                    // Print the stack trace for debugging
                    t.printStackTrace()
                    // Remove the player from the repository on error
                    Repository.removePlayer(player)
                    // Flag the response as an error loading the profile
                    flag(AuthResponse.ErrorLoadingProfile)
                }
                return true // Continue the pulse
            }
        })
    }

    /**
     * Handles the reconnection of a player.
     *
     * @param player The player reconnecting.
     */
    private fun reconnect(player: Player) {
        // Remove the player from the disconnection queue
        Repository.disconnectionQueue.remove(details.username)
        // Initialize the player's reconnect state
        player.initReconnect()
        // Set the player as active
        player.isActive = true
        // Flag the response as successful
        flag(AuthResponse.Success)
        // Update the player's scene graph
        player.updateSceneGraph(true)
        // Reinitialize variable states for the player
        reinitVarps(player)
        // Configure the game world for the player
        LoginConfiguration.configureGameWorld(player)
    }

    /**
     * Validates the login request.
     *
     * @return `True` if the request is valid, otherwise `False`.
     */
    private fun validateRequest(): Boolean {
        // Check if the player's session is active
        if (!details.session.isActive()) {
            return false // Invalid if session is not active
        }
        // Check if the system is currently updating
        if (SystemManager.isUpdating) {
            return flag(AuthResponse.Updating) // Flag as updating
        }
        // Check if the account is banned
        return if (details.isBanned) {
            flag(AuthResponse.AccountDisabled) // Flag as account disabled
        } else true // Valid request
    }

    /**
     * Flags the response for the login attempt.
     *
     * @param response The authentication response to flag.
     * @return `True` if the response indicates success, otherwise `False`.
     */
    fun flag(response: AuthResponse): Boolean {
        // Write the response to the player's session
        details.session.write(response, true)
        // Return whether the response indicates success
        return response == AuthResponse.Success
    }
}