package content.region.kandarin.dialogue.seersvillage

import content.region.kandarin.quest.holygrail.dialogue.SirPelleaseHolyGrailQuestDialogueFile
import content.region.kandarin.quest.merlinsquest.dialogue.SirPelleasDialogueFile
import core.api.consts.NPCs
import core.api.isQuestComplete
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Sir pellaeas dialogue.
 */
@Initializable
class SirPellaeasDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        if (!isQuestComplete(player, "Merlin's Crystal")) {
            openDialogue(player, SirPelleasDialogueFile(), NPCs.SIR_PELLEAS_244)
        } else {
            openDialogue(player, SirPelleaseHolyGrailQuestDialogueFile(), NPCs.SIR_PELLEAS_244)
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SIR_PELLEAS_244)
    }
}