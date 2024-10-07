package core.game.node.item

import core.tools.RandomFunction

/**
 * Weighted chance item.
 */
class WeightedChanceItem(
    val id: Int,
    val minimumAmount: Int,
    val maximumAmount: Int = minimumAmount,
    val weight: Int
) {

    /**
     * Constructs a new Weighted chance item with the same minimum and maximum amount.
     *
     * @param id     the id
     * @param amount the amount
     * @param weight the weight
     */
    constructor(id: Int, amount: Int, weight: Int) : this(id, amount, amount, weight)

    /**
     * Gets item.
     *
     * @return the item
     */
    fun getItem(): Item {
        return Item(id, RandomFunction.random(minimumAmount, maximumAmount))
    }
}