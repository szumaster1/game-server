package core.auth

import core.game.node.entity.player.Player
import core.storage.AccountStorageProvider

/**
 * Auth provider
 *
 * @param T The type of account storage provider that this auth provider will use.
 * @constructor Auth provider
 */
abstract class AuthProvider<T : AccountStorageProvider> {
    lateinit var storageProvider: T

    /**
     * Configure for
     *
     * @param provider The storage provider to configure this auth provider with.
     */
    abstract fun configureFor(provider: T)

    /**
     * Can create account with
     *
     * @param info User account information to check for account creation eligibility.
     * @return True if an account can be created with the provided info, false otherwise.
     */
    fun canCreateAccountWith(info: UserAccountInfo): Boolean {
        return !storageProvider.checkUsernameTaken(info.username)
    }

    /**
     * Create account with
     *
     * @param info User account information to create a new account.
     * @return True if the account was successfully created, false otherwise.
     */
    abstract fun createAccountWith(info: UserAccountInfo): Boolean

    /**
     * Check login
     *
     * @param username The username to check for login.
     * @param password The password associated with the username.
     * @return A pair containing the authentication response and user account information if successful.
     */
    abstract fun checkLogin(username: String, password: String): Pair<AuthResponse, UserAccountInfo?>

    /**
     * Check password
     *
     * @param player The player whose password is being checked.
     * @param password The password to verify against the player's account.
     * @return True if the password matches, false otherwise.
     */
    abstract fun checkPassword(player: Player, password: String): Boolean

    /**
     * Update password
     *
     * @param username The username of the account whose password is to be updated.
     * @param newPassword The new password to set for the account.
     */
    abstract fun updatePassword(username: String, newPassword: String)
}