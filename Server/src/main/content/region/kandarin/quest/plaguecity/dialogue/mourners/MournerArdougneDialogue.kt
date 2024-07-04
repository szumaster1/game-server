package content.region.kandarin.quest.plaguecity.dialogue.mourners

import core.api.consts.NPCs
import core.api.getQuestStage
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class MournerArdougneDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if (getQuestStage(player, "Plague City") > 1) {
            openDialogue(player, MournerPlagueCityDialogue(), npc.asNpc())
        } else {
            playerl(FacialExpression.FRIENDLY, "Hello.").also { stage = 1 }
        }
        return true
    }
    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            1 -> npcl(FacialExpression.NEUTRAL, "What are you up to?").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "Nothing.").also { stage++ }
            3 -> npcl(FacialExpression.NEUTRAL, "I don't trust you.").also { stage++ }
            4 -> playerl(FacialExpression.FRIENDLY, "You don't have to.").also { stage++ }
            5 -> npcl(FacialExpression.FRIENDLY, "If I find you attempting to cross the wall I'll make sure you never return.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray = intArrayOf(NPCs.MOURNER_3216)
}
