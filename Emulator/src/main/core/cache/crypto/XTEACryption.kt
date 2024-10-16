package core.cache.crypto

import java.nio.ByteBuffer

/**
 * Holds XTEA cryption methods.
 * @author Emperor
 */
object XTEACryption {
    /**
     * The delta value
     */
    private const val DELTA = -1640531527

    /**
     * The sum.
     */
    private const val SUM = -957401312

    /**
     * The amount of "cryption cycles".
     */
    private const val NUM_ROUNDS = 32
    /**
     * Decrypts the buffer data.
     *
     * @param keys   The keys.
     * @param buffer The buffer to decrypt.
     * @param offset The offset of the data to decrypt.
     * @param length The length.
     * @return The decrypted data.
     */
    /**
     * Decrypts the contents of the buffer.
     *
     * @param keys   The cryption keys.
     * @param buffer The buffer.
     * @return the byte buffer
     */
    @JvmStatic
    @JvmOverloads
    fun decrypt(
        keys: IntArray,
        buffer: ByteBuffer,
        offset: Int = buffer.position(),
        length: Int = buffer.limit()
    ): ByteBuffer {
        val numBlocks = (length - offset) / 8
        val block = IntArray(2)
        for (i in 0 until numBlocks) {
            val index = i * 8 + offset
            block[0] = buffer.getInt(index)
            block[1] = buffer.getInt(index + 4)
            decipher(keys, block)
            buffer.putInt(index, block[0])
            buffer.putInt(index + 4, block[1])
        }
        return buffer
    }

    /**
     * Deciphers the values.
     *
     * @param keys  The cryption key.
     * @param block The values to decipher.
     */
    private fun decipher(keys: IntArray, block: IntArray) {
        var sum = SUM.toLong()
        repeat(NUM_ROUNDS) {
            block[1] -= (keys[(sum and 0x1933L ushr 11).toInt()] + sum xor (block[0] + (block[0] shl 4 xor (block[0] ushr 5))).toLong()).toInt()
            sum -= DELTA.toLong()
            block[0] -= (((block[1] shl 4 xor (block[1] ushr 5)) + block[1]).toLong() xor keys[(sum and 0x3L).toInt()] + sum).toInt()
        }
    }
    /**
     * Encrypts the buffer data.
     *
     * @param keys   The keys.
     * @param buffer The buffer to encrypt.
     * @param offset The offset of the data to encrypt.
     * @param length The length.
     * @return The encrypted data.
     */
    /**
     * Encrypts the contents of the byte buffer.
     *
     * @param keys   The cryption keys.
     * @param buffer The buffer to encrypt.
     */
    @JvmOverloads
    fun encrypt(keys: IntArray, buffer: ByteBuffer, offset: Int = buffer.position(), length: Int = buffer.limit()) {
        val numBlocks = (length - offset) / 8
        val block = IntArray(2)
        for (i in 0 until numBlocks) {
            val index = i * 8 + offset
            block[0] = buffer.getInt(index)
            block[1] = buffer.getInt(index + 4)
            encipher(keys, block)
            buffer.putInt(index, block[0])
            buffer.putInt(index + 4, block[1])
        }
    }

    /**
     * Enciphers the values of the block.
     *
     * @param keys  The cryption keys.
     * @param block The block to encipher.
     */
    private fun encipher(keys: IntArray, block: IntArray) {
        var sum: Long = 0
        repeat(NUM_ROUNDS) {
            block[0] += (((block[1] shl 4 xor (block[1] ushr 5)) + block[1]).toLong() xor keys[(sum and 0x3L).toInt()] + sum).toInt()
            sum += DELTA.toLong()
            block[1] += (keys[(sum and 0x1933L ushr 11).toInt()] + sum xor (block[0] + (block[0] shl 4 xor (block[0] ushr 5))).toLong()).toInt()
        }
    }
}