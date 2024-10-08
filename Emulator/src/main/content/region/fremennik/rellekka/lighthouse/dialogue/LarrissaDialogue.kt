package content.region.fremennik.rellekka.lighthouse.dialogue

import org.rs.consts.NPCs
import content.region.fremennik.rellekka.lighthouse.quest.horror.dialogue.LarrissaDialogueFile
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.START_DIALOGUE

/**
 * Represents the Larrissa dialogue.
 */
@Initializable
class LarrissaDialogue(player: Player? = null): Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            START_DIALOGUE -> openDialogue(player, LarrissaDialogueFile(), npc)
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.LARRISSA_1336, NPCs.LARRISSA_1337)
    }
}
