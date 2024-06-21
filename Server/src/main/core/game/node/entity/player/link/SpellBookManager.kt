package core.game.node.entity.player.link

import core.api.consts.Components
import core.game.component.Component
import core.game.node.entity.combat.spell.MagicSpell
import core.game.node.entity.player.Player

class SpellBookManager {

    var spellBook: Int = SpellBook.MODERN.interfaceId

    fun setSpellBook(book: SpellBook) {
        this.spellBook = book.interfaceId
    }

    fun update(player: Player) {
        player.interfaceManager.openTab(Component(SpellBook.forInterface(this.spellBook)!!.interfaceId))
    }

    enum class SpellBook(val interfaceId: Int) {
        MODERN(Components.MAGIC_192),
        ANCIENT(Components.MAGIC_ZAROS_193),
        LUNAR(Components.MAGIC_LUNAR_430);

        private val spells: MutableMap<Int, MagicSpell> = HashMap()

        fun register(buttonId: Int, spell: MagicSpell) {
            spell.spellId = buttonId
            spells[buttonId] = spell
        }

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