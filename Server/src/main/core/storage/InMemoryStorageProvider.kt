package core.storage

import core.auth.UserAccountInfo

/**
 * In memory storage provider.
 */
class InMemoryStorageProvider : AccountStorageProvider {
    private val storage = HashMap<String, UserAccountInfo>()

    /**
     * Check if the username is already taken.
     */
    override fun checkUsernameTaken(username: String): Boolean {
        return storage[username] != null
    }

    /**
     * Get the account information for a given username.
     */
    override fun getAccountInfo(username: String): UserAccountInfo {
        return storage[username] ?: UserAccountInfo.createDefault()
            .also { it.uid = username.hashCode(); storage[username] = it }
    }

    /**
     * Store the user account information.
     */
    override fun store(info: UserAccountInfo) {
        info.uid = info.username.hashCode()
        storage[info.username] = info
    }

    /**
     * Update the user account information.
     */
    override fun update(info: UserAccountInfo) {
        storage[info.username] = info
    }

    /**
     * Remove the user account information.
     */
    override fun remove(info: UserAccountInfo) {
        storage.remove(info.username)
    }

    /**
     * Get online friends for a given username.
     */
    override fun getOnlineFriends(username: String): List<String> {
        return ArrayList()
    }

    /**
     * Get usernames associated with a specific IP address.
     */
    override fun getUsernamesWithIP(ip: String): List<String> {
        return ArrayList()
    }
}
