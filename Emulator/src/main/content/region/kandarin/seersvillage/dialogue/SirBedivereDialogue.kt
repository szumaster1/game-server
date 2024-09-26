package content.region.kandarin.seersvillage.dialogue

import content.region.kandarin.quest.grail.dialogue.SirBedivereHGDialogue
import content.region.kandarin.quest.merlin.dialogue.SirBedivereDialogueFile
import org.rs.consts.NPCs
import core.api.isQuestComplete
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable
import org.rs.consts.QuestName

/**
 * Represents the Sir bedivere dialogue.
 */
@Initializable
class SirBedivereDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        if (!isQuestComplete(player, QuestName.MERLINS_CRYSTAL)) {
            openDialogue(player, SirBedivereDialogueFile(), NPCs.SIR_BEDIVERE_242)
        } else {
            openDialogue(player, SirBedivereHGDialogue(), NPCs.SIR_BEDIVERE_242)
        }
        return true
    }
    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SIR_BEDIVERE_242)
    }
}