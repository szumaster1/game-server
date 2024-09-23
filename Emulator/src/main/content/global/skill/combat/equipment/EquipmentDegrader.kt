package content.global.skill.combat.equipment

import core.api.addItemOrDrop
import core.api.getNext
import core.api.isLast
import core.api.isNextLast
import core.game.node.entity.player.Player
import core.game.node.item.Item

/**
 * Equipment degrader.
 */
class EquipmentDegrader {

    companion object StaticDegrader {
        /*
         * Use a static companion object for lists and registers so they persist across instances.
         */
        val itemList = arrayListOf<Int>() // List to hold item IDs that can degrade
        val setList = arrayListOf<ArrayList<Int>>() // List to hold sets of degradable items
        val itemCharges = hashMapOf<Int, Int>() // Map to hold item charges

        /**
         * Register an item with its charge.
         *
         * @param charges The number of charges the item has.
         * @param item The ID of the item to register.
         */
        fun register(charges: Int, item: Int) {
            itemList.add(item) // Add the item ID to the item list
            itemCharges.put(item, charges) // Associate the item ID with its charges
        }

        /**
         * Register a set of items with their charges.
         *
         * @param charges The number of charges for each item in the set.
         * @param items The array of item IDs to register.
         */
        fun registerSet(charges: Int, items: Array<Int>) {
            setList.add(ArrayList(items.map { item -> item.also { register(charges, item) } })) // Register each item in the set
        }
    }

    var p: Player? = null // Player reference for degradation context

    /**
     * Can degrade
     *
     * @return True if the item can degrade, false otherwise.
     */
    fun Int.canDegrade(): Boolean {
        /*
         * Extension function that checks if an item can degrade.
         */
        return itemList.contains(this) // Check if the item ID is in the item list
    }

    /*
     * Loops the player slots and if the item in that slot is
     * non-null, checks if it can degrade etc.
     */

    /**
     * Check armour degrades
     *
     * @param player The player whose armour is being checked.
     */
    fun checkArmourDegrades(player: Player?) {
        p = player // Set the player reference
        for (slot in 0..12) { // Loop through equipment slots
            if (slot == 3) { // Skip the weapon slot
                continue
            }
            player?.equipment?.get(slot)?.let { if (it.id.canDegrade()) it.degrade(slot) } // Check if the item can degrade and degrade it
        }
    }

    /**
     * Check weapon degrades
     *
     * @param player The player whose weapon is being checked.
     */
    fun checkWeaponDegrades(player: Player?) {
        p = player // Set the player reference
        player?.equipment?.get(3)?.let { if (it.id.canDegrade()) it.degrade(3) } // Check if the weapon can degrade and degrade it
    }

    /**
     * Degrade
     *
     * @param slot The slot of the item being degraded.
     */
    fun Item.degrade(slot: Int) { // Extension function that degrades items
        val set = getDegradableSet(this.id) // Get the set of degradable items this item belongs to
        val charges = itemCharges.getOrElse(this.id) { 1000 } // Get the item's charges or default to 1000
        if (this.charge > charges) // If current charge exceeds max charge
            charge = charges // Set charge to max charge
        this.charge-- // Decrease the charge by 1
        if (set?.indexOf(this.id) == 0 && !this.name.contains("100")) { // If this is the first item in the set and not a special item
            charge = 0 // Set charge to 0
        }
        if (this.charge <= 0) { // If charge is depleted
            val charges = itemCharges.getOrElse(this.id) { 1000 } // Get the item's charges or default to 1000
            if (set?.size!! > 2) { // If the set has more than 2 items
                p?.equipment?.remove(this) // Remove the item from the player's equipment
                p?.sendMessage("Your $name has degraded.") // Notify the player
                if (set.isNextLast(this.id)) { // If this is the last item in the set
                    p?.let { addItemOrDrop(it, set.getNext(this.id)) } // Add the next item in the set to the player's inventory
                    p?.sendMessage("Your $name has broken.") // Notify the player
                } else {
                    p?.equipment?.add(Item(set.getNext(this.id), 1, charges), slot, false, false) // Replace with the next item in the set
                    p?.equipment?.refresh() // Refresh the equipment display
                }
            } else if (set.size == 2) { // If the set has exactly 2 items
                if (set.isLast(this.id)) { // If this is the last item in the set
                    p?.equipment?.remove(this) // Remove the item from the player's equipment
                    p?.sendMessage("Your $name degrades into dust.") // Notify the player
                } else {
                    p?.equipment?.remove(this) // Remove the item from the player's equipment
                    p?.sendMessage("Your $name has degraded.") // Notify the player
                    p?.equipment?.add(Item(set.getNext(this.id), 1, charges), slot, false, false) // Replace with the next item in the set
                    p?.equipment?.refresh() // Refresh the equipment display
                }
            }
        }
    }

    /**
     * Gets the set of items this item belongs to.
     *
     * @param item The ID of the item to check.
     * @return The set of degradable items or null if not found.
     */
    private fun getDegradableSet(item: Int): ArrayList<Int>? {
        for (set in setList) { // Loop through each set of items
            if (set.contains(item)) // Check if the item is in the current set
                return set // Return the set if found
        }
        return null // Return null if no set is found
    }
}
