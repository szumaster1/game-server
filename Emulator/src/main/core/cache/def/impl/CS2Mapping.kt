package core.cache.def.impl

import core.cache.Cache
import core.cache.misc.buffer.ByteBufferUtils
import core.game.world.GameWorld
import java.io.BufferedWriter
import java.nio.ByteBuffer
import java.nio.file.Files
import java.nio.file.Paths

/**
 * The CS2 mapping.
 * @author Emperor
 */
class CS2Mapping(private val scriptId: Int) {

    private var unknown: Int = 0
    private var unknown1: Int = 0
    private var defaultString: String? = null
    private var defaultInt: Int = 0
    var map: HashMap<Int, Any>? = null
    private var array: Array<Any?>? = null

    companion object {
        /**
         * The CS2 mappings.
         */
        private val maps: MutableMap<Int, CS2Mapping> = mutableMapOf()

        @JvmStatic
        fun main(vararg args: String) {
            GameWorld.prompt(false)
            BufferedWriter(Files.newBufferedWriter(Paths.get("./cs2.txt"))).use { bw ->
                for (i in 0 until 10000) {
                    forId(i)?.let { mapping ->
                        mapping.map?.let {
                            bw.append("ScriptAPI - $i [")
                            it.keys.forEach { index ->
                                bw.append("${it[index]}: $index ")
                            }
                            bw.append("]")
                            bw.newLine()
                        }
                    }
                }
                bw.flush()
            }
        }

        /**
         * Gets the mapping for the given script id.
         *
         * @param scriptId The script id.
         * @return The mapping.
         */
        fun forId(scriptId: Int): CS2Mapping? {
            return maps[scriptId] ?: run {
                val bs = Cache.getIndexes()[17].getFileData(scriptId shr 8, scriptId and 0xFF)
                bs?.let {
                    CS2Mapping(scriptId).apply {
                        load(ByteBuffer.wrap(it))
                        maps[scriptId] = this
                    }
                }
            }
        }
    }

    /**
     * Loads the mapping data.
     *
     * @param buffer The buffer to read the data from.
     */
    private fun load(buffer: ByteBuffer) {
        while (buffer.get().toInt() and 0xFF != 0) {
            when (val opcode = buffer.get().toInt() and 0xFF) {
                1 -> unknown = buffer.get().toInt() and 0xFF
                2 -> unknown1 = buffer.get().toInt() and 0xFF
                3 -> defaultString = ByteBufferUtils.getString(buffer)
                4 -> defaultInt = buffer.getInt()
                5, 6 -> {
                    val size = buffer.getShort().toInt() and 0xFFFF
                    map = HashMap(size)
                    array = arrayOfNulls(size)

                    for (i in 0 until size) {
                        val key = buffer.getInt()
                        if (opcode == 5) {
                            val string = ByteBufferUtils.getString(buffer)
                            array!![i] = string
                            map!![key] = string
                        } else {
                            val valInt = buffer.getInt()
                            array!![i] = valInt
                            map!![key] = valInt
                        }
                    }
                }
            }
        }
    }
}
