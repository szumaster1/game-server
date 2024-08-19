package core.game.world.map.zone.impl

import core.api.*
import core.api.consts.Items
import core.game.node.Node
import core.game.node.entity.Entity
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.world.map.zone.MapZone

/**
 * Karamja zone
 *
 * @constructor Karamja zone
 */
class KaramjaZone : MapZone("karamja", true) {

    override fun configure() {
        for (regionId in REGIONS) {
            registerRegion(regionId)
        }
    }

    override fun teleport(entity: Entity, type: Int, node: Node?): Boolean {
        if (entity is Player) {
            if(inInventory(entity.asPlayer(), Items.KARAMJAN_RUM_431)) {
                val amount = amountInInventory(entity.asPlayer(), Items.KARAMJAN_RUM_431)
                entity.asPlayer().inventory.remove(Item(Items.KARAMJAN_RUM_431, amount))
                sendMessage(entity.asPlayer(), "During the trip you lose your rum to a sailor in a game of dice. Better luck next time!")
            }
        }
        return super.teleport(entity, type, node)
    }

    companion object {
        private val REGIONS = intArrayOf(11309, 11054, 11566, 11565, 11567, 11568, 11053, 11821, 11055, 11057, 11569, 11822, 11823, 11825, 11310, 11311, 11312, 11313, 11314, 11056, 11057, 11058, 10802, 10801)
    }
}
