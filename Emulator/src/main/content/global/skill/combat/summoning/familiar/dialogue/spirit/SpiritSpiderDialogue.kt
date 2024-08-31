package content.global.skill.combat.summoning.familiar.dialogue.spirit

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Spirit spider dialogue.
 */
@Initializable
class SpiritSpiderDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when ((0..4).random()) {
            0 -> npcl(FacialExpression.CHILD_NORMAL, "Where are we going?").also { stage = 0 }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "Who is that?").also { stage = 5 }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "What are you doing?").also { stage = 12 }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "Sigh...").also { stage = 17 }
            4 -> npcl(FacialExpression.CHILD_NORMAL, "So, do I get any of those flies?").also { stage = 20 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "I've not decided yet.").also { stage++ }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "Fine, don't tell me...").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "Oh, okay, well, we are going...").also { stage++ }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "Don't want to know now.").also { stage++ }
            4 -> playerl(FacialExpression.FRIENDLY, "Siiiigh...spiders.").also { stage = END_DIALOGUE }

            5 -> playerl(FacialExpression.FRIENDLY, "Who?").also { stage++ }
            6 -> npcl(FacialExpression.CHILD_NORMAL, "The two-legs over there.").also { stage++ }
            7 -> playerl(FacialExpression.FRIENDLY, "I can't see who you mean...").also { stage++ }
            8 -> npcl(FacialExpression.CHILD_NORMAL, "Never mind...").also { stage++ }
            9 -> playerl(FacialExpression.FRIENDLY, "Can you describe them a little better...").also { stage++ }
            10 -> npcl(FacialExpression.CHILD_NORMAL, "It doesn't matter now.").also { stage = 7 }
            11 -> playerl(FacialExpression.FRIENDLY, "Siiiigh...spiders.").also { stage = END_DIALOGUE }

            12 -> playerl(FacialExpression.FRIENDLY, "Nothing that you should concern yourself with.").also { stage++ }
            13 -> npcl(FacialExpression.CHILD_NORMAL, "I see, you don't think I'm smart enough to understand...").also { stage++ }
            14 -> playerl(FacialExpression.FRIENDLY, "That's not it at all! Look, I was...").also { stage++ }
            15 -> npcl(FacialExpression.CHILD_NORMAL, "Don't wanna know now.").also { stage++ }
            16 -> playerl(FacialExpression.FRIENDLY, "Siiiigh...spiders.").also { stage = END_DIALOGUE }

            17 -> playerl(FacialExpression.FRIENDLY, "What is it now?").also { stage++ }
            18 -> npcl(FacialExpression.CHILD_NORMAL, "Nothing really.").also { stage++ }
            19 -> playerl(FacialExpression.FRIENDLY, "Oh, well that's a relief.").also { stage = END_DIALOGUE }

            20 -> playerl(FacialExpression.FRIENDLY, "I don't know, I was saving these for a pet.").also { stage++ }
            21 -> npcl(FacialExpression.CHILD_NORMAL, "I see...").also { stage++ }
            22 -> playerl(FacialExpression.FRIENDLY, "Look, you can have some if you want.").also { stage++ }
            23 -> npcl(FacialExpression.CHILD_NORMAL, "Oh, don't do me any favours.").also { stage++ }
            24 -> playerl(FacialExpression.FRIENDLY, "Look, here, have some!").also { stage++ }
            25 -> npcl(FacialExpression.CHILD_NORMAL, "Don't want them now.").also { stage = 7 }
            26 -> playerl(FacialExpression.FRIENDLY, "Siiiigh...spiders.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SPIRIT_SPIDER_6841, NPCs.SPIRIT_SPIDER_6842)
    }

}
