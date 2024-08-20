package core.api.auth

import core.ServerConstants
import core.storage.AccountStorageProvider
import core.game.node.entity.player.Player

/**
 * Handles authentication processes for user accounts.
 */
class DevelopmentAuthenticator : AuthProvider<AccountStorageProvider>() {

    /**
     * Configures the authenticator with the specified account storage provider.
     *
     * @param provider The account storage provider to be used for authentication.
     */
    override fun configureFor(provider: AccountStorageProvider) {
        storageProvider = provider
    }

    /**
     * Checks the login credentials for a user.
     *
     * @param username The username of the account attempting to log in.
     * @param password The password associated with the username.
     * @return A pair containing the authentication response and user account information.
     */
    override fun checkLogin(username: String, password: String): Pair<AuthResponse, UserAccountInfo?> {
        val info: UserAccountInfo
        if (!storageProvider.checkUsernameTaken(username.lowercase())) {
            info = UserAccountInfo.createDefault()
            info.username = username
            createAccountWith(info)
        } else {
            info = storageProvider.getAccountInfo(username.lowercase())
        }
        return Pair(AuthResponse.Success, storageProvider.getAccountInfo(username))
    }

    /**
     * Creates a new account with the provided user account information.
     *
     * @param info The user account information to be stored.
     * @return A boolean indicating the success of the account creation.
     */
    override fun createAccountWith(info: UserAccountInfo): Boolean {
        info.username = info.username.lowercase()
        if (ServerConstants.NOAUTH_DEFAULT_ADMIN) info.rights = 2
        storageProvider.store(info)
        return true
    }

    /**
     * Checks if the provided password matches the player's stored password.
     *
     * @param player The player whose password is being checked.
     * @param password The password to verify against the player's stored password.
     * @return A boolean indicating whether the password matches.
     */
    override fun checkPassword(player: Player, password: String): Boolean {
        return password == player.details.password
    }

    /**
     * Updates the password for the specified user account.
     *
     * @param username The username of the account whose password is being updated.
     * @param newPassword The new password to be set for the account.
     */
    override fun updatePassword(username: String, newPassword: String) {
        val info = storageProvider.getAccountInfo(username)
        info.password = newPassword
        storageProvider.update(info)
    }
}
