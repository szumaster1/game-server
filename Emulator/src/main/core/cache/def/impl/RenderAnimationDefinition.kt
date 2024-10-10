package core.cache.def.impl

import core.cache.Cache
import core.game.world.GameWorld
import core.tools.Log
import java.lang.reflect.Array
import java.lang.reflect.Modifier
import java.nio.ByteBuffer

import core.api.log

/**
 * Holds definitions for render animations.
 * @author Jagex, Emperor
 */
class RenderAnimationDefinition {
    var turn180Animation = -1
    var anInt951 = -1
    var anInt952 = 0
    var turnCWAnimation = -1
    var anInt954 = -1
    var anInt955 = -1
    var anInt956 = 0
    var anInt957 = -1
    var anInt958 = -1
    var anIntArray959: IntArray? = null
    var anInt960 = -1
    var anInt961 = 0
    var anInt962 = -1
    var walkAnimationId = -1
    var anInt964 = -1
    var anInt965 = 0
    var anInt966 = -1
    var standAnimationIds: IntArray? = null
    var anInt969 = 0
    var anIntArray971: IntArray? = null
    var standAnimationId = -1
    var anInt973 = 0
    var anInt974 = -1
    var anInt975 = 0
    var runAnimationId = -1
    var anInt977 = -1
    var aBoolean978 = true
    var anIntArrayArray979 = Array(3) { IntArray(3) }
    var anInt980 = 0
    var turnCCWAnimation = -1
    var anInt983 = -1
    var anInt985 = -1
    var anInt986 = -1
    var anInt987 = -1
    var anInt988 = 0
    var anInt989 = -1
    var anInt990 = -1
    var anInt992 = 0
    var anInt993 = 0
    var anInt994 = 0

    /**
     * Parse the buffer to read animation definitions.
     */
    private fun parse(buffer: ByteBuffer) {
        while (true) {
            val opcode = buffer.get().toInt() and 0xFF
            if (opcode == 0) break
            parseOpcode(buffer, opcode)
        }
    }

    private fun parseOpcode(buffer: ByteBuffer, opcode: Int) {
        when (opcode) {
            1 -> {
                standAnimationId = buffer.short.toInt() and 0xFFFF
                walkAnimationId = buffer.short.toInt() and 0xFFFF
                if (standAnimationId == 65535) standAnimationId = -1
                if (walkAnimationId == 65535) walkAnimationId = -1
            }

            2 -> anInt974 = buffer.short.toInt() and 0xFFFF
            3 -> anInt987 = buffer.short.toInt() and 0xFFFF
            4 -> anInt986 = buffer.short.toInt() and 0xFFFF
            5 -> anInt977 = buffer.short.toInt() and 0xFFFF
            6 -> runAnimationId = buffer.short.toInt() and 0xFFFF
            7 -> anInt960 = buffer.short.toInt() and 0xFFFF
            8 -> anInt985 = buffer.short.toInt() and 0xFFFF
            9 -> anInt957 = buffer.short.toInt() and 0xFFFF
            26 -> {
                anInt973 = (buffer.get().toInt() and 0xFF) * 4
                anInt975 = (buffer.get().toInt() and 0xFF) * 4
            }

            27 -> {
                if (anIntArrayArray979 == null) anIntArrayArray979 = Array(12) { IntArray(6) }
                val i = buffer.get().toInt() and 0xFF
                for (i_1 in 0 until 6) {
                    anIntArrayArray979[i][i_1] = buffer.short.toInt()
                }
            }

            28 -> {
                anIntArray971 = IntArray(12)
                for (i in 0 until 12) {
                    anIntArray971!![i] = buffer.get().toInt() and 0xFF
                    if (anIntArray971!![i] == 255) anIntArray971!![i] = -1
                }
            }

            29 -> anInt992 = buffer.get().toInt() and 0xFF
            30 -> anInt980 = buffer.short.toInt() and 0xFFFF
            31 -> anInt988 = buffer.get().toInt() and 0xFF
            32 -> anInt961 = buffer.short.toInt() and 0xFFFF
            33 -> anInt956 = buffer.short.toInt()
            34 -> anInt993 = buffer.get().toInt() and 0xFF
            35 -> anInt969 = buffer.short.toInt() and 0xFFFF
            36 -> anInt965 = buffer.short.toInt()
            37 -> anInt951 = buffer.get().toInt() and 0xFF
            38 -> anInt958 = buffer.short.toInt() and 0xFFFF
            39 -> turn180Animation = buffer.short.toInt() and 0xFFFF
            40 -> turnCCWAnimation = buffer.short.toInt() and 0xFFFF
            41 -> turnCWAnimation = buffer.short.toInt() and 0xFFFF
            42 -> anInt954 = buffer.short.toInt() and 0xFFFF
            43 -> anInt955 = buffer.short.toInt() and 0xFFFF
            44 -> buffer.short.toInt()
            45 -> anInt964 = buffer.short.toInt() and 0xFFFF
            46 -> anInt966 = buffer.short.toInt() and 0xFFFF
            47 -> anInt989 = buffer.short.toInt() and 0xFFFF
            48 -> anInt983 = buffer.short.toInt() and 0xFFFF
            49 -> anInt952 = buffer.short.toInt() and 0xFFFF
            50 -> anInt990 = buffer.short.toInt() and 0xFFFF
            51 -> anInt962 = buffer.short.toInt() and 0xFFFF
            52 -> {
                val i = buffer.get().toInt() and 0xFF
                anIntArray959 = IntArray(i)
                standAnimationIds = IntArray(i)
                for (i_2 in 0 until i) {
                    standAnimationIds!![i_2] = buffer.short.toInt() and 0xFFFF
                    val i_3 = buffer.get().toInt() and 0xFF
                    anIntArray959!![i_2] = i_3
                    anInt994 += i_3
                }
            }

            53 -> aBoolean978 = false
            54 -> {
                val anInt1260 = (buffer.get().toInt() and 0xFF) shl 6
                val anInt1227 = (buffer.get().toInt() and 0xFF) shl 6
            }

            55 -> {
                val anIntArray1246 = IntArray(12)
                val i_14 = buffer.get().toInt() and 0xFF
                anIntArray1246[i_14] = buffer.short.toInt() and 0xFFFF
            }

            56 -> {
                val anIntArrayArray1217 = Array(12) { IntArray(3) }
                val i_12 = buffer.get().toInt() and 0xFF
                for (i_13 in 0 until 3) {
                    anIntArrayArray1217[i_12][i_13] = buffer.short.toInt()
                }
            }
        }
    }

    companion object {
        /**
         * Gets the render animation definitions for the given id.
         *
         * @param animId The render animation id.
         * @return The render animation definitions.
         */
        @JvmStatic
        fun forId(animId: Int): RenderAnimationDefinition? {
            if (animId == -1) {
                return null
            }
            val data = Cache.getIndexes()[2]?.getFileData(32, animId)
            val defs = RenderAnimationDefinition()
            if (data != null) {
                defs.parse(ByteBuffer.wrap(data))
            } else {
                log(
                    RenderAnimationDefinition::class.java,
                    Log.ERR,
                    "No definitions found for render animation $animId, size=${Cache.getIndexes()[2]?.getFilesSize(32)}!"
                )
            }
            return defs
        }

        /**
         * The entry point of application.
         *
         * @param args the input arguments
         * @throws Throwable the throwable
         */
        @Throws(Throwable::class)
        @JvmStatic
        fun main(vararg args: String) {
            GameWorld.prompt(false)
            val def = forId(1426)

            def?.let {
                it::class.java.declaredFields.forEach { f ->
                    if (!Modifier.isStatic(f.modifiers)) {
                        if (f.type.isArray) {
                            val array = f[it]
                            if (array != null) {
                                val length = Array.getLength(array)
                                print("[")
                                for (i in 0 until length) {
                                    print("${Array.get(array, i)}")
                                    if (i != length - 1) {
                                        print(",")
                                    }
                                }
                                println("] $f.name")
                            }
                        } else {
                            println("${f[it]} ${f.name}")
                        }
                    }
                }
            }
        }
    }
}
