package content.region.karamja.apeatoll.quest.mm.dialogue

import core.api.addItemOrDrop
import org.rs.consts.Items
import core.api.sendDialogueOptions
import core.api.sendItemDialogue
import core.api.setTitle
import core.game.dialogue.DialogueFile

/**
 * Represents the Hangr crate dialogue.
 */
class HangarCrateDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        when (stage) {
            0 -> sendItemDialogue(player!!, Items.SPARE_CONTROLS_4002, "This crate is full of ... spare controls!").also { stage++ }

            1 -> {
                setTitle(player!!, 2)
                sendDialogueOptions(player!!, "Do you wish to take one?", "Yes", "No").also { stage++ }
            }

            2 -> when (buttonID) {
                1 -> addItemOrDrop(player!!, Items.SPARE_CONTROLS_4002).also { end() }
                2 -> end()
            }
        }
    }
}