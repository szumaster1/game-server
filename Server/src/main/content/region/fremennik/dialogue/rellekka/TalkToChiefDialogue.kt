package content.region.fremennik.dialogue.rellekka

import cfg.consts.NPCs
import core.api.isQuestComplete
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Talk to chief dialogue.
 */
@Initializable
class TalkToChiefDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (!isQuestComplete(player, "Fremennik Trials")) {
            npcl(FacialExpression.ANNOYED, "I cannot speak to you outerlander! Talk to Brundt, the Chieftain!").also { stage = END_DIALOGUE }
        } else {
            player(FacialExpression.FRIENDLY, "Hello.")
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.FRIENDLY, "Hello to you, too!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BORROKAR_1307, NPCs.FREIDIR_1306, NPCs.INGA_1314, NPCs.JENNELLA_1312, NPCs.LANZIG_1308, NPCs.PONTAK_1309, NPCs.SASSILIK_1313)
    }
}
