package core.cache.def.impl

import core.Configuration
import core.cache.Cache
import java.nio.ByteBuffer

/**
 * The definitions for player clothing/look.
 * @author Emperor
 */
class ClothDefinition {
    /**
     * The equipment slot.
     */
    private var equipmentSlot: Int = 0

    /**
     * The model ids.
     */
    private var modelIds: IntArray? = null

    /**
     * Unknown boolean.
     */
    private var unknownBool: Boolean = false

    /**
     * Original colors.
     */
    private var originalColors: IntArray? = null

    /**
     * The colors to change to.
     */
    private var modifiedColors: IntArray? = null

    /**
     * Original texture colors.
     */
    private var originalTextureColors: IntArray? = null

    /**
     * Texture colors to change to.
     */
    private var modifiedTextureColors: IntArray? = null

    /**
     * Other model ids(?)
     */
    private var models: IntArray = intArrayOf(-1, -1, -1, -1, -1)

    companion object {

        /**
         * Gets the definitions for the given cloth id.
         *
         * @param clothId the clothing id.
         * @return the definition.
         */
        fun forId(clothId: Int): ClothDefinition {
            val def = ClothDefinition()
            val bs = Cache.getIndexes()[2].getFileData(3, clothId)
            if (bs != null) {
                def.load(ByteBuffer.wrap(bs))
            }
            return def
        }

        /**
         * The main method.
         *
         * @param args the arguments cast on runtime.
         */
        @JvmStatic
        fun main(vararg args: String) {
            try {
                Cache.init(Configuration.CACHE_PATH)
            } catch (e: Throwable) {
                e.printStackTrace()
            }
            val length = Cache.getIndexes()[2].getFilesSize(3)

            for (i in 0 until length) {
                val def = forId(i)
            }
        }
    }

    /**
     * Loads the definitions.
     *
     * @param buffer the buffer.
     */
    fun load(buffer: ByteBuffer) {
        var opcode: Int
        while (buffer.get().toInt() and 0xFF != 0) {
            opcode = buffer.get().toInt() and 0xFF
            parse(opcode, buffer)
        }
    }

    /**
     * Parses an opcode.
     *
     * @param opcode the opcode.
     * @param buffer the buffer to read the data from.
     */
    private fun parse(opcode: Int, buffer: ByteBuffer) {
        when (opcode) {
            1 -> equipmentSlot = buffer.get().toInt() and 0xFF
            2 -> {
                val length = buffer.get().toInt() and 0xFF
                modelIds = IntArray(length)
                for (i in 0 until length) {
                    modelIds!![i] = buffer.short.toInt() and 0xFFFF
                }
            }

            3 -> unknownBool = true
            40 -> {
                val length = buffer.get().toInt() and 0xFF
                originalColors = IntArray(length)
                modifiedColors = IntArray(length)
                for (i in 0 until length) {
                    originalColors!![i] = buffer.short.toInt()
                    modifiedColors!![i] = buffer.short.toInt()
                }
            }

            41 -> {
                val length = buffer.get().toInt() and 0xFF
                originalTextureColors = IntArray(length)
                modifiedTextureColors = IntArray(length)
                for (i in 0 until length) {
                    originalTextureColors!![i] = buffer.short.toInt()
                    modifiedTextureColors!![i] = buffer.short.toInt()
                }
            }

            in 60..69 -> models[opcode - 60] = buffer.short.toInt() and 0xFFFF
        }
    }

    /**
     * Gets the unknown.
     *
     * @return the unknown.
     */
    fun getUnknown(): Int {
        return equipmentSlot
    }

    /**
     * Get the model ids.
     *
     * @return the model ids.
     */
    fun getModelIds(): IntArray? {
        return modelIds
    }

    /**
     * Gets the unknownBool.
     *
     * @return the unknownBool.
     */
    fun isUnknownBool(): Boolean {
        return unknownBool
    }

    /**
     * Get the original color int.
     *
     * @return the int.
     */
    fun getOriginalColors(): IntArray? {
        return originalColors
    }

    /**
     * Get the modified color int.
     *
     * @return the int.
     */
    fun getModifiedColors(): IntArray? {
        return modifiedColors
    }

    /**
     * Get the original texture colors int.
     *
     * @return the int.
     */
    fun getOriginalTextureColors(): IntArray? {
        return originalTextureColors
    }

    /**
     * Get the modified texture colors int.
     *
     * @return the int.
     */
    fun getModifiedTextureColors(): IntArray? {
        return modifiedTextureColors
    }

    /**
     * Get the model int.
     *
     * @return the int.
     */
    fun getModels(): IntArray {
        return models
    }
}
