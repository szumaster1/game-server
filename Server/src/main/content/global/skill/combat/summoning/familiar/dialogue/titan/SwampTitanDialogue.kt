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
class SwampTitanDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (inInventory(player!!, Items.SWAMP_TAR_1939)) {
            npcl(FacialExpression.CHILD_NORMAL, "Do you smell that? Swamp tar, master. I LOVE the smell of swamp tar in the morning. Smells like...victorin.").also { stage = 0 }
            return true
        }
        when ((0..3).random()) {
            0 -> npcl(FacialExpression.CHILD_NORMAL, "I'm alone, all alone I say.").also { stage = 4 }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "Oh, Guthix! I'm so alone, I have no fr").also { stage = 11 }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "Are you my friend, master?").also { stage = 23 }
            3 -> playerl(FacialExpression.FRIENDLY, "Cheer up, it might never happen!").also { stage = 27 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "You actually LIKE the smell of this stuff? It's gross.").also { stage++ }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "Of course! I am made of swamp, after all.").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "Oh, I'm sorry. I didn't mean...I meant the swamp tar itself smells gross, not you. You smell like lavender. Yes, lavender.").also { stage++ }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "*sob* Lavender? Lavender! Why would you be so mean? I'm supposed to smell bad.").also { stage = END_DIALOGUE }

            4 -> playerl(FacialExpression.FRIENDLY, "Oh, stop being so melodramatic.").also { stage++ }
            5 -> npcl(FacialExpression.CHILD_NORMAL, "It's not easy being greenery...well, decomposing greenery.").also { stage++ }
            6 -> playerl(FacialExpression.HALF_ASKING, "Surely, you're not the only swamp...thing in the world? What about the other swamp titans?").also { stage++ }
            7 -> npcl(FacialExpression.CHILD_NORMAL, "They're not my friends...they pick on me...they're so mean...").also { stage++ }
            8 -> playerl(FacialExpression.ASKING, "Why would they do that?").also { stage++ }
            9 -> npcl(FacialExpression.CHILD_NORMAL, "They think I DON'T smell.").also { stage++ }
            10-> playerl(FacialExpression.FRIENDLY, "Oh, yes. That is, er, mean...").also { stage = END_DIALOGUE }

            11-> playerl(FacialExpression.FRIENDLY, "Oh, not again. Look, I'll be your friend.").also { stage++ }
            12-> npcl(FacialExpression.CHILD_NORMAL, "You'll be my friend, master?").also { stage++ }
            13-> playerl(FacialExpression.FRIENDLY, "Yeah, sure, why not.").also { stage++ }
            14-> npcl(FacialExpression.CHILD_NORMAL, "Really?").also { stage++ }
            15-> playerl(FacialExpression.FRIENDLY, "Really really...").also { stage++ }
            16-> npcl(FacialExpression.CHILD_NORMAL, "Oh, I'm so happy!").also { stage++ }
            17-> playerl(FacialExpression.FRIENDLY, "...even if you do smell like a bog of eternal stench.").also { stage++ }
            18-> npcl(FacialExpression.CHILD_NORMAL, "Wait...you think I smell bad?").also { stage++ }
            19-> playerl(FacialExpression.FRIENDLY, "Erm, yes, but I didn't me").also { stage++ }
            20 -> npcl(FacialExpression.CHILD_NORMAL, "Oh, that's the nicest thing anyone's ever said to me! Thank you, master, thank you so much.").also { stage++ }
            21 -> npcl(FacialExpression.CHILD_NORMAL, "You're my friend AND you think I smell. I'm so very happy!").also { stage++ }
            22 -> playerl(FacialExpression.FRIENDLY, "I guess I did mean it like that.").also { stage = END_DIALOGUE }

            23 -> playerl(FacialExpression.ASKING, "Of course I am. I summoned you, didn't I?").also { stage++ }
            24 -> npcl(FacialExpression.CHILD_NORMAL, "Yes, but that was just to do some fighting. When you're done with me you'll send me back.").also { stage++ }
            25 -> playerl(FacialExpression.FRIENDLY, "I'm sure I'll need you again later.").also { stage++ }
            26 -> npcl(FacialExpression.CHILD_NORMAL, "Please don't send me back.").also { stage = END_DIALOGUE }

            27 -> npcl(FacialExpression.CHILD_NORMAL, "Oh, why did you have to go and say something like that?").also { stage++ }
            28 -> playerl(FacialExpression.FRIENDLY, "Like what? I'm trying to cheer you up.").also { stage++ }
            29 -> npcl(FacialExpression.CHILD_NORMAL, "There's no hope for me, oh woe, oh woe.").also { stage++ }
            30 -> playerl(FacialExpression.FRIENDLY, "I'll leave you alone, then.").also { stage++ }
            31 -> npcl(FacialExpression.CHILD_NORMAL, "NO! Don't leave me, master!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SWAMP_TITAN_7329, NPCs.SWAMP_TITAN_7330)
    }
}
