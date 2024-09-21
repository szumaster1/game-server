package core.cache.def.impl

import core.api.log
import core.cache.Cache
import core.cache.misc.buffer.ByteBufferUtils
import core.tools.Log
import core.tools.SystemLogger
import java.nio.ByteBuffer

/**
 * Represents the struct.
 */
class Struct(private val id: Int) {

    companion object {
        private val definitions: MutableMap<Int, Struct> = HashMap()

        /**
         * Get a struct by its id. If it does not exist, it fetches the data,
         * parses it, and stores it in the definitions map.
         *
         * @param id the id of the struct to retrieve.
         * @return the struct with the given id.
         */
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

        /**
         * Parses the given byte array data into a Struct instance.
         *
         * @param id the file id.
         * @param data the byte array containing the data to parse.
         * @return the newly created struct populated with data.
         */
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

        /**
         * Get the total count of struct files available in the cache.
         * @return the amount of struct files.
         */
        fun getCount(): Int {
            return Cache.getIndexes()[2].getFilesSize(26)
        }
    }

    var dataStore: HashMap<Int, Any> = HashMap()

    /**
     * Retrieves an integer value from the [dataStore] by its key.
     *
     * @param key the key associated with the integer value.
     * @return the integer value if found, otherwise -1.
     */
    fun getInt(key: Int): Int {
        if (!dataStore.containsKey(key)) {
            log(this::class.java, Log.ERR, "Invalid value passed for key: $key struct: $id")
            return -1
        }
        return dataStore[key] as Int
    }

    /**
     * Retrieves a string value from the [dataStore] by its key.
     *
     * @param key the key associated with the string value.
     * @return the string value if found, otherwise null.
     */
    fun getString(key: Int): String? {
        return dataStore[key] as String?
    }

    /**
     * Return a string representation of the struct.
     */
    override fun toString(): String {
        return "Struct(id=$id, dataStore=$dataStore)"
    }

    /**
     * Get the id of [Struct] instance.
     *
     * @return the id of the [Struct].
     */
    fun getId(): Int {
        return id
    }
}
