package content.region.kandarin.seersvillage.dialogue

import content.region.kandarin.quest.merlin.dialogue.LadyOfTheLakeDialogueFile
import content.region.kandarin.quest.merlin.dialogue.TheLadyOfTheLakeDialogueFile
import org.rs.consts.NPCs
import core.api.isQuestComplete
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the Lady of the lake dialogue.
 */
@Initializable
class LadyOfTheLakeDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        if (!isQuestComplete(player, "Merlin's Crystal")) {
            openDialogue(player, LadyOfTheLakeDialogueFile(), NPCs.THE_LADY_OF_THE_LAKE_250)
        } else {
            openDialogue(player, TheLadyOfTheLakeDialogueFile(), NPCs.THE_LADY_OF_THE_LAKE_250)
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.THE_LADY_OF_THE_LAKE_250)
    }
}