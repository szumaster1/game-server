package content.data

import core.api.hasRequirement
import core.game.node.entity.player.Player
import core.game.node.item.Item

enum class GodBook(
    name: String,
    val book: Item,
    val damagedBook: Item,
    val blessItem: Array<Item>, vararg pages: Item
) {
    HOLY_BOOK(
        "Holy Book of Saradomin",
        Item(3840),
        Item(3839),
        arrayOf(Item(1718)),
        Item(3827),
        Item(3828),
        Item(3829),
        Item(3830)
    ),
    BOOK_OF_BALANCE(
        "Guthix's Book of Balance",
        Item(3844),
        Item(3843),
        arrayOf(Item(1718), Item(1724)),
        Item(3835),
        Item(3836),
        Item(3837),
        Item(3838)
    ),
    UNHOLY_BOOK(
        "Unholy Book of Zamorak",
        Item(3842),
        Item(3841),
        arrayOf(Item(1724)),
        Item(3831),
        Item(3832),
        Item(3833),
        Item(3834)
    );

    val pages: Array<Item> = pages as Array<Item>

    fun hasGodBook(player: Player, both: Boolean): Boolean {
        return player.inventory.containsItems(
            *if (both) arrayOf(book, damagedBook) else arrayOf(
                book
            )
        )
    }

    fun insertPage(player: Player, book: Item, page: Item) {
        if (!hasRequirement(player, "Horror from the Deep")) return
        if (hasPage(player, book, page)) {
            player.sendMessage("The book already has that page.")
            return
        }
        if (player.inventory.remove(Item(page.id, 1))) {
            setPageHash(player, book, getPageIndex(page))
            player.sendMessage("You add the page to the book...")
            if (isComplete(player, book)) {
                player.getSavedData().globalData.setGodPages(BooleanArray(4))
                player.getSavedData().globalData.setGodBook(-1)
                player.inventory.replace(this.book, book.slot)
                player.getSavedData().globalData.setGodBook(this)
                player.sendMessage("The book is now complete!")
                val message =
                    if (this == UNHOLY_BOOK) "unholy symbols" else if (this == HOLY_BOOK) "holy symbols" else "unblessed holy symbols"
                player.sendMessage("You can now use it to bless $message!")
            }
        }
    }

    fun isPage(asItem: Item): Boolean {
        for (item in pages) {
            if (item.id == asItem.id) {
                return true
            }
        }
        return false
    }

    fun isComplete(player: Player, book: Item?): Boolean {
        for (i in 0..3) {
            if (!hasPage(player, book, i + 1)) {
                return false
            }
        }
        return true
    }

    fun hasPage(player: Player, book: Item?, page: Item): Boolean {
        return hasPage(player, book, getPageIndex(page))
    }

    fun setPageHash(player: Player, book: Item?, pageId: Int) {
        // int hash = getHash(book);
        // hash |= hash | (1 << pageId);
        // book.setCharge(1000 + hash);
        player.getSavedData().globalData.getGodPages()[pageId - 1] = true
    }

    fun hasPage(player: Player, book: Item?, pageId: Int): Boolean {
        // return (getHash(book) & (1 << pageId)) != 0;
        return player.getSavedData().globalData.getGodPages()[pageId - 1]
    }

    fun getHash(book: Item): Int {
        return book.charge - 1000
    }


    fun getPageIndex(page: Item): Int {
        for (i in pages.indices) {
            if (pages[i].id == page.id) {
                return i + 1
            }
        }
        return -1
    }

    companion object {

        fun forItem(item: Item, damaged: Boolean): GodBook? {
            for (book in values()) {
                if ((if (!damaged) book.book.id else book.damagedBook.id) == item.id) {
                    return book
                }
            }
            return null
        }

        fun forPage(page: Item): GodBook? {
            for (book in values()) {
                for (i in book.pages) {
                    if (i.id == page.id) {
                        return book
                    }
                }
            }
            return null
        }
    }
}
