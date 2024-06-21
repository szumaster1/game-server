package content.region.asgarnia.dialogue.falador

import content.region.asgarnia.quest.recruitmentdrive.dialogue.SirTinleyDialogueFile
import core.api.consts.NPCs
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable

@Initializable
class SirTinleyDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        openDialogue(player, SirTinleyDialogueFile(), npc)
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SIR_TINLEY_2286)
    }
}