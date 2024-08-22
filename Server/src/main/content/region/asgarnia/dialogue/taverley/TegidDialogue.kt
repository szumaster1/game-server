package content.region.asgarnia.dialogue.taverley

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.world.map.Direction
import core.game.world.map.Location
import core.plugin.Initializable

/**
 * Represents the Tegid dialogue.
 */
@Initializable
class TegidDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Tegid is a druid washing dirty laundry in
     * the lake in Taverley.
     * Location: 2914,3418
     */

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npc.direction = Direction.SOUTH
        npc.faceLocation(Location(2923, 3418, 0))
        player(FacialExpression.HALF_GUILTY, "So, you're doing laundry, eh?")
        stage = 0
        npc.faceLocation(Location(2923, 3418, 0))
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.HALF_GUILTY, "Yeah. What is it to you?").also { stage = 1 }
            1 -> player(FacialExpression.HALF_GUILTY, "Nice day for it.").also {  stage = 2 }
            2 -> npc(FacialExpression.HALF_GUILTY, "Suppose it is.").also {  stage = 3 }

            3 -> {
                npc.faceLocation(Location(2923, 3418, 0))
                end()
            }
        }
        return true
    }

    override fun newInstance(player: Player): Dialogue {
        return TegidDialogue(player)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.TEGID_1213)
    }
}
