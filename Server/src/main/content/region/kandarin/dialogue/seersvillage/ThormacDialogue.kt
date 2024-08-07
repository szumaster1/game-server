package content.region.kandarin.dialogue.seersvillage

import content.region.kandarin.quest.scorpcather.dialogue.ThormacDialogueFile
import core.api.consts.NPCs
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.START_DIALOGUE

/**
 * Thormac dialogue.
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
