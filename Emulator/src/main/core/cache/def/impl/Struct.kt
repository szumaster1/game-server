package core.cache.def.impl

import core.api.log
import core.cache.Cache
import core.cache.misc.buffer.ByteBufferUtils
import core.tools.Log
import core.tools.SystemLogger
import java.nio.ByteBuffer

class Struct(private val id: Int) {

    companion object {
        private val definitions: MutableMap<Int, Struct> = HashMap()

        /**
         * Retrieves a [Struct] instance by its ID. If it does not exist, it fetches the data,
         * parses it, and stores it in the definitions map.
         *
         * @param id The ID of the [Struct] to retrieve.
         * @return The Struct instance associated with the given ID.
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
         * @param id The ID of the [Struct] being parsed.
         * @param data The byte array containing the data to parse.
         * @return The newly created [Struct] instance populated with data.
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
         * Returns the total count of Struct files available in the cache.
         *
         * @return The number of [Struct] files.
         */
        fun getCount(): Int {
            return Cache.getIndexes()[2].getFilesSize(26)
        }
    }

    var dataStore: HashMap<Int, Any> = HashMap()

    /**
     * Retrieves an integer value from the [dataStore] by its key.
     *
     * @param key The key associated with the integer value.
     * @return The integer value if found, otherwise -1.
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
     * @param key The key associated with the string value.
     * @return The string value if found, otherwise null.
     */
    fun getString(key: Int): String? {
        return dataStore[key] as String?
    }

    /**
     * Returns a string representation of the [Struct] instance.
     *
     * @return A string describing the [Struct].
     */
    override fun toString(): String {
        return "Struct(id=$id, dataStore=$dataStore)"
    }

    /**
     * Retrieves the ID of the [Struct] instance.
     *
     * @return The ID of the [Struct].
     */
    fun getId(): Int {
        return id
    }
}
