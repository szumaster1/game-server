package content.region.asgarnia.dialogue.burthope

import core.api.consts.NPCs
import core.api.openDialogue
import core.api.openNpcShop
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC

class AntonDialogue : DialogueFile(), InteractionListener {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.ANTON_4295)
        when (stage) {
            0 -> npcl(FacialExpression.ASKING, "Ahhh, hello there. How can I help?").also { stage++ }
            1 -> playerl(FacialExpression.NEUTRAL, "Looks like you have a good selection of weapons around here...").also { stage++ }
            2 -> npcl(FacialExpression.FRIENDLY, "Indeed so, specially imported from the finest smiths around the lands, take a look at my wares.").also { stage++ }
            3 -> npcl(FacialExpression.FRIENDLY, "Indeed so, specially imported from the finest smiths around the lands, take a look at my wares.").also { stage++ }
            4 -> {
                end()
                openNpcShop(player!!, NPCs.ANTON_4295)
            }
        }
    }

    override fun defineListeners() {
        on(NPCs.ANTON_4295, IntType.NPC, "Talk-to") { player, _ ->
            openDialogue(player, AntonDialogue())
            return@on true
        }
    }

}