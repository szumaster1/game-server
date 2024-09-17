package content.region.asgarnia.falador.dialogue

import org.rs.consts.NPCs
import content.region.asgarnia.falador.quest.rd.dialogue.LadyTableDialogueFile
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the Lady table dialogue.
 */
@Initializable
class LadyTableDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        openDialogue(player, LadyTableDialogueFile(), npc)
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.LADY_TABLE_2283)
    }
}