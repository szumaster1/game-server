package content.region.asgarnia.dialogue.burthope

import core.api.consts.NPCs
import core.api.openDialogue
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE

class AchiettiesDialogue : DialogueFile(), InteractionListener {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.ACHIETTIES_796)
        when (stage) {
            0 -> npc(FacialExpression.FRIENDLY, "Greetings. Welcome to the Heroes' Guild.").also { stage = END_DIALOGUE }
        }
    }

    override fun defineListeners() {
        on(NPCs.ACHIETTIES_796, IntType.NPC, "Talk-to") { player, _ ->
            openDialogue(player, AchiettiesDialogue())
            return@on true
        }
    }

}