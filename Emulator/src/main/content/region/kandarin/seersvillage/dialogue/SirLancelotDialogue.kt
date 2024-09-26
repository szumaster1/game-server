package content.region.kandarin.seersvillage.dialogue

import content.region.kandarin.quest.merlin.dialogue.SirLancelotDialogueFile
import org.rs.consts.NPCs
import core.api.isQuestComplete
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable
import org.rs.consts.QuestName

/**
 * Represents the Sir lancelot dialogue.
 */
@Initializable
class SirLancelotDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        if (!isQuestComplete(player, QuestName.MERLINS_CRYSTAL)) {
            openDialogue(player, SirLancelotDialogueFile(), NPCs.SIR_LANCELOT_239)
        } else {
            openDialogue(player, content.region.kandarin.quest.grail.dialogue.SirLancelotHGDialogue(), NPCs.SIR_LANCELOT_239)
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SIR_LANCELOT_239)
    }
}