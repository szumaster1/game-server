package content.region.kandarin.feldip.ooglog

import org.rs.consts.Scenery
import content.region.kandarin.feldip.ooglog.dialogue.GrimechinDialogue
import core.api.openDialogue
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * Ooglog listeners.
 */
class Ooglog : InteractionListener {

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