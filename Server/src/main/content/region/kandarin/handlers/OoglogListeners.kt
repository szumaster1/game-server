package content.region.kandarin.handlers

import content.region.kandarin.dialogue.ooglog.GrimechinDialogue
import core.api.consts.Scenery
import core.api.openDialogue
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

class OoglogListeners : InteractionListener {

    companion object {
        private const val GRIMECHIN = Scenery.GRIMECHIN_29106
    }

    override fun defineListeners() {
        on(GRIMECHIN, IntType.SCENERY, "Talk-to") { player, _ ->
            openDialogue(player, GrimechinDialogue())
            return@on true
        }
    }
}