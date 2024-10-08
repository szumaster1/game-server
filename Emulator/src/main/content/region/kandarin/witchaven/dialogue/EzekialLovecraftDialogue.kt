package content.region.kandarin.witchaven.dialogue

import org.rs.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Ezekial lovecraft dialogue.
 */
@Initializable
class EzekialLovecraftDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npcl(FacialExpression.FRIENDLY, "Well hello there; welcome to our little village. Pray, stay awhile.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("What do you do here?", "Can I buy some fishing supplies please?", "You are a bit too strange for me. Bye.").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.ASKING, "What do you do here?").also { stage = 10 }
                2 -> player(FacialExpression.HALF_ASKING, "Can I buy some fishing supplies please?").also { stage = 25 }
                3 -> player(FacialExpression.ANNOYED, "You are a bit too strange for me. Bye.").also { stage = 30 }
            }
            10 -> npcl(FacialExpression.FRIENDLY, "I supply the local fishermen with the tools and bait they need to do their job.").also { stage++ }
            11 -> player(FacialExpression.FRIENDLY, "Interesting. Have you been doing it long?").also { stage++ }
            12 -> npcl(FacialExpression.FRIENDLY, "Why yes, all of my life. I took over from my father, who inherited the business from his father and so on. In fact, there have been Lovecraft's selling bait for over ten generations.").also { stage++ }
            13 -> player(FacialExpression.FRIENDLY, "Wow, that's some lineage.").also { stage++ }
            14 -> npcl(FacialExpression.LAUGH, "Oh yes, we have a long and interesting family history. For one reason or another the Lovecraft's have always been bait sellers or writers.").also { stage++ }
            15 -> playerl(FacialExpression.FRIENDLY, "Hmm, yes well. I'm sure it's all fascinating, but I...").also { stage++ }
            16 -> npcl(FacialExpression.FRIENDLY, "Oh very, very fascinating, for instance my great grandfather Howard...").also { stage++ }
            17 -> playerl(FacialExpression.HALF_ROLLING_EYES, "Yawn. Oh, I'm sorry but I really must be getting on. I think my giraffe needs feeding.").also { stage++ }
            18 -> npc(FacialExpression.HALF_ASKING, "Your?").also { stage++ }
            19 -> playerl(FacialExpression.ANNOYED, "Giraffe. Sorry, but he gets cranky without enough sugar. Bye now!").also { stage++ }
            20 -> npc(FacialExpression.FRIENDLY, "Oh right, goodbye.").also { stage = END_DIALOGUE }
            25 -> {
                end()
                openNpcShop(player, NPCs.EZEKIAL_LOVECRAFT_4856)
            }
            30 -> npc(FacialExpression.FRIENDLY, "Sniff. Yes, everyone says that.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.EZEKIAL_LOVECRAFT_4856)
    }
}
