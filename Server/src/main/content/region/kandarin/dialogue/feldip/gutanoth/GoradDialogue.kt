package content.region.kandarin.dialogue.feldip.gutanoth

import core.api.consts.NPCs
import core.api.isQuestComplete
import core.api.sendDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Gorad dialogue.
 */
@Initializable

class GoradDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if (!isQuestComplete(player, "Watchtower")) {
            sendDialogue(player!!, "Gorad is busy; try again later.").also { stage = END_DIALOGUE}
        } else {
            player("Hello!")
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> player("I seek another task.").also { stage++ }
            1 -> npcl(FacialExpression.OLD_CALM_TALK2, "You know who I is?").also { stage++ }
            2 -> options("A big, ugly brown creature.", "I don't know who you are..").also { stage++ }
            3 -> when (buttonId) {
                1 -> player("A big, ugly brown creature.").also { stage++ }
                2 -> player("I don't know who you are.").also { stage = 5 }
            }
            4 -> {
                end()
                npc.attack(player!!)
            }
            5 -> npcl(FacialExpression.OLD_NORMAL, "I am Gorad, and you are tiny. Go now and I won't chase you!").also { stage = END_DIALOGUE }
        }
        return true
    }


    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GORAD_856)
    }
}
