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

class BernaldDialogue : DialogueFile(), InteractionListener {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.BERNALD_2580)
        when (stage) {
            START_DIALOGUE -> npcl(FacialExpression.WORRIED, "Do you know anything about grapevine diseases?").also { stage++ }
            1 -> playerl(FacialExpression.FRIENDLY, "No, I'm afraid I don't.").also { stage++ }
            2 -> npcl(FacialExpression.GUILTY, "Oh, that's a shame. I hope I find someone soon, otherwise I could lose all of this year's crop.").also { stage = END_DIALOGUE }
        }
    }

    override fun defineListeners() {
        on(NPCs.BERNALD_2580, IntType.NPC, "Talk-to") { player, _ ->
            openDialogue(player, BernaldDialogue())
            return@on true
        }
    }

}

