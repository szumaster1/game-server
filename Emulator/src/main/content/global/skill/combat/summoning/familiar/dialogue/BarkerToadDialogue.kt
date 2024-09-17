package content.global.skill.combat.summoning.familiar.dialogue

import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Barker toad dialogue.
 */
@Initializable
class BarkerToadDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when ((0..5).random()) {
            0 -> npcl(FacialExpression.CHILD_NORMAL, "Ladies and gentlemen, for my next trick, I shall swallow this fly!").also { stage = 0 }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "Roll up, roll up, roll up! See the greatest show on Gielinor!").also { stage = 5 }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "We need to set up the big top somewhere near here. The locals look friendly enough.").also { stage = 11 }
            3 -> npc(FacialExpression.CHILD_NORMAL, "Braaaaaaaaaaaaaaaaaaaaaaap!","(*Burp!*)").also { stage = 13 }
            4 -> npc(FacialExpression.CHILD_NORMAL, "Mumblemumblegrumblemumble...","(*Inaudible mumbles*)").also { stage = 18 }
            5 -> npc(FacialExpression.CHILD_NORMAL, "Bwaaarp graaaawk?","(What's that croaking in your inventory?)").also { stage = 19 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "Seen it.").also { stage++ }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "Ah, but last time was the frog...on fire?").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "No! That would be a good trick.").also { stage++ }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "Well, it won't be this time either.").also { stage++ }
            4 -> playerl(FacialExpression.FRIENDLY, "Awwwww...").also { stage = END_DIALOGUE }

            5 -> npcl(FacialExpression.CHILD_NORMAL, "Roll up, roll up, roll up! See the greatest show on Gielinor!").also { stage++ }
            6 -> playerl(FacialExpression.HALF_ASKING, "Where?").also { stage++ }
            7 -> npcl(FacialExpression.CHILD_NORMAL, "Well, it's kind of...you.").also { stage++ }
            8 -> playerl(FacialExpression.HALF_ASKING, "Me?").also { stage++ }
            9 -> npcl(FacialExpression.CHILD_NORMAL, "Roll up, roll up, roll up! See the greatest freakshow on Gielinor!").also { stage++ }
            10 -> playerl(FacialExpression.FRIENDLY, "Don't make me smack you, slimy.").also { stage = END_DIALOGUE }

            11 -> playerl(FacialExpression.HALF_ASKING, "Are you kidding?").also { stage++ }
            12 -> npcl(FacialExpression.CHILD_NORMAL, "Your problem is that you never see opportunities.").also { stage = END_DIALOGUE }

            13 -> playerl(FacialExpression.FRIENDLY, "That's disgusting behaviour!").also { stage++ }
            14 -> npc(FacialExpression.CHILD_NORMAL, "Braap craaaaawk craaaawk.","(That, my dear boy, was my world-renowned belching.)").also { stage++ }
            15 -> playerl(FacialExpression.HALF_ASKING, "I got that part. Why are you so happy about it?").also { stage++ }
            16 -> npc(FacialExpression.CHILD_NORMAL, "Braaaaaaap craaaaaawk craaaaaaaawk.","(My displays have bedazzled the crowned heads of Gielinor.)").also { stage++ }
            17 -> playerl(FacialExpression.FRIENDLY, "I'd give you a standing ovation, but I have my hands full.").also { stage = END_DIALOGUE }

            18 -> playerl(FacialExpression.LAUGH, "Well, that cannonball seems to have shut him up!").also { stage = END_DIALOGUE }

            19 -> playerl(FacialExpression.HALF_ASKING, "Ah, you mean that toad?").also { stage++ }
            20 -> playerl(FacialExpression.FRIENDLY, "Oh, I'm guessing you're not going to like me carrying a toad about.").also { stage++ }
            21 -> npcl(FacialExpression.CHILD_NORMAL, "Craaawk, croak. (I might not be all that happy, no.)").also { stage++ }
            22 -> playerl(FacialExpression.FRIENDLY, "I'm not going to eat it.").also { stage++ }
            23 -> npc(FacialExpression.CHILD_NORMAL, "Craaaaawk braaap croak.","(Weeeeell, I'd hope not! Reminds me of my mama toad.","She was inflated and fed to a jubbly, you know.","A sad, demeaning way to die.)").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BARKER_TOAD_6889, NPCs.BARKER_TOAD_6890)
    }

}
