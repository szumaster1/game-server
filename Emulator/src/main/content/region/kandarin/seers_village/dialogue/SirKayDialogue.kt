package content.region.kandarin.seers_village.dialogue

import content.region.kandarin.quest.grail.dialogue.SirKayHolyGrailQuestDialogueFile
import content.region.kandarin.quest.arthur.dialogue.SirKayDialogueFile
import cfg.consts.NPCs
import core.api.isQuestComplete
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the Sir kay dialogue.
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