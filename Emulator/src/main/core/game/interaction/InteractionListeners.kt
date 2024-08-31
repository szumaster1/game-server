package core.game.interaction

import core.game.event.InteractionEvent
import core.game.event.UseWithEvent
import core.game.node.Node
import core.game.node.entity.Entity
import core.game.node.entity.player.Player
import core.game.world.map.Location

/**
 * Interaction listeners.
 */
object InteractionListeners {
    private val listeners = HashMap<String, (Player, Node) -> Boolean>(1000)
    private val useWithListeners = HashMap<String, (Player, Node, Node) -> Boolean>(1000)
    private val useAnyWithListeners = HashMap<String, (Player, Node, Node) -> Boolean>(10)
    private val useWithWildcardListeners =
        HashMap<Int, ArrayList<Pair<(Int, Int) -> Boolean, (Player, Node, Node) -> Boolean>>>(10)
    private val destinationOverrides = HashMap<String, (Entity, Node) -> Location>(100)
    private val equipListeners = HashMap<String, (Player, Node) -> Boolean>(10)
    private val interactions = HashMap<String, InteractionListener.InteractionMetadata>()
    private val useWithInteractions = HashMap<String, InteractionListener.UseWithMetadata>()
    val instantClasses = HashSet<String>()

    /**
     * Add a listener for a specific id, type, and options.
     *
     * @param id The unique identifier for the listener.
     * @param type The type of the listener.
     * @param option An array of options associated with the listener.
     * @param method The function to be executed when the listener is triggered.
     * @receiver This function is a static method that can be called without an instance.
     */
    @JvmStatic
    fun add(id: Int, type: Int, option: Array<out String>, method: (Player, Node) -> Boolean) {
        for (opt in option) {
            // Construct a unique key for the listener using id, type, and option.
            val key = "$id:$type:${opt.lowercase()}"
            // Validate if the key is already in use.
            if (!validate(key)) {
                // Throw an exception if the key is already defined.
                throw IllegalStateException("$opt for $id with type ${IntType.values()[type].name} already defined! Existing use: [${listeners[key]!!::class}]")
            }
            // Register the method for the constructed key.
            listeners[key] = method
        }
    }

    /**
     * Validate if a key is not already in use.
     *
     * @param key The key to validate.
     * @return True if the key is not in use, otherwise false.
     */
    private fun validate(key: String): Boolean {
        // Check if the key exists in either listeners or useWithListeners.
        return !listeners.containsKey(key) && !useWithListeners.containsKey(key)
    }

    /**
     * Add a listener for multiple ids.
     *
     * @param ids An array of unique identifiers for the listeners.
     * @param type The type of the listener.
     * @param option An array of options associated with the listener.
     * @param method The function to be executed when the listener is triggered.
     * @receiver This function is a static method that can be called without an instance.
     */
    @JvmStatic
    fun add(ids: IntArray, type: Int, option: Array<out String>, method: (Player, Node) -> Boolean) {
        // Iterate through each id and call the single id add method.
        for (id in ids) {
            add(id, type, option, method)
        }
    }

    /**
     * Add a catchall listener for a specific option and type.
     *
     * @param option The option associated with the listener.
     * @param type The type of the listener.
     * @param method The function to be executed when the listener is triggered.
     * @receiver This function is a static method that can be called without an instance.
     */
    @JvmStatic
    fun add(option: String, type: Int, method: (Player, Node) -> Boolean) {
        // Construct a unique key for the catchall listener.
        val key = "$type:${option.lowercase()}"
        // Validate if the key is already in use.
        if (!validate(key)) {
            // Throw an exception if the key is already defined.
            throw IllegalStateException("Catchall listener for $option on type ${IntType.values()[type].name} already in use: ${listeners[key]!!::class}")
        }
        // Register the method for the constructed key.
        listeners[key] = method
    }

    /**
     * Add listeners for multiple options.
     *
     * @param options An array of options associated with the listeners.
     * @param type The type of the listener.
     * @param method The function to be executed when the listener is triggered.
     * @receiver This function is a static method that can be called without an instance.
     */
    @JvmStatic
    fun add(options: Array<out String>, type: Int, method: (Player, Node) -> Boolean) {
        // Iterate through each option and call the single option add method.
        for (opt in options) {
            add(opt.lowercase(), type, method)
        }
    }

    /**
     * Add a listener for a specific used and with combination.
     *
     * @param used The identifier for the used item.
     * @param with The identifier for the with item.
     * @param type The type of the listener.
     * @param method The function to be executed when the listener is triggered.
     * @receiver This function is a static method that can be called without an instance.
     */
    @JvmStatic
    fun add(used: Int, with: Int, type: Int, method: (Player, Node, Node) -> Boolean) {
        // Construct unique keys for the listener.
        val key = "$used:$with:$type"
        val altKey = "$with:$used:$type"
        // Validate if either key is already in use.
        if (!validate(key) || !validate(altKey)) {
            // Throw an exception if either key is already defined.
            throw IllegalStateException("Usewith using $used with $with for type ${IntType.values()[type]} already in use: [${(useWithListeners[key] ?: useWithListeners[altKey])!!::class}")
        }
        // Register the method for the constructed key.
        useWithListeners[key] = method
    }

    /**
     * Add listeners for multiple with identifiers.
     *
     * @param type The type of the listener.
     * @param used The identifier for the used item.
     * @param with An array of identifiers for the with items.
     * @param method The function to be executed when the listener is triggered.
     * @receiver This function is a static method that can be called without an instance.
     */
    @JvmStatic
    fun add(type: Int, used: Int, with: IntArray, method: (Player, Node, Node) -> Boolean) {
        // Iterate through each id in with and call the used-with add method.
        for (id in with) {
            add(used = used, with = id, type = type, method = method)
        }
    }

    /**
     * Add a wildcard listener for a specific type.
     *
     * @param type The type of the listener.
     * @param predicate A function to determine if the listener should be triggered.
     * @param handler The function to be executed when the listener is triggered.
     * @receiver This function is a static method that can be called without an instance.
     */
    @JvmStatic
    fun addWildcard(
        type: Int,
        predicate: (used: Int, with: Int) -> Boolean,
        handler: (player: Player, used: Node, with: Node) -> Boolean
    ) {
        // Check if the type already has wildcard listeners.
        if (!useWithWildcardListeners.containsKey(type)) {
            // Initialize the list if it does not exist.
            useWithWildcardListeners.put(type, ArrayList())
        }
        // Add the predicate and handler as a pair to the list of wildcard listeners.
        useWithWildcardListeners[type]!!.add(Pair(predicate, handler))
    }

    /**
     * Add an equip listener for a specific id.
     *
     * @param id The unique identifier for the equip listener.
     * @param method The function to be executed when the equip listener is triggered.
     * @receiver This function is a static method that can be called without an instance.
     */
    @JvmStatic
    fun addEquip(id: Int, method: (Player, Node) -> Boolean) {
        // Register the equip method for the constructed key.
        equipListeners["equip:$id"] = method
    }

    /**
     * Add an unequip listener for a specific id.
     *
     * @param id The unique identifier for the unequip listener.
     * @param method The function to be executed when the unequip listener is triggered.
     * @receiver This function is a static method that can be called without an instance.
     */
    @JvmStatic
    fun addUnequip(id: Int, method: (Player, Node) -> Boolean) {
        // Register the unequip method for the constructed key.
        equipListeners["unequip:$id"] = method
    }

    /**
     * Get the equip listener for a specific id.
     *
     * @param id The unique identifier for the equip listener.
     * @return The function associated with the equip listener, or null if not found.
     */
    @JvmStatic
    fun getEquip(id: Int): ((Player, Node) -> Boolean)? {
        // Retrieve the equip listener for the constructed key.
        return equipListeners["equip:$id"]
    }

    /**
     * Get the unequip listener for a specific id.
     *
     * @param id The unique identifier for the unequip listener.
     * @return The function associated with the unequip listener, or null if not found.
     */
    @JvmStatic
    fun getUnequip(id: Int): ((Player, Node) -> Boolean)? {
        // Retrieve the unequip listener for the constructed key.
        return equipListeners["unequip:$id"]
    }

    /**
     * Get the listener for a specific used and with combination.
     *
     * @param used The identifier for the used item.
     * @param with The identifier for the with item.
     * @param type The type of the listener.
     * @return The function associated with the listener, or null if not found.
     */
    @JvmStatic
    fun get(used: Int, with: Int, type: Int): ((Player, Node, Node) -> Boolean)? {
        // Attempt to retrieve the method from useWithListeners or useAnyWithListeners.
        val method = useWithListeners["$used:$with:$type"] ?: useAnyWithListeners["$with:$type"]
        if (method != null) {
            return method
        }
        // Check for wildcard listeners if no direct match is found.
        val handlers = useWithWildcardListeners[type] ?: return null
        for (pair in handlers) {
            // If the predicate matches, return the associated handler.
            if (pair.first(used, with)) {
                return pair.second
            }
        }
        return null
    }

    /**
     * Get the listener for a specific id, type, and option.
     *
     * @param id The unique identifier for the listener.
     * @param type The type of the listener.
     * @param option The option associated with the listener.
     * @return The function associated with the listener, or null if not found.
     */
    @JvmStatic
    fun get(id: Int, type: Int, option: String): ((Player, Node) -> Boolean)? {
        // Retrieve the listener for the constructed key.
        return listeners["$id:$type:${option.lowercase()}"]
    }

    /**
     * Get the listener for a specific option and type.
     *
     * @param option The option associated with the listener.
     * @param type The type of the listener.
     * @return The function associated with the listener, or null if not found.
     */
    @JvmStatic
    fun get(option: String, type: Int): ((Player, Node) -> Boolean)? {
        // Retrieve the listener for the constructed key.
        return listeners["$type:${option.lowercase()}"]
    }

    /**
     * Add destination override for a specific type and id.
     *
     * @param type The type of the destination.
     * @param id The unique identifier for the destination.
     * @param method The function that defines the behavior for the destination.
     * @receiver This function is a static method.
     */
    @JvmStatic
    fun addDestOverride(type: Int, id: Int, method: (Entity, Node) -> Location) {
        // Store the method in the destinationOverrides map using a composite key of type and id.
        destinationOverrides["$type:$id"] = method
    }

    /**
     * Add multiple destination overrides for a specific type and a list of options.
     *
     * @param type The type of the destination.
     * @param options An array of option strings for the destination.
     * @param method The function that defines the behavior for the destination.
     * @receiver This function is a static method.
     */
    @JvmStatic
    fun addDestOverrides(type: Int, options: Array<out String>, method: (Entity, Node) -> Location) {
        // Iterate through each option and store the method in the destinationOverrides map.
        for (opt in options) {
            destinationOverrides["$type:${opt.lowercase()}"] = method
        }
    }

    /**
     * Add multiple destination overrides for a specific type, ids, and options.
     *
     * @param type The type of the destination.
     * @param ids An array of unique identifiers for the destinations.
     * @param options An array of option strings for the destination.
     * @param method The function that defines the behavior for the destination.
     * @receiver This function is a static method.
     */
    @JvmStatic
    fun addDestOverrides(type: Int, ids: IntArray, options: Array<out String>, method: (Entity, Node) -> Location) {
        // Iterate through each id and option to store the method in the destinationOverrides map.
        for (id in ids) {
            for (opt in options) {
                destinationOverrides["$type:$id:${opt.lowercase()}"] = method
            }
        }
    }

    /**
     * Retrieve a specific override based on type, id, and option.
     *
     * @param type The type of the destination.
     * @param id The unique identifier for the destination.
     * @param option The specific option string for the destination.
     * @return The function associated with the specified parameters, or null if not found.
     */
    @JvmStatic
    fun getOverride(type: Int, id: Int, option: String): ((Entity, Node) -> Location)? {
        // Retrieve the method from the destinationOverrides map using a composite key.
        return destinationOverrides["$type:$id:${option.lowercase()}"]
    }

    /**
     * Retrieve an override based on type and id.
     *
     * @param type The type of the destination.
     * @param id The unique identifier for the destination.
     * @return The function associated with the specified parameters, or null if not found.
     */
    @JvmStatic
    fun getOverride(type: Int, id: Int): ((Entity, Node) -> Location)? {
        // Retrieve the method from the destinationOverrides map using a composite key.
        return destinationOverrides["$type:$id"]
    }

    /**
     * Retrieve an override based on type and option.
     *
     * @param type The type of the destination.
     * @param option The specific option string for the destination.
     * @return The function associated with the specified parameters, or null if not found.
     */
    @JvmStatic
    fun getOverride(type: Int, option: String): ((Entity, Node) -> Location)? {
        // Retrieve the method from the destinationOverrides map using a composite key.
        return destinationOverrides["$type:$option"]
    }

    /**
     * Execute an action based on the id, player, node, and whether it is an equip action.
     *
     * @param id The unique identifier for the action.
     * @param player The player executing the action.
     * @param node The node associated with the action.
     * @param isEquip A boolean indicating if the action is an equip action.
     * @return A boolean indicating the success of the action.
     */
    @JvmStatic
    fun run(id: Int, player: Player, node: Node, isEquip: Boolean): Boolean {
        // Dispatch an interaction event based on whether it is an equip or unequip action.
        player.dispatch(InteractionEvent(node, if (isEquip) "equip" else "unequip"))
        // Invoke the appropriate listener based on the equip state.
        if (isEquip) {
            return equipListeners["equip:$id"]?.invoke(player, node) ?: true
        } else {
            return equipListeners["unequip:$id"]?.invoke(player, node) ?: true
        }
    }

    /**
     * Execute an action based on the used node, with node, type, and player.
     *
     * @param used The node that is being used.
     * @param with The node that is being interacted with.
     * @param type The type of interaction.
     * @param player The player executing the action.
     * @return A boolean indicating the success of the action.
     */
    @JvmStatic
    fun run(used: Node, with: Node, type: IntType, player: Player): Boolean {
        // Determine the destination flag based on the type of interaction.
        val flag = when (type) {
            IntType.NPC, IntType.PLAYER -> DestinationFlag.ENTITY
            IntType.SCENERY -> DestinationFlag.OBJECT
            IntType.GROUNDITEM -> DestinationFlag.ITEM
            else -> DestinationFlag.OBJECT
        }

        // Check if the player's interaction is locked.
        if (player.locks.isInteractionLocked) return false

        var flipped = false

        // Retrieve the appropriate method based on the type of interaction.
        val method = if (with is Player) get(-1, used.id, 4) ?: return false
        else get(used.id, with.id, type.ordinal) ?: if (type == IntType.ITEM)
            get(with.id, used.id, type.ordinal).also { flipped = true } ?: return false
        else return false

        // Determine the destination override based on the flipped state.
        val destOverride = if (flipped) {
            getOverride(type.ordinal, used.id, "use") ?: getOverride(type.ordinal, with.id) ?: getOverride(
                type.ordinal,
                "use"
            )
        } else {
            getOverride(type.ordinal, with.id, "use") ?: getOverride(type.ordinal, used.id) ?: getOverride(
                type.ordinal,
                "use"
            )
        }

        // Execute the action based on the type of interaction.
        if (type != IntType.ITEM && !isUseWithInstant(method)) {
            // Check if the player's movement is locked.
            if (player.locks.isMovementLocked) return false
            player.pulseManager.run(object : MovementPulse(player, with, flag, destOverride) {
                override fun pulse(): Boolean {
                    // Check if the player can use the item with the node.
                    if (player.zoneMonitor.useWith(used.asItem(), with)) {
                        return true
                    }
                    player.faceLocation(with.location)
                    // Dispatch the appropriate use event based on the flipped state.
                    if (flipped) player.dispatch(UseWithEvent(with.id, used.id))
                    else player.dispatch(UseWithEvent(used.id, with.id))
                    // Invoke the method based on the flipped state.
                    if (flipped) method.invoke(player, with, used)
                    else method.invoke(player, used, with)
                    return true
                }
            })
        } else {
            // Dispatch the appropriate use event based on the flipped state.
            if (flipped) player.dispatch(UseWithEvent(with.id, used.id))
            else player.dispatch(UseWithEvent(used.id, with.id))
            // Invoke the method based on the flipped state.
            if (flipped) return method.invoke(player, with, used)
            else return method.invoke(player, used, with)
        }
        return true
    }

    /**
     * Execute an action based on the id, type, option, player, and node.
     *
     * @param id The unique identifier for the action.
     * @param type The type of interaction.
     * @param option The specific option string for the action.
     * @param player The player executing the action.
     * @param node The node associated with the action.
     * @return A boolean indicating the success of the action.
     */
    @JvmStatic
    fun run(id: Int, type: IntType, option: String, player: Player, node: Node): Boolean {
        // Determine the destination flag based on the type of interaction.
        val flag = when (type) {
            IntType.PLAYER -> DestinationFlag.ENTITY
            IntType.GROUNDITEM -> DestinationFlag.ITEM
            IntType.NPC -> DestinationFlag.ENTITY
            IntType.SCENERY -> null
            else -> DestinationFlag.OBJECT
        }

        // Check if the player's interaction is locked.
        if (player.locks.isInteractionLocked) return false

        // Retrieve the appropriate method based on the id, type, and option.
        val method = get(id, type.ordinal, option) ?: get(option, type.ordinal)

        // Set the player's interaction attribute and dispatch an interaction event.
        player.setAttribute("interact:option", option.lowercase())
        player.dispatch(InteractionEvent(node, option.lowercase()))

        // If no method is found, retrieve the interaction from the interactions map.
        if (method == null) {
            val inter = interactions["${type.ordinal}:$id:${option.lowercase()}"]
                ?: interactions["${type.ordinal}:${option.lowercase()}"] ?: return false
            val script = Interaction(inter.handler, inter.distance, inter.persist)
            player.scripts.setInteractionScript(node, script)
            player.pulseManager.run(object : MovementPulse(player, node, flag) {
                override fun pulse(): Boolean {
                    return true
                }
            })
            return true
        }

        // Determine the destination override based on the type, id, and option.
        val destOverride = getOverride(type.ordinal, id, option) ?: getOverride(type.ordinal, node.id) ?: getOverride(
            type.ordinal,
            option.lowercase()
        )

        // Execute the action based on the type of interaction.
        if (type != IntType.ITEM && !isInstant(method)) {
            // Check if the player's movement is locked.
            if (player.locks.isMovementLocked) return false
            player.pulseManager.run(object : MovementPulse(player, node, flag, destOverride) {
                override fun pulse(): Boolean {
                    // Check if the player can interact with the node.
                    if (player.zoneMonitor.interact(node, Option(option, 0))) return true
                    player.faceLocation(node.location)
                    // Invoke the method for the interaction.
                    method.invoke(player, node)
                    return true
                }
            })
        } else {
            // Invoke the method for the interaction.
            method.invoke(player, node)
        }
        return true
    }

    /**
     * Add a handler for interactions based on type, used nodes, and with nodes.
     *
     * @param type The type of interaction.
     * @param used An array of unique identifiers for the used nodes.
     * @param with An array of unique identifiers for the with nodes.
     * @param handler The function that defines the behavior for the interaction.
     * @receiver This function is an instance method.
     */
    fun add(type: Int, used: IntArray, with: IntArray, handler: (Player, Node, Node) -> Boolean) {
        // Iterate through each used and with node to store the handler in the useWithListeners map.
        for (u in used) {
            for (w in with) {
                useWithListeners["$u:$w:$type"] = handler
            }
        }
    }

    /**
     * Add a handler for interactions based on type and with nodes.
     *
     * @param type The type of interaction.
     * @param with An array of unique identifiers for the with nodes.
     * @param handler The function that defines the behavior for the interaction.
     * @receiver This function is an instance method.
     */
    fun add(type: Int, with: IntArray, handler: (Player, Node, Node) -> Boolean) {
        // Iterate through each with node to store the handler in the useAnyWithListeners map.
        for (w in with) {
            useAnyWithListeners["$w:$type"] = handler
        }
    }

    /**
     * Check if the handler is an instant action.
     *
     * @param handler The function that defines the behavior for the interaction.
     * @receiver This function is an instance method.
     * @return A boolean indicating if the handler is an instant action.
     */
    fun isInstant(handler: (Player, Node) -> Boolean): Boolean {
        // Retrieve the class name of the handler and check if it is in the instantClasses list.
        val className = handler.javaClass.name.substringBefore("$")
        return instantClasses.contains(className)
    }

    /**
     * Check if the handler for use with is an instant action.
     *
     * @param handler The function that defines the behavior for the interaction.
     * @receiver This function is an instance method.
     * @return A boolean indicating if the handler is an instant action.
     */
    fun isUseWithInstant(handler: (player: Player, used: Node, with: Node) -> Boolean): Boolean {
        // Retrieve the class name of the handler and check if it is in the instantClasses list.
        val className = handler.javaClass.name.substringBefore("$")
        return instantClasses.contains(className)
    }

    /**
     * Add metadata for interactions based on ids, type, options, and metadata.
     *
     * @param ids An array of unique identifiers for the interactions.
     * @param type The type of interaction.
     * @param options An array of option strings for the interaction.
     * @param metadata The metadata associated with the interaction.
     */
    fun addMetadata(
        ids: IntArray,
        type: IntType,
        options: Array<out String>,
        metadata: InteractionListener.InteractionMetadata
    ) {
        // Iterate through each id and option to store the metadata in the interactions map.
        for (id in ids)
            for (opt in options)
                interactions["${type.ordinal}:$id:${opt.lowercase()}"] = metadata
    }

    /**
     * Add metadata for a single interaction based on id, type, options, and metadata.
     *
     * @param id The unique identifier for the interaction.
     * @param type The type of interaction.
     * @param options An array of option strings for the interaction.
     * @param metadata The metadata associated with the interaction.
     */
    fun addMetadata(
        id: Int,
        type: IntType,
        options: Array<out String>,
        metadata: InteractionListener.InteractionMetadata
    ) {
        // Iterate through each option to store the metadata in the interactions map.
        for (opt in options)
            interactions["${type.ordinal}:$id:${opt.lowercase()}"] = metadata
    }

    /**
     * Add generic metadata for interactions based on options, type, and metadata.
     *
     * @param options An array of option strings for the interaction.
     * @param type The type of interaction.
     * @param metadata The metadata associated with the interaction.
     */
    fun addGenericMetadata(
        options: Array<out String>,
        type: IntType,
        metadata: InteractionListener.InteractionMetadata
    ) {
        // Iterate through each option to store the metadata in the interactions map.
        for (opt in options)
            interactions["${type.ordinal}:$opt"] = metadata
    }

    /**
     * Add metadata for interactions based on used node, with nodes, type, and metadata.
     *
     * @param used The unique identifier for the used node.
     * @param with An array of unique identifiers for the with nodes.
     * @param type The type of interaction.
     * @param metadata The metadata associated with the interaction.
     */
    fun addMetadata(used: Int, with: IntArray, type: IntType, metadata: InteractionListener.UseWithMetadata) {
        // Iterate through each with node to store the metadata in the useWithInteractions map.
        for (id in with)
            useWithInteractions["${type.ordinal}:$used:$with"] = metadata
    }

    /**
     * Add metadata for a single interaction based on used node, with node, type, and metadata.
     *
     * @param used The unique identifier for the used node.
     * @param with The unique identifier for the with node.
     * @param type The type of interaction.
     * @param metadata The metadata associated with the interaction.
     */
    fun addMetadata(used: Int, with: Int, type: IntType, metadata: InteractionListener.UseWithMetadata) {
    }
}