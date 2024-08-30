package content.region.kandarin.seers_village.quest.elemental_workshop.book

import content.global.handlers.iface.BookInterfaceListener
import content.region.kandarin.seers_village.quest.elemental_workshop.util.EWUtils
import cfg.consts.Items
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player

/**
 * Slashed book handler.
 */
class SlashedBookHandler : InteractionListener {
    companion object {
        private val TITLE = "Book of the elemental shield"
        private val CONTENTS = EWUtils.PAGES

        private fun display(player: Player, pageNum: Int, buttonID: Int): Boolean {
            BookInterfaceListener.pageSetup(player, BookInterfaceListener.FANCY_BOOK_3_49, TITLE, CONTENTS)
            return true
        }
    }

    override fun defineListeners() {
        on(Items.SLASHED_BOOK_9715, IntType.ITEM, "read") { player, _ ->
            BookInterfaceListener.openBook(player, BookInterfaceListener.FANCY_BOOK_3_49, Companion::display)
            return@on true
        }
    }
}
