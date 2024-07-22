package content.global.plugins.item

import content.data.GodBook
import core.api.consts.Items
import core.api.getStatLevel
import core.api.sendMessage
import core.game.interaction.NodeUsageEvent
import core.game.interaction.OptionHandler
import core.game.interaction.UseWithHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.GroundItem
import core.game.node.item.Item
import core.game.node.item.ItemPlugin
import core.plugin.ClassScanner
import core.plugin.Initializable
import core.plugin.Plugin

@Initializable
class GodBookPlugin : OptionHandler() {

    override fun newInstance(arg: Any?): Plugin<Any?> {
        for (book in GodBook.values()) {
            book.damagedBook.definition.handlers["option:check"] = this
        }
        ClassScanner.definePlugins(PageHandler(), GodBookItem(), SymbolBlessHandler())
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        val book = GodBook.forItem(node.asItem(), option.equals("check", ignoreCase = true))
        if (book != null) {
            when (option) {
                "check" -> {
                    val messages = arrayOfNulls<String>(4)
                    for (i in messages.indices) {
                        messages[i] = if (book.hasPage(player, node.asItem(), i + 1)) "The " + getNumberName(i + 1) + " page is in the book." else "The " + getNumberName(i + 1) + " page is missing."
                    }
                    player.dialogueInterpreter.sendDialogue(*messages)
                    return true
                }

                "preach" -> {
                    player.dialogueInterpreter.open("god-book", book)
                    return true
                }
            }
        }
        return true
    }

    /*
     * Handles the blessing of a symbol with a god book.
     */

    inner class SymbolBlessHandler : UseWithHandler(Items.UNBLESSED_SYMBOL_1716) {

        override fun newInstance(arg: Any?): Plugin<Any?> {
            for (book in GodBook.values()) {
                addHandler(book.book.id, ITEM_TYPE, this)
            }
            return this
        }

        override fun handle(event: NodeUsageEvent): Boolean {
            val player = event.player
            val book = GodBook.forItem(event.usedItem, false) ?: return false
            val symbol = event.usedWith.asItem()
            if (getStatLevel(player, Skills.PRAYER) < 50) {
                sendMessage(player, "You need a Prayer level of at least 50 in order to do this.")
                return true
            }
            if (player.skills.prayerPoints < 4) {
                sendMessage(player, "You need at least 4 prayer points in order to do this.")
                return true
            }
            if (book == GodBook.BOOK_OF_BALANCE) {
                player.dialogueInterpreter.sendOptions("Select an Option", "Unholy symbol", "Holy symbol")
                player.dialogueInterpreter.addAction { player, buttonId ->
                    bless(player, symbol, if (buttonId == 1) GodBook.UNHOLY_BOOK else GodBook.HOLY_BOOK)
                }
                return true
            }
            bless(player, symbol, book)
            return true
        }

        /*
         * Blesses a symbol.
         */

        private fun bless(player: Player, symbol: Item, book: GodBook) {
            if (!player.inventory.containsItem(symbol)) {
                return
            }
            if (player.inventory[symbol.slot] == null) {
                return
            }
            player.inventory.replace(book.blessItem[0], symbol.slot)
            player.skills.decrementPrayerPoints(4.0)
        }

    }

    /*
     * A god book item.
     */

    inner class GodBookItem : ItemPlugin() {

        override fun newInstance(arg: Any?): Plugin<Any?> {
            for (book in GodBook.values()) {
                register(book.damagedBook.id)
            }
            return this
        }

        override fun canPickUp(player: Player, item: GroundItem, type: Int): Boolean {
            if (player.hasItem(item.asItem())) {
                player.sendMessage("You do not need more than one incomplete book.")
                return false
            }
            return true
        }

    }

    /*
     * The page handler.
     */

    inner class PageHandler : UseWithHandler(3839, 3841, 3843) {

        override fun newInstance(arg: Any?): Plugin<Any?> {
            for (book in GodBook.values()) {
                for (i in book.pages) {
                    addHandler(i.id, ITEM_TYPE, this)
                }
            }
            return this
        }

        override fun handle(event: NodeUsageEvent): Boolean {
            val book = GodBook.forItem(event.usedItem, true)
            val player = event.player
            if (book != null && book.isPage(event.usedWith.asItem())) {
                book.insertPage(player, event.usedItem, event.usedWith.asItem())
                return true
            }
            return false
        }

    }

    /*
     * Gets the number name.
     */

    private fun getNumberName(i: Int): String {
        return when (i) {
            1 -> "first"
            2 -> "second"
            3 -> "third"
            else -> "fourth"
        }
    }

}
