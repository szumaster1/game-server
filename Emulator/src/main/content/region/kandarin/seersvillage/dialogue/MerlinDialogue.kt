package content.region.kandarin.seersvillage.dialogue

import content.region.kandarin.quest.merlin.dialogue.MerlinDialogueFile
import org.rs.consts.NPCs
import core.api.isQuestComplete
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable
import org.rs.consts.QuestName

/**
 * Represents the Merlin dialogue.
 */
@Initializable
class MerlinDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        if (!isQuestComplete(player, QuestName.MERLINS_CRYSTAL)) {
            openDialogue(player, MerlinDialogueFile(false), NPCs.MERLIN_249)
        } else {
            openDialogue(player, content.region.kandarin.quest.grail.dialogue.MerlinHGDialogue(), NPCs.MERLIN_249)
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MERLIN_249)
    }
}