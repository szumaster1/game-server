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

    constructor(varpId: Int, id: Int, startBit: Int, endBit: Int) : this(id) {
        this.varpId = varpId
        this.startBit = startBit
        this.endBit = endBit
    }

    /**
     * Get the value of the variable bit for the given player.
     *
     * @param [player] The player whose variable bit value is to be retrieved.
     * @return The value of the variable bit.
     * @throws [IllegalArgumentException] if the player is null.
     */
    fun getValue(player: Player?): Int {
        return getVarbit(player ?: throw IllegalArgumentException("Player cannot be null"), id)
    }

    /**
     * Computes the mask based on the start and end bits.
     * @return The computed mask as an integer.
     */
    val mask: Int
        get() = (startBit..endBit).fold(0) { acc, i -> acc or (1 shl (i - startBit)) }

    /**
     * Returns a string representation of the [VarbitDefinition].
     *
     * @return A string describing the VarbitDefinition.
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
         * Retrieves a [VarbitDefinition] for a scenery id.
         *
         * @param id The scenery id.
         * @return The corresponding VarbitDefinition.
         */
        @JvmStatic
        fun forSceneryID(id: Int): VarbitDefinition = forId(id)

        /**
         * Get a [VarbitDefinition] for a npc id.
         *
         * @param id The npc id.
         * @return The corresponding VarbitDefinition.
         */
        @JvmStatic
        fun forNPCID(id: Int): VarbitDefinition = forId(id)

        /**
         * Get a [VarbitDefinition] for a given item.
         *
         * @param id The item id.
         * @return The corresponding VarbitDefinition.
         */
        @JvmStatic
        fun forItemID(id: Int): VarbitDefinition = forId(id)

        /**
         * Get a [VarbitDefinition] for a given id.
         *
         * @param id The varbit id.
         * @return The corresponding VarbitDefinition.
         */
        @JvmStatic
        fun forId(id: Int): VarbitDefinition {
            return MAPPING[id] ?: createVarbitDefinition(id)
        }

        /**
         * Creates a [VarbitDefinition] based on the provided id.
         *
         * @param id The varbit id.
         * @return The created VarbitDefinition.
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
         * @param varpId The variable parameter ID.
         * @param varbitId The variable bit ID.
         * @param startBit The starting bit position.
         * @param endBit The end bit position.
         */
        fun create(varpId: Int, varbitId: Int, startBit: Int, endBit: Int) {
            val def = VarbitDefinition(varpId, varbitId, startBit, endBit)
            MAPPING[varbitId] = def
        }

        /**
         * Retrieves the mapping of all [VarbitDefinition].
         *
         * @return A map of all VarbitDefinitions.
         */
        val mapping: Map<Int, VarbitDefinition>
            get() = MAPPING
    }
}
