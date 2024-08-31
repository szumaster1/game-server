package content.region.kandarin.seers_village.dialogue

import content.region.kandarin.quest.grail.dialogue.SirPelleaseHolyGrailQuestDialogueFile
import content.region.kandarin.quest.arthur.dialogue.SirPelleasDialogueFile
import cfg.consts.NPCs
import core.api.isQuestComplete
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the Sir pellaeas dialogue.
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