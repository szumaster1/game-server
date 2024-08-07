package core.cache.def.impl

import core.api.log
import core.cache.Cache
import core.cache.misc.buffer.ByteBufferUtils
import core.tools.Log
import java.nio.ByteBuffer

/**
 * Struct
 *
 * @property id
 * @constructor Struct
 */
class Struct(val id: Int) {

    var dataStore: HashMap<Int, Any> = HashMap()

    /**
     * Get int
     *
     * @param key
     * @return
     */
    fun getInt(key: Int): Int {
        if (!dataStore.containsKey(key)) {
            log(this.javaClass, Log.ERR, "Invalid value passed for key: $key struct: $id")
            return -1
        }
        return dataStore[key] as Int
    }

    /**
     * Get string
     *
     * @param key
     * @return
     */
    fun getString(key: Int): String? {
        return dataStore[key] as String?
    }

    override fun toString(): String {
        return "Struct{id=$id, dataStore=$dataStore}"
    }

    companion object {
        /**
         * The struct definitions mapping.
         */
        private val definitions: MutableMap<Int, Struct?> = HashMap()

        fun get(id: Int): Struct? {
            var def = definitions[id]
            if (def != null) {
                return def
            }
            val data = Cache.getIndexes()[2].getFileData(26, id)
            def = parse(id, data)

            definitions[id] = def
            return def
        }

        fun parse(id: Int, data: ByteArray?): Struct {
            val def = Struct(id)
            if (data != null) {
                val buffer = ByteBuffer.wrap(data)
                var opcode: Int

                while (((buffer.get().toInt() and 0xFF).also { opcode = it }) != 0) {
                    if (opcode == 249) {
                        val size = buffer.get().toInt() and 0xFF

                        var i = 0
                        while (size > i) {
                            val bool = (buffer.get().toInt() and 0xFF) == 1
                            val key = ByteBufferUtils.getMedium(buffer)
                            var value: Any = if (bool) {
                                ByteBufferUtils.getString(buffer)
                            } else {
                                buffer.getInt()
                            }
                            def.dataStore[key] = value
                            i++
                        }
                    }
                }
            }
            return def
        }

        val count: Int
            get() = Cache.getIndexes()[2].getFilesSize(26)
    }
}
