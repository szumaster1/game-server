package content.global.random.event.drunkdwarf

import core.api.addItemOrDrop
import core.api.consts.Items
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.system.timer.impl.AntiMacro

/**
 * Represents the Drunken Dwarf dialogue.
 */
class DrunkenDwarfDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        when (stage) {
            0 -> {
                npcl(FacialExpression.OLD_DRUNK_RIGHT, "I 'new it were you matey! 'Ere, have some ob the good stuff!").also { stage++ }
            }

            1 -> {
                addItemOrDrop(player!!, Items.BEER_1917)
                addItemOrDrop(player!!, Items.KEBAB_1971)
                AntiMacro.terminateEventNpc(player!!)
                end()
            }
        }
    }
}
