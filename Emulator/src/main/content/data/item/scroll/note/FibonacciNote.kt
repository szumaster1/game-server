package content.data.item.scroll.note

import cfg.consts.Components
import cfg.consts.Items
import core.api.openInterface
import core.api.setInterfaceText
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player

/**
 * Represents the Fibonacci note.
 */
class FibonacciNote : InteractionListener {

    companion object {
        private fun NotesTheFeud(player: Player) {
            val notesthefeudContent =
                arrayOf("The piece of paper has the word 'Fibonacci'", "scrawled on it.")
            setInterfaceText(player, notesthefeudContent.joinToString("<br>"), Components.BLANK_SCROLL_222, 2)
        }

    }

    override fun defineListeners() {
        on(Items.NOTE_4598, IntType.ITEM, "read") { player, _ ->
            openInterface(player, Components.BLANK_SCROLL_222).also { NotesTheFeud(player) }
            return@on true
        }

    }

}
