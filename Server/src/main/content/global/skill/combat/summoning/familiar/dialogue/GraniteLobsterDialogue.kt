package content.global.skill.combat.summoning.familiar.dialogue

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class GraniteLobsterDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when ((0..4).random()) {
            0 -> npcl(FacialExpression.CHILD_NORMAL, "We shall heap the helmets of the fallen into a mountain!").also { stage = 0 }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "We march to war, Fremennik Player Name. Glory and plunder for all!").also { stage = 2 }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "Fremennik Player Name, what is best in life?").also { stage = 3 }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "Ho, my Fremennik brother, shall we go raiding?").also { stage = 5 }
            4 -> npcl(FacialExpression.CHILD_NORMAL, "Clonkclonk clonk grind clonk. (Keep walking, outerlander. We have nothing to discuss.)").also { stage = 7 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "The outerlanders have insulted our heritage for the last time!").also { stage++ }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "The longhall will resound with our celebration!").also { stage = END_DIALOGUE }

            2 -> playerl(FacialExpression.FRIENDLY, "Yes! We shall pile gold before the longhall of our tribesmen!").also { stage = END_DIALOGUE }

            3 -> playerl(FacialExpression.FRIENDLY, "Crush your enemies, see them driven before you, and hear the lamentation of their women!").also { stage++ }
            4 -> npcl(FacialExpression.CHILD_NORMAL, "I would have settled for raw sharks, but that's good too!").also { stage = END_DIALOGUE }

            5 -> playerl(FacialExpression.FRIENDLY, "Well, I suppose we could when I'm done with this.").also { stage++ }
            6 -> npcl(FacialExpression.CHILD_NORMAL, "Yes! To the looting and the plunder!").also { stage = END_DIALOGUE }

            7 -> playerl(FacialExpression.FRIENDLY, "Fair enough.").also { stage++ }
            8 -> npcl(FacialExpression.CHILD_NORMAL, "Clonkclonkclonk grind clonk grind? (It's nothing personal, you're just an outerlander, you know?)").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GRANITE_LOBSTER_6849, NPCs.GRANITE_LOBSTER_6850)
    }

}
