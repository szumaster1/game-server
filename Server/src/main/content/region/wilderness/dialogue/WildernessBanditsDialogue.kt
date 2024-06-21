package content.region.wilderness.dialogue

import core.api.consts.NPCs
import core.api.sendDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.utilities.END_DIALOGUE

@Initializable
class WildernessBanditsDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        sendDialogue(player, "${npc.name} is not interested in talking.").also { stage = END_DIALOGUE }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BLACK_HEATHER_202, NPCs.DONNY_THE_LAD_203, NPCs.SPEEDY_KEITH_204)
    }
}
