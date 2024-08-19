package core.game.node.entity.combat.equipment

import core.game.node.entity.Entity
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Plugin

/**
 * Handles equipment degrading.
 * @author Emperor
 *
 * @param slot The equipment slot where the item is equipped.
 * @param itemIds The IDs of the items that can be degraded.
 * @constructor Degradable equipment
 */
abstract class DegradableEquipment(val slot: Int, vararg val itemIds: Int) : Plugin<Any?> {

    /**
     * Called when the player receives a hit.
     *
     * @param player The player who is receiving the hit.
     * @param entity The entity that is dealing the hit.
     * @param item The item that is being used in the hit.
     */
    abstract fun degrade(player: Player?, entity: Entity?, item: Item?)

    /**
     * Gets the item to drop when this equipment is getting dropped.
     *
     * @param itemId The ID of the item being dropped.
     * @return The ID of the item to drop.
     */
    abstract fun getDropItem(itemId: Int): Int

    override fun newInstance(arg: Any?): DegradableEquipment {
        // Retrieve the equipment list for the specified slot
        var equipment = EQUIPMENT[slot]
        // If no equipment list exists for the slot, create a new one
        if (equipment == null) {
            EQUIPMENT[slot] = ArrayList(20) // Initialize a new list with a capacity of 20
            equipment = EQUIPMENT[slot] // Assign the newly created list to equipment
        }
        // Add the current instance of DegradableEquipment to the equipment list
        equipment!!.add(this)
        return this // Return the current instance
    }

    override fun fireEvent(key: String, vararg args: Any): Any? {
        // Placeholder for event handling, currently returns null
        return null
    }

    companion object {

        /*
         * The equipment lists.
         */
        private val EQUIPMENT: Array<ArrayList<DegradableEquipment>?> =
            arrayOfNulls<ArrayList<DegradableEquipment>?>(14) // Initialize an array for 14 equipment slots

        /*
         * Handles equipment degrading.
         */
        fun degrade(player: Player?, entity: Entity?, attack: Boolean) {
            // Functionality for degrading equipment will be implemented here
        }

        /*
         * Checks the degrading equipment on an equipment slot.
         */
        fun checkDegrade(player: Player?, entity: Entity?, slot: Int) {
            // Functionality for checking equipment degradation will be implemented here
        }

        /*
         * Gets the item to drop.
         */
        @JvmStatic
        fun getDropReplacement(itemId: Int): Int {
            // Iterate through the equipment slots
            for (i in EQUIPMENT.indices) {
                // Skip if the equipment list for the slot is null
                if (EQUIPMENT[i] == null) {
                    continue
                }
                // Iterate through each degradable equipment in the slot
                for (e in EQUIPMENT[i]!!) {
                    // Check each item ID associated with the equipment
                    for (id in e.itemIds) {
                        // If the item ID matches, return the drop item ID
                        if (id == itemId) {
                            return e.getDropItem(id)
                        }
                    }
                }
            }
            // If no match is found, return the original item ID
            return itemId
        }
    }
}
