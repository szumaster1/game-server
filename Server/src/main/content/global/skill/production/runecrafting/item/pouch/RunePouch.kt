package content.global.skill.production.runecrafting.item.pouch

import cfg.consts.Items
import core.api.freeSlots
import core.api.getStatLevel
import core.api.hasSpaceFor
import core.api.sendMessage
import core.game.global.action.DropListener
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import java.util.*

/**
 * Rune pouch
 *
 * @param pouch Represents the item contained in the rune pouch.
 * @param level Indicates the required level to use the rune pouch.
 * @param capacity Defines the maximum number of runes the pouch can hold.
 * @param cumulativeCapacity Represents the total capacity of the pouch over time.
 * @param uses Specifies how many times the pouch can be used.
 * @constructor Rune pouch Represents a new instance of the RunePouch enum class.
 */
enum class RunePouch(
    val pouch: Item, // The item that the rune pouch holds
    val level: Int, // The level required to utilize the pouch
    private val capacity: Int, // The maximum capacity of the pouch
    val cumulativeCapacity: Int, // The total capacity accumulated over time
    val uses: Int // The number of uses available for the pouch
) {
    /**
     * Small
     *
     * @constructor Small
     */
    SMALL(Item(Items.SMALL_POUCH_5509), 1, 3, 3, 0),

    /**
     * Medium
     *
     * @constructor Medium
     */
    MEDIUM(Item(Items.MEDIUM_POUCH_5510), 25, 6, 9, 45),

    /**
     * Large
     *
     * @constructor Large
     */
    LARGE(Item(Items.LARGE_POUCH_5512), 50, 9, 18, 29),

    /**
     * Giant
     *
     * @constructor Giant
     */
    GIANT(Item(Items.GIANT_POUCH_5514), 75, 12, 30, 10);

    val decayedPouch = Item(pouch.id + 1)

    /**
     * Action
     *
     * @param player
     * @param pouch
     * @param option
     */
    fun action(player: Player, pouch: Item, option: String?) {
        if (pouch.charge == 1000 && getDecay(player) > 0) {
            resetDecay(player)
        }
        when (option) {
            "fill" -> fill(player, pouch)
            "empty" -> empty(player, pouch)
            "check" -> check(player, pouch)
            "drop" -> drop(player, pouch)
        }
    }

    /**
     * Fill
     *
     * @param player
     * @param pouch
     */
    fun fill(player: Player, pouch: Item) {
        if (isFull(pouch, player)) {
            return
        }
        if (!hasEssence(player)) {
            sendMessage(player, "You do not have any essence to fill your pouch with.")
            return
        }
        if (getStatLevel(player, Skills.RUNECRAFTING) < level) {
            sendMessage(player, "You need level " + level + " Runecrafting to fill a " + name.lowercase(Locale.getDefault()) + " pouch.")
            return
        }
        val essence = getEssence(player)
        if (!isValidEssence(pouch, essence, player)) {
            sendMessage(player, "You can only put " + getPouchEssenceName(pouch) + " in this pouch.")
            return
        }
        val amount = getAddAmount(pouch, essence, player)
        addEssence(player, pouch, essence, amount)
        if (isFull(pouch, player)) {
        }
    }

    /**
     * Empty
     *
     * @param player
     * @param pouch
     */
    fun empty(player: Player, pouch: Item) {
        if (isEmpty(pouch)) {
            sendMessage(player, "There are no essences in your pouch.")
            return
        }
        val essenceAmount = getEssence(pouch)
        var addAmount = essenceAmount
        if (player.inventory.freeSlots() < essenceAmount) {
            addAmount = essenceAmount - (essenceAmount - freeSlots(player))
        }
        val add = Item(getEssenceType(pouch).id, addAmount)
        if (!hasSpaceFor(player, add)) {
            sendMessage(player, "You do not have any more free space in your inventory.")
            return
        }
        if (player.inventory.add(add)) {
            incrementCharge(pouch, addAmount)
            if (essenceAmount != addAmount) { // means all didnt get added
                sendMessage(player, "You do not have any more free space in your inventory.")
            }
        }
    }

    /**
     * Check doubles
     *
     * @param player
     * @return
     */
    fun checkDoubles(player: Player): Boolean {
        var hit = false
        for (pouch in values()) {
            if (player.inventory.getAmount(pouch.pouch) > 1 || player.bank.getAmount(pouch.pouch) > 1) {
                hit = true
                player.inventory.remove(Item(pouch.pouch.id, player.inventory.getAmount(pouch.pouch) - 1))
                player.bank.remove(Item(pouch.pouch.id, player.bank.getAmount(pouch.pouch)))
            }
        }
        return hit
    }

    /**
     * Check
     *
     * @param player
     * @param item
     */
    fun check(player: Player, item: Item) {
        val amount = getEssence(item)
        sendMessage(player, if (amount == 0) "There are no essences in this pouch." else "There " + (if (amount == 1) "is" else "are") + " " + amount + " " + getPouchEssenceName(item, amount) + " in this pouch.")
    }

    /**
     * Drop
     *
     * @param player
     * @param item
     */
    fun drop(player: Player, item: Item) {
        onDrop(player, item)
        DropListener.drop(player, item)
    }

    /**
     * On drop
     *
     * @param player
     * @param item
     */
    fun onDrop(player: Player, item: Item) {
        if (!isEmpty(item)) {
            resetCharge(item)
            sendMessage(player, "The contents of the pouch fell out as you dropped it!")
        }
    }

    /**
     * Add essence
     *
     * @param player
     * @param pouch
     * @param essence
     * @param amount
     */
    fun addEssence(player: Player, pouch: Item, essence: Item?, amount: Int) {
        val remove = Item(essence!!.id, amount)
        if (!player.inventory.containsItem(remove)) {
            return
        }
        if (player.inventory.remove(remove)) {
            var charge = getPouchCharge(pouch)
            if (charge == 1000) {
                charge = (if (isPureEssence(essence)) PURE_BASE else NORMAL_BASE)
                setCharge(pouch, charge)
            }
            if (isPureEssence(essence) && charge == NORMAL_BASE) {
                charge = PURE_BASE
            } else if (isNormalEssence(essence) && charge == PURE_BASE) {
                charge = NORMAL_BASE
            }
            charge -= amount
            setHash(pouch, charge)
            decay(player, pouch)
        }
    }

    /**
     * Decay
     *
     * @param player
     * @param pouch
     */
    fun decay(player: Player, pouch: Item) {
        incrementDecay(player)
        if (getDecay(player) >= uses) {
            var message = ""
            if (!isDecayed(pouch)) {
                var decrementAmount = 0
                decrementAmount = if (decayAmount > getEssence(pouch)) {
                    decayAmount - (decayAmount - getEssence(pouch))
                } else {
                    getEssence(pouch) - (getEssence(pouch) - decayAmount)
                }
                incrementCharge(pouch, decrementAmount)
                message = "Your pouch has decayed through use."
                player.inventory.replace(Item(decayedPouch.id, pouch.amount, pouch.charge), pouch.slot)
            } else {
                message = "Your pouch has decayed beyond any further use."
                player.inventory.remove(pouch)
            }
            resetDecay(player)
            player.sendMessage(message)
        }
    }

    /**
     * Repair
     *
     * @param player
     * @param pouch
     */
    fun repair(player: Player, pouch: Item) {
        if (isDecayed(pouch)) {
            player.inventory.replace(Item(this.pouch.id, pouch.amount, pouch.charge), pouch.slot)
        }
        resetDecay(player)
    }

    /**
     * Increment decay
     *
     * @param player
     */
    fun incrementDecay(player: Player) {
        player.getSavedData().globalData.getRcDecays()[ordinal - 1]++
    }

    /**
     * Increment charge
     *
     * @param pouch
     * @param chargeIncrement
     */
    fun incrementCharge(pouch: Item, chargeIncrement: Int) {
        setHash(pouch, getPouchCharge(pouch) + chargeIncrement)
    }

    /**
     * Decrement charge
     *
     * @param pouch
     * @param chargeIncrement
     */
    fun decrementCharge(pouch: Item, chargeIncrement: Int) {
        setHash(pouch, getPouchCharge(pouch) - chargeIncrement)
    }

    /**
     * Set charge
     *
     * @param pouch
     * @param charge
     */
    fun setCharge(pouch: Item, charge: Int) {
        setHash(pouch, charge)
    }

    /**
     * Reset decay
     *
     * @param player
     */
    fun resetDecay(player: Player) {
        player.getSavedData().globalData.getRcDecays()[ordinal - 1] = 0
    }

    /**
     * Reset charge
     *
     * @param pouch
     */
    fun resetCharge(pouch: Item) {
        setHash(pouch, 1000)
    }

    /**
     * Set hash
     *
     * @param pouch
     * @param charge
     */
    fun setHash(pouch: Item, charge: Int) {
        pouch.charge = charge
    }

    /**
     * Get decay
     *
     * @param player
     * @return
     */
    fun getDecay(player: Player): Int {
        return player.getSavedData().globalData.getRcDecay(ordinal - 1)
    }

    /**
     * Get pouch charge
     *
     * @param pouch
     * @return
     */
    fun getPouchCharge(pouch: Item): Int {
        return pouch.charge
    }

    /**
     * Is full
     *
     * @param item
     * @return
     */
    fun isFull(item: Item): Boolean {
        return getEssence(item) >= getCapacity(pouch)
    }

    /**
     * Get add amount
     *
     * @param pouch
     * @param essence
     * @param player
     * @return
     */
    fun getAddAmount(pouch: Item, essence: Item?, player: Player): Int {
        val essyAmount = player.inventory.getAmount(essence)
        val pouchAmount = getEssence(pouch)
        val maxAdd = getCapacity(pouch) - pouchAmount
        if (essyAmount > maxAdd) {
            return maxAdd
        } else if (essyAmount <= maxAdd) {
            return essyAmount
        }
        return 0
    }

    /**
     * Is valid essence
     *
     * @param pouch
     * @param essence
     * @param player
     * @return
     */
    fun isValidEssence(pouch: Item, essence: Item?, player: Player?): Boolean {
        if (isEmpty(pouch)) {
            return true
        }
        return getPouchEssenceName(pouch) == getEssenceName(essence)
    }

    /**
     * Is full
     *
     * @param item
     * @param player
     * @return
     */
    fun isFull(item: Item, player: Player): Boolean {
        if (isFull(item)) {
            player.sendMessage("Your pouch is full.")
            return true
        }
        return false
    }

    /**
     * Get pouch essence name
     *
     * @param item
     * @param amount
     * @return
     */
    fun getPouchEssenceName(item: Item, amount: Int): String {
        return getPouchEssenceName(item) + (if (amount > 1) "s" else "")
    }

    /**
     * Get pouch essence name
     *
     * @param item
     * @return
     */
    fun getPouchEssenceName(item: Item): String {
        val charge = getPouchCharge(item)
        return if (charge > NORMAL_BASE) "pure essence" else "normal essence"
    }

    /**
     * Get essence in pouch
     *
     * @param pouch
     * @return
     */
    fun getEssenceInPouch(pouch: Item): Item {
        return if (getPouchEssenceName(pouch) == "pure essence") PURE_ESSENCE else NORMAL_ESSENCE
    }

    /**
     * Get essence name
     *
     * @param essence
     * @return
     */
    fun getEssenceName(essence: Item?): String {
        return if (essence!!.id == PURE_ESSENCE.id) "pure essence" else "normal essence"
    }

    /**
     * Get essence base
     *
     * @param item
     * @return
     */
    fun getEssenceBase(item: Item): Int {
        return if (getPouchEssenceName(item) == "pure essence") PURE_BASE else NORMAL_BASE
    }

    /**
     * Is pure essence pouch
     *
     * @param pouch
     * @return
     */
    fun isPureEssencePouch(pouch: Item): Boolean {
        return getPouchEssenceName(pouch) == "pure essence"
    }

    /**
     * Is pure essence
     *
     * @param essence
     * @return
     */
    fun isPureEssence(essence: Item?): Boolean {
        return essence!!.id == PURE_ESSENCE.id
    }

    /**
     * Is normal essence
     *
     * @param essence
     * @return
     */
    fun isNormalEssence(essence: Item?): Boolean {
        return essence!!.id == NORMAL_ESSENCE.id
    }

    /**
     * Get essence type
     *
     * @param pouch
     * @return
     */
    fun getEssenceType(pouch: Item): Item {
        return if (getPouchEssenceName(pouch) == "pure essence") PURE_ESSENCE else NORMAL_ESSENCE
    }

    /**
     * Get essence
     *
     * @param player
     * @return
     */
    fun getEssence(player: Player): Item? {
        if (player.inventory.containsItem(PURE_ESSENCE)) {
            return PURE_ESSENCE
        } else if (player.inventory.containsItem(NORMAL_ESSENCE)) {
            return NORMAL_ESSENCE
        }
        return null
    }

    /**
     * Has essence
     *
     * @param player
     * @return
     */
    fun hasEssence(player: Player): Boolean {
        return player.inventory.containsItem(PURE_ESSENCE) || player.inventory.containsItem(NORMAL_ESSENCE)
    }

    /**
     * Is empty
     *
     * @param item
     * @return
     */
    fun isEmpty(item: Item): Boolean {
        return getEssence(item) <= 0
    }

    /**
     * Get essence
     *
     * @param item
     * @return
     */
    fun getEssence(item: Item): Int {
        if (getPouchCharge(item) == 1000 || getPouchCharge(item) == 2000) {
            return 0
        }
        return getEssenceBase(item) - getPouchCharge(item)
    }

    /**
     * Get capacity
     *
     * @param pouch
     * @return
     */
    fun getCapacity(pouch: Item): Int {
        return capacity - (if (isDecayed(pouch)) decayAmount else 0)
    }

    val decayAmount: Int
        get() = if (this == GIANT) 3 else if (this == LARGE) 2 else 1

    /**
     * Is decayed
     *
     * @param pouch
     * @return
     */
    fun isDecayed(pouch: Item): Boolean {
        return pouch.id == decayedPouch.id
    }

    val isDecayable: Boolean
        get() = this != SMALL


    /**
     * Has decay
     *
     * @param player
     * @param pouch
     * @return
     */
    fun hasDecay(player: Player, pouch: Item): Boolean {
        return getDecay(player) > 0 || isDecayed(pouch)
    }

    companion object {
        private val PURE_ESSENCE = Item(7936)
        private val NORMAL_ESSENCE = Item(1436)
        private const val PURE_BASE = 6000
        private const val NORMAL_BASE = 2000
        fun forItem(pouch: Item): RunePouch? {
            for (p in values()) {
                if (p.pouch.id == pouch.id || (p != SMALL && p.decayedPouch.id == pouch.id)) {
                    return p
                }
            }
            return null
        }
    }
}
