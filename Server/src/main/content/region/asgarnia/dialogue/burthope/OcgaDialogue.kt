package content.region.asgarnia.dialogue.burthope

import core.api.consts.NPCs
import core.api.isQuestComplete
import core.api.openDialogue
import core.api.toIntArray
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE

class OcgaDialogue : DialogueFile(), InteractionListener {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.OCGA_1085)
        if (isQuestComplete(player!!, "Death Plateau")) {
            when (stage) {
                START_DIALOGUE -> playerl(FacialExpression.FRIENDLY, "Hi!").also { stage = (1..3).toIntArray().random() }
                1 -> npcl(FacialExpression.HAPPY, "I heard about what you did, thank you!").also { stage = END_DIALOGUE }
                2 -> npcl(FacialExpression.HAPPY, "Thank you so much!").also { stage = END_DIALOGUE }
                3 -> npcl(FacialExpression.HAPPY, "Surely we are safe now!").also { stage = END_DIALOGUE }
            }
        } else {
            when (stage) {
                START_DIALOGUE -> playerl(FacialExpression.FRIENDLY, "Hi!").also { stage = (1..3).toIntArray().random() }
                1 -> npcl(FacialExpression.FRIENDLY, "Hello stranger.").also { stage = END_DIALOGUE }
                2 -> npcl(FacialExpression.FRIENDLY, "Hi!").also { stage = END_DIALOGUE }
                3 -> npcl(FacialExpression.FRIENDLY, "Welcome to Burthorpe!").also { stage++ }
                4 -> playerl(FacialExpression.FRIENDLY, "Thanks!").also { stage = END_DIALOGUE }
            }
        }
    }

    override fun defineListeners() {
        on(NPCs.OCGA_1085, IntType.NPC, "Talk-to") { player, _ ->
            openDialogue(player, OcgaDialogue())
            return@on true
        }
    }

}
