package content.global.skill.construction.decoration.costume

import content.global.handlers.iface.plugin.DiangoReclaimInterfacePlugin
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import org.rs.consts.Scenery

/**
 * Toy box listener.
 */
class ToyBoxListener : InteractionListener {

    override fun defineListeners() {
        on(Scenery.TOY_BOX_18802, IntType.SCENERY, "open"){ player, _ ->
            DiangoReclaimInterfacePlugin.open(player)
            return@on true
        }
    }
}
