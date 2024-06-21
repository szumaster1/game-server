package content.region.kandarin.dialogue.seersvillage

import content.region.kandarin.quest.holygrail.dialogue.SirTristramHolyGrailQuestDialogueFile
import content.region.kandarin.quest.merlinsquest.dialogue.SirTristramDialogueFile
import core.api.consts.NPCs
import core.api.isQuestComplete
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable

@Initializable
class SirTristramDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        if (!isQuestComplete(player, "Merlin's Crystal")) {
            openDialogue(player, SirTristramDialogueFile(), NPCs.SIR_TRISTRAM_243)
        } else {
            openDialogue(player, SirTristramHolyGrailQuestDialogueFile(), NPCs.SIR_TRISTRAM_243)
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SIR_TRISTRAM_243)
    }
}