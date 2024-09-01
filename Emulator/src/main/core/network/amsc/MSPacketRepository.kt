package core.network.amsc;

import core.game.node.entity.player.Player;
import core.game.system.communication.ClanRank;
import core.network.packet.IoBuffer;
import core.network.packet.PacketHeader;

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
        // Create a new IoBuffer with a size of 5 if block is true, otherwise 4
        val buffer = IoBuffer(if (block) 5 else 4, PacketHeader.BYTE);
        buffer.putString(username); // Write the username to the buffer
        buffer.putString(contact); // Write the contact's username to the buffer
        rank?.let {
            buffer.put(2); // Write 2 to the buffer to indicate that we're updating the clan rank
            buffer.put(it.ordinal); // Write the ordinal value of the clan rank to the buffer
        } ?: buffer.put(if (remove) 1 else 0); // Write 1 to the buffer if remove is true, otherwise 0
        WorldCommunicator.getSession().write(buffer); // Send the buffer to the session
    }

    /**
     * Sends the clan rename packet.
     *
     * @param player   The player.
     * @param clanName The clan name.
     */
    fun sendClanRename(player: Player, clanName: String) {
        val buffer = IoBuffer(7, PacketHeader.BYTE);
        buffer.putString(player.name); // Write the player's name to the buffer
        buffer.putString(clanName); // Write the clan name to the buffer
        WorldCommunicator.getSession().write(buffer); // Send the buffer to the session
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
            return;
        }
        val buffer = IoBuffer(8, PacketHeader.BYTE);
        buffer.putString(player.name); // Write the player's name to the buffer
        buffer.put(type); // Write the setting type to the buffer
        rank?.let { buffer.put(it.ordinal) }; // Write the ordinal value of the clan rank to the buffer if rank is not null
        WorldCommunicator.getSession().write(buffer); // Send the buffer to the session
    }

    /**
     * Sends the kicking a clan member packet.
     *
     * @param username The player's username.
     * @param name     The name.
     */
    fun sendClanKick(username: String, name: String) {
        val buffer = IoBuffer(9, PacketHeader.BYTE);
        buffer.putString(username); // Write the player's username to the buffer
        buffer.putString(name); // Write the name to the buffer
        WorldCommunicator.getSession().write(buffer); // Send the buffer to the session
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
        val buffer = IoBuffer(13, PacketHeader.BYTE);
        buffer.putString(player.name); // Write the player's name to the buffer
        buffer.put(publicSetting); // Write the public chat setting to the buffer
        buffer.put(privateSetting); // Write the private chat setting to the buffer
        buffer.put(tradeSetting); // Write the trade setting to the buffer
        WorldCommunicator.getSession().write(buffer) // Send the buffer to the session
            ?: player.sendMessage("Privacy settings unavailable at the moment. Please try again later.");
    }
}
