package content.region.asgarnia.falador.dialogue

import content.region.asgarnia.falador.quest.rd.dialogue.MsHynnTerprettDialogueFile
import org.rs.consts.NPCs
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the Ms hynn terprett dialogue.
 */
@Initializable
class MsHynnTerprettDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        openDialogue(player, MsHynnTerprettDialogueFile(), npc)
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MS_HYNN_TERPRETT_2289)
    }
}