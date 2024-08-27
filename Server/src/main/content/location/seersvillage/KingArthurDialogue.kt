package content.location.seersvillage

import content.region.kandarin.quest.holygrail.dialogue.KingArthurHolyGrailQuestDialogueFile
import content.region.kandarin.quest.merlinsquest.dialogue.KingArthurDialogueFile
import cfg.consts.NPCs
import core.api.isQuestComplete
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the King arthur dialogue.
 */
@Initializable
class KingArthurDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        if (!isQuestComplete(player, "Merlin's Crystal")) {
            openDialogue(player, KingArthurDialogueFile(), NPCs.KING_ARTHUR_251)
        } else {
            openDialogue(player, KingArthurHolyGrailQuestDialogueFile(), NPCs.KING_ARTHUR_251)
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.KING_ARTHUR_251)
    }
}