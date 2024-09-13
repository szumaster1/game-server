package core.net.lobby

import core.game.world.GameWorld
import core.net.IoSession
import core.net.packet.IoBuffer
import java.nio.ByteBuffer

/**
 * Handles the world list.
 * @author Emperor
 */
object WorldList {
    /**
     * The value for Australia.
     */
    const val COUNTRY_AUSTRALIA = 16

    /**
     * The value for Belgium.
     */
    const val COUNTRY_BELGIUM = 22

    /**
     * The value for Brazil.
     */
    const val COUNTRY_BRAZIL = 31

    /**
     * The value for Canada.
     */
    const val COUNTRY_CANADA = 38

    /**
     * The value for Denmark.
     */
    const val COUNTRY_DENMARK = 58

    /**
     * The value for Finland.
     */
    const val COUNTRY_FINLAND = 69

    /**
     * The value for Ireland.
     */
    const val COUNTRY_IRELAND = 101

    /**
     * The value for Mexico.
     */
    const val COUNTRY_MEXICO = 152

    /**
     * The value for the Netherlands.
     */
    const val COUNTRY_NETHERLANDS = 161

    /**
     * The value for Norway.
     */
    const val COUNTRY_NORWAY = 162

    /**
     * The value for Sweden.
     */
    const val COUNTRY_SWEDEN = 191

    /**
     * The value for the UK.
     */
    const val COUNTRY_UK = 77

    /**
     * The value for USA.
     */
    const val COUNTRY_USA = 225

    /**
     * If the world is free to play.
     */
    const val FLAG_NON_MEMBERS = 0

    /**
     * If the world is a members world.
     */
    const val FLAG_MEMBERS = 1

    /**
     * If the world is a quick chat world
     */
    const val FLAG_QUICK_CHAT = 2

    /**
     * If the world is a PvP-world.
     */
    const val FLAG_PVP = 4

    /**
     * If the world is a lootshare world.
     */
    const val FLAG_LOOTSHARE = 8

    /**
     * A list holding all the currently loaded worlds.
     */
    private val WORLD_LIST = mutableListOf<WorldDefinition>()

    /**
     * The last update time stamp (in server ticks).
     */
    private var updateStamp = 0

    /**
     * Populates the world list.
     */
    init {
        addWorld(
            WorldDefinition(
                1,
                0,
                FLAG_MEMBERS or FLAG_LOOTSHARE,
                "2009Scape Classic",
                "127.0.0.1",
                "Anywhere, USA",
                COUNTRY_USA
            )
        )
    }

    /**
     * Adds a world to the world list.
     *
     * @param def The world definitions.
     */
    fun addWorld(def: WorldDefinition) {
        WORLD_LIST.add(def)
        flagUpdate()
    }

    /**
     * Gets the packet to update the world list in the lobby.
     *
     * @return The OutgoingPacket to write.
     */
    fun sendUpdate(session: IoSession, updateStamp: Int) {
        val buf = ByteBuffer.allocate(1024)
        buf.put(0.toByte())
        buf.putShort(0)
        buf.put(1.toByte())
        val buffer = IoBuffer()
        if (updateStamp != WorldList.updateStamp) {
            buf.put(1.toByte()) // Indicates an update occurred.
            putWorldListinfo(buffer)
        } else {
            buf.put(0.toByte())
        }
        putPlayerInfo(buffer)
        if (buffer.toByteBuffer().position() > 0) {
            buf.put(buffer.toByteBuffer().flip())
        }
        buf.putShort(1, (buf.position() - 3).toShort())
        session.queue(buf.flip())
    }

    /**
     * Adds the world configuration on the packet.
     *
     * @param buffer The current packet.
     */
    private fun putWorldListinfo(buffer: IoBuffer) {
        buffer.putSmart(WORLD_LIST.size)
        putCountryInfo(buffer)
        buffer.putSmart(0)
        buffer.putSmart(WORLD_LIST.size)
        buffer.putSmart(WORLD_LIST.size)
        for (w in WORLD_LIST) {
            buffer.putSmart(w.worldId)
            buffer.put(w.location)
            buffer.putInt(w.flag)
            buffer.putJagString(w.activity)
            buffer.putJagString(w.ip)
        }
        buffer.putInt(updateStamp)
    }

    /**
     * Adds the world status on the packet.
     *
     * @param buffer The current packet.
     */
    private fun putPlayerInfo(buffer: IoBuffer) {
        for (w in WORLD_LIST) {
            buffer.putSmart(w.worldId)
            buffer.putShort(w.getPlayerCount())
        }
    }

    /**
     * Sets the countries for each world.
     *
     * @param buffer The current packet.
     */
    private fun putCountryInfo(buffer: IoBuffer) {
        for (w in WORLD_LIST) {
            buffer.putSmart(w.country)
            buffer.putJagString(w.region)
        }
    }

    /**
     * Gets the update stamp.
     *
     * @return the updateStamp.
     */
    fun getUpdateStamp(): Int {
        return updateStamp
    }

    /**
     * Flag update.
     */
    fun flagUpdate() {
        updateStamp = GameWorld.ticks
    }
}
