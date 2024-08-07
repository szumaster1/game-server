package content.minigame.blastfurnace

import core.tools.RandomFunction

/**
 * Blast state.
 */
class BlastState {
    var disableBreaking = false // Indicates whether breaking is disabled.
    var forceBreaking = false // Indicates whether breaking is forced.
    var potPipeBroken = false // Indicates whether the pot pipe is broken.
    var pumpPipeBroken = false // Indicates whether the pump pipe is broken.
    var beltBroken = false // Indicates whether the belt is broken.
    var cogBroken = false // Indicates whether the cog is broken.
    var ticksElapsed = 0 // Keeps track of the number of ticks elapsed.

    var stoveTemp = 0 // Stores the temperature of the stove.
        private set
    var furnaceTemp = 0 // Stores the temperature of the furnace.
        private set
    var cokeInStove = 0 // Stores the amount of coke in the stove.

    /**
     * Tick
     *
     * @param pumping Indicates whether pumping is happening.
     * @param pedaling Indicates whether pedaling is happening.
     */
    fun tick(pumping: Boolean, pedaling: Boolean) {
        ticksElapsed++

        adjustStoveTemperature()
        adjustFurnaceTemperature(pumping)

        checkForCokeDecrement()
        checkForBreakage(pedaling, pumping)
    }

    private fun adjustStoveTemperature() {
        if (cokeInStove > 0) stoveTemp = (stoveTemp + 1).coerceAtMost(100)
        else stoveTemp = (stoveTemp - 1).coerceAtLeast(0)
    }

    private fun adjustFurnaceTemperature(pumping: Boolean) {
        if (pumping && !pumpPipeBroken && !potPipeBroken && !beltBroken && !cogBroken) {
            when (stoveTemp) {
                in 1..32 -> furnaceTemp += 1
                in 32..66 -> furnaceTemp += 2
                in 67..100 -> furnaceTemp += 3
            }
        } else furnaceTemp--

        furnaceTemp = furnaceTemp.coerceAtLeast(0).coerceAtMost(100)
    }

    private fun checkForBreakage(pedaling: Boolean, pumping: Boolean) {
        if (disableBreaking) return
        if (pumping && (!potPipeBroken || !pumpPipeBroken)) {
            if (RandomFunction.roll(50) || forceBreaking) {
                if (RandomFunction.nextBool()) potPipeBroken = true
                else pumpPipeBroken = true
            }
        }

        if (pedaling && (!beltBroken || !cogBroken)) {
            if (RandomFunction.roll(50) || forceBreaking) beltBroken = true
            else if (RandomFunction.roll(50) || forceBreaking) cogBroken = true
        }
    }

    private fun checkForCokeDecrement() {
        if (ticksElapsed % 10 == 0) cokeInStove = (cokeInStove - 1).coerceAtLeast(0)
    }

    /**
     * Add coke
     *
     * @param amount The amount of coke to add.
     */
    fun addCoke(amount: Int) {
        cokeInStove += amount.coerceAtMost(BlastUtils.COKE_LIMIT - cokeInStove)
    }
}
