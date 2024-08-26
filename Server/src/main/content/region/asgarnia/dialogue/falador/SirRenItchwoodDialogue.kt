package content.region.asgarnia.dialogue.falador

import content.region.asgarnia.quest.rd.dialogue.SirRenItchwoodDialogueFile
import cfg.consts.NPCs
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the Sir ren itchwood dialogue.
 */
@Initializable
class SirRenItchwoodDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        openDialogue(player, SirRenItchwoodDialogueFile(), npc)
        return true
    }

    override fun newInstance(player: Player): Dialogue {
        return SirRenItchwoodDialogue(player)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SIR_REN_ITCHOOD_2287)
    }
}