package content.region.asgarnia.dialogue.dwarvmines

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Drokar dialogue.
 */
@Initializable
class DrokarDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        player(FacialExpression.HALF_GUILTY, "Hello, how are you?").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.OLD_DEFAULT, "Packages, packages and more!").also { stage++ }
            1 -> player(FacialExpression.OLD_DEFAULT, "Ugh.. Okay, have a good day.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.DRORKAR_7723) // 7729
    }

}
