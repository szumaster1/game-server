package core.game.world.update

import core.api.*
import core.game.node.entity.Entity
import core.game.node.entity.combat.ImpactHandler
import core.game.node.entity.player.Player
import core.game.world.update.flag.*
import core.game.world.update.flag.context.HitMark
import core.network.packet.IoBuffer
import core.tools.*
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Update masks.
 *
 * @property owner The owner of the update masks.
 * @constructor Represents the update masks.
 */
class UpdateMasks(val owner: Entity) {
    var appearanceStamp: Long = 0
    private val type = EFlagType.of(owner)
    private val updating = AtomicBoolean()
    private var presenceFlags = 0
    private var syncedPresenceFlags = 0
    private val elements = arrayOfNulls<MaskElement?>(SIZE)
    private val syncedElements = arrayOfNulls<MaskElement?>(SIZE)

    private data class MaskElement(val encoder: EFlagProvider, val context: Any?)

    /**
     * Register an update mask.
     *
     * @param flag    The flag to register.
     * @param context The context for the flag.
     * @param sync    Whether to sync the flag or not.
     * @return `true` if the registration was successful, `false` otherwise.
     */
    @JvmOverloads
    fun register(flag: EntityFlag, context: Any?, sync: Boolean = false): Boolean {
        var synced = sync
        var provider = EntityFlags.getFlagFor(type, flag)
        if (provider == null) {
            logWithStack(
                origin = this::class.java,
                type = Log.ERR,
                message = "Tried to use flag ${flag.name} which is not available for ${type.name} in this revision."
            )
            return false
        }
        if (updating.get())
            return false
        if (flag == EntityFlag.Appearance) {
            appearanceStamp = System.currentTimeMillis()
            synced = true
        }
        if (synced) {
            syncedElements[provider.ordinal] = MaskElement(provider, context)
            syncedPresenceFlags = syncedPresenceFlags or provider.presenceFlag
        }
        elements[provider.ordinal] = MaskElement(provider, context)
        presenceFlags = presenceFlags or provider.presenceFlag
        return true
    }

    /**
     * Unregister a synced update mask.
     *
     * @param ordinal The ordinal of the mask element to unregister.
     * @return `true` if the unregistration was successful, `false` otherwise.
     */
    fun unregisterSynced(ordinal: Int): Boolean {
        if (syncedElements[ordinal] != null) {
            syncedPresenceFlags = syncedPresenceFlags and syncedElements[ordinal]!!.encoder.presenceFlag.inv()
            syncedElements[ordinal] = null
            return true
        }
        return false
    }

    /**
     * Write the update masks to the buffer.
     *
     * @param p      The player.
     * @param e      The entity.
     * @param buffer The buffer to write to.
     */
    fun write(p: Player?, e: Entity?, buffer: IoBuffer) {
        var maskData = presenceFlags
        if (maskData >= 0x100) {
            maskData = maskData or if (e is Player) 0x10 else 0x8
            buffer.put(maskData).put(maskData shr 8)
        } else {
            buffer.put(maskData)
        }
        for (i in elements.indices) {
            val element = elements[i]
            element?.encoder?.writeToDynamic(buffer, element.context, p!!)
        }
    }

    /**
     * Write the synced update masks to the buffer.
     *
     * @param p          The player.
     * @param e          The entity.
     * @param buffer     The buffer to write to.
     * @param appearance Whether to include the appearance flag or not.
     */
    fun writeSynced(p: Player?, e: Entity?, buffer: IoBuffer, appearance: Boolean) {
        var maskData = presenceFlags
        var synced = syncedPresenceFlags
        var appearanceFlag = EntityFlags.getPresenceFlag(type, EntityFlag.Appearance)
        if (!appearance && synced and appearanceFlag != 0) {
            synced = synced and appearanceFlag.inv()
        }
        maskData = maskData or synced
        if (maskData >= 0x100) {
            maskData = maskData or if (e is Player) 0x10 else 0x8
            buffer.put(maskData).put(maskData shr 8)
        } else {
            buffer.put(maskData)
        }
        for (i in elements.indices) {
            var element = elements[i]
            if (element == null) {
                element = syncedElements[i]
                if (!appearance && element != null && element.encoder.flag == EntityFlag.Appearance) {
                    continue
                }
            }
            element?.encoder?.writeToDynamic(buffer, element.context, p!!)
        }
    }

    /**
     * Prepare the update masks for an entity.
     *
     * @param entity The entity to prepare the update masks for.
     */
    fun prepare(entity: Entity) {
        val handler = entity.impactHandler
        for (i in 0..1) {
            if (handler.impactQueue.peek() == null) {
                break
            }
            val impact = handler.impactQueue.poll()
            registerHitUpdate(entity, impact, i == 1)
        }
        updating.set(true)
    }

    /**
     * Registers the hit update for the given impact.
     *
     * @param e         The entity.
     * @param impact    The impact to update.
     * @param secondary If the hit update is secondary.
     */
    private fun registerHitUpdate(e: Entity, impact: ImpactHandler.Impact, secondary: Boolean): HitMark {
        val mark = HitMark(impact.amount, impact.type.ordinal, e)
        register(if (secondary) EntityFlag.SecondaryHit else EntityFlag.PrimaryHit, mark)
        return mark
    }

    /**
     * Reset the update masks.
     */
    fun reset() {
        for (i in elements.indices) {
            elements[i] = null
        }
        presenceFlags = 0
        updating.set(false)
    }

    /**
     * Check if the update masks are currently being updated.
     *
     * @return `true` if the update masks are being updated, `false` otherwise.
     */
    fun isUpdating(): Boolean {
        return updating.get()
    }

    /**
     * Check if an update is required.
     *
     * @return `true` if an update is required, `false` otherwise.
     */
    val isUpdateRequired: Boolean
        get() = presenceFlags != 0

    /**
     * Check if there are synced update masks.
     *
     * @return `true` if there are synced update masks, `false` otherwise.
     */
    fun hasSynced(): Boolean {
        return syncedPresenceFlags != 0
    }

    companion object {
        /**
         * The amount of update masks.
         */
        const val SIZE = 11
    }
}
