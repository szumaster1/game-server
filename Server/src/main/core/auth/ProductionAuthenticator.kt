package core.auth

import core.ServerConstants
import core.storage.AccountStorageProvider
import core.game.node.entity.player.Player
import core.game.system.SystemManager
import core.storage.SQLStorageProvider
import java.sql.SQLDataException
import java.sql.Timestamp

/**
 * Production authenticator.
 */
class ProductionAuthenticator : AuthProvider<AccountStorageProvider>() {

    /**
     * Configures the authenticator with the provided storage provider.
     *
     * @param provider The storage provider to configure.
     */
    override fun configureFor(provider: AccountStorageProvider) {
        storageProvider = provider
        if (provider is SQLStorageProvider) {
            provider.configure(
                ServerConstants.DATABASE_ADDRESS!!,
                ServerConstants.DATABASE_NAME!!,
                ServerConstants.DATABASE_USER!!,
                ServerConstants.DATABASE_PASS!!
            )
        }
    }

    /**
     * Creates a new account with the provided user account information.
     *
     * @param info The user account information to create the account with.
     * @return True if the account was created successfully, false otherwise.
     */
    override fun createAccountWith(info: UserAccountInfo): Boolean {
        try {
            info.password = SystemManager.encryption.hashPassword(info.password)
            info.joinDate = Timestamp(System.currentTimeMillis())
            storageProvider.store(info)
        } catch (e: SQLDataException) {
            return false
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    /**
     * Checks the login credentials for a user.
     *
     * @param username The username of the account.
     * @param password The password of the account.
     * @return A pair containing the authentication response and user account information if successful.
     */
    override fun checkLogin(username: String, password: String): Pair<AuthResponse, UserAccountInfo?> {
        val info: UserAccountInfo
        try {
            if (!storageProvider.checkUsernameTaken(username.lowercase())) {
                return Pair(AuthResponse.InvalidCredentials, null)
            }
            info = storageProvider.getAccountInfo(username.lowercase())
            val passCorrect = SystemManager.encryption.checkPassword(password, info.password)
            if (!passCorrect || info.password.isEmpty()) return Pair(AuthResponse.InvalidCredentials, null)
            if (info.banEndTime > System.currentTimeMillis()) return Pair(AuthResponse.AccountDisabled, null)
            if (info.online) return Pair(AuthResponse.AlreadyOnline, null)
        } catch (e: Exception) {
            e.printStackTrace()
            return Pair(AuthResponse.CouldNotLogin, null)
        }
        return Pair(AuthResponse.Success, info)
    }

    /**
     * Checks if the provided password matches the player's password.
     *
     * @param player The player whose password is being checked.
     * @param password The password to check against the player's password.
     * @return True if the password matches, false otherwise.
     */
    override fun checkPassword(player: Player, password: String): Boolean {
        return SystemManager.encryption.checkPassword(password, player.details.password)
    }

    /**
     * Updates the password for the specified user account.
     *
     * @param username The username of the account to update.
     * @param newPassword The new password to set for the account.
     */
    override fun updatePassword(username: String, newPassword: String) {
        val info = storageProvider.getAccountInfo(username)
        info.password = SystemManager.encryption.hashPassword(newPassword)
        storageProvider.update(info)
    }
}
