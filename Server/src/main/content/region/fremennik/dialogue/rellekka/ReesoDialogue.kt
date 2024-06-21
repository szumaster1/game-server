package content.region.fremennik.dialogue.rellekka

import core.api.consts.NPCs
import core.api.getAttribute
import core.api.isQuestComplete
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.utilities.END_DIALOGUE

@Initializable
class ReesoDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (!isQuestComplete(player, "Fremennik Trials")) {
            npcl(FacialExpression.ANNOYED, "Please do not disturb me, outerlander. I have much to do.").also { stage = END_DIALOGUE }
        } else {
            npcl(FacialExpression.STRUGGLE, "Sorry, ${getAttribute(player, "fremennikname", "fremmyname")}, I must get on with my work.").also { stage = END_DIALOGUE }
        }

        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.REESO_3116)
    }
}
