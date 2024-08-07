package content.global.skill.combat.summoning.familiar.dialogue.void

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Void ravager dialogue.
 */
@Initializable
class VoidRavagerDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when ((0..3).random()) {
            0 -> npcl(FacialExpression.CHILD_NORMAL, "You look delicious!").also { stage = 0 }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "Take me to the rift!").also { stage = 1 }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "Pardon me. Could I trouble you for a moment?").also { stage = 4 }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "How do you bear life without ravaging?").also { stage = 12 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "Don't make me dismiss you!").also { stage = END_DIALOGUE }

            1 -> playerl(FacialExpression.FRIENDLY, "I'm not taking you there! Goodness knows what you'd get up to.").also { stage++ }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "I promise not to destroy your world...").also { stage++ }
            3 -> playerl(FacialExpression.FRIENDLY, "If only I could believe you...").also { stage = END_DIALOGUE }

            4 -> playerl(FacialExpression.FRIENDLY, "Yeah, sure.").also { stage++ }
            5 -> npcl(FacialExpression.CHILD_NORMAL, "Oh, it's just a trifling thing. Mmm, trifle...you look like trifle...So, will you help?").also { stage++ }
            6 -> playerl(FacialExpression.FRIENDLY, "Fire away!").also { stage++ }
            7 -> npcl(FacialExpression.CHILD_NORMAL, "Oh, just be honest. I just want a second opinion...Is this me? Mmm trifle...").also { stage++ }
            8 -> playerl(FacialExpression.FRIENDLY, "Huh?").also { stage++ }
            9 -> npcl(FacialExpression.CHILD_NORMAL, "Oh! The claws! The whiskers! The single, yellow eye! Oh! Is it me? Is it truly me?").also { stage++ }
            10 -> playerl(FacialExpression.FRIENDLY, "Erm...why yes...of course. It definitely reflects the inner you.").also { stage++ }
            11 -> npcl(FacialExpression.CHILD_NORMAL, "Oh, I knew it! You've been an absolute delight. An angel delight! And everyone said it was just a phase!").also { stage = END_DIALOGUE }

            12 -> playerl(FacialExpression.FRIENDLY, "It's not always easy.").also { stage++ }
            13 -> npcl(FacialExpression.CHILD_NORMAL, "I could show you how to ravage, if you like...").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.VOID_RAVAGER_7370, NPCs.VOID_RAVAGER_7371)
    }

}
