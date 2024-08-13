package core.cache.def.impl

import core.api.getVarbit
import core.cache.Cache
import core.game.node.entity.player.Player
import java.nio.ByteBuffer

/**
 * Varbit definition
 *
 * @constructor Varbit definition
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
        private set

    /**
     * Constructs a new `ConfigFileDefinition` `Object`.
     *
     * @param id The file id.
     */
    constructor(id: Int) {
        this.id = id
    }

    constructor(varpId: Int, id: Int, startBit: Int, endBit: Int) {
        this.varpId = varpId
        this.id = id
        this.startBit = startBit
        this.endBit = endBit
    }

    /**
     * Get value
     *
     * @param player
     * @return
     */
    fun getValue(player: Player?): Int {
        return getVarbit(player!!, id)
    }

    val mask: Int
        get() {
            var mask = 0
            for (i in startBit..endBit) mask = mask or (1 shl (i - startBit))
            return mask
        }

    override fun toString(): String {
        return "ConfigFileDefinition [id=$id, configId=$varpId, bitShift=$startBit, bitSize=$endBit]"
    }

    companion object {
        /**
         * The config definitions mapping.
         */
        private val MAPPING: MutableMap<Int, VarbitDefinition> = HashMap()

        /**
         * The bit size flags.
         */
        private val BITS = IntArray(32)

        /**
         * Represents the bit flags.
         */
        init {
            var flag = 2
            for (i in 0..31) {
                BITS[i] = flag - 1
                flag += flag
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
            return forId(id)
        }

        @JvmStatic
        fun forNPCID(id: Int): VarbitDefinition {
            return forId(id)
        }

        fun forItemID(id: Int): VarbitDefinition {
            return forId(id)
        }

        fun forId(id: Int): VarbitDefinition {
            var def: VarbitDefinition? = MAPPING[id]
            if (def != null) {
                return def
            }

            def = VarbitDefinition(id)
            val bs: ByteArray? = Cache.getIndexes()[22].getFileData(id ushr 10, id and 0x3ff)
            if (bs != null) {
                val buffer: ByteBuffer = ByteBuffer.wrap(bs)
                var opcode: Int = 0
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

        fun create(varpId: Int, varbitId: Int, startBit: Int, endBit: Int) {
            val def = VarbitDefinition(
                varpId,
                varbitId,
                startBit,
                endBit
            )
            MAPPING[varbitId] = def
        }

        val mapping: Map<Int, VarbitDefinition>
            get() = MAPPING
    }
}
