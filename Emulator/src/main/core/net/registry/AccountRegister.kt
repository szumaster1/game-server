package core.net.registry

import core.Configuration
import core.api.log
import core.auth.UserAccountInfo
import core.cache.misc.buffer.ByteBufferUtils
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.net.Constants
import core.net.IoSession
import core.net.packet.incoming.Login
import core.tools.Log
import java.nio.ByteBuffer
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Handles the registry of new accounts.
 * @author Vexia
 */
object AccountRegister {

    /**
     * The pattern compiler.
     */
    private val PATTERN: Pattern = Pattern.compile("[a-z0-9_]{1,12}")

    /**
     * Reads the incoming opcode of an account register.
     *
     * @param [session] the session.
     * @param [opcode]  the opcode.
     * @param [buffer]  the buffer.
     */
    @JvmStatic
    fun read(session: IoSession, opcode: Int, buffer: ByteBuffer) {
        var day: Int
        var month: Int
        var year: Int
        var country: Int
        val info = UserAccountInfo.createDefault()

        when (opcode) {
            147 -> { // details
                day = buffer.get().toInt()
                month = buffer.get().toInt()
                year = buffer.short.toInt()
                country = buffer.short.toInt()
                response(session, RegistryResponse.SUCCESS)
            }
            186 -> { // username
                val username = ByteBufferUtils.getString(buffer).replace(" ", "_").lowercase().replace("|", "")
                info.username = username
                if (username.length <= 0 || username.length > 12) {
                    response(session, RegistryResponse.INVALID_USERNAME)
                    return
                }
                if (invalidUsername(username)) {
                    response(session, RegistryResponse.INVALID_USERNAME)
                    return
                }
                if (!GameWorld.authenticator.canCreateAccountWith(info)) {
                    response(session, RegistryResponse.NOT_AVAILBLE_USER)
                    return
                }
                response(session, RegistryResponse.SUCCESS)
            }
            36 -> { // register details
                buffer.get()
                val decryptedBuffer = Login.decryptRSABuffer(buffer, Configuration.EXPONENT, Configuration.MODULUS)
                if (decryptedBuffer.get() != 10.toByte()) {
                    log(AccountRegister::class.java, Log.ERR, "Decryption failed during registration :(")
                    response(session, RegistryResponse.CANNOT_CREATE)
                    return
                }
                decryptedBuffer.short
                val revision = decryptedBuffer.short.toInt()

                if (revision != Constants.REVISION) {
                    response(session, RegistryResponse.CANNOT_CREATE)
                    return
                }
                val name = ByteBufferUtils.getString(decryptedBuffer).replace(" ", "_").lowercase().replace("|", "")
                decryptedBuffer.getInt()
                val password = ByteBufferUtils.getString(decryptedBuffer)
                info.username = name
                info.password = password
                if (password.length < 5 || password.length > 20) {
                    response(session, RegistryResponse.INVALID_PASS_LENGTH)
                    return
                }
                if (password == name) {
                    response(session, RegistryResponse.PASS_SIMILAR_TO_USER)
                    return
                }
                if (invalidUsername(name)) {
                    response(session, RegistryResponse.INVALID_USERNAME)
                    return
                }
                decryptedBuffer.getInt()
                decryptedBuffer.short
                day = decryptedBuffer.get().toInt()
                month = decryptedBuffer.get().toInt()
                decryptedBuffer.getInt()
                year = decryptedBuffer.short.toInt()
                country = decryptedBuffer.short.toInt()
                decryptedBuffer.getInt()
                if (!GameWorld.authenticator.canCreateAccountWith(info)) {
                    response(session, RegistryResponse.CANNOT_CREATE)
                    return
                }
                GameWorld.authenticator.createAccountWith(info)
                GameWorld.Pulser.submit(object : Pulse() {
                    override fun pulse(): Boolean {
                        response(session, RegistryResponse.SUCCESS)
                        return true
                    }
                })
            }
            else -> {
                log(AccountRegister::class.java, Log.ERR, "Unhandled account registry opcode = $opcode")
            }
        }
    }

    /**
     * Send a response to the client.
     *
     * @param [session]  the session.
     * @param [response] the response.
     */
    private fun response(session: IoSession, response: RegistryResponse) {
        val buf = ByteBuffer.allocate(100)
        buf.put(response.id.toByte())
        session.queue(buf.flip())
    }

    /**
     * Check if a username is invalid.
     *
     * @param [username] the username.
     * @return `true` if the username is invalid, `false` otherwise.
     */
    fun invalidUsername(username: String): Boolean {
        val matcher: Matcher = PATTERN.matcher(username)
        return !matcher.matches()
    }
}
