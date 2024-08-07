package core.network.packet.incoming

import core.cache.crypto.ISAACCipher
import core.cache.crypto.ISAACPair

/**
 * Represents login information for a user.
 *
 * @param showAds            Flag indicating if ads are shown
 * @param windowMode         The window mode
 * @param screenWidth        The screen width
 * @param screenHeight       The screen height
 * @param displayMode        The display mode
 * @param adAffiliateId      The ad affiliate ID
 * @param settingsHash       The settings hash
 * @param currentPacketCount The current packet count
 * @param username           The username
 * @param password           The password
 * @param isaacPair          The ISAAC pair
 * @param opcode             The opcode
 * @param crcSums            The CRC sums
 * @constructor Initializes login information with provided parameters
 */
class LoginInfo(
    var showAds: Boolean, // Indicates if ads are shown (Unused)
    var windowMode: Int, // Represents the window mode
    var screenWidth: Int, // Represents the screen width
    var screenHeight: Int, // Represents the screen height
    var displayMode: Int, // Represents the display mode
    var adAffiliateId: Int, // The ad affiliate ID (Unused)
    var settingsHash: Int, // The settings hash
    var currentPacketCount: Int, // The current packet count
    var username: String, // The username
    var password: String, // The password
    var isaacPair: ISAACPair, // The ISAAC pair
    var opcode: Int, // The opcode
    var crcSums: IntArray // The CRC sums
) {
    companion object {

        fun createDefault(): LoginInfo {
            return LoginInfo(
                false,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                "",
                "",
                ISAACPair(ISAACCipher(intArrayOf()), ISAACCipher(intArrayOf())),
                0,
                IntArray(Login.CACHE_INDEX_COUNT)
            )
        }
    }

    override fun toString(): String {
        return "ads:$showAds,wm:$windowMode,sw:$screenWidth,sh:$screenHeight,dm:$displayMode,adid:$adAffiliateId,settings:$settingsHash,pkt:$currentPacketCount,un:$username,pw:$password"
    }
}
