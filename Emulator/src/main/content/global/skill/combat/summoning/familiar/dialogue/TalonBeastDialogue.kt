package content.global.skill.combat.summoning.familiar.dialogue

import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Talon beast dialogue.
 */
@Initializable
class TalonBeastDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when ((0..3).random()) {
            0 -> npcl(FacialExpression.CHILD_NORMAL, "Is this all you apes do all day, then?").also { stage = 0 }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "This place smells odd...").also { stage = 4 }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "Hey!").also { stage = 7 }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "C'mon! Lets go fight stuff!").also { stage = 11 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "Well, we do a lot of other things, too.").also { stage++ }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "That's dull. Lets go find something and bite it.").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "I wouldn't want to spoil my dinner.").also { stage++ }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "So, I have to watch you trudge about again? Talk about boring.").also { stage = END_DIALOGUE }

            4 -> playerl(FacialExpression.HALF_ASKING, "Odd?").also { stage++ }
            5 -> npcl(FacialExpression.CHILD_NORMAL, "Yes, not enough is rotting...").also { stage++ }
            6 -> playerl(FacialExpression.FRIENDLY, "For which I am extremely grateful.").also { stage = END_DIALOGUE }

            7 -> playerl(FacialExpression.FRIENDLY, "Aaaargh!").also { stage++ }
            8 -> npcl(FacialExpression.CHILD_NORMAL, "Why d'you always do that?").also { stage++ }
            9 -> playerl(FacialExpression.FRIENDLY, "I don't think I'll ever get used to having a huge, ravenous feline sneaking around behind me all the time.").also { stage++ }
            10 -> npcl(FacialExpression.CHILD_NORMAL, "That's okay, I doubt I'll get used to following an edible, furless monkey prancing in front of me all the time either.").also { stage = END_DIALOGUE }

            11 -> playerl(FacialExpression.ASKING, "What sort of stuff?").also { stage++ }
            12 -> npcl(FacialExpression.CHILD_NORMAL, "I dunno? Giants, monsters, vaguely-defined philosophical concepts. You know: stuff.").also { stage++ }
            13 -> playerl(FacialExpression.ASKING, "How are we supposed to fight a philosophical concept?").also { stage++ }
            14 -> npcl(FacialExpression.CHILD_NORMAL, "With subtle arguments and pointy sticks!").also { stage++ }
            15 -> playerl(FacialExpression.FRIENDLY, "Well, I can see you're going to go far in debates.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.TALON_BEAST_7347, NPCs.TALON_BEAST_7348)
    }

}
