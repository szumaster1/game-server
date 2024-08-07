package content.global.skill.combat.summoning.familiar.dialogue

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Ibis dialogue.
 */
@Initializable
class IbisDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when ((0..4).random()) {
            0 -> npcl(FacialExpression.OLD_DEFAULT, "I'm the best fisherman ever!").also { stage = 0 }
            1 -> npcl(FacialExpression.OLD_DEFAULT, "I like to fish!").also { stage = 3 }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "I want to go fiiiish.").also { stage = 4 }
            3 -> npcl(FacialExpression.OLD_DEFAULT, "Hey, where are we?").also { stage = 5 }
            4 -> npcl(FacialExpression.OLD_DEFAULT, "Can I look after those sharks for you?").also { stage = 8 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.HALF_ASKING, "Where is your skillcape to prove it, then?").also { stage++ }
            1 -> npcl(FacialExpression.OLD_DEFAULT, "At home...").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "I'll bet it is.").also { stage = END_DIALOGUE }

            3 -> playerl(FacialExpression.HAPPY, "I know!").also { stage = END_DIALOGUE }

            4 -> playerl(FacialExpression.FRIENDLY, "We can't be fishing all the time you know.").also { stage = END_DIALOGUE }

            5 -> playerl(FacialExpression.HALF_ASKING, "What do you mean?").also { stage++ }
            6 -> npcl(FacialExpression.OLD_DEFAULT, "I just noticed we weren't fishing.").also { stage++ }
            7 -> playerl(FacialExpression.FRIENDLY, "Well, we can't fish all the time.").also { stage = END_DIALOGUE }

            8 -> playerl(FacialExpression.HALF_ASKING, "I don't know. Would you eat them?").also { stage++ }
            9 -> npcl(FacialExpression.OLD_DEFAULT, "Yes! Ooops...").also { stage++ }
            10 -> playerl(FacialExpression.FRIENDLY, "I think I'll hang onto them myself for now.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.IBIS_6991)
    }

}
