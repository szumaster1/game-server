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
    var username: String,
    var password: String,
    var uid: Int,
    var rights: Int,
    var credits: Int,
    var ip: String,
    var lastUsedIp: String,
    var muteEndTime: Long,
    var banEndTime: Long,
    var contacts: String,
    var blocked: String,
    var clanName: String,
    var currentClan: String,
    var clanReqs: String,
    var timePlayed: Long,
    var lastLogin: Long,
    var online: Boolean,
    var joinDate: Timestamp
) {
    companion object {
        val default = createDefault()

        @JvmStatic
        fun createDefault(): UserAccountInfo {
            return UserAccountInfo(
                username = "",
                password = "",
                uid = 0,
                rights = 0,
                credits = 0,
                ip = "",
                lastUsedIp = "",
                muteEndTime = 0L,
                banEndTime = 0L,
                contacts = "",
                blocked = "",
                clanName = "",
                currentClan = "",
                clanReqs = "1,0,8,9",
                timePlayed = 0L,
                lastLogin = 0L,
                online = false,
                joinDate = Timestamp(System.currentTimeMillis())
            ).also { it.setInitialReferenceValues() }
        }
    }

    lateinit var initialValues: Array<Any>

    /**
     * Set initial reference values
     *
     */
    fun setInitialReferenceValues() {
        initialValues = toArray()
    }

    /**
     * Get changed fields
     *
     * @return A pair containing a list of changed field indices and the current values
     */
    fun getChangedFields(): Pair<ArrayList<Int>, Array<Any>> {
        val current = toArray()
        val changed = ArrayList<Int>()

        for (i in current.indices) {
            if (current[i] != initialValues[i]) changed.add(i)
        }

        return Pair(changed, current)
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
        )
    }

    override fun toString(): String {
        return "USER:$username,PASS:$password,UID:$uid,RIGHTS:$rights,CREDITS:$credits,IP:$ip,LASTIP:$lastUsedIp"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserAccountInfo


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

        return true
    }

    override fun hashCode(): Int {
        var result = username.hashCode()
        result = 31 * result + password.hashCode()
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