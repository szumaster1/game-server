package content.minigame.blastfurnace

import content.global.skill.production.smithing.data.Bar
import content.minigame.blastfurnace.BlastFurnace.Companion.getBarForOreId
import content.minigame.blastfurnace.BlastFurnace.Companion.getNeededCoal
import content.minigame.blastfurnace.BlastUtils.BAR_LIMIT
import core.api.consts.Items
import core.game.node.item.Item
import org.json.simple.JSONArray
import org.json.simple.JSONObject

/**
 * Blast Furnace ore container.
 * @author Ceikry
 */
class BFOreContainer {
    private var coalRemaining = 0 //this is the actual important value needed for the calculations and is unit tested
    private var ores = Array(BlastUtils.ORE_LIMIT * 2) { -1 }
    private var barAmounts = Array(9) { 0 }

    /**
     * Add coal
     *
     * @param amount The amount of coal to add
     * @return The amount of coal that could not be added
     */
    fun addCoal(amount: Int): Int {
        val maxAdd = BlastUtils.COAL_LIMIT - coalRemaining
        val toAdd = amount.coerceAtMost(maxAdd)
        coalRemaining += toAdd
        return amount - toAdd
    }

    /**
     * Coal amount
     *
     * @return The amount of coal remaining
     */
    fun coalAmount(): Int {
        return coalRemaining
    }

    /**
     * Add ore
     *
     * @param id The ID of the ore to add
     * @param amount The amount of ore to add
     * @return The amount of ore that could not be added
     */
    fun addOre(id: Int, amount: Int): Int {
        if (id == Items.COAL_453) return addCoal(amount)

        var limit = BlastUtils.ORE_LIMIT
        if (getBarForOreId(id, -1, 99) == Bar.BRONZE) limit *= 2

        var amountLeft = amount
        var maxAdd = getAvailableSpace(id, 99)
        for (i in 0 until limit) {
            if (ores[i] == -1) {
                ores[i] = id
                if (--amountLeft == 0 || --maxAdd == 0) break
            }
        }
        return amountLeft
    }

    /**
     * Get ore amount
     *
     * @param id The ID of the ore
     * @return The amount of ore
     */
    fun getOreAmount(id: Int): Int {
        if (id == Items.COAL_453) return coalAmount()

        var oreCount = 0
        for (i in 0 until BlastUtils.ORE_LIMIT) {
            if (ores[i] == id) oreCount++
        }
        return oreCount
    }

    /**
     * Index of ore
     *
     * @param id The ID of the ore
     * @return The index of the ore in the container
     */
    fun indexOfOre(id: Int): Int {
        for (i in ores.indices) if (ores[i] == id) return i
        return -1
    }

    /**
     * Get ore amounts
     *
     * @return A HashMap containing the ore IDs and their respective amounts
     */
    fun getOreAmounts(): HashMap<Int, Int> {
        val map = HashMap<Int, Int>()
        for (ore in ores) {
            if (ore == -1) break
            map[ore] = (map[ore] ?: 0) + 1
        }
        return map
    }

    /**
     * Convert to bars
     *
     * @param level The Smithing level
     * @return The experience reward for converting the ores to bars
     */
    fun convertToBars(level: Int = 99): Double {
        val newOres = Array(BlastUtils.ORE_LIMIT * 2) { -1 }
        var oreIndex = 0
        var xpReward = 0.0
        for (i in 0 until BlastUtils.ORE_LIMIT) {
            val bar = getBarForOreId(ores[i], coalRemaining, level)

            if (bar == null) {
                ores[i] = -1
                continue
            }

            if (barAmounts[bar.ordinal] >= BAR_LIMIT) {
                newOres[oreIndex++] = ores[i]
                continue
            }

            val coalNeeded = getNeededCoal(bar)

            //special handling for bronze bar edge case, no other ore does this.
            if (bar == Bar.BRONZE) {
                val indexOfComplement = when (ores[i]) {
                    Items.COPPER_ORE_436 -> indexOfOre(Items.TIN_ORE_438)
                    Items.TIN_ORE_438 -> indexOfOre(Items.COPPER_ORE_436)
                    else -> -1
                }
                if (indexOfComplement == -1) {
                    newOres[oreIndex++] = ores[i]
                    continue
                }
                ores[indexOfComplement] = -1
            }

            if (coalRemaining >= coalNeeded) {
                coalRemaining -= coalNeeded
                ores[i] = -1
                barAmounts[bar.ordinal]++
                xpReward += bar.experience
            } else {
                newOres[oreIndex++] = ores[i]
            }
        }
        ores = newOres
        return xpReward
    }

    /**
     * Get bar amount
     *
     * @param bar The Bar type
     * @return The amount of bars
     */
    fun getBarAmount(bar: Bar): Int {
        return barAmounts[bar.ordinal]
    }

    /**
     * Get total bar amount
     *
     * @return The total amount of bars
     */
    fun getTotalBarAmount(): Int {
        var total = 0
        for (i in barAmounts) total += i
        return total
    }

    /**
     * Take bars
     *
     * @param bar The Bar type
     * @param amount The amount of bars to take
     * @return An Item object representing the taken bars
     */
    fun takeBars(bar: Bar, amount: Int): Item? {
        val amt = amount.coerceAtMost(barAmounts[bar.ordinal])
        if (amt == 0) return null

        barAmounts[bar.ordinal] -= amt
        return Item(bar.product.id, amt)
    }

    /**
     * Get available space
     *
     * @param ore The ID of the ore
     * @param level The Smithing level
     * @return The available space for the ore
     */
    fun getAvailableSpace(ore: Int, level: Int = 99): Int {
        if (ore == Items.COAL_453) return BlastUtils.COAL_LIMIT - coalRemaining

        var freeSlots = 0
        val bar = getBarForOreId(ore, coalRemaining, level)!!
        val oreAmounts = HashMap<Int, Int>()
        for (i in 0 until BlastUtils.ORE_LIMIT) if (ores[i] == -1) {
            var oreLimit = BlastUtils.ORE_LIMIT
            if (bar == Bar.BRONZE) oreLimit *= 2
            freeSlots = oreLimit - i
            break
        } else oreAmounts[ores[i]] = (oreAmounts[ores[i]] ?: 0) + 1

        val currentAmount = oreAmounts[ore] ?: 0
        freeSlots = (BlastUtils.ORE_LIMIT - currentAmount).coerceAtMost(freeSlots)

        return (freeSlots - getBarAmount(bar)).coerceAtLeast(0)
    }

    /**
     * Has any ore
     *
     * @return true if there is any ore in the container, false otherwise
     */
    fun hasAnyOre(): Boolean {
        return ores[0] != -1
    }

    /**
     * To json
     *
     * @return A JSONObject representing the BFOreContainer
     */
    fun toJson(): JSONObject {
        val root = JSONObject()
        val ores = JSONArray()
        val bars = JSONArray()

        for (ore in this.ores) ores.add(ore.toString())
        for (amount in barAmounts) bars.add(amount.toString())

        root["ores"] = ores
        root["bars"] = bars
        root["coal"] = coalRemaining.toString()
        return root
    }

    companion object {
        /**
         * Create a BFOreContainer from a JSONObject
         *
         * @param root The JSONObject representing the BFOreContainer
         * @return The created BFOreContainer object
         */
        fun fromJson(root: JSONObject): BFOreContainer {
            val cont = BFOreContainer()
            val jsonOres = root["ores"] as? JSONArray ?: return cont
            val jsonBars = root["bars"] as? JSONArray ?: return cont


            for (i in 0 until BlastUtils.ORE_LIMIT) cont.ores[i] = jsonOres[i].toString().toIntOrNull() ?: -1
            for (i in 0 until 9) cont.barAmounts[i] = jsonBars[i].toString().toIntOrNull() ?: 0
            cont.coalRemaining = root["coal"].toString().toIntOrNull() ?: 0
            return cont
        }
    }
}
