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
     * Defines a function 'on' that registers a handler for a specific event.
     *
     * @param id The unique identifier of the event.
     * @param type The type of the event.
     * @param option Additional options for the event.
     * @param handler The function that handles the event.
     * @receiver The context in which the event is triggered.
     */
    fun on(id: Int, type: IntType, vararg option: String, handler: (player: Player, node: Node) -> Boolean) {
        InteractionListeners.add(id, type.ordinal, option, handler)
    }

    /**
     * Defines a function 'on' that handles events based on the provided parameters.
     *
     * @param ids An array of integers representing event IDs.
     * @param type An enum representing the type of event.
     * @param option Variable number of strings representing additional options.
     * @param handler A lambda function that takes a player and a node, returning a boolean.
     * @receiver Implicit receiver for extension functions.
     */
    fun on(ids: IntArray, type: IntType, vararg option: String, handler: (player: Player, node: Node) -> Boolean) {
        InteractionListeners.add(ids, type.ordinal, option, handler)
    }

    /**
     * Defines a function 'on' that handles a specific option with a given type and handler.
     *
     * @param option The option to handle
     * @param type The type of the option
     * @param handler The handler function that takes a player and a node and returns a boolean
     * @receiver The context in which the function is called
     */
    @Deprecated("Don't use")
    fun on(option: String, type: IntType, handler: (player: Player, node: Node) -> Boolean) {
        InteractionListeners.add(option, type.ordinal, handler)
    }

    /**
     * Defines a function to handle events based on the type and options provided.
     *
     * @param type The type of event to handle.
     * @param option The options related to the event.
     * @param handler The function that handles the event.
     * @receiver The context in which the event is triggered.
     */
    fun on(type: IntType, vararg option: String, handler: (player: Player, node: Node) -> Boolean) {
        InteractionListeners.add(option, type.ordinal, handler)
    }

    /**
     * Defines a function to handle the interaction when using an item with another item.
     *
     * @param type The type of interaction.
     * @param used The item being used.
     * @param with The item being used with.
     * @param handler The handler function to execute.
     * @receiver The context in which the function is called.
     */
    fun onUseWith(type: IntType, used: Int, vararg with: Int, handler: (player: Player, used: Node, with: Node) -> Boolean) {
        InteractionListeners.add(type.ordinal, used, with, handler)
    }

    /**
     * Defines a function to handle the action of using an item with another item.
     *
     * @param type The type of interaction.
     * @param used The item being used.
     * @param with The item being used with.
     * @param handler The handler function to execute the action.
     * @receiver The context in which the function is called.
     */
    fun onUseWith(type: IntType, used: IntArray, vararg with: Int, handler: (player: Player, used: Node, with: Node) -> Boolean) {
        InteractionListeners.add(type.ordinal, used, with, handler)
    }

    /**
     * Defines a function to handle the event when any item is used with another item.
     *
     * @param type The type of the item being used.
     * @param with The item(s) being used with.
     * @param handler The handler function to execute when the event occurs.
     * @receiver The context in which the function is called.
     */
    fun onUseAnyWith(type: IntType, vararg with: Int, handler: (player: Player, used: Node, with: Node) -> Boolean) {
        InteractionListeners.add(type.ordinal, with, handler)
    }

    /**
     * Defines a function that handles the action of using an item with a player.
     *
     * @param used The item(s) used
     * @param handler The handler function that takes player, used node, and with node as parameters
     * @receiver The context in which the function is called
     */
    fun onUseWithPlayer(vararg used: Int, handler: (player: Player, used: Node, with: Node) -> Boolean) {
        InteractionListeners.add(IntType.PLAYER.ordinal, used, handler)
    }

    /**
     * Defines a function to handle wildcard interactions.
     *
     * @param type The type of interaction.
     * @param predicate The condition for the interaction.
     * @param handler The action to perform on interaction.
     * @receiver The receiver of the interaction.
     *
     * Note: Using wildcard listeners incurs overhead on every interaction, so use them judiciously for items that require a response to every use-with interaction, like important quest NPCs.
     */
    fun onUseWithWildcard(type: IntType, predicate: (used: Int, with: Int) -> Boolean, handler: (player: Player, used: Node, with: Node) -> Boolean) {
        InteractionListeners.addWildcard(type.ordinal, predicate, handler)
    }

    /**
     * Function to handle equipment actions
     *
     * @param id The ID of the equipment
     * @param handler The handler function for equipment actions
     * @receiver The receiver of the equipment action
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
