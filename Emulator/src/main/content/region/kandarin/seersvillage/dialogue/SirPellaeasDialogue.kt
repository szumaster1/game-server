package content.region.kandarin.seersvillage.dialogue

import content.region.kandarin.quest.grail.dialogue.SirPelleaseHGDialogue
import content.region.kandarin.quest.merlin.dialogue.SirPelleasDialogueFile
import org.rs.consts.NPCs
import core.api.isQuestComplete
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable
import org.rs.consts.QuestName

/**
 * Represents the Sir pellaeas dialogue.
 */
@Initializable
class SirPellaeasDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        if (!isQuestComplete(player, QuestName.MERLINS_CRYSTAL)) {
            openDialogue(player, SirPelleasDialogueFile(), NPCs.SIR_PELLEAS_244)
        } else {
            openDialogue(player, SirPelleaseHGDialogue(), NPCs.SIR_PELLEAS_244)
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SIR_PELLEAS_244)
    }
}