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
        if (!validateRequest()) return

        lateinit var parser: PlayerSaveParser

        try {
            parser = PlayerParser.parse(player)
                ?: throw IllegalStateException("Failed parsing save for: " + player.username) // Handle parsing failure
        } catch (e: Exception) {
            e.printStackTrace()
            Repository.removePlayer(player)
            flag(AuthResponse.ErrorLoadingProfile)
        }

        GameWorld.Pulser.submit(object : Pulse(1) {
            override fun pulse(): Boolean {
                try {
                    if (details.session.isActive) {
                        loginListeners.forEach(Consumer { listener: LoginListener -> listener.login(player) }) // Execute login hooks
                        parser.runContentHooks()
                        player.details.session.`object` = (player)

                        if (reconnect) {
                            reconnect(player)
                        } else {
                            flag(AuthResponse.Success)
                            player.init()
                            reinitVarps(player)
                        }
                    } else {
                        Repository.removePlayer(player)
                    }
                } catch (t: Throwable) {
                    t.printStackTrace()
                    Repository.removePlayer(player)
                    flag(AuthResponse.ErrorLoadingProfile)
                }
                return true
            }
        })
    }

    /**
     * Handles the reconnection of a player.
     *
     * @param player The player reconnecting.
     */
    private fun reconnect(player: Player) {
        Repository.disconnectionQueue.remove(details.username)
        player.initReconnect()
        player.isActive = true
        flag(AuthResponse.Success)
        player.updateSceneGraph(true)
        reinitVarps(player)
        LoginConfiguration.configureGameWorld(player)
    }

    /**
     * Validates the login request.
     *
     * @return `True` if the request is valid, otherwise `False`.
     */
    private fun validateRequest(): Boolean {
        if (!details.session.isActive) {
            return false
        }
        if (SystemManager.isUpdating) {
            return flag(AuthResponse.Updating)
        }
        return if (details.isBanned) {
            flag(AuthResponse.AccountDisabled)
        } else true
    }

    /**
     * Flags the response for the login attempt.
     *
     * @param response The authentication response to flag.
     * @return `True` if the response indicates success, otherwise `False`.
     */
    fun flag(response: AuthResponse): Boolean {
        details.session.write(response, true)
        return response == AuthResponse.Success
    }
}