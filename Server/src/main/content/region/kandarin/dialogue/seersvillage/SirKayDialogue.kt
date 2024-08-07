package content.region.kandarin.dialogue.seersvillage

import content.region.kandarin.quest.holygrail.dialogue.SirKayHolyGrailQuestDialogueFile
import content.region.kandarin.quest.merlinsquest.dialogue.SirKayDialogueFile
import core.api.consts.NPCs
import core.api.isQuestComplete
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Sir kay dialogue.
 */
@Initializable
class SirKayDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        if (!isQuestComplete(player, "Merlin's Crystal")) {
            openDialogue(player, SirKayDialogueFile(), NPCs.SIR_KAY_241)
        } else {
            openDialogue(player, SirKayHolyGrailQuestDialogueFile(), NPCs.SIR_KAY_241)
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SIR_KAY_241)
    }
}