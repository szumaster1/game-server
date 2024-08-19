package core.game.world.update.flag

import core.api.logWithStack
import core.game.node.entity.Entity
import core.network.packet.IoBuffer
import core.tools.Log
import kotlin.reflect.KType

/**
 * Entity Flag provider.
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
 * EFlagProvider class is responsible for managing entity flags.
 *
 * @param revision An integer representing the version of the flag.
 * @param type The type of the flag, represented by EFlagType.
 * @param presenceFlag An integer indicating the presence status of the flag.
 * @param ordinal An integer representing the order of the flag.
 * @param flag An instance of EntityFlag that holds the actual flag data.
 */
open class EFlagProvider(
    val revision: Int,
    val type: EFlagType,
    val presenceFlag: Int,
    val ordinal: Int,
    val flag: EntityFlag
) {
    /**
     * Write to the specified IoBuffer with the given context.
     *
     * @param buffer The IoBuffer to write to.
     * @param context The context in which the write operation is performed.
     */
    open fun writeTo(buffer: IoBuffer, context: Any?) {}

    /**
     * Write to a dynamic IoBuffer with the given context and entity.
     *
     * @param buffer The IoBuffer to write to.
     * @param context The context in which the write operation is performed.
     * @param e The entity that is being written to the buffer.
     */
    open fun writeToDynamic(buffer: IoBuffer, context: Any?, e: Entity) {
        writeTo(buffer, context)
    }

    /**
     * Log an invalid type error with the provided context and expected type.
     *
     * @param context The context that was passed, which may be invalid.
     * @param expected The expected type that should have been passed.
     */
    fun logInvalidType(context: Any?, expected: KType) {
        logWithStack(this::class.java, Log.ERR, "Invalid context of type ${context?.let { it::class.java.simpleName } ?: "null"} passed to ${this::class.simpleName} flag which expects $expected.")
    }
}
