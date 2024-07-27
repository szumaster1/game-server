package content.region.asgarnia.dialogue.burthope

import core.api.consts.NPCs
import core.api.openDialogue
import core.api.openNpcShop
import core.api.sendDialogueOptions
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE

class LillyDialogue : DialogueFile(), InteractionListener {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.LILLY_4294)
        when (stage) {
            0 -> npcl(FacialExpression.HALF_GUILTY, "Uh..... hi... didn't see you there. Can.... I help?").also { stage =-1 }
           -1 -> player(FacialExpression.HALF_GUILTY, "Umm... do you sell potions?").also { stage = 1 }
            1 -> npc(FacialExpression.HALF_GUILTY, "Erm... yes. When I'm not drinking them.").also { stage++ }
            2 -> sendDialogueOptions(player!!, "What would you like to say?", "I'd like to see what you have for sale.", "That's a pretty wall hanging.", "Bye!").also { stage++ }
            3 -> when (buttonID) {
                1 -> player(FacialExpression.HALF_GUILTY, "I'd like to see what you have for sale.").also { stage++ }
                2 -> player(FacialExpression.HALF_GUILTY, "That's a pretty wall hanging.").also { stage = 5 }
                3 -> player(FacialExpression.HALF_GUILTY, "Bye!").also { stage= 8 }
            }
            4 -> {
                end()
                openNpcShop(player!!, NPCs.LILLY_4294)
            }
            5 -> npc(FacialExpression.HALF_GUILTY, "Do you think so? I made it my self.").also { stage++ }
            6 -> player(FacialExpression.HALF_GUILTY, "Really? Is that why there's all this cloth and dye around?").also { stage++ }
            7 -> npc(FacialExpression.HALF_GUILTY, "Yes, it's a hobby of mine when I'm.... relaxing.").also { stage++ }
            8 -> npc(FacialExpression.HALF_GUILTY, "Have fun and come back soon!").also { stage = END_DIALOGUE }
        }
    }

    override fun defineListeners() {
        on(NPCs.LILLY_4294, IntType.NPC, "Talk-to") { player, _ ->
            openDialogue(player, LillyDialogue())
            return@on true
        }
    }

}
