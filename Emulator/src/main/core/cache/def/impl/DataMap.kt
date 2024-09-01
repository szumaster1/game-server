package core.cache.def.impl

import core.api.log
import core.cache.Cache
import core.cache.misc.buffer.ByteBufferUtils
import core.tools.CP1252
import core.tools.Log
import core.tools.SystemLogger
import java.nio.ByteBuffer

class DataMap(private val id: Int) {

    /**
     * The enum id.
     */
    var keyType: Char = '?'
    var valueType: Char = '?'
    var defaultString: String? = null
    var defaultInt: Int = 0
    var dataStore: HashMap<Int, Any> = HashMap()

    companion object {
        /**
         * The config definitions mapping.
         */
        private val definitions: MutableMap<Int, DataMap> = HashMap()

        /**
         * Get data map.
         *
         * @param id the id.
         * @return the data map.
         */
        fun get(id: Int): DataMap {
            val def = definitions[id]
            if (def != null) {
                return def
            }
            val data = Cache.getIndexes()[17].getFileData(id shr 8, id and 0xFF)
            val parsedDef = parse(id, data)
            definitions[id] = parsedDef
            return parsedDef
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

                while (buffer.get().toInt() and 0xFF != 0) {
                    opcode = buffer.get().toInt() and 0xFF

                    when (opcode) {
                        1 -> def.keyType = CP1252.decode(buffer.get())
                        2 -> def.valueType = CP1252.decode(buffer.get())
                        3 -> def.defaultString = ByteBufferUtils.getString(buffer)
                        4 -> def.defaultInt = buffer.int
                        5, 6 -> {
                            val size = buffer.short.toInt() and 0xFFFF
                            for (i in 0 until size) {
                                val key = buffer.int
                                val value: Any = if (opcode == 5) {
                                    ByteBufferUtils.getString(buffer)!!
                                } else {
                                    buffer.int
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

    override fun toString(): String {
        return "DataMapDefinition{" +
                "id=$id" +
                ", keyType=$keyType" +
                ", valueType=${if (valueType == 'K') "Normal" else if (valueType == 'J') "Struct Pointer" else "Unknown"}" +
                ", defaultString='$defaultString'" +
                ", defaultInt=$defaultInt" +
                ", dataStore=$dataStore" +
                '}' + "\n"
    }

    fun getString(key: Int): String? {
        return dataStore[key] as String?
    }

    fun getInt(key: Int): Int {
        if (!dataStore.containsKey(key)) {
            log(this::class.java, Log.ERR, "Invalid value passed for key: $key map: $id")
            return -1
        }
        return dataStore[key] as Int
    }

    fun getId(): Int {
        return id
    }
}
