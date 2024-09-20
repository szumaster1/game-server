package core.api.utils

import core.game.node.entity.Entity
import core.game.node.item.Item

/**
 * NPC drop table.
 */
class NPCDropTable : WeightBasedTable() {

    // Table to hold charm drops with associated weights
    private val charmDrops = WeightBasedTable()

    // Table to hold tertiary drops with associated weights
    private val tertiaryDrops = WeightBasedTable()

    /**
     * Add to charms.
     *
     * @param element the element.
     * @return true if the element was added successfully, false otherwise
     */
    fun addToCharms(element: WeightedItem): Boolean {
        return charmDrops.add(element)
    }

    /**
     * Add to tertiary.
     *
     * @param element the element.
     * @return true if the element was added successfully, false otherwise
     */
    fun addToTertiary(element: WeightedItem): Boolean {
        return tertiaryDrops.add(element)
    }

    override fun roll(receiver: Entity?, times: Int): ArrayList<Item> {
        val items = ArrayList<Item>()

        // Charms table is always rolled, and should contain explicit "Nothing"
        // entries at the data level to account for the chance to not drop a charm.
        // Roll the charm drops and add them to the items list
        items.addAll(charmDrops.roll(receiver, times))
        items.addAll(tertiaryDrops.roll(receiver, times))
        items.addAll(super.roll(receiver, times))
        return items
    }

}
