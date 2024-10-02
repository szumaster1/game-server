package core.game.node.item

import core.cache.def.impl.ItemDefinition
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.world.map.Location
import core.plugin.Plugin

abstract class ItemPlugin : Plugin<Any?> {

    /**
     * The drop type identifier.
     */
    companion object {
        const val DROP = 1
    }

    override fun fireEvent(identifier: String, vararg args: Any): Any {
        return this
    }

    /**
     * Registers items to this plugin.
     *
     * @param ids the ids.
     */
    fun register(vararg ids: Int) {
        for (id in ids) {
            ItemDefinition.forId(id).itemPlugin = this
        }
    }

    /**
     * Called when the item is removed from the player.
     *
     * @param player the player.
     * @param item   the item.
     * @param type   the type. (1=drop)
     */
    fun remove(player: Player, item: Item, type: Int) {
    }

    /**
     * Checks if the item can be picked up.
     *
     * @param player the player.
     * @param item   the item.
     * @param type   the pickup type (1=ground, 2=telegab)
     * @return {@code True} if so.
     */
    open fun canPickUp(player: Player, item: GroundItem, type: Int): Boolean = true

    /**
     * Checks if the item can be made as a drop.
     *
     * @param item   the item.
     * @param player the player.
     * @param npc    the NPC.
     * @param location the location.
     * @return boolean
     */
    open fun createDrop(item: Item, player: Player, npc: NPC, location: Location): Boolean = true


    /**
     * Changes an item if needed.
     *
     * @param item the item.
     * @param npc  the npc.
     * @return the item.
     */
    open fun getItem(item: Item, npc: NPC): Item = item

    /**
     * Gets the death item.
     *
     * @param item the item.
     * @return the item.
     */
    open fun getDeathItem(item: Item): Item = item

}
