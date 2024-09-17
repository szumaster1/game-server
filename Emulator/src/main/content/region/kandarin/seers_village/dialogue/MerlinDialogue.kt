package content.region.kandarin.seers_village.dialogue

import content.region.kandarin.quest.grail.dialogue.MerlinHolyGrailQuestDialogueFile
import content.region.kandarin.quest.arthur.dialogue.MerlinDialogueFile
import org.rs.consts.NPCs
import core.api.isQuestComplete
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the Merlin dialogue.
 */
@Initializable
class MerlinDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        if (!isQuestComplete(player, "Merlin's Crystal")) {
            openDialogue(player, MerlinDialogueFile(false), NPCs.MERLIN_249)
        } else {
            openDialogue(player, MerlinHolyGrailQuestDialogueFile(), NPCs.MERLIN_249)
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MERLIN_249)
    }
}