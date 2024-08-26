package core.auth

import java.sql.Timestamp

/**
 * User account info
 *
 * @param username The username of the user
 * @param password The password of the user
 * @param uid Unique identifier for the user
 * @param rights User rights level
 * @param credits User credits balance
 * @param ip Current IP address of the user
 * @param lastUsedIp Last used IP address of the user
 * @param muteEndTime Timestamp for when the mute ends
 * @param banEndTime Timestamp for when the ban ends
 * @param contacts User's contacts information
 * @param blocked Blocked users information
 * @param clanName Name of the user's clan
 * @param currentClan Current clan the user is part of
 * @param clanReqs Clan requirements
 * @param timePlayed Total time played by the user
 * @param lastLogin Timestamp of the last login
 * @param online User's online status
 * @param joinDate Timestamp of when the user joined
 * @constructor User account info
 */
class UserAccountInfo(
    var username: String, // User's username
    var password: String, // User's password
    var uid: Int, // Unique user identifier
    var rights: Int, // User's rights level
    var credits: Int, // User's credits
    var ip: String, // Current IP address
    var lastUsedIp: String, // Last used IP address
    var muteEndTime: Long, // Mute end time in milliseconds
    var banEndTime: Long, // Ban end time in milliseconds
    var contacts: String, // User's contacts
    var blocked: String, // Blocked users
    var clanName: String, // Name of the clan
    var currentClan: String, // Current clan of the user
    var clanReqs: String, // Clan requirements
    var timePlayed: Long, // Total time played
    var lastLogin: Long, // Last login timestamp
    var online: Boolean, // Online status
    var joinDate: Timestamp // Join date timestamp
) {
    companion object {
        val default = createDefault() // Default user account info

        @JvmStatic
        fun createDefault(): UserAccountInfo {
            return UserAccountInfo(
                username = "", // Default username
                password = "", // Default password
                uid = 0, // Default UID
                rights = 0, // Default rights
                credits = 0, // Default credits
                ip = "", // Default IP
                lastUsedIp = "", // Default last used IP
                muteEndTime = 0L, // Default mute end time
                banEndTime = 0L, // Default ban end time
                contacts = "", // Default contacts
                blocked = "", // Default blocked users
                clanName = "", // Default clan name
                currentClan = "", // Default current clan
                clanReqs = "1,0,8,9", // Default clan requirements
                timePlayed = 0L, // Default time played
                lastLogin = 0L, // Default last login
                online = false, // Default online status
                joinDate = Timestamp(System.currentTimeMillis()) // Default join date
            ).also { it.setInitialReferenceValues() } // Set initial reference values
        }
    }

    lateinit var initialValues: Array<Any> // Array to hold initial values

    /**
     * Set initial reference values
     *
     */
    fun setInitialReferenceValues() {
        initialValues = toArray() // Convert current values to array and set as initial
    }

    /**
     * Get changed fields
     *
     * @return A pair containing a list of changed field indices and the current values
     */
    fun getChangedFields(): Pair<ArrayList<Int>, Array<Any>> {
        val current = toArray() // Get current values as an array
        val changed = ArrayList<Int>() // List to hold indices of changed fields

        for (i in current.indices) { // Iterate through current values
            if (current[i] != initialValues[i]) changed.add(i) // Check for changes and add index if changed
        }

        return Pair(changed, current) // Return changed indices and current values
    }

    /**
     * To array
     *
     * @return An array representation of the user account info
     */
    fun toArray(): Array<Any> {
        return arrayOf(
            username,
            password,
            uid,
            rights,
            credits,
            ip,
            lastUsedIp,
            muteEndTime,
            banEndTime,
            contacts,
            blocked,
            clanName,
            currentClan,
            clanReqs,
            timePlayed,
            lastLogin,
            online,
            joinDate
        ) // Convert properties to array
    }

    override fun toString(): String {
        return "USER:$username,PASS:$password,UID:$uid,RIGHTS:$rights,CREDITS:$credits,IP:$ip,LASTIP:$lastUsedIp" // String representation of user account info
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true // Check for reference equality
        if (javaClass != other?.javaClass) return false // Check for class type

        other as UserAccountInfo // Cast to UserAccountInfo

        // Check for equality of all properties
        if (username != other.username) return false
        if (password != other.password) return false
        if (uid != other.uid) return false
        if (rights != other.rights) return false
        if (credits != other.credits) return false
        if (ip != other.ip) return false
        if (lastUsedIp != other.lastUsedIp) return false
        if (muteEndTime != other.muteEndTime) return false
        if (banEndTime != other.banEndTime) return false
        if (contacts != other.contacts) return false
        if (blocked != other.blocked) return false
        if (clanName != other.clanName) return false
        if (currentClan != other.currentClan) return false
        if (clanReqs != other.clanReqs) return false
        if (timePlayed != other.timePlayed) return false
        if (lastLogin != other.lastLogin) return false
        if (online != other.online) return false

        return true // All properties are equal
    }

    override fun hashCode(): Int {
        var result = username.hashCode() // Start with username hash
        result = 31 * result + password.hashCode() // Include password hash
        result = 31 * result + uid // Include UID
        result = 31 * result + rights // Include rights
        result = 31 * result + credits // Include credits
        result = 31 * result + ip.hashCode() // Include current IP hash
        result = 31 * result + lastUsedIp.hashCode() // Include last used IP hash
        result = 31 * result + muteEndTime.hashCode() // Include mute end time hash
        result = 31 * result + banEndTime.hashCode() // Include ban end time hash
        result = 31 * result + contacts.hashCode() // Include contacts hash
        result = 31 * result + blocked.hashCode() // Include blocked users hash
        result = 31 * result + clanName.hashCode() // Include clan name hash
        result = 31 * result + currentClan.hashCode() // Include current clan hash
        result = 31 * result + clanReqs.hashCode() // Include clan requirements hash
        result = 31 * result + timePlayed.hashCode() // Include time played hash
        result = 31 * result + lastLogin.hashCode() // Include last login hash
        result = 31 * result + online.hashCode() // Include online status hash
        return result // Return final hash code
    }

    fun isDefault() : Boolean {
        return this == default
    }
}