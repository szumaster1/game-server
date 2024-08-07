package core.auth

import core.game.node.entity.player.Player
import core.storage.AccountStorageProvider

/**
 * Auth provider
 *
 * @param T
 * @constructor Auth provider
 */
abstract class AuthProvider<T : AccountStorageProvider> {
    lateinit var storageProvider: T

    /**
     * Configure for
     *
     * @param provider
     */
    abstract fun configureFor(provider: T)

    /**
     * Can create account with
     *
     * @param info
     * @return
     */
    fun canCreateAccountWith(info: UserAccountInfo): Boolean {
        return !storageProvider.checkUsernameTaken(info.username)
    }

    /**
     * Create account with
     *
     * @param info
     * @return
     */
    abstract fun createAccountWith(info: UserAccountInfo): Boolean

    /**
     * Check login
     *
     * @param username
     * @param password
     * @return
     */
    abstract fun checkLogin(username: String, password: String): Pair<AuthResponse, UserAccountInfo?>

    /**
     * Check password
     *
     * @param player
     * @param password
     * @return
     */
    abstract fun checkPassword(player: Player, password: String): Boolean

    /**
     * Update password
     *
     * @param username
     * @param newPassword
     */
    abstract fun updatePassword(username: String, newPassword: String)
}
