package core.game.node.entity.player.link

import core.api.consts.Components
import core.game.component.Component
import core.game.node.entity.combat.spell.MagicSpell
import core.game.node.entity.player.Player

/**
 * Represents a managing class of a players spell book.
 * @author Vexia
 */
class SpellBookManager {

    // Variable to hold the current spell book's interface ID
    var spellBook: Int = SpellBook.MODERN.interfaceId

    /**
     * Set spell book
     *
     * @param book The spell book to set
     */
    fun setSpellBook(book: SpellBook) {
        // Assign the interface ID of the provided spell book to the spellBook variable
        this.spellBook = book.interfaceId
    }

    /**
     * Update
     *
     * @param player The player whose interface will be updated
     */
    fun update(player: Player) {
        // Open the spell book tab for the player using the current spell book's interface ID
        player.interfaceManager.openTab(Component(SpellBook.forInterface(this.spellBook)!!.interfaceId))
    }

    /**
     * Spell book
     *
     * @param interfaceId The interface ID of the spell book
     * @constructor Spell book
     */
    enum class SpellBook(val interfaceId: Int) {
        /**
         * Modern.
         */
        MODERN(Components.MAGIC_192),

        /**
         * Ancient.
         */
        ANCIENT(Components.MAGIC_ZAROS_193),

        /**
         * Lunar.
         */
        LUNAR(Components.MAGIC_LUNAR_430);

        // A mutable map to hold spells associated with their button IDs
        private val spells: MutableMap<Int, MagicSpell> = HashMap()

        /**
         * Register
         *
         * @param buttonId The button ID associated with the spell
         * @param spell The magic spell to register
         */
        fun register(buttonId: Int, spell: MagicSpell) {
            // Set the spell ID for the magic spell and add it to the spells map
            spell.spellId = buttonId
            spells[buttonId] = spell
        }

        /**
         * Get spell
         *
         * @param buttonId The button ID of the spell to retrieve
         * @return The magic spell associated with the button ID, or null if not found
         */
        fun getSpell(buttonId: Int): MagicSpell? {
            // Return the spell associated with the given button ID from the spells map
            return spells[buttonId]
        }

        companion object {
            @JvmStatic
            fun forInterface(id: Int): SpellBook? {
                // Iterate through all spell books to find one that matches the given interface ID
                for (book in values()) {
                    if (book.interfaceId == id) {
                        return book // Return the matching spell book
                    }
                }
                return null // Return null if no matching spell book is found
            }
        }
    }
}
