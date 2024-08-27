package content.location.miscellania

import cfg.consts.NPCs
import core.api.isQuestComplete
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the Fishmonger (Miscellania) dialogue.
 */
@Initializable
class FishmongerMiscDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (!isQuestComplete(player, "Throne of Miscellania")) {
            npcl(FacialExpression.FRIENDLY, "Greetings, Sir. Get your fresh fish here! I've heard that the Etceterian fish is stored in a cow shed.").also { stage = 0 }
        } else {
            npcl(FacialExpression.FRIENDLY, "Greetings, Your Highness. Have some fresh fish! I've heard that the Etceterian fish is stored in a cow shed.").also { stage = 0 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                end()
                openNpcShop(player, NPCs.FISHMONGER_1393)
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.FISHMONGER_1393)
    }
}