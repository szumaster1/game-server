package content.region.kandarin.quest.dwarfcannon.book

import content.global.interaction.iface.BookInterfaceListener
import content.global.interaction.iface.BookLine
import content.global.interaction.iface.Page
import content.global.interaction.iface.PageSet
import core.api.consts.Items
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player

class NulodionsNotes : InteractionListener {
    companion object {
        private val TITLE = "Nulodion's notes"
        private val CONTENTS = arrayOf(
            PageSet(
                Page(
                    BookLine("Ammo for the Dwarf Multi", 55),
                    BookLine("Cannon must be made from", 56),
                    BookLine("steel bars. The bars must be", 57),
                    BookLine("heated in a furnace and used", 58),
                    BookLine("with the ammo mould.", 59),
                )
            ),
        )
    }

    private fun display(player: Player, pageNum: Int, buttonID: Int): Boolean {
        BookInterfaceListener.pageSetup(player, BookInterfaceListener.FANCY_BOOK_3_49, TITLE, CONTENTS)
        return true
    }

    override fun defineListeners() {
        on(Items.NULODIONS_NOTES_3, IntType.ITEM, "read") { player, _ ->
            BookInterfaceListener.openBook(player, BookInterfaceListener.FANCY_BOOK_3_49, ::display)
            return@on true
        }
    }
}
