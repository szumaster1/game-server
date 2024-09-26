package content.region.fremennik.quest.olaf.handlers

import org.rs.consts.Components
import org.rs.consts.Items
import core.api.openInterface
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

class OlafsQuestListener : InteractionListener {

    //iface 252	olaf2_lock_gate
    //iface 253	olaf2_skull_puzzle
    override fun defineListeners() {
        val treasuremap = Items.SVENS_LAST_MAP_11034

        on(treasuremap, IntType.ITEM, "read") { player, _ ->
            openInterface(player, Components.OLAF2_TREASUREMAP_254)
            return@on true
        }
    }

}