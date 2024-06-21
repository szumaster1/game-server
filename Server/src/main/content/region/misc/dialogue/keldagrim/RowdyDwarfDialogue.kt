package content.region.misc.dialogue.keldagrim

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.utilities.END_DIALOGUE
import core.utilities.START_DIALOGUE

@Initializable
class RowdyDwarfDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            START_DIALOGUE -> npcl(FacialExpression.OLD_DRUNK_LEFT, "Get OUT of my WAY!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ROWDY_DWARF_2187)
    }
}

