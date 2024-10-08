package content.region.kandarin.seersvillage.dialogue

import content.region.kandarin.seersvillage.quest.scorpcatcher.dialogue.ThormacDialogueFile
import org.rs.consts.NPCs
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.START_DIALOGUE

/**
 * Represents the Thormac dialogue.
 */
@Initializable
class ThormacDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            START_DIALOGUE -> openDialogue(player, ThormacDialogueFile())
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.THORMAC_389)
    }
}
