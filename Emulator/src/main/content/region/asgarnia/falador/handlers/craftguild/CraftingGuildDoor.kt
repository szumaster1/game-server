package content.region.asgarnia.falador.handlers.craftguild

import org.rs.consts.NPCs
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE

/**
 * Represents the Crafting Guild door dialogue file.
 */
class CraftingGuildDoor(val it: Int) : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.MASTER_CRAFTER_805)
        when (it) {
            0 -> when (stage) {
                0 -> npcl(FacialExpression.FRIENDLY, "Welcome to the Guild of Master craftsmen.").also { stage = END_DIALOGUE }
            }
            1 -> when (stage) {
                0 -> npcl(FacialExpression.ASKING, "Where's your brown apron? You can't come in here unless you're wearing one.").also { stage++ }
                1 -> player(FacialExpression.SAD, "Err... I haven't got one.").also { stage = END_DIALOGUE }
            }
            2 -> when (stage) {
                0 -> npcl(FacialExpression.NEUTRAL, "Sorry, only experienced crafters are allowed in here. You must be level 40 or above to enter.").also { stage = END_DIALOGUE }
            }
        }

    }


}
