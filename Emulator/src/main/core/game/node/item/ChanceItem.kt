package core.game.node.item

import core.game.node.entity.npc.drop.DropFrequency
import core.tools.RandomFunction

/**
 * Represents an item with a chance-rate.
 * @author Emperor
 */
class ChanceItem @JvmOverloads constructor(
    id: Int,
    minimumAmount: Int = 1,
    maximumAmount: Int = 1,
    charge: Int = 1000,
    chanceRate: Double = 1.0,
    frequency: DropFrequency? = DropFrequency.UNCOMMON,
    setRate: Int = -1
) :
    Item(id, minimumAmount, charge) {
    /**
     * @return the chanceRate.
     */
    /**
     * @param chanceRate the chanceRate to set.
     */
    /**
     * The chance rate of this item.
     */
    var chanceRate: Double = 0.0

    /**
     * @return the minimumAmount.
     */
    /**
     * @param minimumAmount the minimumAmount to set.
     */
    /**
     * The minimum amount.
     */
    var minimumAmount: Int = 0

    /**
     * @return the maximumAmount.
     */
    /**
     * @param maximumAmount the maximumAmount to set.
     */
    /**
     * The maximum amount.
     */
    var maximumAmount: Int = 0

    /**
     * @return the tableSlot.
     */
    /**
     * @param tableSlot the tableSlot to set.
     */
    /**
     * The table slot.
     */
    var tableSlot: Int = 0

    /**
     * @return the dropFrequency.
     */
    /**
     * @param dropFrequency the dropFrequency to set.
     */
    /**
     * The drop frequency.
     */
    var dropFrequency: DropFrequency? = null

    /**
     * Gets the setRate.
     * @return the setRate
     */
    /**
     * Sets the basetRate.
     * @param setRate the setRate to set.
     */
    /**
     * The set rate.
     */
    var setRate: Int = -1

    /**
     * Constructs a new `ChanceItem` `Object`.
     * @param id The item id.
     * @param minimumAmount The minimum amount.
     * @param chanceRate The chance rate.
     */
    constructor(id: Int, minimumAmount: Int, chanceRate: Double) : this(
        id,
        minimumAmount,
        minimumAmount,
        1000,
        chanceRate
    )

    /**
     * Constructs a new `ChanceItem` `Object`.
     * @param id The item id.
     * @param minimumAmount The minimum amount.
     * @param maximumAmount The maximum amount.
     * @param chanceRate The chance rate.
     */
    constructor(id: Int, minimumAmount: Int, maximumAmount: Int, chanceRate: Double) : this(
        id,
        minimumAmount,
        maximumAmount,
        1000,
        chanceRate
    )

    /**
     * Constructs a new `ChanceItem` `Object`.
     * @param id the id.
     * @param minimumAmount the min amount.
     * @param maximumAmount the max amount.
     * @param frequency the frequency.
     */
    constructor(id: Int, minimumAmount: Int, maximumAmount: Int, frequency: DropFrequency?) : this(
        id,
        minimumAmount,
        maximumAmount,
        1000,
        DropFrequency.rate(frequency).toDouble()
    )

    /**
     * Constructs a new `ChanceItem` `Object`.
     * @param id The item id.
     * @param minimumAmount The minimum amount.
     * @param maximumAmount The maximum amount.
     * @param charge The charge.
     * @param frequency The drop frequency.
     * @param chanceRate The chance rate.
     * @param setRate the set rate.
     */
    /**
     * Constructs a new `ChanceItem` `Object`.
     * @param id The item id.
     * @param minimumAmount The minimum amount.
     * @param maximumAmount The maximum amount.
     * @param charge The charge.
     * @param frequency The drop frequency.
     * @param chanceRate The chance rate.
     */
    /**
     * Constructs a new `ChanceItem` `Object`.
     * @param id The item id.
     * @param minimumAmount The minimum amount.
     * @param maximumAmount The maximum amount.
     * @param charge The charge.		if(frequency == DropFrequency.ALWAYS){
     *
     * }
     * @param chanceRate The chance rate.
     */
    /**
     * Constructs a new `ChanceItem` `Object`.
     * @param id The item id.
     */
    init {
        this.minimumAmount = minimumAmount
        this.maximumAmount = maximumAmount
        this.chanceRate = chanceRate
        this.dropFrequency = frequency
        this.setRate = setRate
    }

    constructor(id: Int, amount: Int, frequency: DropFrequency?) : this(id, amount, amount, frequency)

    val randomItem: Item
        /**
         * Gets the item instance.
         * @return the item.
         */
        get() {
            if (minimumAmount == maximumAmount) {
                return Item(id, minimumAmount)
            }
            return Item(id, RandomFunction.random(minimumAmount, maximumAmount))
        }

    val copy: ChanceItem
        /**
         * Gets a copy.
         * @return the copy.
         */
        get() {
            val newItem = ChanceItem(id, minimumAmount, maximumAmount, charge, chanceRate, dropFrequency)
            return newItem
        }

    override fun toString(): String {
        return "ChanceItem " + super.toString() + " [chanceRate=" + chanceRate + ", minimumAmount=" + minimumAmount + ", maximumAmount=" + maximumAmount + ", tableSlot=" + tableSlot + ", dropFrequency=" + dropFrequency + "]"
    }

    companion object {
        val DROP_RATES: IntArray = intArrayOf(5, 15, 150, 750)

        /**
         * Gets a random chance item from the table.
         * @param table The table.
         * @return The chance item.
         */
        fun getItem(vararg table: ChanceItem): ChanceItem {
            return getItem(RandomFunction.getRandomDouble(75.0), *table)
        }

        /**
         * Gets a random chance item from the table.
         * @param table The table.
         * @return The chance item.
         */
        fun getItem(chance: Double, vararg table: ChanceItem): ChanceItem {
            // TODO:
            return table[0]
        }
    }
}