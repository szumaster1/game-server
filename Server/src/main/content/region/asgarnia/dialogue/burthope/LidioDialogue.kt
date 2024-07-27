package content.region.asgarnia.dialogue.burthope

import core.api.consts.NPCs
import core.api.openDialogue
import core.api.openNpcShop
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC

class LidioDialogue : DialogueFile(), InteractionListener {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.LIDIO_4293)
        when (stage) {
            0 -> npcl(FacialExpression.ASKING, "Greetings, warrior, how can I fill your stomach today?").also { stage++ }
            1 -> playerl(FacialExpression.NEUTRAL, "With food preferable.").also { stage++ }
            2 -> {
                end()
                openNpcShop(player!!, NPCs.LIDIO_4293)
            }
        }
    }

    override fun defineListeners() {
        on(NPCs.LIDIO_4293, IntType.NPC, "Talk-to") { player, _ ->
            openDialogue(player, LidioDialogue())
            return@on true
        }
    }

}