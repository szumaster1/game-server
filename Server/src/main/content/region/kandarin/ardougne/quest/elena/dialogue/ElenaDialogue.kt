package content.region.kandarin.ardougne.quest.elena.dialogue

import cfg.consts.NPCs
import core.api.getQuestStage
import core.api.setQuestStage
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Elena dialogue.
 */
@Initializable
class ElenaDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if (getQuestStage(player,"Plague City") >= 16) {
            playerl(FacialExpression.FRIENDLY, "Hi, you're free to go! Your kidnappers don't seem to be about right now.").also { stage = 1 }
        } else {
            npcl(FacialExpression.FRIENDLY, "Go and see my father, I'll make sure he adequately rewards you. Now I'd better leave while I still can.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun handle(componentID: Int, buttonID: Int): Boolean {
        when (stage) {
            1 -> npcl(FacialExpression.FRIENDLY, "Thank you, being kidnapped was so inconvenient. I was on my way back to East Ardougne with some samples, I want to see if I can diagnose a cure for this plague.").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "Well you can leave via the manhole near the gate.").also { stage++ }
            3 -> npcl(FacialExpression.FRIENDLY, "Go and see my father, I'll make sure he adequately rewards you. Now I'd better leave while I still can.").also { stage++ }
            4 -> {
                end()
                setQuestStage(player!!, "Plague City", 99)
                stage = END_DIALOGUE
            }
        }
        return true
    }

    override fun getIds(): IntArray = intArrayOf(NPCs.ELENA_3215)
}
