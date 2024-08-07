package core.game.world.update.flag

import core.api.logWithStack
import core.game.node.entity.Entity
import core.network.packet.IoBuffer
import core.tools.Log
import kotlin.reflect.KType

/**
 * E flag type
 *
 * @constructor E flag type
 */
enum class EFlagType {
    /**
     * Player
     *
     * @constructor Player
     */
    Player,

    /**
     * Npc
     *
     * @constructor Npc
     */
    NPC;

    companion object {
        @JvmStatic
        fun of(e: Entity): EFlagType {
            if (e is core.game.node.entity.player.Player) return Player
            else return NPC
        }
    }
}

/**
 * E flag provider
 *
 * @property revision
 * @property type
 * @property presenceFlag
 * @property ordinal
 * @property flag
 * @constructor E flag provider
 */
open class EFlagProvider(
    val revision: Int,
    val type: EFlagType,
    val presenceFlag: Int,
    val ordinal: Int,
    val flag: EntityFlag
) {
    /**
     * Write to
     *
     * @param buffer
     * @param context
     */
    open fun writeTo(buffer: IoBuffer, context: Any?) {}

    /**
     * Write to dynamic
     *
     * @param buffer
     * @param context
     * @param e
     */
    open fun writeToDynamic(buffer: IoBuffer, context: Any?, e: Entity) {
        writeTo(buffer, context)
    }

    /**
     * Log invalid type
     *
     * @param context
     * @param expected
     */
    fun logInvalidType(context: Any?, expected: KType) {
        logWithStack(
            this::class.java,
            Log.ERR,
            "Invalid context of type ${context?.let { it::class.java.simpleName } ?: "null"} passed to ${this::class.simpleName} flag which expects $expected.")
    }
}
