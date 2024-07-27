package content.region.asgarnia.dialogue.burthope

import core.api.consts.NPCs
import core.api.openDialogue
import core.api.openNpcShop
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE

class WistanDialogue : DialogueFile(), InteractionListener {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.WISTAN_1083)
        when (stage) {
            START_DIALOGUE -> playerl(FacialExpression.FRIENDLY, "Hi!").also { stage++ }
            1 -> npcl(FacialExpression.FRIENDLY, "Welcome to Burthorpe Supplies. Your last shop before heading north into the mountains!").also { stage++ }
            2 -> npcl(FacialExpression.FRIENDLY, "Would you like to buy something?").also { stage++ }
            3 -> options("Yes, please.", "No, thanks.").also { stage++ }
            4 -> when (buttonID) {
                1 -> playerl(FacialExpression.FRIENDLY, "Yes, please.").also { stage = 5 }
                2 -> playerl(FacialExpression.FRIENDLY, "No, thanks.").also { stage = END_DIALOGUE }
            }
            5 -> {
                end()
                openNpcShop(player!!, NPCs.WISTAN_1083)
            }
        }
    }

    override fun defineListeners() {
        on(NPCs.WISTAN_1083, IntType.NPC, "Talk-to") { player, _ ->
            openDialogue(player, WistanDialogue())
            return@on true
        }
    }
}
