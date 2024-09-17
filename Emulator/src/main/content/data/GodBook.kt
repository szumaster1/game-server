package content.data

import org.rs.consts.Items
import core.api.hasRequirement
import core.game.node.entity.player.Player
import core.game.node.item.Item

/**
 * Represents a god book data.
 *
 * @param bookName      the name of the book.
 * @param book          the book item.
 * @param damagedBook   the damaged book item.
 * @param blessItem     the array of items used to bless the book.
 * @param pages         the array of pages in the book.
 */
enum class GodBook(val bookName: String, val book: Item, val damagedBook: Item, val blessItem: Array<Item>, vararg pages: Item) {
    HOLY_BOOK(
        bookName = "Holy Book of Saradomin",
        book = Item(Items.HOLY_BOOK_3840),
        damagedBook = Item(Items.DAMAGED_BOOK_3839),
        blessItem = arrayOf(Item(Items.HOLY_SYMBOL_1718)),
        pages = arrayOf(
            Item(Items.SARADOMIN_PAGE_1_3827),
            Item(Items.SARADOMIN_PAGE_2_3828),
            Item(Items.SARADOMIN_PAGE_3_3829),
            Item(Items.SARADOMIN_PAGE_4_3830)
        )
    ),
    BOOK_OF_BALANCE(
        bookName = "Guthix's Book of Balance",
        book = Item(Items.BOOK_OF_BALANCE_3844),
        damagedBook = Item(Items.DAMAGED_BOOK_3843),
        blessItem = arrayOf(Item(Items.HOLY_SYMBOL_1718), Item(Items.UNHOLY_SYMBOL_1724)),
        pages = arrayOf(
            Item(Items.GUTHIX_PAGE_1_3835),
            Item(Items.GUTHIX_PAGE_2_3836),
            Item(Items.GUTHIX_PAGE_3_3837),
            Item(Items.GUTHIX_PAGE_4_3838)
        )
    ),
    UNHOLY_BOOK(
        bookName = "Unholy Book of Zamorak",
        book = Item(Items.UNHOLY_BOOK_3842),
        damagedBook = Item(Items.DAMAGED_BOOK_3841),
        blessItem = arrayOf(Item(Items.UNHOLY_SYMBOL_1724)),
        pages = arrayOf(
            Item(Items.ZAMORAK_PAGE_1_3831),
            Item(Items.ZAMORAK_PAGE_2_3832),
            Item(Items.ZAMORAK_PAGE_3_3833),
            Item(Items.ZAMORAK_PAGE_4_3834)
        )
    );

    val pages: Array<Item> = pages as Array<Item>

    /**
     * Checks if the player has the god book.
     *
     * @param player    the player.
     * @param both      the book and the damaged book.
     * @return True if the player has the book, false otherwise.
     */
    fun hasGodBook(player: Player, both: Boolean): Boolean {
        return player.inventory.containsItems(
            *if (both) arrayOf(book, damagedBook) else arrayOf(book)
        )
    }

    /**
     * Inserts a page into the book.
     *
     * @param player    the player.
     * @param book      the book item.
     * @param page      the page item.
     */
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

    /**
     * Checks if an item is a page.
     *
     * @param asItem    the item.
     * @return `true` if the item is a page, `false` otherwise.
     */
    fun isPage(asItem: Item): Boolean {
        for (item in pages) {
            if (item.id == asItem.id) {
                return true
            }
        }
        return false
    }

    /**
     * Checks if the book is complete.
     *
     * @param player    the player.
     * @param book      the book item.
     * @return `true` if the book is complete, `false` otherwise.
     */
    fun isComplete(player: Player, book: Item?): Boolean {
        for (i in 0..3) {
            if (!hasPage(player, book, i + 1)) {
                return false
            }
        }
        return true
    }

    /**
     * Checks if the player has a specific page in the book.
     *
     * @param player    the player.
     * @param book      the book item.
     * @param page      the page item.
     * @return `true` if the player has the page, `false` otherwise.
     */
    fun hasPage(player: Player, book: Item?, page: Item): Boolean {
        return hasPage(player, book, getPageIndex(page))
    }

    /**
     * Sets the page hash.
     *
     * @param player    the player.
     * @param book      the book item.
     * @param pageId    the page ID.
     */
    fun setPageHash(player: Player, book: Item?, pageId: Int) {
        // int hash = getHash(book);
        // hash |= hash | (1 << pageId);
        // book.setCharge(1000 + hash);
        player.getSavedData().globalData.getGodPages()[pageId - 1] = true
    }

    /**
     * Checks if the player has a specific page in the book.
     *
     * @param player    the player.
     * @param book      the book item.
     * @param pageId    the page ID.
     * @return `true` if the player has the page, `false` otherwise.
     */
    fun hasPage(player: Player, book: Item?, pageId: Int): Boolean {
        // return (getHash(book) & (1 << pageId)) != 0;
        return player.getSavedData().globalData.getGodPages()[pageId - 1]
    }

    /**
     * Gets the hash of the book.
     *
     * @param book      the book item.
     * @return The hash of the book.
     */
    fun getHash(book: Item): Int {
        return book.charge - 1000
    }

    /**
     * Gets the index of a page in the book.
     *
     * @param page      the page item.
     * @return The index of the page.
     */
    fun getPageIndex(page: Item): Int {
        for (i in pages.indices) {
            if (pages[i].id == page.id) {
                return i + 1
            }
        }
        return -1
    }

    companion object {

        /**
         * Returns the GodBook enum for a given item.
         *
         * @param item      the item.
         * @param damaged   the damaged item.
         * @return The [GodBook] or `null` if not found.
         */
        @JvmStatic
        fun forItem(item: Item, damaged: Boolean): GodBook? {
            for (book in values()) {
                if ((if (!damaged) book.book.id else book.damagedBook.id) == item.id) {
                    return book
                }
            }
            return null
        }

        /**
         * Returns the [GodBook] for a given page.
         *
         * @param page      the page item.
         * @return The [GodBook] enum, or `null` if not found.
         */
        @JvmStatic
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
