package content.region.kandarin.seersvillage.dialogue

import content.region.kandarin.quest.merlin.dialogue.SirTristramDialogueFile
import org.rs.consts.NPCs
import core.api.isQuestComplete
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable
import org.rs.consts.QuestName

/**
 * Represents the Sir tristram dialogue.
 */
@Initializable
class SirTristramDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        if (!isQuestComplete(player, QuestName.MERLINS_CRYSTAL)) {
            openDialogue(player, SirTristramDialogueFile(), NPCs.SIR_TRISTRAM_243)
        } else {
            openDialogue(player, content.region.kandarin.quest.grail.dialogue.SirTristramHGDialogue(), NPCs.SIR_TRISTRAM_243)
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SIR_TRISTRAM_243)
    }
}