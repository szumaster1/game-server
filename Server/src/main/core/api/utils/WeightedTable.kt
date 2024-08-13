package core.api.utils

import core.tools.RandomFunction

/**
 * Weighted table.
 *
 * @param T The type of elements in the table.
 * @constructor Represents a new instance of the WeightedTable class.
 */
class WeightedTable<T> : ArrayList<Pair<T?, Double>>() {
    var totalWeight: Double = 0.0

    /**
     * Add an element with a specified weight to the table.
     *
     * @param element The element to add.
     * @param weight The weight of the element.
     * @return True if the element was added successfully, false otherwise.
     */
    fun add(element: T?, weight: Double): Boolean {
        totalWeight += weight
        return super.add(Pair(element, weight))
    }

    /**
     * Remove an element from the table.
     *
     * @param element The element to remove.
     * @return True if the element was removed successfully, false otherwise.
     */
    fun remove(element: T?): Boolean {
        var index = -1
        for ((i, pair) in this.withIndex()) {
            val (elem, _) = pair
            if (element == elem) {
                index = i
                break
            }
        }
        if (index == -1) return false

        this.removeAt(index)
        return true
    }

    override fun removeAt(index: Int): Pair<T?, Double> {
        val (_, weight) = this[index]
        totalWeight -= weight
        return super.removeAt(index)
    }

    /**
     * Roll the weighted table and return a random element based on their weights.
     *
     * @return A random element from the table, or null if the table is empty.
     */
    fun roll(): T? {
        if (this.size == 1) return this[0].component1()
        else if (this.size == 0) return null

        var shuffled = this.shuffled()
        var randWeight = RandomFunction.random(0.0, totalWeight)

        for ((element, weight) in shuffled) {
            randWeight -= weight
            if (randWeight <= 0) return element
        }

        return null
    }

    companion object {
        /**
         * Create a new weighted table with the specified elements and their weights.
         *
         * @param elements The elements and their weights.
         * @return A new weighted table.
         */
        fun <T> create(vararg elements: Pair<T?, Double>): WeightedTable<T> {
            var table = WeightedTable<T>()
            for ((element, weight) in elements) table.add(element, weight)
            return table
        }
    }
}
