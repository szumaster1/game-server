package content.global.skill.combat.summoning.familiar.dialogue

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.utilities.END_DIALOGUE

@Initializable
class BullAntDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (player!!.settings.runEnergy < 50) {
            npcl(FacialExpression.CHILD_NORMAL, "What's the matter, Private? Not enjoying the run?").also { stage = 0 }
            return true
        }
        when ((0..3).random()) {
            0 -> npcl(FacialExpression.CHILD_NORMAL, "All right you worthless biped, fall in!").also { stage = 5 }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "Aten...hut!").also { stage = 9 }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "I can't believe they stuck me with you...").also { stage = 14 }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "What in the name of all the layers of the abyss do you think you're doing, biped?").also { stage = 17 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "Sir...wheeze...yes Sir!").also { stage++ }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "Not enjoying the run? You need more training biped?").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "Sir, no Sir! Sir, I'm enjoying the run a great deal, Sir!").also { stage++ }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "Then hop to, Private!").also { stage++ }
            4 -> playerl(FacialExpression.FRIENDLY, "Sir, yes Sir!").also { stage = END_DIALOGUE }

            5 -> playerl(FacialExpression.FRIENDLY, "Sir, yes Sir!").also { stage++ }
            6 -> npcl(FacialExpression.CHILD_NORMAL, "We're going to work you so hard your boots fall off, understood?").also { stage++ }
            7 -> playerl(FacialExpression.FRIENDLY, "Sir, yes Sir!").also { stage++ }
            8 -> npcl(FacialExpression.CHILD_NORMAL, "Carry on Private!").also { stage = END_DIALOGUE }

            9 -> npcl(FacialExpression.CHILD_NORMAL, "Aten...hut!").also { stage++ }
            10 -> npcl(FacialExpression.CHILD_NORMAL, "I can't believe they stuck me with you...").also { stage++ }
            11 -> npcl(FacialExpression.CHILD_NORMAL, "What in the name of all the layers of the abyss do you think you're doing, biped?").also { stage++ }
            12 -> playerl(FacialExpression.FRIENDLY, "Sir, Private Player reporting for immediate active duty, Sir!").also { stage++ }
            13 -> npcl(FacialExpression.CHILD_NORMAL, "As you were, Private!").also { stage = END_DIALOGUE }

            14 -> playerl(FacialExpression.FRIENDLY, "Buck up, Sir, it's not that bad.").also { stage++ }
            15 -> npcl(FacialExpression.CHILD_NORMAL, "Stow that, Private, and get back to work!").also { stage++ }
            16 -> playerl(FacialExpression.FRIENDLY, "Sir, yes Sir!").also { stage = END_DIALOGUE }

            17 -> playerl(FacialExpression.FRIENDLY, "Sir, nothing Sir!").also { stage++ }
            18 -> npcl(FacialExpression.CHILD_NORMAL, "Well double-time it, Private, whatever it is!").also { stage++ }
            19 -> playerl(FacialExpression.FRIENDLY, "Sir, yes Sir!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BULL_ANT_6867, NPCs.BULL_ANT_6868)
    }

}
