package content.region.misc.dialogue.entrana

import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable

@Initializable
class ConeDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HALF_GUILTY, "Hello deary.")
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                player(FacialExpression.HALF_GUILTY, "Um... hello.")
                stage = 1
            }

            1 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(217)
    }
}
