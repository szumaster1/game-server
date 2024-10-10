package core.cache.def.impl

import core.api.getVarbit
import core.cache.Cache
import core.game.node.entity.player.Player
import java.nio.ByteBuffer

/**
 * Represents a variable bit definition with its associated properties.
 */
class VarbitDefinition(val id: Int) {
    var varpId: Int = 0
    var startBit: Int = 0
    var endBit: Int = 0
        private set


    /**
     * Constructor to initialize a VarbitDefinition with specific parameters.
     */
    constructor(varpId: Int, id: Int, startBit: Int, endBit: Int) : this(id) {
        this.varpId = varpId
        this.startBit = startBit
        this.endBit = endBit
    }

    /**
     * Retrieves the value of the varbit for a specific player.
     *
     * @param player The player for whom the varbit value is being retrieved.
     * @return The value of the varbit.
     */
    fun getValue(player: Player?): Int {
        return getVarbit(player ?: throw IllegalArgumentException("Player cannot be null"), id) // Retrieves the varbit value, ensuring player is not null
    }

    /**
     * Computes a mask based on the start and end bit positions.
     */
    val mask: Int
        get() = (startBit..endBit).fold(0) { acc, i -> acc or (1 shl (i - startBit)) } // Generates a bitmask for the defined range

    /**
     * Returns a string representation of the [VarbitDefinition].
     */
    override fun toString(): String {
        return "VarbitDefinition [id=$id, varpId=$varpId, startBit=$startBit, endBit=$endBit]"
    }

    companion object {
        private val MAPPING: MutableMap<Int, VarbitDefinition> = HashMap()
        private val BITS = IntArray(32)

        init {
            var flag = 2
            for (i in BITS.indices) {
                BITS[i] = flag - 1
                flag *= 2
            }
        }

        /**
         * Retrieves a VarbitDefinition based on scenery ID.
         *
         * @param id The scenery ID.
         * @return The corresponding VarbitDefinition.
         */
        @JvmStatic
        fun forSceneryID(id: Int): VarbitDefinition = forId(id)

        /**
         * Retrieves a VarbitDefinition based on NPC ID.
         *
         * @param id The NPC ID.
         * @return The corresponding VarbitDefinition.
         */
        @JvmStatic
        fun forNPCID(id: Int): VarbitDefinition = forId(id)

        /**
         * Retrieves a VarbitDefinition based on item ID.
         *
         * @param id The item ID.
         * @return The corresponding VarbitDefinition.
         */
        @JvmStatic
        fun forItemID(id: Int): VarbitDefinition = forId(id)

        /**
         * Retrieves a VarbitDefinition based on a general ID.
         *
         * @param id The ID.
         * @return The corresponding VarbitDefinition.
         */
        @JvmStatic
        fun forId(id: Int): VarbitDefinition {
            return MAPPING[id] ?: createVarbitDefinition(id)
        }

        /**
         * Creates a VarbitDefinition based on the provided ID.
         *
         * @param id The ID.
         * @return The newly created VarbitDefinition.
         */
        private fun createVarbitDefinition(id: Int): VarbitDefinition {
            val def = VarbitDefinition(id)
            val bs: ByteArray? = Cache.getIndexes()[22]?.getFileData(id ushr 10, id and 0x3ff)
            bs?.let {
                val buffer = ByteBuffer.wrap(it)
                var opcode: Int
                while (buffer.get().toInt().also { opcode = it } != 0) {
                    if (opcode == 1) {
                        def.varpId = buffer.getShort().toInt() and 0xFFFF
                        def.startBit = buffer.get().toInt() and 0xFF
                        def.endBit = buffer.get().toInt() and 0xFF
                    }
                }
            }
            MAPPING[id] = def
            return def
        }

        /**
         * Creates a new VarbitDefinition with specified parameters.
         *
         * @param varpId The variable parameter ID.
         * @param varbitId The variable bit ID.
         * @param startBit The starting bit position.
         * @param endBit The ending bit position.
         */
        fun create(varpId: Int, varbitId: Int, startBit: Int, endBit: Int) {
            val def = VarbitDefinition(varpId, varbitId, startBit, endBit)
            MAPPING[varbitId] = def
        }

        /**
         * Provides access to the mapping of VarbitDefinitions.
         */
        val mapping: Map<Int, VarbitDefinition>
            get() = MAPPING
    }
}
