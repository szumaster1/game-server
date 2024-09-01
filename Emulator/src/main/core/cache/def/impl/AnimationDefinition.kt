package core.cache.def.impl

import core.cache.Cache
import core.cache.misc.buffer.ByteBufferUtils
import java.nio.ByteBuffer

/**
 * Represents an animation's definitions.
 * @author Emperor
 */
class AnimationDefinition {

    var anInt2136: Int = 0
    var anInt2137: Int = 0
    var anIntArray2139: IntArray? = null
    var anInt2140: Int = 0
    var aBoolean2141: Boolean = false
    var anInt2142: Int = 0

    /**
     * The emote item.
     */
    var emoteItem: Int = 0
    var anInt2144: Int = -1

    /**
     * The handled sounds.
     */
    var handledSounds: Array<IntArray>? = null
    var aBooleanArray2149: BooleanArray? = null
    var anIntArray2151: IntArray? = null
    var aBoolean2152: Boolean = false

    /**
     * The durations.
     */
    var durations: IntArray? = null
    var anInt2155: Int = 0
    var aBoolean2158: Boolean = false
    var aBoolean2159: Boolean = false
    var anInt2162: Int = 0
    var anInt2163: Int = 0

    /**
     * The new header.
     */
    var newHeader: Boolean = false

    /**
     * The sound min delay.
     */
    var soundMinDelay: IntArray? = null

    /**
     * The sound max delay.
     */
    var soundMaxDelay: IntArray? = null
    var anIntArray1362: IntArray? = null

    /**
     * The effect 2 sound.
     */
    var effect2Sound: Boolean = false

    companion object {
        private val animDefs: MutableMap<Int, AnimationDefinition> = HashMap()

        /**
         * For id animation definition.
         *
         * @param emoteId the emote id
         * @return the animation definition
         */
        @JvmStatic
        fun forId(emoteId: Int): AnimationDefinition? {
            return try {
                val defs = animDefs[emoteId]
                if (defs != null) {
                    return defs
                }
                val data = Cache.getIndexes()[20].getFileData(emoteId shr 7, emoteId and 0x7f)
                val newDefs = AnimationDefinition()
                if (data != null) {
                    newDefs.readValueLoop(ByteBuffer.wrap(data))
                }
                newDefs.method2394()
                animDefs[emoteId] = newDefs
                newDefs
            } catch (t: Throwable) {
                null
            }
        }
    }

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
     * Gets the duration of this animation in milliseconds.
     *
     * @return The duration.
     */
    fun getDuration(): Int {
        if (durations == null) {
            return 0
        }
        var duration = 0
        for (i in durations!!) {
            if (i > 100) {
                continue
            }
            duration += i * 20
        }
        return duration
    }

    /**
     * Gets cycles.
     *
     * @return the cycles
     */
    fun getCycles(): Int {
        if (durations == null) return 0
        var duration = 0
        for (i in durations!!) {
            duration += i
        }
        return duration
    }

    /**
     * Gets the duration of this animation in (600ms) ticks.
     *
     * @return The duration in ticks.
     */
    fun getDurationTicks(): Int {
        val ticks = getDuration() / 600
        return Math.max(ticks, 1)
    }

    private fun readValues(buffer: ByteBuffer, opcode: Int) {
        when (opcode) {
            1 -> {
                val length = buffer.short.toInt() and 0xFFFF
                durations = IntArray(length)
                for (i in 0 until length) {
                    durations!![i] = buffer.short.toInt() and 0xFFFF
                }
                anIntArray2139 = IntArray(length)
                for (i in 0 until length) {
                    anIntArray2139!![i] = buffer.short.toInt() and 0xFFFF
                }
                for (i in 0 until length) {
                    anIntArray2139!![i] = (buffer.short.toInt() and 0xFFFF shl 16) + anIntArray2139!![i]
                }
            }

            2 -> {}
            3 -> {
                aBooleanArray2149 = BooleanArray(256)
                val length = buffer.get().toInt() and 0xFF
                for (i in 0 until length) {
                    aBooleanArray2149!![buffer.get().toInt() and 0xFF] = true
                }
            }

            4 -> aBoolean2152 = true
            5 -> anInt2142 = buffer.get().toInt() and 0xFF
            6 -> {}
            7 -> emoteItem = buffer.short.toInt() and 0xFFFF
            8 -> anInt2136 = buffer.get().toInt() and 0xFF
            9 -> {}
            10 -> anInt2144 = buffer.short.toInt() and 0xFFFF
            11 -> anInt2155 = buffer.get().toInt() and 0xFF
            12 -> {
                val i = buffer.get().toInt() and 0xFF
                anIntArray2151 = IntArray(i)
                for (i_19_ in 0 until i) {
                    anIntArray2151!![i_19_] = buffer.short.toInt() and 0xFFFF
                }
                for (i_20_ in 0 until i) {
                    anIntArray2151!![i_20_] = (buffer.short.toInt() and 0xFFFF shl 16) + anIntArray2151!![i_20_]
                }
            }

            13 -> {
                val i = buffer.short.toInt() and 0xFFFF
                handledSounds = Array(i) { IntArray(0) }
                for (i_21_ in 0 until i) {
                    val i_22_ = buffer.get().toInt() and 0xFF
                    if (i_22_ > 0) {
                        handledSounds!![i_21_] = IntArray(i_22_)
                        handledSounds!![i_21_][0] = ByteBufferUtils.getMedium(buffer)
                        for (i_23_ in 1 until i_22_) {
                            handledSounds!![i_21_][i_23_] = buffer.short.toInt() and 0xFFFF
                        }
                    }
                }
            }

            14 -> aBoolean2141 = true
            else -> {}
        }
    }

    /**
     * Method 2394.
     */
    fun method2394() {
        if (anInt2140 == -1) {
            anInt2140 = if (aBooleanArray2149 == null) 0 else 2
        }
        if (anInt2162 == -1) {
            anInt2162 = if (aBooleanArray2149 == null) 0 else 2
        }
    }

    /**
     * Instantiates a new Animation definition.
     */
    init {
        anInt2136 = 99
        emoteItem = -1
        anInt2140 = -1
        aBoolean2152 = false
        anInt2142 = 5
        aBoolean2159 = false
        anInt2163 = -1
        anInt2155 = 2
        aBoolean2158 = false
        anInt2162 = -1
    }
}
