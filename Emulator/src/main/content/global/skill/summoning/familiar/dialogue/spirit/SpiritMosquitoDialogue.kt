package content.global.skill.summoning.familiar.dialogue.spirit

import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Spirit mosquito dialogue.
 */
@Initializable
class SpiritMosquitoDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when ((0..3).random()) {
            0 -> npcl(FacialExpression.CHILD_NORMAL, "You have lovely ankles.").also { stage = 0 }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "How about that local sports team?").also { stage = 4 }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "Have you ever tasted pirate blood?").also { stage = 9 }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "I'm soooo hungry!").also { stage = 13 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "Am I meant to be pleased by that?").also { stage++ }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "Thin skin. Your delicious blood is easier to get too.").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "I knew I couldn't trust you.").also { stage++ }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "Oh come on, you won't feel a thing...").also { stage = END_DIALOGUE }

            4 -> playerl(FacialExpression.FRIENDLY, "Which one? The gnomeball team?").also { stage++ }
            5 -> npcl(FacialExpression.CHILD_NORMAL, "I must confess: I have no idea.").also { stage++ }
            6 -> playerl(FacialExpression.FRIENDLY, "Why did you ask, then?").also { stage++ }
            7 -> npcl(FacialExpression.CHILD_NORMAL, "I was just trying to be friendly.").also { stage++ }
            8 -> playerl(FacialExpression.FRIENDLY, "Just trying to get to my veins, more like!").also { stage = END_DIALOGUE }

            9 -> playerl(FacialExpression.FRIENDLY, "Why would I drink pirate blood?").also { stage++ }
            10 -> npcl(FacialExpression.CHILD_NORMAL, "How about dwarf blood?").also { stage++ }
            11 -> playerl(FacialExpression.FRIENDLY, "I don't think you quite understand...").also { stage++ }
            12 -> npcl(FacialExpression.CHILD_NORMAL, "Gnome blood, then?").also { stage = END_DIALOGUE }

            13 -> playerl(FacialExpression.FRIENDLY, "What would you like to eat?").also { stage++ }
            14 -> npcl(FacialExpression.CHILD_NORMAL, "Well, if you're not too attached to your elbow...").also { stage++ }
            15 -> playerl(FacialExpression.FRIENDLY, "You can't eat my elbow! You don't have teeth!").also { stage++ }
            16 -> npcl(FacialExpression.CHILD_NORMAL, "Tell me about it. Cousin Nigel always makes fun of me. Calls me 'No-teeth'.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SPIRIT_MOSQUITO_7331, NPCs.SPIRIT_MOSQUITO_7332)
    }

}
