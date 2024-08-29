package content.region.kandarin.ooglog

import content.region.kandarin.ooglog.dialogue.GrimechinDialogue
import cfg.consts.Scenery
import core.api.openDialogue
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * Ooglog listeners.
 */
class OoglogListeners : InteractionListener {

    companion object {
        private const val GRIMECHIN = Scenery.GRIMECHIN_29106
    }

    /*
     * Handle talk interaction with Grimechin.
     */

    override fun defineListeners() {
        on(GRIMECHIN, IntType.SCENERY, "Talk-to") { player, _ ->
            openDialogue(player, GrimechinDialogue())
            return@on true
        }
    }
}