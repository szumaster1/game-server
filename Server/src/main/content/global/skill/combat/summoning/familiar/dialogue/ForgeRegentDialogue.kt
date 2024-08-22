package content.global.skill.combat.summoning.familiar.dialogue

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Forge regent dialogue.
 */
@Initializable
class ForgeRegentDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when ((0..3).random()) {
            0 -> npc(FacialExpression.CHILD_NORMAL, "Crackley spit crack sizzle?","(Can we go Smithing?)").also { stage = 0 }
            1 -> npc(FacialExpression.CHILD_NORMAL, "Hiss.","(I'm happy.)").also { stage = 6 }
            2 -> npc(FacialExpression.CHILD_NORMAL, "Sizzle!","(I like logs.)").also { stage = 19 }
            3 -> npc(FacialExpression.CHILD_NORMAL, "Sizzle...","(I'm bored.)").also { stage = 22 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when(stage){
            0 -> playerl(FacialExpression.FRIENDLY, "Maybe.").also { stage++ }
            1 -> npc(FacialExpression.CHILD_NORMAL, "Hiss?","(Can we go smelt something?)").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "Maybe.").also { stage++ }
            3 -> npc(FacialExpression.CHILD_NORMAL, "Flicker crackle sizzle?","(Can we go mine something to smelt?)").also { stage++ }
            4 -> playerl(FacialExpression.FRIENDLY, "Maybe.").also { stage++ }
            5 -> npc(FacialExpression.CHILD_NORMAL, "Sizzle flicker!","(Yay! I like doing that!)").also { stage = END_DIALOGUE }

            6 -> playerl(FacialExpression.FRIENDLY, "Good.").also { stage++ }
            7 -> npc(FacialExpression.CHILD_NORMAL, "Crackle.","(Now I'm sad.)").also { stage++ }
            8 -> playerl(FacialExpression.HALF_ASKING, "Oh dear, why?").also { stage++ }
            9 -> npc(FacialExpression.CHILD_NORMAL, "Hiss-hiss.","(Happy again.)").also { stage++ }
            10 -> playerl(FacialExpression.FRIENDLY, "Glad to hear it.").also { stage++ }
            11 -> npc(FacialExpression.CHILD_NORMAL, "Crackley-crick.","(Sad now.)").also { stage++ }
            12 -> playerl(FacialExpression.FRIENDLY, "Um.").also { stage++ }
            13 -> npc(FacialExpression.CHILD_NORMAL, "Hiss.","(Happy.)").also { stage++ }
            14 -> playerl(FacialExpression.FRIENDLY, "Right...").also { stage++ }
            15 -> npc(FacialExpression.CHILD_NORMAL, "Crackle.","(Sad.)").also { stage++ }
            16 -> playerl(FacialExpression.FRIENDLY, "You're very strange.").also { stage++ }
            17 -> npc(FacialExpression.CHILD_NORMAL, "Sizzle hiss?","(What makes you say that?)").also { stage++ }
            18 -> playerl(FacialExpression.FRIENDLY, "Oh...nothing in particular.").also { stage = END_DIALOGUE }

            19 -> playerl(FacialExpression.FRIENDLY, "They are useful for making planks.").also { stage++ }
            20 -> npc(FacialExpression.CHILD_NORMAL, "Sizzley crack hiss spit.","(No, I just like walking on them. They burst into flames.)").also { stage++ }
            21 -> playerl(FacialExpression.FRIENDLY, "It's a good job I can use you as a firelighter really!").also { stage = END_DIALOGUE }

            22 -> playerl(FacialExpression.HALF_ASKING, "Are you not enjoying what we're doing?").also { stage++ }
            23 -> npc(FacialExpression.CHILD_NORMAL, "Crackley crickle sizzle.","(Oh yes, but I'm still bored.)").also { stage++ }
            24 -> playerl(FacialExpression.FRIENDLY, "Oh, I see.").also { stage++ }
            25 -> npc(FacialExpression.CHILD_NORMAL, "Sizzle hiss?","(What's that over there?)").also { stage++ }
            26 -> playerl(FacialExpression.HALF_ASKING, "I don't know. Should we go and look?").also { stage++ }
            27 -> npc(FacialExpression.CHILD_NORMAL, "Hiss crackle spit sizzle crack?","(Nah, that's old news - I'm bored of it now.)").also { stage++ }
            28 -> npc(FacialExpression.CHILD_NORMAL, "Crackle crickle spit hiss?","(Oooooh ooooh oooooh, what's that over there?)").also { stage++ }
            29 -> playerl(FacialExpression.HALF_ASKING, "But...wha...where now?").also { stage++ }
            30 -> npc(FacialExpression.CHILD_NORMAL, "Sizzle crack crickle.","(Oh no matter, it no longer interests me.)").also { stage++ }
            31 -> playerl(FacialExpression.FRIENDLY, "You're hard work.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.FORGE_REGENT_7335, NPCs.FORGE_REGENT_7336)
    }

}
