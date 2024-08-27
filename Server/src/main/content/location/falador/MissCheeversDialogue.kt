package content.location.falador

import cfg.consts.NPCs
import content.region.asgarnia.quest.rd.dialogue.MissCheeversDialogueFile
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the Miss cheevers dialogue.
 */
@Initializable
class MissCheeversDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        openDialogue(player, MissCheeversDialogueFile(), npc)
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MISS_CHEEVERS_2288)
    }
}