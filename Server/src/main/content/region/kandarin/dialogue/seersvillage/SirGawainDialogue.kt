package content.region.kandarin.dialogue.seersvillage

import content.region.kandarin.quest.holygrail.dialogue.SirGawainHolyGrailQuestDialogueFile
import content.region.kandarin.quest.merlinsquest.dialogue.SirGawainDialogueFile
import cfg.consts.NPCs
import core.api.isQuestComplete
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the Sir gawain dialogue.
 */
@Initializable
class SirGawainDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        if (!isQuestComplete(player, "Merlin's Crystal")) {
            openDialogue(player, SirGawainDialogueFile(), NPCs.SIR_GAWAIN_240)
        } else {
            openDialogue(player, SirGawainHolyGrailQuestDialogueFile(), NPCs.SIR_GAWAIN_240)
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SIR_GAWAIN_240)
    }
}