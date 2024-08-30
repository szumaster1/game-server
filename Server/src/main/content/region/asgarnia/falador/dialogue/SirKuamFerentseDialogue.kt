package content.region.asgarnia.falador.dialogue

import content.region.asgarnia.falador.quest.rd.dialogue.SirKuamFerentseDialogueFile
import cfg.consts.NPCs
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the Sir kuam ferentse dialogue.
 */
@Initializable
class SirKuamFerentseDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        openDialogue(player, SirKuamFerentseDialogueFile(), npc)
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SIR_KUAM_FERENTSE_2284)
    }
}