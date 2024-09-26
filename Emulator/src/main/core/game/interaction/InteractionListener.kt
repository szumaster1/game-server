package core.game.interaction

import content.global.handlers.item.SpadeDigUtils
import core.api.ContentInterface
import core.game.node.Node
import core.game.node.entity.Entity
import core.game.node.entity.player.Player
import core.game.world.map.Location

/**
 * Represents an interaction listener interface for handling various game interactions.
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
     * Registers a handler for a specific interaction event.
     *
     * @param id The unique identifier for the interaction event.
     * @param type The type of the interaction event.
     * @param option Additional options related to the event.
     * @param handler The function to handle the interaction event.
     */
    fun on(id: Int, type: IntType, vararg option: String, handler: (player: Player, node: Node) -> Boolean) {
        InteractionListeners.add(id, type.ordinal, option, handler)
    }

    /**
     * Registers a handler for multiple interaction events.
     *
     * @param ids An array of unique identifiers for the interaction events.
     * @param type The type of the interaction events.
     * @param option Additional options related to the events.
     * @param handler The function to handle the interaction events.
     */
    fun on(ids: IntArray, type: IntType, vararg option: String, handler: (player: Player, node: Node) -> Boolean) {
        InteractionListeners.add(ids, type.ordinal, option, handler)
    }

    /**
     * Registers a handler for an interaction event based on an option and type.
     *
     * @param option The option related to the interaction event.
     * @param type The type of the interaction event.
     * @param handler The function to handle the interaction event.
     * @deprecated This method should not be used.
     */
    @Deprecated("Don't use")
    fun on(option: String, type: IntType, handler: (player: Player, node: Node) -> Boolean) {
        InteractionListeners.add(option, type.ordinal, handler)
    }

    /**
     * Registers a handler for an interaction event based on type and options.
     *
     * @param type The type of the interaction event.
     * @param option Additional options related to the event.
     * @param handler The function to handle the interaction event.
     */
    fun on(type: IntType, vararg option: String, handler: (player: Player, node: Node) -> Boolean) {
        InteractionListeners.add(option, type.ordinal, handler)
    }

    /**
     * Registers a handler for using an item with another item.
     *
     * @param type The type of interaction.
     * @param used The item being used.
     * @param with The item(s) being interacted with.
     * @param handler The function to handle the interaction.
     */
    fun onUseWith(
        type: IntType,
        used: Int,
        vararg with: Int,
        handler: (player: Player, used: Node, with: Node) -> Boolean
    ) {
        InteractionListeners.add(type.ordinal, used, with, handler)
    }

    /**
     * Registers a handler for using an item with multiple other items.
     *
     * @param type The type of interaction.
     * @param used The items being used.
     * @param with The item(s) being interacted with.
     * @param handler The function to handle the interaction.
     */
    fun onUseWith(
        type: IntType,
        used: IntArray,
        vararg with: Int,
        handler: (player: Player, used: Node, with: Node) -> Boolean
    ) {
        InteractionListeners.add(type.ordinal, used, with, handler)
    }

    /**
     * Registers a handler for using any item with a specific item.
     *
     * @param type The type of the item being used.
     * @param with The item(s) being interacted with.
     * @param handler The function to handle the interaction.
     */
    fun onUseAnyWith(type: IntType, vararg with: Int, handler: (player: Player, used: Node, with: Node) -> Boolean) {
        InteractionListeners.add(type.ordinal, with, handler)
    }

    /**
     * Registers a handler for using an item with a player.
     *
     * @param used The item(s) being used.
     * @param handler The function to handle the interaction.
     */
    fun onUseWithPlayer(vararg used: Int, handler: (player: Player, used: Node, with: Node) -> Boolean) {
        InteractionListeners.add(IntType.PLAYER.ordinal, used, handler)
    }

    /**
     * Registers a wildcard handler for using any item with another item based on a condition.
     *
     * @param type The type of interaction.
     * @param predicate The condition to be checked.
     * @param handler The function to handle the interaction.
     *
     * Note: Wildcard listeners may add overhead, so they should be used sparingly, especially for high-priority interactions like quest-related NPCs.
     */
    fun onUseWithWildcard(
        type: IntType,
        predicate: (used: Int, with: Int) -> Boolean,
        handler: (player: Player, used: Node, with: Node) -> Boolean
    ) {
        InteractionListeners.addWildcard(type.ordinal, predicate, handler)
    }

    /**
     * Registers a handler for an equipment action.
     *
     * @param id The ID of the equipment.
     * @param handler The function to handle the equipment action.
     */
    fun onEquip(id: Int, handler: (player: Player, node: Node) -> Boolean) {
        InteractionListeners.addEquip(id, handler)
    }

    /**
     * Registers a handler for an unequip action.
     *
     * @param id The ID of the item being unequipped.
     * @param handler The function to handle the unequip action.
     */
    fun onUnequip(id: Int, handler: (player: Player, node: Node) -> Boolean) {
        InteractionListeners.addUnequip(id, handler)
    }

    /**
     * Registers a handler for multiple equipment actions.
     *
     * @param ids The IDs of the items being equipped.
     * @param handler The function to handle the equipment actions.
     */
    fun onEquip(ids: IntArray, handler: (player: Player, node: Node) -> Boolean) {
        ids.forEach { id -> InteractionListeners.addEquip(id, handler) }
    }

    /**
     * Registers a handler for multiple unequip actions.
     *
     * @param ids The IDs of the items being unequipped.
     * @param handler The function to handle the unequip actions.
     */
    fun onUnequip(ids: IntArray, handler: (player: Player, node: Node) -> Boolean) {
        ids.forEach { id -> InteractionListeners.addUnequip(id, handler) }
    }

    /**
     * Placeholder for defining destination overrides in interactions.
     */
    fun defineDestinationOverrides() {}

    /**
     * Sets a destination handler for an interaction based on type and ID.
     *
     * @param type The type of the destination.
     * @param id The ID of the destination.
     * @param handler The function to determine the location.
     */
    fun setDest(type: IntType, id: Int, handler: (Entity, Node) -> Location) {
        InteractionListeners.addDestOverride(type.ordinal, id, handler)
    }

    /**
     * Sets a destination handler for an interaction based on type and options.
     *
     * @param type The type of the destination.
     * @param options Additional options for the destination.
     * @param handler The function to determine the location.
     */
    fun setDest(type: IntType, vararg options: String, handler: (Entity, Node) -> Location) {
        InteractionListeners.addDestOverrides(type.ordinal, options, handler)
    }

    /**
     * Sets a destination handler for an interaction based on type, IDs, and options.
     *
     * @param type The type of the destination.
     * @param ids The IDs of the destinations.
     * @param options Additional options for the destinations.
     * @param handler The function to determine the location.
     */
    fun setDest(type: IntType, ids: IntArray, vararg options: String, handler: (Entity, Node) -> Location) {
        InteractionListeners.addDestOverrides(type.ordinal, ids, options, handler)
    }

    /**
     * Registers a handler for a digging action at a specific location.
     *
     * @param location The location where the digging action occurs.
     * @param method The function to handle the digging action.
     */
    fun onDig(location: Location, method: (player: Player) -> Unit) {
        SpadeDigUtils.registerListener(location, method)
    }

    /**
     * Flags the current class as an instant interaction class.
     *
     * This method adds the name of the current class to the list of instant interaction classes.
     */
    fun flagInstant() {
        val name = this::class.java.name
        InteractionListeners.instantClasses.add(name)
    }

    /**
     * Defines interaction metadata and registers it for a specific interaction type.
     *
     * @param type The type of interaction.
     * @param ids The IDs of the items involved in the interaction.
     * @param options Additional options for the interaction.
     * @param persistent Indicates if the interaction should persist.
     * @param allowedDistance The maximum distance allowed for the interaction.
     */
    fun defineInteraction(
        type: IntType,
        ids: IntArray,
        vararg options: String,
        persistent: Boolean = false,
        allowedDistance: Int = 1,
        handler: (player: Player, node: Node, state: Int) -> Boolean
    ) {
        InteractionListeners.addMetadata(ids, type, options, InteractionMetadata(handler, allowedDistance, persistent))
    }

    /**
     * A DSL (Domain Specific Language) method for defining instant interactions.
     *
     * This method provides a concise way to define interactions that should be handled immediately.
     *
     * @param definition A lambda expression that contains the logic for the instant interaction.
     */
    fun defineInteraction(
        type: IntType,
        vararg options: String,
        persist: Boolean = false,
        allowedDistance: Int = 1,
        handler: (player: Player, node: Node, state: Int) -> Boolean
    ) {
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
