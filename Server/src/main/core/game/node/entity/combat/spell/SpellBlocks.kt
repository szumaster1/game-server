package core.game.node.entity.combat.spell

import core.game.node.Node
import java.util.concurrent.atomic.AtomicBoolean
import java.util.function.Consumer

/**
 * Object to manage spell blocking functionality.
 */
object SpellBlocks {

    // A map to hold spell IDs and their corresponding list of nodes that can be blocked.
    private val blocks = HashMap<Int, MutableList<Node>?>()

    /**
     * Registers a node to be blocked for a specific spell ID.
     *
     * @param spellId The ID of the spell to register the block for.
     * @param toBlock The node that should be blocked.
     */
    @JvmStatic
    fun register(spellId: Int, toBlock: Node) {
        // Check if there is already a list of blocks for the given spell ID.
        if (blocks[spellId] != null) {
            // If it exists, add the new node to the existing list.
            blocks[spellId]!!.add(toBlock)
        } else {
            // If it does not exist, create a new list and add the node to it.
            val blockslist: MutableList<Node> = ArrayList(20) // Initialize a new list with an initial capacity of 20.
            blockslist.add(toBlock) // Add the node to the new list.
            blocks[spellId] = blockslist // Associate the new list with the spell ID in the map.
        }
    }

    /**
     * Checks if a specific node is blocked for a given spell ID.
     *
     * @param spellId The ID of the spell to check for blocks.
     * @param node The node to check if it is blocked.
     * @return True if the node is blocked, false otherwise.
     */
    @JvmStatic
    fun isBlocked(spellId: Int, node: Node): Boolean {
        val blocked = AtomicBoolean(false) // AtomicBoolean to safely manage the blocked state.

        // Check if there are any blocks registered for the given spell ID.
        if (blocks[spellId] == null) {
            return false // If no blocks exist, return false.
        }

        // Iterate through the list of blocked nodes for the spell ID.
        blocks[spellId]!!.forEach(Consumer { n: Node ->
            // Compare the names of the nodes to determine if the current node is blocked.
            if (node.name == n.name) {
                blocked.set(true) // Set blocked to true if a match is found.
            }
        })

        return blocked.get() // Return the final blocked state.
    }
}
