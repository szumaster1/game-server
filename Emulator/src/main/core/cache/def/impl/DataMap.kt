package core.cache.def.impl

import core.api.log
import core.cache.Cache
import core.cache.misc.buffer.ByteBufferUtils
import core.tools.CP1252.decode
import core.tools.Log
import java.nio.ByteBuffer

/**
 * Data map.
 */
class DataMap(/**
               * Gets id.
               *
               * @return the id
               */
              val id: Int) {
    /**
     * The Key type.
     */
    var keyType: Char = '?'

    /**
     * The Value type.
     */
    var valueType: Char = '?'

    /**
     * The Default string.
     */
    var defaultString: String? = null

    /**
     * The Default int.
     */
    var defaultInt: Int = 0

    /**
     * The Data store.
     */
    var dataStore: HashMap<Int, Any> = HashMap()

    /**
     * Gets int.
     *
     * @param key the key.
     * @return the int.
     */
    fun getInt(key: Int): Int {
        if (!dataStore.containsKey(key)) {
            log(this.javaClass, Log.ERR, "Invalid value passed for key: $key map: $id")
            return -1
        }
        return dataStore[key] as Int
    }

    /**
     * Gets string.
     *
     * @param key the key.
     * @return the string.
     */
    fun getString(key: Int): String? {
        return dataStore[key] as String?
    }

    override fun toString(): String {
        return "DataMapDefinition{" +
                "id=" + id +
                ", keyType=" + keyType +
                ", valueType=" + (if (valueType == 'K') "Normal" else if (valueType == 'J') "Struct Pointer" else "Unknown") +
                ", defaultString='" + defaultString + '\'' +
                ", defaultInt=" + defaultInt +
                ", dataStore=" + dataStore +
                '}' + "\n"
    }

    companion object {
        private val definitions: MutableMap<Int, DataMap> = HashMap()

        /**
         * Get data map.
         *
         * @param id the id.
         * @return the data map.
         */
        fun get(id: Int): DataMap? {
            var def = definitions[id]
            if (def != null) {
                return def
            }
            val data = Cache.getIndexes()[17].getFileData(id ushr 8, id and 0xFF)
            def = parse(id, data)
            definitions[id] = def
            return def
        }

        /**
         * Parse data map.
         *
         * @param id   the id.
         * @param data the data.
         * @return the data map.
         */
        fun parse(id: Int, data: ByteArray?): DataMap {
            val def = DataMap(id)
            if (data != null) {
                val buffer = ByteBuffer.wrap(data)
                var opcode: Int

                while (((buffer.get().toInt() and 0xFF).also { opcode = it }) != 0) {
                    when (opcode) {
                        1 -> def.keyType = decode(buffer.get())
                        2 -> def.valueType = decode(buffer.get())
                        3 -> def.defaultString = ByteBufferUtils.getString(buffer)
                        4 -> def.defaultInt = buffer.getInt()
                        5, 6 -> {
                            val size = buffer.getShort().toInt() and 0xFFFF

                            for (i in 0 until size) {
                                val key = buffer.getInt()
                                var value: Any = if (opcode == 5) {
                                    ByteBufferUtils.getString(buffer)
                                } else {
                                    buffer.getInt()
                                }
                                def.dataStore[key] = value
                            }
                        }
                    }
                }
            }
            return def
        }
    }
}
