package content.global.skill.combat.summoning.familiar.dialogue.void

import core.api.anyInInventory
import core.api.consts.Items
import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class VoidSpinnerDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (anyInInventory(player!!, Items.PURPLE_SWEETS_4561, Items.PURPLE_SWEETS_10476)) {
            npcl(FacialExpression.CHILD_NORMAL, "You have sweeties for spinner?").also { stage = 0 }
            return true
        }
        when ((0..3).random()) {
            0 -> npcl(FacialExpression.CHILD_NORMAL, "Let's go play hide an' seek!").also { stage = 6 }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "My mummy told me I was clever.").also { stage = 9 }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "I'm coming to tickle you!").also { stage = 13 }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "Where's the sweeties?").also { stage = 16 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "Sweeties? No sweeties here.").also { stage++ }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "You do! You do! Gimmie sweeties!").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "I don't have any sweeties!").also { stage++ }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "What you hiding in your backpack, then?").also { stage++ }
            4 -> playerl(FacialExpression.FRIENDLY, "That? Oh, that's...erm...worms! Yes, worms. Purple worms.").also { stage++ }
            5 -> npcl(FacialExpression.CHILD_NORMAL, "Yucky!").also { stage = END_DIALOGUE }

            6 -> playerl(FacialExpression.FRIENDLY, "Okay, you hide and I'll come find you.").also { stage++ }
            7 -> npcl(FacialExpression.CHILD_NORMAL, "You'll never find me!").also { stage++ }
            8 -> playerl(FacialExpression.FRIENDLY, "What a disaster that would be...").also { stage = END_DIALOGUE }

            9 -> playerl(FacialExpression.HALF_ASKING, "Aren't you meant to be the essence of a spinner? How do you have a mother?").also { stage++ }
            10-> npcl(FacialExpression.CHILD_NORMAL, "What you mean, 'essence'?").also { stage++ }
            11 -> playerl(FacialExpression.FRIENDLY, "Never mind, I don't think it matters.").also { stage++ }
            12 -> npcl(FacialExpression.CHILD_NORMAL, "My logimical powers has proved me smarterer than you!").also { stage = END_DIALOGUE }

            13 -> playerl(FacialExpression.FRIENDLY, "No! You've got so many tentacles!").also { stage++ }
            14 -> npcl(FacialExpression.CHILD_NORMAL, "I'm coming to tickle you!").also { stage++ }
            15 -> playerl(FacialExpression.FRIENDLY, "Aieee!").also { stage = END_DIALOGUE }

            16 -> playerl(FacialExpression.FRIENDLY, "They are wherever good spinners go.").also { stage++ }
            17 -> npcl(FacialExpression.CHILD_NORMAL, "Yay for me!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.VOID_SPINNER_7333, NPCs.VOID_SPINNER_7334)
    }

}
