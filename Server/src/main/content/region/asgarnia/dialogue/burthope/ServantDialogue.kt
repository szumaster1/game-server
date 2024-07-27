package content.region.asgarnia.dialogue.burthope

import core.api.consts.NPCs
import core.api.openDialogue
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE

class ServantDialogue : DialogueFile(), InteractionListener {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.SERVANT_1081)
        when (stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "Hi!").also { stage++ }
            1 -> npcl(FacialExpression.HALF_GUILTY, "Hi").also { stage++ }
            2 -> npcl(FacialExpression.HALF_GUILTY, "Look, I'd better not talk. I'll get in trouble.").also { stage++ }
            3 -> npcl(FacialExpression.HALF_GUILTY, "If you want someone to show you round the castle ask Eohric, the Head Servant.").also { stage = END_DIALOGUE }
        }
    }

    override fun defineListeners() {
        on(NPCs.SERVANT_1081, IntType.NPC, "Talk-to") { player, _ ->
            openDialogue(player, ServantDialogue())
            return@on true
        }
    }

}
