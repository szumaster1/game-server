package content.minigame.blastfurnace

import content.global.skill.production.smithing.data.Bar
import core.api.*
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.world.map.Location
import org.json.simple.JSONArray
import org.json.simple.JSONObject

/**
 * Blast Furnace player state.
 * @author Ceikry
 */
class BFPlayerState(val player: Player) {
    var container = BFOreContainer()
    val oresOnBelt = ArrayList<BFBeltOre>()
    var barsNeedCooled = false
        private set

    /**
     * Process ores into bars
     *
     * @return
     */
    fun processOresIntoBars(): Boolean {
        // Check if bars need to be cooled and if the dispenser state is 1
        if (barsNeedCooled && getVarbit(player, DISPENSER_STATE) == 1) {
            setVarbit(player, DISPENSER_STATE, 2, true)
            return false
        }
        // Check if the dispenser state is not 0 and if the container has any ore
        if (getVarbit(player, DISPENSER_STATE) != 0 || !container.hasAnyOre()) return false

        // Convert ores to bars and get the XP reward
        val xpReward = container.convertToBars(getStatLevel(player, Skills.SMITHING))
        if (xpReward > 0) {
            rewardXP(player, Skills.SMITHING, xpReward)
            setVarbit(player, DISPENSER_STATE, 1, true)
            barsNeedCooled = true
            return true
        }

        return false
    }

    /**
     * Update ores.
     */
    fun updateOres() {
        val toRemove = ArrayList<BFBeltOre>()
        for (ore in oresOnBelt) {
            // Tick the ore and check if it needs to be removed
            if (ore.tick()) toRemove.add(ore)
        }
        oresOnBelt.removeAll(toRemove)
    }

    /**
     * Cool bars.
     */
    fun coolBars() {
        // Set barsNeedCooled to false and update the dispenser state
        barsNeedCooled = false
        setVarbit(player, DISPENSER_STATE, 3, true)
    }

    /**
     * Check bars.
     */
    fun checkBars() {
        // Check if the dispenser state is 3 and update it to 0
        if (getVarbit(player, DISPENSER_STATE) == 3) setVarbit(player, DISPENSER_STATE, 0, true)
    }

    /**
     * Has bars claimable.
     */
    fun hasBarsClaimable(): Boolean {
        // Check if the container has any bars that can be claimed
        return container.getTotalBarAmount() > 0
    }

    /**
     * Claim bars.
     */
    fun claimBars(bar: Bar, amount: Int): Boolean {
        // Check if bars need to be cooled
        if (barsNeedCooled) return false

        // Calculate the maximum amount of bars that can be claimed
        val maxAmt = amount.coerceAtMost(freeSlots(player))
        if (maxAmt == 0) return false

        // Take the bars from the container and add them to the player's inventory
        val reward = container.takeBars(bar, maxAmt) ?: return false
        addItem(player, reward.id, reward.amount)
        setBarClaimVarbits()
        return true
    }

    /**
     * Set bar claim varbits.
     */
    fun setBarClaimVarbits() {
        for (bar in Bar.values()) {
            val amount = container.getBarAmount(bar)
            val varbit = getVarbitForBar(bar)
            if (varbit == 0) continue

            // Check if the varbit value is already equal to the amount, if not, update it
            if (getVarbit(player, varbit) == amount) continue

            setVarbit(player, varbit, amount, true)
        }

        var totalCoalNeeded = 0
        val level = getStatLevel(player, Skills.SMITHING)
        for ((id, amount) in container.getOreAmounts()) {
            val barType = BlastFurnace.getBarForOreId(id, container.coalAmount(), level)
            totalCoalNeeded += BlastFurnace.getNeededCoal(barType!!) * amount
        }

        setVarbit(player, COAL_NEEDED, (totalCoalNeeded - container.coalAmount()).coerceAtLeast(0))
    }

    private fun getVarbitForBar(bar: Bar): Int {
        // Get the corresponding varbit for the given bar type
        return when (bar) {
            Bar.BRONZE -> BRONZE_COUNT
            Bar.IRON -> IRON_COUNT
            Bar.STEEL -> STEEL_COUNT
            Bar.MITHRIL -> MITHRIL_COUNT
            Bar.ADAMANT -> ADDY_COUNT
            Bar.RUNITE -> RUNITE_COUNT
            Bar.GOLD -> GOLD_COUNT
            Bar.SILVER -> SILVER_COUNT
            else -> 0
        }
    }

    fun toJson(): JSONObject {
        val save = JSONObject()
        save["bf-ore-cont"] = container.toJson()

        val beltOres = JSONArray()
        for (ore in oresOnBelt) {
            beltOres.add(ore.toJson())
        }

        if (beltOres.isNotEmpty()) save["bf-belt-ores"] = beltOres
        save["barsHot"] = barsNeedCooled

        return save
    }

    fun readJson(data: JSONObject) {
        oresOnBelt.clear()
        if (data.containsKey("bf-ore-cont")) {
            val contJson = data["bf-ore-cont"] as JSONObject
            container = BFOreContainer.fromJson(contJson)
        }
        if (data.containsKey("bf-belt-ores")) {
            val beltArray = data["bf-belt-ores"] as JSONArray
            for (oreObj in beltArray) {
                val oreInfo = oreObj as JSONObject
                val id = oreInfo["id"].toString().toInt()
                val amount = oreInfo["amount"].toString().toInt()
                val location = Location.fromString(oreInfo["location"].toString())
                val ore = BFBeltOre(player, id, amount, location)
                oresOnBelt.add(ore)
            }
        }
        if (data.containsKey("barsHot")) barsNeedCooled = data["barsHot"] as Boolean
    }

    companion object {
        val DISPENSER_STATE = 936
        val COAL_NEEDED = 940
        val BRONZE_COUNT = 941
        val IRON_COUNT = 942
        val STEEL_COUNT = 943
        val MITHRIL_COUNT = 944
        val ADDY_COUNT = 945
        val RUNITE_COUNT = 946
        val GOLD_COUNT = 947
        val SILVER_COUNT = 948
    }
}
