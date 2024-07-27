package content.region.asgarnia.dialogue.burthope

import core.api.consts.NPCs
import core.api.getStatLevel
import core.api.openDialogue
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.game.node.entity.skill.Skills
import core.tools.END_DIALOGUE

class GhommalDialogue : DialogueFile(), InteractionListener {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.GHOMMAL_4285)
        when (stage) {
            0 -> if (getStatLevel(player!!, Skills.ATTACK) + getStatLevel(player!!, Skills.STRENGTH) >= 130 || getStatLevel(player!!, Skills.ATTACK) == 99 || getStatLevel(player!!, Skills.STRENGTH) == 99) {
                npc(FacialExpression.HALF_GUILTY, "Ghommal welcome you to Warrior Guild!").also { stage = 6 }
            } else {
                npc(FacialExpression.HALF_GUILTY, "You not pass. You too weedy.").also { stage++ }
            }
            1 -> player(FacialExpression.HALF_GUILTY, "What? But I'm a warrior!").also { stage++ }
            2 -> npc(FacialExpression.HALF_GUILTY, "Heehee... he say he warrior... I not heard that one", "for... at leas' 5 minutes!").also { stage++ }
            3 -> player(FacialExpression.HALF_GUILTY, "Go on, let me in, you know you want to. I could...", "make it worth your while...").also { stage++ }
            4 -> npc(FacialExpression.HALF_GUILTY, "No! You is not a strong warrior, you not enter till you", "bigger, Ghommal does not takes bribes.").also { stage++ }
            5 -> npc(FacialExpression.HALF_GUILTY, "Ghommal stick to Warrior's Code of Honour. When", "you a bigger, stronger warriror, you come back.").also { stage = END_DIALOGUE }
            6 -> player(FacialExpression.HALF_GUILTY, "Umm...thank you, I think.").also { stage = END_DIALOGUE }
        }
    }

    override fun defineListeners() {
        on(NPCs.GHOMMAL_4285, IntType.NPC, "Talk-to") { player, _ ->
            openDialogue(player, GhommalDialogue())
            return@on true
        }
    }
}
