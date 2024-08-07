package content.global.skill.combat.summoning.familiar.dialogue.titan

import core.api.consts.NPCs
import core.api.sendDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Moss titan dialogue.
 */
@Initializable
class MossTitanDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when ((0..3).random()) {
            0 -> npcl(FacialExpression.CHILD_NORMAL, "Oh, look! A bug!").also { stage = 0 }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "When you stamp on 'em, humies go squish.").also { stage = 9 }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "Stampy stampy stampy stampy stampy stampy, I've got big feet.").also { stage = 23 }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "What are we doing today?").also { stage = 31 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "It's quite a large bug.").also { stage++ }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "He's so cute! I wanna keep him.").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "Well, be careful.").also { stage++ }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "I'm gonna call him Buggie and I'm gonna keep him in a box.").also { stage++ }
            4 -> playerl(FacialExpression.FRIENDLY, "Don't get overexcited.").also { stage++ }
            5 -> npcl(FacialExpression.CHILD_NORMAL, "I'm gonna feed him and we're gonna be so happy together!").also { stage++ }
            6 -> sendDialogue(player!!, "The Moss titan begins to bounce up and down.").also { stage++ }
            7 -> npcl(FacialExpression.CHILD_NORMAL, "Aww...Buggie went squish.").also { stage++ }
            8 -> playerl(FacialExpression.FRIENDLY, "Sigh.").also { stage = END_DIALOGUE }

            9 -> npcl(FacialExpression.CHILD_NORMAL, "When you punch 'em, humies go squish.").also { stage++ }
            10 -> playerl(FacialExpression.FRIENDLY, "...").also { stage++ }
            11 -> npcl(FacialExpression.CHILD_NORMAL, "When you push 'em, humies go squish.").also { stage++ }
            12 -> playerl(FacialExpression.FRIENDLY, "...").also { stage++ }
            13 -> npcl(FacialExpression.CHILD_NORMAL, "Squish squish squish.").also { stage++ }
            14 -> playerl(FacialExpression.FRIENDLY, "...").also { stage++ }
            15 -> npcl(FacialExpression.CHILD_NORMAL, "When you touch 'em, humies go squish.").also { stage++ }
            16 -> playerl(FacialExpression.FRIENDLY, "...").also { stage++ }
            17 -> npcl(FacialExpression.CHILD_NORMAL, "When you talk to 'em, humies go squish.").also { stage++ }
            18 -> playerl(FacialExpression.FRIENDLY, "...").also { stage++ }
            19 -> npcl(FacialExpression.CHILD_NORMAL, "When you poke 'em, humies go squish.").also { stage++ }
            20 -> playerl(FacialExpression.FRIENDLY, "...").also { stage++ }
            21 -> npcl(FacialExpression.CHILD_NORMAL, "Squish squish squish.").also { stage++ }
            22 -> playerl(FacialExpression.FRIENDLY, "You have problems, you know that. Come on, we have got stuff to do.").also { stage = END_DIALOGUE }

            23 -> playerl(FacialExpression.FRIENDLY, "Are you quite finished?").also { stage++ }
            24 -> npcl(FacialExpression.CHILD_NORMAL, "Stampy stampy stampy stampy stampy stampy, I've got big hands.").also { stage++ }
            25 -> playerl(FacialExpression.FRIENDLY, "Done yet?").also { stage++ }
            26 -> npcl(FacialExpression.CHILD_NORMAL, "Stampy stampy stampy stampy stampy stampy, I've got big chest.").also { stage++ }
            27 -> playerl(FacialExpression.FRIENDLY, "Done yet?").also { stage++ }
            28 -> npcl(FacialExpression.CHILD_NORMAL, "Stampy stampy stampy stampy stampy stampy, I've got big hair.").also { stage++ }
            29 -> playerl(FacialExpression.FRIENDLY, "Oh, be quiet and come on.").also { stage++ }
            30 -> npcl(FacialExpression.CHILD_NORMAL, "...").also { stage = END_DIALOGUE }

            31 -> playerl(FacialExpression.FRIENDLY, "Let's just wait and see.").also { stage++ }
            32 -> npcl(FacialExpression.CHILD_NORMAL, "I want to do some squishing of tiny things!").also { stage++ }
            33 -> playerl(FacialExpression.FRIENDLY, "Preferably not me.").also { stage++ }
            34 -> npcl(FacialExpression.CHILD_NORMAL, "Even if only a little bit, like your foot or something?").also { stage++ }
            35 -> playerl(FacialExpression.FRIENDLY, "Um, no. I really don't fancy being squished today, thanks.").also { stage++ }
            36 -> npcl(FacialExpression.CHILD_NORMAL, "Awww...").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MOSS_TITAN_7357, NPCs.MOSS_TITAN_7358)
    }

}
