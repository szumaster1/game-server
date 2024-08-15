package core.api.storage

import core.api.auth.UserAccountInfo

/**
 * Account storage provider interface.
 */
interface AccountStorageProvider {
    /**
     * Check if the username is already taken.
     *
     * @param username The username to check.
     * @return True if the username is taken, false otherwise.
     */
    fun checkUsernameTaken(username: String): Boolean

    /**
     * Retrieve account information for a given username.
     *
     * @param username The username to retrieve account info for.
     * @return UserAccountInfo object containing account information.
     */
    fun getAccountInfo(username: String): UserAccountInfo

    /**
     * Get a list of usernames associated with a specific IP address.
     *
     * @param ip The IP address to search for.
     * @return List of usernames associated with the IP address.
     */
    fun getUsernamesWithIP(ip: String): List<String>

    /**
     * Store user account information.
     *
     * @param info UserAccountInfo object to store.
     */
    fun store(info: UserAccountInfo)

    /**
     * Update user account information.
     *
     * @param info UserAccountInfo object to update.
     */
    fun update(info: UserAccountInfo)

    /**
     * Remove user account information.
     *
     * @param info UserAccountInfo object to remove.
     */
    fun remove(info: UserAccountInfo)

    /**
     * Get a list of online friends for a given username.
     *
     * @param username The username to retrieve online friends for.
     * @return List of online friends associated with the username.
     */
    fun getOnlineFriends(username: String): List<String>
}
