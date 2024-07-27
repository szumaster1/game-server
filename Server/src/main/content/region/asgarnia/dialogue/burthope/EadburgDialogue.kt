package content.region.asgarnia.dialogue.burthope

import core.api.consts.NPCs
import core.api.openDialogue
import core.api.openNpcShop
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC

class EadburgDialogue : DialogueFile(), InteractionListener {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.EADBURG_1072)
        when (stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "What's cooking, good looking?").also { stage++ }
            1 -> npcl(FacialExpression.FRIENDLY, "The stew for the servant's main meal.").also { stage++ }
            2 -> playerl(FacialExpression.HALF_WORRIED, "Um...er...see you later.").also { stage++ }
            3 -> playerl(FacialExpression.HALF_WORRIED, "Um...er...see you later.").also { stage++ }
            4 -> npcl(FacialExpression.FRIENDLY, "Bye!").also { stage++ }
            5 -> {
                end()
                openNpcShop(player!!, NPCs.ANTON_4295)
            }
        }
    }

    override fun defineListeners() {
        on(NPCs.EADBURG_1072, IntType.NPC, "Talk-to") { player, _ ->
            openDialogue(player, EadburgDialogue())
            return@on true
        }
    }

}