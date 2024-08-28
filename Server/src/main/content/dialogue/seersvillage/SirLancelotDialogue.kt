package content.dialogue.seersvillage

import content.region.kandarin.quest.holygrail.dialogue.SirLancelotHolyGrailQuestDialogueFile
import content.region.kandarin.quest.merlinsquest.dialogue.SirLancelotDialogueFile
import cfg.consts.NPCs
import core.api.isQuestComplete
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the Sir lancelot dialogue.
 */
@Initializable
class SirLancelotDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        if (!isQuestComplete(player, "Merlin's Crystal")) {
            openDialogue(player, SirLancelotDialogueFile(), NPCs.SIR_LANCELOT_239)
        } else {
            openDialogue(player, SirLancelotHolyGrailQuestDialogueFile(), NPCs.SIR_LANCELOT_239)
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SIR_LANCELOT_239)
    }
}