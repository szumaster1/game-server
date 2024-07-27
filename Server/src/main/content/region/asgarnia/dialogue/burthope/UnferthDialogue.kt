package content.region.asgarnia.dialogue.burthope

import core.api.consts.NPCs
import core.api.openDialogue
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE

class UnferthDialogue : DialogueFile(), InteractionListener {

    override fun handle(componentID: Int, buttonID: Int) {
//      when (stage) {
//          START_DIALOGUE -> playerl(FacialExpression.FRIENDLY, "Hi Unferth. How are you doing?").also { stage++ }
//          1 -> npcl(FacialExpression.FRIENDLY, "It's just not the same without Bob around.").also { stage++ }
//          2 -> playerl(FacialExpression.FRIENDLY, "I'm so sorry Unferth.").also { stage++ }
//          3 -> npcl(FacialExpression.FRIENDLY, "Gertrude asked me if I'd like one of her new kittens. I don't think I'm ready for that yet.").also { stage++ }
//          4 -> playerl(FacialExpression.FRIENDLY, "Give it time. Things will get better, I promise.").also { stage++ }
//          5 -> npcl(FacialExpression.FRIENDLY, "Thanks ${player.name}.").also { stage = END_DIALOGUE }
//      }
        npc = NPC(NPCs.UNFERTH_2655)
        when (stage) {
            START_DIALOGUE -> npcl(FacialExpression.GUILTY, "Hello.").also { stage++ }
            1 -> playerl(FacialExpression.FRIENDLY, "What's wrong?").also { stage++ }
            2 -> npcl(FacialExpression.GUILTY, "It's fine. Nothing for you to worry about.").also { stage = END_DIALOGUE }
        }
    }

    override fun defineListeners() {
        on(NPCs.UNFERTH_2655, IntType.NPC, "Talk-to") { player, _ ->
            openDialogue(player, UnferthDialogue())
            return@on true
        }
    }

}
