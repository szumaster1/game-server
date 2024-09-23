package content.global.handlers.item

import core.api.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.TeleportManager.TeleportType
import core.game.node.item.Item
import core.game.world.map.Location
import core.game.world.map.zone.impl.WildernessZone
import org.rs.consts.Items

/**
 * Represents the listener used to handle the elf teleport crystal item.
 */
class TeleportCrystal : InteractionListener {

    override fun defineListeners() {

        /*
         * Activate option.
         */

        on(crystalIDs, IntType.ITEM, "activate") { player, node ->
            if (!hasRequirement(player, "Mourning's End Part I")) return@on true
            if (!WildernessZone.checkTeleport(player, 20)) {
                sendMessage(player, "The crystal is unresponsive.")
                return@on false
            }
            sendDialogueOptions(player, "Select an Option", "Teleport to Lletya", "Cancel").also {
                addDialogueAction(player) { player, button ->
                    if (button == 1) {
                        player.teleporter.send(Location(2329, 3172), TeleportType.NORMAL)
                        degrade(player, Item(node.id))
                    }
                    return@addDialogueAction
                }
            }
            return@on true
        }
    }


    companion object {

        val crystalIDs = intArrayOf(
            Items.TELEPORT_CRYSTAL_4_6099,
            Items.TELEPORT_CRYSTAL_3_6100,
            Items.TELEPORT_CRYSTAL_2_6101,
            Items.TELEPORT_CRYSTAL_1_6102
        )

        private fun degrade(player: Player, item: Item) {
            val id = item.id
            val newItem = item.id + 1
            if (id < 6102) {
                removeItem(player, Item(id, 1))
                addItem(player, newItem, 1)
                sendMessage(player, "Your teleportation crystal has degraded from use.")
            } else {
                removeItem(player, Item(id, 1))
                addItem(player, newItem, 1)
                sendMessage(player, "Your teleportation crystal has degraded to a tiny elf crystal,")
                sendMessage(player, "Eluned can re-enchant it.")
            }
        }
    }
}
