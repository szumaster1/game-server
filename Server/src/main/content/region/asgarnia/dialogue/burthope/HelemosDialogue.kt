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

class HelemosDialogue : DialogueFile(), InteractionListener {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.HELEMOS_797)
        when (stage) {
            0 -> npc(FacialExpression.FRIENDLY, "Greetings. Welcome to the Heroes' Guild.").also { stage++ }
            1 -> options("So do you sell anything here?", "So what can I do here?").also { stage++ }
            2 -> when (buttonID) {
                1 -> playerl(FacialExpression.HALF_ASKING, "So do you sell anything good here?").also { stage++ }
                2 -> playerl(FacialExpression.HALF_ASKING, "So what can I do here?").also { stage = 5 }
            }
            3 -> npcl(FacialExpression.HAPPY, "Why yes! We DO run an exclusive shop for our members!").also { stage++ }
            4 -> {
                end()
                openNpcShop(player!!, NPCs.HELEMOS_797)
            }
            5 -> npcl(FacialExpression.HAPPY, "Look around... there are all sorts of things to keep our guild members entertained!").also { stage = END_DIALOGUE }
        }
    }

    override fun defineListeners() {
        on(NPCs.HELEMOS_797, IntType.NPC, "Talk-to") { player, _ ->
            openDialogue(player, HelemosDialogue())
            return@on true
        }
    }
}
