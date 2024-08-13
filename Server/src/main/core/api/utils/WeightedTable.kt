package core.api.utils

import core.tools.RandomFunction

/**
 * Weighted table.
 *
 * @param T The type of elements in the table.
 * @constructor Represents a new instance of the WeightedTable class.
 */
class WeightedTable<T> : ArrayList<Pair<T?, Double>>() {
    var totalWeight: Double = 0.0 // Initialize total weight to zero

    /**
     * Add an element with a specified weight to the table.
     *
     * @param element The element to add.
     * @param weight The weight of the element.
     * @return True if the element was added successfully, false otherwise.
     */
    fun add(element: T?, weight: Double): Boolean {
        totalWeight += weight // Update total weight by adding the new weight
        return super.add(Pair(element, weight)) // Add the element and its weight as a pair
    }

    /**
     * Remove an element from the table.
     *
     * @param element The element to remove.
     * @return True if the element was removed successfully, false otherwise.
     */
    fun remove(element: T?): Boolean {
        var index = -1 // Initialize index to -1 to indicate not found
        for ((i, pair) in this.withIndex()) { // Iterate through the list with index
            val (elem, _) = pair // Destructure the pair to get the element
            if (element == elem) { // Check if the current element matches the target
                index = i // Update index if found
                break // Exit the loop once found
            }
        }
        if (index == -1) return false // Return false if the element was not found

        this.removeAt(index) // Remove the element at the found index
        return true // Return true indicating successful removal
    }

    override fun removeAt(index: Int): Pair<T?, Double> {
        val (_, weight) = this[index] // Destructure the pair to get the weight
        totalWeight -= weight // Decrease total weight by the removed element's weight
        return super.removeAt(index) // Call the superclass method to remove the element
    }

    /**
     * Roll the weighted table and return a random element based on their weights.
     *
     * @return A random element from the table, or null if the table is empty.
     */
    fun roll(): T? {
        if (this.size == 1) return this[0].component1() // Return the only element if size is 1
        else if (this.size == 0) return null // Return null if the table is empty

        var shuffled = this.shuffled() // Shuffle the elements for randomness
        var randWeight = RandomFunction.random(0.0, totalWeight) // Generate a random weight

        for ((element, weight) in shuffled) { // Iterate through the shuffled elements
            randWeight -= weight // Decrease random weight by the current element's weight
            if (randWeight <= 0) return element // Return the element if the weight threshold is met
        }

        return null // Return null if no element is selected
    }

    companion object {
        /**
         * Create a new weighted table with the specified elements and their weights.
         *
         * @param elements The elements and their weights.
         * @return A new weighted table.
         */
        fun <T> create(vararg elements: Pair<T?, Double>): WeightedTable<T> {
            var table = WeightedTable<T>() // Instantiate a new WeightedTable
            for ((element, weight) in elements) table.add(element, weight) // Add each element and weight to the table
            return table // Return the populated weighted table
        }
    }
}
