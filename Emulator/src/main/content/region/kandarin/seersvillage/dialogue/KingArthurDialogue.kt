package content.region.kandarin.seersvillage.dialogue

import content.region.kandarin.quest.merlin.dialogue.KingArthurDialogueFile
import org.rs.consts.NPCs
import core.api.isQuestComplete
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable
import org.rs.consts.QuestName

/**
 * Represents the King arthur dialogue.
 */
@Initializable
class KingArthurDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        if (!isQuestComplete(player, QuestName.MERLINS_CRYSTAL)) {
            openDialogue(player, KingArthurDialogueFile(), NPCs.KING_ARTHUR_251)
        } else {
            openDialogue(player, content.region.kandarin.quest.grail.dialogue.KingArthurHGDialogue(), NPCs.KING_ARTHUR_251)
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.KING_ARTHUR_251)
    }
}