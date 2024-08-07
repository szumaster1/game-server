package content.global.skill.combat.summoning.familiar.dialogue

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Abyssal parasite dialogue.
 */
@Initializable
class AbyssalParasiteDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when ((0..4).random()) {
            0 -> npcl(FacialExpression.CHILD_NORMAL, "Ongk n'hd?").also { stage = 0 }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "Noslr'rh...").also { stage = 5 }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "Ace'e e ur'y!").also { stage = 9 }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "Tdsa tukk!").also { stage = 10 }
            4 -> npcl(FacialExpression.CHILD_NORMAL, "Tdsa tukk!").also { stage = 12 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.HALF_WORRIED, "Oh, I'm not feeling so well.").also { stage++ }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "Uge f't es?").also { stage++ }
            2 -> playerl(FacialExpression.SAD, "Please have mercy!").also { stage++ }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "F'tp ohl't?").also { stage++ }
            4 -> playerl(FacialExpression.AFRAID, "I shouldn't have eaten that kebab. Please stop talking!").also { stage = END_DIALOGUE }

            5 -> playerl(FacialExpression.HALF_ASKING, "What's the matter?").also { stage++ }
            6 -> npcl(FacialExpression.CHILD_NORMAL, "Kdso Seo...").also { stage++ }
            7 -> playerl(FacialExpression.HALF_ASKING, "Could you...could you mime what the problem is?").also { stage++ }
            8 -> npcl(FacialExpression.CHILD_NORMAL, "Yiao itl!").also { stage++ }

            9 -> playerl(FacialExpression.HALF_ASKING, "I want to help it but, aside from the language gap its noises make me retch!").also { stage = END_DIALOGUE }

            10 -> playerl(FacialExpression.HALF_WORRIED, "I think I'm going to be sick... The noises! Oh, the terrifying noises.").also { stage = END_DIALOGUE }
            11 -> playerl(FacialExpression.AFRAID, "Oh, the noises again.").also { stage = END_DIALOGUE }

            12 -> playerl(FacialExpression.AFRAID, "Oh, the noises again.").also { stage++ }
            13 -> npcl(FacialExpression.CHILD_NORMAL, "Hem s'htee?").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ABYSSAL_PARASITE_6818, NPCs.ABYSSAL_PARASITE_6819)
    }

}
