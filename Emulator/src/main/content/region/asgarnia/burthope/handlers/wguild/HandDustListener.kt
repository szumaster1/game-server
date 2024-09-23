package content.region.asgarnia.burthope.handlers.wguild

import org.rs.consts.Items
import core.api.removeItem
import core.api.sendMessage
import core.api.setAttribute
import core.api.withinDistance
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.Item
import core.game.world.map.Location

/**
 * Represents the Hand dust listener.
 */
class HandDustListener : InteractionListener {

    override fun defineListeners() {
        on(Items.GROUND_ASHES_8865, IntType.ITEM, "dust-hands") { player, node ->
            /*
             * Check that player is in the shotput throwing areas.
             */
            if (!(withinDistance(player, Location(2861, 3553, 1), 1) || withinDistance(player, Location(2861, 3547, 1), 1))) {
                sendMessage(player, "You may only dust your hands while in the shotput throwing areas.")
                return@on true
            }
            if (removeItem(player, node as Item)) {
                sendMessage(player, "You dust your hands with the finely ground ash.")
                setAttribute(player, "hand_dust", true)
            }
            return@on true
        }
    }
}
