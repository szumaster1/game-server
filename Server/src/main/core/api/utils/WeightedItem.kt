package core.api.utils

import core.game.node.item.Item
import core.tools.RandomFunction

/**
 * Weighted item
 *
 * @property id
 * @property minAmt
 * @property maxAmt
 * @property weight
 * @property guaranteed
 * @constructor Weighted item
 */
class WeightedItem(var id: Int, var minAmt: Int, var maxAmt: Int, var weight: Double, var guaranteed: Boolean = false) {
    /**
     * Get item
     *
     * @return
     */
    fun getItem(): Item {
        return Item(id, RandomFunction.random(minAmt, maxAmt))
    }
}
