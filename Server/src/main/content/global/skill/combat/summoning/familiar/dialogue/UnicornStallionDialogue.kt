package content.global.skill.combat.summoning.familiar.dialogue

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Unicorn stallion dialogue.
 */
@Initializable
class UnicornStallionDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when ((0..4).random()) {
            0 -> npc(FacialExpression.CHILD_NORMAL, "Neigh neigh neighneigh snort?","(Isn't everything so awesomely wonderful?)").also { stage = 0 }
            1 -> npc(FacialExpression.CHILD_NORMAL, "Whicker whicker. Neigh, neigh, whinny.","(I feel so, like, enlightened. Let's meditate and enhance our auras.)").also { stage = 5 }
            2 -> npc(FacialExpression.CHILD_NORMAL, "Whinny whinny whinny.","(I think I'm astrally projecting.)").also { stage = 7 }
            3 -> npc(FacialExpression.CHILD_NORMAL, "Whinny, neigh!","(Oh, happy day!)").also { stage = 9 }
            4 -> npc(FacialExpression.CHILD_NORMAL, "Whicker snort! Whinny whinny whinny.","(You're hurt! Let me try to heal you.)").also { stage = 11 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when(stage) {
            0 -> npc(FacialExpression.CHILD_NORMAL, "Neigh neigh neighneigh snort?","(Isn't everything so awesomely wonderful?)").also { stage++ }
            1 -> playerl(FacialExpression.ASKING, "Err...yes?").also { stage++ }
            2 -> npc(FacialExpression.CHILD_NORMAL, "Whicker whicker snuffle.","(I can see you're not tuning in, Player name.)").also { stage++ }
            3 -> playerl(FacialExpression.FRIENDLY, "No, no, I'm completely at one with...you know...everything.").also { stage++ }
            4 -> npc(FacialExpression.CHILD_NORMAL, "Whicker!","(Cosmic.)").also { stage = END_DIALOGUE }

            5 -> playerl(FacialExpression.FRIENDLY, "I can't do that! I barely even know you.").also { stage++ }
            6 -> npc(FacialExpression.CHILD_NORMAL, "Whicker...","(Bipeds...)").also { stage = END_DIALOGUE }

            7 -> playerl(FacialExpression.HALF_ASKING, "Okay... Hang on. Seeing as I summoned you here, wouldn't that mean you are physically projecting instead?").also { stage++ }
            8 -> npc(FacialExpression.CHILD_NORMAL, "Whicker whicker whicker.","(You're, like, no fun at all, man.)").also { stage = END_DIALOGUE }

            9 -> playerl(FacialExpression.HALF_ASKING, "Happy day? Is that some sort of holiday or something?").also { stage++ }
            10 -> npc(FacialExpression.CHILD_NORMAL, "Snuggle whicker","(Man, you're totally, like, uncosmic, ${player!!.username}.)").also { stage = END_DIALOGUE }

            11 -> playerl(FacialExpression.FRIENDLY, "Yes, please do!").also { stage++ }
            12 -> npc(FacialExpression.CHILD_NORMAL, "Snuffle whicker whicker neigh neigh...","(Okay, we'll begin with acupuncture and some reiki, then I'll get my crystals...)").also { stage++ }
            13 -> playerl(FacialExpression.FRIENDLY, "Or you could use some sort of magic...like the other unicorns...").also { stage++ }
            14 -> npc(FacialExpression.CHILD_NORMAL, "Whicker whinny whinny neigh.","(Yes, but I believe in alternative medicine.)").also { stage++ }
            15 -> playerl(FacialExpression.FRIENDLY, "Riiight. Don't worry about it, then; I'll be fine.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.UNICORN_STALLION_6822, NPCs.UNICORN_STALLION_6823)
    }

}
