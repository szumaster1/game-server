package content.region.asgarnia.dialogue.burthope

import content.region.asgarnia.quest.deathplateau.dialogue.EohricDialogueFile
import core.api.consts.NPCs
import core.api.isQuestComplete
import core.api.openDialogue
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE

class EohricDialogue : DialogueFile(), InteractionListener {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.EOHRIC_1080)
        when (stage) {
            0 -> if (!isQuestComplete(player!!, "Death Plateau")) {
                openDialogue(player!!, EohricDialogueFile(), npc!!)
            } else {
                player(FacialExpression.FRIENDLY, "Hi!").also { stage++ }
            }
            1 -> npc(FacialExpression.ASKING, "Hello. Can I help?").also { stage++ }
            2 -> options("What is this place?", "That's quite an outfit.", "Goodbye.").also { stage++ }
            3 -> when (buttonID) {
                1 -> playerl(FacialExpression.ASKING, "What is this place?").also { stage = 5 }
                2 -> playerl(FacialExpression.ASKING, "That's quite an outfit.").also { stage++ }
                3 -> player(FacialExpression.FRIENDLY, "Goodbye.").also { stage = END_DIALOGUE }
            }
            4 -> npcl(FacialExpression.HAPPY, "Why, thank you. I designed it myself. I've always found purple such a cheerful colour!").also { stage = 2 }
            5 -> npcl(FacialExpression.FRIENDLY, "This is Burthorpe Castle, home to His Royal Highness Prince Anlaf, heir to the throne of Asgarnia.").also { stage = 10 }
            6 -> npc(FacialExpression.FRIENDLY, "No doubt you're impressed.").also { stage++ }
            7 -> options("Where is the prince?", "Goodbye.").also { stage++ }
            8 -> when (buttonID) {
                1 -> npcl(FacialExpression.SUSPICIOUS, "I cannot disclose the prince's exact whereabouts for fear of compromising his personal safety.").also { stage = 8 }
                2 -> player(FacialExpression.FRIENDLY, "Goodbye.").also { stage = END_DIALOGUE }
            }
            9 -> npcl(FacialExpression.FRIENDLY, "But rest assured that he is working tirelessly to maintain the safety and well being of Burthorpe's people.").also { stage = 2 }
        }
    }

    override fun defineListeners() {
        on(NPCs.EOHRIC_1080, IntType.NPC, "Talk-to") { player, _ ->
            openDialogue(player, EohricDialogue())
            return@on true
        }
    }

}
