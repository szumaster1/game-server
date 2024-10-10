package core.cache.def.impl

import core.cache.Cache
import core.cache.misc.buffer.ByteBufferUtils
import core.tools.Log
import java.nio.ByteBuffer
import java.util.HashMap

import core.api.log

class Struct private constructor(val id: Int) {

    var dataStore: HashMap<Int, Any> = HashMap()

    fun getInt(key: Int): Int {
        if (!dataStore.containsKey(key)) {
            log(this.javaClass, Log.ERR, "Invalid value passed for key: $key struct: $id")
            return -1
        }
        return dataStore[key] as Int
    }

    fun getString(key: Int): String? {
        return dataStore[key] as? String
    }

    override fun toString(): String {
        return "Struct(id=$id, dataStore=$dataStore)"
    }

    companion object {

        /**
         * The struct definitions mapping.
         */
        @JvmStatic
        private val definitions: MutableMap<Int, Struct> = HashMap()

        @JvmStatic
        fun get(id: Int): Struct {
            var def = definitions[id]
            if (def != null) {
                return def
            }
            val data = Cache.getIndexes()[2]?.getFileData(26, id)
            def = parse(id, data)

            definitions[id] = def
            return def
        }

        @JvmStatic
        fun parse(id: Int, data: ByteArray?): Struct {
            val def = Struct(id)
            if (data != null) {
                val buffer = ByteBuffer.wrap(data)
                var opcode: Int

                while (buffer.get().also { opcode = it.toInt() and 0xFF }.toInt() != 0) {

                    if (opcode == 249) {
                        val size = buffer.get().toInt() and 0xFF

                        for (i in 0 until size) {
                            val bool = (buffer.get().toInt() and 0xFF) == 1
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

        @JvmStatic
        fun getCount(): Int {
            return Cache.getIndexes()[2]!!.getFilesSize(26)
        }
    }
}
