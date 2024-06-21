package content.global.skill.combat.summoning.familiar.dialogue.titan

import core.api.consts.Items
import core.api.consts.NPCs
import core.api.inInventory
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.utilities.END_DIALOGUE

@Initializable
class IronTitanDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (inInventory(player!!, Items.IRON_BAR_2351)) {
            npcl(FacialExpression.CHILD_NORMAL, "Are you using that iron bar, boss?").also { stage++ }
            return true
        }
        when ((0..3).random()) {
            0 -> playerl(FacialExpression.HALF_ASKING, "Titan?").also { stage = 7 }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "Boss!").also { stage = 14 }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "Boss?").also { stage = 21 }
            3 -> playerl(FacialExpression.HALF_ASKING, "How are you today, titan?").also { stage = 27 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.HALF_ASKING, "Well, not right now, why?").also { stage++ }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "Can I have it, then?").also { stage++ }
            2 -> playerl(FacialExpression.HALF_ASKING, "What for?").also { stage++ }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "I've got a cunning plan.").also { stage++ }
            4 -> playerl(FacialExpression.HALF_ASKING, "Involving my iron bar?").also { stage++ }
            5 -> npcl(FacialExpression.CHILD_NORMAL, "No, but if I sell your iron bar I'll have enough money to buy a new hat.").also { stage++ }
            6 -> npcl(FacialExpression.CHILD_NORMAL, "You can't go through with a cunning plan without the right headgear, boss!").also { stage = END_DIALOGUE }

            7 -> npcl(FacialExpression.CHILD_NORMAL, "Yes, boss?").also { stage++ }
            8 -> playerl(FacialExpression.HALF_ASKING, "What's that in your hand?").also { stage++ }
            9 -> npcl(FacialExpression.CHILD_NORMAL, "I'm glad you asked that, boss.").also { stage++ }
            10 -> npcl(FacialExpression.CHILD_NORMAL, "This is the prototype for the Iron Titan (tm) action figure. You just pull this string here and he fights crime with real action sounds.").also { stage++ }
            11 -> playerl(FacialExpression.ASKING, "Titan?").also { stage++ }
            12 -> npcl(FacialExpression.CHILD_NORMAL, "Yes, boss?").also { stage++ }
            13 -> playerl(FacialExpression.STRUGGLE, "Never mind.").also { stage = END_DIALOGUE }

            14 -> playerl(FacialExpression.HALF_ASKING, "What?").also { stage++ }
            15 -> npcl(FacialExpression.CHILD_NORMAL, "I've just had a vision of the future.").also { stage++ }
            16 -> playerl(FacialExpression.FRIENDLY, "I didn't know you were a fortune teller. Let's hear it then.").also { stage++ }
            17 -> npcl(FacialExpression.CHILD_NORMAL, "Just imagine, boss, an Iron Titan (tm) on every desk.").also { stage++ }
            18 -> playerl(FacialExpression.FRIENDLY, "That doesn't even make sense.").also { stage++ }
            19 -> npcl(FacialExpression.CHILD_NORMAL, "Hmm. It was a bit blurry, perhaps the future is having technical issues at the moment.").also { stage++ }
            20 -> playerl(FacialExpression.FRIENDLY, "Riiight.").also { stage = END_DIALOGUE }

            21 -> playerl(FacialExpression.HALF_ASKING, "Yes, titan?").also { stage++ }
            22 -> npcl(FacialExpression.CHILD_NORMAL, "You know how you're the boss and I'm the titan?").also { stage++ }
            23 -> playerl(FacialExpression.HALF_ASKING, "Yes?").also { stage++ }
            24 -> npcl(FacialExpression.CHILD_NORMAL, "Do you think we could swap for a bit?").also { stage++ }
            25 -> playerl(FacialExpression.FRIENDLY, "No, titan!").also { stage++ }
            26 -> npcl(FacialExpression.CHILD_NORMAL, "Aww...").also { stage = END_DIALOGUE }

            27 -> npcl(FacialExpression.CHILD_NORMAL, "I'm very happy.").also { stage++ }
            28 -> playerl(FacialExpression.HALF_ASKING, "That's marvellous, why are you so happy?").also { stage++ }
            29 -> npcl(FacialExpression.CHILD_NORMAL, "Because I love the great taste of Iron Titan (tm) cereal!").also { stage++ }
            30 -> playerl(FacialExpression.ASKING, "?").also { stage++ }
            31 -> playerl(FacialExpression.ASKING, "You're supposed to be working for me, not promoting yourself.").also { stage++ }
            32 -> npcl(FacialExpression.CHILD_NORMAL, "Sorry, boss.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.IRON_TITAN_7375, NPCs.IRON_TITAN_7376)
    }
}
