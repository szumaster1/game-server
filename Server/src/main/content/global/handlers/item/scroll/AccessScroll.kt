package content.global.handlers.item.scroll

import cfg.consts.Components
import cfg.consts.Items
import core.api.openInterface
import core.api.setInterfaceText
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * Represents the Access scroll.
 */
class AccessScroll : InteractionListener {

    /*
     * The Scroll is given to the player by King Vargas
     * during Royal Trouble. Granting you access into
     * the underground caves.
     */

    companion object {

        private const val MESSAGE_SCROLL = Components.BLANK_SCROLL_222

        val crumpledScrollContent = arrayOf(
            "I, King Vargas, proclaim that the bearer of this",
            "scroll is the Regent of Miscellania.",
        )
    }

    override fun defineListeners() {
        on(Items.SCROLL_7968, IntType.ITEM, "read") { player, _ ->
            openInterface(player, MESSAGE_SCROLL)
            setInterfaceText(player, crumpledScrollContent.joinToString("<br>"), MESSAGE_SCROLL, 8)
            return@on true
        }
    }
}
