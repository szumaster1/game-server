package content.global.skill.summoning.familiar.dialogue

import core.api.anyInEquipment
import org.rs.consts.Items
import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Desert wyrm dialogue.
 */
@Initializable
class DesertWyrmDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (anyInEquipment(player!!, *PICKAXE)) {
            npcl(FacialExpression.CHILD_NORMAL, "If you have that pick, why make me dig?").also { stage = 0 }
            return true
        }
        when ((0..3).random()) {
            0 -> npcl(FacialExpression.CHILD_NORMAL, "This is so unsafe...I should have a hard hat for this work...").also { stage = 6 }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "You can't touch me, I'm part of the union!").also { stage = 8 }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "You know, you might want to register with the union.").also { stage = 10 }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "Why are you ignoring that good ore seam, ${player!!.username}?").also { stage = 12 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "Because it's a little quicker and easier on my arms.").also { stage++ }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "I should take industrial action over this...").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "You mean you won't work for me any more?").also { stage++ }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "No. It means me and the lads feed you legs-first into some industrial machinery, maybe the Blast Furnace.").also { stage++ }
            4 -> playerl(FacialExpression.FRIENDLY, "I'll just be over here, digging.").also { stage++ }
            5 -> npcl(FacialExpression.CHILD_NORMAL, "That's the spirit, lad!").also { stage = END_DIALOGUE }

            6 -> playerl(FacialExpression.FRIENDLY, "Well, I could get you a rune helm if you like - those are pretty hard.").also { stage++ }
            7 -> npcl(FacialExpression.CHILD_NORMAL, "Keep that up and you'll have the union on your back, ${player!!.username}.").also { stage = END_DIALOGUE }

            8 -> playerl(FacialExpression.HALF_ASKING, "Is that some official no touching policy or something?").also { stage++ }
            9 -> npcl(FacialExpression.CHILD_NORMAL, "You really don't get it, do you ${player!!.username}?").also { stage = END_DIALOGUE }

            10 -> npcl(FacialExpression.CHILD_NORMAL, "I stop bugging you to join the union.").also { stage++ }
            11 -> playerl(FacialExpression.FRIENDLY, "Ask that again later; I'll have to consider that generous proposal.").also { stage = END_DIALOGUE }

            12 -> playerl(FacialExpression.HALF_ASKING, "Which ore seam?").also { stage++ }
            13 -> npcl(FacialExpression.CHILD_NORMAL, "There's a good ore seam right underneath us at this very moment.").also { stage++ }
            14 -> playerl(FacialExpression.HALF_ASKING, "Great! How long will it take for you to get to it?").also { stage++ }
            15-> npcl(FacialExpression.CHILD_NORMAL, "Five years, give or take.").also { stage++ }
            16 -> playerl(FacialExpression.FRIENDLY, "Five years!").also { stage++ }
            17 -> npcl(FacialExpression.CHILD_NORMAL, "That's if we go opencast, mind. I could probably reach it in three if I just dug.").also { stage++ }
            18 -> playerl(FacialExpression.FRIENDLY, "Right. I see. I think I'll skip it thanks.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.DESERT_WYRM_6831, NPCs.DESERT_WYRM_6832)
    }

    companion object {
        val PICKAXE = intArrayOf(Items.BRONZE_PICKAXE_1265, Items.IRON_PICKAXE_1267, Items.STEEL_PICKAXE_1269, Items.MITHRIL_PICKAXE_1273, Items.ADAMANT_PICKAXE_1271, Items.RUNE_PICKAXE_1275, Items.INFERNO_ADZE_13661)
    }

}
