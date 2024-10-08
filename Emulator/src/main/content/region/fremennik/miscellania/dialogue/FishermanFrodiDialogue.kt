package content.region.fremennik.miscellania.dialogue

import org.rs.consts.NPCs
import core.game.dialogue.DialogueFile
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE

/**
 * Represents the Fisherman frodi dialogue.
 */
class FishermanFrodiDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.FISHERMAN_FRODI_1397)
        when (stage) {
            0 -> npc("Hello.").also { stage = END_DIALOGUE }
        }
    }
}