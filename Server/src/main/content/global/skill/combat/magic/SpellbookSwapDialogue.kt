package content.global.skill.combat.magic

import core.api.sendDialogueOptions
import core.game.component.Component
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.SpellBookManager.SpellBook
import core.plugin.Initializable

/**
 * Spellbook swap dialogue.
 */
@Initializable
class SpellbookSwapDialogue(player: Player? = null) : Dialogue(player) {

    private var perk = false

    override fun open(vararg args: Any): Boolean {
        if (args.size > 1) {
            perk = true
            sendDialogueOptions(player, "Select a Spellbook:", "Modern", "Ancient", "Lunar")
            return true
        }
        sendDialogueOptions(player, "Select a Spellbook:", "Ancient", "Modern")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                if (perk) {
                    val book = SpellBook.values()[buttonId - 1]
                    player.spellBookManager.setSpellBook(book)
                    player.interfaceManager.openTab(Component(book.interfaceId))
                    end()
                    return true
                }
                var type = 0
                when (buttonId) {
                    1 -> type = 1
                    2 -> type = 2
                }
                val book = if (type == 1) SpellBook.ANCIENT else SpellBook.MODERN
                player.spellBookManager.setSpellBook(book)
                player.interfaceManager.openTab(Component(book.interfaceId))
                end()
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(3264731)
    }
}
