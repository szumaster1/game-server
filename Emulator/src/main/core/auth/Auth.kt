package core.auth

import core.Configuration
import core.storage.AccountStorageProvider
import core.storage.InMemoryStorageProvider
import core.storage.SQLStorageProvider

/**
 * The Auth object is responsible for managing authentication and account storage.
 */
object Auth {

    /**
     * The authenticator instance used for authentication processes.
     * It is initialized later in the configure function.
     */
    lateinit var authenticator: AuthProvider<*>

    /**
     * The storage provider instance that handles account data storage.
     * It is initialized later in the configure function.
     */
    lateinit var storageProvider: AccountStorageProvider

    /**
     * Configures the Auth object by initializing the storage provider and authenticator
     * based on server constants.
     */
    fun configure() {
        storageProvider = if (Configuration.PERSIST_ACCOUNTS)
            SQLStorageProvider()
        else
        // Use in-memory storage if persistence is not required.
            InMemoryStorageProvider()

        // Initialize the authenticator.
        authenticator = if (Configuration.USE_AUTH)
            ProductionAuthenticator().also { it.configureFor(storageProvider) }
        else
            DevelopmentAuthenticator().also { it.configureFor(storageProvider) }
    }

}
