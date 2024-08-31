package core.cache.def.impl

import core.api.getVarbit
import core.cache.Cache
import core.game.node.entity.player.Player
import java.nio.ByteBuffer

/**
 * Varbit definition
 */
class VarbitDefinition {
    /**
     * Gets the file id.
     * @return The id.
     */
    val id: Int

    /**
     * The config id.
     */
    var varpId: Int = 0

    /**
     * The bit shift amount.
     */
    var startBit: Int = 0

    /**
     * The bit amount.
     */
    var endBit: Int = 0
        private set // The setter is private to restrict modification from outside the class

    /**
     * Constructs a new `ConfigFileDefinition` `Object`.
     *
     * @param id The file id.
     */
    constructor(id: Int) {
        this.id = id // Assigning the provided id to the class property
    }

    constructor(varpId: Int, id: Int, startBit: Int, endBit: Int) {
        this.varpId = varpId // Assigning the provided varpId to the class property
        this.id = id // Assigning the provided id to the class property
        this.startBit = startBit // Assigning the provided startBit to the class property
        this.endBit = endBit // Assigning the provided endBit to the class property
    }

    /**
     * Get value
     *
     * @param player The player instance to retrieve the varbit value for
     * @return The varbit value for the player
     */
    fun getValue(player: Player?): Int {
        return getVarbit(player!!, id) // Fetching the varbit value using the player's instance and the id
    }

    val mask: Int
        get() {
            var mask = 0 // Initializing the mask variable
            for (i in startBit..endBit) mask = mask or (1 shl (i - startBit)) // Creating a bitmask based on start and end bits
            return mask // Returning the constructed mask
        }

    override fun toString(): String {
        return "ConfigFileDefinition [id=$id, configId=$varpId, bitShift=$startBit, bitSize=$endBit]" // Returning a string representation of the object
    }

    companion object {
        /**
         * The config definitions mapping.
         */
        private val MAPPING: MutableMap<Int, VarbitDefinition> = HashMap() // A mutable map to store VarbitDefinitions by their id

        /**
         * The bit size flags.
         */
        private val BITS = IntArray(32) // An array to hold bit size flags

        /**
         * Represents the bit flags.
         */
        init {
            var flag = 2 // Initializing the flag variable
            for (i in 0..31) {
                BITS[i] = flag - 1 // Storing the flag value in the BITS array
                flag += flag // Doubling the flag for the next iteration
            }
        }

        /**
         * Gets the config file definitions for the given file id.
         *
         * @param id The file id.
         * @return The definition.
         */
        @JvmStatic
        fun forObjectID(id: Int): VarbitDefinition {
            return forId(id) // Delegating to forId method to retrieve the definition
        }

        @JvmStatic
        fun forNPCID(id: Int): VarbitDefinition {
            return forId(id) // Delegating to forId method to retrieve the definition
        }

        fun forItemID(id: Int): VarbitDefinition {
            return forId(id) // Delegating to forId method to retrieve the definition
        }

        fun forId(id: Int): VarbitDefinition {
            var def: VarbitDefinition? = MAPPING[id] // Attempting to retrieve the definition from the mapping
            if (def != null) {
                return def // Returning the existing definition if found
            }

            def = VarbitDefinition(id) // Creating a new VarbitDefinition if not found
            val bs: ByteArray? = Cache.getIndexes()[22].getFileData(id ushr 10, id and 0x3ff) // Fetching file data from cache
            if (bs != null) {
                val buffer: ByteBuffer = ByteBuffer.wrap(bs) // Wrapping the byte array in a ByteBuffer
                var opcode: Int = 0 // Initializing opcode variable
                while (buffer.get().toInt().also { opcode = it } != 0) { // Reading opcodes until a zero is encountered
                    if (opcode == 1) {
                        def.varpId = buffer.getShort().toInt() and 0xFFFF // Reading and assigning varpId
                        def.startBit = buffer.get().toInt() and 0xFF // Reading and assigning startBit
                        def.endBit = buffer.get().toInt() and 0xFF // Reading and assigning endBit
                    }
                }
            }
            MAPPING[id] = def // Storing the newly created definition in the mapping
            return def // Returning the newly created definition
        }

        fun create(varpId: Int, varbitId: Int, startBit: Int, endBit: Int) {
            val def = VarbitDefinition(
                varpId,
                varbitId,
                startBit,
                endBit
            ) // Creating a new VarbitDefinition instance
            MAPPING[varbitId] = def // Storing the new definition in the mapping
        }

        val mapping: Map<Int, VarbitDefinition>
            get() = MAPPING // Exposing the mapping as a read-only property
    }
}
