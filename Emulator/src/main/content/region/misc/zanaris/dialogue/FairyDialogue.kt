package content.region.misc.zanaris.dialogue

import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.RandomFunction
import org.rs.consts.NPCs

/**
 * Represents the Fairy dialogue.
 */
@Initializable
class FairyDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        when (RandomFunction.random(1, 7)) {
            1 -> npcl(FacialExpression.OLD_CALM_TALK1, "Err, hello. Do you have any idea what a Winter Fairy actually does?").also { stage = 10 }
            2 -> npcl(FacialExpression.OLD_DISTRESSED, "Have you got any idea where Burthorpe is? The co-ordinator says I need to go there and paint all the leaves yellow, but I've never heard of that place!").also { stage = END_DIALOGUE }
            3 -> npcl(FacialExpression.OLD_DISTRESSED, "Be careful when you pick mushrooms near the strange ruins to the south. Some of them will try to eat you!").also { stage = END_DIALOGUE }
            4 -> npcl(FacialExpression.OLD_DISTRESSED, "I'm afraid I can't stop to chat. I've just been told to get ready to go to Etceteria for some reason or other!").also { stage = END_DIALOGUE }
            5 -> npcl(FacialExpression.OLD_CALM_TALK2, "Picking mushrooms near the forge will probably end quite badly for you. Not as bad as the ones by the strange ruins to the south though. They're much bigger.").also { stage = END_DIALOGUE }
            6 -> npc(FacialExpression.OLD_DISTRESSED, "Sorry, I can't talk I'm looking for Sarah-Jane!").also { stage = END_DIALOGUE }
            7 -> npc(FacialExpression.OLD_CALM_TALK2, "Is your name Freddie, and are you losing your teeth?").also { stage = 20 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            10 -> player(FacialExpression.THINKING, "No, no idea at all.").also { stage++ }
            11 -> npcl(FacialExpression.OLD_DISTRESSED, "Oh, pity. Neither do I, but I'm supposed to be one this week.").also { stage = END_DIALOGUE }
            20 -> player(FacialExpression.THINKING, "Err, no. I'm afraid not.").also { stage++ }
            21 -> npcl(FacialExpression.OLD_CALM_TALK2, "Oh, right. You don't have any idea where I can find him do you?").also { stage++ }
            22 -> player(FacialExpression.NEUTRAL, "No, I don't know that either.").also { stage++ }
            23 -> npcl(FacialExpression.OLD_CALM_TALK1, "Oh, ok then. If you do find out please let me know. The co-ordinator has sent me to find him.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.FAIRY_4443, NPCs.FAIRY_4444, NPCs.FAIRY_4445, NPCs.FAIRY_4446, NPCs.FAIRY_567, NPCs.FAIRY_57)
    }

}
