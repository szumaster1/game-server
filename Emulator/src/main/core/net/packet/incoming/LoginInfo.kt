package core.net.packet.incoming

import core.cache.crypto.ISAACCipher
import core.cache.crypto.ISAACPair

/**
 * Represents login information for a user.
 *
 * @param showAds            Indicates if ads are shown (Unused)
 * @param windowMode         the window mode
 * @param screenWidth        the screen width
 * @param screenHeight       the screen height
 * @param displayMode        the display mode
 * @param adAffiliateId      the ad affiliate ID
 * @param settingsHash       the settings hash
 * @param currentPacketCount the current packet count
 * @param username           the username
 * @param password           the password
 * @param isaacPair          the ISAAC pair
 * @param opcode             the opcode
 * @param crcSums            the CRC sums
 * @constructor Represents [LoginInfo] with provided parameters
 */
class LoginInfo(
    var showAds: Boolean,
    var windowMode: Int,
    var screenWidth: Int,
    var screenHeight: Int,
    var displayMode: Int,
    var adAffiliateId: Int,
    var settingsHash: Int,
    var currentPacketCount: Int,
    var username: String,
    var password: String,
    var isaacPair: ISAACPair,
    var opcode: Int,
    var crcSums: IntArray
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
