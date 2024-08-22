package content.minigame.mta.dialogue

import content.minigame.mta.impl.TelekineticZone
import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Maze Guardian dialogue.
 */
@Initializable
class MazeGuardianDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        player("Hi!").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                npc(FacialExpression.OLD_NORMAL, "Well done on releasing me. Would you like to try", "another maze?").also { stage++ }
            }
            1 -> options("Yes please!", "No thanks.").also { stage++ }
            2 -> when (buttonId) {
                1 -> player("Yes please!").also { stage = 4 }
                2 -> player("No thanks.").also { stage++ }

            }
            3 -> npc(FacialExpression.OLD_NORMAL, "Very well. Talk to me if you want to move onto the", "next maze, or you can return to the entrance hall", "through the portal.").also { stage = END_DIALOGUE }
            4 -> npc(FacialExpression.OLD_NORMAL, "Very well, I shall teleport you.").also { stage++ }
            5 -> {
                npc.clear()
                TelekineticZone.getZone(player).setup()
                end()
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MAZE_GUARDIAN_3102)
    }

}
