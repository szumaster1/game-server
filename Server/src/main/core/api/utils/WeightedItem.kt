package core.api.utils

import core.game.node.item.Item
import core.tools.RandomFunction

/**
 * Represents a weighted item with specific attributes.
 *
 * @param id Unique identifier for the item.
 * @param minAmt Minimum amount of the item that can be generated.
 * @param maxAmt Maximum amount of the item that can be generated.
 * @param weight The weight of the item, influencing its probability of being selected.
 * @param guaranteed Indicates if the item is guaranteed to be generated.
 * @constructor Creates a weighted item with the specified parameters.
 */
class WeightedItem(var id: Int, var minAmt: Int, var maxAmt: Int, var weight: Double, var guaranteed: Boolean = false) {
    /**
     * Retrieves an item based on the weighted item's parameters.
     *
     * @return An instance of Item with a random amount between minAmt and maxAmt.
     */
    fun getItem(): Item {
        // Create and return a new Item instance with a random amount
        return Item(id, RandomFunction.random(minAmt, maxAmt))
    }
}