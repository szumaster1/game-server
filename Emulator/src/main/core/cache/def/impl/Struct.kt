package core.cache.def.impl

import core.api.log
import core.cache.Cache
import core.cache.misc.buffer.ByteBufferUtils
import core.tools.Log
import core.tools.SystemLogger
import java.nio.ByteBuffer

class Struct(private val id: Int) {

    /**
     * The struct definitions mapping.
     */
    companion object {
        private val definitions: MutableMap<Int, Struct> = HashMap()
        @JvmStatic
        fun get(id: Int): Struct {
            val def = definitions[id]
            if (def != null) {
                return def
            }
            val data = Cache.getIndexes()[2].getFileData(26, id)
            val parsedDef = parse(id, data)

            definitions[id] = parsedDef
            return parsedDef
        }

        fun parse(id: Int, data: ByteArray?): Struct {
            val def = Struct(id)
            data?.let {
                val buffer = ByteBuffer.wrap(data)
                var opcode: Int

                while (buffer.get().toInt() and 0xFF != 0) {
                    opcode = buffer.get().toInt() and 0xFF

                    if (opcode == 249) {
                        val size = buffer.get().toInt() and 0xFF

                        for (i in 0 until size) {
                            val bool = buffer.get().toInt() and 0xFF == 1
                            val key = ByteBufferUtils.getMedium(buffer)
                            val value: Any = if (bool) {
                                ByteBufferUtils.getString(buffer)
                            } else {
                                buffer.int
                            }
                            def.dataStore[key] = value
                        }
                    }
                }
            }
            return def
        }

        fun getCount(): Int {
            return Cache.getIndexes()[2].getFilesSize(26)
        }
    }

    var dataStore: HashMap<Int, Any> = HashMap()

    fun getInt(key: Int): Int {
        if (!dataStore.containsKey(key)) {
            log(this::class.java, Log.ERR, "Invalid value passed for key: $key struct: $id")
            return -1
        }
        return dataStore[key] as Int
    }

    fun getString(key: Int): String? {
        return dataStore[key] as String?
    }

    override fun toString(): String {
        return "Struct(id=$id, dataStore=$dataStore)"
    }

    fun getId(): Int {
        return id
    }
}
