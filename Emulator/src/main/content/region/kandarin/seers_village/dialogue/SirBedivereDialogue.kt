package content.region.kandarin.seers_village.dialogue

import content.region.kandarin.quest.grail.dialogue.SirBedivereHolyGrailQuestDialogueFile
import content.region.kandarin.quest.arthur.dialogue.SirBedivereDialogueFile
import org.rs.consts.NPCs
import core.api.isQuestComplete
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the Sir bedivere dialogue.
 */
@Initializable
class SirBedivereDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        if (!isQuestComplete(player, "Merlin's Crystal")) {
            openDialogue(player, SirBedivereDialogueFile(), NPCs.SIR_BEDIVERE_242)
        } else {
            openDialogue(player, SirBedivereHolyGrailQuestDialogueFile(), NPCs.SIR_BEDIVERE_242)
        }
        return true
    }
    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SIR_BEDIVERE_242)
    }
}