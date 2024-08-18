package content.region.kandarin.quest.biohazard.dialogue

import core.api.consts.NPCs
import core.api.sendDialogue
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC

/**
 * Represents the Guidors wife dialogue file.
 */
class GuidorsWifeDialogueFile : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.GUIDORS_WIFE_342)
        when (stage) {
            0 -> sendDialogue(player!!, "Guidor's wife refuses to let you enter.").also { stage++ }
            1 -> npc("Please leave my husband alone. He's very sick, and", "I don't want anyone bothering him.").also { stage++ }
            2 -> player("I'm sorry to hear that. Is there anything", "I can do?").also { stage++ }
            3 -> npc("Thank you, but I just want him to see a priest.").also { stage++ }
            4 -> player(FacialExpression.THINKING, "A priest? Hmmm...").also { stage++ }
            5 -> end()
        }
    }
}