package content.region.kandarin.seers_village.dialogue

import content.region.kandarin.quest.grail.dialogue.SirPalomedesHolyGrailQuestDialogueFile
import content.region.kandarin.quest.arthur.dialogue.SirPalomedesDialogueFile
import cfg.consts.NPCs
import core.api.isQuestComplete
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the Sir palomedes dialogue.
 */
@Initializable
class SirPalomedesDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        if (!isQuestComplete(player, "Merlin's Crystal")) {
            openDialogue(player, SirPalomedesDialogueFile(), NPCs.SIR_PALOMEDES_3787)
        } else {
            openDialogue(player, SirPalomedesHolyGrailQuestDialogueFile(), NPCs.SIR_PALOMEDES_3787)
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SIR_PALOMEDES_3787)
    }
}