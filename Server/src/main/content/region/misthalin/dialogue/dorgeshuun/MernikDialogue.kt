package content.region.misthalin.dialogue.dorgeshuun

import core.api.consts.NPCs
import core.api.sendNPCDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Mernik dialogue.
 */
@Initializable
class MernikDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        when ((1..5).random()) {
            1 -> sendNPCDialogue(player, NPCs.CAVE_GOBLIN_CHILD_5816, "Haha! You look funny!", FacialExpression.OLD_NORMAL).also { stage = 1 }
            2 -> sendNPCDialogue(player, NPCs.CAVE_GOBLIN_CHILD_5816, "You smell!", FacialExpression.OLD_NORMAL).also { stage = 3 }
            3 -> sendNPCDialogue(player, NPCs.CAVE_GOBLIN_CHILD_5816, "Are you really from the surface?", FacialExpression.OLD_NORMAL).also { stage = 4 }
            4 -> sendNPCDialogue(player, NPCs.CAVE_GOBLIN_CHILD_5816, "Are you ${player.username}? Did you help Zanik save the city?", FacialExpression.OLD_NORMAL).also { stage = 5 }
            5 -> sendNPCDialogue(player, NPCs.CAVE_GOBLIN_CHILD_5816, "Shan't!", FacialExpression.OLD_NORMAL).also { stage++ }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            1 -> npc(FacialExpression.OLD_NORMAL, "I'm sure you look just as funny to the surface-dweller!").also { stage = END_DIALOGUE }
            2 -> npcl(FacialExpression.OLD_NORMAL, "There's no need to be rude!").also { stage++ }
            3 -> npcl(FacialExpression.OLD_NORMAL, "I'm sorry about that, surface-dweller.").also { stage = END_DIALOGUE }
            4 -> playerl(FacialExpression.FRIENDLY, "Yes I am.").also { stage = END_DIALOGUE }
            5 -> npcl(FacialExpression.OLD_NORMAL, "Just because " + (if (player.isMale) "he's" else "she's") + " a surface-dweller, doesn't mean " + (if (player.isMale) "he's" else "she's") + " ${player.username}! There are lots of surface-dwellers!").also { stage++ }
            6 -> playerl(FacialExpression.FRIENDLY, "Actually I am ${player.username}.").also { stage++ }
            7 -> npcl(FacialExpression.OLD_NORMAL, "${player.username}? Really?").also { stage++ }
            8 -> npcl(FacialExpression.OLD_NORMAL, "Zanik told me about you! I'm so pleased to meet you!").also { stage = END_DIALOGUE }
            9 -> npcl(FacialExpression.OLD_NORMAL, "Well don't blame me if he doesn't want to talk to you either!").also { stage++ }
            10 -> npcl(FacialExpression.OLD_NORMAL, "Sorry about that.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MERNIK_5782)
    }

}
