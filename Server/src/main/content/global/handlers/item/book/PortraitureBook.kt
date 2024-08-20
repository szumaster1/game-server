package content.global.handlers.item.book

import content.global.handlers.iface.BookInterfaceListener
import content.global.handlers.iface.BookLine
import content.global.handlers.iface.Page
import content.global.handlers.iface.PageSet
import core.api.consts.Items
import core.api.sendItemDialogue
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player

/**
 * Represents the Portraiture book.
 */
class PortraitureBook : InteractionListener {

    /*
     * The book of portraiture is a quest item and has no use other than
     * the one involved in Zogre Flesh Eaters quest, in which it provides
     * a hint to the player. It is found, along with charcoal and a piece
     * of papyrus, in the drawers in the room of Sithik Ints.
     */

    companion object {
        private val TITLE = "book of portraiture"
        private val CONTENTS = arrayOf(
            PageSet(
                Page(
                    BookLine("All interested artisans", 55),
                    BookLine("should really consider", 56),
                    BookLine("taking up the hobby of", 57),
                    BookLine("portraiture. To do so,", 58),
                    BookLine("one uses a piece of", 59),
                    BookLine("papyrus on the intended", 60),
                    BookLine("subject to initiate a", 61),
                    BookLine("likeness drawing activity.", 62),
                ),
            )
        )
    }

    private fun display(player: Player, pageNum: Int, buttonID: Int): Boolean {
        BookInterfaceListener.pageSetup(player, BookInterfaceListener.FANCY_BOOK_3_49, TITLE, CONTENTS)
        return true
    }

    override fun defineListeners() {
        on(Items.BOOK_OF_PORTRAITURE_4817, IntType.ITEM, "read") { player, _ ->
            sendItemDialogue(player, Items.BOOK_OF_PORTRAITURE_4817,"All interested artisans should really consider taking up the hobby of portraiture. To do so, one uses a piece of papyrus on the intended subject to initiate a likeness drawing activity.")
            return@on true
        }
    }
}
