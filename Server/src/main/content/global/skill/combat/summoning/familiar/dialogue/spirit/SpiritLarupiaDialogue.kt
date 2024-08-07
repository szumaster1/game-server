package content.global.skill.combat.summoning.familiar.dialogue.spirit

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Spirit larupia dialogue.
 */
@Initializable
class SpiritLarupiaDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when ((0..3).random()) {
            0 -> playerl(FacialExpression.FRIENDLY, "Kitty cat!").also { stage = 0 }
            1 -> playerl(FacialExpression.FRIENDLY, "Hello friend!").also { stage = 5 }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "What are we doing today, master?").also { stage = 11 }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "Master, do you ever worry that I might eat you?").also { stage = 14 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.CHILD_NORMAL, "What is your wish master?").also { stage++ }
            1 -> playerl(FacialExpression.FRIENDLY, "Have you ever thought about doing something other than hunting and serving me?").also { stage++ }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "You mean, like stand-up comedy, master?").also { stage++ }
            3 -> playerl(FacialExpression.FRIENDLY, "Umm...yes, like that.").also { stage++ }
            4 -> npcl(FacialExpression.CHILD_NORMAL, "No, master.").also { stage = END_DIALOGUE }

            5 -> npcl(FacialExpression.CHILD_NORMAL, "'Friend', master? I do not understand this word.").also { stage++ }
            6 -> playerl(FacialExpression.FRIENDLY, "Friends are people, or animals, who like one another. I think we are friends.").also { stage++ }
            7 -> npcl(FacialExpression.CHILD_NORMAL, "Ah, I think I understand friends, master.").also { stage++ }
            8 -> playerl(FacialExpression.FRIENDLY, "Great!").also { stage++ }
            9 -> npcl(FacialExpression.CHILD_NORMAL, "A friend is someone who looks tasty, but you don't eat.").also { stage++ }
            10 -> playerl(FacialExpression.FRIENDLY, "!").also { stage = END_DIALOGUE }

            11 -> playerl(FacialExpression.FRIENDLY, "I don't know, what do you want to do?").also { stage++ }
            12 -> npcl(FacialExpression.CHILD_NORMAL, "I desire only to hunt and to serve my master.").also { stage++ }
            13 -> playerl(FacialExpression.FRIENDLY, "Err...great! I guess I'll decide then.").also { stage = END_DIALOGUE }

            14 -> playerl(FacialExpression.FRIENDLY, "No, of course not! We're pals.").also { stage++ }
            15 -> npcl(FacialExpression.CHILD_NORMAL, "That is good, master.").also { stage++ }
            16 -> playerl(FacialExpression.FRIENDLY, "Should I?").also { stage++ }
            17 -> npcl(FacialExpression.CHILD_NORMAL, "Of course not, master.").also { stage++ }
            18 -> playerl(FacialExpression.FRIENDLY, "Oh. Good.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SPIRIT_LARUPIA_7337, NPCs.SPIRIT_LARUPIA_7338)
    }

}
