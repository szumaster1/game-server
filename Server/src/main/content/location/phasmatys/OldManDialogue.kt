package content.location.phasmatys

import cfg.consts.NPCs
import content.region.morytania.quest.ghostsahoy.dialogue.OldManGADialogue
import core.api.getQuestStage
import core.api.isQuestComplete
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Old man dialogue.
 */
@Initializable
class OldManDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Info: Found in the shipwreck northwest of Port Phasmatys.
     * When he was 12, he left his mother and joined a band of pirates as a cabin boy.
     * At some point, he and the crew attacked Port Phasmatys, taking the book of haricanto.
     * The ship later crashed and the captain died in his seat. The man became delusional,
     * taking orders from the skeleton and claiming that they were just waiting for the right time to set sail.
     * When an adventurer presented him with his old toy boat, he handed over the key to the chest containing the book,
     * then waited for his mother to come collect him.
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when {
            isQuestComplete(player, "Ghosts Ahoy") -> player("How is it going?").also { stage = 5 }
            getQuestStage(player, "Ghosts Ahoy") >= 4 -> openDialogue(player, OldManGADialogue())
            else -> player("What are you doing on this shipwreck?")
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.HALF_GUILTY, "Shipwreck?!? Not shipwreck, surely not! Just in port, that's all!").also { stage++ }
            1 -> player("Don't be silly - half the ship's missing!").also { stage++ }
            2 -> npcl(FacialExpression.HALF_GUILTY, "No no no - the captain's just waiting for the wind to change, then we're off!").also { stage++ }
            3 -> player("You mean the skeleton sitting here in this chair?").also { stage++ }
            4 -> npcl(FacialExpression.HALF_GUILTY, "You must show more respect to the Captain.").also { stage = END_DIALOGUE }
            5 -> npcl(FacialExpression.HAPPY, "Wonderful, wonderful! Mother's coming to get me!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.OLD_MAN_1696)
    }

}
