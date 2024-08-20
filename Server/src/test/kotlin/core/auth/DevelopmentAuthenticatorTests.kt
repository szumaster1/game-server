package core.auth

import core.api.auth.AuthResponse
import core.api.auth.DevelopmentAuthenticator
import core.api.auth.UserAccountInfo
import core.storage.InMemoryStorageProvider
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * Development authenticator tests.
 */
class DevelopmentAuthenticatorTests {
    private val authProvider = DevelopmentAuthenticator()
    private val storageProvider = InMemoryStorageProvider()

    init {
        authProvider.configureFor(storageProvider)
    }

    /**
     * Should allow checking if account exists.
     */
    @Test
    fun shouldAllowCheckingIfAccountExists() {
        val info = UserAccountInfo.createDefault()
        info.username = "Billy"
        Assertions.assertEquals(true, authProvider.canCreateAccountWith(info))
        authProvider.createAccountWith(info)
        Assertions.assertEquals(false, authProvider.canCreateAccountWith(info))
    }

    /**
     * Login with valid account info returns success.
     */
    @Test
    fun loginWithValidAccountInfoReturnsSuccess() {
        val info = UserAccountInfo.createDefault()
        info.username = "Billy"
        authProvider.createAccountWith(info)
        Assertions.assertEquals(AuthResponse.Success, authProvider.checkLogin("Billy", "").first)
    }

    /**
     * Development authenticator should work regardless if account exists or not.
     */
    @Test
    fun loginWithInvalidAccountInfoReturnsSuccess() {
        val info = UserAccountInfo.createDefault()
        info.username = "Billy"
        authProvider.createAccountWith(info)
        Assertions.assertEquals(AuthResponse.Success, authProvider.checkLogin("Bilbo", "ebbeb").first)
    }

    /**
     * Login username is not case-sensitive.
     */
    @Test
    fun loginUsernameIsNotCaseSensitive() {
        val info = UserAccountInfo.createDefault()
        info.username = "Billy"
        authProvider.createAccountWith(info)
        Assertions.assertEquals(AuthResponse.Success, authProvider.checkLogin("Billy", "").first)
        Assertions.assertEquals(AuthResponse.Success, authProvider.checkLogin("billy", "").first)
    }

    /**
     * Development authenticator should basically bypass needing/creating an account entirely. useful for SP too.
     */
    @Test
    fun loginToUnregisteredAccountCreatesIt() {
        authProvider.checkLogin("masterbaggins", "whatever")
        val info = storageProvider.getAccountInfo("masterbaggins")
        Assertions.assertEquals(2, info.rights)
    }
}
