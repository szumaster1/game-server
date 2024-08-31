package content.global.skill.combat.magic

import core.api.hasLineOfSight
import core.api.log
import core.api.removeAttribute
import core.game.event.SpellCastEvent
import core.game.interaction.MovementPulse
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.SpellBookManager
import core.game.world.map.path.Pathfinder
import core.tools.Log

/**
 * Spell listeners.
 */
object SpellListeners {

    // A map to hold spell casting methods associated with their identifiers.
    val castMap = HashMap<String, (Player, Node?) -> Unit>()
    // A map to hold the casting ranges for spells.
    val spellRanges = HashMap<String, Int>()

    /**
     * Add a spell listener.
     *
     * @param spellID  The ID of the spell.
     * @param type     The type of the spell.
     * @param book     The spell book.
     * @param distance The casting distance of the spell.
     * @param method   The method to be invoked when the spell is cast.
     */
    fun add(spellID: Int, type: Int, book: String, distance: Int, method: (Player, Node?) -> Unit) {
        // Store the method in the castMap using a unique key.
        castMap["$book:$spellID:$type"] = method
        // Store the casting distance in the spellRanges map.
        spellRanges["$book:$spellID:$type"] = distance
    }

    /**
     * Add a spell listener with multiple IDs.
     *
     * @param spellID  The ID of the spell.
     * @param type     The type of the spell.
     * @param ids      The array of IDs.
     * @param book     The spell book.
     * @param distance The casting distance of the spell.
     * @param method   The method to be invoked when the spell is cast.
     */
    fun add(spellID: Int, type: Int, ids: IntArray, book: String, distance: Int, method: (Player, Node?) -> Unit) {
        // Iterate through each ID and store the method and distance in the maps.
        for (id in ids) {
            castMap["$book:$spellID:$type:$id"] = method
            spellRanges["$book:$spellID:$type:$id"] = distance
        }
    }

    /**
     * Get the casting range and method for a spell.
     *
     * @param spellID The ID of the spell.
     * @param type    The type of the spell.
     * @param book    The spell book.
     * @return a pair containing the casting range and the method to be invoked.
     */
    fun get(spellID: Int, type: Int, book: String): Pair<Int, ((Player, Node?) -> Unit)?> {
        // Log the retrieval of the spell information.
        log(this::class.java, Log.FINE, "Getting $book:$spellID:$type")
        // Return the casting range and method, defaulting to a range of 10 if not found.
        return Pair(spellRanges["$book:$spellID:$type"] ?: 10, castMap["$book:$spellID:$type"])
    }

    /**
     * Get the casting range and method for a spell with a specific ID.
     *
     * @param spellID The ID of the spell.
     * @param type    The type of the spell.
     * @param id      The ID of the spell instance.
     * @param book    The spell book.
     * @return a pair containing the casting range and the method to be invoked.
     */
    fun get(spellID: Int, type: Int, id: Int, book: String): Pair<Int, ((Player, Node?) -> Unit)?> {
        // Log the retrieval of the spell information with a specific ID.
        log(this::class.java, Log.FINE, "Getting $book:$spellID:$type:$id")
        // Return the casting range and method, defaulting to a range of 10 if not found.
        return Pair(spellRanges["$book:$spellID:$type:$id"] ?: 10, castMap["$book:$spellID:$type:$id"])
    }

    /**
     * Run the spell casting process.
     *
     * @param button The button ID.
     * @param type   The type of the spell.
     * @param book   The spell book.
     * @param player The player casting the spell.
     * @param node   The target node of the spell.
     */
    @JvmStatic
    fun run(button: Int, type: Int, book: String, player: Player, node: Node? = null) {
        // Retrieve the casting range and method for the spell.
        var (range, method) = get(button, type, node?.id ?: 0, book)
        // If no method is found, retrieve the default method and range.
        if (method == null) {
            var next = get(button, type, book)
            range = next.first
            method = next.second ?: return
        }

        // Check if the spell type is one of the specified types.
        if (type in intArrayOf(SpellListener.NPC, SpellListener.OBJECT, SpellListener.PLAYER, SpellListener.GROUND_ITEM)) {
            // Create a movement pulse for the player to cast the spell.
            player.pulseManager.run(object : MovementPulse(player, node, Pathfinder.SMART) {
                override fun pulse(): Boolean {
                    try {
                        // Invoke the spell method with the player and target node.
                        method.invoke(player, node)
                    } catch (e: IllegalStateException) {
                        // Remove the spell runes attribute if an exception occurs.
                        player.removeAttribute("spell:runes")
                        return true
                    }
                    return true
                }

                override fun update(): Boolean {
                    // Check if the player is within range and has line of sight to the target node.
                    if (player.location.withinMaxnormDistance(node!!.centerLocation, range) && hasLineOfSight(player, node)) {
                        // Face the target node and reset the walking queue.
                        player.faceLocation(node.getFaceLocation(player.location))
                        player.walkingQueue.reset()
                        pulse() // Execute the pulse method.
                        stop() // Stop the movement pulse.
                        return true
                    }
                    return super.update() // Call the superclass update method.
                }
            })
        } else {
            try {
                // Invoke the spell method directly if not a movement pulse.
                method.invoke(player, node)
                // Dispatch a spell cast event.
                player.dispatch(SpellCastEvent(SpellBookManager.SpellBook.valueOf(book.uppercase()), button, node))
            } catch (e: IllegalStateException) {
                // Remove the spell runes attribute if an exception occurs.
                removeAttribute(player, "spell:runes")
                return
            }
        }
    }
}
