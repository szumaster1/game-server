package content.global.skill.production.runecrafting.pouch

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
    val pouch: Item,
    val level: Int,
    private val capacity: Int,
    val cumulativeCapacity: Int,
    val uses: Int
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
     * Performs an action based on the player's input option regarding the pouch.
     *
     * @param player The player performing the action.
     * @param pouch The pouch being acted upon.
     * @param option The action to be performed on the pouch.
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
     * Fills the pouch with essence if conditions are met.
     *
     * @param player The player filling the pouch.
     * @param pouch The pouch to be filled.
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
        }
        val amount = getAddAmount(pouch, essence, player)
        addEssence(player, pouch, essence, amount)

        if (isFull(pouch, player)) {
        }
    }

    /**
     * Empties the pouch of its contents.
     *
     * @param player The player emptying the pouch.
     * @param pouch The pouch to be emptied.
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

            if (essenceAmount != addAmount) {
                sendMessage(player, "You do not have any more free space in your inventory.")
            }
        }
    }

    /**
     * Checks for duplicate pouches in the player's inventory and bank.
     *
     * @param player The player whose pouches are being checked.
     * @return True if duplicates were found and handled, false otherwise.
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
     * Checks the contents of the pouch and sends a message to the player.
     *
     * @param player The player checking the pouch.
     * @param item The pouch item being checked.
     */
    fun check(player: Player, item: Item) {
        val amount = getEssence(item)
        sendMessage(player, if (amount == 0) "There are no essences in this pouch." else "There " + (if (amount == 1) "is" else "are") + " " + amount + " " + getPouchEssenceName(item, amount) + " in this pouch.")
    }

    /**
     * Handles the dropping of an item by the player.
     *
     * @param player The player who is dropping the item.
     * @param item The item that is being dropped.
     */
    fun drop(player: Player, item: Item) {
        onDrop(player, item)
        DropListener.drop(player, item)
    }

    /**
     * Handles the logic when an item is dropped by the player.
     *
     * @param player The player who dropped the item.
     * @param item The item that was dropped.
     */
    fun onDrop(player: Player, item: Item) {
        if (!isEmpty(item)) {
            resetCharge(item)
            sendMessage(player, "The contents of the pouch fell out as you dropped it!")
        }
    }

    /**
     * Adds essence to the pouch from the player's inventory.
     *
     * @param player The player adding essence to the pouch.
     * @param pouch The pouch to which essence is being added.
     * @param essence The essence item being added.
     * @param amount The amount of essence to add.
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
     * Handles the decay of the pouch based on usage.
     *
     * @param player The player whose pouch is decaying.
     * @param pouch The pouch that is decaying.
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
     * Repairs the pouch if it is decayed.
     *
     * @param player The player repairing the pouch.
     * @param pouch The pouch being repaired.
     */
    fun repair(player: Player, pouch: Item) {
        if (isDecayed(pouch)) {
            player.inventory.replace(Item(this.pouch.id, pouch.amount, pouch.charge), pouch.slot)
        }
        resetDecay(player)
    }

    /**
     * Increments the decay count for the player.
     *
     * @param player The player whose decay count is being incremented.
     */
    fun incrementDecay(player: Player) {
        player.getSavedData().globalData.getRcDecays()[ordinal - 1]++
    }

    /**
     * Increments the charge of the pouch.
     *
     * @param pouch The pouch whose charge is being incremented.
     * @param chargeIncrement The amount to increment the charge by.
     */
    fun incrementCharge(pouch: Item, chargeIncrement: Int) {
        setHash(pouch, getPouchCharge(pouch) + chargeIncrement)
    }

    /**
     * Decrements the charge of the pouch.
     *
     * @param pouch The pouch whose charge is being decremented.
     * @param chargeIncrement The amount to decrement the charge by.
     */
    fun decrementCharge(pouch: Item, chargeIncrement: Int) {
        setHash(pouch, getPouchCharge(pouch) - chargeIncrement)
    }

    /**
     * Set charge
     *
     * @param pouch The pouch whose charge is being set.
     * @param charge The charge value to set.
     */
    fun setCharge(pouch: Item, charge: Int) {
        setHash(pouch, charge)
    }

    /**
     * Reset decay
     *
     * @param player The player whose decay count is being reset.
     */
    fun resetDecay(player: Player) {
        player.getSavedData().globalData.getRcDecays()[ordinal - 1] = 0
    }

    /**
     * Reset charge
     *
     * @param pouch The pouch whose charge is being reset.
     */
    fun resetCharge(pouch: Item) {
        setHash(pouch, 1000)
    }

    /**
     * Set hash
     *
     * @param pouch The pouch whose hash is being set.
     * @param charge The charge value to set.
     */
    fun setHash(pouch: Item, charge: Int) {
        pouch.charge = charge
    }

    /**
     * Get decay
     *
     * @param player The player whose decay count is being retrieved.
     * @return The current decay count for the player.
     */
    fun getDecay(player: Player): Int {
        return player.getSavedData().globalData.getRcDecay(ordinal - 1)
    }

    /**
     * Get pouch charge
     *
     * @param pouch The pouch whose charge is being retrieved.
     * @return The current charge of the pouch.
     */
    fun getPouchCharge(pouch: Item): Int {
        return pouch.charge
    }

    /**
     * Is full
     *
     * @param item The item being checked for fullness.
     * @return True if the item is full, otherwise false.
     */
    fun isFull(item: Item): Boolean {
        return getEssence(item) >= getCapacity(pouch)
    }

    /**
     * Get add amount
     *
     * @param pouch The pouch being checked.
     * @param essence The essence being added.
     * @param player The player adding the essence.
     * @return The amount of essence that can be added to the pouch.
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
     * is valid essence
     *
     * @param pouch The pouch being validated.
     * @param essence The essence being checked.
     * @param player The player performing the validation.
     * @return True if the essence is valid for the pouch, otherwise false.
     */
    fun isValidEssence(pouch: Item, essence: Item?, player: Player?): Boolean {
        if (isEmpty(pouch)) {
            return true
        }
        return getPouchEssenceName(pouch) == getEssenceName(essence)
    }

    /**
     * check if the pouch is full
     *
     * @param item The item being checked for fullness.
     * @param player The player associated with the item.
     * @return True if the item is full, otherwise false.
     */
    fun isFull(item: Item, player: Player): Boolean {
        if (isFull(item)) {
            player.sendMessage("Your pouch is full.")
            return true
        }
        return false
    }

    /**
     * Gets essence pouch name
     *
     * @param item The item being checked.
     * @param amount The amount being checked.
     * @return The name of the essence in the pouch.
     */
    fun getPouchEssenceName(item: Item, amount: Int): String {
        return getPouchEssenceName(item) + (if (amount > 1) "s" else "")
    }

    /**
     * Gets essence pouch name
     *
     * @param item The item being checked.
     * @return The name of the essence in the pouch.
     */
    fun getPouchEssenceName(item: Item): String {
        val charge = getPouchCharge(item)
        return if (charge > NORMAL_BASE) "pure essence" else "normal essence"
    }

    /**
     * Retrieves the essence contained in the specified pouch.
     *
     * @param pouch The pouch from which to retrieve the essence.
     * @return The essence contained in the pouch, either pure or normal.
     */
    fun getEssenceInPouch(pouch: Item): Item {
        return if (getPouchEssenceName(pouch) == "pure essence") PURE_ESSENCE else NORMAL_ESSENCE
    }

    /**
     * Gets the name of the essence based on the provided essence item.
     *
     * @param essence The essence item to evaluate.
     * @return A string representing the name of the essence.
     */
    fun getEssenceName(essence: Item?): String {
        return if (essence!!.id == PURE_ESSENCE.id) "pure essence" else "normal essence"
    }

    /**
     * Determines the base value of the essence based on the item.
     *
     * @param item The item to evaluate for its essence base.
     * @return The base value of the essence, either pure or normal.
     */
    fun getEssenceBase(item: Item): Int {
        return if (getPouchEssenceName(item) == "pure essence") PURE_BASE else NORMAL_BASE
    }

    /**
     * Checks if the specified pouch contains pure essence.
     *
     * @param pouch The pouch to check for pure essence.
     * @return True if the pouch contains pure essence, otherwise false.
     */
    fun isPureEssencePouch(pouch: Item): Boolean {
        return getPouchEssenceName(pouch) == "pure essence"
    }

    /**
     * Determines if the provided essence is pure essence.
     *
     * @param essence The essence item to evaluate.
     * @return True if the essence is pure, otherwise false.
     */
    fun isPureEssence(essence: Item?): Boolean {
        return essence!!.id == PURE_ESSENCE.id
    }

    /**
     * Determines if the provided essence is normal essence.
     *
     * @param essence The essence item to evaluate.
     * @return True if the essence is normal, otherwise false.
     */
    fun isNormalEssence(essence: Item?): Boolean {
        return essence!!.id == NORMAL_ESSENCE.id
    }

    /**
     * Retrieves the type of essence contained in the specified pouch.
     *
     * @param pouch The pouch from which to retrieve the essence type.
     * @return The essence type, either pure or normal.
     */
    fun getEssenceType(pouch: Item): Item {
        return if (getPouchEssenceName(pouch) == "pure essence") PURE_ESSENCE else NORMAL_ESSENCE
    }

    /**
     * Retrieves the essence item from the player's inventory.
     *
     * @param player The player whose inventory is checked.
     * @return The essence item if found, otherwise null.
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
     * Checks if the player has any essence in their inventory.
     *
     * @param player The player to check for essence.
     * @return True if the player has either pure or normal essence, otherwise false.
     */
    fun hasEssence(player: Player): Boolean {
        return player.inventory.containsItem(PURE_ESSENCE) || player.inventory.containsItem(NORMAL_ESSENCE)
    }

    /**
     * Determines if the specified item is empty of essence.
     *
     * @param item The item to evaluate for emptiness.
     * @return True if the item is empty, otherwise false.
     */
    fun isEmpty(item: Item): Boolean {
        return getEssence(item) <= 0
    }

    /**
     * Retrieves the amount of essence contained in the specified item.
     *
     * @param item The item to evaluate for its essence amount.
     * @return The amount of essence in the item.
     */
    fun getEssence(item: Item): Int {
        if (getPouchCharge(item) == 1000 || getPouchCharge(item) == 2000) {
            return 0
        }
        return getEssenceBase(item) - getPouchCharge(item)
    }

    /**
     * Retrieves the capacity of the specified pouch.
     *
     * @param pouch The pouch to evaluate for its capacity.
     * @return The available capacity of the pouch.
     */
    fun getCapacity(pouch: Item): Int {
        return capacity - (if (isDecayed(pouch)) decayAmount else 0)
    }

    val decayAmount: Int
        get() = if (this == GIANT) 3 else if (this == LARGE) 2 else 1

    /**
     * Checks if the specified pouch is decayed.
     *
     * @param pouch The pouch to check for decay.
     * @return True if the pouch is decayed, otherwise false.
     */
    fun isDecayed(pouch: Item): Boolean {
        return pouch.id == decayedPouch.id
    }

    val isDecayable: Boolean
        get() = this != SMALL

    /**
     * Checks if the player has any decay affecting their pouch.
     *
     * @param player The player to check for decay.
     * @param pouch The pouch to evaluate for decay.
     * @return True if the player has decay or the pouch is decayed, otherwise false.
     */
    fun hasDecay(player: Player, pouch: Item): Boolean {
        return getDecay(player) > 0 || isDecayed(pouch)
    }

    companion object {
        private val PURE_ESSENCE = Item(7936)
        private val NORMAL_ESSENCE = Item(1436)
        private const val PURE_BASE = 6000
        private const val NORMAL_BASE = 2000

        /**
         * Retrieves the RunePouch associated with the specified item.
         *
         * @param pouch The pouch item to evaluate.
         * @return The corresponding RunePouch if found, otherwise null.
         */
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
