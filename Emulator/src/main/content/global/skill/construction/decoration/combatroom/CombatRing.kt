package content.global.skill.construction.decoration.combatroom

import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * Combat ring listener.
 */
class CombatRing : InteractionListener {

    private val COMBAT_RING = intArrayOf(13129, 13133, 13137)

    override fun defineListeners() {
        on(COMBAT_RING, IntType.SCENERY, "climb-over") { player, ring ->
            return@on true
        }
    }

}