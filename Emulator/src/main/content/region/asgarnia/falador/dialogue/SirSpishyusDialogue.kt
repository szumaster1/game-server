package content.region.asgarnia.falador.dialogue

import content.region.asgarnia.falador.quest.rd.dialogue.SirSpishyusDialogueFile
import org.rs.consts.NPCs
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the Sir spishyus dialogue.
 */
@Initializable
class SirSpishyusDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        openDialogue(player, SirSpishyusDialogueFile(), npc)
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SIR_SPISHYUS_2282)
    }
}