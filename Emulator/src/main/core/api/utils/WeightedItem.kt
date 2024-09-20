package core.api.utils

import core.game.node.item.Item
import core.tools.RandomFunction

/**
 * Represents a weighted item.
 */
class WeightedItem(var id: Int, var minAmt: Int, var maxAmt: Int, var weight: Double, var guaranteed: Boolean = false) {
    /**
     * Retrieves an item based on the weighted item's parameters.
     *
     * @return An instance of Item with a random amount between minAmt and maxAmt.
     */
    fun getItem(): Item {
        return Item(id, RandomFunction.random(minAmt, maxAmt))
    }
}