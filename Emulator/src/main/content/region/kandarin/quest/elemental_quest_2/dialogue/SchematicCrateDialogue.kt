package content.region.kandarin.quest.elemental_quest_2.dialogue

import content.region.kandarin.quest.elemental_quest_2.handlers.EW2Utils
import core.api.addItemOrDrop
import core.api.sendDialogueOptions
import core.api.sendItemDialogue
import core.game.dialogue.DialogueFile

/**
 * Schematic crate dialogue.
 */
class SchematicCrateDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        when (stage) {
            0 -> sendDialogueOptions(player!!, "There are two schematics here...", "Crane schematic", "Lever schematic").also { stage++ }

            1 -> when (buttonID) {
                1 -> {
                    sendItemDialogue(player!!, EW2Utils.CraneSchematic, "You take a copy of the schematic drawing.")
                    addItemOrDrop(player!!, EW2Utils.CraneSchematic)
                    end()
                }

                2 -> {
                    sendItemDialogue(player!!, EW2Utils.LeverSchematic, "You take a copy of the schematic drawing.")
                    addItemOrDrop(player!!, EW2Utils.LeverSchematic)
                    end()
                }
            }
        }
    }
}