package core.cache.def.impl

import core.api.log
import core.cache.Cache
import core.cache.misc.buffer.ByteBufferUtils
import core.tools.Log
import java.nio.ByteBuffer

/**
 * Struct class represents a data structure that holds an ID and a data store.
 *
 * @param id Unique identifier for the struct.
 * @constructor Creates a Struct instance with the specified ID.
 */
class Struct(val id: Int) {

    // HashMap to store key-value pairs where key is an Int and value can be of any type.
    var dataStore: HashMap<Int, Any> = HashMap()

    /**
     * Retrieves an integer value associated with the specified key from the data store.
     *
     * @param key The key whose associated value is to be returned.
     * @return The integer value associated with the specified key, or -1 if the key is not found.
     */
    fun getInt(key: Int): Int {
        // Check if the key exists in the data store.
        if (!dataStore.containsKey(key)) {
            // Log an error message if the key is invalid.
            log(this.javaClass, Log.ERR, "Invalid value passed for key: $key struct: $id")
            return -1 // Return -1 to indicate the key was not found.
        }
        // Return the integer value associated with the key.
        return dataStore[key] as Int
    }

    /**
     * Retrieves a string value associated with the specified key from the data store.
     *
     * @param key The key whose associated value is to be returned.
     * @return The string value associated with the specified key, or null if the key is not found.
     */
    fun getString(key: Int): String? {
        // Return the string value associated with the key, or null if not found.
        return dataStore[key] as String?
    }

    // Returns a string representation of the Struct instance.
    override fun toString(): String {
        return "Struct{id=$id, dataStore=$dataStore}"
    }

    companion object {
        /**
         * The struct definitions mapping, storing Struct instances by their IDs.
         */
        private val definitions: MutableMap<Int, Struct?> = HashMap()

        /**
         * Retrieves a Struct instance by its ID.
         *
         * @param id The ID of the struct to retrieve.
         * @return The Struct instance associated with the specified ID, or null if not found.
         */
        fun get(id: Int): Struct? {
            // Check if the struct is already defined.
            var def = definitions[id]
            if (def != null) {
                return def // Return the existing struct if found.
            }
            // Retrieve the raw data for the struct from the cache.
            val data = Cache.getIndexes()[2].getFileData(26, id)
            // Parse the data to create a new Struct instance.
            def = parse(id, data)

            // Store the newly created struct in the definitions map.
            definitions[id] = def
            return def // Return the newly created struct.
        }

        /**
         * Parses the raw data to create a Struct instance.
         *
         * @param id The ID of the struct being created.
         * @param data The raw byte array data to parse.
         * @return The newly created Struct instance.
         */
        fun parse(id: Int, data: ByteArray?): Struct {
            val def = Struct(id) // Create a new Struct instance with the given ID.
            if (data != null) {
                // Wrap the byte array in a ByteBuffer for easier data manipulation.
                val buffer = ByteBuffer.wrap(data)
                var opcode: Int

                // Read opcodes from the buffer until a zero opcode is encountered.
                while (((buffer.get().toInt() and 0xFF).also { opcode = it }) != 0) {
                    if (opcode == 249) { // Check for a specific opcode indicating a data structure.
                        val size = buffer.get().toInt() and 0xFF // Get the size of the data.

                        var i = 0
                        // Loop through the size of the data to read key-value pairs.
                        while (size > i) {
                            val bool = (buffer.get().toInt() and 0xFF) == 1 // Determine if the value is a string.
                            val key = ByteBufferUtils.getMedium(buffer) // Get the key from the buffer.
                            // Get the value based on the boolean flag.
                            var value: Any = if (bool) {
                                ByteBufferUtils.getString(buffer) // Retrieve string value.
                            } else {
                                buffer.getInt() // Retrieve integer value.
                            }
                            // Store the key-value pair in the data store.
                            def.dataStore[key] = value
                            i++ // Increment the counter.
                        }
                    }
                }
            }
            return def // Return the constructed Struct instance.
        }

        // Property to get the count of struct definitions available in the cache.
        val count: Int
            get() = Cache.getIndexes()[2].getFilesSize(26) // Return the size of the files in the cache.
    }
}
