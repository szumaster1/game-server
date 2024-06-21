package content.region.kandarin.quest.ew1.book

import content.global.interaction.iface.BookInterfaceListener
import content.region.kandarin.quest.ew1.util.EWUtils
import core.api.consts.Items
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player

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
