package core.network.amsc

import core.game.node.entity.player.Player
import core.game.system.communication.ClanRank
import core.network.packet.IoBuffer
import core.network.packet.PacketHeader

/**
 * The Management server packet repository.
 * @author Emperor
 */
object MSPacketRepository {
    /**
     * Sends a contact update.
     *
     * @param username The username.
     * @param contact  The contact's username.
     * @param remove   If we're removing the contact from the list.
     * @param block    If the contact list is for the blocked players.
     * @param rank     The new clan rank (or null when not updating clan rank!).
     */
    @JvmStatic
    fun sendContactUpdate(username: String, contact: String, remove: Boolean, block: Boolean, rank: ClanRank?) {
        val buffer = IoBuffer(if (block) 5 else 4, PacketHeader.BYTE)
        buffer.putString(username)
        buffer.putString(contact)
        rank?.let {
            buffer.put(2)
            buffer.put(it.ordinal)
        } ?: buffer.put(if (remove) 1 else 0)
        WorldCommunicator.getSession().write(buffer)
    }

    /**
     * Sends the clan rename packet.
     *
     * @param player   The player.
     * @param clanName The clan name.
     */
    fun sendClanRename(player: Player, clanName: String) {
        val buffer = IoBuffer(7, PacketHeader.BYTE)
        buffer.putString(player.name)
        buffer.putString(clanName)
        WorldCommunicator.getSession().write(buffer)
    }

    /**
     * Sets a clan setting.
     *
     * @param player The player.
     * @param type   The setting type.
     * @param rank   The rank to set.
     */
    fun setClanSetting(player: Player, type: Int, rank: ClanRank?) {
        if (!WorldCommunicator.isEnabled()) {
            return
        }
        val buffer = IoBuffer(8, PacketHeader.BYTE)
        buffer.putString(player.name)
        buffer.put(type)
        rank?.let { buffer.put(it.ordinal) }
        WorldCommunicator.getSession().write(buffer)
    }

    /**
     * Sends the kicking a clan member packet.
     *
     * @param username The player's username.
     * @param name     The name.
     */
    fun sendClanKick(username: String, name: String) {
        val buffer = IoBuffer(9, PacketHeader.BYTE)
        buffer.putString(username)
        buffer.putString(name)
        WorldCommunicator.getSession().write(buffer)
    }

    /**
     * Sends the chat settings.
     *
     * @param player         The player.
     * @param publicSetting  The public chat setting.
     * @param privateSetting The private chat setting.
     * @param tradeSetting   The trade setting.
     */
    fun sendChatSetting(player: Player, publicSetting: Int, privateSetting: Int, tradeSetting: Int) {
        val buffer = IoBuffer(13, PacketHeader.BYTE)
        buffer.putString(player.name)
        buffer.put(publicSetting)
        buffer.put(privateSetting)
        buffer.put(tradeSetting)
        WorldCommunicator.getSession().write(buffer)
            ?: player.sendMessage("Privacy settings unavailable at the moment. Please try again later.")
    }
}
