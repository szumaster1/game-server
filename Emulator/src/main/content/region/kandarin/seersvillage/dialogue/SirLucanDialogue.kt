package content.region.kandarin.seersvillage.dialogue

import content.region.kandarin.quest.merlin.dialogue.SirLucanDialogueFile
import org.rs.consts.NPCs
import core.api.isQuestComplete
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable
import org.rs.consts.QuestName

/**
 * Represents the Sir lucan dialogue.
 */
@Initializable
class SirLucanDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        if (!isQuestComplete(player, QuestName.MERLINS_CRYSTAL)) {
            openDialogue(player, SirLucanDialogueFile(), NPCs.SIR_LUCAN_245)
        } else {
            openDialogue(player, content.region.kandarin.quest.grail.dialogue.SirLucanHGDialogue(), NPCs.SIR_LUCAN_245)
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SIR_LUCAN_245)
    }
}