package content.global.travel.glider

import core.api.isQuestComplete
import core.api.openInterface
import core.api.sendMessage
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import org.rs.consts.Components
import org.rs.consts.NPCs

/**
 * Handles the gnome pilots option.
 */
class GliderOption : InteractionListener {

    private val GNOME_PILOTS = intArrayOf(NPCs.CAPTAIN_DALBUR_3809, NPCs.CAPTAIN_BLEEMADGE_3810, NPCs.CAPTAIN_ERRDO_3811, NPCs.CAPTAIN_KLEMFOODLE_3812)

    override fun defineListeners() {

        on(GNOME_PILOTS, IntType.NPC, "glider") { player, _ ->
            if (!isQuestComplete(player, "The Grand Tree")) {
                sendMessage(player, "You must complete The Grand Tree Quest to access the gnome glider.")
            } else {
                openInterface(player, Components.GLIDERMAP_138)
            }
            return@on true
        }
    }


}
