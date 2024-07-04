package content.region.fremennik.dialogue.waterbrith

import core.api.consts.NPCs
import core.api.getAttribute
import core.api.isQuestComplete
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class BardurDialogue(player: Player? = null): Dialogue(player) {
    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (!isQuestComplete(player, "Fremennik Trials")) {
            npcl(FacialExpression.FRIENDLY, "Ah! Outerlander! Do not interrupt me in my business! I must cull these fiends!").also { stage = 0 }
        } else {
            npcl(FacialExpression.FRIENDLY, "Hello there Bardur. How's it going?").also { stage = 4 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "What are you doing down here?").also { stage++ }
            1 -> npcl(FacialExpression.FRIENDLY, "Only an outerlander would ask such a question! Is it not obvious what I am doing?").also { stage++ }
            2 -> npcl(FacialExpression.FRIENDLY, "I kill the daggermouths for glory and for sport! When I have had my fill, I move on to the daggermouths' lair!").also { stage++ }
            3 -> playerl(FacialExpression.FRIENDLY, "Erm... okay then. I'll leave you to it.").also { stage = END_DIALOGUE }
            4 -> playerl(FacialExpression.FRIENDLY, "Hello there Bardur. How's it going?").also { stage++ }
            5 -> npcl(FacialExpression.FRIENDLY, "Listen, ${getAttribute(player!!, "fremennikname", "name")}. I'm sorry about the way I acted while you were exiled. Bardur gets hostile around outerlanders.").also { stage++ }
            6 -> playerl(FacialExpression.FRIENDLY, "Don't worry about it, I understand! All's well that ends well! How've you been?").also { stage++ }
            7 -> npcl(FacialExpression.FRIENDLY, "I have been here all the long day, slaughtering the daggermouth spawns so that none may pass!").also { stage++ }
            8 -> npcl(FacialExpression.FRIENDLY, "My sword arm grows weary, and the blood of my foes hangs heavy upon my clothing!").also { stage++ }
            9 -> playerl(FacialExpression.FRIENDLY, "Can you tell me anything about the caves down here?").also { stage++ }
            10 -> npcl(FacialExpression.FRIENDLY, "Aye, that I can ${getAttribute(player!!, "fremennikname", "name")}! Yonder lies the lair of the daggermouth kings, the three beasts of legend!").also { stage++ }
            11 -> npcl(FacialExpression.FRIENDLY, "I train my skills upon its foul brood, to prepare myself for the fight ahead!").also { stage++ }
            12 -> playerl(FacialExpression.FRIENDLY, "Okay, thanks Bardur. Do you have any food with you?").also { stage++ }
            13 -> npcl(FacialExpression.FRIENDLY, "Ah, you did not come prepared ${getAttribute(player!!, "fremennikname", "name")}?").also { stage++ }
            14 -> npcl(FacialExpression.FRIENDLY, "I have nothing to spare, but my equipment grows weaker under the onslaught of the dagger-mouth spawns!").also { stage++ }
            15 -> npcl(FacialExpression.FRIENDLY, "I will trade you a finely cooked shark if you have a replacement for my helm, my shield or my blade...").also { stage++ }
            16 -> npcl(FacialExpression.FRIENDLY, "Give me any equipment you wish to trade, and I will pay you a shark for it.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BARDUR_2879)
    }

}
