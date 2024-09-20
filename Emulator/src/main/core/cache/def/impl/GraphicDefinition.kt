package core.cache.def.impl

import core.Configuration
import core.cache.Cache
import java.nio.ByteBuffer

/**
 * Graphic definition.
 */
class GraphicDefinition {

    private var aShortArray1435: ShortArray? = null
    private var aShortArray1438: ShortArray? = null
    private var anInt1440: Int = 0
    private var aBoolean1442: Boolean = false
    private var defaultModel: Int = 0
    private var anInt1446: Int = 0
    private var aBoolean1448: Boolean = false
    private var anInt1449: Int = 0
    private var animationId: Int = 0
    private var anInt1451: Int = 0
    private var graphicsId: Int = 0
    private var anInt1454: Int = 0
    private var aShortArray1455: ShortArray? = null
    private var aShortArray1456: ShortArray? = null
    private var byteValue: Byte = 0
    private var intValue: Int = -1


    companion object {
        private var graphicDefinitions: MutableMap<Int, GraphicDefinition> = HashMap()

        /**
         * For id graphic definition.
         *
         * @param [gfxId] the gfx id.
         * @return [GraphicDefinition].
         */
        fun forId(gfxId: Int): GraphicDefinition {
            val def = graphicDefinitions[gfxId]
            if (def != null) {
                return def
            }
            val data = Cache.getIndexes()[21].getFileData(gfxId shr 8, gfxId and 0xff)
            val newDef = GraphicDefinition()
            newDef.graphicsId = gfxId
            data?.let {
                newDef.readValueLoop(ByteBuffer.wrap(it))
            }
            graphicDefinitions[gfxId] = newDef
            return newDef
        }

        @JvmStatic
        fun main(vararg s: String) {
            try {
                Cache.init(Configuration.CACHE_PATH)
            } catch (e: Throwable) {
                e.printStackTrace()
            }

            val d = forId(803)

            for (i in 0 until 5000) {
                val def = forId(i)
                if (def == null) {
                    continue
                }
                if ((def.animationId in 2001..2199) || (def.defaultModel in 1300..1499)) {

                }
            }
        }
    }

    /**
     * Reads values from the provided ByteBuffer.
     *
     * @param buffer the buffer containing graphic data.
     */
    private fun readValueLoop(buffer: ByteBuffer) {
        while (true) {
            val opcode = buffer.get().toInt() and 0xFF
            if (opcode == 0) {
                break
            }
            readValues(buffer, opcode)
        }
    }

    /**
     * Read values based on the opcode from the buffer.
     *
     * @param buffer the buffer containing graphic data.
     * @param opcode the opcode indicating which value to read.
     */
    fun readValues(buffer: ByteBuffer, opcode: Int) {
        when (opcode) {
            1 -> defaultModel = buffer.short.toInt()
            2 -> animationId = buffer.short.toInt()
            4 -> anInt1446 = buffer.short.toInt() and 0xFFFF
            5 -> anInt1449 = buffer.short.toInt() and 0xFFFF
            7 -> anInt1440 = buffer.get().toInt() and 0xFF
            8 -> anInt1451 = buffer.get().toInt() and 0xFF
            9 -> { /* No action */ }
            10 -> aBoolean1448 = true
            11 -> byteValue = 1
            12 -> byteValue = 4
            13 -> byteValue = 5
            14 -> {
                byteValue = 2
                intValue = (buffer.get().toInt() and 0xFF) * 256
            }

            15 -> {
                byteValue = 3
                intValue = buffer.short.toInt() and 0xFFFF
            }

            16 -> {
                byteValue = 3
                intValue = buffer.int
            }

            40 -> {
                val i = buffer.get().toInt() and 0xFF
                aShortArray1455 = ShortArray(i)
                aShortArray1435 = ShortArray(i)
                for (i_0_ in 0 until i) {
                    aShortArray1455!![i_0_] = (buffer.short.toInt() and 0xFFFF).toShort()
                    aShortArray1435!![i_0_] = (buffer.short.toInt() and 0xFFFF).toShort()
                }
            }

            41 -> {
                val i = buffer.get().toInt() and 0xFF
                aShortArray1438 = ShortArray(i)
                aShortArray1456 = ShortArray(i)
                for (i_1_ in 0 until i) {
                    aShortArray1438!![i_1_] = (buffer.short.toInt() and 0xFFFF).toShort()
                    aShortArray1456!![i_1_] = (buffer.short.toInt() and 0xFFFF).toShort()
                }
            }

            else -> {
                anInt1454 = buffer.short.toInt() and 0xFFFF
            }
        }
    }

    init {
        byteValue = 0
        intValue = -1
        anInt1446 = 128
        aBoolean1442 = false
        anInt1449 = 128
        anInt1451 = 0
        animationId = -1
        anInt1454 = 0
        anInt1440 = 0
    }
}
