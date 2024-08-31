package core.game.interaction

import content.global.handlers.item.SpadeDig
import core.api.ContentInterface
import core.game.node.Node
import core.game.node.entity.Entity
import core.game.node.entity.player.Player
import core.game.world.map.Location

/**
 * Interaction listener.
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
     * @param id The identifier for the item being unequipped.
     * @param handler A function that handles the unequip action.
     * @receiver This function is an extension function for a specific receiver type.
     */
    fun onUnequip(id: Int, handler: (player: Player, node: Node) -> Boolean) {
        // Add the unequip listener for the specified item ID and handler function.
        InteractionListeners.addUnequip(id, handler)
    }

    /**
     * On equip
     *
     * @param ids An array of identifiers for the items being equipped.
     * @param handler A function that handles the equip action.
     * @receiver This function is an extension function for a specific receiver type.
     */
    fun onEquip(ids: IntArray, handler: (player: Player, node: Node) -> Boolean) {
        // Iterate through each ID and add an equip listener for each item.
        ids.forEach { id -> InteractionListeners.addEquip(id, handler) }
    }

    /**
     * On unequip
     *
     * @param ids An array of identifiers for the items being unequipped.
     * @param handler A function that handles the unequip action.
     * @receiver This function is an extension function for a specific receiver type.
     */
    fun onUnequip(ids: IntArray, handler: (player: Player, node: Node) -> Boolean) {
        // Iterate through each ID and add an unequip listener for each item.
        ids.forEach { id -> InteractionListeners.addUnequip(id, handler) }
    }

    /**
     * Define destination overrides
     *
     * This function is a placeholder for defining destination overrides.
     */
    fun defineDestinationOverrides() {}

    /**
     * Set dest
     *
     * @param type The type of the destination.
     * @param id The identifier for the destination.
     * @param handler A function that determines the location based on the entity and node.
     * @receiver This function is an extension function for a specific receiver type.
     */
    fun setDest(type: IntType, id: Int, handler: (Entity, Node) -> Location) {
        // Add a destination override for the specified type and ID with the provided handler.
        InteractionListeners.addDestOverride(type.ordinal, id, handler)
    }

    /**
     * Set dest
     *
     * @param type The type of the destination.
     * @param options A variable number of options for the destination.
     * @param handler A function that determines the location based on the entity and node.
     * @receiver This function is an extension function for a specific receiver type.
     */
    fun setDest(type: IntType, vararg options: String, handler: (Entity, Node) -> Location) {
        // Add destination overrides for the specified type and options with the provided handler.
        InteractionListeners.addDestOverrides(type.ordinal, options, handler)
    }

    /**
     * Set dest
     *
     * @param type The type of the destination.
     * @param ids An array of identifiers for the destinations.
     * @param options A variable number of options for the destination.
     * @param handler A function that determines the location based on the entity and node.
     * @receiver This function is an extension function for a specific receiver type.
     */
    fun setDest(type: IntType, ids: IntArray, vararg options: String, handler: (Entity, Node) -> Location) {
        // Add destination overrides for the specified type, IDs, and options with the provided handler.
        InteractionListeners.addDestOverrides(type.ordinal, ids, options, handler)
    }

    /**
     * On dig
     *
     * @param location The location where the digging action occurs.
     * @param method A function that handles the digging action for the player.
     * @receiver This function is an extension function for a specific receiver type.
     */
    fun onDig(location: Location, method: (player: Player) -> Unit) {
        // Register a listener for the digging action at the specified location.
        SpadeDig.registerListener(location, method)
    }

    /**
     * Flag instant
     *
     * This function flags the current class as an instant class for interaction listeners.
     */
    fun flagInstant() {
        // Get the name of the current class and add it to the instant classes list.
        val name = this::class.java.name
        InteractionListeners.instantClasses.add(name)
    }

    /**
     * Define interaction
     *
     * @param type The type of interaction.
     * @param ids An array of identifiers for the items involved in the interaction.
     * @param options A variable number of options for the interaction.
     * @param persistent A flag indicating if the interaction should persist.
     * @param allowedDistance The maximum distance allowed for the interaction.
     * @param handler A function that handles the interaction.
     * @receiver This function is an extension function for a specific receiver type.
     */
    fun defineInteraction(type: IntType, ids: IntArray, vararg options: String, persistent: Boolean = false, allowedDistance: Int = 1, handler: (player: Player, node: Node, state: Int) -> Boolean) {
        // Add metadata for the interaction with the specified parameters.
        InteractionListeners.addMetadata(ids, type, options, InteractionMetadata(handler, allowedDistance, persistent))
    }

    /**
     * Define interaction
     *
     * @param type The type of interaction.
     * @param options A variable number of options for the interaction.
     * @param persist A flag indicating if the interaction should persist.
     * @param allowedDistance The maximum distance allowed for the interaction.
     * @param handler A function that handles the interaction.
     * @receiver This function is an extension function for a specific receiver type.
     */
    fun defineInteraction(type: IntType, vararg options: String, persist: Boolean = false, allowedDistance: Int = 1, handler: (player: Player, node: Node, state: Int) -> Boolean) {
        // Add generic metadata for the interaction with the specified parameters.
        InteractionListeners.addGenericMetadata(options, type, InteractionMetadata(handler, allowedDistance, persist))
    }

    /**
     * Interaction metadata
     *
     * @param handler A function that handles the interaction.
     * @param distance The maximum distance allowed for the interaction.
     * @param persist A flag indicating if the interaction should persist.
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
     * @param handler A function that handles the use action with metadata.
     * @param distance The maximum distance allowed for the use action.
     * @param persist A flag indicating if the use action should persist.
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
     * This function is a placeholder for defining interaction listeners.
     */
    fun defineListeners() {}
}
