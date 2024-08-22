package content.global.skill.combat.summoning.familiar.dialogue.spirit

import cfg.consts.Items
import cfg.consts.NPCs
import core.api.hasAnItem
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Spirit kalphite dialogue.
 */
@Initializable
class SpiritKalphiteDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        var hasKeris = hasAnItem(player!!, *KERIS).container == player.inventory
        if (hasKeris) {
            playerl(FacialExpression.ASKING, "How dare I what?").also { stage = 0 }
            return true
        }
        when ((0..3).random()) {
            0 -> npcl(FacialExpression.CHILD_NORMAL, "This activity is not optimal for us.").also { stage = 4 }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "We are growing infuriated. What is our goal?").also { stage = 6 }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "We find this to be wasteful of our time.").also { stage = 9 }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "We grow tired of your antics, biped.").also { stage = 11 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.CHILD_NORMAL, "That weapon offends us!").also { stage++ }
            1 -> playerl(FacialExpression.HALF_ASKING, "What weapon?").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "Oh...").also { stage++ }
            3 -> playerl(FacialExpression.FRIENDLY, "Awkward.").also { stage = END_DIALOGUE }

            4 -> playerl(FacialExpression.FRIENDLY, "Well, you'll just have to put up with it for now.").also { stage++ }
            5 -> npcl(FacialExpression.CHILD_NORMAL, "We would not have to 'put up' with this in the hive.").also { stage = END_DIALOGUE }

            6 -> playerl(FacialExpression.FRIENDLY, "Well, I haven't quite decided yet.").also { stage++ }
            7 -> npcl(FacialExpression.CHILD_NORMAL, "There is no indecision in the hive.").also { stage++ }
            8 -> playerl(FacialExpression.FRIENDLY, "Or a sense of humour or patience, it seems.").also { stage = END_DIALOGUE }

            9 -> playerl(FacialExpression.FRIENDLY, "Maybe I find you wasteful...").also { stage++ }
            10 -> npcl(FacialExpression.CHILD_NORMAL, "We would not face this form of abuse in the hive.").also { stage = END_DIALOGUE }

            11 -> playerl(FacialExpression.FRIENDLY, "What antics? I'm just getting on with my day.").also { stage++ }
            12 -> npcl(FacialExpression.CHILD_NORMAL, "In an inefficient way. In the hive, you would be replaced.").also { stage++ }
            13 -> playerl(FacialExpression.FRIENDLY, "In the hive this, in the hive that...").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SPIRIT_KALPHITE_6994, NPCs.SPIRIT_KALPHITE_6995)
    }

    companion object {
        private val KERIS = intArrayOf(Items.KERIS_10581, Items.KERISP_10582, Items.KERISP_PLUS_10583, Items.KERISP_PLUS_PLUS_10584)
    }

}
