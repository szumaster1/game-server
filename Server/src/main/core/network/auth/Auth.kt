package core.network.auth

import core.ServerConstants

object Auth {
    lateinit var authenticator: AuthProvider<*>
    lateinit var storageProvider: AccountStorageProvider

    fun configure() {
        storageProvider = if (ServerConstants.PERSIST_ACCOUNTS) SQLStorageProvider()
        else InMemoryStorageProvider()

        authenticator =
            if (ServerConstants.USE_AUTH) ProductionAuthenticator().also { it.configureFor(storageProvider) }
            else DevelopmentAuthenticator().also { it.configureFor(storageProvider) }
    }
}
