package content.region.misthalin.quest.free.shieldofarrav.dialogue

import content.region.misthalin.quest.free.shieldofarrav.ShieldofArrav
import core.api.consts.NPCs
import core.api.sendMessage
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.tools.END_DIALOGUE

/**
 * Jonnythe beard dialogue.
 */
class JonnytheBeardDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (ShieldofArrav.isPhoenixMission(player)) {
            sendMessage(player, "Johnny the beard is not interested in talking.")
            end()
            return true
        }
        npc(FacialExpression.HALF_GUILTY, "Will you buy me a beer?")
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> player(FacialExpression.HALF_GUILTY, "No, I don't think I will.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun newInstance(player: Player): Dialogue {
        return JonnytheBeardDialogue(player)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.JONNY_THE_BEARD_645)
    }
}