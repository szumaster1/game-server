package content.global.handlers.item

import core.api.*
import cfg.consts.Items
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.link.TeleportManager
import core.game.node.item.Item

/**
 * House teleport tab listener.
 */
class HouseTeleportTabListener : InteractionListener {

    private val houseTeleport = Items.TELEPORT_TO_HOUSE_8013

    override fun defineListeners() {
        on(houseTeleport, IntType.ITEM, "break") { player, node ->
            var hasHouse = player.houseManager.location.exitLocation != null
            if (!hasHouse) {
                sendMessage( player, "You must have a house to teleport to before attempting that.")
                return@on false
            }
            closeInterface(player)
            lock(player, 5)
            if (inInventory(player, node.id)) {
                player.houseManager.preEnter(player, false)
                val location = player.houseManager.getEnterLocation()
                if (teleport(player, location, TeleportManager.TeleportType.TELETABS)) {
                    removeItem(player, Item(node.id, 1))
                    player.houseManager.postEnter(player, false)
                }
            }
            return@on true
        }
    }
}
