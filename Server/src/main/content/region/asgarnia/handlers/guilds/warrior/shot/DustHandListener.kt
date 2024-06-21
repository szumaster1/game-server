package content.region.asgarnia.handlers.guilds.warrior.shot

import core.api.consts.Items
import core.api.setAttribute
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.Item
import core.game.world.map.Location

class DustHandListeners : InteractionListener {
    override fun defineListeners() {
        on(Items.GROUND_ASHES_8865, IntType.ITEM, "dust-hands") { player, node ->
            // Check that player is in the shotput throwing areas.
            if (!(player.location.withinDistance(Location(2861, 3553, 1), 1) ||
                        player.location.withinDistance(Location(2861, 3547, 1), 1))
            ) {
                player.packetDispatch.sendMessage("You may only dust your hands while in the shotput throwing areas.")
                return@on true
            }
            if (player.inventory.remove(node as Item)) {
                player.packetDispatch.sendMessage("You dust your hands with the finely ground ash.")
                setAttribute(player, "hand_dust", true)
            }
            return@on true
        }
    }
}