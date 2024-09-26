package content.region.kandarin.ourania

import org.rs.consts.Components
import org.rs.consts.NPCs
import core.api.openInterface
import core.api.restrictForIronman
import core.api.setAttribute
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.link.IronmanMode

class EniolaListener : InteractionListener {

    override fun defineListeners() {

        /*
         * Handling the bank at ZMI.
         */

        on(NPCs.ENIOLA_6362, IntType.NPC, "bank") { player, _ ->
            restrictForIronman(player, IronmanMode.ULTIMATE) {
                setAttribute(player, "zmi:bankaction", "open")
                openInterface(player, Components.BANK_CHARGE_ZMI_619)
            }

            return@on true
        }

        /*
         * Handling the collecting a stuff from Grand Exchange at ZMI.
         */

        on(NPCs.ENIOLA_6362, IntType.NPC, "collect") { player, _ ->
            restrictForIronman(player, IronmanMode.ULTIMATE) {
                setAttribute(player, "zmi:bankaction", "collect")
                openInterface(player, Components.BANK_CHARGE_ZMI_619)
            }

            return@on true
        }
    }
}