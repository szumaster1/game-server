package core.game.node.entity.player.link

import core.api.consts.Components
import core.game.component.Component
import core.game.node.entity.combat.spell.MagicSpell
import core.game.node.entity.player.Player

/**
 * Spell book manager
 *
 * @constructor Spell book manager
 */
class SpellBookManager {

    var spellBook: Int = SpellBook.MODERN.interfaceId

    /**
     * Set spell book
     *
     * @param book
     */
    fun setSpellBook(book: SpellBook) {
        this.spellBook = book.interfaceId
    }

    /**
     * Update
     *
     * @param player
     */
    fun update(player: Player) {
        player.interfaceManager.openTab(Component(SpellBook.forInterface(this.spellBook)!!.interfaceId))
    }

    /**
     * Spell book
     *
     * @property interfaceId
     * @constructor Spell book
     */
    enum class SpellBook(val interfaceId: Int) {
        /**
         * Modern
         *
         * @constructor Modern
         */
        MODERN(Components.MAGIC_192),

        /**
         * Ancient
         *
         * @constructor Ancient
         */
        ANCIENT(Components.MAGIC_ZAROS_193),

        /**
         * Lunar
         *
         * @constructor Lunar
         */
        LUNAR(Components.MAGIC_LUNAR_430);

        private val spells: MutableMap<Int, MagicSpell> = HashMap()

        /**
         * Register
         *
         * @param buttonId
         * @param spell
         */
        fun register(buttonId: Int, spell: MagicSpell) {
            spell.spellId = buttonId
            spells[buttonId] = spell
        }

        /**
         * Get spell
         *
         * @param buttonId
         * @return
         */
        fun getSpell(buttonId: Int): MagicSpell? {
            return spells[buttonId]
        }

        companion object {
            @JvmStatic
            fun forInterface(id: Int): SpellBook? {
                for (book in values()) {
                    if (book.interfaceId == id) {
                        return book
                    }
                }
                return null
            }
        }
    }
}