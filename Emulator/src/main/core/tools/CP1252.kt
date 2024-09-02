package core.tools

import java.nio.ByteBuffer
import java.nio.CharBuffer
import java.nio.charset.Charset
import java.nio.charset.CharsetDecoder
import java.nio.charset.CharsetEncoder
import java.nio.charset.CoderResult

/**
 * CP1252 Charset implementation
 * This object represents the CP1252 character set, which is a single-byte character encoding.
 */
object CP1252 : Charset("Cp1252", null) {

    private val CODE_PAGE = charArrayOf(
        '\u20AC', '\u0000', '\u201A', '\u0192', '\u201E', '\u2026', '\u2020', '\u2021',
        '\u02C6', '\u2030', '\u0160', '\u2039', '\u0152', '\u0000', '\u017D', '\u0000',
        '\u0000', '\u2018', '\u2019', '\u201C', '\u201D', '\u2022', '\u2013', '\u2014',
        '\u02DC', '\u2122', '\u0161', '\u203A', '\u0153', '\u0000', '\u017E', '\u0178'
    )

    private val ENCODE_TABLE = ByteArray(65536)
    private val DECODE_TABLE = CharArray(256)

    private const val REPLACEMENT_CHAR = '\uFFFD'
    private const val REPLACEMENT_BYTE = 0x003F.toByte()

    init {
        for (b in 0 until 256) {
            val c = if (b in 0x80 until 0xA0) CODE_PAGE[b and 0x7F] else b.toChar()
            if (c != '\u0000') {
                ENCODE_TABLE[c.code] = b.toByte()
                DECODE_TABLE[b] = c
            }
        }
    }

    /**
     * Decode a single byte to a character
     *
     * @param byte The byte to decode
     * @return The decoded character or a replacement character if invalid
     */
    @JvmStatic
    fun decode(byte: Byte): Char {
        return DECODE_TABLE[byte.toInt() and 0xFF].takeIf { it != '\u0000' } ?: REPLACEMENT_CHAR
    }

    /**
     * Encode a single character to a byte
     *
     * @param char The character to encode
     * @return The encoded byte or a replacement byte if invalid
     */
    fun encode(char: Char): Byte {
        return ENCODE_TABLE[char.code].takeIf { it.toInt() != 0 } ?: REPLACEMENT_BYTE
    }

    /**
     * Get a character from a byte
     *
     * @param value The byte value
     * @return The corresponding character
     */
    @JvmStatic
    fun getFromByte(value: Byte): Char {
        val out = value.toInt() and 0xff
        require(out != 0) { "Non cp1252 character 0x${out.toString(16)} provided" }
        return if (out in 128 until 160) {
            CODE_PAGE[out - 128].takeIf { it.code != 0 } ?: 63.toChar()
        } else {
            out.toChar()
        }
    }

    /**
     * Check if the charset contains another charset
     *
     * @param cs The charset to check
     */
    override fun contains(cs: Charset) = Charsets.US_ASCII.contains(cs) || cs is CP1252

    /**
     * Create a new encoder for the charset
     *
     */
    override fun newEncoder() = object : CharsetEncoder(this, 1F, 1F, byteArrayOf(REPLACEMENT_BYTE)) {
        override fun encodeLoop(input: CharBuffer, output: ByteBuffer): CoderResult {
            while (input.hasRemaining()) {
                if (!output.hasRemaining()) return CoderResult.OVERFLOW

                val char = input.get()
                val byte = ENCODE_TABLE[char.code]

                if (byte.toInt() == 0) {
                    input.position(input.position() - 1)
                    return CoderResult.unmappableForLength(1)
                }

                output.put(byte)
            }
            return CoderResult.UNDERFLOW
        }
    }

    // Create a new decoder for the charset.
    override fun newDecoder() = object : CharsetDecoder(this, 1F, 1F) {
        init {
            replaceWith(REPLACEMENT_CHAR.toString())
        }

        /**
         * Decode loop for converting bytes to characters
         *
         * @param input The byte buffer to decode
         * @param output The character buffer to fill
         * @return The result of the decoding operation
         */
        override fun decodeLoop(input: ByteBuffer, output: CharBuffer): CoderResult {
            while (input.hasRemaining()) {
                if (!output.hasRemaining()) return CoderResult.OVERFLOW

                val byte = input.get()
                val char = DECODE_TABLE[byte.toInt() and 0xFF]

                if (char == '\u0000') {
                    input.position(input.position() - 1)
                    return CoderResult.unmappableForLength(1)
                }

                output.put(char)
            }
            return CoderResult.UNDERFLOW
        }
    }
}
