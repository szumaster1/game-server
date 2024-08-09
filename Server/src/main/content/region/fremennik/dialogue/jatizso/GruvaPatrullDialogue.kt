package content.region.fremennik.dialogue.jatizso

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Gruva patrull dialogue.
 */
@Initializable
class GruvaPatrullDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npcl(FacialExpression.FRIENDLY, "Ho! Outerlander.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.HALF_ASKING, "What's down the scary-looking staircase?").also { stage++ }
            1 -> npcl(FacialExpression.NEUTRAL, "These are the stairs down to the mining caves. There are rich veins of many types down there, and miners too. Though be careful; some of the trolls occasionally sneak into the far end of the cave.").also { stage++ }
            2 -> playerl(FacialExpression.NEUTRAL, "Thanks. I'll look out for them.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GRUVA_PATRULL_5500)
    }

}
