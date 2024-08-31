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
     * Constructor to initialize a [VarbitDefinition] with parameters.
     *
     * @param varpId   The varp id.
     * @param id       The varbit id.
     * @param startBit The startbit id.
     * @param endBit   The endbit id.
     */
    constructor(varpId: Int, id: Int, startBit: Int, endBit: Int) : this(id) {
        this.varpId = varpId
        this.startBit = startBit
        this.endBit = endBit
    }

    /**
     * Retrieves the value of the varbit for player.
     *
     * @param player The player for whom the varbit value is being retrieved.
     * @return The value of the varbit.
     */
    fun getValue(player: Player?): Int {
        return getVarbit(player ?: throw IllegalArgumentException("Player cannot be null"), id)
    }

    /**
     * Computes a mask based on the start and end bit positions.
     */
    val mask: Int
        get() = (startBit..endBit).fold(0) { acc, i -> acc or (1 shl (i - startBit)) }

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
         * Retrieves a [VarbitDefinition] for a given object ID.
         *
         * @param id The scenery ID for which to retrieve the varbit definition.
         * @return The [VarbitDefinition] associated with the scenery id.
         */
        @JvmStatic
        fun forSceneryID(id: Int): VarbitDefinition = forId(id)

        /**
         * Retrieves a [VarbitDefinition] for a given NPC id.
         *
         * @param id The NPC ID for which to retrieve the varbit definition.
         * @return The [VarbitDefinition] associated with the NPC id.
         */
        @JvmStatic
        fun forNPCID(id: Int): VarbitDefinition = forId(id)

        /**
         * Retrieves a [VarbitDefinition] for a given item ID.
         *
         * @param id The item ID for which to retrieve the varbit definition.
         * @return The [VarbitDefinition] associated with the specified item ID.
         */
        fun forItemID(id: Int): VarbitDefinition = forId(id)

        /**
         * Retrieves a [VarbitDefinition] for a given ID.
         *
         * @param id The ID for which to retrieve the varbit definition.
         * @return The [VarbitDefinition] associated with the specified ID.
         */
        fun forId(id: Int): VarbitDefinition {
            return MAPPING[id] ?: createVarbitDefinition(id)
        }

        /**
         * Creates a [VarbitDefinition] based on the provided ID.
         *
         * @param id The ID for which to create the varbit definition.
         * @return The newly created [VarbitDefinition].
         */
        private fun createVarbitDefinition(id: Int): VarbitDefinition {
            val def = VarbitDefinition(id)
            val bs: ByteArray? = Cache.getIndexes()[22].getFileData(id ushr 10, id and 0x3ff)
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
         * Creates a new [VarbitDefinition] and adds it to the mapping.
         *
         * @param varpId   The varp id.
         * @param varbitId The varbit id.
         * @param startBit The startbit id.
         * @param endBit   The endbit id.
         */
        fun create(varpId: Int, varbitId: Int, startBit: Int, endBit: Int) {
            val def = VarbitDefinition(varpId, varbitId, startBit, endBit)
            MAPPING[varbitId] = def
        }

        /**
         * Mapping of all varbits.
         * @return A map of all [VarbitDefinition] indexed by their IDs.
         */
        val mapping: Map<Int, VarbitDefinition>
            get() = MAPPING
    }
}
