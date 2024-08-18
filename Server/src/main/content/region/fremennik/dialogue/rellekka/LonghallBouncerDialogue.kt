package content.region.fremennik.dialogue.rellekka

import core.api.consts.NPCs
import core.api.isQuestComplete
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Longhall bouncer dialogue.
 */
@Initializable
class LonghallBouncerDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (!isQuestComplete(player, "Fremennik Trials")) {
            npcl(FacialExpression.ANNOYED, "Hey, outerlander. You can't go through there. Talent only, backstage.").also { stage = END_DIALOGUE }
        } else {
            npcl(FacialExpression.ANNOYED, "You can't go through there. Talent only, backstage.")
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> player(FacialExpression.PANICKED, "But I'm a Bard!").also { stage++ }
            1 -> npcl(FacialExpression.FRIENDLY, "No you're not. I saw your performance. I was paid well to keep you from ever setting foot on stage here again.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.LONGHALL_BOUNCER_1278)
    }
}
