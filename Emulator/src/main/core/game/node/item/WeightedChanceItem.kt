package core.game.node.item

import core.tools.RandomFunction

class WeightedChanceItem(var id: Int, var minimum_amount: Int, var maximum_amount: Int, var weight: Int) {
    constructor(id: Int, amount: Int, weight: Int) : this(id, amount, amount, weight)

    val item: Item
        get() = Item(this.id, RandomFunction.random(this.minimum_amount, this.maximum_amount))
}