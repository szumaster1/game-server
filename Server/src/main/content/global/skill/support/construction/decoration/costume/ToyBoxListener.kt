package content.global.skill.support.construction.decoration.costume

import content.global.interaction.item.plugins.DiangoReclaimInterfacePlugin
import core.api.consts.Scenery
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

class ToyBoxListener : InteractionListener {

    override fun defineListeners() {
        on(Scenery.TOY_BOX_18802, IntType.SCENERY, "open"){ player, _ ->
            DiangoReclaimInterfacePlugin.open(player)
            return@on true
        }
    }
}