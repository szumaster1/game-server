package content.global.skill.combat.summoning.familiar.dialogue

import core.api.anyInEquipment
import core.api.anyInInventory
import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class ThornySnailDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (anyInInventory(player, *SNELM) || anyInEquipment(player, *SNELM)) {
            npcl(FacialExpression.OLD_NORMAL, "...").also { stage = 0 }
        } else {
            when ((0..3).random()) {
                0 -> npcl(FacialExpression.OLD_NORMAL, "All this running around the place is fun!").also { stage = 7 }
                1 -> npcl(FacialExpression.CHILD_NORMAL, "I think my stomach is drying out...").also { stage = 12 }
                2 -> npcl(FacialExpression.CHILD_NORMAL, "Okay, I have to ask, what are those things you people totter about on?").also { stage = 15 }
                3 -> npcl(FacialExpression.OLD_NORMAL, "Can you slow down?").also { stage = 20 }
            }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.CHILD_NORMAL, "...").also { stage++ }
            1 -> playerl(FacialExpression.FRIENDLY, "What's the matter?").also { stage++ }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "Check your head...").also { stage++ }
            3 -> playerl(FacialExpression.FRIENDLY, "What about it... Oh, wait! Oh, this is pretty awkward...").also { stage++ }
            4 -> npcl(FacialExpression.CHILD_NORMAL, "You're wearing the spine of one of my relatives as a hat...").also { stage++ }
            5 -> playerl(FacialExpression.FRIENDLY, "Well more of a faux-pas, then.").also { stage++ }
            6 -> npcl(FacialExpression.CHILD_NORMAL, "Just a bit...").also { stage = END_DIALOGUE }

            7 -> playerl(FacialExpression.FRIENDLY, "I'll be it's a step up from your usually sedentary lifestyle!").also { stage++ }
            8 -> npcl(FacialExpression.OLD_NORMAL, "True, but it's mostly seeing the sort of sights you don't get at home.").also { stage++ }
            9 -> playerl(FacialExpression.FRIENDLY, "Such as?").also { stage++ }
            10 -> npcl(FacialExpression.OLD_NORMAL, "Living things for a start.").also { stage++ }
            11 -> playerl(FacialExpression.FRIENDLY, "Those are in short supply in Mort Myre, I admit.").also { stage = END_DIALOGUE }

            12 -> playerl(FacialExpression.FRIENDLY, "Your stomach? How do you know how it's feeling?").also { stage++ }
            13 -> npcl(FacialExpression.OLD_NORMAL, "I am walking on it, you know...").also { stage++ }
            14 -> playerl(FacialExpression.FRIENDLY, "Urrgh...").also { stage = END_DIALOGUE }

            15 -> playerl(FacialExpression.HALF_ASKING, "You mean my legs?").also { stage++ }
            16 -> npcl(FacialExpression.OLD_NORMAL, "Yes, those. How are you supposed to eat anything through them?").also { stage++ }
            17 -> playerl(FacialExpression.FRIENDLY, "Well, we don't. That's what our mouths are for.").also { stage++ }
            18 -> npcl(FacialExpression.OLD_NORMAL, "Oh, right! I thought those were for expelling waste gas and hot air!").also { stage++ }
            19 -> playerl(FacialExpression.HALF_ASKING, "Well, for a lot of people they are.").also { stage = END_DIALOGUE }

            20 -> playerl(FacialExpression.FRIENDLY, "Are we going too fast for you?").also { stage++ }
            21 -> npcl(FacialExpression.OLD_NORMAL, "I bet if you had to run on your internal organs you'd want a break now and then!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.THORNY_SNAIL_6806, NPCs.THORNY_SNAIL_6807)
    }

    companion object {
        val SNELM = intArrayOf(3345, 3327,3355, 3337,3349, 3341,3341, 3359, 3347, 3329,3357, 3339,3351, 3333,3361, 3343,3353, 3335)
    }
}
