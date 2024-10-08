package content.region.kandarin.seersvillage.dialogue

import content.region.kandarin.quest.grail.dialogue.SirKayHGDialogue
import content.region.kandarin.quest.merlin.dialogue.SirKayDialogueFile
import org.rs.consts.NPCs
import core.api.isQuestComplete
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable
import org.rs.consts.QuestName

/**
 * Represents the Sir kay dialogue.
 */
@Initializable
class SirKayDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        if (!isQuestComplete(player, QuestName.MERLINS_CRYSTAL)) {
            openDialogue(player, SirKayDialogueFile(), NPCs.SIR_KAY_241)
        } else {
            openDialogue(player, SirKayHGDialogue(), NPCs.SIR_KAY_241)
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SIR_KAY_241)
    }
}