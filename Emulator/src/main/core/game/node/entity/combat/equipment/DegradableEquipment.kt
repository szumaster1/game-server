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
        var equipment = EQUIPMENT[slot]
        if (equipment == null) {
            EQUIPMENT[slot] = ArrayList(20)
            equipment = EQUIPMENT[slot]
        }
        equipment!!.add(this)
        return this
    }

    override fun fireEvent(key: String, vararg args: Any): Any? {
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
        }

        /*
         * Checks the degrading equipment on an equipment slot.
         */
        fun checkDegrade(player: Player?, entity: Entity?, slot: Int) {
        }

        /*
         * Gets the item to drop.
         */
        @JvmStatic
        fun getDropReplacement(itemId: Int): Int {
            for (i in EQUIPMENT.indices) {
                if (EQUIPMENT[i] == null) {
                    continue
                }
                for (e in EQUIPMENT[i]!!) {
                    for (id in e.itemIds) {
                        if (id == itemId) {
                            return e.getDropItem(id)
                        }
                    }
                }
            }
            return itemId
        }
    }
}
