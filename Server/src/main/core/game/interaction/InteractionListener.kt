package core.game.interaction

import content.global.handlers.item.SpadeDig
import core.api.ContentInterface
import core.game.node.Node
import core.game.node.entity.Entity
import core.game.node.entity.player.Player
import core.game.world.map.Location

/**
 * Interaction listener
 *
 * @constructor Interaction listener
 */
interface InteractionListener : ContentInterface {
    val ITEM
        get() = IntType.ITEM
    val GROUNDITEM
        get() = IntType.GROUNDITEM
    val NPC
        get() = IntType.NPC
    val SCENERY
        get() = IntType.SCENERY

    /**
     * On
     *
     * @param id
     * @param type
     * @param option
     * @param handler
     * @receiver
     */
    fun on(id: Int, type: IntType, vararg option: String, handler: (player: Player, node: Node) -> Boolean) {
        InteractionListeners.add(id, type.ordinal, option, handler)
    }

    /**
     * On
     *
     * @param ids
     * @param type
     * @param option
     * @param handler
     * @receiver
     */
    fun on(ids: IntArray, type: IntType, vararg option: String, handler: (player: Player, node: Node) -> Boolean) {
        InteractionListeners.add(ids, type.ordinal, option, handler)
    }

    /**
     * On
     *
     * @param option
     * @param type
     * @param handler
     * @receiver
     */
    @Deprecated("Don't use")
    fun on(option: String, type: IntType, handler: (player: Player, node: Node) -> Boolean) {
        InteractionListeners.add(option, type.ordinal, handler)
    }

    /**
     * On
     *
     * @param type
     * @param option
     * @param handler
     * @receiver
     */
    fun on(type: IntType, vararg option: String, handler: (player: Player, node: Node) -> Boolean) {
        InteractionListeners.add(option, type.ordinal, handler)
    }

    /**
     * On use with
     *
     * @param type
     * @param used
     * @param with
     * @param handler
     * @receiver
     */
    fun onUseWith(type: IntType, used: Int, vararg with: Int, handler: (player: Player, used: Node, with: Node) -> Boolean) {
        InteractionListeners.add(type.ordinal, used, with, handler)
    }

    /**
     * On use with
     *
     * @param type
     * @param used
     * @param with
     * @param handler
     * @receiver
     */
    fun onUseWith(type: IntType, used: IntArray, vararg with: Int, handler: (player: Player, used: Node, with: Node) -> Boolean) {
        InteractionListeners.add(type.ordinal, used, with, handler)
    }

    /**
     * On use any with
     *
     * @param type
     * @param with
     * @param handler
     * @receiver
     */
    fun onUseAnyWith(type: IntType, vararg with: Int, handler: (player: Player, used: Node, with: Node) -> Boolean) {
        InteractionListeners.add(type.ordinal, with, handler)
    }

    /**
     * On use with player
     *
     * @param used
     * @param handler
     * @receiver
     */
    fun onUseWithPlayer(vararg used: Int, handler: (player: Player, used: Node, with: Node) -> Boolean) {
        InteractionListeners.add(IntType.PLAYER.ordinal, used, handler)
    }

    /**
     * On use with wildcard
     *
     * @param type
     * @param predicate
     * @param handler
     * @receiver
     * @receiver
     */// Note: wildcard listeners incur overhead on every use-with interaction, only use them as a space-time tradeoff when something
    // is actually supposed to have a response to every item used with it (e.g. imp boxes, certain quest npcs)
    fun onUseWithWildcard(type: IntType, predicate: (used: Int, with: Int) -> Boolean, handler: (player: Player, used: Node, with: Node) -> Boolean) {
        InteractionListeners.addWildcard(type.ordinal, predicate, handler)
    }

    /**
     * On equip
     *
     * @param id
     * @param handler
     * @receiver
     */
    fun onEquip(id: Int, handler: (player: Player, node: Node) -> Boolean) {
        InteractionListeners.addEquip(id, handler)
    }

    /**
     * On unequip
     *
     * @param id
     * @param handler
     * @receiver
     */
    fun onUnequip(id: Int, handler: (player: Player, node: Node) -> Boolean) {
        InteractionListeners.addUnequip(id, handler)
    }

    /**
     * On equip
     *
     * @param ids
     * @param handler
     * @receiver
     */
    fun onEquip(ids: IntArray, handler: (player: Player, node: Node) -> Boolean) {
        ids.forEach { id -> InteractionListeners.addEquip(id, handler) }
    }

    /**
     * On unequip
     *
     * @param ids
     * @param handler
     * @receiver
     */
    fun onUnequip(ids: IntArray, handler: (player: Player, node: Node) -> Boolean) {
        ids.forEach { id -> InteractionListeners.addUnequip(id, handler) }
    }

    /**
     * Define destination overrides
     *
     */
    fun defineDestinationOverrides() {}

    /**
     * Set dest
     *
     * @param type
     * @param id
     * @param handler
     * @receiver
     */
    fun setDest(type: IntType, id: Int, handler: (Entity, Node) -> Location) {
        InteractionListeners.addDestOverride(type.ordinal, id, handler)
    }

    /**
     * Set dest
     *
     * @param type
     * @param options
     * @param handler
     * @receiver
     */
    fun setDest(type: IntType, vararg options: String, handler: (Entity, Node) -> Location) {
        InteractionListeners.addDestOverrides(type.ordinal, options, handler)
    }

    /**
     * Set dest
     *
     * @param type
     * @param ids
     * @param options
     * @param handler
     * @receiver
     */
    fun setDest(type: IntType, ids: IntArray, vararg options: String, handler: (Entity, Node) -> Location) {
        InteractionListeners.addDestOverrides(type.ordinal, ids, options, handler)
    }

    /**
     * On dig
     *
     * @param location
     * @param method
     * @receiver
     */
    fun onDig(location: Location, method: (player: Player) -> Unit) {
        SpadeDig.registerListener(location, method)
    }

    /**
     * Flag instant
     *
     */
    fun flagInstant() {
        val name = this::class.java.name
        InteractionListeners.instantClasses.add(name)
    }

    /**
     * Define interaction
     *
     * @param type
     * @param ids
     * @param options
     * @param persistent
     * @param allowedDistance
     * @param handler
     * @receiver
     */
    fun defineInteraction(type: IntType, ids: IntArray, vararg options: String, persistent: Boolean = false, allowedDistance: Int = 1, handler: (player: Player, node: Node, state: Int) -> Boolean) {
        InteractionListeners.addMetadata(ids, type, options, InteractionMetadata(handler, allowedDistance, persistent))
    }

    /**
     * Define interaction
     *
     * @param type
     * @param options
     * @param persist
     * @param allowedDistance
     * @param handler
     * @receiver
     */
    fun defineInteraction(type: IntType, vararg options: String, persist: Boolean = false, allowedDistance: Int = 1, handler: (player: Player, node: Node, state: Int) -> Boolean) {
        InteractionListeners.addGenericMetadata(options, type, InteractionMetadata(handler, allowedDistance, persist))
    }

    /**
     * Interaction metadata
     *
     * @property handler
     * @property distance
     * @property persist
     * @constructor Interaction metadata
     */
    data class InteractionMetadata(
        val handler: (player: Player, node: Node, state: Int) -> Boolean,
        val distance: Int,
        val persist: Boolean
    )

    /**
     * Use with metadata
     *
     * @property handler
     * @property distance
     * @property persist
     * @constructor Use with metadata
     */
    data class UseWithMetadata(
        val handler: (player: Player, used: Node, with: Node, state: Int) -> Boolean,
        val distance: Int,
        val persist: Boolean
    )

    /**
     * Define listeners
     *
     */
    fun defineListeners()
}
