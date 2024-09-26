package content.region.kandarin.seersvillage.dialogue

import content.region.kandarin.quest.merlin.dialogue.SirPalomedesDialogueFile
import org.rs.consts.NPCs
import core.api.isQuestComplete
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable
import org.rs.consts.QuestName

/**
 * Represents the Sir palomedes dialogue.
 */
@Initializable
class SirPalomedesDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        if (!isQuestComplete(player, QuestName.MERLINS_CRYSTAL)) {
            openDialogue(player, SirPalomedesDialogueFile(), NPCs.SIR_PALOMEDES_3787)
        } else {
            openDialogue(player, content.region.kandarin.quest.grail.dialogue.SirPalomedesHGDialogue(), NPCs.SIR_PALOMEDES_3787)
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SIR_PALOMEDES_3787)
    }
}