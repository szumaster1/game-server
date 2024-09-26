package content.global.ame.prisonpete

import core.game.interaction.InterfaceListener
import org.rs.consts.Components

/**
 * Prison pete interface.
 */
class PrisonPeteInterface : InterfaceListener {

    val PRISON_PETE_INTERFACE = Components.PRISONPETE_273
    val BALLOON_ANIMATION_IDS = intArrayOf(3048, 3049, 3050, 3052)

    override fun defineInterfaceListeners() {

        on(PRISON_PETE_INTERFACE) { player, _, _, buttonID, _, _ ->
            return@on true
        }
    }
}
