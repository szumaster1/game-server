package content.region.kandarin.seersvillage.dialogue

import content.region.kandarin.quest.grail.dialogue.SirGawainHGDialogue
import content.region.kandarin.quest.merlin.dialogue.SirGawainDialogueFile
import org.rs.consts.NPCs
import core.api.isQuestComplete
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable
import org.rs.consts.QuestName

/**
 * Represents the Sir gawain dialogue.
 */
@Initializable
class SirGawainDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        if (!isQuestComplete(player, QuestName.MERLINS_CRYSTAL)) {
            openDialogue(player, SirGawainDialogueFile(), NPCs.SIR_GAWAIN_240)
        } else {
            openDialogue(player, SirGawainHGDialogue(), NPCs.SIR_GAWAIN_240)
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SIR_GAWAIN_240)
    }
}