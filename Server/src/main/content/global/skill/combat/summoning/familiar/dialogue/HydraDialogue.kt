package content.global.skill.combat.summoning.familiar.dialogue

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.utilities.END_DIALOGUE

@Initializable
class HydraDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when ((0..3).random()) {
            0 -> npcl(FacialExpression.CHILD_NORMAL, "Raaaspraaasp? (Isn't it hard to get things done with just one head?)").also { stage = 0 }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "Raaaasp raaaasp! (Man, I feel good!)").also { stage = 4 }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "Rassssp rasssssp! (You know, two heads are better than one!)").also { stage = 10 }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "Raaaaaaasp. (Siiiigh.)").also { stage = 12 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "Not really!").also { stage++ }
            1 -> npc(FacialExpression.CHILD_NORMAL, "Raaasp raaaaap raaaasp?","(Well I suppose you work with what you got, right?").also { stage++ }
            2 -> npc(FacialExpression.CHILD_NORMAL, "Raaaaaasp raaaasp raaaasp.","(At least he doesn't have someone whittering in their ear all the time.)").also { stage++ }
            3 -> npc(FacialExpression.CHILD_NORMAL, "Raaaaaaasp!","(Quiet, you!)").also { stage = END_DIALOGUE }

            4 -> npc(FacialExpression.CHILD_NORMAL, "Raaasp ssssss raaaasp.","(That's easy for you to say.)").also { stage++ }
            5 -> playerl(FacialExpression.HALF_ASKING, "What's up?").also { stage++ }
            6 -> npc(FacialExpression.CHILD_NORMAL, "Raaa....","(well...)").also { stage++ }
            7 -> npc(FacialExpression.CHILD_NORMAL, "Raaaaasp sss rassssp.","(Don't pay any attention, they are just feeling whiny.)").also { stage++ }
            8 -> playerl(FacialExpression.HALF_ASKING, "But they're you, aren't they?").also { stage++ }
            9 -> npc(FacialExpression.CHILD_NORMAL, "Raaaasp raasp rasssp!","(Don't remind me!)").also { stage = END_DIALOGUE }

            10 -> npc(FacialExpression.CHILD_NORMAL, "Raaaasp rassssp sssssp....","(Unless you're the one doing all the heavy thinking....)").also { stage++ }
            11 -> playerl(FacialExpression.FRIENDLY, "I think I'll stick to one for now, thanks.").also { stage = END_DIALOGUE }

            12 -> npc(FacialExpression.CHILD_NORMAL, "Raasp raasp raaaaasp?","(What's up this time?)").also { stage++ }
            13 -> playerl(FacialExpression.HALF_ASKING, "Can I help?").also { stage++ }
            14 -> npc(FacialExpression.CHILD_NORMAL, "Rasssp ssssssp? raaaaasp raaaasp.","(Do you mind? This is a private conversation.)").also { stage++ }
            15 -> playerl(FacialExpression.FRIENDLY, "Well, excu-u-use me.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.HYDRA_6811, NPCs.HYDRA_6812)
    }

}
