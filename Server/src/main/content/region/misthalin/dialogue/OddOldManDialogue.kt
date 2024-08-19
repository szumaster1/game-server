package content.region.misthalin.dialogue

import core.api.consts.NPCs
import content.region.misthalin.quest.member.ragandboneman.dialogue.OddOldManDialogueFile
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the Odd Old Man dialogue.
 */
@Initializable
class OddOldManDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        openDialogue(player!!, OddOldManDialogueFile(), npc)
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ODD_OLD_MAN_3670)
    }
}
